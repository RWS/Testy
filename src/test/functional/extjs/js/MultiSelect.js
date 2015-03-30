Ext.onReady(function(){

    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';

    var fm = Ext.form;

    var store = new Ext.data.ArrayStore({
        fields: [
            {name: 'id', type: 'string'},
            {name: 'value', type: 'string'},
        ],
        data: [
            ["afghan", "Afghan"],
            ["albanian", "Albanian"],
            ["algerian", "Algerian"],
            ["french", "French"],
            ["english", "English"],
            ["german", "German"],
            ["hebrew", "Hebrew"],
            ["italian", "Italian"],
            ["japanese", "Japanese"],
            ["romanian", "Romanian"],
            ["russian", "Russian"],
            ["spanish", "Spanish"],
        ]
    });

    var fsf = new Ext.form.FormPanel({
        width: 700,
        bodyStyle: 'padding:10px;',
        title: 'MultiSelectFromPanel',
        items: [{
            xtype: 'multiselect',
            fieldLabel: 'MultiSelect',
            width: 250,
            height: 100,
            allowBlank: false,
            valueField: 'id',
            displayField: 'value',
            store: store,
        }]
    });

    var showComponentButton = new Ext.Button({
        text: 'MultiSelect',
        handler: function(){
            var win = new Ext.Window({
                title: 'MultiSelectWindow',
                width:400,
                height:400,
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

    Testy.topToolbar.add(showComponentButton);
    Testy.topToolbar.doLayout();
});