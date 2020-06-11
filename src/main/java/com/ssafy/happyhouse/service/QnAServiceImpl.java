package com.ssafy.happyhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dao.QnADao;
import com.ssafy.happyhouse.dto.QnA;

@Service
public class QnAServiceImpl implements QnAService{

	private QnADao dao;
	
	@Autowired
	public void setDao(QnADao dao) {
		this.dao = dao;
	}
	
	@Override
	public List<QnA> getAllQnA() {
		return dao.selectAll(); 
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
	public boolean addReply(QnA qna) {
		return dao.setReply(qna) > 0;
	}

}
