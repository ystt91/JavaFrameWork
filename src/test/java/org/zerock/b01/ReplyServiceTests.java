package org.zerock.b01;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.dto.ReplyDTO;
import org.zerock.b01.service.ReplyService;

@SpringBootTest
@Log4j2
public class ReplyServiceTests {

    @Autowired
    private ReplyService replyService;

    @Test
    public void testRegister(){
        ReplyDTO replyDTO = ReplyDTO.builder()
                .replyText("ReplyDTTO Text")
                .replyer("replyer")
                .bno(100L)
                .build();

        log.info(replyService.register(replyDTO));
    }
}
