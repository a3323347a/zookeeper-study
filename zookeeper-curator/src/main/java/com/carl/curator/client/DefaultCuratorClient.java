package com.carl.curator.client;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * zk客户端
 *
 * @author zjx
 * @date 2022/8/4 11:40
 */
@Component
@ConditionalOnProperty("zookeeper.address")
public class DefaultCuratorClient {

    @Value("${zookeeper.address}")
    private String zkAddress;

    private CuratorFramework client = null;

    @PostConstruct
    public void initClient() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3);
        client = CuratorFrameworkFactory.builder()
                .connectString(zkAddress)
                .sessionTimeoutMs(50000)
                .connectionTimeoutMs(30000)
                .retryPolicy(retryPolicy)
                .namespace("base") //不同业务不同的命名空间，即根路径
                .build();
        client.start();
    }

    public CuratorFramework getClient() {
        if (client == null) {
            throw new RuntimeException("can not find zookeeper client");
        }
        return client;
    }

    @PreDestroy
    public void destroy() {
        if (client != null) {
            client.close();
        }
    }
}
