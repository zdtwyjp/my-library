(function() {
    var map, local, baseLayer, resultLayer, vectorLayer, spatialAnalystURL,
        points = [new SuperMap.Geometry.Point(4933.319287022352, -3337.3849141502124),
                  new SuperMap.Geometry.Point(4960.9674060199022, -3349.3316322355736),
                  new SuperMap.Geometry.Point(5006.0235999418364, -3358.8890067038628),
                  new SuperMap.Geometry.Point(5075.3145648369318, -3378.0037556404409),
                  new SuperMap.Geometry.Point(5305.19551436013, -3376.9669111768926)],
        roadLine = new SuperMap.Geometry.LineString(points),
        styleLine = {
            strokeColor: "blue",
            strokeWidth: 3,
            strokeLinecap: "round",
            strokeDashstyle: "solid"
        },
        styleRegion = {
            strokeColor: "#304DBE",
            strokeWidth: 2,
            pointerEvents: "visiblePainted",
            fillColor: "#304DBE",
            fillOpacity: 0.4
        };
    function dowork() {
        var create = '缓冲区分析', remove = '移除结果';
        addBtn(create,bufferAnalystProcess);
        addBtn(remove,removeResult);

        map = new SuperMap.Map("map", { controls: [
            new SuperMap.Control.LayerSwitcher(),
            new SuperMap.Control.ScaleLine(),
            new SuperMap.Control.PanZoomBar(),
            new SuperMap.Control.Navigation({
                dragPanOptions: {
                                    enableKinetic: true
                                }
            })], units: "m"
        });
        map.allOverlays = true;
        baseLayer = new SuperMap.Layer.TiledDynamicRESTLayer("Changchun", DemoURL.changChun_Map, {transparent: true, cacheEnabled: true}, { maxResolution: "auto" });
        baseLayer.events.on({ "layerInitialized": addLayer });
        resultLayer = new SuperMap.Layer.Vector("缓冲区分析结果");
        vectorLayer = new SuperMap.Layer.Vector("vectorLine");

        var featureLine = new SuperMap.Feature.Vector();
        featureLine.geometry = roadLine;
        featureLine.style = styleLine;
        vectorLayer.addFeatures(featureLine);
    }

    function addLayer() {
        map.addLayers([baseLayer, resultLayer, vectorLayer]);
        map.setCenter(new SuperMap.LonLat(5105, -3375), 4);
    }
    function bufferAnalystProcess() {
        resultLayer.removeAllFeatures();
        var bufferServiceByDatasets = new SuperMap.REST.BufferAnalystService(DemoURL.changchun_spatialanalyst),
            bufferDistance = new SuperMap.REST.BufferDistance({
                value: 10
            }),
            bufferSetting = new SuperMap.REST.BufferSetting({
                endType: SuperMap.REST.BufferEndType.ROUND,
            leftDistance: bufferDistance,
            rightDistance: bufferDistance,
            semicircleLineSegment: 10
            }),
            filterParameter = new SuperMap.REST.FilterParameter({
                attributeFilter: "NAME='团结路'"
            }),
            dsBufferAnalystParameters = new SuperMap.REST.DatasetBufferAnalystParameters({
                dataset: "RoadLine2@Changchun",
            filterQueryParameter: filterParameter,
            bufferSetting: bufferSetting
            });
        bufferServiceByDatasets.events.on({ "processCompleted": bufferAnalystCompleted, "processFailed": bufferAnalystFailed });
        bufferServiceByDatasets.processAsync(dsBufferAnalystParameters);
    }

    function bufferAnalystCompleted(args) {
        resultLayer.addFeatures(args.result.recordset.features);
    }

    function bufferAnalystFailed(args) {
        doMapAlert("",args.error.errorMsg,true);
    }

    function removeResult() {
        resultLayer.removeAllFeatures();
    }
    dowork();
})();
