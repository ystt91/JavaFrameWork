package org.zerock.b01.domain;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Reply", indexes = {
        @Index(name = "idx_reply_board_bno", columnList = "board_bno")
})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    @ManyToOne(fetch = FetchType.LAZY)
    //엔티티를 조회할 때 연관된 모든 엔티티를 즉시 로딩하지 않고 필요할 때만 로딩하는 방식
    //다대일 관계에서는 LAZY 사용할 것
    private Board board;
    //board 객체를 선언해서 board.id를 fk로 갖도록 한다.

    private String replyText;

    private String replyer;

    public void changeText(String text){
        this.replyText = text;
    }

}
