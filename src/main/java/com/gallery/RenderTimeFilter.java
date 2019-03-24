package com.gallery;


import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Project: Gallery
 * Class: RenderTimeFilter
 * Date: 2019-03-24 13:15 [Sunday]
 *
 * @author Dennis Obukhov
 */

@Component
public class RenderTimeFilter implements Filter {

    @Autowired
    @Lazy
    private Logger logger;

    private double time;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        long nanoTime = System.nanoTime();
        chain.doFilter(request, response);
        time = (System.nanoTime() - nanoTime) * 1e-9;
        logger.debug(
                String.format("'%s' is served in %.3f s", ((HttpServletRequest) request).getRequestURI(),
                time ));
    }
}