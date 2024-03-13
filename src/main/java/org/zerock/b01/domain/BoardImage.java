package org.zerock.b01.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
public class BoardImage implements Comparable<BoardImage>{
                                    //BoardImage 끼리 순번에 맞게 정렬하기 위함
    @Id
    private String uuid;

    private String fileName;

    private int ord;

    @ManyToOne
    private Board board;

    @Override
    public int compareTo(BoardImage other){
        return this.ord - other.ord;
    }

    // board 객체 지정 하게 하여
    // board 엔티티 삭제시 boardImage 객체 참조도 변경하기 위해
    public void changeBoard(Board board){
        this.board = board;
    }
}
