package com.ssafy.happyhouse.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.happyhouse.dto.UserInfo;
import com.ssafy.happyhouse.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api("User Controller")
@Controller
@RequestMapping("/user")
public class UserController {
	private UserService service;

	@Autowired
	public void setService(UserService service) {
		this.service = service;
	}

	/*
	 *  .go Mapping
	 */
	@GetMapping("/login.go")
	private String loginGo(HttpServletRequest request, Model model) {
		Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("ssafy_id".equals(cookie.getName())) {
					model.addAttribute("savedId", cookie.getValue());
					model.addAttribute("isCheck", "checked");
					break;
				}
			}
		}
		return "login";
	}

	@GetMapping("/join.go")
	private String joinGo() {
		return "join";

	}

	@PostMapping("/joinCheck.go")
	private String join(HttpSession session, UserInfo user) {
		service.addUser(user);
		session.setAttribute("id", user.getUserid());
		session.setAttribute("name", user.getUsername());
		return "joincheck";
	}
	
	@GetMapping("/useredit.go")
	private String editGo(Model model, HttpSession session) {
		String id = (String) session.getAttribute("id");
		UserInfo userInfo = service.search(id);

		model.addAttribute("user", userInfo);
		return "useredit";
	}
	
	@GetMapping("/removeConfirm.go")
	private String removeGo() {
		return "removeConfirm";
	}
	
	@GetMapping("/findpw.go")
	private String findPwGo() {
		return "find_pw";
	}
	
	/*
	 * .do Mapping
	 */
	
	@ApiOperation(value = "로그인")
	@ResponseBody
	@PostMapping("/login")
	private ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, Object> map, HttpSession session) {
		String userid = (String)map.get("userid");
		String userpwd = (String)map.get("userpwd");
		
		UserInfo user = new UserInfo();
		user.setUserid(userid);
		user.setUserpwd(userpwd);
		String name = service.login(user);

		HashMap<String, Object> ret = new HashMap<>();
		
		if (name != null) {
			// 로그인 성공
			System.out.println("로그인 성공");
			session.setAttribute("id", userid);
			session.setAttribute("name", name);
			// 유저 == 관리자라면 추가적인 설정
			if (userid.equals("admin")) {
				session.setAttribute("isAdmin", true);
			}
			
			ret.put("state", true);
			ret.put("name", name);
		} else {
			// 로그인 실패
			System.out.println("로그인 실패");
			
			ret.put("state", false);
		}
		return new ResponseEntity<Map<String,Object>>(ret, HttpStatus.OK);
	}
	
	@PostMapping("/login.do")
	private String login2(HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session,
			String userid, String userpwd) {
		UserInfo user = new UserInfo();
		user.setUserid(userid);
		user.setUserpwd(userpwd);
		String name = service.login(user);

		if (userid.trim().equals("") || userpwd.trim().equals("")) {
			System.out.println("아이디 비번 미기입");
			model.addAttribute("errorMessage", "아이디와 비밀번호를 모두 입력해주세요.");
			return "login.go";
		}

		if (name != null) {
			String idsave = request.getParameter("idsave");
			if ("saveok".equals(idsave)) {
				System.out.println("idsave == saveok");
				Cookie cookie = new Cookie("ssafy_id", userid);
				cookie.setPath(request.getContextPath());
				cookie.setMaxAge(60 * 60 * 24 * 7);
				response.addCookie(cookie);
			} else {
				System.out.println("idsave != saveok");
				Cookie cookies[] = request.getCookies();
				if (cookies != null) {
					for (Cookie cookie : cookies) {
						if ("ssafy_id".equals(cookie.getName())) {
							cookie.setValue("");
							cookie.setPath(request.getContextPath());
							cookie.setMaxAge(0);
							response.addCookie(cookie);
							break;
						}
					}
					// test
					for (Cookie cookie : cookies) {
						if ("ssafy_id".equals(cookie.getName())) {
							System.out.println("지운 후" + cookie.getName() + cookie.getValue());
						}
					}
				}
			}

			// 로그인 성공
			System.out.println("로그인 성공");
			session.setAttribute("id", userid);
			session.setAttribute("name", name);
			// 유저 == 관리자라면 추가적인 설정
			if (userid.equals("admin")) {
				session.setAttribute("isAdmin", true);
			}

			return "redirect:/";
		} else {

			// 로그인 실패
			System.out.println("로그인 실패");
			model.addAttribute("errorMessage", "아이디 또는 비밀번호가 잘못되었습니다.");
			return "/login.go";
		}
	}

	@ApiOperation(value = "로그아웃")
	@ResponseBody
	@GetMapping("/logout")
	private ResponseEntity<String> logout(HttpSession session) {
		session.invalidate();
		return new ResponseEntity<String>("logout", HttpStatus.OK);
	}
	
	@GetMapping("/logout.do")
	private String logout2(HttpSession session) {
		session.invalidate();

		return "redirect:/";
	}

	@GetMapping("/viewinfo.do")
	private String viewInfo(HttpSession session, Model model) {
		String id = (String) session.getAttribute("id");
		UserInfo userInfo = service.search(id);

		model.addAttribute("user", userInfo);

		return "viewinfo";
	}

	

	@GetMapping("join.do")
	private String join() {
		return "redirect:/";
	}

	

	@PostMapping("/edit.do")
	private String edit(Model model, HttpSession session, UserInfo user) {
		String id = (String) session.getAttribute("id");

		user.setUserid(id);
		service.modifyUser(user);

		model.addAttribute("user", user);

		return "viewinfo";
	}

	

	@GetMapping("/remove.do")
	private String unjoin(HttpSession session) {
		String id = (String) session.getAttribute("id");
		service.deleteUser(id);
		session.invalidate();
		return "redirect:/";
	}

	@PostMapping("/resetpw.do")
	private String resetPw(Model model, String password1,String password2, HttpSession session ) {
		if (!password1.equals(password2)) {
			model.addAttribute("pwUnmatch", true);
			return "reset_pw";
		}

		String id = (String) session.getAttribute("id");
		UserInfo info = service.search(id);
		String name = info.getUsername();
		String address = info.getAddress();
		String email = info.getEmail();
		
		UserInfo userInfo = new UserInfo(id, name, password1, email, address, "");
		service.modifyUser(userInfo);
		return "redirect:/";
	}

	@PostMapping("/findpw.do")
	private String findPw(HttpSession session, Model model, String userid, String username, String email) throws SQLException {
		UserInfo userInfo = service.search(userid);
		if (userInfo != null) {
			System.out.println("아이디 존재");
			if (userInfo.getUsername().equals(username) && userInfo.getEmail().equals(email)) {
				System.out.println("입력정보 일치");
				model.addAttribute("userInfo", userInfo);
			} else {
				System.out.println("입력정보 불일치");
				model.addAttribute("infoUnmatch", true);
			}
		} else {
			System.out.println("아이디 없음");
			model.addAttribute("noInfo", true);
		}
		
		session.setAttribute("id", userid);

		return "reset_pw";
	}

	
	@ApiOperation(value = "회원가입")
	@ResponseBody
	@PostMapping("/join")
	private ResponseEntity<Map<String, Object>> join(@RequestBody Map<String, Object> map, HttpSession session) {
		String userid = (String)map.get("userid");
		String userpwd = (String)map.get("userpwd");
		String username = (String)map.get("username");
		String email = (String)map.get("email");
		String address = (String)map.get("address");
		
		UserInfo user = new UserInfo();
		user.setUserid(userid);
		user.setUserpwd(userpwd);
		user.setUsername(username);
		user.setEmail(email);
		user.setAddress(address);
		
		HashMap<String, Object> ret = new HashMap<>();
		ret.put("state",service.addUser(user));
		
		return new ResponseEntity<Map<String,Object>>(ret, HttpStatus.OK);
	}

}
