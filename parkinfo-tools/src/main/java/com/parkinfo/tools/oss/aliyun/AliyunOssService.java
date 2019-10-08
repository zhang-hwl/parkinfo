package com.parkinfo.tools.oss.aliyun;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.parkinfo.exception.NormalException;
import com.parkinfo.tools.oss.IOssService;
import com.parkinfo.tools.properties.OssProperties;
import com.parkinfo.tools.properties.ToolsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

@Component
@Slf4j
public class AliyunOssService implements IOssService {

    @Autowired
    private ToolsProperties toolsProperties;

    @Override
    public String upload(InputStream inputStream, String keyPrefix,String fileName) {
        OssProperties aliyunOSSProperties = toolsProperties.getOss();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


//        OSSClient ossClient = new OSSClient(aliyunOSSProperties.getEndpoint(), aliyunOSSProperties.getKeyid(), aliyunOSSProperties.getKeysecret());
        OSS ossClient = new OSSClientBuilder().build(aliyunOSSProperties.getEndpoint(), aliyunOSSProperties.getKeyid(), aliyunOSSProperties.getKeysecret());
        try {
            //容器不存在，就创建
            if (!ossClient.doesBucketExist(aliyunOSSProperties.getBucketname())) {
                ossClient.createBucket(aliyunOSSProperties.getBucketname());
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(aliyunOSSProperties.getBucketname());
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }
            //创建文件路径
//            String fileName = file.getName();
            String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            String fileKey = keyPrefix + "/" +format.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
            String fileUrl = aliyunOSSProperties.getFilehost() + "/" +  fileKey;
            //上传文件
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(aliyunOSSProperties.getBucketname(), fileKey, inputStream));
            //设置权限 这里是公开读
            ossClient.setBucketAcl(aliyunOSSProperties.getBucketname(), CannedAccessControlList.PublicRead);
            if (null != result) {
                log.info("==========>OSS文件上传成功,OSS地址：" + fileUrl);
                return fileUrl;
            }
            return null;
        } catch (OSSException oe) {
            throw new NormalException("上传失败" + oe.getMessage());
        } catch (ClientException ce) {
            throw new NormalException("上传失败" + ce.getMessage());
        } finally {
            //关闭
            ossClient.shutdown();
        }
    }


    @Override
    public String MultipartFileUpload(HttpServletRequest request, String keyPrefix) {
        StringBuilder filePath = new StringBuilder();
        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                // 取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());

                String fileName = file.getOriginalFilename();
//                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
//                String newFileName = keyPrefix +"/"+ df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
                try {
//                    File newFile = new File(fileName);
//                    FileOutputStream os = new FileOutputStream(newFile);
//                    os.write(file.getBytes());
//                    os.close();
//                    file.transferTo(newFile);
                    InputStream inputStream = file.getInputStream();
                    String imageUrl = this.upload(inputStream,keyPrefix,fileName);
                    filePath.append(imageUrl).append(",");
                    inputStream.close();
                } catch (IOException e) {
                    throw new NormalException("上传失败," + "文件格式不正确");
                }
            }
            if (filePath.toString().endsWith(",")) {
                filePath = new StringBuilder(filePath.substring(0, filePath.length() - 1));
            }
            return filePath.toString();
        } else {
            throw new NormalException("请选择上传文件");
        }
    }

    @Override
    public String fileUploadTest(MultipartFile file, String keyPrefix) {
        StringBuilder filePath = new StringBuilder();
        String fileName = file.getOriginalFilename();
        try {
            InputStream inputStream = file.getInputStream();
            String imageUrl = this.upload(inputStream,keyPrefix,fileName);
            filePath.append(imageUrl).append(",");
            inputStream.close();
        } catch (IOException e) {
            throw new NormalException("上传失败," + "文件格式不正确");
        }
        if (filePath.toString().endsWith(",")) {
            filePath = new StringBuilder(filePath.substring(0, filePath.length() - 1));
        }
        return filePath.toString();
    }
}