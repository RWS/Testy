
Ext.onReady(function(){

    function pressButton(button){
         setTimeout(function(){
            Ext.MessageBox.alert('Alert', button.getText() + ' button was pressed');
         }, (1 + Math.round(Math.random(2)*2)) * 1000);
    }

    var simple = new Ext.Panel({
        frame: true,
        title: 'Condition Manager',
        bodyStyle:'padding:5px 5px 0',
        width: 350,
        buttons: [
            {
                text: 'Expect1',
                handler: pressButton
            },{
                text: 'Expect2',
                handler: pressButton
            },{
                text: 'Expect3',
                handler: function(){
                     Ext.MessageBox.wait('Please wait...');
                     setTimeout(function(){
                         setTimeout(function(){
                             Ext.MessageBox.confirm('Confirm', 'Are you sure you want to do that?');
                         },  Math.round(1+Math.random(2)*2) * 1000);
                     },  Math.round(1+Math.random(2)*5) * 1000);
                 }
            }, {
                text: 'Instant Message',
                handler: function(button){
                    Ext.MessageBox.alert('Alert', button.getText() + ' button was pressed');
                }
            }
        ]
    });
    simple.render(document.body);
});