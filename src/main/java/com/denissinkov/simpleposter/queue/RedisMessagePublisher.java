package com.denissinkov.simpleposter.queue;

import com.denissinkov.simpleposter.domain.Post;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class RedisMessagePublisher implements MessagePublisher{

    Logger logger = LoggerFactory.getLogger(RedisMessagePublisher.class);

    private RedisTemplate<String, Object> redisTemplate;
    private ChannelTopic topic;

    @Autowired
    public RedisMessagePublisher(final RedisTemplate<String, Object> redisTemplate, @Qualifier("topicSource") final ChannelTopic topic) {
        this.redisTemplate = redisTemplate;
        this.topic = topic;
    }

    @Override
    public void publish(Post post) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            redisTemplate.convertAndSend(topic.getTopic(), mapper.writeValueAsString(post));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        logger.debug("Message was published");
    }
}
