package com.ssafy.happyhouse.service;

import java.util.HashMap;
import java.util.List;

import com.ssafy.happyhouse.dto.QnA;
import com.ssafy.happyhouse.dto.Reply;

public interface QnAService {

	public List<QnA> getAllQnA(HashMap<String, Object> map);
	public QnA getQnA(int qna_no);
	public boolean addQnA(QnA qna);
	public boolean modifyQnA(QnA qna);
	public boolean deleteQnA(int qna_no);
	public boolean addReply(Reply reply);
	public int countQna();
	public List<QnA> searchQna(HashMap<String, Object> map);
	public List<Reply> replyList(int qno);
	public Boolean deleteReply(int no);
	public boolean updateHitCount(int qna_no);
}
