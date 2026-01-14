package com.apiece.springboot_twitter.post;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostTest {

    @Test
    void increaseCommentCount() {
        Post post = Post.builder().build();
        post.increaseCommentCount();

        // 임시 테스트용
        // 테스트 123
        assertEquals(1, post.getCommentCount());
    }
}