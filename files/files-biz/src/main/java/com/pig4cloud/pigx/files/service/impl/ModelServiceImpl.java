package com.pig4cloud.pigx.files.service.impl;

import com.pig4cloud.pigx.files.service.ModelService;
import com.pig4cloud.pigx.files.utils.PyUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ModelServiceImpl implements ModelService{
	@Override
	public Object recognizeImage(MultipartFile image) {
		return PyUtil.callNNbyImg(image);
	}
}