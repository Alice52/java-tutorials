package cn.edu.ntu.springmvc.sample;

import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zack
 * @create 2019-11-11 21:23
 * @function controller handler means servlet
 */
public class CustomerServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    ServletContext context = getServletContext();
    ApplicationContext ctx = (ApplicationContext) context.getAttribute("applicationContext");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    super.doPost(req, resp);
  }
}