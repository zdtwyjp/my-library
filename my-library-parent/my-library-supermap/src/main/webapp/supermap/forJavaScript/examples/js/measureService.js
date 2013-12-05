﻿(function() {
    var map, local, layer, drawLine, drawPolygon, lineLayer, polygonLayer;
    style = {
        strokeColor: "#304DBE",
        strokeWidth: 2,
        pointerEvents: "visiblePainted",
        fillColor: "#304DBE",
        fillOpacity: 0.8
    };
    function dowork() {
        var create = '距离量算', create1 = '面积量算', remove = '清除';
        addBtn(create,distanceMeasure);
        addBtn(create1,areaMeasure);
        addBtn(remove,clearFeatures);

        //新建线矢量图层
        lineLayer = new SuperMap.Layer.Vector("lineLayer");
        //对线图层应用样式style（前面有定义）
        lineLayer.style = style;
        //新建面矢量图层
        polygonLayer = new SuperMap.Layer.Vector("polygonLayer");
        //对面图层应用样式style（前面有定义）
        polygonLayer.style = style;

        //创建画线控制，图层是lineLayer;这里DrawFeature(图层,类型,属性)；multi:true在将要素放入图层之前是否现将其放入几何图层中
        drawLine = new SuperMap.Control.DrawFeature(lineLayer, SuperMap.Handler.Path, { multi: true });

        /*
           注册featureadded事件,触发drawCompleted()方法
           例如注册"loadstart"事件的单独监听
           events.on({ "loadstart": loadStartListener });
           */
        drawLine.events.on({"featureadded": drawCompleted});

        //创建画面控制，图层是polygonLayer
        drawPolygon = new SuperMap.Control.DrawFeature(polygonLayer, SuperMap.Handler.Polygon);  
        drawPolygon.events.on({"featureadded": drawCompleted});
        //定义layer图层，TiledDynamicRESTLayer：分块动态 REST 图层
        layer = new SuperMap.Layer.TiledDynamicRESTLayer("World", DemoURL.world, { transparent: true, cacheEnabled: true }, { maxResolution: "auto" });
        //为图层初始化完毕添加addLayer()事件
        layer.events.on({"layerInitialized":addLayer});                
        map = new SuperMap.Map("map",{controls: [
            new SuperMap.Control.LayerSwitcher(),
            new SuperMap.Control.ScaleLine(),
            new SuperMap.Control.PanZoomBar(),
            new SuperMap.Control.Navigation({
                dragPanOptions: {
                                    enableKinetic: true
                                }}),
            drawLine, drawPolygon]
        });
    }

    function addLayer() {
        map.addLayers([layer, lineLayer, polygonLayer]);
        map.setCenter(new SuperMap.LonLat(0, 0), 0);
    }
    function distanceMeasure(){
        clearFeatures();
        drawLine.activate();
    }

    function areaMeasure(){
        clearFeatures();
        drawPolygon.activate();
    }

    //绘完触发事件
    function drawCompleted(drawGeometryArgs) {
        //停止画线画面控制
        drawLine.deactivate();
        drawPolygon.deactivate();
        //获得图层几何对象
        var geometry = drawGeometryArgs.feature.geometry,
            measureParam = new SuperMap.REST.MeasureParameters(geometry), /* MeasureParameters：量算参数类。 客户端要量算的地物间的距离或某个区域的面积*/
            myMeasuerService = new SuperMap.REST.MeasureService(DemoURL.world); //量算服务类，该类负责将量算参数传递到服务端，并获取服务端返回的量算结果
        myMeasuerService.events.on({ "processCompleted": measureCompleted });

        //对MeasureService类型进行判断和赋值，当判断出是LineString时设置MeasureMode.DISTANCE，否则是MeasureMode.AREA
        if (geometry.CLASS_NAME.indexOf("LineString") > -1) {
            myMeasuerService.measureMode = SuperMap.REST.MeasureMode.DISTANCE;
        } else {
            myMeasuerService.measureMode = SuperMap.REST.MeasureMode.AREA;
        }
        myMeasuerService.processAsync(measureParam); //processAsync负责将客户端的量算参数传递到服务端。
    }

    //测量结束调用事件
    function measureCompleted(measureEventArgs) {
        var distance = measureEventArgs.result.distance,
            area = measureEventArgs.result.area,
            unit = measureEventArgs.result.unit;
        if (distance != -1) {
            doMapAlert("量算结果", distance + "米");
        } else if (area != -1) {
            doMapAlert("量算结果", area + "平方米");
        }
    }

    //移除图层要素
    function clearFeatures(){
        lineLayer.removeAllFeatures();
        polygonLayer.removeAllFeatures();
    }
    dowork();
})();
