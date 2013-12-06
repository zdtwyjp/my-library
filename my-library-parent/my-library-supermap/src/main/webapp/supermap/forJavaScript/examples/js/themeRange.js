(function() {
    var map, local, baseLayer, layersID, themeLayer;
    function dowork() {
        var create = '创建专题图', remove = '移除专题图';
        addBtn(create,addThemeRange);
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
    function addThemeRange() {
        removeTheme();
        var themeService = new SuperMap.REST.ThemeService(DemoURL.china_Map, {eventListeners:{"processCompleted": themeCompleted, "processFailed": themeFailed}}),
            style1 = new SuperMap.REST.ServerStyle({
                fillForeColor: new SuperMap.REST.ServerColor(137,203,187),
            lineColor: new SuperMap.REST.ServerColor(0,0,0),
            lineWidth: 0.1
            }),
            style2 = new SuperMap.REST.ServerStyle({
                fillForeColor: new SuperMap.REST.ServerColor(233,235,171),
            lineColor: new SuperMap.REST.ServerColor(0,0,0),
            lineWidth: 0.1
            }),
            style3 = new SuperMap.REST.ServerStyle({
                fillForeColor: new SuperMap.REST.ServerColor(135,157,157),
            lineColor: new SuperMap.REST.ServerColor(0,0,0),
            lineWidth: 0.1
            }),
            themeRangeIteme1 = new SuperMap.REST.ThemeRangeItem({
                start: 0,
            end: 500000000000,
            style: style1
            }),
            themeRangeIteme2 = new SuperMap.REST.ThemeRangeItem({
                start: 500000000000,
            end: 1000000000000,
            style: style2
            }),
            themeRangeIteme3 = new SuperMap.REST.ThemeRangeItem({
                start: 1000000000000,
            end:  3000000000000,
            style: style3
            }),
            themeRange = new SuperMap.REST.ThemeRange({
                rangeExpression: "SMAREA",
            rangeMode: SuperMap.REST.RangeMode.EQUALINTERVAL,
            items: [themeRangeIteme1,themeRangeIteme2,themeRangeIteme3]
            }),
            themeParameters = new SuperMap.REST.ThemeParameters({
                datasetNames: ["China_Province_R"],
            dataSourceNames: ["China400"], 
            joinItems: null,
            themes: [themeRange]
            });

        themeService.processAsync(themeParameters);
    }

    function themeCompleted(themeEventArgs) {
        if(themeEventArgs.result.resourceInfo.id) {
            themeLayer = new SuperMap.Layer.TiledDynamicRESTLayer("中国各省面积_分段专题图", DemoURL.china_Map, {cacheEnabled:false,transparent: true,layersID: themeEventArgs.result.resourceInfo.id}, {"maxResolution":"auto"});   
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
