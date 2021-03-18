package org.anonymous.nocos;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/17 8:16
 */
public class ConfigClient {

    public static void main(String[] args) throws NacosException, IOException {
        String serverAddr = "localhost:8808";
        // String serverAddr = "192.168.3.147:8808";
        // String dataId = "nacos-config";
        String dataId = "aaa.yml";
        String group = "DEFAULT_GROUP";

        Properties prop = new Properties() {{
            put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        }};

        // final ConfigService configService = NacosFactory.createConfigService(serverAddr);
        final ConfigService configService = NacosFactory.createConfigService(prop);
        // NacosConfigService
        final String config = configService.getConfig(dataId, group, 1000L);

        configService.addListener(dataId, group, new Listener() {
            @Override
            public Executor getExecutor() {
                return null;
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                System.out.println("配置信息改变了。。。");
                System.out.println(configInfo);
            }
        });

        System.out.println(config);

        // 如果服务端没有对应的 dataId/group, 服务端会自动创建
        // 如果已经存在对应的 dataId/group, 则 content 会覆盖原始的文件内容.
        configService.publishConfig(dataId, "anewgroup", "客户端发布的内容");

        System.in.read();
    }

}
