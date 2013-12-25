(function() {
    var map, local, layer, vectorLayer, drawPoint, drawPolygon, dataUrl, ids, modifyFeature,
        style = {
            strokeColor: "#304DBE",
            strokeWidth: 1,
            pointerEvents: "visiblePainted",
            fillColor: "#304DBE",
            fillOpacity: 0.8,
            pointRadius:2
        };
    function dowork() {
        var create = '新增地物', create1 = '选择地物', create2 = '编辑地物', remove = '删除地物';
        addBtn(create,activateAddFeature);
        addBtn(create1,activateSelectedFeature);
        addBtn(create2,editselectedFeature);
        addBtn(remove,deleteSelectedFeature);

        layer = new SuperMap.Layer.TiledDynamicRESTLayer("京津", DemoURL.jingjin_rest, {transparent: true, cacheEnabled: false}, {maxResolution:"auto"}); 
        layer.events.on({"layerInitialized":addLayer});     
        vectorLayer = new SuperMap.Layer.Vector("Vector Layer");
        vectorLayer.events.on({"afterfeaturemodified": editFeatureCompleted});
        modifyFeature = new SuperMap.Control.ModifyFeature(vectorLayer);

        drawPoint = new SuperMap.Control.DrawFeature(vectorLayer, SuperMap.Handler.Point);
        drawPoint.events.on({"featureadded": selectedFeatureCompleted});

        drawPolygon = new SuperMap.Control.DrawFeature(vectorLayer, SuperMap.Handler.Polygon);     
        drawPolygon.events.on({"featureadded": addFeatureCompleted});

        map = new SuperMap.Map("map", {controls: [
            new SuperMap.Control.LayerSwitcher(),
            new SuperMap.Control.ScaleLine(),
            new SuperMap.Control.PanZoomBar(),
            new SuperMap.Control.Navigation({
                dragPanOptions: {
                                    enableKinetic: true
                                }}),
            drawPoint, drawPolygon]
        });
        map.addControl(modifyFeature);
    }

    function addLayer() {
        map.addLayers([layer, vectorLayer]);
        map.setCenter(new SuperMap.LonLat(116.22, 39.53), 1);
    } 
    //激活添加地物
    function activateAddFeature() {
        //先清除上次的显示结果
        vectorLayer.removeAllFeatures();
        clearAllDeactivate();
        drawPolygon.activate();
    }
    //执行添加地物
    function addFeatureCompleted(drawGeometryArgs) {
        drawPolygon.deactivate();
        var geometry = drawGeometryArgs.feature.geometry,
            feature = new SuperMap.Feature.Vector();
        feature.geometry = drawGeometryArgs.feature.geometry,
            feature.style = style;
        vectorLayer.addFeatures(feature);

        geometry.id = "100000";
        var editFeatureParameter, 
            editFeatureService,
            features = {
                fieldNames:[],
                fieldValues:[],
                geometry:geometry
            };
        editFeatureParameter = new SuperMap.REST.EditFeaturesParameters({
            features: [features],
                             editType: SuperMap.REST.EditType.ADD,
                             returnContent:false
        });
        editFeatureService = new SuperMap.REST.EditFeaturesService(DemoURL.jingjin_Landuse_R, {
            eventListeners: {
                                "processCompleted": addFeaturesProcessCompleted,
                           "processFailed": processFailed
                            }
        });
        editFeatureService.processAsync(editFeatureParameter);
    }
    //添加地物成功
    function addFeaturesProcessCompleted(editFeaturesEventArgs) {
        var ids = editFeaturesEventArgs.result.IDs,
            resourceInfo = editFeaturesEventArgs.result.resourceInfo;
        if(ids === null && resourceInfo === null) return;

        if((ids && ids.length > 0) || (resourceInfo && resourceInfo.succeed)) {
            doMapAlert("","新增地物成功");
            vectorLayer.removeAllFeatures();
            //重新加载图层
            map.removeLayer(layer,true);
            layer = new SuperMap.Layer.TiledDynamicRESTLayer("京津", DemoURL.jingjin_rest, {transparent: true, cacheEnabled: false}, {maxResolution:"auto"}); 
            layer.events.on({"layerInitialized":reloadLayer});
        }else {
            doMapAlert("","新增地物失败",true);
        }
    }
    function reloadLayer() {
        map.addLayer(layer);
        map.setCenter(new SuperMap.LonLat(116.22, 39.53), 1);
    }
    function processFailed(e) {
        doMapAlert("",e.error.errorMsg,true);
    }        
    //激活选择地物
    function activateSelectedFeature() {
        vectorLayer.removeAllFeatures();
        clearAllDeactivate();
        drawPoint.activate();
    }
    //执行选择地物
    function selectedFeatureCompleted(drawGeometryArgs) {
        drawPoint.deactivate();
        var getFeaturesByGeometryParams,
            getFeaturesByGeometryService,
            geometry = drawGeometryArgs.feature.geometry;
        getFeaturesByGeometryParams = new SuperMap.REST.GetFeaturesByGeometryParameters({
            datasetNames: ["Jingjin:Landuse_R"],
                                    spatialQueryMode: SuperMap.REST.SpatialQueryMode.INTERSECT,
                                    geometry: geometry
        });
        getFeaturesByGeometryService = new SuperMap.REST.GetFeaturesByGeometryService(DemoURL.jingjin_data, {
            eventListeners: {
                                "processCompleted": selectedFeatureProcessCompleted,
                                     "processFailed": processFailed
                            }
        });
        getFeaturesByGeometryService.processAsync(getFeaturesByGeometryParams);
    }
    //选择地物完成
    function selectedFeatureProcessCompleted(getFeaturesEventArgs) {
        var features, 
            feature,
            i, len, 
            originFeatures = getFeaturesEventArgs.originResult.features,
            result = getFeaturesEventArgs.result;
        vectorLayer.removeAllFeatures();
        if(originFeatures === null || originFeatures.length === 0) {
            doMapAlert("","查询地物为空");
            return;
        }
        ids = new Array();
        //将当前选择的地物的ID保存起来，以备删除地物使用
        for(i = 0, len = originFeatures.length; i < len; i++) {            
            ids.push(originFeatures[i].ID);
        }
        if (result && result.features) {
            features = result.features;
            for (var j=0, len = features.length; j<len; j++) {
                feature = features[j];
                feature.style = style;
                vectorLayer.addFeatures(feature);
            }
        }
    }
    //激活编辑地物
    function editselectedFeature() {
        clearAllDeactivate();
        modifyFeature.activate();
    }
    //执行地物编辑
    function editFeatureCompleted(event) {
        modifyFeature.deactivate();
        var editFeatureParameter, 
            editFeatureService,
            features,
            attributes,
            feature = event.feature;
            
        attributes = feature.attributes;
        var attrNames = [];
        var attrValues = [];
        for(var attr in attributes) {
            attrNames.push(attr);
            attrValues.push(attributes[attr]);
        }
        features = {
            fieldNames:attrNames,
            fieldValues:attrValues,
            geometry:event.feature.geometry
        };
        features.geometry.id = feature.fid;
        editFeatureParameter = new SuperMap.REST.EditFeaturesParameters({
            features: [features],
                             editType: SuperMap.REST.EditType.UPDATE
        });
        editFeatureService = new SuperMap.REST.EditFeaturesService(DemoURL.jingjin_Landuse_R, {
            eventListeners: {
                                "processCompleted": updateFeaturesProcessCompleted,
                           "processFailed": processFailed
                            }
        });
        editFeatureService.processAsync(editFeatureParameter);
    }
    //更新地物完成
    function updateFeaturesProcessCompleted(editFeaturesEventArgs) {
        if(editFeaturesEventArgs.result.resourceInfo.succeed) {
            doMapAlert("","更新地物成功");
            //重新加载图层
            vectorLayer.removeAllFeatures();
            map.removeLayer(layer,true);
            layer = new SuperMap.Layer.TiledDynamicRESTLayer("京津", DemoURL.jingjin_rest, {transparent: true, cacheEnabled: false}, {maxResolution:"auto"}); 
            layer.events.on({"layerInitialized":reloadLayer}); 
        }
        else {
            alert("更新地物失败");
        }
    }
    //删除选中地物
    function deleteSelectedFeature() {
        clearAllDeactivate();
        //if(ids === null || typeof ids === "undefined") return; 
        var editFeatureParameter, 
            editFeatureService;
        editFeatureParameter = new SuperMap.REST.EditFeaturesParameters({
            IDs: ids,
                             editType: SuperMap.REST.EditType.DELETE
        });
        editFeatureService = new SuperMap.REST.EditFeaturesService(DemoURL.jingjin_Landuse_R, {
            eventListeners: {
                                "processCompleted": deleteFeaturesProcessCompleted,
                           "processFailed": processFailed
                            }
        });
        editFeatureService.processAsync(editFeatureParameter);
    }
    //删除地物完成
    function deleteFeaturesProcessCompleted(editFeaturesEventArgs) {
        if(editFeaturesEventArgs.result.resourceInfo.succeed) {
            alert("删除地物成功");
            //重新加载图层
            vectorLayer.removeAllFeatures();
            map.removeLayer(layer,true);
            layer = new SuperMap.Layer.TiledDynamicRESTLayer("京津", DemoURL.jingjin_rest, {transparent: true, cacheEnabled: false}, {maxResolution:"auto"}); 
            layer.events.on({"layerInitialized":reloadLayer}); 
        }
        else {
            alert("删除地物失败");
        }
    }  
    function clearAllDeactivate() {
        modifyFeature.deactivate();
        drawPoint.deactivate();
        drawPolygon.deactivate();
    }
    dowork();
})();
