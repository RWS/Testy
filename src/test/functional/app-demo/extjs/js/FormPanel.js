Ext.onReady(function(){

    Ext.QuickTips.init();

    Ext.form.Field.prototype.msgTarget = 'side';

    var bd = Ext.getBody();
    bd.createChild({tag: 'h2', html: 'Field Components'});

    var simple = new Ext.FormPanel({
        labelWidth: 75,
        frame: true,
        title: 'Simple Form',
        bodyStyle:'padding:5px 5px 0',
        width: 480,
        defaults: {width: 230},

        items: [{
            xtype:'displayfield',
            name: 'displayField',
            value: 'DisplayFieldValue'
        },{
            xtype: 'datefield',
            name: 'dateField',
            editable : false,
            format: 'd/m/Y',
            minValue: '01/01/06'
        }, new Ext.Button({
            style:{
                fontSize:12,
                marginLeft: "100px"
            },
            html:'<a href="#" style="color: blue">Download File</a>',
            handler: function() {
                window.location.href = '../../../resources/upload/text.docx';
            }
        }), {
            xtype: 'fileuploadfield',
            id: 'form-file',
            emptyText: 'Select a file',
            fieldLabel: 'Upload File',
            name: 'photo-path',
            buttonText: 'Browse'
        }, {
            xtype: 'checkbox',
            fieldLabel: '',
            labelSeparator: '',
            boxLabel: 'CatRight'
        }, {
            xtype: 'checkbox',
            fieldLabel: 'CatLeft'
        }
        ],

        buttons: [{
            text: 'Download',
            handler: function() {
                window.location.href = '../../../resources/upload/text.docx';
            }
        }, {
            text: 'Download with spaces',
            handler: function() {
                window.location.href = '../../../resources/upload/text t.docx';
            }
        },{
            text: 'Stress',
            id: 'stress'
        },{
            text: 'Don\'\"t Accept'
        },{
            text: 'Cancel'
        }]
    });

    simple.render(document.body);

    // TODO find what is the reason for this test
    setInterval( function (){
        var stressButton = Ext.get('stress');
        if(stressButton){
            stressButton.hide();
            console.debug("test");
        } else {
             stressButton.show();
        }
    }, 1000);
});