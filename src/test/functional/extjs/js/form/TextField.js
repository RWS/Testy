Ext.BLANK_IMAGE_URL = 'http://extjs.cachefly.net/ext-3.4.0/resources/images/default/s.gif';

Ext.onReady(function(){
    var fm = Ext.form;

    var fsf = new Ext.FormPanel({
//        frame:true,
//        title: 'Simple Form with TextField',
        border: false,
		frame : true,
		monitorValid : true,
		defaultType : 'textfield',
		labelAlign: 'right',
		labelWidth: 100,
		autoHeight:true,
		bodyStyle : 'padding: 10px 50px 10px 10px;',
		defaults: {
			allowBlank : false,
			anchor: '100%',
			minLength: 5
		},
        items: [{
            xtype:'textfield',
            fieldLabel: 'First Name',
            name: 'firstName',
            value: 'First Name',
            allowBlank: false,
            readOnly : true
        }, {
            xtype:'textfield',
            fieldLabel: 'Las\'t Name',
            name: 'lastName',
            allowBlank: false
        }, {
           xtype:'textfield',
           fieldLabel: 'Disable TextField',
           name: 'disableTextField',
           value: 'Disable Name',
           allowBlank: false,
           disabled: true
       }],
    });

    new Ext.Button({
        text: 'TextField',
        renderTo: Ext.getBody(),
        handler: function(){
            var win = new Ext.Window({
                title: 'TextFieldWindow',
                layout:'fit',
                width:300,
                height:200,
                plain: true,

                items: fsf,

                buttons: [{
                    text:'Submit',
                    disabled:true
                },{
                    text: 'Close',
                    handler: function(){
                        win.close();
                    }
                }]
            });
            win.show();
        }
    });
});