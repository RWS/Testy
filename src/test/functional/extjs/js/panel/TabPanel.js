Ext.BLANK_IMAGE_URL = 'http://extjs.cachefly.net/ext-3.4.0/resources/images/default/s.gif';

Ext.onReady(function(){
    function getNewTabPanel(suffix){
        return new Ext.TabPanel({
            activeTab: 0,
            items: [
                new Ext.Container({
                    title: 'Tab1' + suffix,
                    html: 'tab inside tabs <span class="element">element 00</span>'
                }),
                new Ext.Container({
                    title: 'Tab2' + suffix,
                    html: 'tab inside tabs <span class="element">element 01</span>'
                })
            ]
        });
    }


    new Ext.Button({
        text: 'TabPanel',
        renderTo: Ext.getBody(),
        handler: function(){

            var win = new Ext.Window({
                title: 'TabPanel Win',
                layout:'fit',
                width:900,
                height:500,
                plain: true,
                maximizable : true,

                items: new Ext.TabPanel({
                    activeTab: 0,
                    frame: false,
                    //layout:'fit',
                    border: false,
                    items: [
                        new Ext.Container({
                            title: 'Tab1',
                            items: [
                                new Ext.Panel({
                                    title: 'Panel with frame',
                                    frame: true,
                                    items: [
                                        {
                                            xtype: 'box',
                                            html: 'panel content <span class="element">element 1</span>'
                                        },
                                        getNewTabPanel("1")
                                    ]
                                })
                            ]
                        }),
                        new Ext.Container({
                            title: 'Tab2',
                            items: [
                                new Ext.Panel({
                                    title: 'Panel no frame',
                                    frame: false,
                                    html: 'panel content <span class="element">element 2</span>'
                                })
                            ]
                        }),
                        new Ext.Panel({
                            title: 'Panel no frame2',
                            frame: false,
                            html: 'panel content <span class="element">element 3</span>'
                        }),
                        new Ext.Panel({
                            title: 'Panel with frame2',
                            frame: true,
                            html: 'panel content <span class="element">element 4</span>'
                        }),
                        new Ext.Panel({
                            title: 'x',
                            cls: 'panel-with-no-title',
                            items: [
                                new Ext.Panel({
                                    items: [
                                        new Ext.Panel({
                                            title: 'panel1',
                                            html: 'content1'
                                        }),
                                        new Ext.Panel({
                                            title: 'panel2',
                                            frame: true,
                                            html: 'content2'
                                        })
                                    ]
                                })
                            ]
                        })
                    ]
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