
Ext.onReady(function(){
    Ext.get('mb1').on('click', function(e){
        var box = Ext.MessageBox.wait('Please wait...');
        setTimeout(function(){
            box.hide();
            setTimeout(function(){
                Ext.MessageBox.confirm('Confirm', 'Are you sure you want to do that?');
            }, 2000);
        }, 8000);
    });

    Ext.get('mb2').on('click', function(e){
        Ext.MessageBox.prompt('Name', 'Please enter your name:');
    });

    Ext.get('mb3').on('click', function(e){
        Ext.MessageBox.show({
           title: 'Address',
           msg: 'Please enter your address:',
           width:300,
           buttons: Ext.MessageBox.OKCANCEL,
           multiline: true,
           animEl: 'mb3'
       });
    });

    Ext.get('mb4').on('click', function(e){
        Ext.MessageBox.show({
           title:'Save Changes?',
           msg: 'You are closing a tab that has unsaved changes. <br />Would you like to save your changes?',
           buttons: Ext.MessageBox.YESNOCANCEL,
           animEl: 'mb4',
           icon: Ext.MessageBox.QUESTION
       });
    });

    Ext.get('mb6').on('click', function(){
        Ext.MessageBox.show({
           title: 'Please wait',
           msg: 'Loading items...',
           progressText: 'Initializing...',
           width:300,
           progress:true,
           closable:false,
           animEl: 'mb6'
       });

       // this hideous block creates the bogus progress
       var f = function(v){
            return function(){
                if(v == 12){
                    Ext.MessageBox.hide();
                }else{
                    var i = v/11;
                    Ext.MessageBox.updateProgress(i, Math.round(100*i)+'% completed');
                }
           };
       };
       for(var i = 1; i < 13; i++){
           setTimeout(f(i), i*500);
       }
    });

    Ext.get('mb7').on('click', function(){
        Ext.MessageBox.show({
           msg: 'Saving your data, please wait...',
           progressText: 'Saving...',
           width:300,
           wait:true,
           waitConfig: {interval:200},
           icon:'ext-mb-download',
           animEl: 'mb7'
       });
        setTimeout(function(){
            Ext.MessageBox.hide();
        }, 8000);
    });

    Ext.get('mb8').on('click', function(){
        Ext.MessageBox.alert('Status', 'Changes saved successfully.');
//        var myMask = new Ext.LoadMask(Ext.getBody(), {msg:"Please wait..."});
//        myMask.show();
    });
});