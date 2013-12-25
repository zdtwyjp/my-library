(function() {
    var map, layerWorld, layerJingjing;
    function dowork() {
        map = new SuperMap.Map("map",{controls: [
            new SuperMap.Control.LayerSwitcher(),
            new SuperMap.Control.ScaleLine(),
            new SuperMap.Control.OverviewMap(),
            new SuperMap.Control.PanZoomBar(),
            new SuperMap.Control.Navigation({
                dragPanOptions: {
                                    enableKinetic: true
                                }
            })], allOverlays: true
        });
        layerWorld = new SuperMap.Layer.TiledDynamicRESTLayer("World", DemoURL.world, {transparent: true, cacheEnabled: true});
        layerWorld.events.on({"layerInitialized": addLayer1});
    }

    function addLayer1(){
        layerJingjing = new SuperMap.Layer.TiledDynamicRESTLayer("京津地区地图", DemoURL.jingjin_map, {transparent: true, cacheEnabled: true});
        layerJingjing.events.on({"layerInitialized": addLayer2});
    }
    function addLayer2(){
        map.addLayers([layerWorld,layerJingjing]);
        map.setCenter(new SuperMap.LonLat(118, 40), 6);
    }
    dowork();
})();
