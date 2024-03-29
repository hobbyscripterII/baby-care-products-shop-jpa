package com.baby.babycareproductsshop.board;

import com.baby.babycareproductsshop.board.model.*;
import com.baby.babycareproductsshop.common.PageNation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    int insBoard(BoardInsDto dto);

    int updBoard(BoardUpdDto dto);

    int delBoard(BoardDelDto dto);

    BoardSelVo selBoard(int iboard);

    List<BoardGetVo> getBoard(PageNation.Criteria criteria);

    int insBoardPic(BoardPicsDto dto);

    int insBoardPics(BoardPicsDto dto);

    int delBoardPics(int iboard);

    List<String> selBoardPics(int iboard);

    List<BoardSelVo.comment> getComment(int iboard);

    int getPostCnt(PageNation.Criteria criteria);
}