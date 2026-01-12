package com.apiece.springboot_twitter.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor // 프라이벳 생성자 생성
public class PostController {

    private final PostRepository postRepository;

    // 게실글 목록 조회
    @GetMapping("/api/posts")
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    // 특정 게시글 조회
    @GetMapping("/api/posts/{id}")
    public Post getPost(@PathVariable Long id) {
        return postRepository.findById(id).orElseThrow();
    }

    // 새 게시글 작성
    @ResponseStatus(HttpStatus.CREATED)

    // 기존에 사용하던 ResponeEntiy<> 라는 형식으로, 응답에 대한 요청을 반환할 수 있다
    // 위의 애노테이션은 그역할을 대신해주는 것이다.
    // 물론 아무것도 없이 보내도, postman 쪽에서 파싱해서 인식해주긴 하지만,
    // 애노테이션 or ResponseEntity 중 선택해서 반환해주는게 맞다.
    @PostMapping("/api/posts")
    public Post createPost(@RequestBody Post post) {
        Post created = Post.builder()
                .content(post.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        postRepository.save(created);

        return created;
    }

    // 게시글 전체 수정
    @PutMapping("/api/posts/{id}")
    public Post updateAllPost(@PathVariable Long id, @RequestBody Post postDto) {

        return postRepository.findById(id)
                .map(post -> {
                    post.updateContent(postDto.getContent());
                    return postRepository.save(post);
                })
                .orElseThrow();
    }

    // 게시글 일부 수정
    @PatchMapping("/api/posts/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody Post postDto) {

        return postRepository.findById(id)
                .map(post -> {
                    post.updateContent(postDto.getContent());
                    return postRepository.save(post);
                })
                .orElseThrow();
    }

    @DeleteMapping("/api/delete/{id}")
    public void deletePost(@PathVariable Long id) {
//        if (false == posts.containsKey(id)) {
//            throw new Exception("잘못된 접근");
//        }
        postRepository.deleteById(id);
    }

    @GetMapping("/api/posts/search")
    public Slice<Post> searchPosts(
            @RequestParam(required = false) Long lastPostId,
            @RequestParam(defaultValue = "3") int size) {
        int page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        if (lastPostId == null) {
            return postRepository.findSlicesBy(pageable);
        } else {
            return postRepository.findSlicesByIdLessThan(lastPostId, pageable);
        }

    }
}
