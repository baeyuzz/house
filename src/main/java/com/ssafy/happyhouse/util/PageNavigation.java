package com.ssafy.happyhouse.util;

public class PageNavigation {

	private boolean startRange;
	private boolean endRange;
	private int totalCount;
	private int newCount;
	private int totalPageCount;
	private int currentPage;
	private int naviSize;
	private int countPerPage;
	private String navigator;

	public boolean isStartRange() {
		return startRange;
	}

	public void setStartRange(boolean startRange) {
		this.startRange = startRange;
	}

	public boolean isEndRange() {
		return endRange;
	}

	public void setEndRange(boolean endRange) {
		this.endRange = endRange;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getNewCount() {
		return newCount;
	}

	public void setNewCount(int newCount) {
		this.newCount = newCount;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getNaviSize() {
		return naviSize;
	}

	public void setNaviSize(int naviSize) {
		this.naviSize = naviSize;
	}

	public String getNavigator() {
		return navigator;
	}

	public int getCountPerPage() {
		return countPerPage;
	}

	public void setCountPerPage(int countPerPage) {
		this.countPerPage = countPerPage;
	}

	public void makeNavigator() {
		int startPage = (currentPage - 1) / naviSize * naviSize + 1;
		int endPage = startPage + naviSize - 1;
		if(totalPageCount < endPage)
			endPage = totalPageCount;
		
		StringBuilder buffer = new StringBuilder();
		buffer.append("		<ul class=\"pagination justify-content-center\"> \n");
		buffer.append("			<li class=\"page-item\"> \n");
		buffer.append("				<a href=\"#\" id=\"1\" class=\"page-link\">최신</a> \n");
		buffer.append("			</li> \n");
		
		if(startRange)	buffer.append("			<li class=\"page-item disabled\"> \n");
		else			buffer.append("			<li class=\"page-item\"> \n");
		buffer.append("				<a href=\"#\" id=\"" + (startPage - 1) + "\" class=\"page-link\">이전</a> \n");
		buffer.append("			</li> \n");
		
		for(int i=startPage;i<=endPage;i++) {
			buffer.append("			<li class=\"" + (currentPage == i ? "page-item active" : "page-item") + "\"><a href=\"#\" id=\"" + i + "\" class=\"page-link\">" + i + "</a></li> \n");
		}
		
		if(endRange)	buffer.append("			<li class=\"page-item disabled\"> \n");
		else			buffer.append("			<li class=\"page-item\"> \n");
		buffer.append("				<a href=\"#\" id=\"" + (endPage + 1) + "\" class=\"page-link\">다음</a> \n");
		buffer.append("			</li> \n");
		
		buffer.append("			<li class=\"page-item\"> \n");
		buffer.append("				<a href=\"#\" id=\"" + totalPageCount + "\" class=\"page-link\">마지막</a> \n");
		buffer.append("			</li> \n");
		buffer.append("		</ul> \n");
		this.navigator = buffer.toString();
	}
	
	public static PageNavigation makePageNavigation(int currentPage, int sizePerPage, int totalCount) {
		PageNavigation pageNavigation = new PageNavigation();
		int naviSize = 10; // 페이지 갯수
		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		pageNavigation.setTotalCount(totalCount);
		int totalPageCount = (totalCount - 1) / sizePerPage; // 총 페이지 수
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = currentPage <= naviSize; // startRange 가 true 면 '이전' 못누름
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage; // true 면 '다음' 버튼 못누름
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();
		return pageNavigation;
	}
}
