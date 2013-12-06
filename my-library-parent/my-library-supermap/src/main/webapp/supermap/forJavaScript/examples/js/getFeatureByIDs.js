(function() {
    var map, local, layer, vectorLayer,
        style = {
            strokeColor: "#304DBE",
            strokeWidth: 1,
            fillColor: "#304DBE",
            fillOpacity: "0.8"
        };
    function dowork() {
        var create = '查询', remove = '清除';
        addBtn(create,getFeaturesByIDs);
        addBtn(remove,clearFeatures);

        map = new SuperMap.Map("map",{controls: [
            new SuperMap.Control.LayerSwitcher(),
            new SuperMap.Control.ScaleLine(),
            new SuperMap.Control.PanZoomBar(),
            new SuperMap.Control.Navigation({
                dragPanOptions: {
                                    enableKinetic: true
                                }
            })]
        });
        layer = new SuperMap.Layer.TiledDynamicRESTLayer("World", DemoURL.world, {transparent: true, cacheEnabled: true}, {maxResolution:"auto"}); 
        layer.events.on({"layerInitialized":addLayer});    
        vectorLayer = new SuperMap.Layer.Vector("Vector Layer");
    }

    function addLayer() {
        map.addLayers([layer, vectorLayer]);
        map.setCenter(new SuperMap.LonLat(0, 0), 0);
    }
    function getFeaturesByIDs() {
        vectorLayer.removeAllFeatures();

        var getFeaturesByIDsParameters, getFeaturesByIDsService;
        getFeaturesByIDsParameters = new SuperMap.REST.GetFeaturesByIDsParameters({
            returnContent: true,
                                   datasetNames: ["World:Countries"],
                                   fromIndex: 0,
                                   toIndex:-1,
                                   IDs: [1,247]
        });
        getFeaturesByIDsService = new SuperMap.REST.GetFeaturesByIDsService(DemoURL.world_data, {
            eventListeners: {"processCompleted": processCompleted, "processFailed": processFailed}});
        getFeaturesByIDsService.processAsync(getFeaturesByIDsParameters);
    }
    function processCompleted(getFeaturesEventArgs) {
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
        //先清除上次的显示结果
        vectorLayer.removeAllFeatures();
        vectorLayer.refresh();
    }
    dowork();
})();
