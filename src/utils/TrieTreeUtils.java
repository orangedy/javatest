/*
 * @(#) TrieTreeUtils.java 2015年2月28日
 * 
 * Copyright 2010 NetEase.com, Inc. All rights reserved.
 */
package utils;

import java.util.List;

/**
 *
 * @author hzdingyong
 * @version 2015年2月28日
 */
public class TrieTreeUtils {

    public static TrieTree createTrieTree() {
        return new TrieTree();
    }

    public static TrieTree createTrieTree(List<String> strList) {
        TrieTree tree = new TrieTree();
        for (String str : strList) {
            tree.insert(str);
        }
        return tree;
    }

    public static boolean find(TrieTree tree, String str) {
        return tree.search(str);
    }

}
