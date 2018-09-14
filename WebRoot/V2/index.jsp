<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="/www.w3.org/1999/xhtml">
<head><meta http-equiv="Content-Type" content="text/html; charset=utf-8" /><title>
	海阳浩付
</title>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script src="js/jquery.JQueryEx.js" type="text/javascript"></script>
    <script src="js/jquery.numeral.js" type="text/javascript"></script>
    <link type="text/css" href="css/index.css" rel="stylesheet" /><link type="text/css" href="css/header.css" rel="stylesheet" />
    <link type="text/css" href="css/css/chong_menu.20130716.css" rel="stylesheet" /><link rel="stylesheet" type="text/css" href="css/css/chong_mobile.20131021.css" />
    <script type="text/javascript">
        //控制左侧菜单的当前选中项样式（子页面必须有<input id="NavigationIndex" type="hidden" value="1"/>,value为左侧菜单NavigationIndex自定义属性的值）
        $(function () {
            var NavigationIndex = $("#NavigationIndex").val();
            NavigationIndex = NavigationIndex == null ? 1 : NavigationIndex;
            $("li[NavigationIndex=" + NavigationIndex + "]").addClass("t-1");
        })
    </script>
    
    <link rel="stylesheet" type="text/css" href="css/css/chong_mobile.20131021.css"/>
    <script type="text/javascript">
        $(function () {
            //手机号码文本框样式内容切换
            $("#tbtel").focus(function () {
                $(this).addClass("focus");
                $(this).removeClass("unfocus");
                if ($(this).val().trim() == "支持全国移动/联通/电信") {
                    $(this).val("");
                }
            }).blur(function () {
                if ($(this).val().trim() == "") {
                    $(this).val("支持全国移动/联通/电信");
                    $(this).addClass("unfocus");
                    $(this).removeClass("focus");
                }
            })
            //选择充值方式
            $(".attribution").click(function(){
            	$(".attribution").removeClass("cur");
            	$(".deno").removeClass("cur");
            	$("#mobileAutoPrixShow").html("");
            	$(this).addClass("cur");
            	var attribution = $(this).attr("value");
            	if(attribution==1){
            		$("#prixCon1").fadeIn();
            		$("#prixCon2").fadeOut();
            	}else if(attribution==2){
            		$("#prixCon1").fadeOut();
            		$("#prixCon2").fadeIn();
            	}else if(attribution==3){
            		$("#prixCon1").fadeOut();
            		$("#prixCon2").fadeIn();
            	}
            })
            $(".deno").click(function(){
            	$(".deno").removeClass("cur");
            	$(this).addClass("cur");
            	var deno = $(this).attr("value");
            	$("#mobileAutoPrixShow").html("¥"+deno+"元");
            })
        })
        //表单验证
        function chkform() {
        	 var tbtel = $("#tbtel").val();
        	var deno = $(".deno.sqr-sel.cur").attr("value");
            var patten = /^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/;
            if (!patten.exec($("#tbtel").val())) {
                alert("请输入正确的手机号码");
                return false;
            }  else if (typeof(deno) == "undefined") {
                alert("请选择充值面额");
                return false;
            } else {
                return true;
            }
        }
        function __doPostBack() {
        		var deno = $(".deno.sqr-sel.cur").attr("value");
        		$("#cardnum").val(deno);
        		$("#form1").submit();
        }
    </script>
</head>
<body>
    <form method="post" action="<%=basePath %>/phonerecharge/node" id="form1">
