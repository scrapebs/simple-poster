package com.denissinkov.simpleposter.queue;

import com.denissinkov.simpleposter.service.ProcessRedisResponseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Service;

@Service
public class RedisMessageSubscriber implements MessageListener {

    Logger logger = LoggerFactory.getLogger(MessageListener.class);

    private RedisTemplate redisTemplate;
    private ProcessRedisResponseService processRedisResponseService;

    @Autowired
    public RedisMessageSubscriber(RedisTemplate redisTemplate, ProcessRedisResponseService processRedisResponseService) {
        this.redisTemplate = redisTemplate;
        this.processRedisResponseService = processRedisResponseService;
    }

    @Override
    public void onMessage(final Message message, final byte[] pattern) {
        logger.debug("Message was received");

        processRedisResponseService.processResponse(message.toString());
    }
}
