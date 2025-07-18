package com.koreait.restapi.service;

import com.koreait.restapi.dto.BoardDTO;
import com.koreait.restapi.mapper.BoardMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;

    public BoardServiceImpl(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    @Override
    public boolean writeBoard(BoardDTO board) {
        System.out.println("📌 BoardDTO to insert: " + board);

        try {
            int rows = boardMapper.insertBoard(board);
            System.out.println("✅ insertBoard() 반환값 (row 수): " + rows);
            return rows > 0;
        } catch (Exception e) {
            System.out.println("❌ 예외 발생:");
            e.printStackTrace(); // 반드시 전체 스택트레이스 출력
            return false;
        }
    }

    @Override
    public List<BoardDTO> getBoardList(int page, int size) {
        int offset = (page - 1) * size;
        return boardMapper.selectBoardList(offset, size);
    }

    @Override
    public int getBoardCount() {
        return boardMapper.selectBoardCount();
    }

    @Override
    public BoardDTO getBoardById(int id) {
        return boardMapper.selectBoardById(id);
    }

    @Override
    public boolean updateBoard(BoardDTO board) {
        return boardMapper.updateBoard(board) > 0;
    }

    @Override
    public boolean deleteBoard(int id, int userId) {
        BoardDTO board = boardMapper.selectBoardById(id);
        if (board == null || !board.getWriterId().equals(userId)) {
            return false;
        }
        return boardMapper.deleteBoard(id) > 0;
    }

    @Override
    public List<BoardDTO> findByWriterId(int memberId) {
        return boardMapper.findByWriterId(memberId);
    }
    @Override
    public BoardDTO findById(int id) {
        return boardMapper.findById(id);
    }
}