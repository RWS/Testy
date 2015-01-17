Ext.onReady(function(){
    var fm = Ext.form;

    var showComponentButton = new Ext.Button({
        text: 'DisplayField',
        handler: function(){
            var win = new Ext.Window({
                title: 'DisplayFieldWindow',
                //layout:'fit',
                width:200,
                height:200,
                plain: true,

                items:
                /*new fm.DisplayField({
                    name: 'displayField',
                    value: 'DisplayFieldValue'
               }),*/

               [{
                    xtype:'button',
                    text: 'Download',
                    handler: function() {
                        window.location.href = ''
                    }
               },{
                    xtype:'displayfield',
                    name: 'displayField',
                    value: 'DisplayFieldValue'
               }],
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

    Testy.topToolbar.add(showComponentButton);
    Testy.topToolbar.doLayout();
});