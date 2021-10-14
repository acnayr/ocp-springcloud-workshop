package co.com.ibm.dolab.configserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.bus.event.RefreshRemoteApplicationEvent;
import org.springframework.cloud.config.monitor.CompositePropertyPathNotificationExtractor;
import org.springframework.cloud.config.monitor.PropertyPathNotification;
import org.springframework.cloud.config.monitor.PropertyPathNotificationExtractor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyspaceEventMessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RedisModificationListener extends KeyspaceEventMessageListener {

    private static final Topic KEYEVENT_TOPIC = new PatternTopic("__keyevent@*__:hset");


    public void fireRefreshEvent() {
        try {
            final String url = "http://localhost:8888/monitor";
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            headers.add("X-Event-Key", "repo:push");
            headers.add("X-Hook-UUID", "webhook-uuid");

            Map<String, Object> request = new ObjectMapper().readValue("{\"push\": {\"changes\": []} }", Map.class);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

            if (response.getStatusCode() == HttpStatus.CREATED) {
                log.info("Request Successful");
                log.info(response.getBody());
            } else {
                log.warn("Request Failed >> " + response.getStatusCode());
            }
        } catch (JsonProcessingException e) {
            log.error("Error on Refresh Event, details; " + e.getMessage());
        }
    }


    /**
     * @param listenerContainer
     */
    public RedisModificationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * @param listenerContainer
     */
    protected void doRegister(RedisMessageListenerContainer listenerContainer) {
        listenerContainer.addMessageListener(this, KEYEVENT_TOPIC);
    }


    protected void doHandleMessage(Message message) {
        log.info("message body >>>> " + message.toString());
        fireRefreshEvent();
    }
}
