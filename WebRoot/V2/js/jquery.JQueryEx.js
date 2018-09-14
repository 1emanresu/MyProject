/*                              
* JS扩展库，需要JQuery支持
*
*  扩展JS字符串内置方法
*      len()        返回字符串的长度（中文占2）
*      hasCN()      检测字符串中是否含有中文（中文占2）
*      trim()       去除首尾空格
*      replaceAll() 替换字符串
*      isCN()       是否是中文
*      isEn()       是否是英文
*      isEmail()    是否是邮箱地址
*      isMobile()   是否是手机号
*      isPhone()    是否是固定电话
*      isZipCode()  是否是邮编
*      isNumber()   是否是数字
*      isEmpty()    是否是空字符串
*      isFax()      是否是传真
*      isUrl()      是否是URL地址
*      isInt()      是否是整数
*      test()       正则匹配
*
*  扩展JS字符串内置方法
*      format() 时间对象的格式化,new Date().format("yyyy-MM-dd hh:mm:ss");  
*
*  扩展JQuery相关方法
*      $.GetStrLen(str)                      获取字符串长度（一个中文字符长度=2）
*      $.HasCN(str)                          检测字符串中是否含有中文
*      $.ForbidRefurbish()                   禁止刷新，回退(F5,←按钮),只要在页面加载完后调用
*      $.ForbidContextmenu()                 禁用鼠标右键,只要在页面加载完后调用
*      $.Center(jObject,keep)                元素在页面绝对居中
*      $.ShowByTag(source,target,autoWidth)  将源对象显示于目标对象下面（一般用于文本框自动完成）
*      $.Alert(msg,time)                     用blockUI插件模拟对话框
*      $.SetPosition(container,elmt,pos,p)   定位元素位置
*      $.ArrUnique(array)                    去除数组中重复元素，并返回新数组
*/

/*
* 增加字符串的len()方法，返回字符串的长度（中文占2）
*/
String.prototype.len = function() {
    return this.replace(/[^\x00-\xff]/g, "**").length;
}
/*
* 替换字符串
*/
String.prototype.replaceAll = function (s1,s2) {
    return this.replace(new RegExp("\\"+s1, "g"), s2);
}
/*
* 增加字符串的hasCN()方法，检测字符串中是否含有中文
*/
String.prototype.hasCN = function() {
    return this.replace(/[^\x00-\xff]/g, "**").length != this.length;
}

/*
* 增加字符串的trim()方法，去除首尾空格
*/
String.prototype.trim = function () {
    return $.trim(this);
}

/*
* 增加字符串的isCN()方法，字符串是否是中文
*/
String.prototype.isCN = function () {
    var p = /^[\u4e00-\u9fa5]+$/;
    return this.test(s, p);
}

/*
* 增加字符串的isEn()方法，字符串是否是英文   
*/
String.prototype.isEn = function () {
    var p = /^[A-Za-z]+$/;
    return this.test(s, p);
}

/*
* 增加字符串的isEmail()方法，字符串是否是邮箱格式
*/
String.prototype.isEmail = function () {
    var p = "^[-!#$%&\'*+\\./0-9=?A-Z^_`a-z{|}~]+@[-!#$%&\'*+\\/0-9=?A-Z^_`a-z{|}~]+\.[-!#$%&\'*+\\./0-9=?A-Z^_`a-z{|}~]+$";
    return this.test(s, p);
}

/*
* 增加字符串的isMobile()方法，字符串是否是手机号码
*/
String.prototype.isMobile = function () {
    return this.test(s, /^(13[0-9]|15[0|3|6|7|8|9]|18[2|8|9])\d{8}$/);
}

/*
* 增加字符串的isPhone()方法，字符串是否是电话号码
*/
String.prototype.isPhone = function () {
    return this.test(s, /^[0-9]{3,4}\-[0-9]{7,8}$/);
}

/*
* 增加字符串的isZipCode()方法，字符串是否是邮编
*/
String.prototype.isZipCode = function () {
    return this.test(s, /^[1-9][0-9]{5}$/);
}

/*
* 增加字符串的isNumber()方法，字符串是否是数字
*/
String.prototype.isNumber = function () {
    return !isNaN(s.nodeType == 1 ? s.value : s) && (!d || !this.test(s, '^-?[0-9]*\\.[0-9]*$'));
}

