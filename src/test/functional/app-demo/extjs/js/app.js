Ext.BLANK_IMAGE_URL = 'http://cdn.sencha.com/ext/gpl/3.4.1.1/resources/images/default/s.gif';

/**
 * @class Testy
 * @singleton
 * @type {Object}
 */
var Testy = {

};

Ext.onReady(function(){

    /**
     * @class Testy.topToolbar
     * @type {Ext.Toolbar}
     */
    Testy.topToolbar = new Ext.Toolbar({
        id: 'top-toolbar',
        renderTo: Ext.getBody(),
        buttonAlign: 'center',
        items: [
            {
                text: 'Bootstrap Examples',
                handler: function(){
                    document.location = '../bootstrap/index.html';
                }
            },
            '-'
        ]
    });

});