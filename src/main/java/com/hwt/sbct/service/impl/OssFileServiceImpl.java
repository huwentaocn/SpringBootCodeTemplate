package com.hwt.sbct.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwt.sbct.pojo.entity.OssFile;
import com.hwt.sbct.pojo.vo.FileInfo;
import com.hwt.sbct.service.OssFileService;
import com.hwt.sbct.mapper.OssFileMapper;
import com.hwt.sbct.result.Result;
import com.hwt.sbct.result.ResultCodeEnum;
import com.hwt.sbct.until.UrlUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;

/**
 * <p>
 * oss文件表 服务实现类
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-08-31
 */
@Service
public class OssFileServiceImpl extends ServiceImpl<OssFileMapper, OssFile> implements OssFileService {

    @Value("${aliyun.oss.endpoint}")
    private String ENDPOINT;

    @Value("${aliyun.oss.keyId}")
    private String KEY_ID;

    @Value("${aliyun.oss.keySecret}")
    private String KEY_SECRET;

    @Value("${aliyun.oss.bucketName}")
    private String BUCKET_NAME;

    @Value("${aliyun.oss.urlPrefix}")
    private String URL_PREFIX;

    /**
     * 文件上传到oss，返回上传路径
     * @param file
     * @return
     * @throws Exception
     */
    public String uploadToOss(MultipartFile file) throws Exception {
        // 创建OSS实例。
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, KEY_ID, KEY_SECRET);

        //获取上传文件输入流
        InputStream inputStream = file.getInputStream();
        //获取文件名称
        String fileName = file.getOriginalFilename();
        //获取文件md5
        String md5Hex = DigestUtils.md5Hex(file.getInputStream());

        //存储文件名：md5 + 后缀
        String storageFileName = md5Hex + "." + org.springframework.util.StringUtils.getFilenameExtension(fileName);

        //文件路径
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();

        //上传路径
        String uploadPath = year + "/" + month + "/" + day + "/" + storageFileName;

        //调用oss方法实现上传
        //第一个参数  Bucket名称
        //第二个参数  上传到oss文件路径和文件名称   aa/bb/1.jpg
        //第三个参数  上传文件输入流
        ossClient.putObject(BUCKET_NAME, uploadPath, inputStream);

        //需要把上传到阿里云oss路径手动拼接出来
        //  https://edu-guli-1010.oss-cn-beijing.aliyuncs.com/01.jpg
        String url = URL_PREFIX + "/" + uploadPath;

        // 关闭OSSClient。
        ossClient.shutdown();

        return url;
    }

    @Override
    public Result<FileInfo> uploadFile(MultipartFile file) {
        try {
            //获取文件md5
            String md5Hex = DigestUtils.md5Hex(file.getInputStream());

            //查询是否上传过该资源
            LambdaQueryWrapper<OssFile> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(StringUtils.isNotBlank(md5Hex), OssFile::getMd5, md5Hex);
            OssFile ossFile = getOne(queryWrapper);

            //未上传过，直接上传
            if (ossFile == null) {
                //上传oss
                String url = uploadToOss(file);

                //存库
                ossFile = new OssFile();
                ossFile.setFileName(file.getOriginalFilename());
                ossFile.setMd5(md5Hex);
                ossFile.setFileType(file.getContentType());
                ossFile.setFileSize(file.getSize());
                ossFile.setLocation(url.replace(URL_PREFIX, ""));
                ossFile.setAccLocation(url);
                ossFile.setUseCount(1);
            } else {
                //上传过，引用计数+1
                //url失效，重新上传
                if (!UrlUtil.isUrlValid(ossFile.getAccLocation())) {
                    //上传过，但是地址不可用了，重新上传
                    String url = uploadToOss(file);

                    ossFile.setLocation(url.replace(URL_PREFIX, ""));
                    ossFile.setAccLocation(url);
                }
                ossFile.setUseCount(ossFile.getUseCount() + 1);
            }

            //存库
            saveOrUpdate(ossFile);

            FileInfo fileInfo = new FileInfo();
            BeanUtils.copyProperties(ossFile, fileInfo);

            return Result.success(fileInfo);
        } catch (Exception e) {
            log.error("OssFileServiceImpl upload Exception: ==>", e);
            return Result.fail(ResultCodeEnum.SYSTEM_ERROR500, e.getMessage());
        }
    }
}
