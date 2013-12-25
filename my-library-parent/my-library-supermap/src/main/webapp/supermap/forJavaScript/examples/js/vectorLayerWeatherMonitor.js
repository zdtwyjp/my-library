(function() {
    var map, layer, vectorLayer, features, select, tempLayer ,
        drag, delIndex = 0, editEnable = false, dataIndex = 0, timerID = null,
        weatherDatas = [], 
        //设置图层样式
        transformControl;
        style = {
            strokeColor: "#cccccc",
            strokeWidth: 1,
            fillColor: "#ccfffa",
            fillOpacity: "0.5"
        };
        style2 = {
            strokeColor: "#cccccc",
            strokeWidth: 1,
            fillColor: "#a3f8b4",
            fillOpacity: "0.5"
        };
        style1 = {
            strokeColor: "#cccccc",
            strokeWidth: 1,
            fillColor: "#79f26f",
            fillOpacity: "0.5"
        };
        style3 = {
            strokeColor: "#cccccc",
            strokeWidth: 1,
            fillColor: "#2dd90b",
            fillOpacity: "0.5"
        };
        style4 = {
            strokeColor: "#cccccc",
            strokeWidth: 1,
            fillColor: "#8aee1e",
            fillOpacity: "0.5"
        };
        style6 = {
            strokeColor: "#cccccc",
            strokeWidth: 1,
            fillColor: "#c5ef0f",
            fillOpacity: "0.5"
        };
        style5 = {
            strokeColor: "#cccccc",
            strokeWidth: 1,
            fillColor: "#fff100",
            fillOpacity: "0.5"
        };
        style7 = {
            strokeColor: "#cccccc",
            strokeWidth: 1,
            fillColor: "#fcbd10",
            fillOpacity: "0.5"
        };
        style8 = {
            strokeColor: "#cccccc",
            strokeWidth: 1,
            fillColor: "#fb8722",
            fillOpacity: "0.5"
        };
        style9 = {
            strokeColor: "#cccccc",
            strokeWidth: 1,
            fillColor: "#d53b3b",
            fillOpacity: "1"
        };
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

        var create = '开始监控', remove = '清除';
            addBtn(create,monitor);
            addBtn(remove,clearFeatures);
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
        layer = new SuperMap.Layer.TiledDynamicRESTLayer("China400", DemoURL.world, {transparent: true, cacheEnabled: true}, {maxResolution:"auto"}); 
        //初始化Vector图层
        vectorLayer = new SuperMap.Layer.Vector("Vector Layer", {renderers: ["Canvas2"]});
        layer.events.on({"layerInitialized":addLayer});
        
    }

    //添加图层
    function addLayer() {
        map.addLayers([layer,vectorLayer]);            
        map.setCenter(new SuperMap.LonLat(106, 34), 2);  
        map.addControl(new SuperMap.Control.MousePosition()) ;            
    };      


    //添加数据
    function monitor() {
        //初始化数据
        if(weatherDatas.length == 0) {
            weatherDatas[0] = SuperMap.REST.Recordset.fromJson(sampleData1);
            weatherDatas[1] = SuperMap.REST.Recordset.fromJson(sampleData2);
            weatherDatas[2] = SuperMap.REST.Recordset.fromJson(sampleData3);
            weatherDatas[3] = SuperMap.REST.Recordset.fromJson(sampleData4);
            weatherDatas[4] = SuperMap.REST.Recordset.fromJson(sampleData5);
            weatherDatas[5] = SuperMap.REST.Recordset.fromJson(sampleData6);
            weatherDatas[6] = SuperMap.REST.Recordset.fromJson(sampleData7);
            weatherDatas[7] = SuperMap.REST.Recordset.fromJson(sampleData8);
        }
        vectorLayer.removeAllFeatures();
        var i, len, feature, features = [],result;

        if(dataIndex > 7){
            dataIndex = 0;
        }
        result = weatherDatas[dataIndex];
        dataIndex++;

        if (result && result.features) {
            var len = result.features.length;
            for (i = 0; i < len; i++) {
                feature = result.features[i];
                var data = feature.attributes;
                var value = data['DMAXVALUE'];
                    if( value < -24){
                        feature.style = style;
                    }
                    else if( value < -18){
                        feature.style = style1;
                    }
                    else if( value < -12){
                        feature.style = style2;
                    }
                    else if( value < -6){
                        feature.style = style3;
                    }
                    else if( value < 0){
                        feature.style = style4;
                    }
                    else if( value < 6){
                        feature.style = style5;
                    }
                    else if( value < 12){
                        feature.style = style6;
                    }
                    else if( value < 18){
                        feature.style = style7;
                    }
                    else if( value < 24){
                        feature.style = style8;
                    }
                    else {
                        features.style = style9;
                    }
                features.push(feature);
            }
        }
        vectorLayer.addFeatures(features);
        if(timerID){
            window.clearTimeout(timerID);
            timerID = null;
        }
        timerID = window.setTimeout(monitor,1000);
    };

    //清除全部要素
    function clearFeatures() {
        if(timerID){
            window.clearTimeout(timerID);
            timerID = null;
        }
        vectorLayer.removeAllFeatures();
    };
    dowork();
})();
