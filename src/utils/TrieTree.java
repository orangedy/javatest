/*
 * @(#) TrieTree.java 2015年2月28日
 * 
 * Copyright 2010 NetEase.com, Inc. All rights reserved.
 */
package utils;

import org.apache.commons.lang.StringUtils;

/**
 *
 * @author hzdingyong
 * @version 2015年2月28日
 */
public class TrieTree {
    private static final int SIZE = 26;
    private TrieTreeNode root;

    private class TrieTreeNode {
        boolean isEnd;
        TrieTreeNode[] childNodes;

        public TrieTreeNode() {
            isEnd = false;
            childNodes = new TrieTreeNode[SIZE];
        }
    }

    public TrieTree() {
        root = new TrieTreeNode();
    }

    public void insert(String str) {
        if (StringUtils.isBlank(str)) {
            return;
        }
        TrieTreeNode node = root;
        for (int i = 0; i < str.length(); i++) {
            int index = str.charAt(i) - 'a';
            if (node.childNodes[index] == null) {
                // 创建新节点
                TrieTreeNode newNode = new TrieTreeNode();
                node.childNodes[index] = newNode;
            }
            node = node.childNodes[index];
        }
        node.isEnd = true;
    }

    public boolean search(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        TrieTreeNode node = root;
        for (int i = 0; i < str.length(); i++) {
            int index = str.charAt(i) - 'a';
            if (node.childNodes[index] == null) {
                return false;
            }
            node = node.childNodes[index];
        }
        return node.isEnd;
    }
}
