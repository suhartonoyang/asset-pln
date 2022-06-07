package com.project.assetpln.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileHandlingService {

	@Value("${upload.path}")
	private String uploadPath;

	public void init(String subFolder) {
		try {
			Files.createDirectories(Paths.get(uploadPath + subFolder + "/"));
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize folder for upload");
		}
	}

	public void saveFile(String subFolder, File file) {
		try {
			init(subFolder);
			InputStream inputStream = new FileInputStream(file);

			Path copyLocation = Paths.get(uploadPath + subFolder + "/" + File.separator + StringUtils.cleanPath(file.getName()));
			Files.copy(inputStream, copyLocation, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
	}

	public void saveFile(String subFolder, File file, String type) {
		try {
			init(subFolder);
			String filename = StringUtils.cleanPath(file.getName());
			filename = filename.replace(".", type + ".");

			InputStream inputStream = new FileInputStream(file);

			Path copyLocation = Paths.get(uploadPath + subFolder + "/" + File.separator + filename);
			Files.copy(inputStream, copyLocation, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
	}

	public void saveMultipartFile(String subFolder, MultipartFile file) {
		try {
			init(subFolder);

			Path copyLocation = Paths
					.get(uploadPath + subFolder + "/" + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
			String path = copyLocation.toString();
			convertMultipartFiletoFile(file, path);
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
	}

	public String getPath(String subFolder, String filename) {
		Path copyLocation = Paths
				.get(uploadPath + subFolder + "/" + File.separator + filename);
		String path = copyLocation.toString();
		return path;
	}

	private void convertMultipartFiletoFile(MultipartFile file, String path) throws IOException {
		File convFile = new File(path);
		convFile.createNewFile();
		FileOutputStream output = new FileOutputStream(convFile);
		output.write(file.getBytes());
		output.close();
	}
}
