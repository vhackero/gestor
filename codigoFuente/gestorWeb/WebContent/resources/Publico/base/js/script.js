/* Scripts gestorWeb */

$(document).ready(function(){
	if($(window).width()>700){
		var mnu = $('.navbar-default.primary, .navbar-inverse.secondary');
		$(window).on("scroll", function(){
			if($(this).scrollTop()>100){
				if(!mnu.hasClass('smaller')){
					mnu.addClass('smaller');
					$('.navbar-inverse.secondary .navbar-nav').addClass('navbar-right');
				}
			}else{
				if(mnu.hasClass('smaller')){
					mnu.removeClass('smaller');
					$('.navbar-inverse.secondary .navbar-nav').removeClass('navbar-right');
				}
			}
		});
	}

	
});
