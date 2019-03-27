let fixmeTop = $('.sidebar-form').offset().top;
$(window).scroll(function() {
    let currentScroll = $(window).scrollTop();
    if (window.matchMedia('(max-width: 850px)').matches){
        return;
    }
    if (currentScroll >= fixmeTop) {
        $('.sidebar-form').css({
            position: 'fixed',
            top: '0',
            left: '0'
        });
    } else {
        $('.sidebar-form').css({
            position: 'static'
        });
    }
});