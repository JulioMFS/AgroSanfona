package com.login.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.login.bean.LoginBean;
import com.login.dao.LoginDao;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("username");
		String password = request.getParameter("password");

		System.out.println("Inside servlet, userName: " + userName
				+ ", password: " + password);

		String scheme = request.getScheme();
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		String contextPath = request.getContextPath(); // includes leading
														// forward slash

		String resultPath = scheme + "://" + serverName + ":" + serverPort
				+ "/AgroSanfonaWeb";
		System.out.println("Result path: " + resultPath);

		LoginBean loginBean = new LoginBean();

		loginBean.setUserName(userName);
		loginBean.setPassword(password);

		LoginDao loginDao = new LoginDao();

		try {
			String userValidate = loginDao.authenticateUser(loginBean);
			Cookie c1 = new Cookie("userName", "");
			c1.setMaxAge(0);
			Cookie c2 = new Cookie("userName", "");
			c2.setMaxAge(0);
			response.addCookie(c1);
			response.addCookie(c2);

			if (userValidate.equals("Admin_Role")) {
				System.out.println("Admin's Home");

				HttpSession session = request.getSession(); // Creating a
															// session
				session.setAttribute("Admin", userName); // setting session
															// attribute
				session.setAttribute("role", "Admin");

				// Creating two cookies
				c1 = new Cookie("userName", userName);
				c1.setPath("/");
				c1.setMaxAge(3600);
				c2 = new Cookie("role", "Admin");
				c2.setPath("/");
				c2.setMaxAge(3600);

				// Adding the cookies to response header
				response.addCookie(c1);
				response.addCookie(c2);

				request.setAttribute("userName", userName);

				// request.getRequestDispatcher("/JSP/Admin.jsp").forward(request,
				// response);

				response.sendRedirect(resultPath);

			} else if (userValidate.equals("Editor_Role")) {
				System.out.println("Editor's Home");

				HttpSession session = request.getSession();
				session.setAttribute("Editor", userName);
				session.setAttribute("role", "Editor");
				// Creating two cookies
				c1 = new Cookie("userName", userName);
				c2 = new Cookie("role", "Editor");
				c1.setPath("/");
				c2.setPath("/");
				c1.setMaxAge(600);
				c2.setMaxAge(600);
				// Adding the cookies to response header
				response.addCookie(c1);
				response.addCookie(c2);
				request.setAttribute("userName", userName);

				// request.getRequestDispatcher("/JSP/Editor.jsp").forward(
				// request, response);
				response.sendRedirect(resultPath);

			} else if (userValidate.equals("User_Role")) {
				System.out.println("User's Home");

				HttpSession session = request.getSession();
				session.setMaxInactiveInterval(10 * 60);
				session.setAttribute("User", userName);
				session.setAttribute("role", "User");
				// Creating two cookies
				c1 = new Cookie("userName", userName);
				c2 = new Cookie("role", "User");
				c1.setPath("/");
				c2.setPath("/");
				c1.setMaxAge(600);
				c2.setMaxAge(600);

				// Adding the cookies to response header
				response.addCookie(c1);
				response.addCookie(c2);
				request.setAttribute("userName", userName);

				// request.getRequestDispatcher("/JSP/User.jsp").forward(request,
				// response);
				response.sendRedirect(resultPath);

			} else {
				System.out.println("Error message = " + userValidate);
				request.setAttribute("errMessage", userValidate);

				request.getRequestDispatcher("/JSP/Login.jsp").forward(request,
						response);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	} // End of doPost()
}