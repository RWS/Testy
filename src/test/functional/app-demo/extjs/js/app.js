Ext.BLANK_IMAGE_URL = 'http://cdn.sencha.com/ext/gpl/3.4.1.1/resources/images/default/s.gif';

/**
 * @class Testy
 * @singleton
 * @type {Object}
 */
var Testy = {

};

Ext.onReady(function(){

    Testy.headerToolbar = new Ext.Toolbar({
        renderTo: Ext.getBody(),
        items: [
            ' ',
            {
                text: 'ExtJS App Example',
                pressed: true,
                handler: function(){
                    document.location = '';
                }
            },
            '-',
            {
                text: 'Bootstrap Examples',
                handler: function(){
                    document.location = '../bootstrap/index.html';
                }
            },
            '->',
            {
                text: 'Logout',
                handler: function(){
                    document.location = '../login.html';
                }
            },
            ' '
        ]
    });

    /**
     * @class Testy.topToolbar
     * @type {Ext.Toolbar}
     */
    Testy.topToolbar = new Ext.Toolbar({
        id: 'top-toolbar',
        renderTo: Ext.getBody(),
        buttonAlign: 'center',
        items: []
    });

});