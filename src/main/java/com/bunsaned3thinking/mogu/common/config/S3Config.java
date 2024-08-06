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

	public static String ImageURL;
	public static String UserImage;
	public static String PostImage;

	@Value("${s3.basic.user}")
	private void setBasicUserImage(String userImage) {
		S3Config.UserImage = userImage;
	}

	@Value("${s3.basic.post}")
	private void setBasicPostImage(String postImage) {
		S3Config.PostImage = postImage;
	}

	@Value("${s3.basic.url}")
	private void setBasicImageUrl(String url) {
		S3Config.ImageURL = url;
	}

	@Bean
	public AmazonS3Client s3Client() {
		BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
		return (AmazonS3Client)AmazonS3ClientBuilder.standard()
			.withRegion(region)
			.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
			.build();
	}
}

