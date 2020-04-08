package me.zeph.spring.cors.springfiltercorsexample.filter;

import org.apache.catalina.connector.RequestFacade;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

import static org.springframework.util.StringUtils.isEmpty;

@Component
public class CorsFilter implements Filter {

  public static final Pattern HOST_ALLOWED_PATTERN = Pattern.compile("^(https?://(?:.+\\.)?mywebsite\\.com(?::\\d{1,5})?)$", Pattern.CASE_INSENSITIVE);

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletResponse res = (HttpServletResponse) response;
    String origin = ((RequestFacade) request).getHeader("Origin");
    if (!isEmpty(origin) && HOST_ALLOWED_PATTERN.matcher(origin).matches()) {
      res.addHeader("Access-Control-Allow-Origin", origin);
      res.addHeader("Access-Control-Allow-Methods", "*");
      res.addHeader("Access-Control-Allow-Headers", "*");
      res.addHeader("Vary", "Origin");
      res.addHeader("Vary", "Access-Control-Request-Method");
      res.addHeader("Vary", "Access-Control-Request-Headers");
    }
    chain.doFilter(request, res);
  }
}
