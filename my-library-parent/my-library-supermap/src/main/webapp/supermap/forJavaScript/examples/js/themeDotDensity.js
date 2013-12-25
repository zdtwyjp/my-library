(function() {
    var map, baseLayer, layersID, themeLayer;
    function dowork() {
        var create = '创建专题图', remove = '移除专题图';
        addBtn(create,addThemeDotDensity);
        addBtn(remove,removeTheme);
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
        baseLayer = new SuperMap.Layer.TiledDynamicRESTLayer("World", DemoURL.world, {transparent: true, cacheEnabled: true}, {maxResolution:"auto"});
        baseLayer.events.on({"layerInitialized":addLayer});
    }

    function addLayer() {
        map.addLayer(baseLayer);
        map.setCenter(new SuperMap.LonLat(0, 0), 0);
        map.allOverlays = true;            
    }        
    function addThemeDotDensity() {
        removeTheme();
        var themeService = new SuperMap.REST.ThemeService(DemoURL.world, {eventListeners:{"processCompleted": themeCompleted, "processFailed": themeFailed}}),
            dotStyle = new SuperMap.REST.ServerStyle({
                markerSize: 3,
            markerSymbolID: 12
            }),
            themeDotDensity = new SuperMap.REST.ThemeDotDensity({
                dotExpression: "Pop_1994",
            value: 5000000,
            style: dotStyle
            }),
            themeParameters = new SuperMap.REST.ThemeParameters({
                themes: [themeDotDensity],
            datasetNames: ["Countries"],
            dataSourceNames: ["World"]
            });
        themeService.processAsync(themeParameters);
    }

    function themeCompleted(themeEventArgs) {
        if(themeEventArgs.result.resourceInfo.id) {
            themeLayer = new SuperMap.Layer.TiledDynamicRESTLayer("全世界人口点密度专题图", DemoURL.world, {cacheEnabled: false, transparent: true,layersID: themeEventArgs.result.resourceInfo.id}, {"maxResolution":"auto"});
            themeLayer.events.on({"layerInitialized": addThemelayer});                 
        }
    }
    function addThemelayer() {
        map.addLayer(themeLayer);
    }
    function themeFailed(serviceFailedEventArgs) {
        doMapAlert("",serviceFailedEventArgs.error.errorMsg,true);
    }

    function removeTheme() {
        if (map.layers.length > 1) {
            map.removeLayer(themeLayer, true);
        }
    }
    dowork();
})()
