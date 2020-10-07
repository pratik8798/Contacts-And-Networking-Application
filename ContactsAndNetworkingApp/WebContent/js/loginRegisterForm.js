$(document).ready(function() {
    $('.cta-login').on('click', function() {
        $('#loginForm, .formwrap').addClass('active');
        if (!$('#registrationForm').hasClass('active')) {
            $("body").toggleClass("left", 100);


        } else {
            $('.cta-register').removeClass("colorChange");
            $('#registrationForm, .formwrap').removeClass('active');

        }
        $('.icon-close').addClass('open');
        // $("body").toggleClass("left", 100);
        $('.cta-login').addClass("colorChange");
    });

    $('.cta-register').on('click', function() {
        $('#registrationForm, .formwrap').addClass('active');
        if (!$('#loginForm').hasClass('active')) {
            $("body").toggleClass("left", 100);


        } else {
            $('.cta-login').removeClass("colorChange");
            $('#loginForm, .formwrap').removeClass('active');

        }
        $('.icon-close').addClass('open');
        $('.cta-register').addClass("colorChange");
    });
    $('.icon-close').on('click', function() {
        $('.toggle-form, .formwrap').removeClass('active');
        $('.icon-close').removeClass('open');
        $("body").toggleClass("left", 100);
        $('.cta-register').removeClass("colorChange");
        $('.cta-login').removeClass("colorChange");

    });
});