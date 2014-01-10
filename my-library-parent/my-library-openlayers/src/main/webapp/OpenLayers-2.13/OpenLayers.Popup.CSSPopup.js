/**
 * update Popup Style
 */
OpenLayers.Popup.CSSPopup = OpenLayers.Class(OpenLayers.Popup, {
    autoSize: true,
    panMapIfOutOfView: true,
    fixedRelativePosition: false,
	displayClass: 'olPopup_css',
	contentDisplayClass: 'olPopupContent_css',
	
	
	initialize:function(id, lonlat, contentSize, contentHTML, closeBox, closeBoxCallback, backgroundColor, border, opacity) {
        if (id == null) {
            id = OpenLayers.Util.createUniqueID(this.CLASS_NAME + "_");
        }

        this.id = id;
        this.lonlat = lonlat;

        this.contentSize = (contentSize != null) ? contentSize 
                                  : new OpenLayers.Size(
                                                   OpenLayers.Popup.CSSPopup.WIDTH,
                                                   OpenLayers.Popup.CSSPopup.HEIGHT);
        if (contentHTML != null) { 
             this.contentHTML = contentHTML;
        }
        
        if (backgroundColor != null) {
        	this.backgroundColor = backgroundColor;
        } else {
	        this.backgroundColor = OpenLayers.Popup.CSSPopup.COLOR;
        }
        
        if (border != null) {
        	this.border = border;
        } else {
	        this.border = OpenLayers.Popup.CSSPopup.BORDER;
        }

        if (opacity != null) {
        	this.opacity = opacity;
        } else {
	        this.opacity = OpenLayers.Popup.CSSPopup.OPACITY;
        }
        
        this.div = OpenLayers.Util.createDiv(this.id, null, null, 
                                             null, null, this.border, 'hidden', this.opacity);
        this.div.className = this.displayClass;
        
        var groupDivId = this.id + "_GroupDiv";
        this.groupDiv = OpenLayers.Util.createDiv(groupDivId, null, null, 
                                                    null, 'relative', null,
                                                    'hidden');

        var id = this.div.id + "_contentDiv";
        this.contentDiv = OpenLayers.Util.createDiv(id, null, this.contentSize.clone(), 
                                                    null, 'relative');
        this.contentDiv.className = this.contentDisplayClass;
        this.groupDiv.appendChild(this.contentDiv);
        this.div.appendChild(this.groupDiv);

        if (closeBox) {
            this.addCloseBox(closeBoxCallback);
        } 

        this.registerEvents();
    },

    CLASS_NAME: 'OpenLayers.Popup.CSSPopup'
});

OpenLayers.Popup.CSSPopup.WIDTH = 100;
OpenLayers.Popup.CSSPopup.HEIGHT = 50;
OpenLayers.Popup.CSSPopup.COLOR = "white";
OpenLayers.Popup.CSSPopup.OPACITY = 1;
OpenLayers.Popup.CSSPopup.BORDER = "1px solid red";