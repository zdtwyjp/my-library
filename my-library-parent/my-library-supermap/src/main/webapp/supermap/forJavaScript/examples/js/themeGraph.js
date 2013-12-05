﻿(function() {
    var map, local, baseLayer, layersID, themeLayer;
    function dowork() {
        var create = '创建专题图', remove = '移除专题图';
        addBtn(create,addThemeGraph);
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
        baseLayer = new SuperMap.Layer.TiledDynamicRESTLayer("京津地区人口分布图_专题图", DemoURL.jingjin_map, {transparent: true, cacheEnabled: true}, {maxResolution:"auto"});
        baseLayer.events.on({"layerInitialized":addLayer});
    }

    function addLayer() {
        map.addLayer(baseLayer);
        map.setCenter(new SuperMap.LonLat(117, 40), 2);
        map.allOverlays = true;          
    }        
    //对 Jingjin 数据源的 BaseMap_R 数据集两个字段 Pop_Rate95 和 Pop_Rate99 制作统计专题图
    function addThemeGraph() {
        removeTheme();
        //创建统计专题图对象，ThemeGraph 必设 items。
        //专题图参数 ThemeParameters 必设 theme（即以设置好的分段专题图对象）、dataSourceName 和 datasetName
        var style1 = new SuperMap.REST.ServerStyle({
            fillForeColor: new SuperMap.REST.ServerColor(92,73,234),
            lineWidth: 0.1
        }),
            style2 = new SuperMap.REST.ServerStyle({
                fillForeColor: new SuperMap.REST.ServerColor(211,111,240),
                lineWidth: 0.1
            }),
            item1 = new SuperMap.REST.ThemeGraphItem({
                caption: "1992-1995人口增长率",
                graphExpression: "Pop_Rate95",
                uniformStyle: style1
            }),
            item2 = new SuperMap.REST.ThemeGraphItem({
                caption: "1995-1999人口增长率",
                graphExpression: "Pop_Rate99",
                uniformStyle: style2
            }),
            themeGraph  = new SuperMap.REST.ThemeGraph({
                items: new Array(item1,item2),
                barWidth: 0.03,
                graduatedMode: SuperMap.REST.GraduatedMode.SQUAREROOT,
                graphAxes: new SuperMap.REST.ThemeGraphAxes({
                axesDisplayed: true
            }),
            graphSize: new SuperMap.REST.ThemeGraphSize({
                maxGraphSize: 1,
                minGraphSize: 0.35
                       }),
            graphText: new SuperMap.REST.ThemeGraphText({
                           graphTextDisplayed: true,
                           graphTextFormat: SuperMap.REST.ThemeGraphTextFormat.VALUE,
                           graphTextStyle: new SuperMap.REST.ServerTextStyle({
                               sizeFixed: true,
                               fontHeight: 9,
                               fontWidth: 5
                           })
                       }),
            graphType: SuperMap.REST.ThemeGraphType.BAR3D
            }),

            //专题图参数对象
            themeParameters = new SuperMap.REST.ThemeParameters({
                themes: [themeGraph],
                dataSourceNames: ["Jingjin"],
                datasetNames: ["BaseMap_R"]
            }),

            //与服务端交互
            themeService=new SuperMap.REST.ThemeService(DemoURL.jingjin_map, {
                eventListeners: {
                                "processCompleted": ThemeCompleted,
                                "processFailed": themeFailed
                                }
            });
        themeService.processAsync(themeParameters);
    }

    //显示专题图。专题图在服务端为一个资源，每个资源都有一个 ID 号和一个 url
    //要显示专题图即将资源结果的 ID 号赋值给图层的 layersID 属性即可
    function ThemeCompleted(themeEventArgs){
        if (themeEventArgs.result.resourceInfo.id){
            themeLayer = new SuperMap.Layer.TiledDynamicRESTLayer("京津地区人口分布图_专题图",
                   DemoURL.jingjin_map, {cacheEnabled:false,transparent: true,
                       layersID: themeEventArgs.result.resourceInfo.id}, {maxResolution:"auto"});  
            themeLayer.events.on({"layerInitialized":addThemelayer});                  

        }
    }
    function addThemelayer() {
        map.addLayer(themeLayer);          
    }

    function themeFailed(serviceFailedEventArgs) {
        doMapAlert("",serviceFailedEventArgs.error.errorMsg,true);
    }

    //移除专题图图层
    function removeTheme(){
        if (map.layers.length > 1) {
            map.removeLayer(themeLayer, true);
        }
    }
    dowork();
})();