/*
* 增加字符串的isEmpty()方法，字符串是否是空
*/
String.prototype.isEmpty = function () {
    return !jQuery.isEmptyObject(s);
}

/*
* 增加字符串的isFax()方法，字符串是否是传真号
*/
String.prototype.isFax = function () {
    return this.test(s, /^((\+?[0-9]{2,4}\-[0-9]{3,4}\-)|([0-9]{3,4}\-))?([0-9]{7,8})(\-[0-9]+)?$/);
}

/*
* 增加字符串的isUrl()方法，字符串是否是URL地址
*/
String.prototype.isUrl = function () {
    return this.test(s, /^((http|https):\/\/(\w+:{0,1}\w*@)?(\S+)|)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?$/);
}

/*
* 增加字符串的isInt()方法，字符串是否是整数
*/
String.prototype.isInt = function () {
    return this.test(s, /^\-?[0-9]+$/);
}

/*
* 正则匹配
*/
String.prototype.test = function (s, p) {
    s = s.nodeType == 1 ? s.value : s;
    return new RegExp(p).test(s);
}

/*  
* 时间对象的格式化;  
*/
Date.prototype.format = function(format) {
    /*  
    * eg:format="YYYY-MM-dd hh:mm:ss";  
    */
    var o = {
        "M+": this.getMonth() + 1, // month   
        "d+": this.getDate(), // day   
        "h+": this.getHours(), // hour   
        "m+": this.getMinutes(), // minute   
        "s+": this.getSeconds(), // second   
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter   
        "S": this.getMilliseconds()
        // millisecond   
    }

    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "")
                .substr(4 - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
                    : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}
