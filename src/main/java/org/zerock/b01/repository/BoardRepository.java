package org.zerock.b01.repository;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.b01.domain.Board;
import org.zerock.b01.repository.search.BoardSearch;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long>, BoardSearch {

//    Page<Board> findByTitleContainingOrderByBnoDesc(String keyword, Pageable pageable);

//    @Query("select b from Board b where b.title like concat('%',:keyword,'%')")
//    Page<Board> findKeyword(String keyword, Pageable pageable);

//    @Query(value="select now()", nativeQuery = true)
//    String getTime();


    @EntityGraph(attributePaths = {"imageSet"})
    @Query("select b from Board b where b.bno=:bno")
    Optional<Board> findByWithImages(Long bno);
}
