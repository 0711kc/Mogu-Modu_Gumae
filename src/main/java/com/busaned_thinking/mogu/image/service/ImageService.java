package com.busaned_thinking.mogu.image.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
	String upload(MultipartFile file);
}
