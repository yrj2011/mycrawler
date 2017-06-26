package com.mycrawler.common.utils;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

public class LoggerUtils {
	
	public static void init() {
		try {
			File log4j2File = PropertiesLoader.loadFile("log4j2.xml", true);
			String log4j2FilePath = log4j2File.toURI().toURL().toString();
			System.err.println("load log4j2.xml from " + log4j2FilePath);
			System.setProperty("log4j.configurationFile", log4j2FilePath);
			LoggerContext context =(LoggerContext)LogManager.getContext(false);
			context.reconfigure();


		} catch (Exception e) {
			System.out.println("fatal error in logger utils " + e.toString());
			e.printStackTrace();
			System.exit(-1);
		}

	}
}
