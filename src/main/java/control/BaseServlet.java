package control;

import annotation.WebUrl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

public class BaseServlet extends HttpServlet{

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        String methodName = url.substring(url.lastIndexOf("/")+1);
        for (Method method:getClass().getMethods()) {
            if (method.getAnnotation(WebUrl.class) != null) {
                if (methodName.equals(method.getAnnotation(WebUrl.class).url())) {
                    try {
                        method.invoke(this, req, resp);
                        break;
                    } catch (InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
