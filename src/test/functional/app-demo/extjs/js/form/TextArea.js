Ext.onReady(function(){
    var fm = Ext.form;

    var showComponentButton = new Ext.Button({
        text: 'TextArea',
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

    Testy.topToolbar.add(showComponentButton);
    Testy.topToolbar.doLayout();
});