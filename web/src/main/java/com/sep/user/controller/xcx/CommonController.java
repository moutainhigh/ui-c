package com.sep.user.controller.xcx;

import com.sep.common.model.response.ResponseData;
import com.sep.sku.tool.TencentCos;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/xcx/common")
@Api(value = "文件上传相关API", tags = {"文件上传相关API"})
public class CommonController {


    @Autowired
    TencentCos tencentCos;


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<String> Upload(@RequestParam(value = "file") MultipartFile file, HttpSession session) throws IOException {
        if (file == null) {
            ResponseData.ERROR();
        }
        String oldFileName = file.getOriginalFilename();
        String eName = oldFileName.substring(oldFileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID() + eName;
        System.out.println("wenjianming---->" + newFileName);
        String path = tencentCos.SimpleUploadFileFromStream(newFileName, file.getInputStream());
        return ResponseData.OK(path);
    }


}
