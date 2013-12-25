(function()  {
    var map, layer;
    map = new SuperMap.Map("map",{controls: [                      
        new SuperMap.Control.ScaleLine(),
        new SuperMap.Control.PanZoomBar(),
        new SuperMap.Control.Navigation({
            dragPanOptions: {
                                enableKinetic: true
                            }
        })]
    });

    //wmts或许所需要的matrixID信息
    var matrixIds = [];
    for (var i=0; i<22; ++i) {
        matrixIds[i] = {identifier:i};
    };
    //当前图层的分辨率数组信息,和matrixIds一样，需要用户从wmts服务获取并明确设置,resolutions数组和matrixIds数组长度相同
    var resolutions = [1.25764139776733,0.628820698883665,0.251528279553466,
                        0.125764139776733,0.0628820698883665,0.0251528279553466,
                        0.0125764139776733,0.00628820698883665,0.00251528279553466,
                        0.00125764139776733,0.000628820698883665,0.000251528279553466,    
                        0.000125764139776733,0.0000628820698883665,0.0000251528279553466,
                        0.0000125764139776733, 0.00000628820698883665,0.00000251528279553466,
                        0.00000125764139776733,0.000000628820698883665,0.000000251528279553466];
    //新建图层
    layer = new SuperMap.Layer.WMTS({name: "World",
        url: DemoURL.world_WMTS,
          layer: "World",
          style: "default",
          matrixSet: "GlobalCRS84Scale_World",
          format: "image/png",
          resolutions:resolutions,
          matrixIds:matrixIds,
          opacity: 1,
          requestEncoding:"KVP"});

    //图层添加并显示指定级别
    map.addLayers([layer]);  
    map.setCenter(new SuperMap.LonLat(0, 0), 2);  
})();