<div class="aspNetHidden">
<input type="hidden" name="cardnum" id="cardnum" value="" />
</div>
<div class="aspNetHidden">

	
</div>
    <!----头部------->
    <div class="top">
        <div class="top_nr">
            <div class="top_logo">
                <div class="top_logo_l">
                    <div class="index_logo">
                        <img src="images/logo.gif" width="142" height="45" /></div>
                    <div class="index_wenzi">
                        全国城市综合运营平台</div>
                </div>
                <div class="top_logo_r">
                    <a href="#">我的账户 |</a> <a href="#" target="_blank">注册 |</a> <a href="#">帮助中心 |</a> <a href="#" target="_blank" style="color: #0c6ec4;">
                        自助查单</a> <span>
                        <a href="/e.weibo.com/3194379347/" target="_blank"><img src="images/weibo.gif" width="22" height="17" /></a>
                            
                            <img src="images/weixin.gif" width="22" height="18" /></span></div>
            </div>
            <div class="top_nav">
                <div class="top_nav_l">
                    <ul>
                        <li class="hover"><a href="/TestLogin.aspx">首页</a></li>
                         
                        </li>
                        <!--<li><a href="/join.html">商城</a></li>-->
                        <li><a href="/">优惠</a></li>
                       <li><a href="/zhushou.e138.com">浩付助手</a></li>
                    </ul>
                </div>
                <div class="top_nav_r">
                    商家服务</div>
            </div>
        </div>
    </div>
    <!----banner------->
    <div class="ny_banner"><img src="images/ny_banner.gif" width="100%" height="150" /></div>
    <!----主体------->
    <div class="chanpinfw">
        <div class="chanpinfw_l">
            <div class="menu nav-mobile">
                <!--LFWEBPAGEBEGIN@2415-->
                <ul class="group">
                    <li NavigationIndex="1"><a href="#"><i></i><strong>充话费</strong></a></li>
                    <li NavigationIndex="2" class="t-14"><a href="#"><i></i><strong>充流量</strong></a></li>
                    <li NavigationIndex="3" class="t-2"><a href="#"><i></i><strong>充网游</strong></a></li>
                    <li NavigationIndex="4" class="t-3"><a href="#"><i></i><strong>充Q币</strong></a></li>
                    <li NavigationIndex="5" class="t-4"><a href="#"><i></i><strong>买彩票</strong></a></li>
                    <li NavigationIndex="6" class="t-8"><a href="#"><i></i><strong>买保险</strong></a></li>
                    <li NavigationIndex="7" class="t-5"><a href="#"><i></i><strong>信用卡还款</strong></a></li>
                    <li NavigationIndex="8" class="t-13"><a href="#"><i></i><strong>买理财产品</strong></a></li>
                    <li NavigationIndex="9" class="t-6"><a href="#"><i></i><strong>转账付款</strong></a></li>
                    <li NavigationIndex="10" class="t-7"><a href="#"><i></i><strong>生活缴费</strong></a></li>
                    <li NavigationIndex="11" class="t-12"><a href="#"><i></i><strong>淘QQ靓号</strong></a></li>
                    <li NavigationIndex="12" class="t-10"><a href="#"><i></i><strong>机票酒店</strong></a></li>
                    <li style="height: 30px;"></li>
                </ul>
                <!--LFWEBPAGEEND@2415-->
            </div>
        </div>
        <!--------主体--右-------->
        <div class="cont_r" style="">
            
<input id="NavigationIndex" type="hidden" value="1"/>
<div style="display:none">
        <input id="money" name="money" type="text" />        
        <input id="price" name="price" type="text" />        
        <input id="type" name="type" type="text" />        
        <input id="cardP" name="cardP" type="text" />
    </div>
    <div class="chanpinfw_r">
  <h3><a href="#" class="cHf_ddcx">订单查询</a>快捷 安全 便宜 自动充值1-10分钟到账 </h3>
  <div class="chongQbt">
    <div class="main_jl">
				<!-- 中部业务区 -->
				<div class="bd" id="mobFuncCon">
		
					
					<!--话费充值-->
					<div class="mb-chong-cont group" id="mbChongCon">
						<div class="layout-col-1">
							<div class="chong-mb-form" id="mbForm">
                                    <!-- S 自动充值 -->
                                    <div class="charge-mode" tag="auto">
                                        <div id="divMobileInfo" class="form-line group" style="text-align:center">
                                        </div>
                                        <div class="form-line group">
                                            <label class="attr">
                                                手机号码：</label>
                                            <div class="cont group">
                                                <div class="ipt-container ipt-phonenumber" id="numContainer">
                                                    <span class="ic-l"></span>
                                                    <input id="tbtel" name="tbtel" type="text" class="unfocus" maxlength="11" value="支持全国移动/联通/电信" />
                                                    <span id="ddlCtr" class="btn-show-list" style="cursor:pointer" onclick="$('#hisList').toggle()"></span><span class="confirm-tips hide" id="MobInfo">
                                                    </span><span class="warn-tips hide" id="warnTip"></span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-line has-cb group">
                                            <label class="attr">手机归属：</label>
                                            <div id="chargeType" class="cont group">
                                                <span class="attribution sqr-sel cur" value="1" tag="auto"><span class="ss-r">移动</span> </span>
                                                <span class="attribution sqr-sel" value="2" tag="auto"><span class="ss-r">联通</span> </span>
                                                <span class="attribution sqr-sel" value="3" tag="auto"><span class="ss-r">电信</span> </span>
                                            </div>
                                        </div>
                                        <div class="form-line has-cb group">
                                            <label class="attr">充值类型：</label>
                                            <div id="chargeType" class="cont group">
                                                <span class="sqr-sel cur" value="快充" tag="auto"><span class="ss-r">快充</span> </span>
                                            </div>
                                        </div>
                                        <div class="form-line has-cb group">
                                            <label class="attr">充值面额：</label>
                                            <div class="cont sel-type-1 group" id="prixCon1" role="radiogroup">
                                                <span class="deno sqr-sel" value="1"><span class="ss-r">1元</span></span>
                                                <span class="deno sqr-sel" value="2"><span class="ss-r">2元</span></span>
                                                <span class="deno sqr-sel" value="5"><span class="ss-r">5元</span></span>
                                                <span class="deno sqr-sel" value="10"><span class="ss-r">10元</span></span>
                                                <span class="deno sqr-sel" value="20"><span class="ss-r">20元</span></span>
                                                <span class="deno sqr-sel" value="30"><span class="ss-r">30元</span></span>
                                                <span class="deno sqr-sel" value="50"><span class="ss-r">50元</span></span>
                                                <span class="deno sqr-sel" value="100"><span class="ss-r">100元</span></span>
                                                <span class="deno sqr-sel" value="300"><span class="ss-r">300元</span></span>
                                            </div>
                                            <div class="cont sel-type-1 group" id="prixCon2" role="radiogroup" style="display: none;">
                                                <span class="deno sqr-sel" value="10"><span class="ss-r">10元</span></span>
                                                <span class="deno sqr-sel" value="20"><span class="ss-r">20元</span></span>
                                                <span class="deno sqr-sel" value="30"><span class="ss-r">30元</span></span>
                                                <span class="deno sqr-sel" value="50"><span class="ss-r">50元</span></span>
                                                <span class="deno sqr-sel" value="100"><span class="ss-r">100元</span></span>
                                            </div>
                                        </div>
                                        <div class="price-line group">
                                            <label class="attr">售价：</label>
                                            <div class="cont group">
                                                <strong id="mobileAutoPrixShow"></strong>
                                            </div>
                                        </div>
                                        <div class="btn-line">
                                            <div class="btns group">
                                                <a onclick="return chkform();" id="Content_mobileAutoSubmit" class="btn-chong" href="javascript:__doPostBack()">立即充值</a>
                                                <!-- 点击后添加class:show -->
                                            </div>
                                            <div class="tips hide" id="cardTip">
                                            </div>
                                            <div id="adList">
                                            </div>
                                        </div>
                                    </div>
                                    <!-- E 自动充值 -->
							
							</div>
						</div>
					</div>
				</div>
		  </div> 
  </div>
  <div class="chongQbb">
     <div class="chongQbb_l">
        <div class="chongQbb_l_bt">推荐服务<span>明星服务推荐</span></div>
        <div class="chongQbb_l_nr">
           <div class="chongQbb_l_nr_l"><img src="images/cQb5.gif" width="102" height="102" /></div>
           <div class="chongQbb_l_nr_r">
              <ul>
                <li style="font-size:14px; color:#c9a48c;">发家致富靠双手</li>
                <li style="color:#999;">让打游戏成为一种事业，只是梦想吗？<br />在这里，爱玩游戏也能创造百万财富！不信来看看</li>
                <li><a href="#"><img src="images/cQb6.gif" width="187" height="29" /></a></li>
              </ul>
           </div>
           
        </div>
     </div>
     <div class="chongQbb_r">
        <div class="chongQbb_r_bt">最新活动</div>
        <div class="chongQbb_r_nr"><img src="images/cQb4.gif" width="220" height="120" /></div>
     </div>
  </div>
