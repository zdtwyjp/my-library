﻿(function() {
    var map, local, layer, vectorLayer, drawPoint, drawLine, drawPolygon, markerLayer,
        style = {
            strokeColor: "#304DBE",
            strokeWidth: 1,
            pointerEvents: "visiblePainted",
            fillColor: "#304DBE",
            fillOpacity: 0.8,
            pointRadius:2
        };
    function dowork() {
        var create = '点', create1 = '线', create2 = '面', remove = '清除';
        addBtn(create,drawPointGeometry);
        addBtn(create1,drawLineGeometry);
        addBtn(create2,drawPolygonGeometry);
        addBtn(remove,clearFeatures);

        layer = new SuperMap.Layer.TiledDynamicRESTLayer("World", DemoURL.world, {transparent: true, cacheEnabled: true}, {maxResolution:"auto"}); 
        layer.events.on({"layerInitialized":addLayer});     
        vectorLayer = new SuperMap.Layer.Vector("Vector Layer");
        markerLayer = new SuperMap.Layer.Markers("Markers");

        drawPoint = new SuperMap.Control.DrawFeature(vectorLayer, SuperMap.Handler.Point);
        drawPoint.events.on({"featureadded": drawCompleted});

        drawLine = new SuperMap.Control.DrawFeature(vectorLayer, SuperMap.Handler.Path);
        drawLine.events.on({"featureadded": drawCompleted})

            drawPolygon = new SuperMap.Control.DrawFeature(vectorLayer, SuperMap.Handler.Polygon);     
        drawPolygon.events.on({"featureadded": drawCompleted});

        map = new SuperMap.Map("map",{controls: [
            new SuperMap.Control.LayerSwitcher(),
            new SuperMap.Control.ScaleLine(),
            new SuperMap.Control.PanZoomBar(),
            new SuperMap.Control.Navigation({
                dragPanOptions: {
                                    enableKinetic: true
                                }}),
            drawPoint, drawLine, drawPolygon]
        });
    }

    function addLayer() {
        map.addLayers([layer, vectorLayer, markerLayer]);
        map.setCenter(new SuperMap.LonLat(0, 0), 1);
    } 
    function drawPointGeometry() {
        //先清除上次的显示结果
        vectorLayer.removeAllFeatures();
        markerLayer.clearMarkers();

        drawPoint.activate();
    }
    function drawLineGeometry() {
        //先清除上次的显示结果
        vectorLayer.removeAllFeatures();
        markerLayer.clearMarkers();

        drawLine.activate();
    }
    function drawPolygonGeometry() {
        //先清除上次的显示结果
        vectorLayer.removeAllFeatures();
        markerLayer.clearMarkers();

        drawPolygon.activate();
    }
    function drawCompleted(drawGeometryArgs) {
        drawPoint.deactivate();
        drawLine.deactivate();
        drawPolygon.deactivate();
        var feature = new SuperMap.Feature.Vector();
        feature.geometry = drawGeometryArgs.feature.geometry,
            feature.style = style;
        vectorLayer.addFeatures(feature);

        var getFeatureParameter, getFeatureService;
        getFeatureParameter = new SuperMap.REST.GetFeaturesByBufferParameters({
            bufferDistance: 30,
                            //attributeFilter: "SMID > 0",                
                            datasetNames: ["World:Capitals"],
                            returnContent:true,
                            geometry: drawGeometryArgs.feature.geometry
        });
        getFeatureService = new SuperMap.REST.GetFeaturesByBufferService(DemoURL.world_data, {
            eventListeners: {
                                "processCompleted": processCompleted,
                          "processFailed": processFailed
                            }
        });
        getFeatureService.processAsync(getFeatureParameter);
    }
    
     function processCompleted(getFeaturesEventArgs) {
        var i, len, features, result = getFeaturesEventArgs.result;
        if (result && result.features) {
            features = result.features;
            for (i=0, len=features.length; i<len; i++) {
                var point = features[i].geometry,
                    size = new SuperMap.Size(44, 33),
                    offset = new SuperMap.Pixel(-(size.w/2), -size.h),
                    icon = new SuperMap.Icon("../resource/controlImages/marker.png", size, offset);
                markerLayer.addMarker(new SuperMap.Marker(new SuperMap.LonLat(point.x, point.y), icon));
            }
        }
    }
    
    function processFailed(e) {
        doMapAlert("",e.error.errorMsg,true);
    }
    function clearFeatures() {
        vectorLayer.removeAllFeatures();
        markerLayer.clearMarkers();
    }
    dowork();
})();
