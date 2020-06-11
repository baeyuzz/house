package com.ssafy.happyhouse.service;

import java.util.List;

import com.ssafy.happyhouse.dto.QnA;

public interface QnAService {

	public List<QnA> getAllQnA();
	public QnA getQnA(int qna_no);
	public boolean addQnA(QnA qna);
	public boolean modifyQnA(QnA qna);
	public boolean deleteQnA(int qna_no);
	public boolean addReply(QnA qna);
}
