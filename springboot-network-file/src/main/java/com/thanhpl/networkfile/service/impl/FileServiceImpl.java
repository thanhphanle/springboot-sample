package com.thanhpl.networkfile.service.impl;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.thanhpl.networkfile.service.FileService;
import com.thanhpl.networkfile.utility.NioFileHelper;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public void store(MultipartFile file, String dirPath) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		String filePath = dirPath + File.separator + fileName;
		NioFileHelper.saveFile(filePath, file.getBytes());
	}

}
