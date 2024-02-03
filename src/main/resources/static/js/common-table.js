/**
 * 搜索信息，展开，收起
 */
$('legend').click(function () {
    $('#search-form').slideToggle('1000');
});

/**
 * 图片展示
 */
$('body').on('click', '.avatar-show', function () {
    var url = $(this).attr('src');
    if (!!url) {
        var r = '';
        r += '<div style="width: 100%; height: 100%; position: relative;">';
        r += '<img src="' + url + '" style="position: absolute; left: 0; right: 0; top: 0; bottom: 0; max-height: 100%; max-width: 100%; margin: auto;">';
        r += '</div>';
        layer.open({
            type: 1,
            title: '图片预览',
            area: ['400px', '600px'],
            content: r,
            shadeClose: true
        });
    }
});

/**
 * 去掉字符串的HTML标签
 */
function filterHTMLTag(str) {
    var msg = str.replace(/<\/?[^>]*>/g, '');
    msg = msg.replace(/[|]*\n/, '');
    msg = msg.replace(/&nbsp;/ig, '');
    return msg;
}

/**
 * 获取url中的参数
 */
function getUrlParam(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (let i = 0; i < vars.length; i++) {
        let pair = vars[i].split("=");
        if (pair[0] == variable) {
            return pair[1];
        }
    }
    return ('');
}

/**
 * 判断是否是图片
 */
function isPicture(suffix) {
    var flag = false;
    if(!suffix){
        return flag;
    }
    var arr = ["bmp", "dib", "gif", "jfif", "jpe", "jpeg", "jpg", "png", "tif", "tiff", "ico"];
    let number = arr.indexOf(suffix);
    // -1 表示没找到
    if(-1 != number){
        return true;
    }
    return flag;
}

var layer;
var notice;
var table;
var form;
var util;
var nprogress;
/**
 * 加载Layui常用组件
 */
layui.use(['layer', 'notice', 'form', 'util', 'nprogress'], function () {
    layer = layui.layer;
    notice = layui.notice;
    form = layui.form;
    util = layui.util;
    nprogress = layui.nprogress;


    /**
     * 页面加载进度条
     */
    nprogress.start();
    if (document.readyState == "complete" || document.readyState == "interactive") {
        // 进度条结束
        nprogress.done();
    }

    /**
     * 回到顶部
     */
    util.fixbar({});

    /**
     * 鼠标移入，显示弹窗
     */
    var tipsIndex;
    $('body').on('mouseover', 'tbody .layui-table-cell', function () {
        var that = this;
        if (!$(this).hasClass('laytable-cell-numbers') && 0 == $(this).find('.toolbar_span').length && !!filterHTMLTag($(this).html())) {
            tipsIndex = layer.tips(filterHTMLTag($(this).html()), that, {tips: [1, '#1890ff'], time: 10000});
        }
    });

    /**
     * 鼠标移出，关闭弹窗
     */
   $('body').on('mouseout', 'tbody .layui-table-cell', function () {
        if (!$(this).hasClass('laytable-cell-numbers') && 0 == $(this).find('.toolbar_span').length && !!filterHTMLTag($(this).html())) {
            layer.close(tipsIndex);
        }
    });

    /**
     * 点击复制
     */
    /*var copyIndex;
    $('body').on('click', 'tbody .layui-table-cell', function () {
        if (!$(this).hasClass('laytable-cell-numbers') && 0 == $(this).find('.toolbar_span').length && !!filterHTMLTag($(this).html())) {
            // 内容
            var text = filterHTMLTag($(this).html());
            // 创建一个input，赋值，选中，赋值，删除input
            var oInput = document.createElement('input');
            oInput.value = text;
            $(oInput).css({opacity: '0'});
            $(oInput).attr({name: "__copy_secukey"});
            document.body.appendChild(oInput);
            // 选择对象
            oInput.select();
            // 复制
            document.execCommand("Copy");
            oInput.className = 'oInput';
            // 删除元素
            $("input[name='__copy_secukey']").remove();
            copyIndex = layer.msg('已复制', {offset: 't'});
        }
    });*/
    /**
     * 双击复制表格内容
     */
    let copyIndex;
    $('body').on('dblclick', 'tbody .layui-table-cell', function () {
        if (!$(this).hasClass('laytable-cell-numbers') && 0 == $(this).find('.toolbar_span').length && !!filterHTMLTag($(this).html())) {
            // 内容
            let text = filterHTMLTag($(this).html());
            // 创建一个input，赋值，选中，赋值，删除input
            let oInput = document.createElement('input');
            oInput.value = text;
            $(oInput).css({opacity: '0'});
            $(oInput).attr({name: "__copy_secukey"});
            document.body.appendChild(oInput);
            // 选择对象
            oInput.select();
            // 复制
            document.execCommand("Copy");
            oInput.className = 'oInput';
            // 删除元素
            $("input[name='__copy_secukey']").remove();
            copyIndex = layer.msg('已复制', {offset: 't'});
        }
    });
});

