package com.mycrawler.crawler.config.impl;

import org.springframework.stereotype.Component;

import com.mycrawler.crawler.config.BasicConfiguration;

@Component
public class IPProxyConfiguration extends BasicConfiguration {

    public static final String SUBDIR_MEMBER = "member/";
    public static final String SUBDIR_FOLLOWEE = "followee/";

    public IPProxyConfiguration(String path) {
        super(path);
    }

    public IPProxyConfiguration() {

    }

    public String getMemberPath() {
        return getBaseDir() + SUBDIR_MEMBER;
    }

    public String getFolloweePath() {
        return getBaseDir() + SUBDIR_FOLLOWEE;
    }

    public String getMemberDataPath() {
        return getMemberPath() + site.getDomain() + "/";
    }

    public String getFolloweeDataPath() {
        return getFolloweePath() + site.getDomain() + "/";
    }

    public static void main(String[] args) {
        IPProxyConfiguration configuration = new IPProxyConfiguration();
        System.out.println(configuration.getSite());
        System.out.println(configuration.getBaseDir());
        System.out.println(configuration.getMemberPath());
    }

}
