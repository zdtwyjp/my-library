(function() {
    var local, map, layer, vectorLayer, markerLayer1,
        markerLayer2, drawPoint, nodeArray = [], i = 0, j = 0,
        centerArray = [new SuperMap.Geometry.Point(6000, -5500),
            new SuperMap.Geometry.Point(5500, -2500),
            new SuperMap.Geometry.Point(2500, -3500)],
        styleMTSP = {
            strokeColor: "#304DBE",
            strokeWidth: 3,
            pointerEvents: "visiblePainted",
            fill: false
        },
        styleTSP = {
            strokeColor: "red",
            strokeWidth: 2,
            //pointRadius: 8,
            pointerEvents: "visiblePainted",
            fill: false
        };
    function dowork() {
        var create = '配送目标', create1 = '物流配送', create2 = '质检巡查', remove = '清除';
        addBtn(create,selectPoints);
        addBtn(create1,findMTSPPaths);
        addBtn(create2,findTSPPaths);
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
            eventListeners: { "processCompleted": processCompleted1 }
        });
        findMTSPPathsService.processAsync(parameter);
    }
    function processCompleted1(findMTSPPathsEventArgs) {
        var result = findMTSPPathsEventArgs.result,
            features = [];
        if (result.pathList) {
            for (var i = 0, pathList = result.pathList, len = pathList.length; i < len; i++) {
                var feature = new SuperMap.Feature.Vector();
                feature.geometry = pathList[i].route.line;
                feature.style = styleMTSP;
                features.push(feature);
            }
        }
        vectorLayer.addFeatures(features);
    }


    function findTSPPaths() {
        drawPoint.deactivate();
        var findTSPPathsService, parameter, analystParameter, resultSetting;
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
        parameter = new SuperMap.REST.FindTSPPathsParameters({
            isAnalyzeById: false,
                  nodes: nodeArray,
                  endNodeAssigned: false,
                  parameter: analystParameter
        });
        if (nodeArray.length <= 1) {
            doMapAlert("","站点数目应大于2个",true);
        } else {
            findTSPPathsService = new SuperMap.REST.FindTSPPathsService(DemoURL.changchun_RoadNet, {
                eventListeners: { "processCompleted": processCompleted2 }
            });
            findTSPPathsService.processAsync(parameter);
        }
    }

    function processCompleted2(findTSPPathsEventArgs) {
        var result = findTSPPathsEventArgs.result;
        allScheme(result);
    }
    function allScheme(result) {
        if (i < result.tspPathList.length) {
            addPath(result);
        } else {
            i = 0;
        }
    }
    //以动画效果显示分析结果
    function addPath(result) {
        if (j < result.tspPathList[i].route.points.length) {
            var pathFeature = new SuperMap.Feature.Vector();
            var points = [];
            for (var k = 0; k < 2; k++) {
                if (result.tspPathList[i].route.points[j + k]) {
                    points.push(new SuperMap.Geometry.Point(result.tspPathList[i].route.points[j + k].x, result.tspPathList[i].route.points[j + k].y));
                }
            }
            var curLine = new SuperMap.Geometry.LinearRing(points);
            pathFeature.geometry = curLine;
            pathFeature.style = styleTSP;
            vectorLayer.addFeatures(pathFeature);

            //每隔0.01毫秒加载一条弧段
            pathTime = setTimeout(function () { addPath(result); }, 0.01);
            j++;
        } else {
            clearTimeout(pathTime);
            j = 0;
            i++;
            allScheme(result);
        }
    }
    function clearElements() {
        nodeArray = [];
        markerLayer2.clearMarkers();
        vectorLayer.removeAllFeatures();
    }
    dowork();
})();
