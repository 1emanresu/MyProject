/*
只允许输入数字
使用方法：
$(#textBox).numeral();	
$(#textBox).numeral({precision:2, min:-100, max:100});
*/
$.fn.numeral = function (options) {
    var defaults = {
        precision: 0,   //保留小数位数
        min: null,      //最小值
        max: null,      //最大值
        onblur: function () { }, //失去焦点事件
        onkeypress: function(){} //键盘输入事件
    };
    var opts = jQuery.extend(defaults, options);

    $(this).css("ime-mode", "disabled");
    this.bind("keypress", function () {
        if (event.keyCode == 46) {
            if (opts.precision <= 0 || this.value.indexOf(".") != -1) {
                return false;
            }
        } else if (event.keyCode == 45) {
            if (opts.min != null && opts.min >= 0) {
                return false;
            } else {
                return this.value.indexOf("-") == -1;
            }
        } else {
            return event.keyCode >= 46 && event.keyCode <= 57;
        }
        opts.onkeypress();
    });
    this.bind("blur", function () {
        if (isNaN(this.value) || this.value == "") {
            this.value = opts.min == null ? "0" : opts.min;
        }
        this.value = parseFloat(this.value);
        if (this.value.indexOf(".") == 0 || this.value.indexOf(".") == this.value.length - 1) {
            this.value = this.value.replace(".", "");
        } else if (this.value.indexOf(".") != -1 && this.value.indexOf(".") < this.value.length - opts.precision - 1) {
            this.value = this.value.substring(0, this.value.indexOf(".") + opts.precision + 1);
        }
        if (opts.min != null && this.value < opts.min) {
            this.value = opts.min;
        } else if (opts.max != null && this.value > opts.max) {
            this.value = opts.max;
        }
        opts.onblur();
    });
    //粘贴事件
    this.bind("paste", function() {
        var s = clipboardData.getData('text');
        if (!/\D/.test(s));
        value = s.replace(/^0*/, '');
        return false;
    });
    //拖入事件
    this.bind("dragenter", function () {
        return false;
    });
    this.bind("keyup", function () {
        if (this.value.length > 1 && this.value.indexOf("-") > 0) {
            this.value = this.value.replace("-", "");
        }
    });
};