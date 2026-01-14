package com.apiece.springboot_twitter.comment;


import com.apiece.springboot_twitter.post.Post;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)  // -> 해당 값을 가져올때 쿼리 문이 발생
    @JoinColumn(name = "post_id")
    private Post post;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public void updateContent(String content) {
        this.content = content;
    }
}
