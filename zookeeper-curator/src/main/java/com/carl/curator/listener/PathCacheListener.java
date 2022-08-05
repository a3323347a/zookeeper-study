package com.carl.curator.listener;

import com.carl.curator.client.DefaultCuratorClient;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 只能监听节点的CRUD,不能监听节点的数据变化
 *
 * @author zjx
 * @date 2022/8/4 14:50
 */
@Component
public class PathCacheListener {

    private static final Logger logger = LoggerFactory.getLogger(PathCacheListener.class);

    @Resource
    private DefaultCuratorClient client;

    @PostConstruct
    public void init() throws Exception {
        String path = "/test_parent";
        final PathChildrenCache cache = new PathChildrenCache(client.getClient(), path, true);
        cache.start();
        cache.getListenable().addListener((curatorFramework, pathChildrenCacheEvent) -> {
            switch (pathChildrenCacheEvent.getType()) {
                case CHILD_ADDED:
                    logger.info("CHILD_ADDED: {}", pathChildrenCacheEvent.getData().getPath());
                    break;
                case CHILD_UPDATED:
                    logger.info("CHILD_UPDATED: {}", pathChildrenCacheEvent.getData().getPath());
                    break;
                case CHILD_REMOVED:
                    logger.info("CHILD_REMOVED: {}", pathChildrenCacheEvent.getData().getPath());
                    break;
                case CONNECTION_SUSPENDED:
                    logger.info("CONNECTION_SUSPENDED: {}", pathChildrenCacheEvent.getData().getPath());
                    break;
                case CONNECTION_RECONNECTED:
                    logger.info("CONNECTION_RECONNECTED: {}", pathChildrenCacheEvent.getData().getPath());
                    break;
                case CONNECTION_LOST:
                    logger.info("CONNECTION_LOST: {}", pathChildrenCacheEvent.getData().getPath());
                    break;
                case INITIALIZED:
                    logger.info("INITIALIZED: {}", pathChildrenCacheEvent.getData().getPath());
                    break;
            }
        });
    }
}
