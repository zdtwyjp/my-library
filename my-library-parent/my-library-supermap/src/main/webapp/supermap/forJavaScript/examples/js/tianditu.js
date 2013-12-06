(function() {
    var map, layer, layer1, layer2, marker, marker1,
		url_2_10_lyr = "http://tile0.tianditu.com/services/A0512_EMap",
		url_11_12_lyr = "http://tile0.tianditu.com/services/B0312_Map1112",
		url_13_18_lyr = "http://tile0.tianditu.com/services/tdt_vec_13_18",
	
		url_2_10_mkr = "http://tile0.tianditu.com/services/AB0512_Anno",
		url_11_18_mkr = "http://tile0.tianditu.com/services/tdt_vec_anno_13_18",
		
		layerName = "全球1:100万地图",
		markerName = "全球1:100万注记";    
        
	function dowork(){
		map = new SuperMap.Map("map", {controls: [
			new SuperMap.Control.ScaleLine(),
			new SuperMap.Control.PanZoomBar(),
			new SuperMap.Control.LayerSwitcher(),
			new SuperMap.Control.Navigation({
				dragPanOptions: {
					enableKinetic: true
				}
			})], units: "degrees", allOverlays: true
		});
		layer = new Geo.View2D.Layer.GlobeTile(layerName, url_2_10_lyr, {
			transitionEffect: "resize",
			topLevel: 2,
			bottomLevel: 18,
			maxExtent: new Geo.Bounds(-180, -90, 180, 90)
		});
		marker = new Geo.View2D.Layer.GlobeTile(markerName, url_2_10_mkr, {
			transitionEffect: "resize",
			topLevel: 2,
			bottomLevel: 18,
			maxExtent: new Geo.Bounds(-180, -90, 180, 90)
		});        
		layer1 = new Geo.View2D.Layer.GlobeTile(layerName, url_11_12_lyr, {
			transitionEffect: "resize",
			topLevel: 2,
			bottomLevel: 18,
			maxExtent: new Geo.Bounds(-180, -90, 180, 90)
		});
		marker1 = new Geo.View2D.Layer.GlobeTile(markerName, url_11_18_mkr, {
			transitionEffect: "resize",
			topLevel: 2,
			bottomLevel: 18,
			maxExtent: new Geo.Bounds(-180, -90, 180, 90)
		});
		layer2 = new Geo.View2D.Layer.GlobeTile(layerName, url_13_18_lyr, {
			transitionEffect: "resize",
			topLevel: 2,
			bottomLevel: 18,
			maxExtent: new Geo.Bounds(-180, -90, 180, 90)
		});
		//map.addLayers([layer2,layer,marker,layer1,marker1]);
		map.addLayers([layer2, layer, marker, layer1, marker1]);
		map.setCenter(new SuperMap.LonLat(0, 0), 2);
	}
    dowork();
})();
