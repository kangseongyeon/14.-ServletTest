package kr.or.ddit.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class T04ServletCookieTest extends HttpServlet{
	/*
	 * 쿠키(Cookie)정보에 대하여...
	 * 
	 * => 웹서버와 브라우저는 애플리케이션을 사용하는 동안 필요한 값을 쿠기를 통해 공유하여 상태를 유지한다.
	 * 
	 *  1. 구성 요소?
	 *  - 이름
	 *  - 값
	 *  - 유효시간(초)
	 *  - 도메인 : ex) www.somehost.com, .somehost.com => 쿠키의 도메인이 쿠키를 생성한 서버의 도메인을 벗어나면
	 *  		브라우저는 쿠키를 저장(생성)하지 않는다. (보안위험 때문에...)
	 *  - 경로 : 쿠키를 공유할 기준경로를 지정한다 (도메인 이후 부분으로 디렉토리 수준)
	 *  		=> 지정하지 않으면 URL 경로 부분이 사용된다 
	 *  
	 *  2. 동작 방식
	 *  - 쿠키 생성 단계 : 생성한 쿠키를 응답 메시지의 헤더에 저장하여 웹브라우저(사용자)에 전송한다
	 *  - 쿠키 저장 단계 : 웹브라우저는  응답데이터에 포함된 쿠키를 쿠키저장소에 보관한다
	 *  - 쿠키 전송 단계 : 웹브라우저는 저장한 쿠키를 요청이 있을 때마다 웹서버에 전송한다 (삭제되기 전까지..)
	 *  			     웹서버는 브라우저가 전송한 쿠키를 사용해서 필요한 작업을 수행한다
	 */
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 쿠키 생성 예제
		setCookieExam(req, resp);
	}
	
	private void setCookieExam(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.setCharacterEncoding("UTF-8");
		
		// 쿠키 생성하기
		Cookie userId = new Cookie("userId", req.getParameter("userId"));
		
		// 쿠키값에 한글을 사용시 인코딩 처리를 해준다
		Cookie name = new Cookie("name",
				URLEncoder.encode(req.getParameter("name"), "UTF-8"));
		
		// 쿠키 소멸시간 설정하기 (초단위) => 지정하지 않으면 웹브라우저를 종료할 때 쿠키를 함께 삭제한다
		userId.setMaxAge(60 * 60 * 24);		// 1일
		userId.setHttpOnly(true);			// Javascript를 이용한 직접 접근 방지 (XSS 공격대비)
		
		name.setMaxAge(60 * 60 * 48);
		
		//////////////////////////////////////////////////////////////////////////
		
		// 응답헤더에 쿠키 추가하기
		resp.addCookie(userId);
		resp.addCookie(name);
		
		// 응답헤더에 인코딩 및 Content-Type 설정
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();
		
		String title = "쿠키 설정 예제";
		
		out.print("<!DOCTYPE html><html><head><title>" + title + "</title></head>"
				+ "<body>"
				+ "<h1 align = \"center\">" + title + "</h1>"
				+ "<ul>"
				+ "<li><b>ID : </b>"
				+ req.getParameter("userId") + "</li>"
				+ "<li><b>이름 : </b>"
				+ req.getParameter("name") + "</li>"
				+ "</ul></body></html>"
				);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
