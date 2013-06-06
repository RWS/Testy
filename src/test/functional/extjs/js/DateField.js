Ext.BLANK_IMAGE_URL = 'http://extjs.cachefly.net/ext-3.4.0/resources/images/default/s.gif';

Ext.onReady(function(){
    var fm = Ext.form;
    new Ext.Button({
        text: 'DateField',
        renderTo: Ext.getBody(),
        handler: function(){
            var win = new Ext.Window({
                title: 'DateFieldWindow',
                layout:'fit',
                width:200,
                height:200,
                plain: true,

                items: new fm.DateField({
                    name: 'dateField',
                    editable : false,
                    format: 'd/m/Y',
                    minValue: '01/01/06'
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