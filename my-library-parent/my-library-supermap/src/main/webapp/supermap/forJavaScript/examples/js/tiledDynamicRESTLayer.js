(function() {
    var map, layer;
    function dowork() {
        map = new SuperMap.Map("map",{controls: [                      
            new SuperMap.Control.ScaleLine(),
            new SuperMap.Control.PanZoomBar(),
            new SuperMap.Control.Navigation({
                dragPanOptions: {
                                    enableKinetic: true
                                }
            })]
        });
        layer = new SuperMap.Layer.TiledDynamicRESTLayer("World", DemoURL.china, {transparent: true, cacheEnabled: true}, {maxResolution:"auto"}); 
        layer.events.on({"layerInitialized": addLayer});
    }

    function addLayer() {
        map.addLayer(layer);
        map.setCenter(new SuperMap.LonLat(0, 0), 3);
    }
    dowork();
})();
