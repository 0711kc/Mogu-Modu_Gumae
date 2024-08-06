package com.bunsaned3thinking.mogu.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {

	@Value("${s3.credentials.access-key}")
	private String accessKey;

	@Value("${s3.credentials.secret-key}")
	private String secretKey;

	@Value("${s3.credentials.region}")
	private String region;

	@Bean
	public AmazonS3Client s3Client() {
		BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
		return (AmazonS3Client)AmazonS3ClientBuilder.standard()
			.withRegion(region)
			.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
			.build();
	}

	public static String basicUserImage() {
		return "https://moguimages.s3.ap-northeast-2.amazonaws.com/ano.png";
	}

	public static String basicPostImage() {
		return "https://moguimages.s3.ap-northeast-2.amazonaws.com/basicPostImage.png";
	}

}

