(function() {
    var local, map, layer, vectorLayer, markerLayer,
        drawPoint, nodeArray = [], pathTime, i = 0, j = 0, result,
        style = {
            strokeColor: "#304DBE",
            strokeWidth: 3,
            pointerEvents: "visiblePainted",
            fill: false
        };
    function dowork() {
        var create = '选择站点', create1 = '提交', remove = '清除';
        addBtn(create,selectPoints);
        addBtn(create1,findTSPPaths);
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
        vectorLayer = new SuperMap.Layer.Vector("Vector Layer");
        markerLayer = new SuperMap.Layer.Markers("Markers");
    }

    function addLayer() {
        map.addLayers([layer, vectorLayer, markerLayer]);
        map.setCenter(new SuperMap.LonLat(4503.6240321526, -3861.911472192499), 1);
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
        markerLayer.addMarker(new SuperMap.Marker(new SuperMap.LonLat(point.x, point.y), icon));
        nodeArray.push(point);
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
            doMapAlert("","站点数目有误",true);
        }
        findTSPPathsService = new SuperMap.REST.FindTSPPathsService(DemoURL.changchun_RoadNet, {
            eventListeners: { "processCompleted": processCompleted }
        });
        findTSPPathsService.processAsync(parameter);
    }

    function processCompleted(findTSPPathsEventArgs) {
        result = findTSPPathsEventArgs.result;
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
            pathFeature.style = style;
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
        i = 0;
        j = 0;
        markerLayer.clearMarkers();
        vectorLayer.removeAllFeatures();
    }
    dowork();
})();
