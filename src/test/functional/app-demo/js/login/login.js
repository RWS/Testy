$(document).ready(function () {

    $('#loginButton').on('click', function () {
        var $email = $("#email"),
            $pass = $("#password"),
            email = $email.val(),
            pass = $pass.val();

        if(DB_MOCKS.getUser(email, pass)) {
            Session.createSession(email);
            window.location.href = "./bootstrap/index.html";
        } else {
            if(email == '') {
                $('.error-msg').html('Please enter your email!');
            } else if(pass == '') {
                $('.error-msg').html('Please enter your password!');
            } else {
                $pass.val("");
                $('.error-msg').html('Invalid user or password!');
            }
        }
    });

    var users = DB_MOCKS.list(),
        userTable = ['<table border="1" style="border: 1px solid #e5e5e5;">'];

    $.each(users, function(i, user){
        console.debug(user.join('</td><td>'));
        userTable.push('<tr><td>');
        userTable.push(user.join('</td><td>'));
        userTable.push('</td></tr>');
    });
    userTable.push('</table>');

    $('body').append('<div id="logging">' + userTable.join('') + '</div>');
});
