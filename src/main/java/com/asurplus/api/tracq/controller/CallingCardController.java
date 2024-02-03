package com.asurplus.api.tracq.controller;

import com.asurplus.api.tracq.service.CallingCardService;
import com.asurplus.api.tracq.vo.CallingCardAskVo;
import com.asurplus.common.layui.LayuiTableResult;
import com.asurplus.common.utils.CommonUtil;
import com.asurplus.common.utils.FileUtil;
import com.asurplus.common.utils.ResponseResult;
import com.asurplus.console.system.entity.UsrCallingCard;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Api(tags = "1名片相关接口")
@RestController()
@RequestMapping("/api/CallingCard")
public class CallingCardController {

    @Resource
    private CallingCardService callingCardService;

    @Value("${sysconfig.file-root-dir}")
    private String uploadFilePath;

    @Autowired
    private FileUtil fileUtil;


    @ApiOperation("文件上传")
    @ResponseBody
    @PostMapping(value = "/filesUpload", headers = "content-type=multipart/form-data")
    public ResponseResult filesUpload(@RequestParam(name = "file") MultipartFile file) throws IOException {
        cn.hutool.core.lang.Assert.isTrue(file != null && !file.isEmpty(), "上传文件不能为空!");
        String ext = FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase(Locale.ROOT);
        if (!ext.equals("jpg") && !ext.equals("jpeg") && !ext.equals("png") && !ext.equals("gif") && !ext.equals("webp")) {
            throw new IllegalArgumentException("文件格式仅支持jpg, jpeg, png, gif, webp");
        }
        // 封面输出资源目录中
        String basePath = uploadFilePath + fileUtil.createFolder();
        File f = new File(basePath,System.currentTimeMillis()+"."+ext);
        f.mkdirs();
        file.transferTo(f);
        // 在新的访问路径上追加时间戳，避免浏览器缓存导致图片不变
        return ResponseResult.success("上传成功","file/access/"+ CommonUtil.encode(f.getAbsolutePath()));

    }

    @GetMapping("getDiscloseList")
    @ApiOperation(value = "分页查询获取公开库列表数据")
  /*  @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "查询第几页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页条数", required = true),
            @ApiImplicitParam(name = "key", value = "名称")})*/
    public LayuiTableResult<List<UsrCallingCard>> getDiscloseList(CallingCardAskVo cardAskVo) {
        return callingCardService.getDiscloseList(cardAskVo);
    }



}
