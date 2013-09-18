Ext.BLANK_IMAGE_URL = 'http://extjs.cachefly.net/ext-3.4.0/resources/images/default/s.gif';

Ext.onReady(function(){
    var fm = Ext.form;
    new Ext.Button({
        text: 'FindElement',
        renderTo: Ext.getBody(),
        handler: function(){
            var win = new Ext.Window({
                title: 'Element',
                layout:'fit',
                width:200,
                height:200,
                plain: true,
                buttons: [{
                    text:'Alert',
                    handler: function(){
                        Ext.MessageBox.alert('Alert', 'Alert button was pressed');
                    }
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