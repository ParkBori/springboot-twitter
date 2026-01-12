package com.apiece.springboot_twitter.dto;

import com.apiece.springboot_twitter.comment.Comment;
import com.apiece.springboot_twitter.comment.CommentRepository;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
