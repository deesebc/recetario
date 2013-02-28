package es.home.recetario.filtro;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionFilter implements Filter {

    /** Objeto de configuracion del filtro */
    private FilterConfig filterConfig;

    /**
     * Constructor publico
     */
    public SessionFilter() {
	super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException,
    ServletException {
	final HttpServletRequest req = (HttpServletRequest) request;
	final HttpSession session = req.getSession();

	if (session == null) {
	    final HttpServletResponse httpRes = (HttpServletResponse) response;
	    httpRes.sendRedirect(filterConfig.getServletContext().getContextPath() + "/faces/pages/index.xhtml");
	    return;
	} else {
	    chain.doFilter(request, response);
	}
    }

    /**
     * @return the filterConfig
     */
    public FilterConfig getFilterConfig() {
	return filterConfig;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
	this.filterConfig = filterConfig;

    }

    /**
     * @param filterConfig
     *            the filterConfig to set
     */
    public void setFilterConfig(final FilterConfig filterConfig) {
	this.filterConfig = filterConfig;
    }

}
