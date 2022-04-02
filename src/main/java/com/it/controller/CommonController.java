package com.it.controller;

import com.alibaba.fastjson.JSON;
import com.it.common.Result;
import com.it.exception.ConsumerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * 文件上传和下载通用类
 *
 * @author NightGeneral
 * @date 2022/4/3 0:23
 */

@Slf4j
@RestController
@RequestMapping("common")
public class CommonController {

    @Value("${reggie.dish-picture}")
    private String bashPath;

    /**
     * 文件上传
     *
     * @param file 上传的文件
     * @return 文件名称
     */
    @PostMapping("upload")
    public Result<String> upload(MultipartFile file) {

        log.info("上传图片，入参：{}", file);

        // 判断上传图片大小是否小于 2MB
        if (file.getSize() > 1024 * 1024 * 2) {
            throw new ConsumerException("图片大小不能超过 2MB");
        }

        // 获取文件后缀名
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        // 拼接文件名
        String fileName = UUID.randomUUID().toString() + suffix;
        String absolutePath = bashPath + fileName;

        // 转存文件
        try {
            file.transferTo(new File(absolutePath));
        } catch (IOException e) {
            log.error("上传文件，异常：{}", e.getMessage());
        }

        Result<String> res = Result.success(fileName);

        log.info("上传图片，出参：{}", JSON.toJSONString(res));

        return res;
    }

    /**
     * 文件下载
     *
     * @param name     文件名
     * @param response 响应对象
     */
    @GetMapping("download")
    public void downLoad(String name, HttpServletResponse response) {

        log.info("下载文件，入参：{}", name);

        // 拼接文件的绝对路径
        String absolutePath = bashPath + name;

        // 读取文件
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(absolutePath));
            bos = new BufferedOutputStream(response.getOutputStream());

            response.setContentType("image/jpeg");

            byte[] bytes = new byte[1024 * 1024 * 8];
            int len;

            while ((len = bis.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
                bos.flush();
            }
        } catch (Exception e) {
            log.error("文件下载，异常：{}", e.getMessage());
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    log.error("文件下载，异常：{}", e.getMessage());
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    log.error("文件下载，异常：{}", e.getMessage());
                }
            }
        }
    }
}
