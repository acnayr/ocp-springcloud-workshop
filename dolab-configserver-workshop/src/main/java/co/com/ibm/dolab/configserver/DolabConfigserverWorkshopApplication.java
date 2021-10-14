package co.com.ibm.dolab.configserver;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.KeyspaceEventMessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Slf4j
@EnableConfigServer
@SpringBootApplication
public class DolabConfigserverWorkshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(DolabConfigserverWorkshopApplication.class, args);
    }

}
