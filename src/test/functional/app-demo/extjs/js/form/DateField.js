Ext.onReady(function(){
    var fm = Ext.form;
    var showComponentButton = new Ext.Button({
        text: 'DateField',
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
                    id: 'close',
                    handler: function(){
                        win.close();
                    }
                }]
            });
            win.show();
        }
    });

    Testy.topToolbar.add('-');
    Testy.topToolbar.add(showComponentButton);
    Testy.topToolbar.doLayout();
});