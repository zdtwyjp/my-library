(function() {
    var  local, map, layer, vectorLayer, markerLayer, drawPoint,
        centersArray = [], weightsArray = [], n = 0,
        style = {
            strokeColor: "#304DBE",
            strokeWidth: 1,
            pointerEvents: "visiblePainted",
            fillColor: "#304DBE",
            fillOpacity: 0.4
        };
    function dowork() {
        var create = '服务中心点', create1 = '提交', remove = '清除';
        addBtn(create,selectCenters);
        addBtn(create1,findServiceAreas);
        addBtn(remove,clearElements);

        vectorLayer = new SuperMap.Layer.Vector("Vector Layer");
        drawPoint = new SuperMap.Control.DrawFeature(vectorLayer, SuperMap.Handler.Point);     
        drawPoint.events.on({"featureadded": drawCompleted});
        map = new SuperMap.Map("map",{controls: [
            new SuperMap.Control.LayerSwitcher(),
            new SuperMap.Control.ScaleLine(),
            new SuperMap.Control.PanZoomBar(),
            new SuperMap.Control.Navigation({
                dragPanOptions: {
                                    enableKinetic: true
                                }}),
            drawPoint], units: "m"
        });
        layer = new SuperMap.Layer.TiledDynamicRESTLayer("Changchun", DemoURL.changChun_Map, {transparent: true, cacheEnabled: true}, {maxResolution:"auto"});
        layer.events.on({"layerInitialized":addLayer});                
        vectorLayer = new SuperMap.Layer.Vector("Vector Layer");
        markerLayer = new SuperMap.Layer.Markers("Markers");
    }

    function addLayer() {

        map.addLayers([layer, vectorLayer, markerLayer]);
        map.setCenter(new SuperMap.LonLat(4503.6240321526, -3861.911472192499), 1);
    }
    function selectCenters(){
        clearElements();
        drawPoint.activate();
    }
    function drawCompleted(drawGeometryArgs){
        var point = drawGeometryArgs.feature.geometry,
            size = new SuperMap.Size(44, 33),
            offset = new SuperMap.Pixel(-(size.w/2), -size.h),
            icon = new SuperMap.Icon("../resource/controlImages/marker.png", size, offset);
        markerLayer.addMarker(new SuperMap.Marker(new SuperMap.LonLat(point.x, point.y), icon));
        centersArray.push(point);
        n++;
        weightsArray.push(400+n*100);
    }
    function findServiceAreas() {
        vectorLayer.removeAllFeatures();
        drawPoint.deactivate();
        var findServiceAreasService, parameter, analystParameter, resultSetting;
        resultSetting = new SuperMap.REST.TransportationAnalystResultSetting({
            returnEdgeFeatures: true,
                      returnEdgeGeometry: true,
                      returnEdgeIDs: true,
                      returnNodeFeatures: true,
                      returnNodeGeometry: true,
                      returnNodeIDs: true,
                      returnPathGuides: true,
                      returnRoutes: true
        });
        analystParameter = new SuperMap.REST.TransportationAnalystParameter({
            resultSetting: resultSetting,
                         weightFieldName: "length"
        });
        parameter = new SuperMap.REST.FindServiceAreasParameters({
            centers: centersArray,
                  isAnalyzeById: false,
                  parameter: analystParameter,
                  weights: weightsArray
        });     
        findServiceAreasService  = new SuperMap.REST.FindServiceAreasService(DemoURL.changchun_RoadNet, {
            eventListeners: {"processCompleted": processCompleted}});
        findServiceAreasService.processAsync(parameter);
    }
    function processCompleted(findServiceAreasEventArgs) {
        var result = findServiceAreasEventArgs.result,
            features = [];
        if (result.serviceAreaList) {
            for (var i=0,serviceAreaList=result.serviceAreaList,len=serviceAreaList.length; i<len; i++) {
                var feature = new SuperMap.Feature.Vector();
                feature.geometry = serviceAreaList[i].serviceRegion;
                feature.style = style;
                features.push(feature);
            }
        }
        vectorLayer.addFeatures(features);
    }
    function clearElements() {
        n = 0;
        centersArray = [];
        weightsArray = [];
        markerLayer.clearMarkers();
        vectorLayer.removeAllFeatures();
    }
    dowork();
})();
