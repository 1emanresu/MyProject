/**
* 公共js函数
**/

var yes_img = '<img src="static/images/tips_yes.png" width="16" height="16" />';
var no_img = '<img src="static/images/tips_no.png" width="16" height="16" />';
var illegalCharPatn = /[\&\<>\']/;
var legalCharPatn = /^[\u3000-\u9fff\u0020-\u007e\uff00-\uffef]{2,16}$/;
var domain_patn = /^(http|https):\/\/([\w-]+\.)+((com)|(net)|(org)|(gov\.cn)|(info)|(cc)|(com\.cn)|(net\.cn)|(org\.cn)|(name)|(biz)|(tv)|(cn)|(mobi)|(name)|(sh)|(ac)|   (io)|(tw)|(com\.tw)|(hk)|(com\.hk)|(ws)|(travel)|(us)|(tm)|(la)|(me\.uk)|(org\.uk)|(ltd\.uk)|(plc\.uk)|(in)|(eu)|(it)|(jp))$/;


//js去两端空格函数
function js_trim(str){
  return  str.replace(/^\s+|\s+$/g,"");
}

//判断是否为正整数
function isInt(value){
	if(value.match(/^[0-9]*[1-9][0-9]*$/))return true;
	return false;
}

//获取当前时间
function get_time(){
	return new Date().getTime();
}

//弹出窗口提示
jQuery.extend(jQuery, {
	  // jQuery UI alert弹出提示
	  jqalert: function(text, title, fn) {
	    var html =
	    	'<div class="dialog" id="dialog-confirm">'
			+ '  <p style="font-size:15px;color: red;">'
			+ '    <span class="ui-icon ui-icon-alert" style="float:left; margin: 0 20px 20px 0;"></span>'
			+ text + '  </p>' + '</div>';
	    return $(html).dialog({
	      //autoOpen: false,
	      resizable: false,
	      modal: true,
	      show: {
	        effect: 'fade',
	        duration: 300
	      },
	      title: title || "提示信息",
	      buttons: {
	        "确定": function() {
	          var dlg = $(this).dialog("close");
	          fn && fn.call(dlg);
	        }
	      }      
	    });
	  },
	  jqconfirm: function(text, title, fn1, fn2) {
		    var html =
		    '<div class="dialog" id="dialog-confirm">' +
		    '  <p>' +
		    '    <span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>' + text +
		    '  </p>' +
		    '</div>';
		    return $(html).dialog({
		      //autoOpen: false,
		      resizable: false,
		      modal: true,
		      show: {
		        effect: 'fade',
		        duration: 300
		      },
		      title: title || "提示信息",
		      buttons: {
		        "确定": function() {
		          var dlg = $(this).dialog("close");
		          fn1 && fn1.call(dlg, true);
		        },
		        "取消": function() {
		          var dlg = $(this).dialog("close");
		          fn2 && fn2(dlg, false);
		        }
		      }
		    });
		  }

});