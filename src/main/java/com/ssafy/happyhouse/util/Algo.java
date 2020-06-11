package com.ssafy.happyhouse.util;

import java.util.LinkedList;
import java.util.List;

import com.ssafy.happyhouse.dto.Notice;
import com.ssafy.happyhouse.dto.QnA;

public class Algo {

	private static int[] getPi(String pattern) {
		int len = pattern.length();
		int[] pi = new int[len];
		pi[0] = 0;
		int j = 0;
		for(int i = 1; i < len; i++) {
			while(j > 0 && pattern.charAt(i) != pattern.charAt(j)) j = pi[j - 1];
			
			if(pattern.charAt(i) == pattern.charAt(j)) pi[i] = ++j;
		}
		
		return pi;
	}
	
	private static boolean find(String title, String pattern, int[] pi) {
		int tl = title.length();
		int pl = pattern.length();
		
		int j = 0;
		for(int i = 0; i < tl; i++) {
			while(j > 0 && title.charAt(i) != pattern.charAt(j)) {
				j = pi[j - 1];
			}
			
			if(title.charAt(i) == pattern.charAt(j)) {
				if(j == pl - 1) return true;
				j++;
			}
		}
		return false;
	}
	
	public static List<Notice> KMP(List<Notice> list, String pattern) {
		int[] pi = getPi(pattern);
		
		List<Notice> ret = new LinkedList<Notice>();
		for(Notice notice : list) {
			String title = notice.getTitle();
			if(find(title, pattern, pi)) {
				ret.add(notice);
			}
		}
		
		return ret;
	}
	public static List<QnA> KMP2(List<QnA> list, String pattern) {
		int[] pi = getPi(pattern);
		
		List<QnA> ret = new LinkedList<QnA>();
		for(QnA qna : list) {
			String title = qna.getQna_title();
			if(find(title, pattern, pi)) {
				ret.add(qna);
			}
		}
		
		return ret;
	}
	
}
