package com.carl.curator.listener;

import com.carl.curator.client.DefaultCuratorClient;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * 特定节点监听事件
 *
 * @author zjx
 * @date 2022/8/4 14:29
 */
@Component
public class NodeCacheListener {

    private static final Logger logger = LoggerFactory.getLogger(NodeCacheListener.class);

    @Resource
    private DefaultCuratorClient client;

    @PostConstruct
    public void init() throws Exception {
        String path = "/test";
        final NodeCache nodeCache = new NodeCache(client.getClient(), path);
        nodeCache.start();
        nodeCache.getListenable().addListener(() -> {
            ChildData childData = nodeCache.getCurrentData();
            if (childData != null) {
                logger.info("node happen update, data node's content: {}", new String(childData.getData(), StandardCharsets.UTF_8));
            } else {
                logger.info("</test> node has been deleted");
            }
        });
    }
}
