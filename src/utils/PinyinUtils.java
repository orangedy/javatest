/*
 * @(#) PinyinUtils.java 2015年2月28日
 * 
 * Copyright 2010 NetEase.com, Inc. All rights reserved.
 */
package utils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author hzdingyong
 * @version 2015年2月28日
 */
public class PinyinUtils {
    private static final Logger logger = Logger.getLogger(PinyinUtils.class);
    private static final String PINYIN_RES = "pinyin.txt";
    private static TrieTree pinyinTree = buildTree(PINYIN_RES);

    private static TrieTree buildTree(String resourceFile) {
        try {
            List<String> allPinyin = FileUtils.readLines(new File(resourceFile));
            return TrieTreeUtils.createTrieTree(allPinyin);
        } catch (IOException e) {
            logger.error("create pinyin tree failed", e);
            return new TrieTree();
        }
    }

    public static boolean isPinYin(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        } else {
            return pinyinTree.search(str);
        }
    }

    /**
     * 将输入的字符串切割为拼音，如果不能完全切割则返回null
     * 匹配时优先采用最长匹配，防止将一个拼音切割为两个，比如liang不会被切割为li ang等
     * 但是同时保证优先最终完全匹配，比如liangong会切割为lian gong，因为liang ong后者不是拼音
     * @param str 输入拼音串，只能包含a-z的小写字母，否则会报异常
     * @return
     */
    public static List<String> pinyinSplit(String str) {
        if (StringUtils.isEmpty(str)) {
            return new LinkedList<String>();
        }
        List<String> result = new LinkedList<String>();
        for (int i = 6 < str.length() ? 6 : str.length(); i > 0; i--) {
            if (pinyinTree.search(str.substring(0, i))) {
                result.add(str.substring(0, i));
                List<String> subResult = pinyinSplit(str.substring(i));
                if (subResult != null) {
                    result.addAll(subResult);
                    return result;
                } else {
                    result.remove(result.size() - 1);
                }
            }
        }
        return null;
    }
}
