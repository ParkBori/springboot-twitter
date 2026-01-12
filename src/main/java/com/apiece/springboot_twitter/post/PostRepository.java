package com.apiece.springboot_twitter.post;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 여기서 아래의 인터페이스에 작성한 함수들은
    // 별도의 선언이 없이도 작동하는데, 그 이유는 무엇일까?

    /*

    * */

    Slice<Post> findSlicesBy(Pageable pageable);

    Slice<Post> findSlicesByIdLessThan(Long lastId, Pageable pageable);
}
