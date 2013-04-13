Ext.BLANK_IMAGE_URL = 'http://extjs.cachefly.net/ext-3.4.0/resources/images/default/s.gif';

Ext.onReady(function(){
    var fm = Ext.form;
    var multiSelect = new Ext.ux.form.MultiSelect({
                fieldLabel:'MultiSelect',
                width: 230,
                height: 65,
                disabled: true,
                allowBlank:true,
                valueField: 'id',
                displayField: 'value',
                store: store
            });

            var store = new Ext.data.ArrayStore({
                        fields: [
                            {name: 'id', type: 'string'},
                            {name: 'value', type: 'string'},

                        ],
                        data: [
                            ["afghan", "Afghan"],
                            ["albanian", "Albanian"],
                            ["algerian", "Algerian"],
                        ]
                    });

    var fsf = new Ext.FormPanel({
        frame:true,
        title: 'MultiSelectFromPanel',
        items: multiSelect
    });

    new Ext.Button({
        text: 'MultiSelect',
        renderTo: Ext.getBody(),
        handler: function(){
            var win = new Ext.Window({
                title: 'MultiSelectWindow',
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