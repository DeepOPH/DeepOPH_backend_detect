package com.pig4cloud.pigx.files.service;

import org.springframework.web.multipart.MultipartFile;

public interface ModelService {
	Object recognizeImage(MultipartFile image);
}
