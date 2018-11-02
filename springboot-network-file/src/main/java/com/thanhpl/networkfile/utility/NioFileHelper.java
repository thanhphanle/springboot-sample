package com.thanhpl.networkfile.utility;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class NioFileHelper {
	public static List<String> getFiles(String dirPath) throws IOException {
		List<String> files = new ArrayList<String>();
		Path dir = Paths.get(dirPath);
		DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.*" );
		for (Path path : stream) {
			files.add(path.toAbsolutePath().toString());
		}
		stream.close();
		return files;
	}
	
	public static boolean exists(String filePath) {
		Path path = Paths.get(filePath);
		return Files.exists(path);
	}
	
	public static void saveFile(String filePath, byte[] bytes) throws IOException {
		Path path = Paths.get(filePath);
        Files.write(path, bytes);
	}
	
	public static void deleteFile(String filePath) throws IOException {
		Path path = Paths.get(filePath);
		Files.delete(path);
	}
	
	public static void copyFile(String srcFilePath, String desFilePath) throws IOException {
		Path srcPath = Paths.get(srcFilePath);
		Path desPath = Paths.get(desFilePath);
		if (!Files.exists(desPath)) {
			Files.createDirectories(desPath.getParent());
		}
		Files.copy(srcPath, desPath, StandardCopyOption.REPLACE_EXISTING);
	}
	
	public static void moveFile(String srcFilePath, String desFilePath) throws IOException {
		Path srcPath = Paths.get(srcFilePath);
		Path desPath = Paths.get(desFilePath);
		if (!Files.exists(desPath)) {
			Files.createDirectories(desPath.getParent());
		}
		Files.move(srcPath, desPath, StandardCopyOption.REPLACE_EXISTING);
	}
	
	public static void createDir(String dirPath) throws IOException {
		Path path = Paths.get(dirPath);
		Files.createDirectories(path);
	}
}
