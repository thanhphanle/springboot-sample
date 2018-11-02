package com.thanhpl.networkfile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.thanhpl.networkfile.response.UploadFileResponse;
import com.thanhpl.networkfile.response.UploadFileResponseData;
import com.thanhpl.networkfile.service.FileService;
import com.thanhpl.networkfile.utility.JsonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping({ "/v1/file" })
public class UploadFileController {

	@Autowired
	private FileService fileService;
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
		UploadFileResponse response = new UploadFileResponse();
		UploadFileResponseData data = new UploadFileResponseData();

		try {
			Long fileSize = file.getSize(); // in byte
			System.out.println("Size: " + fileSize);
			
			fileService.store(file, "D:\\dev\\upload");
			
			data.setStatus("Uploaded");
			response.setData(data);
			response.setSuccess(true);
			response.setCode("0");
			response.setMessage("Success");
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setCode("900");
			response.setMessage("Server error");
		}

		log.info(JsonUtil.toJson(response));
		return response;
	}
}
