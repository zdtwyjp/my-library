(function() {
    var map, local, layer, vectorLayer, features, select, tempLayer , drag, delIndex = 0, editEnable = false,
        //设置图层样式
        style = {
            externalGraphic: "./images/marker.png",
            graphicWidth: 13,
            graphicHeight: 16,
            name: "town"
        },
        styleCity = {
            pointRadius: 10,
            externalGraphic: "./images/markerbig.png",
            name: "city"
        },
        styleCaptial = {
            pointRadius: 15,
            externalGraphic: "./images/markerflag.png",
            name: "captial"
        },transformControl;
    function dowork() {
        /*
         * 不支持canvas的浏览器不能运行该范例
         * android 设备也不能运行该范例*/
        var broz = SuperMap.Util.getBrowser();

        if(!document.createElement('canvas').getContext) {
            alert('您的浏览器不支持 canvas，请升级');
            return;
        } else if (broz.device === 'android') {
            alert('您的设备不支持高性能渲染，请使用pc或其他设备');
            return;
        }
        var create = '查询', create1 = '清除全部', create2 = '清除100个', create3 = '清除选择', create4 = '启动编辑',create5='停止编辑';
        addBtn(create,queryBySQL);
        addBtn(create1,clearFeatures);
        addBtn(create2,clearFewFeatures);
        addBtn(create3,clearSelectedFeatures);
        addBtn(create4,openEdit);
        addBtn(create5,closeEdit);

        //加载map控件
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
        //初始化图层
        layer = new SuperMap.Layer.TiledDynamicRESTLayer("China400", DemoURL.china_Map, {transparent: true, cacheEnabled: true}, {maxResolution:"auto"}); 
        layer.events.on({"layerInitialized":addLayer});    
        //初始化Vector图层
        vectorLayer = new SuperMap.Layer.Vector("Vector Layer", {renderers: ["Canvas2"]});
        //给在vector图层上所选择的要素初始化
        select = new SuperMap.Control.SelectFeature(vectorLayer, {onSelect: onFeatureSelect, onUnselect: onFeatureUnselect});
        map.addControl(select);
    }

    //要素被选中时调用此函数
    function onFeatureSelect(feature) {
        if(editEnable) {
            editSelectedFeatures();
        } else {
            selectedFeature = feature;
			var contentHTML = "<div style='font-size:.8em; opacity: 0.8; overflow-y:hidden;'>" + 
                              "<span style='font-weight: bold; font-size: 18px;'>详细信息</span><br>";
			if(feature.attributes["ADMINNAME"]){
				contentHTML += "ADMINNAME：" + feature.attributes["ADMINNAME"] + "<br>";
			}else{
				contentHTML += "NAME：" + feature.attributes["NAME"] + "<br>";
			}
			contentHTML += "SMID："  + feature.attributes["SMID"] + "</div>";
            //初始化一个弹出窗口，当某个地图要素被选中时会弹出此窗口，用来显示选中地图要素的属性信息
            popup = new SuperMap.Popup.FramedCloud("chicken", 
                                        feature.geometry.getBounds().getCenterLonLat(),
                                        null,
                                        contentHTML,
                                        null,
                                        true);
            feature.popup = popup;
            map.addPopup(popup);
        }
    }

    //编辑选择的要素
    function editSelectedFeatures() {
        if(vectorLayer.selectedFeatures.length == 1) {
            var feature = vectorLayer.selectedFeatures[0];
            //先删除popup。
            if(feature.popup) {
                map.removePopup(feature.popup);
                feature.popup.destroy();
                feature.popup = null;
            }
            //还原已经在编辑状态的feature
            if(!tempLayer){
                return;
            }
            if(tempLayer.features){
                var tempFeature;
                for(var id in tempLayer.features) {
                    tempFeature  = tempLayer.features[id];
                    if(tempFeature.geometry){
                        resaveFeature(vectorLayer,tempFeature);
                    }
                }
                tempLayer.removeAllFeatures();
            }

            //显示feature
            var cloneFeature = feature.clone();
            switch (cloneFeature.style.name) {
                case "town":
                    cloneFeature.style = {
                        externalGraphic: "./images/marker_select.png",
                        graphicWidth: 13,
                        graphicHeight: 16,
                        name: "town"
                    }
                    break;
                case "city":
                    cloneFeature.style = {
                        pointRadius: 10,
                        externalGraphic: "./images/markerbig_select.png",
                        name: "city"
                    };
                    break;
                case "captial":
                    cloneFeature.style = {
                        pointRadius: 15,
                        externalGraphic: "./images/markerflag_select.png",
                        name: "captial"
                    };
                    break;
            }
            tempLayer.addFeatures(cloneFeature);
            //删除以前的feature
            vectorLayer.removeFeatures(feature);
        }
    }

    function editFeatureActive() {
        if(editEnable){
            if(tempLayer){
                return;
            }
            tempLayer = new SuperMap.Layer.Vector("tempEdit", {renderers: ["SVG"]});
            map.addLayer(tempLayer);

            drag = new SuperMap.Control.DragFeature(tempLayer);
            map.addControl(drag);
            drag.activate();

            drag.onComplete = function(feature, pixel) {
                //重新将feature绘制到高性能矢量图层上
                resaveFeature(vectorLayer,feature);
                drag.outFeature(feature);
                tempLayer.removeFeatures(feature);
                feature.destroy();
            }
        }else{
            if(!tempLayer){
                return;
            }
            if(tempLayer.features){
                var tempFeature;
                for(var id in tempLayer.features) {
                    tempFeature  = tempLayer.features[id];
                    if(tempFeature.geometry){
                        resaveFeature(vectorLayer,tempFeature);
                    }
                }
                tempLayer.removeAllFeatures();
            }
            drag.deactivate();
            drag = null;
            map.removeLayer(tempLayer,true);
            tempLayer.destroy();
            tempLayer = null;
        }
    }

    function resaveFeature(layer,feature){
        var cloneFeature = feature.clone();
        switch (cloneFeature.style.name) {
            case "town":
                cloneFeature.style = style;
                break;
            case "city":
                cloneFeature.style = styleCity;
                break;
            case "captial":
                cloneFeature.style = styleCaptial;
                break;
        }
        layer.addFeatures(cloneFeature);
    }

    //关闭弹出窗口
    function onPopupClose(evt) {
    }
    //清除要素时调用此函数
    function onFeatureUnselect(feature) {
        map.removePopup(feature.popup);
        feature.popup.destroy();
        feature.popup = null;
    }

    //添加图层
    function addLayer() {
        map.addLayers([layer,vectorLayer]);            
        map.setCenter(new SuperMap.LonLat(13008120, 4327701), 3);    
        map.addControl(new SuperMap.Control.MousePosition()) ;            
    }        

    //SQL查询
    function queryBySQL() {
        vectorLayer.removeAllFeatures();

        // 查询中国的部分县。
        var queryParam, queryBySQLParams, queryBySQLService;
        // 初始化查询参数
        queryParam = new SuperMap.REST.FilterParameter({
            name: "China_Town_P@China400",
                   attributeFilter: "SmID < 10000"
        }),
        // 初始化sql查询参数
       queryBySQLParams = new SuperMap.REST.QueryBySQLParameters({
           queryParams: [queryParam]
       }),
       // SQL查询服务
       queryBySQLService = new SuperMap.REST.QueryBySQLService(DemoURL.china_Map, {
           eventListeners: {"processCompleted": processCompleted, "processFailed": processFailed}});
       queryBySQLService.processAsync(queryBySQLParams);

        // 查询中国的全部市。
        var queryParamCity, queryBySQLParamsCity, queryBySQLServiceCity;
        // 初始化查询参数
        queryParamCity = new SuperMap.REST.FilterParameter({
                        name: "China_PreCenCity_P@China400",
                        attributeFilter: "SmID > 0"
        }),
       // 初始化sql查询参数
        queryBySQLParamsCity = new SuperMap.REST.QueryBySQLParameters({
            queryParams: [queryParamCity]
        }),
       // SQL查询服务
        queryBySQLServiceCity = new SuperMap.REST.QueryBySQLService(DemoURL.china_Map, {
           eventListeners: {"processCompleted": processCompletedCity, "processFailed": processFailedCity}});
        queryBySQLServiceCity.processAsync(queryBySQLParamsCity);

        //查询中国的全部省会。
        var queryParamCapital, queryBySQLParamsCapital, queryBySQLServiceCapital;
        //初始化查询参数
        queryParamCapital = new SuperMap.REST.FilterParameter({
            name: "China_Capital_P@China400",
                          attributeFilter: "SmID > 0"
        }),
        //初始化sql查询参数
        queryBySQLParamsCapital = new SuperMap.REST.QueryBySQLParameters({
            queryParams: [queryParamCapital]
        }),
        //SQL查询服务
        queryBySQLServiceCapital = new SuperMap.REST.QueryBySQLService(DemoURL.china_Map, {
          eventListeners: {"processCompleted": processCompletedCapital, "processFailed": processFailedCapital}});
        queryBySQLServiceCapital.processAsync(queryBySQLParamsCapital);
    }

    //SQL查询(县)成功时触发此事件
    function processCompleted(queryEventArgs) {
        var i, j, feature, 
            result = queryEventArgs.result;
        features = [];
        if (result && result.recordsets) {
            for (i=0; i<result.recordsets.length; i++) {
                if (result.recordsets[i].features) {
                    for (j=0; j<result.recordsets[i].features.length; j++) {
                        feature = result.recordsets[i].features[j];
                        feature.style = style;
                        features.push(feature);
                    }
                }
            }
        }

        vectorLayer.addFeatures(features);
        select.activate();
    }
    //SQL查询(县)失败时出发的事件
    function processFailed(e) {
        doMapAlert("",e.error.errorMsg,true);
    }

    //SQL查询(城市)成功时触发此事件
    function processCompletedCity(queryEventArgs) {
        var i, j, feature, 
            result = queryEventArgs.result;
        features = [];
        if (result && result.recordsets) {
            for (i=0; i<result.recordsets.length; i++) {
                if (result.recordsets[i].features) {
                    for (j=0; j<result.recordsets[i].features.length; j++) {
                        feature = result.recordsets[i].features[j];
                        feature.style = styleCity;
                        features.push(feature);
                    }
                }
            }
        }
        vectorLayer.addFeatures(features);
        select.activate();
    }

    //SQL查询(城市)失败时出发的事件
    function processFailedCity(e) {
        doMapAlert("",e.error.errorMsg,true);
    }

    //SQL查询(省会)成功时触发此事件
    function processCompletedCapital(queryEventArgs) {
        var i, j, feature, 
            result = queryEventArgs.result;
        features = [];
        if (result && result.recordsets) {
            for (i=0; i<result.recordsets.length; i++) {
                if (result.recordsets[i].features) {
                    for (j=0; j<result.recordsets[i].features.length; j++) {
                        feature = result.recordsets[i].features[j];
                        feature.style = styleCaptial;
                        features.push(feature);
                    }
                }
            }
        }
        vectorLayer.addFeatures(features);
        select.activate();
    }

    //SQL查询(省会)失败时出发的事件
    function processFailedCapital(e) {
        doMapAlert("",e.error.errorMsg,true);
    }

    //清除全部要素
    function clearFeatures() {
        if(vectorLayer.selectedFeatures.length > 0) {
            map.removePopup(vectorLayer.selectedFeatures[0].popup);
        }
        vectorLayer.removeAllFeatures();
    }

    //清除选择的要素
    function clearSelectedFeatures() {
        if(vectorLayer.selectedFeatures.length > 0) {
            var selectFeatures = vectorLayer.selectedFeatures;
            for (var i = 0; i < selectFeatures.length; i ++) {
                var feature = selectFeatures[i];
                map.removePopup(feature.popup);
                feature.popup.destroy();
                feature.popup = null;
            }
            vectorLayer.removeFeatures(vectorLayer.selectedFeatures);
        }
    }

    //清除指定数目的要素
    function clearFewFeatures() {
        var delFeatures = [];
        for(var i = delIndex; i < delIndex + 1000; i++) {
            if(features[i]){
                delFeatures.push(features[i]);
            }
        }
        vectorLayer.removeFeatures(delFeatures);
        delIndex += 1000;
    }

    //开关编辑功能。
    function openEdit() {
        if(!editEnable) {
            editEnable = true;
            editFeatureActive();
            editSelectedFeatures();
        }
    }

    function closeEdit(){
        if(editEnable){
            editEnable = false;
            editFeatureActive();
        }
    }
    dowork();
})();
