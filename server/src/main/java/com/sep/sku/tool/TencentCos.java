package com.sep.sku.tool;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.StorageClass;
import com.qcloud.cos.region.Region;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Component
@Slf4j
public class TencentCos {


    @Value("${cos.bucketName}")
    private String bucketName; //桶的名称

    @Value("${cos.region}")
    private String region;//区域北京则  beijing

    @Value("${cos.SecretId}")
    private String SecretId;

    @Value("${cos.SecretKey}")
    private String SecretKey;


    public String SimpleUploadFileFromLocal(String key, String filepath) {

        COSCredentials cred = new BasicCOSCredentials(SecretId, SecretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        // 3 生成cos客户端
        COSClient cosClient = new COSClient(cred, clientConfig);
        File localFile = new File(filepath);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        // 设置存储类型, 默认是标准(Standard), 低频(standard_ia)
        putObjectRequest.setStorageClass(StorageClass.Standard);

        return this.getUrl(cosClient,putObjectRequest,key);
    }


    public  String SimpleUploadFileFromStream(String key, InputStream input) throws IOException {
        //  生成cos客户端
        COSCredentials cred = new BasicCOSCredentials(SecretId, SecretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        COSClient cosClient = new COSClient(cred, clientConfig);
        // bucket名需包含appid
        ObjectMetadata objectMetadata = new ObjectMetadata();
        // 从输入流上传必须制定content length, 否则http客户端可能会缓存所有数据，存在内存OOM的情况
        objectMetadata.setContentLength(Integer.valueOf(input.available()).longValue());
        // 默认下载时根据cos路径key的后缀返回响应的contenttype, 上传时设置contenttype会覆盖默认值
       // objectMetadata.setContentType("image/jpeg");

        PutObjectRequest putObjectRequest =
                new PutObjectRequest(bucketName, key, input, objectMetadata);
        // 设置存储类型, 默认是标准(Standard), 低频(standard_ia)
        putObjectRequest.setStorageClass(StorageClass.Standard);

        return this.getUrl(cosClient,putObjectRequest,key);
    }


    private  String  getUrl(COSClient cosClient , PutObjectRequest putObjectRequest , String key){

        try {
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

            return "https://" + bucketName + ".cos." + region + ".myqcloud.com/" + key;
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }
        // 关闭客户端
        cosClient.shutdown();
        return null;


    }



}
