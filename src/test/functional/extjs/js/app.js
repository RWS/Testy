Ext.BLANK_IMAGE_URL = 'http://extjs.cachefly.net/ext-3.4.0/resources/images/default/s.gif';

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

        ]
    });

});