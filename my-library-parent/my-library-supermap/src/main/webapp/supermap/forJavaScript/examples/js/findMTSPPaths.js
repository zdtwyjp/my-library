(function() {
    var local, map, layer, vectorLayer, markerLayer1,
        markerLayer2, drawPoint, nodeArray = [], 
        centerArray = [new SuperMap.Geometry.Point(6000, -5500), new SuperMap.Geometry.Point(5500, -2500), new SuperMap.Geometry.Point(2500, -3500)],
        style = {
            strokeColor: "#304DBE",
            strokeWidth: 3,
            pointerEvents: "visiblePainted",
            fill: false
        };
    function dowork() {
        var create = '配送目标', create1 = '提交', remove = '清除';
        addBtn(create,selectPoints);
        addBtn(create1,findMTSPPaths);
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
            offset = new SuperMap.Pixel(-(size.w / 2), -size.h);
        markerLayer1.addMarker(new SuperMap.Marker(new SuperMap.LonLat(6000, -5500), new SuperMap.Icon("../resource/controlImages/marker-gold.png", size, offset)));
        markerLayer1.addMarker(new SuperMap.Marker(new SuperMap.LonLat(5500, -2500), new SuperMap.Icon("../resource/controlImages/marker-gold.png", size, offset)));
        markerLayer1.addMarker(new SuperMap.Marker(new SuperMap.LonLat(2500, -3500), new SuperMap.Icon("../resource/controlImages/marker-gold.png", size, offset)));

        var size = new SuperMap.Size(44, 40),
            offset = new SuperMap.Pixel(-(size.w / 2), -size.h);
        markerLayer1.addMarker(new SuperMap.Marker(new SuperMap.LonLat(6000, -5500), new SuperMap.Icon("../resource/controlImages/marker-gold.png", size, offset)));
        markerLayer1.addMarker(new SuperMap.Marker(new SuperMap.LonLat(5500, -2500), new SuperMap.Icon("../resource/controlImages/marker-gold.png", size, offset)));
        markerLayer1.addMarker(new SuperMap.Marker(new SuperMap.LonLat(2500, -3500), new SuperMap.Icon("../resource/controlImages/marker-gold.png", size, offset)));
    }

    function addLayer() {
        map.addLayers([layer, vectorLayer, markerLayer1, markerLayer2]);
        map.setCenter(new SuperMap.LonLat(4503.6240321526, -3861.911472192499), 0);
    }
    function selectPoints() {
        clearElements();
        drawPoint.activate();
    }
    function drawCompleted(drawGeometryArgs) {
        var point = drawGeometryArgs.feature.geometry,
            size = new SuperMap.Size(44, 33),
            offset = new SuperMap.Pixel(-(size.w / 2), -size.h),
            icon = new SuperMap.Icon("../resource/controlImages/marker.png", size, offset);
        markerLayer2.addMarker(new SuperMap.Marker(new SuperMap.LonLat(point.x, point.y), icon));
        nodeArray.push(point);
    }
    function findMTSPPaths() {
        drawPoint.deactivate();
        var findMTSPPathsService, parameter, analystParameter, resultSetting;
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
        parameter = new SuperMap.REST.FindMTSPPathsParameters({
            centers: centerArray,
                  isAnalyzeById: false,
                  nodes: nodeArray,
                  hasLeastTotalCost: true,
                  parameter: analystParameter
        });
        findMTSPPathsService = new SuperMap.REST.FindMTSPPathsService(DemoURL.changchun_RoadNet, {
            eventListeners: { "processCompleted": processCompleted }
        });
        findMTSPPathsService.processAsync(parameter);
    }
    function processCompleted(findMTSPPathsEventArgs) {
        var result = findMTSPPathsEventArgs.result,
            features = [];
        if (result.pathList) {
            for (var i = 0, pathList = result.pathList, len = pathList.length; i < len; i++) {
                var feature = new SuperMap.Feature.Vector();
                feature.geometry = pathList[i].route.line;
                feature.style = style;
                features.push(feature);
            }
        }
        vectorLayer.addFeatures(features);
    }
    function clearElements() {
        nodeArray = [];
        markerLayer2.clearMarkers();
        vectorLayer.removeAllFeatures();
    }

    dowork();
})();
