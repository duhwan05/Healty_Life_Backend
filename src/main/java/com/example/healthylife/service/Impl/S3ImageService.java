//package com.example.healthylife.service.Impl;
//
//import com.amazonaws.internal.http.ErrorCodeParser;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.AmazonS3Exception;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.executor.ErrorContext;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.integration.IntegrationProperties;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.Objects;
//
//@Slf4j
//@RequiredArgsConstructor
//@Component
//public class S3ImageService {
//
//    private final AmazonS3 amazonS3;
//
//    @Value("${cloud.aws.trendydevbucket}")
//    private String bucketName;
////
////    public String upload(MultipartFile image) {
////        if (image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
////
////        }
////    }
//}
