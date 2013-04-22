Ext.BLANK_IMAGE_URL = 'http://extjs.cachefly.net/ext-3.4.0/resources/images/default/s.gif';

Ext.onReady(function(){
    var fm = Ext.form;
    new Ext.Button({
        text: 'DisplayField',
        renderTo: Ext.getBody(),
        handler: function(){
            var win = new Ext.Window({
                title: 'DisplayFieldWindow',
                layout:'fit',
                width:200,
                height:200,
                plain: true,

                items: new fm.DisplayField({
                    name: 'displayField',
                    value: 'DisplayFieldValue'
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