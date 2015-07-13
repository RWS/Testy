var DB_MOCKS = (function(){

    var PASS_KEY = "DB_MOCK_pass";

    var DB_USERS = {
        'eu@fast.com': {id: 1, pass: 'eu.pass'},
        'tu@fast.com': {id: 2, pass: 'tu.pass'},
        'el@fast.com': {id: 3, pass: 'el.pass'}
    };


    function getPass(userId, defaultPass) {
        // return $.cookie(PASS_KEY + userId) || defaultPass;
        return $.jStorage.get(PASS_KEY + userId, defaultPass);
    }

    function savePass(userId, pass) {
        console.debug('store pass', userId, pass);
        //$.cookie(PASS_KEY + userId, pass, {path: '/'});
        $.jStorage.set(PASS_KEY + userId, pass);
    }

    return {
        getUser: function (email, pass) {
            var user = DB_USERS[email],
                db_pass,
                valid = false;

            if(user) {
                db_pass = getPass(user.id, user.pass);
                valid = db_pass === pass;
            }

            return valid;
        },

        setPassword: function(email, pass) {
            var user = DB_USERS[email];
            if(user) {
                user.pass = pass;
                savePass(user.id, pass);
            }
        }
    };
})();
