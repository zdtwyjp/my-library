(function() {
    var local, map, layer, vectorLayer, markerLayer1,
        markerLayer2, drawPoint, eventPoint, n = 0, 
        facilityPoints = [
            new SuperMap.Geometry.Point(6000, -5500),
            new SuperMap.Geometry.Point(5500, -2500),
            new SuperMap.Geometry.Point(2500, -3500)],
        style = {
            strokeColor: "#304DBE",
            strokeWidth: 3,
            pointerEvents: "visiblePainted",
            fill: false
        };
    function dowork() {
        var create = '事件点', create1 = '提交', remove = '清除';
        addBtn(create,selectEventPoint);
        addBtn(create1,findClosestFacilities);
        addBtn(remove,clearElements);

        vectorLayer = new SuperMap.Layer.Vector("Vector Layer");
        drawPoint = new SuperMap.Control.DrawFeature(vectorLayer, SuperMap.Handler.Point);
        drawPoint.events.on({ "featureadded": drawCompleted });
        map = new SuperMap.Map("map", { controls: [
            new SuperMap.Control.LayerSwitcher(),
            new SuperMap.Control.ScaleLine(),
            new SuperMap.Control.PanZoomBar(),
            new SuperMap.Control.Navigation({
                dragPanOptions: {
                                    enableKinetic: true
                                }
            }),
            drawPoint], units: "m"
        });
        layer = new SuperMap.Layer.TiledDynamicRESTLayer("Changchun", DemoURL.changChun_Map, {transparent: true, cacheEnabled: true}, { maxResolution: "auto" });
        layer.events.on({ "layerInitialized": addLayer });
        markerLayer1 = new SuperMap.Layer.Markers("Markers");
        markerLayer2 = new SuperMap.Layer.Markers("Markers");

        var size = new SuperMap.Size(44, 40),
            offset = new SuperMap.Pixel(-(size.w / 2), -size.h),
            icon1 = new SuperMap.Icon("../resource/controlImages/marker-gold.png", size, offset),
            icon2 = new SuperMap.Icon("../resource/controlImages/marker-gold.png", size, offset),
            icon3 = new SuperMap.Icon("../resource/controlImages/marker-gold.png", size, offset);
        markerLayer1.addMarker(new SuperMap.Marker(new SuperMap.LonLat(6000, -5500), icon1));
        markerLayer1.addMarker(new SuperMap.Marker(new SuperMap.LonLat(5500, -2500), icon2));
        markerLayer1.addMarker(new SuperMap.Marker(new SuperMap.LonLat(2500, -3500), icon3));
    }

    function addLayer() {
        map.addLayers([layer, vectorLayer, markerLayer1, markerLayer2])
            map.setCenter(new SuperMap.LonLat(4503.6240321526, -3861.911472192499), 1);
    }
    function selectEventPoint() {
        clearElements();
        drawPoint.activate();
    }
    function drawCompleted(drawGeometryArgs) {
        var point = drawGeometryArgs.feature.geometry,
            size = new SuperMap.Size(44, 33),
            offset = new SuperMap.Pixel(-(size.w / 2), -size.h),
            icon = new SuperMap.Icon("../resource/controlImages/marker.png", size, offset);
        markerLayer2.addMarker(new SuperMap.Marker(new SuperMap.LonLat(point.x, point.y), icon));
        eventPoint = point;
        n++;
        if (n >= 1) {
            drawPoint.deactivate();
        }
    }
    function findClosestFacilities() {
        drawPoint.deactivate();
        var findClosestFacilitiesService, parameter, analystParameter, resultSetting;
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
        parameter = new SuperMap.REST.FindClosestFacilitiesParameters({
            event: eventPoint,
                  expectFacilityCount: 1,
                  isAnalyzeById: false,
                  facilities: facilityPoints,
                  parameter: analystParameter
        });
        findClosestFacilitiesService = new SuperMap.REST.FindClosestFacilitiesService(DemoURL.changchun_RoadNet, {
            eventListeners: { "processCompleted": processCompleted }
        });
        findClosestFacilitiesService.processAsync(parameter);
    }
    function processCompleted(findClosestFacilitiesEventArgs) {
        var result = findClosestFacilitiesEventArgs.result,
            features = [];
        if (result.facilityPathList) {
            for (var i = 0, facilityPathList = result.facilityPathList, len = facilityPathList.length; i < len; i++) {
                var feature = new SuperMap.Feature.Vector();
                feature.geometry = facilityPathList[i].route.line;
                feature.style = style;
                features.push(feature);
            }
        }
        vectorLayer.addFeatures(features);
    }
    function clearElements() {
        eventPoint = null;
        n = 0;
        markerLayer2.clearMarkers();
        vectorLayer.removeAllFeatures();
    }
    dowork();
})();
