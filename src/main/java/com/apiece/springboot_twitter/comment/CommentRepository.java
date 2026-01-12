package com.apiece.springboot_twitter.comment;

import com.apiece.springboot_twitter.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 게시글의 모든 댓글을 시간 순으로 가져오기

    /*
    * find -> select
    * By -> where
    *
    * OrderBy[필드][Asc|Desc] -> order by 필드 desc
    * */

    List<Comment> findByPostIdOrderByIdDesc(Long postID);


    Optional<Comment> findByIdAndPostId(Long commentId, Long postId);

}
