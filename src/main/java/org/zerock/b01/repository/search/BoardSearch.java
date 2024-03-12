package org.zerock.b01.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.BoardListReplyCountDTO;

//Querydsl을 이용할 인터페이스
public interface BoardSearch {

    //단순 페이지 처리만 할 것
    Page<Board> search1(Pageable pageable);

    Page<Board> searchAll(String[] types, String keyword, Pageable pageable);

    Page<BoardListReplyCountDTO> searchWithReplyCount(String[]types, String keyword, Pageable pageable);

    Page<BoardListReplyCountDTO> searchWithAll(String[] types, String keyword, Pageable pageable);
}