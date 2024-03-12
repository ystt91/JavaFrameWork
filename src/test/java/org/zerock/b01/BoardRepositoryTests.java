package org.zerock.b01;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.BoardImage;
import org.zerock.b01.dto.BoardListReplyCountDTO;
import org.zerock.b01.repository.BoardRepository;
import org.zerock.b01.repository.ReplyRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void testInsert(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Board board = Board.builder()
                    .title("title..." + i)
                    .content("content..." + i)
                    .writer("user" + (i%10))
                    .build();

            Board result = boardRepository.save(board);
            log.info("BNO " + result.getBno());
        });
    }

    @Test
    public void testFind(){
        Long bno = 30L;

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();

        log.info(board);
    }

    @Test
    public void testUpdate(){
        Long bno = 33L;

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();

        board.change("update..title 33", "update content 33");

        boardRepository.save(board);

    }


    @Test
    public void testDelete(){
        Long bno = 1L;

        boardRepository.deleteById(bno);
    }

    @Test
    public void testPaging(){
        Pageable pageable = PageRequest.of
                (0,10, Sort.by("bno").descending());

        Page<Board> result = boardRepository.findAll(pageable);

        log.info("total count " + result.getTotalElements());
        log.info("total pages " + result.getTotalPages());
        log.info("page number " + result.getNumber());
        log.info("page size : " + result.getSize());

        List<Board> todoList = result.getContent();

        todoList.forEach(board -> log.info(board));
    }

    @Test
    public void testSearch1(){
        Pageable pageable = PageRequest.of(1,10,Sort.by("bno").descending());
        boardRepository.search1(pageable);
    }

    @Test
    public void testSearchAll(){
        String[] types = {"t","c","w"};

        String keyword = "1";

        Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending());

        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);
    }

    @Test
    public void testSearchAll2(){
        String[] types = {"t","c","w"};

        String keyword = "1";

        Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending());

        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);

        log.info(result.getTotalPages());

        log.info(result.getSize());

        log.info(result.getNumber());

        log.info(result.hasPrevious() + ": " + result.hasNext());

        result.getContent().forEach(board -> log.info(board));
    }

    @Test
    public void testSearchReplyCOunt(){
        String[] types = {"t","c","w"};
        String keyword = "1";

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<BoardListReplyCountDTO> result = boardRepository.searchWithReplyCount(types, keyword, pageable);

        //전체 페이지
        log.info(result.getTotalPages());
        //페이지 사이즈
        log.info(result.getSize());
        //페이지 넘버
        log.info(result.getNumber());
        //이전 이후
        log.info(result.hasPrevious() + ":" +result.hasNext());

        result.getContent().forEach(board -> log.info(board));

    }

    @Test
    public void testInsertWithImages(){
        Board board = Board.builder()
                .title("Image Test")
                .content("첨부파일 테스트")
                .writer("tester")
                .build();

        //게시물 하나에 3개의 첨부파일 추가하는 경우 가정
        //board 테이블에 1번 board_image 테이블에 3번 insert가 일어남
        for (int i = 0; i <3; i++) {
            board.addImage(UUID.randomUUID().toString(), "file" + i + ".jpg");
        }//end for

        boardRepository.save(board);
    }


    @Test
    public void testReadWithImages(){

        //반드시 존재하는 bno로 확인
        //Board 객체와 BoardImage 객체들을 생성해야 하므로 2번의 select가 필요하다.

        Optional<Board> result = boardRepository.findById(1L);

        Board board = result.orElseThrow();

        log.info(board);
        log.info("----------");
        log.info(board.getImageSet());

        for (BoardImage boardImage: board.getImageSet()){
            log.info(boardImage);
        }

    }

    @Test
    @Transactional
    @Commit
    public void testModifyImages(){
        Optional<Board> result = boardRepository.findByWithImages(1L);

        Board board = result.orElseThrow();

        board.clearImages();

        for (int i = 0; i < 2; i++) {
            board.addImage(UUID.randomUUID().toString(), "updatefile" + i + ".jpg");
        }

        boardRepository.save(board);

    }

    @Test
    @Transactional
    @Commit
    public void testRemoveAll(){
        Long bno = 1L;

        replyRepository.deleteByBoard_Bno(bno);

        boardRepository.deleteById(bno);
    }


    @Test
    public void testInsertAll(){
        for (int i = 0; i < 100; i++) {
            Board board = Board.builder()
                    .title("Title..." + i)
                    .content("Content..." + i)
                    .writer("writer..." + i)
                    .build();

            for (int j = 0; j < 3; j++) {
                if(i%5 == 0){
                    continue;
                }
                board.addImage(UUID.randomUUID().toString(), i + "file" + j + ".jpg");
            }
            boardRepository.save(board);
        }
    }

    @Transactional
    @Test
    public void testSearchImageReplyCount(){
        Pageable page = PageRequest.of(0,10,Sort.by("bno").descending());
        boardRepository.searchWithAll(null, null, page);
    }


}
