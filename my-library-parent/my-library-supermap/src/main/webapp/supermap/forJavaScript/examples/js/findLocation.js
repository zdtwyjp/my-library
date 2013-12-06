﻿(function() {
    var local, map, layer, vectorLayer, markerLayer1, 
        markerLayer2,
        style = {
            strokeColor: "#304DBE",
            strokeWidth: 2,
            pointRadius: 2,
            pointerEvents: "visiblePainted",
            fill: true,
            fillColor: "#304DBE"
        };
    function dowork() {
        var create = '分析', remove = '清除';
        addBtn(create,findLocaltion);
        addBtn(remove,clearElements);

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
        layer = new SuperMap.Layer.TiledDynamicRESTLayer("Changchun", DemoURL.changChun_Map, {transparent: true, cacheEnabled: true},{maxResolution:"auto"});
        layer.events.on({"layerInitialized":addLayer});
        vectorLayer = new SuperMap.Layer.Vector("Vector Layer");
        markerLayer1 = new SuperMap.Layer.Markers("Markers");
        markerLayer2 = new SuperMap.Layer.Markers("Markers");
        var size = new SuperMap.Size(44,40),
            offset = new SuperMap.Pixel(-(size.w/2), -size.h);
        markerLayer1.addMarker(new SuperMap.Marker(new SuperMap.LonLat(1675.9256791377206, -593.56822512495194), new SuperMap.Icon("../resource/controlImages/marker-gold.png", size, offset)));
        markerLayer1.addMarker(new SuperMap.Marker(new SuperMap.LonLat(2820.35101097629, -2358.0414663985171), new SuperMap.Icon("../resource/controlImages/marker-gold.png", size, offset)));
        markerLayer1.addMarker(new SuperMap.Marker(new SuperMap.LonLat(2909.4396668115278, -3647.0074300836109), new SuperMap.Icon("../resource/controlImages/marker-gold.png", size, offset)));
        markerLayer1.addMarker(new SuperMap.Marker(new SuperMap.LonLat(1544.5037476378677 ,-5616.5950974905827), new SuperMap.Icon("../resource/controlImages/marker-gold.png", size, offset)));
        markerLayer1.addMarker(new SuperMap.Marker(new SuperMap.LonLat(6623.5972101719526, -2130.4887600981415), new SuperMap.Icon("../resource/controlImages/marker-gold.png", size, offset)));
        markerLayer1.addMarker(new SuperMap.Marker(new SuperMap.LonLat(5482.4979617984973 ,-4504.2328567816048), new SuperMap.Icon("../resource/controlImages/marker-gold.png", size, offset)));
        markerLayer1.addMarker(new SuperMap.Marker(new SuperMap.LonLat(6940.6579024271468 ,-1627.6012900626256), new SuperMap.Icon("../resource/controlImages/marker-gold.png", size, offset)));
        markerLayer1.addMarker(new SuperMap.Marker(new SuperMap.LonLat(8215.9188781715948 ,-5747.5063918659716), new SuperMap.Icon("../resource/controlImages/marker-gold.png", size, offset)));
    }

    function addLayer() {
        map.addLayers([layer, vectorLayer, markerLayer1, markerLayer2]);
        map.setCenter(new SuperMap.LonLat(4503.6240321526, -3861.911472192499), 0);
    }
    function findLocaltion() {
        vectorLayer.removeAllFeatures();
        markerLayer2.clearMarkers();

        var findLocaltionService, parameter, analystParameter, resultSetting,
            supplyCenterType_FIXEDCENTER = SuperMap.REST.SupplyCenterType.FIXEDCENTER,
            supplyCenterType_NULL = SuperMap.REST.SupplyCenterType.NULL;
        supplyCenterType_OPTIONALCENTER = SuperMap.REST.SupplyCenterType.OPTIONALCENTER,
                                        supplyCenters=[new SuperMap.REST.SupplyCenter({
                                            maxWeight: 500,
                                        nodeID: 139,
                                        resourceValue: 100,
                                        type: supplyCenterType_OPTIONALCENTER

                                        }),
                                        new SuperMap.REST.SupplyCenter({
                                            maxWeight: 500,
                                        nodeID: 1358,
                                        resourceValue: 100,
                                        type: supplyCenterType_OPTIONALCENTER

                                        }),
                                        new SuperMap.REST.SupplyCenter({
                                            maxWeight: 500,
                                        nodeID: 2972,
                                        resourceValue: 100,
                                        type: supplyCenterType_OPTIONALCENTER

                                        }),
                                        new SuperMap.REST.SupplyCenter({
                                            maxWeight: 500,
                                        nodeID: 5523,
                                        resourceValue: 100,
                                        type: supplyCenterType_OPTIONALCENTER

                                        }),
                                        new SuperMap.REST.SupplyCenter({
                                            maxWeight: 500,
                                        nodeID: 1161,
                                        resourceValue: 100,
                                        type: supplyCenterType_OPTIONALCENTER
                                        }),
                                        new SuperMap.REST.SupplyCenter({
                                            maxWeight: 500,
                                        nodeID: 4337,
                                        resourceValue: 100,
                                        type: supplyCenterType_OPTIONALCENTER

                                        }),
                                        new SuperMap.REST.SupplyCenter({
                                            maxWeight: 500,
                                        nodeID: 5732,
                                        resourceValue: 100,
                                        type: supplyCenterType_NULL

                                        }),
                                        new SuperMap.REST.SupplyCenter({
                                            maxWeight: 500,
                                        nodeID: 663,
                                        resourceValue: 100,
                                        type: supplyCenterType_FIXEDCENTER

                                        })
        ];
        parameter = new SuperMap.REST.FindLocationParameters({
                  expectedSupplyCenterCount: 3,
                  isFromCenter: false,
                  nodeDemandField: "Demand",
                  turnWeightField: "TurnCost",
                  weightName: "length",
                  supplyCenters: supplyCenters
        });

        findLocaltionService = new SuperMap.REST.FindLocationService(DemoURL.changchun_RoadNet, {
            eventListeners: {"processCompleted": processCompleted}});
        findLocaltionService.processAsync(parameter);
    }

    function processCompleted(findLocationEventArgs) {
        var result = findLocationEventArgs.result,
            features = [];
        if (result.demandResults){
            for (var i=0,demandResults=result.demandResults,len=demandResults.length; i<len; i++) {
                var feature = new SuperMap.Feature.Vector();
                feature.geometry = demandResults[i].geometry;
                feature.style = style;
                features.push(feature);
            }
        }
        vectorLayer.addFeatures(features);
    }

    function clearElements() {
        vectorLayer.removeAllFeatures();
        markerLayer2.clearMarkers();
    }
    dowork();
})();
