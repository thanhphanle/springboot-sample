package com.thanhpl.networkfile.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

// https://www.baeldung.com/spring-mvc-image-media-data
// https://stackoverflow.com/questions/49346841/how-to-return-an-image-to-browser-in-rest-api-in-java
@Slf4j
@RestController
@RequestMapping({ "/v1/file" })
public class DownloadFileController {

	@PostMapping("/download")
	public void downloadFile(HttpServletResponse response, @PathVariable String fileName) {
		String imageFile = "D:\\dev\\upload\\" + fileName;
		try {
			File file = new File(imageFile);
		    if(file.exists()) {
		        String contentType = "application/octet-stream";
		        response.setContentType(contentType);
		        OutputStream out = response.getOutputStream();
		        FileInputStream in = new FileInputStream(file);
		        // copy from in to out
		        IOUtils.copy(in, out);
		        out.close();
		        in.close();
		    } else {
		    	String contentType = "application/json";
		        response.setContentType(contentType);
		        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		    }
		} catch (Exception e) {
			log.error("Download error", e);
		}
		
	}
	
	@GetMapping("/load/{fileName}")
	public void loadImage(HttpServletResponse response, @PathVariable String fileName) {
		String imageFile = "D:\\dev\\upload\\" + fileName;
		try {
			File file = new File(imageFile);
		    if(file.exists()) {
		    	String contentType = "image/jpeg";
		        response.setContentType(contentType);
		        OutputStream out = response.getOutputStream();
		        FileInputStream in = new FileInputStream(file);
		        // copy from in to out
		        IOUtils.copy(in, out);
		        out.close();
		        in.close();
		    } else {
		    	String contentType = "application/json";
		        response.setContentType(contentType);
		        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		    }
		} catch (Exception e) {
			log.error("Load image error", e);
		}
	}
	
	@GetMapping("/load2/{fileName}")
	public ResponseEntity<Resource> loadImage2(@PathVariable String fileName) {
		String imageFile = "D:\\dev\\upload\\" + fileName;
		ByteArrayResource resource = null;
		try {
			Path path = Paths.get(imageFile);
			if (!Files.exists(path)) {
				return ResponseEntity.notFound().build();
			}
		    resource = new ByteArrayResource(Files.readAllBytes(path));
		} catch (Exception e) {
			log.error("Load image error", e);
		}
		return ResponseEntity.ok()
	            .contentLength(resource.getByteArray().length)
	            .contentType(MediaType.parseMediaType("image/jpeg"))
	            .body(resource);
	}
}
