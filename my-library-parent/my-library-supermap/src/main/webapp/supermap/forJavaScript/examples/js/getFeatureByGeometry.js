(function() {
    var map, local, layer, vectorLayer, drawPolygon, 
        style = {
            strokeColor: "#304DBE",
            strokeWidth: 1,
            pointerEvents: "visiblePainted",
            fillColor: "#304DBE",
            fillOpacity: 0.8
        };
    function dowork() {
        var create = '查询', remove = '清除';
        addBtn(create,drawGeometry);
        addBtn(remove,clearFeatures);

        layer = new SuperMap.Layer.TiledDynamicRESTLayer("World", DemoURL.world, {transparent: true, cacheEnabled: true}, {maxResolution:"auto"}); 
        layer.events.on({"layerInitialized":addLayer});     
        vectorLayer = new SuperMap.Layer.Vector("Vector Layer");

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
            drawPolygon]
        });
    }

    function addLayer() {
        map.addLayers([layer, vectorLayer]);
        map.setCenter(new SuperMap.LonLat(0, 0), 1);
    }        
    function drawGeometry() {
        //先清除上次的显示结果
        vectorLayer.removeAllFeatures();
        drawPolygon.activate();
    }
    function drawCompleted(drawGeometryArgs) {
        var geometry = drawGeometryArgs.feature.geometry;
        vectorLayer.removeAllFeatures();
        var getFeaturesByGeometryParameters, getFeaturesByGeometryService;
        getFeaturesByGeometryParameters = new SuperMap.REST.GetFeaturesByGeometryParameters({
            datasetNames: ["World:Countries"],
            toIndex:-1,
            spatialQueryMode:SuperMap.REST.SpatialQueryMode.INTERSECT,
            geometry: drawGeometryArgs.feature.geometry
        });
        getFeaturesByGeometryService = new SuperMap.REST.GetFeaturesByGeometryService(DemoURL.world_data, {
            eventListeners: {
                "processCompleted": processCompleted,
                "processFailed": processFailed
            }
        });
        getFeaturesByGeometryService.processAsync(getFeaturesByGeometryParameters);
    }
    function processCompleted(getFeaturesEventArgs) {
        drawPolygon.deactivate();
        var i, len, features, feature, result = getFeaturesEventArgs.result;
        if (result && result.features) {
            features = result.features
            for (i=0, len=features.length; i<len; i++) {
                feature = features[i];
                feature.style = style;
                vectorLayer.addFeatures(feature);
            }
        }
    }
    function processFailed(e) {
        doMapAlert("",e.error.errorMsg,true);
    }
    function clearFeatures() {
        vectorLayer.removeAllFeatures();
    }
    dowork();
})();
