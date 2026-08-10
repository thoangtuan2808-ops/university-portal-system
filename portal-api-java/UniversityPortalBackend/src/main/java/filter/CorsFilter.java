package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebFilter("/*")
public class CorsFilter implements Filter {
	public void init(FilterConfig filterConfig) throws ServletException{}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rep = (HttpServletResponse) response;
		rep.setHeader("Access-Control-Allow-Origin", "*");
		rep.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		rep.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
		// Trình duyệt thường gửi một request "hỏi đường" (OPTIONS) trước khi gửi request thật
        // Nếu là OPTIONS, chỉ cần trả về OK (200) là đủ
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            rep.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        chain.doFilter(request, response);
	}
	public void destroy() {}
}
