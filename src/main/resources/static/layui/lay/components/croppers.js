/*!
 * Cropper v3.0.0
 */

layui.config({
    base: '/static/cropper/' //layui自定义layui组件目录
}).define(['jquery','layer','cropper'],function (exports) {
    var $ = layui.jquery
        ,layer = layui.layer;
    var html = "<div class=\"layui-fluid showImgEdit\" style=\"display: none;padding: 15px 15px\">\n" +
        "    <div class=\"layui-form-item\">\n" +
        "        <div class=\"layui-input-inline layui-btn-container\" style=\"width: auto;\">\n" +
        "            <label for=\"cropper_avatarImgUpload\" class=\"layui-btn layui-btn-primary\">\n" +
        "                <i class=\"layui-icon\" style=\"color: #1890ff\">&#xe67c;</i>选择图片\n" +
        "            </label>\n" +
        "            <input class=\"layui-upload-file\" id=\"cropper_avatarImgUpload\" type=\"file\" value=\"选择图片\" name=\"file\">\n" +
        "        </div>\n" +
        "        <div class=\"layui-form-mid layui-word-aux\">头像的尺寸限定150x150px,大小在50kb以内</div>\n" +
        "    </div>\n" +
        "    <div class=\"layui-row layui-col-space15\">\n" +
        "        <div class=\"layui-col-xs9\">\n" +
        "            <div class=\"readyimg\" style=\"height:450px;border: 1px solid #1890ff;background-color: rgb(247, 247, 247);\">\n" +
        "                <img src=\"\" >\n" +
        "            </div>\n" +
        "        </div>\n" +
        "        <div class=\"layui-col-xs3\">\n" +
        "            <div class=\"img-preview\" style=\"width:200px;height:200px;border: 1px solid #1890ff;overflow:hidden\">\n" +
        "            </div>\n" +
        "        </div>\n" +
        "    </div>\n" +
        "    <div class=\"layui-row layui-col-space15\">\n" +
        "        <div class=\"layui-col-xs9\">\n" +
        "            <div class=\"layui-row\">\n" +
        "                <div class=\"layui-col-xs6\">\n" +
        "                    <button type=\"button\" class=\"layui-btn layui-bg-blue\" cropper-event=\"rotate\" data-option=\"-15\" title=\"向左旋转\">向左旋转</button>\n" +
        "                    <button type=\"button\" class=\"layui-btn layui-bg-blue\" cropper-event=\"rotate\" data-option=\"15\" title=\"向右旋转\">向右旋转</button>\n" +
        "                </div>\n" +
        "                <div class=\"layui-col-xs5\" style=\"text-align: right;\">\n" +
        "                    <button type=\"button\" class=\"layui-btn layui-bg-blue\" cropper-event=\"scale0\" title=\"放大\">放大</button>\n" +
        "                    <button type=\"button\" class=\"layui-btn layui-bg-blue\" cropper-event=\"scale1\" title=\"缩小\">缩小</button>\n" +
        "                    <button type=\"button\" class=\"layui-btn layui-bg-blue\" cropper-event=\"reset\" title=\"重置图片\">重置</button>\n" +
        "                </div>\n" +
        "            </div>\n" +
        "        </div>\n" +
        "        <div class=\"layui-col-xs3\">\n" +
        "            <button class=\"layui-btn layui-bg-blue layui-btn-fluid\" cropper-event=\"confirmSave\" type=\"button\"> 确定</button>\n" +
        "        </div>\n" +
        "    </div>\n" +
        "\n" +
        "</div>";
    var obj = {
        render: function(e){
            $('body').append(html);
            var self = this,
                elem = e.elem,
                saveW = e.saveW,
                saveH = e.saveH,
                mark = e.mark,
                area = e.area,
                url = e.url,
                done = e.done,
                scalex = 1.0,
                scaley = 1.0;

            var content = $('.showImgEdit')
                ,image = $(".showImgEdit .readyimg img")
                ,preview = '.showImgEdit .img-preview'
                ,file = $(".showImgEdit input[name='file']")
                , options = {aspectRatio: mark,preview: preview,viewMode:1};

            // 弹出裁剪框
            $(elem).on('click',function () {
                layer.open({
                    type: 1
                    , content: content
                    , area: area
                    , title: '裁剪上传'
                    , success: function () {
                        image.cropper(options);
                    }
                    , cancel: function (index) {
                        layer.close(index);
                        image.cropper('destroy');
                    }
                });
            });

            // 监听按钮事件
            $(".layui-btn").on('click',function () {
                var event = $(this).attr("cropper-event");
                // 监听确认保存图像
                if(event === 'confirmSave'){
                    image.cropper("getCroppedCanvas",{
                        width: saveW,
                        height: saveH
                    }).toBlob(function(blob){
                        var formData=new FormData();
                        formData.append('files',blob,'head.jpg');
                        $.ajax({
                            method: "post",
                            url: url, //用于文件上传的服务器端请求地址
                            data: formData,
                            processData: false,
                            contentType: false,
                            success:function(res){
                                if(0 == res.code){
                                    layer.msg(res.msg,{icon: 1});
                                    layer.closeAll('page');
                                    return done(res.data.src);
                                }else {
                                    layer.alert(res.msg, {icon: 2});
                                }
                            },
                            error: function () {
                                layer.alert("服务器异常", {icon: 2});
                            }
                        });
                    });
                }
                // 监听旋转
                else if(event === 'rotate'){
                    var option = $(this).attr('data-option');
                    image.cropper('rotate', option);
                }
                // 重设图片
                else if(event === 'reset'){
                    image.cropper('reset');
                }
                // 放大
                else if(event === 'scale0'){
                    scalex = parseFloat(scalex + 0.1);
                    scaley = parseFloat(scaley + 0.1);
                    image.cropper('scale', scalex, scaley);
                }
                // 缩小
                else if(event === 'scale1'){
                    scalex = parseFloat(scalex - 0.1);
                    scaley = parseFloat(scaley - 0.1);
                    image.cropper('scale', scalex, scaley);
                }
                // 文件选择
                file.change(function () {
                    var r = new FileReader();
                    var f = this.files[0];
                    r.readAsDataURL(f);
                    r.onload=function (e) {
                        image.cropper('destroy').attr('src', this.result).cropper(options);
                    };
                });
            });
        }

    };
    exports('croppers', obj);
});