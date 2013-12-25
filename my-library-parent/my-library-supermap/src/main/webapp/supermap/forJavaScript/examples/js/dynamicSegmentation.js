(function() {
    var map, layer, themeLayer;
    function dowork() {
        var create = '动态分段', remove = '清除';
        addBtn(create,GenerateSpatialData);
        addBtn(remove,clearThemeLayer);

        layer = new SuperMap.Layer.TiledDynamicRESTLayer("changchun", DemoURL.changChun_Map, { transparent: true, cacheEnabled: true }, { maxResolution: "auto" });
        layer.events.on({"layerInitialized":addLayer});                
        map = new SuperMap.Map("map",{allOverlays:true, controls: [
            new SuperMap.Control.LayerSwitcher(),
            new SuperMap.Control.ScaleLine(),
            new SuperMap.Control.PanZoomBar(),
            new SuperMap.Control.Navigation({
                dragPanOptions: {
                                    enableKinetic: true
                                }})]
        });
    }

    function addLayer() {
        map.addLayers([layer]);
        map.setCenter(new SuperMap.LonLat(4503.6240321526, -3861.911472192499), 1);
    }
    //生成动态分段
    function GenerateSpatialData(){
        clearThemeLayer();

        //配置数据返回Option
        var option = new SuperMap.REST.DataReturnOption({
            expectCount: 1000,
            dataset: "generateSpatialData@Changchun",
            deleteExistResultDataset: true,
            dataReturnMode: SuperMap.REST.DataReturnMode.DATASET_ONLY
        }),
            //配置动态分段Parameters
            parameters = new SuperMap.REST.GenerateSpatialDataParameters({
                routeTable: "RouteDT_road@Changchun",
                routeIDField: "RouteID",
                eventTable: "LinearEventTabDT@Changchun",
                eventRouteIDField: "RouteID",
                measureField: "",
                measureStartField: "LineMeasureFrom",
                measureEndField: "LineMeasureTo",
                measureOffsetField: "",
                errorInfoField: "",
                dataReturnOption: option
            }),
            //配置动态分段iService
            iService = new SuperMap.REST.GenerateSpatialDataService(DemoURL.changchun_spatialanalyst, {
                eventListeners: {
                                    processCompleted: generateCompleted,
            processFailed: generateFailded 
                                }
            });

        //execute
        iService.processAsync(parameters);

        //completed
        function generateCompleted(generateSpatialDataEventArgs) {
            //配置专题样式
            var style1, style2, style3;
            style1 = new SuperMap.REST.ServerStyle({
                fillForeColor: new SuperMap.REST.ServerColor(242, 48, 48),
                   lineColor: new SuperMap.REST.ServerColor(242, 48, 48),
                   lineWidth: 1
            });
            style2 = new SuperMap.REST.ServerStyle({
                fillForeColor: new SuperMap.REST.ServerColor(255, 159, 25),
                   lineColor: new SuperMap.REST.ServerColor(255, 159, 25),
                   lineWidth: 1
            });
            style3 = new SuperMap.REST.ServerStyle({
                fillForeColor: new SuperMap.REST.ServerColor(91, 195, 69),
                   lineColor: new SuperMap.REST.ServerColor(91, 195, 69),
                   lineWidth: 1
            });

            //配置专题项
            var themeUniqueIteme1, themeUniqueIteme2, themeUniqueIteme3;
            themeUniqueIteme1 = new SuperMap.REST.ThemeUniqueItem({
                unique: "拥挤",
                              style: style1
            });
            themeUniqueIteme2 = new SuperMap.REST.ThemeUniqueItem({
                unique: "缓行",
                              style: style2
            });
            themeUniqueIteme3 = new SuperMap.REST.ThemeUniqueItem({
                unique: "畅通",
                              style: style3
            });
            var themeUniqueItemes=[themeUniqueIteme1, themeUniqueIteme2, themeUniqueIteme3];

            var themeUnique = new SuperMap.REST.ThemeUnique({
                uniqueExpression: "TrafficStatus",
                items: themeUniqueItemes,
                defaultStyle: new SuperMap.REST.ServerStyle({
                    fillForeColor: new SuperMap.REST.ServerColor(48, 89, 14),
                lineColor: new SuperMap.REST.ServerColor(48, 89, 14)
                })
            });

            var themeParameters = new SuperMap.REST.ThemeParameters({
                themes: [themeUnique],
                datasetNames: ["generateSpatialData"],
                dataSourceNames: ["Changchun"]
            });

            var themeService = new SuperMap.REST.ThemeService(DemoURL.changChun_Map, {
                eventListeners:{
                                   processCompleted: themeCompleted, 
                processFailed: themeFailed
                               }
            });

            themeService.processAsync(themeParameters);

            function themeCompleted(themeEventArgs) {
                if(themeEventArgs.result.resourceInfo.id) {
                    themeLayer = new SuperMap.Layer.TiledDynamicRESTLayer("道路交通状况_专题图", DemoURL.changChun_Map, {cacheEnabled:false,transparent: true,layersID: themeEventArgs.result.resourceInfo.id}, {"maxResolution": "auto"});  
                    themeLayer.events.on({"layerInitialized": addThemeLayer});                
                }
            }
            function addThemeLayer() {
                map.addLayer(themeLayer);
            }
            function themeFailed(e) {
                doMapAlert("", e.error.errorMsg, true);
            }  
        }
        //failed
        function generateFailded(e) {
            doMapAlert("", e.error.errorMsg, true);
        }
    }      
    //移除专题图层
    function clearThemeLayer(){
        if(themeLayer) {
            map.removeLayer(themeLayer,true);
            themeLayer = null;
        }
    }
    dowork();
})();
