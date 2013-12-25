(function() {
    var local, map, layer, markerLayer1, markerLayer2,
    centerPoint = new SuperMap.Geometry.Point(121, 31);//构造一个点的几何图形

    function dowork() {
        var create = '查询', remove = '清除';
        addBtn(create,queryByDistance);
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
        markerLayer1 = new SuperMap.Layer.Markers("Markers");
        markerLayer2 = new SuperMap.Layer.Markers("Markers");

        //在地图上具体位置上画一个图形
        var point = centerPoint,
            size = new SuperMap.Size(44, 40),
            offset = new SuperMap.Pixel(-(size.w/2), -size.h),
            icon = new SuperMap.Icon("../resource/controlImages/marker-gold.png", size, offset);
        markerLayer1.addMarker(new SuperMap.Marker(new SuperMap.LonLat(point.x, point.y), icon));
    }

    function addLayer() {
        map.addLayers([layer, markerLayer1, markerLayer2]);
        map.setCenter(new SuperMap.LonLat(0, 0), 0);
    }
    function queryByDistance() {
        markerLayer2.clearMarkers();

        var queryByDistanceParams = new SuperMap.REST.QueryByDistanceParameters({
            queryParams: new Array(new SuperMap.REST.FilterParameter({name: "Capitals@World.1"})),
            returnContent: true,
            distance: 30,
            geometry: centerPoint
        }); 

        var queryByDistanceService = new SuperMap.REST.QueryByDistanceService(DemoURL.world);
        queryByDistanceService.events.on({
            "processCompleted": processCompleted,
            "processFailed": processFailed
        });
        queryByDistanceService.processAsync(queryByDistanceParams);
    }
    function processCompleted(queryEventArgs) {
        var i, j, result = queryEventArgs.result;
        for(i = 0;i < result.recordsets.length; i++) {
            for(j = 0; j < result.recordsets[i].features.length; j++) {
                var point = result.recordsets[i].features[j].geometry,
                    size = new SuperMap.Size(44, 33),
                         offset = new SuperMap.Pixel(-(size.w/2), -size.h),
                         icon = new SuperMap.Icon("../resource/controlImages/marker.png", size, offset);
                markerLayer2.addMarker(new SuperMap.Marker(new SuperMap.LonLat(point.x, point.y), icon));
            }
        }
    }
    function processFailed(e) {
        doMapAlert("",e.error.errorMsg,true);
    }
    function clearFeatures() {
        //清除上次的显示结果
        markerLayer2.clearMarkers();
    }
    dowork();
})();
