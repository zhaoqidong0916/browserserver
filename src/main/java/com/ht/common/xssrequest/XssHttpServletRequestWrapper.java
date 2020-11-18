package com.ht.common.xssrequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XssHttpServletRequestWrapper
  extends HttpServletRequestWrapper
{
  public XssHttpServletRequestWrapper(HttpServletRequest request)
  {
    super(request);
  }
  
  public String[] getParameterValues(String parameter)
  {
    String[] values = super.getParameterValues(parameter);
    if (values == null) {
      return null;
    }
    int count = values.length;
    String[] encodedValues = new String[count];
    for (int i = 0; i < count; i++) {
      encodedValues[i] = cleanXSS(values[i]);
    }
    return encodedValues;
  }
  
  public String getParameter(String parameter)
  {
    String value = super.getParameter(parameter);
    if (value != null) {
      return cleanXSS(value);
    }
    return null;
  }
  
  public String getHeader(String name)
  {
    String value = super.getHeader(name);
    if (value == null) {
      return null;
    }
    return cleanXSS(value);
  }
  
  private static String cleanXSS(String value)
  {
    value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    value = value.replaceAll("%3C", "&lt;").replaceAll("%3E", "&gt;");
    value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
    value = value.replaceAll("%28", "&#40;").replaceAll("%29", "&#41;");
    value = value.replaceAll("'", "&#39;");
    value = value.replaceAll("eval\\((.*)\\)", "");
    value = value.replaceAll("[\\\"\\'][\\s]*javascript:(.*)[\\\"\\']", "\"\"");
    value = value.replaceAll("script", "");
    return value;
  }
  
  public static void main(String[] args)
  {
    String value = "{\"roleName\":\"eeeeeee\",\"roleDescription\":\"tttttttttttt\"}";
    
    value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    value = value.replaceAll("%3C", "&lt;").replaceAll("%3E", "&gt;");
    value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
    value = value.replaceAll("%28", "&#40;").replaceAll("%29", "&#41;");
    value = value.replaceAll("'", "&#39;");
    value = value.replaceAll("eval\\((.*)\\)", "");
    value = value.replaceAll("[\\\"\\'][\\s]*javascript:(.*)[\\\"\\']", "\"\"");
    value = value.replaceAll("script", "");
    System.out.println(value);
  }
}
