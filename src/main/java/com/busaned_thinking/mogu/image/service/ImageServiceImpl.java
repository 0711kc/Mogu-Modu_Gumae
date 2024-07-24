package com.busaned_thinking.mogu.image.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
	private final AmazonS3Client s3Client;

	@Value("${s3.bucket}")
	private String bucket;

	@Override
	public String upload(MultipartFile file) {
		try {
			String originalFileName = file.getOriginalFilename();
			String fileName = changeFileName(originalFileName);
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(file.getContentType());
			metadata.setContentLength(file.getSize());
			s3Client.putObject(bucket, fileName, file.getInputStream(), metadata);
			return s3Client.getUrl(bucket, fileName).toString();
		} catch (IOException e) {
			throw new IllegalArgumentException();
		}
	}

	private String changeFileName(String originalFileName) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		return originalFileName + "_" + LocalDateTime.now().format(formatter);
	}
}
