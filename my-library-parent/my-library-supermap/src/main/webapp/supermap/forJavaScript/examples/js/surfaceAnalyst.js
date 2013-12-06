(function() {
    var map, local, baseLayer, resultLayer, Spatialanalyst_sample,
        points = new SuperMap.Geometry.LinearRing([
            new SuperMap.Geometry.Point(0,4010338),
            new SuperMap.Geometry.Point(1063524,4010338),
            new SuperMap.Geometry.Point(1063524,3150322),
            new SuperMap.Geometry.Point(0,3150322)
        ]),        
        region = new SuperMap.Geometry.Polygon([points]);
    function dowork() {
        var create = '提取等值线', remove = '移除';
        addBtn(create,surfaceAnalystProcess);
        addBtn(remove,removeData);

        map = new SuperMap.Map("map",{controls: [
            new SuperMap.Control.LayerSwitcher(),
            new SuperMap.Control.ScaleLine(),
            new SuperMap.Control.PanZoomBar(),
            new SuperMap.Control.Navigation({
                dragPanOptions: {
                                    enableKinetic: true
                                }
            })], units: "m"
        });
        map.allOverlays = true;
        baseLayer = new SuperMap.Layer.TiledDynamicRESTLayer("全国温度变化图", DemoURL.china_Temperature_map, {transparent: true, cacheEnabled: true}, {maxResolution:"auto"});   
        baseLayer.events.on({"layerInitialized":addLayer});                
        resultLayer = new SuperMap.Layer.Vector("等值线"); 
    }

    function addLayer() {
        map.addLayers([baseLayer, resultLayer]);
        map.setCenter(new SuperMap.LonLat(531762, 3580330), 2);
    }        
    function surfaceAnalystProcess() {
        resultLayer.removeAllFeatures();
        var surfaceAnalystService = new SuperMap.REST.SurfaceAnalystService(DemoURL.spatialanalyst_sample),
            surfaceAnalystParameters = new SuperMap.REST.SurfaceAnalystParametersSetting({
                datumValue: 0,
            interval: 2,
            resampleTolerance: 0,
            smoothMethod: SuperMap.REST.SmoothMethod.BSPLINE,
            smoothness: 3,
            clipRegion: region
            }),
            params = new SuperMap.REST.DatasetSurfaceAnalystParameters({
                extractParameter: surfaceAnalystParameters,
            dataset: "SamplesP@Interpolation",
            resolution: 3000,
            zValueFieldName: "AVG_TMP"
            });
        surfaceAnalystService.events.on({"processCompleted": surfaceAnalystCompleted, "processFailed": surfaceAnalystFailed});
        surfaceAnalystService.processAsync(params);
    }

    function surfaceAnalystCompleted(args) {
        var features = args.result.recordset.features;
        for (var len=features.length,i=0;i<len;i++) {
            features[i].style.strokeColor = "#304DBE";
        }
        resultLayer.addFeatures(args.result.recordset.features);
    }

    function surfaceAnalystFailed(args) {
        doMapAlert("",args.error.errorMsg,true);
    }

    function removeData() {
        resultLayer.removeAllFeatures();
    }
    dowork();
})();
