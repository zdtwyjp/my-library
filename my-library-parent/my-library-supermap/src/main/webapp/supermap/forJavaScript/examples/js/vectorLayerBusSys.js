(function() {
    var car1 = new Image();
    car1.src = "images/car1.png";
    var car2 = new Image();
    car2.src = "images/car2.png";
    var car3 = new Image();
    car3.src = "images/car3.png";

    var map, layer, vectorLayer, features, cars, vectorLayerCars, steps = 10, redraw = false, select, selectedBus, timerid,
    //定义公交线路的样式。
    styleLine = {
        strokeColor: "black",
        strokeWidth: 1,
        fill: false
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
        var create = '查询车辆', create1 = '开始监控', remove = '停止监控';
        addBtn(create,queryBySQL);
        addBtn(create1,monitor);
        addBtn(remove,stopMonitor);

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

        layer = new SuperMap.Layer.TiledDynamicRESTLayer("changchun", DemoURL.changChun_Map, {transparent: true, cacheEnabled: true}, {maxResolution:"auto"}); 
        layer.events.on({"layerInitialized":addLayer}); 
        //初始化公交车路线图层。
        vectorLayer = new SuperMap.Layer.Vector("Vector Layer", {renderers: ["Canvas"],
            styleMap: new SuperMap.StyleMap({
                "default": styleLine})});
        //初始化公交车图层。
        vectorLayerCars = new SuperMap.Layer.Vector("Vector Layer Cars", {renderers: ["Canvas2"]});
        //为 vectorLayerCars 添加一个选择控件。
        select = new SuperMap.Control.SelectFeature(vectorLayerCars, {onSelect: onFeatureSelect, onUnselect: onFeatureUnselect});
        map.addControl(select);
    }

    //要素被选中时调用此函数
    function onFeatureSelect(feature) {
        selectedBus = feature;
        selectedBus.geometry.calculateBounds();
        //初始化一个弹出窗口，当某个地图要素被选中时会弹出此窗口，用来显示选中地图要素的属性信息
        popup = new SuperMap.Popup("chicken", 
                selectedBus.geometry.getBounds().getCenterLonLat(),
                new SuperMap.Size(300,60),
                "<div style='font-size:.8em; opacity: 0.8'><p>公交线路：" + selectedBus.line.num + "路" + 
                "</p><p>当前位置：" + selectedBus.geometry.x + "，" + selectedBus.geometry.y + "</p></div>",
                false);
        selectedBus.popup = popup;
        map.addPopup(popup);
    }

    //清除要素时调用此函数
    function onFeatureUnselect(feature) {
        map.removePopup(feature.popup);
        feature.popup.destroy();
        feature.popup = null;
        selectedBus = null;
    }

    function addLayer() {
        map.addLayers([layer, vectorLayer, vectorLayerCars]);            
        map.setCenter(new SuperMap.LonLat(5935, -3580), 2);    
        map.addControl(new SuperMap.Control.MousePosition()) ;            
        vectorLayerCars.events.register("featuresadded", vectorLayerCars, addFeaturesCompelte);
        select.activate();
    }        

    //定义查询公交线路。
    function queryBySQL() {
        //移除选择项
        if(selectedBus) {
            map.removePopup(selectedBus.popup);
            selectedBus.popup.destroy();
            selectedBus.popup = null;
        }
        selectedBus = null;
        vectorLayerCars.removeAllFeatures();
        vectorLayer.removeAllFeatures();
        window.clearTimeout(timerid);
        var queryParam, queryBySQLParams, queryBySQLService;
        queryParam = new SuperMap.REST.FilterParameter({
            name: "BusLine@Changchun#1",
                   attributeFilter: "SmID > 0"
        }),
                   queryBySQLParams = new SuperMap.REST.QueryBySQLParameters({
                       queryParams: [queryParam]
                   }),
                   queryBySQLService = new SuperMap.REST.QueryBySQLService(DemoURL.changChun_Map, {
                       eventListeners: {"processCompleted": processCompleted, "processFailed": processFailed}});
        queryBySQLService.processAsync(queryBySQLParams);
    }

    function processCompleted(queryEventArgs) {
        var i, j, feature,
            result = queryEventArgs.result;
        features = [];
        cars = [];
        var orientation = 1;
        if (result && result.recordsets) {
            for (i=0; i<result.recordsets.length; i++) {
                if (result.recordsets[i].features) {
                    for (j=0, len = result.recordsets[i].features.length; j < len; j++) {
                        feature = result.recordsets[i].features[j];
                        feature.style = null;
                        feature.num = j;
                        features.push(feature);
                        var featureComps = feature.geometry.components;
                        for(var k = 0, len = featureComps.length; k < len; k++) {
                            var car = new SuperMap.Feature.Vector(featureComps[k].clone());
                            //定义bus的style。
                            car.style = {pointRadius: 10, stroke: false};
                            var cargeometry = car.geometry;
                            //模拟bus的基本信息。
                            car.line = feature;
                            car.orientation = orientation; 
                            car.currentIndex = k;
                            if(featureComps[car.currentIndex + car.orientation]) {
                                car.nextPoint = featureComps[k + car.orientation];                                
                                var dx = car.nextPoint.x - cargeometry.x;
                                var dy = car.nextPoint.y - cargeometry.y;
                                var distance = Math.sqrt(dx * dx + dy * dy);
                                car.speed = parseInt(Math.random() * 5 + 2) * car.orientation;
                                if(car.speed == 0) {car.speed = 10};
                                car.moves = distance / car.speed;
                                car.vx = dx / car.moves;
                                car.vy = dy / car.moves;
                                var angle = Math.atan2(dx, dy);
                                car.style.rotation = angle / Math.PI * 180;
                                //分配不同的car图片。考虑Canvas和SVG不同设计，设置不同参数
                                if (Math.abs(car.speed < 4)){
                                    car.style.externalGraphicSource = car1;
                                    car.style.externalGraphic = car1.src;
                                }
                                if (Math.abs(car.speed) >= 4 && Math.abs(car.speed < 5)){
                                    car.style.externalGraphicSource = car2;
                                    car.style.externalGraphic = car2.src;
                                }
                                if (Math.abs(car.speed >= 5)){
                                    car.style.externalGraphicSource = car3;
                                    car.style.externalGraphic = car3.src;
                                }
                                car.stop = false;
                            }else{
                                car.stop = true;
                                car.style.externalGraphicSource = car3;
                                car.style.externalGraphic = car3.src;
                            }
                            orientation *= -1;
                            cars.push(car);
                        }
                    }
                }
            }
        }
        vectorLayer.addFeatures(features);
        vectorLayerCars.addFeatures(cars);
    }

    function processFailed(e) {
        alert(e.error.errorMsg);
    }

    function monitor() {
        if(selectedBus) {
            map.removePopup(selectedBus.popup);
            selectedBus.popup.destroy();
            selectedBus.popup = null;
        }
        vectorLayerCars.removeAllFeatures();
        redraw = false;
        for(var i =0, len = cars.length; i < len; i++) {
            var car = cars[i], cargeometry = car.geometry;
            if(!car.stop) {
                if(car.moves < 1) {
                    cargeometry.x = car.nextPoint.x;
                    cargeometry.y = car.nextPoint.y;
                    //定义car的信息。
                    var feature = car.line, nextP;                            
                    car.currentIndex += car.orientation;
                    nextP = feature.geometry.components[car.currentIndex + car.orientation];
                    if(nextP) {
                        var dx = nextP.x - cargeometry.x;
                        var dy = nextP.y - cargeometry.y;
                        var distance = Math.sqrt(dx * dx + dy * dy);
                        car.moves = Math.abs(distance / car.speed);
                        car.vx = dx / car.moves;
                        car.vy = dy / car.moves;
                        var angle = Math.atan2(dx, dy);
                        car.style.rotation = angle / Math.PI * 180;
                        car.nextPoint = nextP;
                    }else{
                        car.stop = true;
                        car.style.fillColor = "rgb(150,150,150)";
                    }
                }else{
                    cargeometry.x += car.vx;
                    cargeometry.y += car.vy;
                    car.moves--;
                }
                //只要有车移动就需要重绘。
                redraw = true;
            }
        }
        vectorLayerCars.addFeatures(cars);
        if(selectedBus) {
            vectorLayerCars.selectedFeatures.push(selectedBus);
            //因为geometry变化，需要重新计算bounds
            selectedBus.geometry.calculateBounds();
            popup = new SuperMap.Popup("chicken", 
                    selectedBus.geometry.getBounds().getCenterLonLat(),
                    new SuperMap.Size(300,60),
                    "<div style='font-size:.8em; opacity: 0.8'><p>公交线路：" + selectedBus.line.num + "路" + 
                    "</p><p>当前位置：" + selectedBus.geometry.x + "，" + selectedBus.geometry.y + "</p></div>",
                    false);
            selectedBus.popup = popup;
            map.addPopup(popup);
        }
    }

    //在这个函数里定义重绘，保证每一个都已经被绘制。
    function addFeaturesCompelte(args) {
        if(redraw) {
            timerid = setTimeout(monitor, 50);
        }
    }

    //停止监控
    function stopMonitor() {
        window.clearTimeout(timerid);
        redraw = false;
    }
    dowork();
})();
