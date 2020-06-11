**Vue Project**
 
---

 뷰 프로젝트 위치 : vue_project.zip
 
--- 
 
 구현 기능 
          
          □ 기본 - Vue Q&A 게시글
         
          □ 기본 - 서버 Q&A 게시글 
          
          □ 심화 - Vue 메인화면 및 추가기능
          
         
          
          


---

기본 기능


 1. Vue QnA 게시판
 


 1-1>. 게시글 목록
 
  ![qnaList](./vueScreenShot/qnaList.png)
  
  
 1-2>. 게시글 등록
 
  ![qnaAdd](./vueScreenShot/qnaAdd.png)
  
  
 1-3>. 게시글 수정
 
  ![qnaModify](./vueScreenShot/qnaModify.png)

 
 1-4>. 게시글 상세 검색
 
  ![qnaDetail](./vueScreenShot/qnaDetail.png)
  
  
 1-5>. 게시글 삭제
 
  ![qnaRemove](./vueScreenShot/qnaRemove.png)

 
 
 
 
 
 2. 서버 QnA 게시판
 
소스코드 일부
~~~
@Controller
@RequestMapping("/qna")
public class QnAController {
    
    ...
	
	@ApiOperation(value = "QnA 리스트 전체 조회")
	@ResponseBody
	@GetMapping
	public ResponseEntity<List<QnA>> getQnAList() {
		return new ResponseEntity<List<QnA>>(service.getAllQnA(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "QnA 수정")
	@ResponseBody
	@PutMapping("/qna")
	public ResponseEntity<Boolean> modifyQnA(@RequestBody QnA qna) {
		return new ResponseEntity<Boolean>(service.modifyQnA(qna), HttpStatus.OK);
	}
	
	@ApiOperation(value = "QnA 삭제")
	@ResponseBody
	@DeleteMapping("/{qna_no}")
	public ResponseEntity<Boolean> deleteQnA(@PathVariable int qna_no) {
		return new ResponseEntity<Boolean>(service.deleteQnA(qna_no), HttpStatus.OK);
	}
	
    ...
}
~~~
 
  





---

심화 기능


  
 3. Vue 메인화면 및 추가기능
 


  3-1> 메인화면

  ![main](./vueScreenShot/main.png)
 
 
  3-2> 건물 검색 및 정렬

  (모든 칼럼으로 정렬 가능)


  ![search](./vueScreenShot/search.png)
  ![sortByBuildYear](./vueScreenShot/sortByBuildYear.png)
  
  
  3-3> 건물 상세 정보

  ![houseDetail](./vueScreenShot/houseDetail.png)


  3-4> 공지 사항 리스트
  
  ![noticeList](./vueScreenShot/noticeList.png)
  
  

  3-5> 공지 사항 상세 페이지

  ![noticeDetail](./vueScreenShot/noticeDetail.png)
  
  
  3-6> 공지 사항 검색

  ![noticeSearch](./vueScreenShot/noticeSearch.png)
  
  
  3-7> 로그인 (헤더,네비게이션 포함)

  ![login](./vueScreenShot/login.png)
 
 
 
 
 
