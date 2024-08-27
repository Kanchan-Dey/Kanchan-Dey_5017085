package com.app.bookstoreapi.filter;
import org.springframework.stereotype.Component;
import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityHeadersFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)throws IOException, ServletException{
        HttpServletResponse httpResponse=(HttpServletResponse)response;
        httpResponse.setHeader("Done-By", "Soham-Dutta");
        httpResponse.setHeader("X-Content-Type-Options", "nosniff");
        httpResponse.setHeader("X-Frame-Options", "DENY");
        chain.doFilter(request, httpResponse);
    }
}
