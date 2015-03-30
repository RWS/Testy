Ext.onReady(function(){
    var fm = Ext.form;

    var showComponentButton = new Ext.Button({
        text: 'FindElement',
        handler: function(){
            var win = new Ext.Window({
                title: 'Element',
                layout:'fit',
                width:200,
                height:200,
                plain: true,
                buttons: [{
                    text:'Alert',
                    handler: function(){
                        Ext.MessageBox.alert('Alert', 'Alert button was pressed');
                    }
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

    var timeOutField;
    var simple = new Ext.form.FormPanel({
           frame: true,
           title: 'Find Elements after Timeout',
           bodyStyle:'padding:5px 5px 0',
           width: 350,
           items: [
                timeOutField = new Ext.form.NumberField({
                    fieldLabel : 'Timeout',
                    value: 3000
                })
           ],
           buttons: [
               {
                   text: 'Show',
                   handler: function(){
                        setTimeout(function(){
                             Ext.MessageBox.alert('Message', 'Timeout element in ' + timeOutField.getValue());
                         },  timeOutField.getValue());
                   }
               }
           ]
       });
       simple.render(document.body);

       var simple1 = new Ext.form.FormPanel({
           frame: true,
           title: 'Find Elements when contains quotes',
           bodyStyle:'padding:5px 5px 0',
           width: 450,
           buttons: [
               {
                   text: 'Don\'t Accept'
               },{
                   text: 'It was "good" ok!'
               },{
                   text: 'Don\'t do it "now" ok!'
               },{
                   text: 'Don\'"t Accept'
               }
           ]
       });
       simple1.render(document.body);

});