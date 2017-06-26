package com.mycrawler.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Write File
 * @author Cooper
 *
 */
public class FileUtils {
	final Logger logger = LoggerFactory.getLogger(FileUtils.class);
	public static void fileWriter(String fileName,List<String> clist) throws IOException{
        FileWriter fw = new FileWriter(fileName);
        for (String str: clist){
            fw.write(str);
            fw.write("\r\n");
        }
        fw.flush();
        fw.close();
    }
    
    public static List<String> readFileByLines(String fileName) throws IOException{
        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String tempString = null;
        List<String> nums = new ArrayList<String>();
        while ((tempString = reader.readLine()) != null){
            nums.add(tempString);
        }
        reader.close();
        return nums;
    }
    
    public static List<String> readFileByLines(File file) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String tempString = null;
        List<String> nums = new ArrayList<String>();
        while ((tempString = reader.readLine()) != null){
            nums.add(tempString);
        }
        reader.close();
        return nums;
    }
}
