/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.filters;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.sapac.controllers.usuario.UsuarioController;

/**
 *
 * @author carlson
 */
public class LoginFilter extends HttpServlet implements Filter {

	/**
	 * 
	 * @param request
	 * @param response 
	 * @throws ServletException
	 * @throws IOException
	 */
	private void doLogin(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		System.out.println("--aslkals--");
		request.getRequestDispatcher("/public/login.xhtml").forward(request, response);
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();

		if (session.isNew()) {
			doLogin(request, response);
			return;
		}
		UsuarioController usuarioController = (UsuarioController) req.getSession().
				getAttribute("usuarioController");
		if (usuarioController == null) {
			doLogin(request, response);
			return;
		} else if (!usuarioController.isLogado()) {
			doLogin(request, response);
			return;
		}

		chain.doFilter(request, response);
	}
}