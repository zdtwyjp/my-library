(function() {
    var map, local, layer, vectorLayer, control, queryBounds, markerLayer,
    style = {
        strokeColor: "#304DBE",
        strokeWidth: 1,
        pointerEvents: "visiblePainted",
        fillColor: "#304DBE",
        fillOpacity: 0.3
    };
    function dowork() {
        var create = '查询', remove = '清除';
        addBtn(create,drawGeometry);
        addBtn(remove,clearFeatures);
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
        layer = new SuperMap.Layer.TiledDynamicRESTLayer("World", DemoURL.world, {transparent: true, cacheEnabled: true},{maxResolution:"auto"}); 
        layer.events.on({"layerInitialized":addLayer});    
        vectorLayer = new SuperMap.Layer.Vector("Vector Layer");//新建一个vectorLayer的矢量图层
        markerLayer = new SuperMap.Layer.Markers("Markers");//创建一个有标签的图层
    }

    function addLayer() {
        map.addLayers([layer, vectorLayer, markerLayer]);
        map.setCenter(new SuperMap.LonLat(0, 0), 0);
    }            
    function drawGeometry() {
        //先清除上次的显示结果
        vectorLayer.removeAllFeatures();//清除矢量地图上的所有特征和元素
        markerLayer.clearMarkers();//清除图层的标签

        control = new SuperMap.Control();
        SuperMap.Util.extend(control, {//Util工具类   extend指的是将复制所有的属性的源对象到目标对象
            draw: function () {
                      this.box = new SuperMap.Handler.Box(control,{"done": this.notice});//此句是创建一个句柄，Box是一个处理地图拖放一个矩形的事件，这个矩形显示是开始于在按下鼠标，然后移动鼠标，最后完成在松开鼠标。
                      this.box.boxDivClassName = "qByBoundsBoxDiv";//boxDivClassName用于绘制这个矩形状的图形
                      this.box.activate();//激活句柄
                  },
            //将拖动的矩形显示在地图上
            notice: function (bounds) {
                        this.box.deactivate();//处理关闭激活句柄

                        var ll = map.getLonLatFromPixel(new SuperMap.Pixel(bounds.left, bounds.bottom)),//getLonLatFromPixel从视口坐标获得地理坐标
            ur = map.getLonLatFromPixel(new SuperMap.Pixel(bounds.right, bounds.top));
        queryBounds = new SuperMap.Bounds(ll.lon, ll.lat, ur.lon, ur.lat);

        var feature = new SuperMap.Feature.Vector();
        feature.geometry = queryBounds.toGeometry(),
            feature.style = style;
        vectorLayer.addFeatures(feature);

        var queryParam, queryByBoundsParams, queryService;
        queryParam = new SuperMap.REST.FilterParameter({name: "Capitals@World.1"});//FilterParameter设置查询条件，name是必设的参数，（图层名称格式：数据集名称@数据源别名）
        queryByBoundsParams = new SuperMap.REST.QueryByBoundsParameters({queryParams: [queryParam], bounds: queryBounds});//queryParams查询过滤条件参数数组。bounds查询范围
        queryService = new SuperMap.REST.QueryByBoundsService(DemoURL.world, {
            eventListeners: {
                                "processCompleted": processCompleted,
                     "processFailed": processFailed
                            }
        });
        queryService.processAsync(queryByBoundsParams);//向服务端传递参数，然后服务端返回对象
                    }
        });
        map.addControl(control);
    }
    function processCompleted(queryEventArgs) {
        var i, j, result = queryEventArgs.result;//queryEventArgs服务端返回的对象
        if (result && result.recordsets) {
            for (i=0, recordsets=result.recordsets, len=recordsets.length; i<len; i++) {
                if (recordsets[i].features) {
                    for (j=0; j<recordsets[i].features.length; j++) {
                        var point = recordsets[i].features[j].geometry,
                            size = new SuperMap.Size(44, 33),
                                 offset = new SuperMap.Pixel(-(size.w/2), -size.h),
                                 icon = new SuperMap.Icon("../resource/controlImages/marker.png", size, offset);
                        markerLayer.addMarker(new SuperMap.Marker(new SuperMap.LonLat(point.x, point.y), icon));
                    }
                }
            }
        }
    }
    function processFailed(e) {
        doMapAlert("",e.error.errorMsg,true);
    }
    function clearFeatures() {
        vectorLayer.removeAllFeatures();
        markerLayer.clearMarkers();
    }

    dowork();
})();
