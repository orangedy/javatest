/*
 * @(#) FileUtils.java 2014年12月12日
 * 
 * Copyright 2010 NetEase.com, Inc. All rights reserved.
 */
package file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author hzdingyong
 * @version 2014年12月12日
 */
public class SimpleFileUtils {

    public static List<String> getFileContent(String fileName) {
        List<String> contents = new LinkedList<String>();
        File file = new File(fileName);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                contents.add(line);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return contents;
    }

    public static boolean writeToFile(String fileName, String content) {
        boolean result = true;
        if (content == null || content.length() == 0) {
            return true;
        } else {
            File file = new File(fileName);
            BufferedWriter bw = null;
            try {
                bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "utf8"));
                bw.append(content);
            } catch (UnsupportedEncodingException e) {
                result = false;
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                result = false;
                e.printStackTrace();
            } catch (IOException e) {
                result = false;
                e.printStackTrace();
            } finally {
                if (bw != null) {
                    try {
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }
}
