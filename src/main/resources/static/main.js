/**
 *
 */

$(document).ready(function () {

    $('.table .eBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');

        $.get(href, function (user, status) {
            $('.myForm #id').val(user.id)
            $('.myForm #firstName').val(user.firstName)
            $('.myForm #lastName').val(user.lastName)
            $('.myForm #age').val(user.age)
            $('.myForm #email').val(user.email)
            $('.myForm #password').val(user.password)

        });
        $('.myForm #exampleModal').modal();
    });

    $('.table.delBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');

        $('#myModal #delRef').attr('href', href);
        $('#myModal').modal();
    });
});