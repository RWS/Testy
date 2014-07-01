Ext.BLANK_IMAGE_URL = 'http://extjs.cachefly.net/ext-3.4.0/resources/images/default/s.gif';

Ext.onReady(function () {
    var fsf = new Ext.FormPanel({
        frame:true,
        title:'Radio Groups',
        items:[
            {
                xtype:'radiogroup',
                fieldLabel:'Enabled Radio Groups',
                items:[
                    {boxLabel:'Item 1', name:'enabledRadio', inputValue:1},
                    {boxLabel:'Item 2', name:'enabledRadio', inputValue:2},
                    {boxLabel:'Item 3', name:'enabledRadio', inputValue:3},
                    {boxLabel:'Item 4', name:'enabledRadio', inputValue:4},
                    {boxLabel:'Item 5', name:'enabledRadio', inputValue:5}
                ]
            },{
                xtype:'radiogroup',
                disabled:true,
                fieldLabel:'Disabled Radio Groups',
                items:[
                    {boxLabel:'Item 1', name:'disabledRadio', inputValue:1},
                    {boxLabel:'Item 2', name:'disabledRadio', inputValue:2},
                    {boxLabel:'Item 3', name:'disabledRadio', inputValue:3},
                    {boxLabel:'Item 4', name:'disabledRadio', inputValue:4},
                    {boxLabel:'Item 5', name:'disabledRadio', inputValue:5}
                ]
            }
        ]
    });

    new Ext.Button({
        text:'RadioGroups',
        renderTo:Ext.getBody(),
        handler:function () {
            var win = new Ext.Window({
                title:'RadioGroupsWindow',
                layout:'fit',
                width:500,
                height:200,
                plain:true,

                items: fsf,

                buttons:[{
                        text: 'Submit',
                        disabled: true
                    },{
                        text: 'Close',
                        handler: function () {
                            win.close();
                        }
                    }
                ]
            });
            win.show();
        }
    });
});