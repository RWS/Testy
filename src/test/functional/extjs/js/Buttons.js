Ext.onReady(function(){

    var showComponentButton = new Ext.Button({
        text: 'Buttons',
        handler: function(){
            var win = new Ext.Window({
                title: 'Buttons Window',
                width:400,
                height:200,
                plain: true,

                items: [
                    
                ],

                buttons: [{
                    xtype: 'splitbutton',
                    text:'Export',
                    handler: function(){
                        Ext.MessageBox.alert('Split Button', 'You selected ' + this.text);
                    },
                    menu: {
                        items: [
                            {
                                text: 'PDF',
                                handler: function(){
                                    console.info(this);
                                    Ext.MessageBox.alert('Split Button', 'You selected ' + this.text);
                                }
                            },
                            {
                                text: 'EXCEL',
                                handler: function(){
                                    Ext.MessageBox.alert('Split Button', 'You selected ' + this.text);
                                }
                            }
                        ]
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

    Testy.topToolbar.add(showComponentButton);
    Testy.topToolbar.doLayout();
});