package com.spider.jwt.web.filter;

import com.spider.jwt.JwtInterceptor;
import com.spider.jwt.JwtProperties;
import com.spider.jwt.util.JwtFilterTool;
import com.spider.jwt.util.JwtTool;
import com.spider.util.Strings;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * jwt Filter拦截校验
 *
 * @author liuzhongkai
 */
public class JwtFilter implements Filter {

    private final static Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);

    private JwtProperties properties;

    private List<JwtInterceptor> interceptorList;

    public JwtFilter(JwtProperties properties, List<JwtInterceptor> interceptorList) {
        this.properties = properties;
        this.interceptorList = interceptorList;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("jwt-filter start to work");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
        //判定uri是否为配置的合格uri
        final Set<String> qualifiedUris = properties.getQualifiedUris();
        if (qualifiedUris != null && !qualifiedUris.isEmpty())
            if (JwtFilterTool.isSetItemContains(qualifiedUris, uri)) {
                filterChain.doFilter(request, response);
                return;
            }
        //判定uri是否为配置的需要校验的uri
        final Set<String> interceptUris = properties.getInterceptUris();
        if (interceptUris != null && !interceptUris.isEmpty())
            if (JwtFilterTool.isSetItemContains(interceptUris, uri)) {
                String authorizationInfo = request.getHeader(properties.getHeaderName());
                if (Strings.isNotEmpty(authorizationInfo)) {
                    Claims claims;
                    try {
                        claims = JwtTool.verifyJWT(authorizationInfo, properties.getSecretKey());
                    } catch (Exception e) {
                        LOGGER.error("jwt check failure url: {} token: {} message: {}", uri, authorizationInfo, e.getMessage());
                        sendError(response);
                        return;
                    }
                    if (interceptorList != null && !interceptorList.isEmpty())
                        for (JwtInterceptor jwtInterceptor : interceptorList)
                            if (!jwtInterceptor.verify(claims)) {
                                sendError(response);
                                return;
                            }
                    JwtModel.CLAIMS_CONTAINER.set(claims);
                    filterChain.doFilter(request, response);
                    JwtModel.CLAIMS_CONTAINER.remove();
                } else {
                    LOGGER.error("jwt check failure token is null");
                    sendError(response);
                    return;
                }
            }
        filterChain.doFilter(request, response);
    }

    private void sendError(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

    @Override
    public void destroy() {
        //there is no
    }
}
