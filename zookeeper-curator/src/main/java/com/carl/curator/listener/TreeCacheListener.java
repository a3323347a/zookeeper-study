package com.carl.curator.listener;

import com.carl.curator.client.DefaultCuratorClient;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * @author zjx
 * @date 2022/8/4 16:37
 */
@Component
public class TreeCacheListener {

    private static final Logger logger = LoggerFactory.getLogger(TreeCacheListener.class);

    @Resource
    private DefaultCuratorClient client;

    @PostConstruct
    public void init() throws Exception {
        String path = "/test_parent";
        final TreeCache treeCache = new TreeCache(client.getClient(), path);
        treeCache.start();
        treeCache.getListenable().addListener((curatorFramework, treeCacheEvent) -> {
            switch (treeCacheEvent.getType()) {
                case NODE_ADDED:
                    logger.info("NODE_ADDED: {}", treeCacheEvent.getData().getPath());
                    break;
                case NODE_REMOVED:
                    logger.info("NODE_REMOVED: {}", treeCacheEvent.getData().getPath());
                    break;
                case NODE_UPDATED:
                    logger.info("NODE_UPDATED: {}", treeCacheEvent.getData().getPath());
                    logger.info("data node's content: {}", new String(treeCacheEvent.getData().getData(), StandardCharsets.UTF_8));
                    break;
                case CONNECTION_LOST:
                    logger.info("CONNECTION_LOST: {}", treeCacheEvent.getData().getPath());
                    break;
                case CONNECTION_RECONNECTED:
                    logger.info("CONNECTION_RECONNECTED: {}", treeCacheEvent.getData().getPath());
                    break;
                case CONNECTION_SUSPENDED:
                    logger.info("CONNECTION_SUSPENDED: {}", treeCacheEvent.getData().getPath());
                    break;
                case INITIALIZED:
                    logger.info("INITIALIZED: {}", treeCacheEvent.getData().getPath());
                    break;
                default:
                    break;
            }
        });
    }
}
