var SESSION_USER_KEY = 'loggedInUser';

var Session = {
    invalidate: function() {
        console.debug('invalidate session');
        //$.removeCookie(SESSION_USER_KEY);
        $.jStorage.deleteKey(SESSION_USER_KEY);
    },

    createSession: function(email) {
        //$.cookie(SESSION_USER_KEY, email);
        $.jStorage.set(SESSION_USER_KEY, email);
        $.jStorage.setTTL(SESSION_USER_KEY, 5*60*1000); // expires in 5 min
    },

    getSessionUser: function() {
        //$.cookie(SESSION_USER_KEY);
        return $.jStorage.get(SESSION_USER_KEY);
    }
};

var Form = {

    getFormValues: function(selector) {
        var $elems = $(selector).find('input[name],select[name],textarea[name]'),
            values = {};
        $.each($elems, function(index, obj) {
            values[obj.name] = obj.value;
        });
        return values;
    },

    /**
     * example:
     * <pre>
     *      Utils.setFormValues('#form', {from: 'From',to: 'TO'});
     * </pre>
     * @param selector
     * @param values Object (Json)
     */
    setFormValues: function(selector, values) {
        //console.debug('set form values', selector, values);
        selector = $(selector);
        $.each(values || {}, function(name, value){
            var $field = $('[name="' + name + '"]', selector);
            switch ($field.attr('type')) {
                case 'radio' :
                case 'checkbox':
                    $field.each(function () {
                        var $el = $(this);
                        if ($el.attr('value') == value) {
                            $el.prop('checked', value);
                        }
                    });
                    break;
                default:
                    $field.val(value);
            }
        });
    },

    setError: function(idElem, message) {
        return '<div class="error" for="' + idElem + '">' + message + '</div>';
    }
};