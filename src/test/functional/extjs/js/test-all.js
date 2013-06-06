Ext.onReady(function(){
    Ext.QuickTips.init();
    // http://extjs.cachefly.net/ext-3.4.0/examples/#sample-4
    // simple array store
    var store = new Ext.data.ArrayStore({
        fields: ['abbr', 'state', 'nick'],
        data : [
            ['AL', 'Alabama', 'The Heart of Dixie'],
            ['AK', 'Alaska', 'The Land of the Midnight Sun']
        ]
    });

    var combo1 = new Ext.form.ComboBox({
        store: store,
        displayField:'state',
        typeAhead: true,
        mode: 'local',
        forceSelection: true,
        triggerAction: 'all',
        emptyText:'Select a state...',
        selectOnFocus:true,
        renderTo: Ext.getBody()
    });

    var tb = new Ext.Toolbar();
        tb.render.Ext.getBody();

        tb.add({
                text:'Button w/ Menu',
                iconCls: 'bmenu',  // <-- icon
                menu: menu  // assign menu by instance
            }, {
                text: 'Users',
                iconCls: 'user',
                menu: {
                    xtype: 'menu',
                    plain: true,
                    items: {
                        xtype: 'buttongroup',
                        title: 'User options',
                        autoWidth: true,
                        columns: 2,
                        defaults: {
                            xtype: 'button',
                            scale: 'large',
                            width: '100%',
                            iconAlign: 'left'
                        },
                        items: [{
                            text: 'User<br/>manager',
                            iconCls: 'edit'
                        },{
                            iconCls: 'add',
                            width: 'auto',
                            tooltip: 'Add user'
                        },{
                            colspan: 2,
                            text: 'Import',
                            scale: 'small'
                        },{
                            colspan: 2,
                            text: 'Who is online?',
                            scale: 'small'
                        }]
                    }
                }
            },
            new Ext.Toolbar.SplitButton({
                text: 'Split Button',
                handler: onButtonClick,
                tooltip: {text:'This is a an example QuickTip for a toolbar item', title:'Tip Title'},
                iconCls: 'blist',
                // Menus can be built/referenced by using nested menu config objects
                menu : {
                    items: [{
                        text: '<b>Bold</b>', handler: onItemClick
                    }, {
                        text: '<i>Italic</i>', handler: onItemClick
                    }, {
                        text: '<u>Underline</u>', handler: onItemClick
                    }, '-', {
                        text: 'Pick a Color',
                        handler: onItemClick,
                        menu: {
                            items: [
                                new Ext.ColorPalette({
                                    listeners: {
                                        select: function(cp, color){
                                            Ext.example.msg('Color Selected', 'You chose {0}.', color);
                                        }
                                    }
                                }), '-',
                                {
                                    text: 'More Colors...',
                                    handler: onItemClick
                                }
                            ]
                        }
                    }, {
                        text: 'Extellent!',
                        handler: onItemClick
                    }]
                }
            }), '-', {
            text: 'Toggle Me',
            enableToggle: true,
            toggleHandler: onItemToggle,
            pressed: true
        });

        menu.addSeparator();
        // Menus have a rich api for
        // adding and removing elements dynamically
        var item = menu.add({
            text: 'Dynamically added Item'
        });
        // items support full Observable API
        item.on('click', onItemClick);

        // items can easily be looked up
        menu.add({
            text: 'Disabled Item',
            id: 'disableMe'  // <-- Items can also have an id for easy lookup
            // disabled: true   <-- allowed but for sake of example we use long way below
        });
        // access items by id or index
        menu.items.get('disableMe').disable();

        // They can also be referenced by id in or components
        tb.add('-', {
            icon: 'list-items.gif', // icons can also be specified inline
            cls: 'x-btn-icon',
            tooltip: '<b>Quick Tips</b><br/>Icon only button with tooltip'
        }, '-');

        var scrollMenu = new Ext.menu.Menu();
        for (var i = 0; i < 50; ++i){
            scrollMenu.add({
                text: 'Item ' + (i + 1)
            });
        }
        // scrollable menu
        tb.add({
            icon: 'preview.png',
            cls: 'x-btn-text-icon',
            text: 'Scrolling Menu',
            menu: scrollMenu
        });

        // add a combobox to the toolbar
        var combo = new Ext.form.ComboBox({
            store: store,
            displayField: 'state',
            typeAhead: true,
            mode: 'local',
            triggerAction: 'all',
            emptyText:'Select a state...',
            selectOnFocus:true,
            width:135
        });
        tb.addField(combo);

        tb.doLayout();

});