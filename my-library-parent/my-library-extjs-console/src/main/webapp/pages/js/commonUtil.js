function alertWarring(msg) {
	Ext.MessageBox.show({
		title : "提示",
		msg : msg,
		width : 250,
		buttons : Ext.MessageBox.OK,
		icon : Ext.MessageBox.WARNING
	});
}

function alertInformation(msg) {
	Ext.MessageBox.show({
		title : "提示",
		msg : msg,
		width : 250,
		buttons : Ext.MessageBox.OK,
		icon : Ext.MessageBox.INFO
	});
}
function alertSuccess(msg){
	Ext.MessageBox.show({
		title : "提示",
		msg : '<font style="color:green">'+msg+'</font>',
		width : 250,
		buttons : Ext.MessageBox.OK,
		icon : Ext.MessageBox.INFO
	});
}

function alertError(msg) {
	Ext.MessageBox.show({
		title : "提示",
		msg : '<font style="color:red">'+msg+'</font>',
		width : 250,
		buttons : Ext.MessageBox.OK,
		icon : Ext.MessageBox.ERROR
	});
}

function alertOkorCancel(msg, fn)
{
	Ext.MessageBox.show({
        title: '提示',
        msg: msg,
        width : 250,
        buttons: Ext.MessageBox.YESNO,
        fn: fn,
        icon : Ext.MessageBox.QUESTION
    });
}

Ext.override(Ext.form.RadioGroup, {
    getValue : function() {
        var v;
        this.items.each(function(item) {
            if (item.getValue()) {
                v = item.getRawValue();
                return false;
			}
		});
		return v;
	},
	setValue : function(v) {
        if (this.rendered) {
            this.items.each(function(item) {
                item.setValue(item.getRawValue() == v);
			});
        } else {
            for (k in this.items) {
                this.items[k].checked = this.items[k].inputValue == v;
            }
        }
	}
});


