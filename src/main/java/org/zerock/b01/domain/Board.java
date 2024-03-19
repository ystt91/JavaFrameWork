package org.zerock.b01.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(length = 50, nullable = false)
    private String writer;

    //    @OneToMany private Set<BoardImage> imageSet = new HashSet<>(); : 이 줄은 게시글과 이미지의 일대다 관계를 정의합니다.
    //    @OneToMany 애노테이션은 하나의 게시글이 여러 개의 이미지를 가질 수 있음을 나타냅니다.
    //    Set<BoardImage>는 이 게시글에 속한 이미지 객체들의 집합을 저장하는 필드입니다.
    //    HashSet은 중복을 허용하지 않는 집합의 구현입니다. JPA에서 성능상의 이점이 있어서 사용됩니다.


    @OneToMany(mappedBy = "board", //BoardImage의 board 변수
                cascade = {CascadeType.ALL},
                fetch = FetchType.LAZY,
                orphanRemoval = true)
    @Builder.Default
    @BatchSize(size = 20)
    private Set<BoardImage> imageSet = new HashSet<>();
//    orphanRemoval 속성은 JPA에서 일대다 관계 매핑 시 사용되는 옵션이며, "고아 객체 제거" 기능을 활성화합니다.
//
//    일대다 관계란 하나의 엔티티 (부모)가 여러 개의 다른 엔티티 (자식)를 가질 수 있는 관계를 말합니다.
//    이 코드에서 Board 엔티티는 BoardImage 엔티티와 일대다 관계를 가지며, Board 가 부모, BoardImage 가 자식 엔티티입니다.
//
//    고아 객체란 부모 엔티티와의 연관 관계가 끊어졌지만 여전히 데이터베이스에 존재하는 자식 객체를 말합니다.
//    예를 들어, Board 객체에서 특정 BoardImage 객체를 제거했을 때 해당 BoardImage 객체가 다른 곳에서 참조되지 않으면 고아 객체가 될 수 있습니다.
//
//    orphanRemoval 속성을 true로 설정하면 JPA는 다음과 같은 동작을 수행합니다.
//
//    부모 엔티티가 영속 상태에서 제거되거나 (영속 컨텍스트에서 detach 됨)
//    자식 엔티티와의 연관 관계가 끊어질 때 (예: Board 객체에서 BoardImage 객체를 제거)
//    JPA는 자동으로 해당 자식 엔티티 (고아 객체)를 데이터베이스에서도 함께 삭제합니다. 이를 통해 데이터베이스의 불필요한 데이터 누적을 방지할 수 있습니다.
    public void addImage(String uuid, String fileName){
        BoardImage boardImage = BoardImage.builder()
                .uuid(uuid)
                .fileName(fileName)
                .board(this)
                .ord(imageSet.size())
                .build();

        imageSet.add(boardImage);
    }

    public void clearImages(){
        imageSet.forEach(boardImage -> boardImage.changeBoard(null));
        this.imageSet.clear();
    }

    public void change(String title, String content){
        this.title = title;
        this.content = content;
    }
}
