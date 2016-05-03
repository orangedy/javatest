/*
 * @(#) EHCache.java 2016年1月14日
 * 
 * Copyright 2010 NetEase.com, Inc. All rights reserved.
 */
package cache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.CacheManagerBuilder;
import org.ehcache.config.CacheConfigurationBuilder;
import org.junit.Test;

/**
 *
 * @author hzdingyong
 * @version 2016年1月14日
 */
public class EHCacheTest {

    @Test
    public void test() {
        CacheManager cacheManager = CacheManagerBuilder
                        .newCacheManagerBuilder()
                        .withCache("preConfigured",
                                   CacheConfigurationBuilder.newCacheConfigurationBuilder().buildConfig(Long.class,
                                                                                                        String.class))
                        .build(true);

        Cache<Long, String> preConfigured = cacheManager.getCache("preConfigured", Long.class, String.class);
        Cache<Long, String> myCache = cacheManager.createCache("myCache", CacheConfigurationBuilder
                        .newCacheConfigurationBuilder().buildConfig(Long.class, String.class));

        myCache.put(1L, "da one!");
        String value = myCache.get(1L);

        cacheManager.close();
    }

}
