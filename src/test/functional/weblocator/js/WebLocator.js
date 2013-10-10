(function(){

    $('div').on('click', function(){
        var el = $(this);
        var cls = el.attr('class');
        $('#logger').text($.trim(el.text()) + (cls ? '-' + cls : ''));
    })

})();