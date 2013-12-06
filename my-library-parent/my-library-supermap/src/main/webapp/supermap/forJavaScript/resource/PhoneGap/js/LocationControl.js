/* Copyright (c) 2000-2011 by SuperMap Software Co., Ltd.*/

/**
 * Class: SuperMap.LocationControl
 * 支持安卓定位功能。
 */
SuperMap.LocationControl = SuperMap.Class({
    
    /**
     * Constructor: SuperMap.LocationControl
     * 构造函数。
     *
     * 例如：
     * (start code)	
     * var control = new SuperMap.LocationControl();
	control.local();
     * (end)
     */
    initialize: function() {
    },
    /**
     * Method: local
     * 截图。
     * Parameters:
     * onSuccess - {<Function>} 定位成功回调函数。
     * onError - {<Function>} 定位失败回调函数。
     */
    local: function(onSuccess,onError){
		var me = this;
        try{
	    navigator.geolocation.getCurrentPosition(function(onSuccess,onError){
		return function(position){
		    position = new SuperMap.LonLat(position.coords.longitude, position.coords.latitude);
		   /* position = new SuperMap.LonLat(position.coords.longitude, position.coords.latitude).transform(
				  new SuperMap.Projection("EPSG:4326"),
				  new SuperMap.Projection("EPSG:900913"));*/
			me.transCoordinate(position.lon,position.lat,onSuccess,onError);
		    //onSuccess(position);
		}
	    }(onSuccess,onError), onError, { maximumAge: 5000, timeout: 120000, enableHighAccuracy: true });	
	}
	catch(e){
	    alert("定位失败");
	}
    },
	/*var i=0;
		if(coordinateXY.length == 0 && i<2){  //转换不成功时重复执行
		    i++;
		    transCoordinate();
		}*/
	
	//云服务转换坐标
	
    transCoordinate:function(lon,lat,onSuccess,onError){   
		//var coordinateXY=new Array();
        var urlCoordinate="http://services.supermapcloud.com/iserver/cloudhandler";
        
        var lon_Cloud = lon;
        var lat_Cloud = lat;
        
        var param = "{\"x\":" + lon_Cloud + ",\"y\":" + lat_Cloud + "}";
        var data = {"servicename":"coordinateService","methodname":"convertGPS2SM","parameter":param};
        
        jQuery.ajax({
            "dataType":"jsonp",
            "jsonp":"jsonp",
            "type":"GET",
            "url":urlCoordinate,
            "data":data,
            "success":function(onSuccess){
				return function(cb){
					var position = {
						"lon":cb.result[0].x,
						"lat":cb.result[0].y
					};
					onSuccess(position);
					//coordinateXY.push(cb.result[0].x);
					//coordinateXY.push(cb.result[0].y);
				}
            }(onSuccess),
            "error":onError
        });
    },
	
    /**
     * APIMethod: destroy
     * 释放资源,将引用资源的属性置空。
     */
    destroy: function() {
    },
    
    CLASS_NAME: "SuperMap.ShotScreenControl"
});