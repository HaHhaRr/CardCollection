package ru.hahharr.cardcollection.s3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Component
public class S3Service {

    @Value("${security.aws.bucket_name}")
    private String bucketName;

    @Value("${security.aws.endpoint}")
    private String s3Endpoint;

    @Autowired
    private S3Client s3Client;

    public String uploadImage(MultipartFile image) throws IOException {
        String key = image.getOriginalFilename();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(image.getContentType())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(image.getBytes()));
        return s3Endpoint + "/" + bucketName;
    }

    public ResponseEntity<byte[]> downloadImage(String key) throws IOException {
        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        ResponseInputStream<GetObjectResponse> inputStream = s3Client.getObject(objectRequest);
        byte[] image = inputStream.readAllBytes();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, inputStream.response().contentType());
        return ResponseEntity.ok()
                .headers(headers)
                .body(image);
    }
}
