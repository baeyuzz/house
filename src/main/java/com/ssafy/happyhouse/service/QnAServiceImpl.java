package com.ssafy.happyhouse.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dao.QnADao;
import com.ssafy.happyhouse.dto.QnA;
import com.ssafy.happyhouse.dto.Reply;

@Service
public class QnAServiceImpl implements QnAService {

	private QnADao dao;

	@Autowired
	public void setDao(QnADao dao) {
		this.dao = dao;
	}

	@Override
	public QnA getQnA(int qna_no) {
		return dao.selectOne(qna_no);
	}

	@Override
	public boolean addQnA(QnA qna) {
		return dao.insertQnA(qna) > 0;
	}

	@Override
	public boolean modifyQnA(QnA qna) {
		return dao.updateQnA(qna) > 0;
	}

	@Override
	public boolean deleteQnA(int qna_no) {
		return dao.deleteQnA(qna_no) > 0;
	}

	@Override
	public boolean addReply(Reply reply) {
		dao.updateReplyCnt(reply.getQno());
		return dao.setReply(reply) > 0;
	}

	@Override
	public int countQna() {
		// TODO Auto-generated method stub
		return dao.countQna();
	}

	@Override
	public List<QnA> getAllQnA(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return dao.selectAll(map);
	}

	@Override
	public List<QnA> searchQna(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return dao.searchQna(map);
	}

	@Override
	public List<Reply> replyList(int qno) {
		return dao.replyList(qno);
	}

	@Override
	public Boolean deleteReply(int no) {
		if (dao.minusReplyCnt(no) <= 0)
			return false;
		return dao.deleteReply(no) > 0;
	}

	@Override
	public boolean updateHitCount(int qna_no) {
		return dao.updateHitCount(qna_no) > 0;
	}

}
