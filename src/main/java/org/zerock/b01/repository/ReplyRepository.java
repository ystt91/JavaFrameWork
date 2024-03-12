package org.zerock.b01.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.b01.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    //JPQL로 게시물의 댓글 목록을 조회하였다.
    @Query("select r from Reply r where r.board.bno = :bno")
    Page<Reply> listOfBoard(Long bno, Pageable pageable);

    void deleteByBoard_Bno(Long bno);
}
