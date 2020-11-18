package com.ht.common.xssrequest;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter(filterName="XSSFilter", urlPatterns={"/*"})
public class XssFilter
  implements Filter
{
  FilterConfig filterConfig = null;
  
  public void init(FilterConfig filterConfig)throws ServletException{
    
      this.filterConfig = filterConfig;
  }
  
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
    throws IOException, ServletException{

    filterChain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest)servletRequest), servletResponse);
  }
  
  public void destroy()  {
    this.filterConfig = null;
  }
}
