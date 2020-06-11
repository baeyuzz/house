package com.ssafy.happyhouse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ssafy.happyhouse.dto.QnA;

@Mapper
@Repository
public interface QnADao {
	List<QnA> selectAll();
	QnA selectOne(int qna_no);
	int insertQnA(QnA qna);
	int updateQnA(QnA qna);
	int deleteQnA(int qna_no);
	int setReply(QnA qna);
}
