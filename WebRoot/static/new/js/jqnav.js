$(document).ready(function(){
	m_prod_catalog();


});

function m_prod_catalog(){
	var self = "";
	var self_top = "";
	var pos_nav_offsetTop = "";
	$(".navlist li").hover(
		function(){
			self = $(this);
			self.addClass("hover").children("div").slideDown();
			//slideDown
		},
		function(){
			self = $(this);
			self.children("div").hide();
			//slideUp
			self.removeClass("hover");
		}
	);
};


