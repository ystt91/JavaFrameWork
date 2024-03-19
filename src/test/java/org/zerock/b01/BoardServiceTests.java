package org.zerock.b01;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.BoardImage;
import org.zerock.b01.dto.*;
import org.zerock.b01.service.BoardService;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister(){
        log.info(boardService.getClass().getName());

        BoardDTO boardDTO = BoardDTO.builder()
                .title("Sample Title...")
                .content("Sample Content...")
                .writer("user00")
                .build();

        Long bno = boardService.register(boardDTO);

        log.info("bno : " + bno);

    }

    @Test
    public void testModify(){

        BoardDTO boardDTO = BoardDTO.builder() //필드 속성 부여
                .bno(100L)
                .title("Udated...101")
                .content("Updated content 101...")
                .build();

        boardService.modify(boardDTO);
    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tcw")
                .keyword("1")
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);

        log.info(responseDTO);
    }

    @Test
    public void testRegisterWithImages(){
        log.info(boardService.getClass().getName());

        BoardDTO boardDTO = BoardDTO.builder()
                .title("File...Sample Title...")
                .content("Sample Content")
                .writer("user00")
                .build();

        boardDTO.setFileNames(
                Arrays.asList(
                        UUID.randomUUID()+ "_aaa.jpg",
                        UUID.randomUUID()+ "_bbb.jpg",
                        UUID.randomUUID()+ "_bbb.jpg"
                )
        );

        Long bno = boardService.register(boardDTO);

        log.info("bno: " +bno);
    }

    @Test
    public void testReadOneAll(){
        Long bno = 102L;

        BoardDTO boardDTO = boardService.readOne(bno);

        log.info(boardDTO);

        for(String fileName : boardDTO.getFileNames()){
            log.info(fileName);
        } // end for
    }

    @Test
    public void testModifyImage(){

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(103L)
                .title("Updated...103")
                .content("Updated content 103...")
                .build();

        boardDTO.setFileNames(Arrays.asList(UUID.randomUUID()+ "_zzz.jpg"));

        boardService.modify(boardDTO);
    }

    @Test
    public void testRemoveAll(){
        Long bno = 2L;
        boardService.remove(bno);
    }

    @Test
    public void testListWithAll(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<BoardListAllDTO> responseDTO = boardService.listWithAll(pageRequestDTO);

        List<BoardListAllDTO> dtoList = responseDTO.getDtoList();

        dtoList.forEach(boardListAllDTO -> {
            log.info("-----------------------------------");
            log.info(boardListAllDTO.getBno()+":"+boardListAllDTO.getTitle());

            if(boardListAllDTO.getBoardImages() != null){
                for (BoardImageDTO boardImageDTO : boardListAllDTO.getBoardImages()){
                    log.info(boardImageDTO);
                }
            }

            log.info("-----------------------------------");
        });

    }


}
