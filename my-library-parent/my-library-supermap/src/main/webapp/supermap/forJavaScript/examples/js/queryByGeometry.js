(function() {
    var map, local, layer, vectorLayer, drawPolygon, markerLayer,
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
        markerLayer = new SuperMap.Layer.Markers("Markers");

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
        map.addLayers([layer, vectorLayer, markerLayer]);
        map.setCenter(new SuperMap.LonLat(0, 0), 0);
    }        
    function drawGeometry() {
        //先清除上次的显示结果
        vectorLayer.removeAllFeatures();
        markerLayer.clearMarkers();

        drawPolygon.activate();
    }
    function drawCompleted(drawGeometryArgs) {
        var feature = new SuperMap.Feature.Vector();
        feature.geometry = drawGeometryArgs.feature.geometry,
            feature.style = style;
        vectorLayer.addFeatures(feature);

        var queryParam, queryByGeometryParameters, queryService;
        queryParam = new SuperMap.REST.FilterParameter({name: "Capitals@World.1"});
        queryByGeometryParameters = new SuperMap.REST.QueryByGeometryParameters({
                queryParams: [queryParam], 
                geometry: drawGeometryArgs.feature.geometry,
                spatialQueryMode: SuperMap.REST.SpatialQueryMode.INTERSECT
        });
        queryService = new SuperMap.REST.QueryByGeometryService(DemoURL.world, {
            eventListeners: {
                "processCompleted": processCompleted,
                "processFailed": processFailed
                            }
        });
        queryService.processAsync(queryByGeometryParameters);
    }
    function processCompleted(queryEventArgs) {
        drawPolygon.deactivate();
        var i, j, result = queryEventArgs.result;
        if (result && result.recordsets) {
            for (i=0, recordsets=result.recordsets, len=recordsets.length; i<len; i++) {
                if (recordsets[i].features) {
                    for (j=0; j<recordsets[i].features.length; j++) {
                        var point = recordsets[i].features[j].geometry,
                            size = new SuperMap.Size(44, 33),
                                 offset = new SuperMap.Pixel(-(size.w/2), -size.h),
                                 icon = new SuperMap.Icon("../resource/controlImages/marker.png", size, offset);
                        markerLayer.addMarker(new SuperMap.Marker(new SuperMap.LonLat(point.x, point.y), icon));
                    }
                }
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
