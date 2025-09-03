package com.kit.snsop.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    public static final String COMMON_BUCKET_NAME = "biometrics";
    public static final String TEMP_BUCKET = "temporary";
    public static final String PRE_REGISTER_BUCKET = "pre-register";

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.port}")
    private Integer port;

    @Value("${minio.secure}")
    private Boolean secure;

    @Value("${minio.username}")
    private String minioUsername;

    @Value("${minio.password}")
    private String minioPassword;

    @Bean
    public MinioClient minioClient() throws Exception {
        MinioClient client = MinioClient.builder()
                .endpoint(minioUrl, port, secure)
                .credentials(minioUsername, minioPassword)
                .build();
        if (!client.bucketExists(BucketExistsArgs.builder().bucket(COMMON_BUCKET_NAME).build())) {
            client.makeBucket(
                    MakeBucketArgs
                            .builder()
                            .bucket(COMMON_BUCKET_NAME)
                            .build()
            );
        }
        if (!client.bucketExists(BucketExistsArgs.builder().bucket(TEMP_BUCKET).build())) {
            client.makeBucket(
                    MakeBucketArgs
                            .builder()
                            .bucket(TEMP_BUCKET)
                            .build()
            );
        }
        return client;
    }
}

