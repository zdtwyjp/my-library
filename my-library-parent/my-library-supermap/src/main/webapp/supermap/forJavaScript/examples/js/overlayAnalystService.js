(function() {
    var map, local, baseLayer, resultLayer, Spatialanalyst_sample,
        style = {
            strokeColor: "#343434",
            strokeWidth: 0.1,
            pointerEvents: "visiblePainted",
            fill: true,
            fillColor: "#304DBE",
            fillOpacity: 0.8
        };
    function dowork() {
        var create = '叠加分析', remove = '移除结果';
        addBtn(create,overlayAnalystProcess);
        addBtn(remove,removeResult);

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
        baseLayer = new SuperMap.Layer.TiledDynamicRESTLayer("京津地区人口分布图_专题图", DemoURL.jingjin_map, {transparent: true, cacheEnabled: true}, {maxResolution:"auto"});  
        baseLayer.events.on({"layerInitialized": addLayer});                
        resultLayer = new SuperMap.Layer.Vector("叠加结果");
    }

    function addLayer() {
        map.addLayers([baseLayer, resultLayer]);
        map.setCenter(new SuperMap.LonLat(117, 40), 1);
        map.allOverlays = true;
    }                
    function overlayAnalystProcess() {
        resultLayer.removeAllFeatures();
        var overlayServiceByDatasets = new SuperMap.REST.OverlayAnalystService(DemoURL.spatialanalyst_sample),
            dsOverlayAnalystParameters = new SuperMap.REST.DatasetOverlayAnalystParameters({
                sourceDataset: "BaseMap_R@Jingjin",
            operateDataset: "Neighbor_R@Jingjin",
            tolerance: 0,
            operation: SuperMap.REST.OverlayOperationType.UNION
            });
        overlayServiceByDatasets.events.on({"processCompleted": overlayAnalystCompleted, "processFailed": overlayAnalystFailed});
        overlayServiceByDatasets.processAsync(dsOverlayAnalystParameters);
    }

    function overlayAnalystCompleted(args) {
        var feature, features = [];
        for(var i=0;i<args.result.recordset.features.length;i++){
            feature = args.result.recordset.features[i];
            feature.style = style;
            features.push(feature);
        }
        resultLayer.addFeatures(features);
    }

    function overlayAnalystFailed(args) {
        doMapAlert("",args.error.errorMsg,true);
    }

    function removeResult() {
        resultLayer.removeAllFeatures();
    }

    dowork();
})();
