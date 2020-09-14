package com.denissinkov.simpleposter.queue;

import com.denissinkov.simpleposter.domain.Post;

public interface MessagePublisher {
    void publish(final Post post);
}
