//验证码
;(function($, window, document,undefined) {

    //定义Code的构造函数
    var Code = function(ele, opt) {
        this.$element = ele,
            this.defaults = {
                figure : 10,	//位数
                btnId : 'login', //点击确认的按钮ID
                ready : function(){},
                success : function(){},
                error : function(){}
            },
            this.options = $.extend({}, this.defaults, opt)
    };

    var _code_color1 = ['#fffff0', '#f0ffff', '#f0fff0', '#fff0f0'];
    var _code_color2 = ['#FF0033', '#006699', '#993366', '#FF9900', '#66CC66', '#FF33CC'];

    //定义Code的方法
    Code.prototype = {
        init : function() {
            // 成员变量
            var _this = this;
            // 加载页面
            this.loadDom();
            // 设置验证码
            this.setCode();
            // 开启监听
            this.options.ready();

            this.$element[0].onselectstart = document.body.ondrag = function(){
                return false;
            };

            //监听验证码点击事件
            this.$element.find('.verify-code, .verify-change-code').on('click', function() {
                _this.setCode();
            });

            //监听确认按钮点击事件
            this.htmlDoms.code_btn.on('click', function() {
                _this.checkCode();
            })

        },

        //加载页面
        loadDom : function() {

            this.isEnd = false;

            this.htmlDoms = {
                code_btn : $('#'+this.options.btnId), //点击确定的按钮
                code : this.$element.find('.verify-code'), //验证码图片
                code_input : this.$element.find('.verify-code-input'), //验证码输入框
            };
        },


        //设置验证码
        setCode : function() {
            if(this.isEnd == false) {
                // 随机颜色
                let color1Num = Math.floor(Math.random() * 3);
                let color2Num = Math.floor(Math.random() * 5);
                // 设置css
                this.htmlDoms.code.css({'background-color': _code_color1[color1Num], 'color': _code_color2[color2Num]});
                this.htmlDoms.code_input.val('');
                // 验证码返回的字符串
                let code = '';
                // 验证码答案
                this.code_chose = '1000';

                //算法验证码
                // 两个随机数
                let num1 = Math.floor(Math.random() * this.options.figure);
                let num2 = Math.floor(Math.random() * this.options.figure);
                // 计算方法，随机生成
                let tmparith = Math.floor(Math.random() * 3);
                // 根据随机生成的数值选取对应的计算方法，
                switch(tmparith) {
                    case 1 :
                        this.code_chose = parseInt(num1) + parseInt(num2);
                        code = num1 + ' + ' + num2 + ' = ?';
                        break;
                    case 2 :
                        if(parseInt(num1) < parseInt(num2)) {
                            var tmpnum = num1;
                            num1 = num2;
                            num2 = tmpnum;
                        }
                        this.code_chose = parseInt(num1) - parseInt(num2);
                        code = num1 + ' - ' + num2 + ' = ?';
                        break;
                    default :
                        this.code_chose = parseInt(num1) * parseInt(num2);
                        code = num1 + ' × ' + num2 + ' = ?';
                        break;
                }

                this.htmlDoms.code.text(code);
            }
        },

        //比对验证码
        checkCode : function() {
            var own_input = this.htmlDoms.code_input.val();

            if(own_input == this.code_chose) {
                this.isEnd = true;
                this.options.success(this);
            }else {
                this.options.error(this);
                this.setCode();
            }
        },
    };

    //在插件中使用codeVerify对象
    $.fn.codeVerify = function(options, callbacks) {
        var code = new Code(this, options);
        code.init();
    };

})(jQuery, window, document);

//展示loading
function g_showLoading(){
    let idx = layer.msg('玩命加载中...', {icon: 16,shade: [0.5, '#f5f5f5'],scrollbar: false,offset: '0px', time:100000}) ;
    return idx;
}
//salt
var g_passsword_salt="1a2b3c4d"
// 获取url参数
function g_getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r != null) return unescape(r[2]);
    return null;
};
//设定时间格式化函数，使用new Date().format("yyyyMMddhhmmss");
Date.prototype.format = function (format) {
    var args = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
    };
    if (/(y+)/.test(format))
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (const i in args) {
        const n = args[i];
        if (new RegExp("(" + i + ")").test(format))
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? n : ("00" + n).substr(("" + n).length));
    }
    return format;
};
// 刷新验证码 模拟点击事件
function updateVerifyCode(){
    $('.verify-code').trigger("click")
}
