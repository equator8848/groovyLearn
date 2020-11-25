package com.equator.learn.dynamic.groovy;

import com.equator.learn.dynamic.base.MessageQueue;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * @author libinkai
 * @date 2020/11/25 12:33 上午
 */
@Slf4j
public class DynamicTransformation implements Runnable {
    private Jedis jedis;
    private String currentVersion;
    private GroovyObject instance;

    public DynamicTransformation() {
        this.jedis = MessageQueue.getMQ().getClient();
    }

    private String getConfig(String key) {
        return jedis.get(key);
    }

    private void start() {
        log.info("Dynamic转换启动，有点慢...");
        try {
            Thread.sleep(1000 * 15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Dynamic转换终于启动了！");
        // 获取脚本版本与脚本
        currentVersion = getConfig("transformation_version");
        String transformationScript = getConfig("transformation_script");
        // 解析脚本构建实例
        GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
        Class parseClass = groovyClassLoader.parseClass(transformationScript);
        try {
            instance = (GroovyObject) parseClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                instance.invokeMethod("transform", null);
                String latestVersion = getConfig("transformation_version");
                Thread.sleep(1000);
                if (!currentVersion.equals(latestVersion)) {
                    System.out.printf("切换版本，当前版本 %s, 最新版本 %s %n", currentVersion, latestVersion);
                    // 去掉此行可以引发Full GC
                    currentVersion = latestVersion;
                    groovyClassLoader = new GroovyClassLoader();
                    transformationScript = getConfig("transformation_script");
                    parseClass = groovyClassLoader.parseClass(transformationScript);
                    instance = (GroovyObject) parseClass.newInstance();
                }
            } catch (InstantiationException | IllegalAccessException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        start();
    }

    public static void main(String[] args) {
        new Thread(new DynamicTransformation()).start();
    }
}
