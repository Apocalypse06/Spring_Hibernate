package _01_ShowMember;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;

import spring._06.model.Member;
import spring._06.model.MemberDAO;

/**
 * Servlet implementation class ShowMemberServlet
 */
@WebServlet("/_01_ShowMember/showMember.do")
public class ShowMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		WebApplicationContext ctx = 
			WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		MemberDAO memberDao = (MemberDAO) ctx.getBean("memberJdbcTemplate");
		List<Member> list = memberDao.getAll();
		String type = request.getParameter("type");
		if (type == null) {
			request.setAttribute("MemberList", list);
			System.out.println(list);
			RequestDispatcher rd = request.getRequestDispatcher("/_01_ShowMember/showMember.jsp");
			rd.forward(request, response);
			return;
		} else if (type.equalsIgnoreCase("JSON")){
			Gson gson = new Gson();
			String s = gson.toJson(list);
			response.setContentType("application/json; charset=UTF8");
			try (
				PrintWriter out = response.getWriter();
			){
				out.print(s);
				System.out.println(s);
			}			
		} 		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
