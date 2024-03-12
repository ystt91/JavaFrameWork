package org.zerock.b01.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.b01.dto.BoardDTO;
import org.zerock.b01.dto.BoardListReplyCountDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.service.BoardService;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        //PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
        PageResponseDTO<BoardListReplyCountDTO> responseDTO = boardService.listWithReplyCount(pageRequestDTO);

        log.info(responseDTO);

        model.addAttribute("responseDTO", responseDTO);
    }

    @GetMapping("/register")
    public void registerGET(){}

    @PostMapping("/register")
    public String registerPost(@Valid BoardDTO boardDTO, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){
        log.info("board POST register......");

        if(bindingResult.hasErrors()){
            log.info("has errors.....");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/board/register";
        }

        log.info(boardDTO);

        Long bno = boardService.register(boardDTO);

        redirectAttributes.addFlashAttribute("result", bno);

        return "redirect:/board/list";

    }

    @GetMapping({"/read","/modify"})
    public void read(Long bno, PageRequestDTO pageRequestDTO, Model model){
        BoardDTO boardDTO = boardService.readOne(bno);
        model.addAttribute("dto",boardDTO);
    }

    @PostMapping("/modify")
    public String modify(PageRequestDTO pageRequestDTO,
                         @Valid BoardDTO boardDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){
        log.info("board modify post......" + boardDTO);

        //에러 발생할 경우
        if(bindingResult.hasErrors()){
            log.info("has errors......");
            String link = pageRequestDTO.getLink();
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("bno", boardDTO.getBno());
            return "redirect:/board/modify?"+link;
        }

        // 무난하게 수정
        boardService.modify(boardDTO);

        //Flash Attribute: 리다이렉션 후 한 번만 사용할 수 있는 데이터입니다.
        redirectAttributes.addFlashAttribute("result", "modified");

        //Attribute: 리다이렉션 후에도 계속 사용할 수 있는 데이터입니다.
        redirectAttributes.addAttribute("bno", boardDTO.getBno());

        return "redirect:/board/read";
        //단순 수정 작업 후 다시 조회 페이지(read)로 갑니다.
        //why? 수정 후에는 검색 조건에 안 맞을 수도 있으므로

    }

    @PostMapping("/remove")
    public String remove(Long bno, RedirectAttributes redirectAttributes){
        log.info("remove post.." + bno);
        boardService.remove(bno);
        redirectAttributes.addFlashAttribute("result", "removed");
        return "redirect:/board/list";
    }
}
