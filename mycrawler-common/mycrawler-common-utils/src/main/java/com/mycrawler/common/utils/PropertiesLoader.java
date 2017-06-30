package com.mycrawler.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesLoader {
	public static final Logger logger = LoggerFactory.getLogger(PropertiesLoader.class);

	public static Properties load(String fileName) throws Exception {
		return load(fileName, true);
	}

	// enableDevMode  
	public static Properties load(String fileName, boolean enableDevMode) throws Exception {
		Properties props = new Properties();
		InputStream is = null;
		try {
			// 1. Search <running-jar-root-directory>/ folder with .dev suffix
			// 2. Search <running-jar-root-directory>/conf/ folder with .dev
			// suffix
			if (enableDevMode) {
				is = ResourceLoader.getResourceFromFile(fileName + ".dev", null);

				if (is == null) {
					is = ResourceLoader.getResourceFromFile(fileName + ".dev", "conf");
				}
				
				if (is == null) {
					is = ResourceLoader.getResourceFromFile(fileName + ".dev", "resource" + File.separator + "conf");
				}
			}

			// 3. Search <running-jar-root-directory>/ folder without .dev
			// suffix
			// 4. Search <running-jar-root-directory>/conf/ folder without .dev
			// suffix
			if (is == null) {
				is = ResourceLoader.getResourceFromFile(fileName, null);

				if (is == null) {
					is = ResourceLoader.getResourceFromFile(fileName, "conf");
				}
				
				if (is == null) {
					is = ResourceLoader.getResourceFromFile(fileName, "resource" + File.separator + "conf");
				}
			}

			// Search up to 10 levels' parent folder under dev mode (Eclipse RCP
			// application has deep folder hierarchy for the running jar)
			if (is == null && enableDevMode) {
				String parentFolder = "";
				for (int i = 0; i < 10; ++i) {
					parentFolder += (".." + File.separator);
					is = ResourceLoader.getResourceFromFile(fileName + ".dev", parentFolder + "conf");

					if (is == null) {
						is = ResourceLoader.getResourceFromFile(fileName + ".dev", parentFolder + "resource" + File.separator + "conf");
					}
					
					if (is == null) {
						is = ResourceLoader.getResourceFromFile(fileName, parentFolder + "conf");
					}
					
					if (is == null) {
						is = ResourceLoader.getResourceFromFile(fileName, parentFolder + "resource" + File.separator + "conf");
					}

					if (is != null)
						break;
				}
			}

			// 5. Search class path / with .dev suffix
			// 6. Search class path /properties/ with .dev suffix
			if (is == null) {
				is = ResourceLoader.getResourceFromClassPath(fileName + ".dev", null);

				if (is == null) {
					is = ResourceLoader.getResourceFromClassPath(fileName + ".dev", "conf");
				}

				if (is == null) {
					is = ResourceLoader.getResourceFromClassPath(fileName + ".dev", "properties");
				}
			}

			// 7. Search class path / folder without .dev suffix
			// 8. Search class path /properties/ folder without .dev suffix
			if (is == null) {
				is = ResourceLoader.getResourceFromClassPath(fileName, null);

				if (is == null) {
					is = ResourceLoader.getResourceFromClassPath(fileName, "conf");
				}

				if (is == null) {
					is = ResourceLoader.getResourceFromClassPath(fileName, "properties");
				}
			}

			if (is != null) {
				props.load(is);
			} else {
				throw new RuntimeException(String.format("'%s' file not found", fileName));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception happens in PropertiesLoader.load()", e);
			throw e;
		} finally {
			if (is != null)
				is.close();
		}

		return props;
	}

	public static File loadFile(String fileName, boolean enableDevMode) throws Exception {
		return loadFile(fileName, "conf", enableDevMode);
	}
	
	public static File loadFile(String fileName, String dir, boolean enableDevMode) throws Exception {
		File file = null;
		try {
			// 1. Search <running-jar-root-directory>/ folder with .dev suffix
			// 2. Search <running-jar-root-directory>/conf/ folder with .dev
			// suffix
			if (enableDevMode) {
				file = ResourceLoader.getResourceFileFromFile(fileName + ".dev", null);

				if (!file.exists()) {
					file = ResourceLoader.getResourceFileFromFile(fileName + ".dev", dir);
				}
				
				if (!file.exists()) {
					file = ResourceLoader.getResourceFileFromFile(fileName + ".dev", "resource" + File.separator + dir);
				}
			}

			// 3. Search <running-jar-root-directory>/ folder without .dev
			// suffix
			// 4. Search <running-jar-root-directory>/conf/ folder without .dev
			// suffix
			if (file == null || !file.exists()) {
				file = ResourceLoader.getResourceFileFromFile(fileName, null);

				if (!file.exists()) {
					file = ResourceLoader.getResourceFileFromFile(fileName, dir);
				}
				
				if (!file.exists()) {
					file = ResourceLoader.getResourceFileFromFile(fileName, "resource" + File.separator + dir);
				}
			}

			// Search up to 10 levels' parent folder under dev mode (Eclipse RCP
			// application has deep folder hierarchy for the running jar)
			if ((file == null || !file.exists()) && enableDevMode) {
				String parentFolder = "";
				for (int i = 0; i < 10; ++i) {
					parentFolder += (".." + File.separator);
					file = ResourceLoader.getResourceFileFromFile(fileName + ".dev", parentFolder + dir);

					

					if (!file.exists()) {
						file = ResourceLoader.getResourceFileFromFile(fileName + ".dev", parentFolder + "resource" + File.separator + dir);
					}
					
					if (!file.exists()) {
						file = ResourceLoader.getResourceFileFromFile(fileName, parentFolder + dir);
					}
										
					if (!file.exists()) {
						file = ResourceLoader.getResourceFileFromFile(fileName, parentFolder + "resource" + File.separator + dir);
					}
					
					if (file.exists()){
						break;
					}
				}
			}

			// 5. Search class path / with .dev suffix
			// 6. Search class path /properties/ with .dev suffix
			if (file == null || !file.exists()) {
				file = ResourceLoader.getResourceFileFromClassPath(fileName + ".dev", null);

				if (!file.exists()) {
					file = ResourceLoader.getResourceFileFromClassPath(fileName + ".dev", dir);
				}

				if (!file.exists()) {
					file = ResourceLoader.getResourceFileFromClassPath(fileName + ".dev", "properties");
				}
			}

			// 7. Search class path / folder without .dev suffix
			// 8. Search class path /properties/ folder without .dev suffix
			if (file == null || !file.exists()) {
				file = ResourceLoader.getResourceFileFromClassPath(fileName, null);

				if (!file.exists()) {
					file = ResourceLoader.getResourceFileFromClassPath(fileName, dir);
				}

				if (!file.exists()) {
					file = ResourceLoader.getResourceFileFromClassPath(fileName, "properties");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception happens in PropertiesLoader.loadFile()", e);
			throw e;
		}

		return file;
	}

	
	public static void store(Properties props , String fileName, boolean enableDevMode) throws Exception {
		if(props == null){
			throw new IllegalArgumentException("properties cann't be null");
		}
		if(fileName == null){
			throw new IllegalArgumentException("fileName cann't be null");
		}
		File target = loadFile(fileName , enableDevMode);
		store(props, target, enableDevMode);
	}
	
	public static void store(Properties props , File target, boolean enableDevMode) throws Exception {
		if(props == null){
			throw new IllegalArgumentException("properties cann't be null");
		}
		if(target == null || !target.exists()){
			throw new IllegalArgumentException("target File cann't be null and target file must exists");
		}
		logger.debug("store() target file�� " + target.getPath());
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(target);
			props.store(fos, "salave-computer settings");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception happens in PropertiesLoader.store()", e);
			throw e;
		}finally{
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	public static void updatePropertiesContent(String fileName,String updateKey,String updateContent){
		logger.info("updatePropertiesContent() Start");
		try {
			File file = PropertiesLoader.loadFile(fileName, true); 
			String propertieLine ;
			BufferedReader br = new BufferedReader(new FileReader(file));
			List<String> propertiesList = new ArrayList<>();
			while ((propertieLine = br.readLine())!=null) {
				if (propertieLine.contains(updateKey)) {
					propertieLine = updateKey+"="+updateContent;
				}
				propertiesList.add(propertieLine);
			}
			br.close();
			logger.debug("update file path >>>>> "+file.getPath());
			FileUtils.fileWriter(file.getPath(), propertiesList);
			logger.info("updatePropertiesContent() Done");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
		}
	}

	public static Properties loadFile(File file) throws Exception {
		Properties props = new Properties();
		props.load(new FileInputStream(file));
		return props;
	}
}
