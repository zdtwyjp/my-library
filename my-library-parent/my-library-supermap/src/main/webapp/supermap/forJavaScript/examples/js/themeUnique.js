﻿(function() {
    var map, local, baseLayer, layersID, themeLayer;
    function dowork() {
        var create = '创建专题图', remove = '移除专题图';
        addBtn(create,addThemeUnique);
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
        baseLayer = new SuperMap.Layer.TiledDynamicRESTLayer("China400", DemoURL.china_Map, {transparent: true, cacheEnabled: true}, {maxResolution: "auto"});             
        baseLayer.events.on({"layerInitialized": addLayer});
    }

    function　addLayer() {
        map.addLayer(baseLayer);
        map.setCenter(new SuperMap.LonLat(12080677, 4591416), 3);
        map.allOverlays = true;
    }
    function addThemeUnique() {
        removeTheme();
        var themeService = new SuperMap.REST.ThemeService(DemoURL.china_Map, {eventListeners:{"processCompleted": themeCompleted, "processFailed": themeFailed}});
        var style1, style2, style3, style4, style5, style6;
        style1 = new SuperMap.REST.ServerStyle({
            fillForeColor: new SuperMap.REST.ServerColor(248, 203, 249),
		    lineColor: new SuperMap.REST.ServerColor(0, 0, 0),
		    lineWidth: 0.1
        });
        style2 = new SuperMap.REST.ServerStyle({
            fillForeColor: new SuperMap.REST.ServerColor(196, 255, 189),
            lineColor: new SuperMap.REST.ServerColor(0, 0, 0),
            lineWidth: 0.1
        });
        style3 = new SuperMap.REST.ServerStyle({
            fillForeColor: new SuperMap.REST.ServerColor(255, 173, 173),
            lineColor: new SuperMap.REST.ServerColor(0, 0, 0),
            lineWidth: 0.1
        });
        style4 = new SuperMap.REST.ServerStyle({
            fillForeColor: new SuperMap.REST.ServerColor(255, 239, 168),
            lineColor: new SuperMap.REST.ServerColor(0, 0, 0),
            lineWidth: 0.1
        });
        style5 = new SuperMap.REST.ServerStyle({
            fillForeColor: new SuperMap.REST.ServerColor(173, 209, 255),
            lineColor: new SuperMap.REST.ServerColor(0, 0, 0),
            lineWidth: 0.1
        });
        style6 = new SuperMap.REST.ServerStyle({
            fillForeColor: new SuperMap.REST.ServerColor(132, 164, 232),
            lineColor: new SuperMap.REST.ServerColor(0, 0, 0),
            lineWidth: 0.1
        });


        var themeUniqueIteme1 = new SuperMap.REST.ThemeUniqueItem({
            unique: "黑龙江省",
            style: style1
        }),
            themeUniqueIteme2 = new SuperMap.REST.ThemeUniqueItem({
                unique: "湖北省",
				style: style2
            }),
            themeUniqueIteme3 = new SuperMap.REST.ThemeUniqueItem({
                unique: "吉林省",
				style: style3
            }),
            themeUniqueIteme4 = new SuperMap.REST.ThemeUniqueItem({
                unique: "内蒙古自治区",
				style: style4
            }),
            themeUniqueIteme5 = new SuperMap.REST.ThemeUniqueItem({
                unique: "青海省",
				style: style5
            }),
            themeUniqueIteme6 = new SuperMap.REST.ThemeUniqueItem({
                unique: "新疆维吾尔自治区",
				style: style6
            }),
            themeUniqueIteme7 = new SuperMap.REST.ThemeUniqueItem({
                unique: "云南省",
				style: style1
            }),
            themeUniqueIteme8 = new SuperMap.REST.ThemeUniqueItem({
                unique: "四川省",
				style: style4
            }),
            themeUniqueIteme9 = new SuperMap.REST.ThemeUniqueItem({
                unique: "贵州省",
				style: style3
            }),
            themeUniqueIteme10 = new SuperMap.REST.ThemeUniqueItem({
                unique: "甘肃省",
            style: style3
            }),
            themeUniqueIteme11 = new SuperMap.REST.ThemeUniqueItem({
                unique: "宁夏回族自治区",
				style: style5
            }),
            themeUniqueIteme12 = new SuperMap.REST.ThemeUniqueItem({
                unique: "重庆市",
				style: style6
            }),
            themeUniqueIteme13 = new SuperMap.REST.ThemeUniqueItem({
                unique: "山东省",
				style: style1
            }),
            themeUniqueIteme14 = new SuperMap.REST.ThemeUniqueItem({
                unique: "安徽省",
				style: style2
            }),
            themeUniqueIteme15 = new SuperMap.REST.ThemeUniqueItem({
                unique: "江西省",
				style: style3
            }),
            themeUniqueIteme16 = new SuperMap.REST.ThemeUniqueItem({
                unique: "浙江省",
				style: style4
            }),
            themeUniqueIteme17 = new SuperMap.REST.ThemeUniqueItem({
                unique: "台湾省",
				style: style2
            }),
            themeUniqueIteme18 = new SuperMap.REST.ThemeUniqueItem({
                unique: "江苏省",
				style: style6
            }),
            themeUniqueIteme19 = new SuperMap.REST.ThemeUniqueItem({
                unique: "湖南省",
				style: style5
            }),
            themeUniqueIteme20 = new SuperMap.REST.ThemeUniqueItem({
                unique: "河南省",
				style: style4
            }),
            themeUniqueIteme21 = new SuperMap.REST.ThemeUniqueItem({
                unique: "河北省",
				style: style3
            }),
            themeUniqueIteme22 = new SuperMap.REST.ThemeUniqueItem({
                unique: "福建省",
				style: style5
            }),
            themeUniqueIteme23 = new SuperMap.REST.ThemeUniqueItem({
                unique: "广西壮族自治区",
				style: style6
            }),
            themeUniqueIteme24 = new SuperMap.REST.ThemeUniqueItem({
                unique: "西藏自治区",
				style: style2
            }),
            themeUniqueIteme25 = new SuperMap.REST.ThemeUniqueItem({
                unique: "广东省",
				style: style4
            }),
            themeUniqueIteme26 = new SuperMap.REST.ThemeUniqueItem({
                unique: "山西省",
				style: style2
            }),
            themeUniqueIteme27 = new SuperMap.REST.ThemeUniqueItem({
                unique: "陕西省",
				style: style1
            }),
            themeUniqueIteme28 = new SuperMap.REST.ThemeUniqueItem({
                unique: "天津市",
				style: style5
            }),
            themeUniqueIteme29 = new SuperMap.REST.ThemeUniqueItem({
                unique: "北京市",
				style: style2
            }),

            themeUniqueIteme30 = new SuperMap.REST.ThemeUniqueItem({
                unique: "辽宁省",
				style: style1
            });

        var themeUniqueItemes=[themeUniqueIteme1, themeUniqueIteme2, themeUniqueIteme3, themeUniqueIteme4, themeUniqueIteme5, themeUniqueIteme6,themeUniqueIteme7,themeUniqueIteme8,themeUniqueIteme9,themeUniqueIteme10,themeUniqueIteme11,themeUniqueIteme12, themeUniqueIteme13,themeUniqueIteme14,themeUniqueIteme15,themeUniqueIteme16,themeUniqueIteme17,themeUniqueIteme18,themeUniqueIteme19,themeUniqueIteme20,themeUniqueIteme21,themeUniqueIteme22,themeUniqueIteme23,themeUniqueIteme24,themeUniqueIteme25,themeUniqueIteme26,themeUniqueIteme27,themeUniqueIteme28,themeUniqueIteme29,themeUniqueIteme30];

        var themeUnique = new SuperMap.REST.ThemeUnique({
            uniqueExpression: "Name",
            items: themeUniqueItemes,
            defaultStyle: new SuperMap.REST.ServerStyle({
                fillForeColor: new SuperMap.REST.ServerColor(48, 89, 14),
                ineColor: new SuperMap.REST.ServerColor(48, 89, 14)
            })
        });
        themeParameters = new SuperMap.REST.ThemeParameters({
            datasetNames: ["China_Province_R"],
            dataSourceNames: ["China400"], 
            themes: [themeUnique]
        });

        themeService.processAsync(themeParameters);
    }

    function themeCompleted(themeEventArgs) {
        if(themeEventArgs.result.resourceInfo.id) {
            themeLayer = new SuperMap.Layer.TiledDynamicRESTLayer("中国行政区划_专题图", DemoURL.china_Map, {cacheEnabled:false,transparent: true,layersID: themeEventArgs.result.resourceInfo.id}, {"maxResolution": "auto"});  
            themeLayer.events.on({"layerInitialized": addThemeLayer});                
        }
    }
    function addThemeLayer() {
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
