package com.pig4cloud.pigx.files.controller;

import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.files.service.ModelService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Objects;

@RequestMapping("/model")
@RestController
@RequiredArgsConstructor
@Api(value = "model", tags = "图像识别管理")
public class ModelController {

	@Resource
	private ModelService modelService;

	@PostMapping("/recognize")
	@PreAuthorize("@pms.hasPermission('files_files_export')" )
	public R recognizeImage(@RequestParam("file") MultipartFile image){
		System.out.println("------------------------------------" + image);
		String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
		System.out.println("------------------------------------" + originalFileName);
//		 1 限制上传类型只能是jar或者json类型
		if (!(originalFileName.contains(".jpg")||originalFileName.contains(".png")||originalFileName.contains(".jepg"))){
			return R.failed("图片上传类型错误");
		}
//		 2 判断文件是否为空
		if (image.isEmpty()){
			return R.failed("图片为空");
		}

		Object result = modelService.recognizeImage(image);

		if (Objects.isNull(result)) {
			return R.failed("图像识别错误");
		}
		return R.ok(result);
//		return null;
	}
}
