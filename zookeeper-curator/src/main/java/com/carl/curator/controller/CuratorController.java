package com.carl.curator.controller;

import com.carl.curator.client.DefaultCuratorClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * @author zjx
 * @date 2022/8/4 14:35
 */
@RestController
@RequestMapping("/zk")
public class CuratorController {

    private static final Logger logger = LoggerFactory.getLogger(CuratorController.class);

    @Resource
    private DefaultCuratorClient client;

    @GetMapping("/update")
    public String test1(@RequestParam String msg) throws Exception{
        logger.info("节点设置内容: {}", msg);
        client.getClient().setData().forPath("/test",msg.getBytes(StandardCharsets.UTF_8));
        return "ok";
    }
}
