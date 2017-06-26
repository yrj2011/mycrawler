package com.mycrawler.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceLoader {

	public static final Logger logger = LoggerFactory.getLogger(ResourceLoader.class);
	
	public static InputStream getResourceFromFile(String fileName, String baseFolder) throws Exception {
		InputStream is = null;
		
		File runningJarFile = new File(ResourceLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile()
				.getParentFile();

		String filePath = null;
		if (baseFolder == null || baseFolder.length() == 0) {
			filePath = runningJarFile.getPath().concat(File.separator).concat(fileName);
		} else {
			filePath = runningJarFile.getPath().concat(File.separator).concat(baseFolder).concat(File.separator).concat(fileName);
		}
		
		logger.debug("Searched file path:" + filePath);
		
		File propFile = new File(filePath);
		if (propFile.exists() && !propFile.isDirectory()) {
			logger.debug(String.format("Get resource from external file [%s]", propFile.getPath()));
			is = new FileInputStream(propFile);
		}

		return is;
	}

	public static InputStream getResourceFromClassPath(String fileName, String baseFolder) throws Exception {
		return getResourceFromClassPath(fileName, baseFolder, null);
	}
	
	public static InputStream getResourceFromClassPath(String fileName, String baseFolder, Class<?> clazz) throws Exception {

		InputStream is = null;

		String resourcePath = (baseFolder == null) ? fileName : baseFolder + "/" + fileName;
		
		if (clazz == null) {
			is = ResourceLoader.class.getClassLoader().getResourceAsStream(resourcePath);
		} else {
			is = clazz.getResourceAsStream(resourcePath);
		}
		if (is != null) {
			logger.debug(String.format("Get resource from class path [%s]", resourcePath));
		}

		return is;
	}
	
	
	public static File getResourceFileFromFile(String fileName, String baseFolder) throws Exception {

		File runningJarFile = new File(ResourceLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile()
				.getParentFile();
		
		String filePath = null;
		if (baseFolder == null || baseFolder.length() == 0) {
			filePath = runningJarFile.getPath().concat(File.separator).concat(fileName);
		} else {
			filePath = runningJarFile.getPath().concat(File.separator).concat(baseFolder).concat(File.separator).concat(fileName);
		}
		
		System.out.println(filePath);
		logger.debug("Searched file path:" + filePath);
		File propFile = new File(filePath);
		return propFile;
	}
	

	public static File getResourceFileFromClassPath(String fileName, String baseFolder) throws Exception {
		String resourcePath = (baseFolder == null) ? fileName : baseFolder + "/" + fileName;
		URL url = ResourceLoader.class.getClassLoader().getResource(resourcePath);
		if (url != null) {
			logger.debug(String.format("Get resource from class path [%s]", resourcePath));
			return new File(url.getPath());
		}
		return new File("");
	}
}
