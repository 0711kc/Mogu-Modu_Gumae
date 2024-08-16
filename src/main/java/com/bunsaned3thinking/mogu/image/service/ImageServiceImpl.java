package com.bunsaned3thinking.mogu.image.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.bunsaned3thinking.mogu.common.config.S3Config;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageServiceImpl implements ImageService {
	private final AmazonS3Client s3Client;

	@Value("${s3.bucket}")
	private String bucket;

	@Override
	public String upload(MultipartFile file) {
		try {
			String fileName = createFileName();
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(file.getContentType());
			metadata.setContentLength(file.getSize());
			s3Client.putObject(bucket, fileName, file.getInputStream(), metadata);
			return fileName;
		} catch (IOException e) {
			throw new IllegalStateException("[Error] AWS S3 서비스 접근에 실패했습니다.");
		}
	}

	@Override
	public List<String> uploadAll(List<MultipartFile> multipartFileList) {
		return multipartFileList.stream()
			.map(this::upload)
			.toList();
	}

	@Override
	public void delete(String fileName) {
		if (fileName.equals(S3Config.PostImage) | fileName.equals(S3Config.UserImage)) {
			return;
		}
		try {
			s3Client.deleteObject(bucket, fileName);
		} catch (SdkClientException e) {
			throw new IllegalStateException("[Error] AWS S3 서비스 접근에 실패했습니다.");
		}
	}

	@Override
	public void deleteAll(List<String> fileNames) {
		if (fileNames.isEmpty()) {
			return;
		}
		if (fileNames.contains(S3Config.PostImage) | fileNames.contains(S3Config.UserImage)) {
			return;
		}
		DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucket)
			.withKeys(fileNames.stream()
				.map(DeleteObjectsRequest.KeyVersion::new)
				.toList());
		s3Client.deleteObjects(deleteObjectsRequest);
	}

	private String createFileName() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
		return UUID.randomUUID() + "_" + LocalDateTime.now().format(formatter);
	}
}
