(function() {
    var local, map, layer, vectorLayer, markerLayer, 
        drawPoint, select, 
        nodeArray = [], pathTime, i = 0, j = 0,
        style = {
            strokeColor: "#304DBE",
            strokeWidth: 3,
            pointerEvents: "visiblePainted",
            fill: false
        },
        styleGuidePoint = {
            pointRadius: 10,
            externalGraphic: "./images/walk.png"
        },
        styleGuideLine = {
            strokeColor: "#25FF25",
            strokeWidth: 6,
            fill: false
        };
    function dowork() {
        var create = '选择站点', create1 = '提交', remove = '清除';
        addBtn(create,selectPoints);
        addBtn(create1,findPath);
        addBtn(remove,clearElements);

        vectorLayer = new SuperMap.Layer.Vector("Vector Layer");
        drawPoint = new SuperMap.Control.DrawFeature(vectorLayer, SuperMap.Handler.Point);
        select = new SuperMap.Control.SelectFeature(vectorLayer, {onSelect: onFeatureSelect, onUnselect: onFeatureUnselect});
        drawPoint.events.on({ "featureadded": drawCompleted });
        map = new SuperMap.Map("map", { controls: [
            new SuperMap.Control.LayerSwitcher(),
            new SuperMap.Control.PanZoomBar({showSlider:false}),
            new SuperMap.Control.Navigation({
                dragPanOptions: {
                    enableKinetic: true
                }
            }),
            drawPoint,
            select], units: "m"
        });
        layer = new SuperMap.Layer.TiledDynamicRESTLayer("Changchun", DemoURL.changChun_Map, {transparent: true, cacheEnabled: true}, { maxResolution: "auto" });
        layer.events.on({ "layerInitialized": addLayer });
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

    //选中时显示路径指引信息
    function onFeatureSelect(feature) {
        if(feature.attributes.description) {
                popup = new SuperMap.Popup("chicken", 
                                         feature.geometry.getBounds().getCenterLonLat(),
                                         new SuperMap.Size(200,30),
                                         "<div style='font-size:.8em; opacity: 0.8'>" + feature.attributes.description + "</div>",
                                         null, false);
                feature.popup = popup;
                map.addPopup(popup);
            }
        if(feature.geometry.CLASS_NAME != "SuperMap.Geometry.Point"){
            feature.style = styleGuideLine;
            vectorLayer.redraw();
        }
    }

    //清除要素时调用此函数
    function onFeatureUnselect(feature) {
        map.removePopup(feature.popup);
        feature.popup.destroy();
        feature.popup = null;
        if(feature.geometry.CLASS_NAME != "SuperMap.Geometry.Point"){
            feature.style = style;
        }
        vectorLayer.redraw();

    }

    function findPath() {
        drawPoint.deactivate();
        var findPathService, parameter, analystParameter, resultSetting;
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
        parameter = new SuperMap.REST.FindPathParameters({
            isAnalyzeById: false,
            nodes: nodeArray,
            hasLeastEdgeCount: false,
            parameter: analystParameter
        });
        if (nodeArray.length <= 1) {
            doMapAlert("","站点数目有误",true);
        }
        findPathService = new SuperMap.REST.FindPathService(DemoURL.changchun_RoadNet, {
            eventListeners: { "processCompleted": processCompleted }
        });
        findPathService.processAsync(parameter);
    }
    function processCompleted(findPathEventArgs) {
        var result = findPathEventArgs.result;
        allScheme(result);
    }
    function allScheme(result) {
        if (i < result.pathList.length) {
            addPath(result);
        } else {
            i = 0;
            //线绘制完成后会绘制关于路径指引点的信息
            addPathGuideItems(result);
        }
    }
    //以动画效果显示分析结果
    function addPath(result) {
        if (j < result.pathList[i].route.points.length) {
            var pathFeature = new SuperMap.Feature.Vector();
            var points = [];
            for (var k = 0; k < 2; k++) {
                if (result.pathList[i].route.points[j + k]) {
                    points.push(new SuperMap.Geometry.Point(result.pathList[i].route.points[j + k].x, result.pathList[i].route.points[j + k].y));
                }
            }
            var curLine = new SuperMap.Geometry.LinearRing(points);
            pathFeature.geometry = curLine;
            pathFeature.style = style;
            vectorLayer.addFeatures(pathFeature);
            //每隔0.001毫秒加载一条弧段
            pathTime = setTimeout(function () { addPath(result); }, 0.001);
            j++;
        } else {
            clearTimeout(pathTime);
            j = 0;
            i++;
            allScheme(result);
        }
    }

    function addPathGuideItems(result){
        vectorLayer.removeAllFeatures();
        //显示每个pathGuideItem和对应的描述信息
        for(var k = 0; k < result.pathList.length; k++){
            var pathGuideItems = result.pathList[i].pathGuideItems, len = pathGuideItems.length;
            for(var m = 0; m < len; m++){
                var guideFeature = new SuperMap.Feature.Vector();
                guideFeature.geometry = pathGuideItems[m].geometry;
                guideFeature.attributes = {description: pathGuideItems[m].description};
                if(guideFeature.geometry.CLASS_NAME === "SuperMap.Geometry.Point"){
                    guideFeature.style = styleGuidePoint;
                }
                else{
                    guideFeature.style = style;
                }
                vectorLayer.addFeatures(guideFeature);
            }
        }
        select.activate();
    }

    function clearElements() {
        n = 0;
        i = 0;
        j = 0;
        nodeArray = [];
        select.deactivate();
        if(vectorLayer.selectedFeatures.length > 0) {
            map.removePopup(vectorLayer.selectedFeatures[0].popup);
        }
        vectorLayer.removeAllFeatures();
        markerLayer.clearMarkers();
    }
    dowork();
})();
