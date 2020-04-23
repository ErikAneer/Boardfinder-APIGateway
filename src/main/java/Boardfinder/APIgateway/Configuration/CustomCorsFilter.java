/*

 */
package Boardfinder.APIgateway.Configuration;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Custom cors filter configuration that overrides the standard filter and sets rules for access to the API. 
 * Needed to avoid pre-flight request errors. 
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomCorsFilter implements Filter{

public CustomCorsFilter () {
    super();
}

/**
 * Method that sets custom filter qriteria.
 * @param req
 * @param res
 * @param chain
 * @throws IOException
 * @throws ServletException 
 */
@Override
public final void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain) throws IOException, ServletException {
    final HttpServletResponse response = (HttpServletResponse) res;
    response.setHeader("Access-Control-Allow-Origin", "https://localhost:4200");
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE"); // remove delete and put?
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Authorization, Origin, Content-Type, Version, Access-Control-Allow-Headers, observe");
    response.setHeader("Access-Control-Expose-Headers", "X-Requested-With, Authorization, Origin, Content-Type, observe ");

    final HttpServletRequest request = (HttpServletRequest) req;
    
    if (!request.getMethod().equals("OPTIONS")) {
        chain.doFilter(req, res);
    } else {
        // do not continue with filter chain for options requests
    }   
}

@Override
public void destroy() {

} 

@Override
public void init(FilterConfig filterConfig) throws ServletException {       
}
}
