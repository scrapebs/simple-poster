package com.denissinkov.simpleposter.domain;

public enum PostStatus {
    NEW, PUBLISHED, DELETED;

    public String getPostStatus() {
        return name();
    }
}

