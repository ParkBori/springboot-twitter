package com.apiece.springboot_twitter.post;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
// JPA 에서 이클래스의 복제 버전을 만들어서 사용하는데
// 복제된 클래스를 프록시라고 한다.
// 프록시에서는 기본 생성자를 필요로 해서, 매개변수가 없는
// 기본 생성자가 필요하다. 그 기능을 아래의 애노테이션에서 한다.
// 또한, 내가 직접 Post 객체를 생성할때는, 내용이 null 이면 안된다.
// 그렇다면, 기본 생성자는 외부에서 호출할수 없이 막아야 한다
// 그렇기때문에 내부에 access 레벨을 지정해준다
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private int commentCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public void updateContent(String content) {
        this.content = content;
    }

    public void increaseCommentCount() {
        this.commentCount++;
    }

    public void decreaseCommentCount() {
        this.commentCount--;
    }
}
