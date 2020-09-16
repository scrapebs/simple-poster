package com.denissinkov.simpleposter.queue;

import com.denissinkov.simpleposter.domain.Post;
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
        redisTemplate.convertAndSend(topic.getTopic(), post);
        logger.debug("Message was published");
    }
}