/**
 * Ajax请求
 */
const Asurplus = {
    /**
     * GET 请求
     */
    get(options, succback, errback) {
        if (!options.url) {
            notice.error('请求错误');
            return false;
        }
        options.type = 'GET'
        options.timeout = options.timeout || 5000
        options.async = options.async || true
        options.cache = options.cache || false
        options.dataType = options.dataType || 'json'
        options.contentType = options.contentType || 'application/json'
        options.succMsg = options.succMsg || false
        options.errMsg = options.errMsg || false
        var index = layer.load(2, {shade: 0.1});
        $.ajax({
            url: options.url,
            type: options.type,
            timeout: options.timeout,
            async: options.async,
            cache: options.cache,
            dataType: options.dataType,
            success: function (res) {
                layer.close(index);
                if (200 === res.code) {
                    layer.closeAll('dialog');
                    if (!options.succMsg) {
                        notice.success(res.msg);
                    }
                    // 成功回调
                    if (!!succback) {
                        succback(res);
                    }
                } else {
                    if (!options.errMsg) {
                        notice.warning(res.msg);
                    }
                    // 失败回调
                    if (!!errback) {
                        errback(res);
                    }
                }
            },
            error: function (res) {
                layer.close(index);
                if(res.status == 405){
                    notice.error("参数验证错误！");
                }else if(res.status == 400){
                    notice.error(res.responseJSON.msg);
                }else {
                    notice.error("服务器异常！");
                }
            }
        });
    },

    /**
     * POST 请求
     */
    doGet(options, succback, errback) {
        if (!options.url) {
            notice.error('请求错误');
            return false;
        }
        options.type = 'GET'
        options.timeout = options.timeout || 5000
        options.async = options.async || true
        options.cache = options.cache || false
        options.dataType = options.dataType || 'json'
        options.contentType = options.contentType || 'application/x-www-form-urlencoded'
        options.data = options.data || ''
        options.succMsg = options.succMsg || false
        options.errMsg = options.errMsg || false
        var index = layer.load(2, {shade: 0.1});
        $.ajax({
            url: options.url,
            type: options.type,
            timeout: options.timeout,
            async: options.async,
            cache: options.cache,
            dataType: options.dataType,
            data: options.data,
            contentType: options.contentType,
            success: function (rep) {
                layer.close(index);
                if (200 === rep.code) {
                    layer.closeAll('dialog');
                    // 成功回调
                    if (!!succback) {
                        succback(rep);
                    }
                } else {
                    if(!!errback){
                        errback(rep);
                    } else {
                        layer.msg(rep.msg, {icon: 7});
                    }
                }
            },
            error: function (rep) {
                layer.close(index);
                if(rep.status == 405){
                    layer.msg("参数验证错误！", {icon: 7});
                }else if(rep.status == 400){
                    layer.msg(rep.responseJSON.msg, {icon: 7});
                }else {
                    layer.msg("网络连接异常，请刷新页面重试！", {icon: 2});
                }
            }
        });
    },
    /**
     * POST 请求
     */
    post(options, succback, errback) {
        if (!options.url) {
            notice.error('请求错误');
            return false;
        }
        options.type = 'POST'
        options.timeout = options.timeout || 5000
        options.async = options.async || true
        options.cache = options.cache || false
        options.dataType = options.dataType || 'json'
        options.contentType = options.contentType || 'application/json'
        options.data = options.data || ''
        options.succMsg = options.succMsg || false
        options.errMsg = options.errMsg || false
        var index = layer.load(2, {shade: 0.1});
        $.ajax({
            url: options.url,
            type: options.type,
            timeout: options.timeout,
            async: options.async,
            cache: options.cache,
            dataType: options.dataType,
            data: options.data,
            contentType: options.contentType,
            success: function (res) {
                layer.close(index);
                if (200 === res.code) {
                    layer.closeAll('dialog');
                    if (!options.succMsg) {
                        notice.success(res.msg);
                    }
                    // 成功回调
                    if (!!succback) {
                        succback(res);
                    }
                } else {
                    if (!options.errMsg) {
                        notice.warning(res.msg);
                    }
                    // 失败回调
                    if (!!errback) {
                        errback(res);
                    }
                }
            },
            error: function (res) {
                layer.close(index);
                if(res.status == 405){
                    notice.error("参数验证错误！");
                }else if(res.status == 400){
                    notice.error(res.responseJSON.msg);
                }else {
                    notice.error("服务器异常！");
                }
            }
        });
    },

    /**
     * POST 请求
     */
    doPost(options, succback, errback) {
        if (!options.url) {
            notice.error('请求错误');
            return false;
        }
        options.type = 'POST'
        options.timeout = options.timeout || 5000
        options.async = options.async || true
        options.cache = options.cache || false
        options.dataType = options.dataType || 'json'
        options.contentType = options.contentType || 'application/x-www-form-urlencoded'
        options.data = options.data || ''
        options.succMsg = options.succMsg || false
        options.errMsg = options.errMsg || false
        var index = layer.load(2, {shade: 0.1});
        $.ajax({
            url: options.url,
            type: options.type,
            timeout: options.timeout,
            async: options.async,
            cache: options.cache,
            dataType: options.dataType,
            data: options.data,
            contentType: options.contentType,
            success: function (rep) {
                layer.close(index);
                if (200 === rep.code) {
                    layer.closeAll('dialog');
                    // 成功回调
                    if (!!succback) {
                        succback(rep.data);
                    }
                } else {
                    if(!!errback){
                        errback(rep);
                    } else {
                        layer.msg(rep.msg, {icon: 7});
                    }
                }
            },
            error: function (rep) {
                layer.close(index);
                if(rep.status == 405){
                    layer.msg("参数验证错误！", {icon: 7});
                }else if(rep.status == 400){
                    layer.msg(rep.responseJSON.msg, {icon: 7});
                }else {
                    layer.msg("网络连接异常，请刷新页面重试！", {icon: 2});
                }
            }
        });
    },
    /**
     * 上传文件
     */
    upload(options, succback, errback) {
        if (!options.url) {
            notice.error('请求错误');
            return false;
        }
        options.type = options.type || 'POST'
        options.timeout = options.timeout || 5000
        options.async = options.async || true
        options.cache = options.cache || false
        options.dataType = options.dataType || 'json'
        options.data = options.data || ''
        options.dir = options.dir || ''
        options.processData = options.processData || false
        options.contentType = options.contentType || false
        options.succMsg = options.succMsg || false
        options.errMsg = options.errMsg || false
        var index = layer.load(2, {shade: 0.1});
        $.ajax({
            url: options.url,
            type: options.type,
            cache: options.cache,
            dataType: options.dataType,
            data: options.data,
            headers: {'Upload-dir': options.dir},
            processData: options.processData,
            contentType: options.contentType,
            success: function (res) {
                layer.close(index);
                if (200 === res.code) {
                    if (!options.succMsg) {
                        // notice.success(res.msg);
                    }
                    // 成功回调
                    if (!!succback) {
                        succback(res);
                    }
                } else {
                    if (!options.errMsg) {
                        notice.warning(res.msg);
                    }
                    // 失败回调
                    if (!!errback) {
                        errback(res);
                    }
                }
            },
            error: function (res) {
                layer.close(index);
                if(res.status == 405){
                    notice.error("参数验证错误！");
                }else if(res.status == 400){
                    notice.error(res.responseJSON.msg);
                }else {
                    notice.error("服务器异常！");
                }
            }
        });
    },
    /**
     * 渲染表格
     */
    tableRender(options) {
        if (!options.url) {
            notice.error('请求错误');
            return false;
        }
        if (!options.table) {
            notice.error('请求错误');
            return false;
        }
        table = options.table
        options.elem = options.elem || '#currentTableId'
        options.id = options.id || 'currentTableId'
        options.method = options.method || 'POST'
        options.toolbar = options.toolbar || '#toolbarDemo'
        options.height = options.height || $(document).height() - $('.table-search-fieldset').height() - 80
        options.defaultToolbar = options.defaultToolbar || ['filter', 'exports', 'print', {
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }]
        options.limits = options.limits || [2,10, 15, 20, 25, 50, 100]
        options.limit = options.limit || 20
        //判断是否开起分页
        if(options.page == false){
            options.page = false;
        }else {
            options.page = options.page ? options.page : true
        }

        //页面为空则重新执行搜索
        if($("#toolbarDemo").html() == ''){
            // 执行搜索重载
            table.reload('currentTableId', {
                page: {
                    curr: 1
                }
                , where: form.val("search-form")
            }, 'data');
        }
        /**
         * 表格搜索操作
         */
        form.on('submit(data-search-btn)', function (data) {
            // 执行搜索重载
            table.reload('currentTableId', {
                page: {
                    curr: 1
                }
                , where: form.val("search-form")
            }, 'data');
            return false;
        });

        /**
         * 表格重置搜索
         */
        form.on('submit(data-reset-btn)', function (data) {
            // 清空
            $("#search-form")[0].reset();
            // 执行搜索重载
            table.reload('currentTableId', {
                page: {
                    curr: 1
                }
                , where: form.val("search-form")
            }, 'data');
            return false;
        });

        /**
         * 表格行单击事件
         */
        table.on('row(currentTableFilter)', function (obj) {
            // 标注选中样式
            obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
        });
        /**
         * 渲染表格
         */
        return options.table.render({
            elem: options.elem,
            url: options.url,
            method: options.method,
            toolbar: options.toolbar,
            height: options.height,
            defaultToolbar: options.defaultToolbar,
            where: options.where,
            cols: options.cols,
            limits: options.limits,
            limit: options.limit,
            page: options.page,
            done: options.done,

        })
    },
    /**
     * 打开新增、修改页面
     */
    openLayer(options, callback) {
        var bool = true;
        if (!options.content) {
            notice.error('请求错误');
            return false;
        }
        options.type = options.type || 2
        options.title = options.title || '新页面'
        options.shadeClose = options.shadeClose || false
        options.shade = options.shade || 0.5
        options.area = options.area || ['45%', '100%']
        options.offset = options.offset || 'r'
        options.anim = options.anim || 2
        options.success = options.success || null
        layer.open({
            type: options.type,
            title: options.title,
            shadeClose: options.shadeClose,
            shade: options.shade,
            area: options.area,
            content: options.content,
            offset: options.offset,
            anim: options.anim,
            success: options.success,
            cancel: function(index, layero){
                bool = false;
            },
            end: function (index, layero) {
                /*if(bool){
                    if (!!callback) {
                        callback();
                    }
                }*/

                if (!!callback) {
                    callback();
                }

            }
        });
    },
    /**
     * 打开新增、修改页面
     */
    openAllLayer(options, callback) {
        if (!options.content) {
            notice.error('请求错误');
            return false;
        }
        options.type = options.type || 2
        options.title = options.title || '新页面'
        options.shadeClose = options.shadeClose || false
        options.shade = options.shade || 0.5
        options.area = options.area || ['100%', '100%']
        options.offset = options.offset || 'r'
        options.anim = options.anim || 2
        layer.open({
            type: options.type,
            title: options.title,
            shadeClose: options.shadeClose,
            shade: options.shade,
            area: options.area,
            content: options.content,
            offset: options.offset,
            anim: options.anim,
            end: function (index, layero) {
                if (!!callback) {
                    callback();
                }
            }
        });
    }
}