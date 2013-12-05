(function() {
    var map, layer;
    function dowork() {
        map = new SuperMap.Map("map", { controls: [
            new SuperMap.Control.ScaleLine(),
            new SuperMap.Control.PanZoomBar(),
            new SuperMap.Control.Navigation({
                dragPanOptions: {
                                    enableKinetic: true
                                }
            })], units: "m"
        });
        layer = new SuperMap.Layer.CloudLayer();
        map.addLayer(layer);
        map.setCenter(new SuperMap.LonLat(0, 0), 2);
    }
    dowork();
})();
