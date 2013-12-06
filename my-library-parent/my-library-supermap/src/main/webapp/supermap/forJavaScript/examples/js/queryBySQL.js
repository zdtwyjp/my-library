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
        addBtn(create,queryBySQL);
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
    function queryBySQL() {
        vectorLayer.removeAllFeatures();

        var queryParam, queryBySQLParams, queryBySQLService;
        queryParam = new SuperMap.REST.FilterParameter({
            name: "Countries@World.1",
                   attributeFilter: "Pop_1994>1000000000 and SmArea>900"
        });
        queryBySQLParams = new SuperMap.REST.QueryBySQLParameters({
            queryParams: [queryParam]
        });
        queryBySQLService = new SuperMap.REST.QueryBySQLService(DemoURL.world, {
           eventListeners: {"processCompleted": processCompleted, "processFailed": processFailed}});
        queryBySQLService.processAsync(queryBySQLParams);
    }
    function processCompleted(queryEventArgs) {
        var i, j, feature, 
            result = queryEventArgs.result;
        if (result && result.recordsets) {
            for (i=0; i<result.recordsets.length; i++) {
                if (result.recordsets[i].features) {
                    for (j=0; j<result.recordsets[i].features.length; j++) {
                        feature = result.recordsets[i].features[j];
                        feature.style = style;
                        vectorLayer.addFeatures(feature);
                    }
                }
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
