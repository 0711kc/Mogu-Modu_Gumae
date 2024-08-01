package com.bunsaned3thinking.mogu.image.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
	String upload(MultipartFile file);

	List<String> uploadAll(List<MultipartFile> multipartFileList);
}
