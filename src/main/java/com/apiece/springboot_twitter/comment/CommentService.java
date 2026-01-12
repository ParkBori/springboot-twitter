package com.apiece.springboot_twitter.comment;

import com.apiece.springboot_twitter.dto.CommentRequest;
import com.apiece.springboot_twitter.dto.CommentResponse;
import com.apiece.springboot_twitter.post.Post;
import com.apiece.springboot_twitter.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@Service
/*
final 필드나 @NonNull 어노테이션이 붙은 필드에 대한 생성자를 자동으로 생성합니다.
생성자를 통한 의존성 주입을 간편하게 만들어줍니다.
코드의 양을 줄이고 가독성을 높입니다.

@Autowired를 사용한 필드 주입보다 여러 장점이 있다.
장점:
1. 불변성: final 필드를 사용하여 객체의 불변성을 보장할 수 있다.
2. 테스트 용이성: 의존성을 명시적으로 선언하여 단위 테스트가 쉽다.
3. 순환 의존성 탐지: 컴파일 시점에 순환 의존성을 쉽게 발견할 수 있다.
*/

/*
* 현재 서비스의 문제점이라고 할 수 있는 부분
* 여기는 comment 관련 서비스(비지니스로직)이 처리 되는 곳임
* 댓글이 삭제됨에 따라 게시글의 댓글 카운트가 증/감하게 되는 역할이
* 여기에 부여되어있음.
* */
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentResponse createComment(Long postId, CommentRequest request) {

        // 댓글을 작성할 게시물이 존재하는지 확인
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글을 찰을 수 없습니다"));

        // request dto -> entity
        Comment comment = Comment.builder()
                .content(request.content())
                .postId(postId)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // CoomentRepository 를 통해 commtent 를 db 에 저장
        Comment savedComment = commentRepository.save(comment);
        // 댓글 개수 늘리기
        post.increaseCommentCount();
        // PostRepositoy 를 통해 commtent 를 db 에 저장
        postRepository.save(post);
        return CommentResponse.from(savedComment);
    }

    public List<CommentResponse> getComments(Long postId) {

        // 댓글을 가져올 게시물이 존재한지 확인
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글을 찰을 수 없습니다"));

        return commentRepository.findByPostIdOrderByIdDesc(postId)
                .stream()
                .map(CommentResponse::from)// 람다 -> 메서드 래퍼런스 교체
                .toList();
    }

    public CommentResponse updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody CommentRequest request
    ) {
        Comment comment = commentRepository.findByIdAndPostId(commentId, postId).orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다"));

        comment.updateContent(request.content());
        commentRepository.save(comment);
        return CommentResponse.from(comment);
    }

    public void deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId
    ) {
        Comment comment = commentRepository.findByIdAndPostId(commentId, postId).orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다"));

        post.decreaseCommentCount();
        postRepository.save(post);
        commentRepository.delete(comment);
    }
}