</div>

        </div>
    </div>
    </div>
    <!-----合作商户------>
    <div class="hezuo_w">
        <div class="hezuo">
            <div class="hezuo_l">
                <div class="hezuo_l_bt">
                    合作商户</div>
                <div class="hezuo_l_nr">
                    <img src="images/shanghu.gif" width="289" height="44" /></div>
            </div>
            <div class="hezuo_c">
                <div class="hezuo_c_bt">
                    合作银行</div>
                <div class="hezuo_c_nr">
                    <img src="images/yinhang.gif" width="234" height="44" /></div>
            </div>
            <div class="hezuo_r">
                <div class="hezuo_r_bt">
                    友情链接</div>
                <div class="hezuo_r_nr">
                    <a href="#">盛付通支付 |</a> <a href="#">支付宝 |</a> <a href="#">中国银联 |</a> <a href="#">财付通
                        |</a> <a href="#">中国银行 |</a> <a href="#">建设银行 |</a> <a href="#">百度搜索 |</a> <a href="#">
                            淘宝网 |</a> <a href="#">讯支付</a> <a href="#" style="color: #2bacef;">更多>></a></div>
            </div>
        </div>
    </div>
    <!----尾部------->
    <div class="footer_w">
        <div class="footer_in">
            <p>
                <a href="#">关于我们 |</a> <a href="#">招聘信息 |</a> <a href="#">在线留言 |</a> <a href="#">联系我们
                    |</a> 客服热线：4000-7118-07未开通地区或海外用户请拨 +86</p>
            <p style="margin-top: 10px; color: #999;">
                Copyright 2013 E138.com All right reserved.海阳浩松网络科技有限公司 版权所有
            </p>
            <p style="color: #999;">
                地址：山东省海阳市凤城旅游度假区曦岛游艇会2509</p>
            <p>
                <img src="images/f1.gif" width="44" height="31" />
                <img src="images/f2.gif" width="32" height="32" />
                <img src="images/f3.gif" width="22" height="23" />
                <img src="images/f4.gif" width="37" height="26" />
                <img src="images/f5.gif" width="53" height="25" />
                <img src="images/f6.gif" width="44" height="29" />
                <img src="images/f7.gif" width="28" height="29" />
                <img src="images/f8.gif" width="83" height="29" />
                <img src="images/f9.gif" width="74" height="29" />
                <img src="images/f10.gif" width="83" height="29" />
            </p>
      </div>
    </div>
    </form>
</body>
</html>
