package ru.hahharr.cardcollection.s3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Base64;

@Component
public class S3Service {

    @Value("${security.aws.bucket_name}")
    private String bucketName;

    @Value("${security.aws.endpoint}")
    private String s3Endpoint;

    @Autowired
    private S3Client s3Client;

    public String uploadImage(long cardId, MultipartFile image) throws IOException {
        String key = String.join("_", String.valueOf(cardId), image.getOriginalFilename());
        String encodedKey = Base64.getEncoder().encodeToString(key.getBytes());

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(encodedKey)
                .contentType(image.getContentType())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(image.getBytes()));

        return String.join("/", s3Endpoint, bucketName, encodedKey);
    }
}
