(function() {
    var map, local, baseLayer, layersID, themeLayer;
    function dowork() {
        var create = '创建专题图', remove = '移除专题图';
        addBtn(create,addThemeGraduatedSymbol);
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
        baseLayer = new SuperMap.Layer.TiledDynamicRESTLayer("China400", DemoURL.china_Map, {transparent: true, cacheEnabled: true}, {maxResolution:"auto"});
        baseLayer.events.on({"layerInitialized":addLayer});
    }

    function addLayer() {
        map.addLayer(baseLayer);
        map.setCenter(new SuperMap.LonLat(12080677, 4591416), 3); 
        map.allOverlays = true;
    }
    function addThemeGraduatedSymbol() {
        removeTheme();
        var themeService = new SuperMap.REST.ThemeService(DemoURL.china_Map, 
                {eventListeners:{"processCompleted": themeCompleted, "processFailed": themeFailed}}),
				graStyle = new SuperMap.REST.ThemeGraduatedSymbolStyle({
                positiveStyle: new SuperMap.REST.ServerStyle({
				   markerSize: 50,
				   markerSymbolID: 0,
				   lineColor: new SuperMap.REST.ServerColor(255,165,0),
				   fillBackColor: new SuperMap.REST.ServerColor(255,0,0)
			    })
            }),
            themeGraduatedSymbol = new SuperMap.REST.ThemeGraduatedSymbol({
                expression: "SMAREA",
                baseValue: 3000000000000,
                graduatedMode: SuperMap.REST.GraduatedMode.CONSTANT,
                flow: new SuperMap.REST.ThemeFlow({
                    flowEnabled: true
                }),
                style: graStyle
            }),
            themeParameters = new SuperMap.REST.ThemeParameters({
                themes: [themeGraduatedSymbol],
                datasetNames: ["China_Province_R"],
                dataSourceNames: ["China400"]        
            });

        themeService.processAsync(themeParameters);
    }

    function themeCompleted(themeEventArgs) {
        if(themeEventArgs.result.resourceInfo.id) {
            themeLayer = new SuperMap.Layer.TiledDynamicRESTLayer("中国各省面积等级符号专题图",
                   DemoURL.china_Map, 
                   {cacheEnabled: false, transparent: true,
                       layersID: themeEventArgs.result.resourceInfo.id},
                       {"maxResolution":"auto"});
            themeLayer.events.on({"layerInitialized":addThemelayer});                
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
})();
