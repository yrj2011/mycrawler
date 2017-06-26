package com.mycrawler.configure;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import us.codecraft.webmagic.Site;

public class BasicConfiguration extends AbstractConfiguration {
    protected Site site;

    protected String baseDir;

    public BasicConfiguration(String path) {
        super(path);
    }

    public BasicConfiguration() {
    }

    @Override
    protected void resolve() {
        JSONObject jsonObject = JSON.parseObject(config);
        site = JSON.parseObject(jsonObject.getString("site"), Site.class);
     /*   List<String[]> poolHosts = new ArrayList<String[]>();
        poolHosts.add(new String[]{"username","password","101.101.101.101","8888"});
        poolHosts.add(new String[]{"username","password","102.102.102.102","8888"});
        //httpProxyList输入是IP+PORT, isUseLastProxy是指重启时是否使用上一次的代理配置
        site.setHttpProxyPool(poolHosts,false);*/
        setBaseDir(jsonObject.getString("base_dir"));
    }

    private void setBaseDir(String directory) {
        baseDir = directory.endsWith("/") ? directory : directory + "/";
    }

    public String getBaseDir() {
        return baseDir;
    }

    public Site getSite() {
        return site;
    }

}
