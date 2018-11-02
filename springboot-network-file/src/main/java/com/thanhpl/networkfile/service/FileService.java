package com.thanhpl.networkfile.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	void store(MultipartFile file, String dirPath) throws IOException;
}
