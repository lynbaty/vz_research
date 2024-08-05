package org.example.es_module.tenant;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
public class TenantFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String tenantName = req.getHeader("X-TenantID");
        /*TODO: Get current user & tenant here form database
        Set Tenant here with tenantId*/
        TenantContext.setCurrentTenant(tenantName);

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            TenantContext.setCurrentTenant("");
        }
    }
}
