Ext.BLANK_IMAGE_URL = 'http://extjs.cachefly.net/ext-3.4.0/resources/images/default/s.gif';

Ext.onReady(function(){

    // shorthand alias
    var fm = Ext.form;

    function formatDate(value){
        return value ? value.dateFormat('M d, Y') : '';
    }

    function getNewGrid(config){
        // the column model has information about grid columns
        // dataIndex maps the column to the specific data field in
        // the data store (created below)
        var cm = new Ext.grid.ColumnModel({
            // specify any defaults for each column
            defaults: {
                sortable: true // columns are not sortable by default
            },
            columns: [
            {
                id: 'common',
                header: 'Common Name',
                dataIndex: 'common',
                width: 220,
                // use shorthand alias defined above
                editor: new fm.TextField({
                    allowBlank: false
                })
            },
            {
                id: 'botanical',
                header: 'Botanical',
                dataIndex: 'botanical',
                width: 220,
                // use shorthand alias defined above
                editor: new fm.TextArea({
                    allowBlank: false
                })
            },
            {
                header: 'Light',
                dataIndex: 'light',
                width: 130,
                editor: new fm.ComboBox({
                    typeAhead: true,
                    triggerAction: 'all',
                    store: [
                        "Shade",
                        "Mostly Shady",
                        "Sun or Shade",
                        "Mostly Sunny",
                        "Sunny"
                    ],
                    listClass: 'x-combo-list-small'
                })
            }, {
                header: 'Price',
                dataIndex: 'price',
                width: 70,
                align: 'right',
                renderer: 'usMoney',
                editor: new fm.NumberField({
                    allowBlank: false,
                    allowNegative: false,
                    maxValue: 100000
                })
            }, {
                header: 'Available',
                dataIndex: 'availDate',
                width: 95,
                renderer: formatDate,
                editor: new fm.DateField({
                    format: 'm/d/y',
                    minValue: '01/01/06',
                    disabledDays: [0, 6],
                    disabledDaysText: 'Plants are not available on the weekends'
                })
            }, {
                xtype: 'checkcolumn',
                header: 'Indoor?',
                dataIndex: 'indoor',
                width: 55
            }]
        });

        // create the Data Store
        var store = new Ext.data.ArrayStore({
            fields: [
                // the 'name' below matches the tag name to read, except 'availDate'
                // which is mapped to the tag 'availability'
                {name: 'common', type: 'string'},
                {name: 'botanical', type: 'string'},
                {name: 'numb', type: 'string'},
                {name: 'light'},
                {name: 'price', type: 'float'},
                // dates can be automatically converted by specifying dateFormat
                {name: 'availDate', type: 'date', dateFormat: 'm/d/Y'},
                {name: 'indoor', type: 'bool'}
            ],
            sortInfo: {
                field:'common',
                direction:'ASC'
            },
            data: [
                ["Bloodroot", "Sanguinaria canadensis", "4", "Mostly Shady", "2.44", "03/15/2006", true],
                ["Columbine", "Aquilegia canadensis", "3", "Mostly Shady", "9.37", "03/06/2006", true],
                ["Marsh Marigold", "Caltha palustris", "4", "Mostly Sunny", "6.81", "05/17/2006", false],
                ["Cowslip", "Caltha palustris", "4", "Mostly Shady", "9.90", "03/06/2006", true],
                ["Dutchman's-Breeches", "Dicentra cucullaria", "3", "Mostly Shady", "6.44", "01/20/2006", true],
                ["Ginger, Wild", "Asarum canadense", "3", "Mostly Shady", "9.03", "04/18/2006", true],
                ["Hepatica", "Hepatica americana", "4", "Mostly Shady", "4.45", "01/26/2006", true],
                ["Liverleaf", "Hepatica americana", "4", "Mostly Shady", "3.99", "01/02/2006", true],
                ["Jack-In-The-Pulpit", "Arisaema triphyllum", "4", "Mostly Shady", "3.23", "02/01/2006", true],
                ["Mayapple", "Podophyllum peltatum", "3", "Mostly Shady", "2.98", "06/05/2006", true],
                ["Phlox, Woodland", "Phlox divaricata", "3", "Sun or Shade", "2.80", "01/22/2006", false],
                ["Phlox, Blue", "Phlox divaricata", "3", "Sun or Shade", "5.59", "02/16/2006", false],
                ["Spring-Beauty", "Claytonia Virginica", "7", "Mostly Shady", "6.59", "02/01/2006", true],
                ["Trillium", "Trillium grandiflorum", "5", "Sun or Shade", "3.90", "04/29/2006", false],
                ["Wake Robin", "Trillium grandiflorum", "5", "Sun or Shade", "3.20", "02/21/2006", false],
                ["Violet, Dog-Tooth", "Erythronium americanum", "4", "Shade", "9.04", "02/01/2006", true],
                ["Trout Lily", "Erythronium americanum", "4", "Shade", "6.94", "03/24/2006", true],
                ["Adder's-Tongue", "Erythronium americanum", "4", "Shade", "9.58", "04/13/2006", true],
                ["Anemone", "Anemone blanda", "6", "Mostly Shady", "8.86", "12/26/2006", true],
                ["Grecian Windflower", "Anemone blanda", "6", "Mostly Shady", "9.16", "07/10/2006", true],
                ["Bee Balm", "Monarda didyma", "4", "Shade", "4.59", "05/03/2006", true],
                ["Bergamot", "Monarda didyma", "4", "Shade", "7.16", "04/27/2006", true],
                ["Black-Eyed Susan", "Rudbeckia hirta", "Annual", "Sunny", "9.80", "06/18/2006", false],
                ["Buttercup", "Ranunculus", "4", "Shade", "2.57", "06/10/2006", true],
                ["Crowfoot", "Ranunculus", "4", "Shade", "9.34", "04/03/2006", true],
                ["Butterfly Weed", "Asclepias tuberosa", "Annual", "Sunny", "2.78", "06/30/2006", false],
                ["Cinquefoil", "Potentilla", "Annual", "Shade", "7.06", "05/25/2006", true],
                ["Primrose", "Oenothera", "3 - 5", "Sunny", "6.56", "01/30/2006", false],
                ["Gentian", "Gentiana", "4", "Sun or Shade", "7.81", "05/18/2006", false],
                ["Blue Gentian", "Gentiana", "4", "Sun or Shade", "8.56", "05/02/2006", false],
                ["Jacob's Ladder", "Polemonium caeruleum", "Annual", "Shade", "9.26", "02/21/2006", true],
                ["Greek Valerian", "Polemonium caeruleum", "Annual", "Shade", "4.36", "07/14/2006", true],
                ["California Poppy", "Eschscholzia californica", "Annual", "Sunny", "7.89", "03/27/2006", false],
                ["Shooting Star", "Dodecatheon", "Annual", "Mostly Shady", "8.60", "05/13/2006", true],
                ["Snakeroot", "Cimicifuga", "Annual", "Shade", "5.63", "07/11/2006", true],
                ["Cardinal Flower", "Lobelia cardinalis", "2", "Shade", "3.02", "02/22/2006", true]
            ]
        });

        // create the editor grid
        var grid = new Ext.grid.EditorGridPanel(Ext.apply({
            store: store,
            cm: cm,
            autoExpandColumn: 'common', // column with this id will be expanded
            title: 'EditableGrid',
            frame: true,
            clicksToEdit: 1,
            tbar: [{
                text: 'Add Plant',
                handler : function(){
                    // access the Record constructor through the grid's store
                    var Plant = grid.getStore().recordType;
                    var p = new Plant({
                        common: 'New Plant 1',
                        light: 'Mostly Shade',
                        price: 0,
                        availDate: (new Date()).clearTime(),
                        indoor: false
                    });
                    grid.stopEditing();
                    store.insert(0, p);
                    grid.startEditing(0, 0);
                }
            }]
        }, config || {}));

        return grid;
    }

    new Ext.Button({
        text: 'DateField',
        renderTo: Ext.getBody(),
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
                    handler: function(){
                        win.close();
                    }
                }]
            });
            win.show();
        }
    });

//    getNewGrid({
//        height: 400,
//        title: 'MyTitle',
//        tbar: null
//    }).render(Ext.getBody())
});