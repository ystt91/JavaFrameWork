package org.zerock.b01.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardListReplyCountDTO {
    
    //게시물에 댓글 수를 보여 주기 위해 DTO 추가 선언
    
    private Long bno;
    private String title;
    private String writer;
    private LocalDateTime regDate;

    private Long replyCount;

}