$.extend({
    /*
    * 获取字符串长度（一个中文字符长度=2）
    * 参数：
    *	   str 要获取长度的字符串
    */
    GetStrLen: function(str) {
        return str.replace(/[^\x00-\xff]/g, "**").length;
    },
    /*
    * 检测字符串中是否含有中文
    * 参数：
    *	   str 要进行检测的字符串
    */
    HasCN: function(str) {
        return str.replace(/[^\x00-\xff]/g, "**").length != str.length;
    },
    /*
    * 禁止刷新，回退(F5,←按钮),只要在页面加载完后调用
    */
    ForbidRefurbish: function() {
        document.onkeydown = function() {
            if ((event.altKey) || ((event.keyCode == 8) && (event.srcElement.type != "text" &&
			        event.srcElement.type != "textarea" && event.srcElement.type != "password")) ||
			        ((event.ctrlKey) && ((event.keyCode == 78) || (event.keyCode == 82))) || (event.keyCode == 116)) {
                event.keyCode = 0;
                event.returnValue = false;
            }
        }
    },
    /*
    * 禁用鼠标右键,只要在页面加载完后调用
    */
    ForbidContextmenu: function() {
        document.oncontextmenu = function() { return false; };
    },
    /*
    * 元素在页面绝对居中
    * 参数：
    *     jObject 要居中的jQuery对象
    *     keep    是否始终保持居中状态(窗体改变大小和滚动条滚动的时候)
    */
    Center: function(jObject, keep) {
        var obj = $(jObject);
        obj.css("position", "absolute");
        var window_Width = document.documentElement.clientWidth;                        //窗口可视区域宽度
        var window_Height = document.documentElement.clientHeight;                      //窗口可视区域高度
        var el_Width = obj.attr("clientWidth");                                         //需要居中的元素的宽度
        var el_Height = obj.attr("clientHeight");                                       //需要居中的元素的高度
        obj.css("left", (window_Width - el_Width) / 2);                                      //左边距
        obj.css("top", document.documentElement.scrollTop + (window_Height - el_Height) / 2); //顶边距
        if (keep == null || keep == true) {
            if (window.onscroll == null) {
                window.onscroll = function() { $.center(jObject, keep); };
                window.onresize = function() { $.center(jObject, keep); };
            }
        }
    },
    /*
    * 将源对象显示于目标对象下面（一般用于文本框自动完成）
    * 参数：
    *      source：需要显示的源对象
    *      target：目标对象ID
    *      autoWidth：是否自动调整源对象的宽度（与目标对象使用相同宽度）
    */
    ShowByTag: function(source, target, autoWidth) {
        var s = $(source);
        var t = $(target);
        var t_top = t.offset().top;
        var t_left = t.offset().left;
        s.css("position", "absolute");
        s.css("top", t_top + t.attr("clientHeight") + 4);
        s.css("left", t_left);
        if (autoWidth == true) {
            s.css("width", t.attr("clientWidth") + 2);
        }
    },
    /*
    * 用blockUI插件模拟对话框
    * 参数：
    *      msg：要输出的消息
    *      time：消息持续时间（多少毫秒后消失）
    */
    Alert: function(msg, time) {
        $.blockUI({
            css: {
                padding: '15px', size: '12.0pt',
                backgroundColor: '#3F73B0', color: '#fff',
                '-webkit-border-radius': '10px', '-moz-border-radius': '10px'
            },
            message: msg
        });
        setTimeout($.unblockUI, time);
    },
    /*
    * 定位元素位置
    * 参数：
    *      container：容纳定位元素的容器
    *      elmt：需要定位的元素
    *      pos：定位方式：TL,TC,TR,BL,BC,BR,LC,RC,C
    *      offset: 偏移量（以免顶边）
    */
    SetPosition: function(container, elmt, pos, p) {
        p = p == null ? 0 : p;
        pos = pos == null ? "TL" : pos;
        var jqContainer = $(container);
        var jqElmt = $(elmt);
        var offset = jqContainer.offset();
        var cw = parseInt(jqContainer.css("width").substring(0, jqContainer.css("width").indexOf("px", 0)));
        var ch = parseInt(jqContainer.css("height").substring(0, jqContainer.css("height").indexOf("px", 0)));
        var ew = parseInt(jqElmt.css("width").substring(0, jqElmt.css("width").indexOf("px", 0)));
        var eh = parseInt(jqElmt.css("height").substring(0, jqElmt.css("height").indexOf("px", 0)));
        jqElmt.css("position", "absolute");
        if (pos == "TL") {        //上左
            jqElmt.css("top", offset.top + p + "px");
            jqElmt.css("left", offset.left + p + "px");
        } else if (pos == "TC") {  //上中
            jqElmt.css("top", offset.top + p + "px");
            jqElmt.css("left", offset.left + parseInt((cw - ew) / 2) + "px");
        } else if (pos == "TR") {  //上右
            jqElmt.css("top", offset.top + p + "px");
            jqElmt.css("left", offset.left + cw - ew - p + "px");
        } else if (pos == "BL") {  //下左
            jqElmt.css("top", offset.top + ch - eh - p + "px");
            jqElmt.css("left", offset.left + p + "px");
        } else if (pos == "BC") {  //中下
            jqElmt.css("top", offset.top + ch - eh - p + "px");
            jqElmt.css("left", offset.left + parseInt((cw - ew) / 2) + "px");
        } else if (pos == "BR") {  //下右
            jqElmt.css("top", offset.top + ch - eh - p + "px");
            jqElmt.css("left", offset.left + cw - ew - p + "px");
        } else if (pos == "LC") {  //左中
            jqElmt.css("top", offset.top + parseInt((ch - eh) / 2) + "px");
            jqElmt.css("left", offset.left + p + "px");
        } else if (pos == "RC") {  //右中
            jqElmt.css("top", offset.top + parseInt((ch - eh) / 2) + "px");
            jqElmt.css("left", offset.left + cw - ew - p + "px");
        } else if (pos == "C") {   //中心            
            jqElmt.css("top", offset.top + parseInt((ch - eh) / 2) + "px");
            jqElmt.css("left", offset.left + parseInt((cw - ew) / 2) + "px");
        }
    },
    /*
    * 去除数组中重复元素，并返回新数组
    * 参数：
    *      array：数组
    */
    ArrUnique: function(array) {
        var ret = new Array();
        try {
            for (var i = 0; i < array.length; i++) {
                var unique = true;
                for (var j = 0; j < ret.length; j++) {
                    if (ret[j].toString() == array[i].toString()) {
                        unique = false;
                        break;
                    }
                }
                if (unique == true) {
                    ret.push(array[i]);
                }
            }
        } catch (e) {
            ret = array;
        }
        return ret;
    }
})