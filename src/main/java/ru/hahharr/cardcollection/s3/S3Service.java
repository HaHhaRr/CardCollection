package ru.hahharr.cardcollection.s3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.hahharr.cardcollection.models.primitives.id.CollectionId;
import ru.hahharr.cardcollection.models.primitives.rarity.Rarity;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
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

    public String uploadImage(CollectionId collectionId, Rarity rarity, MultipartFile image) throws IOException {
        String key = String.join("_", String.valueOf(collectionId.getId()),
                rarity.name(), image.getOriginalFilename());

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(image.getContentType())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(image.getBytes()));
        String url = String.join("/", s3Endpoint, bucketName, key);

        return Base64.getEncoder().encodeToString(url.getBytes());
    }
}
