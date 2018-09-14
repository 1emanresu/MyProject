$(document).ready(function(){
	m_prod_catalog();
	liho();

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


function liho(){
	var self = "";
	$(".joblist2 i").click(
		function(){
			$(".joblist2 li").removeClass("hover");
			self = $(this);
			self.parents(".joblist2 li").addClass("hover");
			//slideDown
		}
	);
};

function wjlihover(){
	var self = "";
	$(".lihover li").hover(
		function(){
			self = $(this);
			self.addClass("hover");
		},
		function(){
			self = $(this);
			self.removeClass("hover");
		}
	);
};