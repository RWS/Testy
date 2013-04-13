Ext.BLANK_IMAGE_URL = 'http://extjs.cachefly.net/ext-3.4.0/resources/images/default/s.gif';

Ext.onReady(function(){
    var fm = Ext.form;

    var fsf = new Ext.FormPanel({
        frame:true,
        title: 'Simple Form with TextField',
        items: [{
            xtype:'textfield',
            fieldLabel: 'First Name',
            name: 'firstName',
            allowBlank: false,
            readOnly : true
        }, {
            xtype:'textfield',
            fieldLabel: 'Last Name',
            name: 'lastName',
            allowBlank: false
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