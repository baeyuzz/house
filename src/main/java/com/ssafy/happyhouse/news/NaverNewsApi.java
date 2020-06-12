package com.ssafy.happyhouse.news;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//	집 클릭하면 naver new api 를 다른데서 호출해서 돌리고
//	거기서 파싱해서 제일 밑쪽에 관련 뉴스 해서
//	5개 정도? 띄우면 됨?
public class NaverNewsApi {

	public static List<News> searchNews(String apt) {
		String clientId = "qsbJOPl6cmDXhTYFqzhS";// 애플리케이션 클라이언트 아이디값";
		String clientSecret = "YqMQQyENb4";// 애플리케이션 클라이언트 시크릿값";
		StringBuffer response = new StringBuffer();
		try {
			String text = URLEncoder.encode(apt, "UTF-8"); // 검색어";
			String apiURL = "https://openapi.naver.com/v1/search/news.json?query=" + text
					+ "&display=10&start=1&sort=date"; // 뉴스의 json 결과
			// String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text;
			// // 블로그의 xml 결과
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			int responseCode = con.getResponseCode();
			BufferedReader br;
			if (responseCode == 200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			System.out.println(response.toString());
			br.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		// news json parsing

		List<News> news = new ArrayList<News>();

		JSONParser parser = new JSONParser();
		JSONObject itemsObj;
		try {
			itemsObj = (JSONObject) parser.parse(response.toString());
			JSONArray items = (JSONArray) itemsObj.get("items");
			for (int i = 0; i < items.size(); i++) {
				JSONObject item = (JSONObject) items.get(i);
				int no = i+1;
				String title = item.get("title").toString();
				String link = item.get("link").toString();
				String description = item.get("description").toString();
				description = description.replace("<b>","");
				description = description.replace("</b>","");
				String pubDate = item.get("pubDate").toString();

				news.add(new News(no,title, link, description, pubDate));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		for (int i = 0; i < news.size(); i++) {
			System.out.println(news.get(i).getTitle());
			System.out.println(news.get(i).getDescription());
			System.out.println(news.get(i).getPubDate());
			System.out.println(news.get(i).getLink());
			System.out.println();
		}
 		*/	
		return news;
	}
}

