package com.carl;

import com.carl.curator.CuratorApplication;
import com.carl.curator.client.DefaultCuratorClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author zjx
 * @date 2022/8/4 12:00
 */
@SpringBootTest(classes = CuratorApplication.class)
public class TestBaseOperation {

    @Resource
    private DefaultCuratorClient client;

    @Test
    public void test1() throws Exception {
        //创建节点，附带节点内容
        client.getClient().create().forPath("/test", "hello".getBytes(StandardCharsets.UTF_8));
    }

    @Test
    public void test2() throws Exception {
        //递归创建节点，即使父节点不存在
        client.getClient().create().creatingParentsIfNeeded().forPath("/test_parents/test", "hello".getBytes(StandardCharsets.UTF_8));
    }

    @Test
    public void test3() throws Exception {
        //删除一个节点，并且递归删除其所有的子节点
        client.getClient().delete().deletingChildrenIfNeeded().forPath("/test_parents");
    }

    @Test
    public void test4() throws Exception {
        //读取节点内容
        byte[] bytes = client.getClient().getData().forPath("/test");
        String s = new String(bytes,StandardCharsets.UTF_8);
        System.out.println(s);
    }

    @Test
    public void test5() throws Exception {
        //更新节点内容
        client.getClient().setData().forPath("/test","data".getBytes(StandardCharsets.UTF_8));
    }

}
