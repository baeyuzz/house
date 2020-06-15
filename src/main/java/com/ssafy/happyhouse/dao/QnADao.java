package com.ssafy.happyhouse.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ssafy.happyhouse.dto.QnA;
import com.ssafy.happyhouse.dto.Reply;

@Mapper
@Repository
public interface QnADao {
	List<QnA> selectAll(HashMap<String, Object> map);
	QnA selectOne(int qna_no);
	int insertQnA(QnA qna);
	int updateQnA(QnA qna);
	int deleteQnA(int qna_no);
	int setReply(Reply reply);
	int countQna();
	List<QnA> searchQna(HashMap<String, Object> map);
	List<Reply> replyList(int qno);
	int deleteReply(int no);
	int updateHitCount(int qna_no);
	void updateReplyCnt(int qno);
	int minusReplyCnt(int no);
}
