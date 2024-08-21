package com.example.healthylife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;

@Service
public class S3Service {

    private final S3Client s3Client;

    @Autowired
    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    private final String bucketName = "today-image1";

    // 파일 업로드
    public String uploadFileToS3(MultipartFile file) {
        String fileName = file.getOriginalFilename();

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            // 반환된 URL은 직접 생성해야 합니다.
            return "https://" + bucketName + ".s3.amazonaws.com/" + fileName;

        } catch (IOException e) {
            throw new RuntimeException("S3 파일 업로드 실패", e);
        }
    }
    
    // 파일 삭제
    public void deleteFileFromS3(String fileName) {
        try {
            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("S3 파일 삭제 실패", e);
        }
    }

}
