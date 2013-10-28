/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.sapac.controllers.usuario.PerfilController;

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
		request.getRequestDispatcher("/public/login.xhtml").forward(request, response);
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();

		if (session.isNew()) {
			doLogin(request, response);
		} else {
			PerfilController perfilController = (PerfilController)
					req.getSession().getAttribute("perfilController");
			if (perfilController == null) {
				doLogin(request, response);
			} else if (!perfilController.isLogado()) {
				doLogin(request, response);
			}
		}

		chain.doFilter(request, response);
	}
}