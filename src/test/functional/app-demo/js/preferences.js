$(document).ready(function () {

    var winId = '#preferences-win';

    $(winId).find('.modal-body button').on('click', function () {
        var email = Session.getSessionUser(),
            values = Form.getFormValues(winId),
            $errorEl = $(winId).find('.status-msg');

        $errorEl.show();

        console.info('changing pass', email, values);

        if(DB_MOCKS.getUser(email, values.password)) {
            if(values.newPassword === values.newPasswordRepeat) {
                DB_MOCKS.setPassword(email, values.newPassword);
                $errorEl.html('Your password has been successfully changed.');
            } else {
                $errorEl.html('Password does not match the confirm password!');
            }
        } else {
            $errorEl.html('Your preview password is incorrect!');
        }
    });

});