var DB_MOCKS = (function(){

    var DB_USERS = {
        'eu@fast.com': {id: 1, pass: 'eu.pass'},
        'tu@fast.com': {id: 2, pass: 'tu.pass'},
        'el@fast.com': {id: 3, pass: 'el.pass'}
    };

    return {
        getUser: function (user, pass) {
            var result = DB_USERS[user],
                valid = result ? result.pass == pass : false;

            return valid ? result : false;
        }
    };
})();
