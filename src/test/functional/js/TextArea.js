Ext.BLANK_IMAGE_URL = 'http://extjs.cachefly.net/ext-3.4.0/resources/images/default/s.gif';

Ext.onReady(function(){
    var fm = Ext.form;
    new Ext.Button({
        text: 'TextArea',
        renderTo: Ext.getBody(),
        handler: function(){
            var win = new Ext.Window({
                title: 'TextAreaWindow',
                layout:'fit',
                width:200,
                height:200,
                plain: true,

                items: new fm.TextArea({
                                fieldLabel: 'TextArea',
                                hideLabel: true,
                                name: 'textArea',
                                value: 'Value TextArea',
                                flex: 1
               }),

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