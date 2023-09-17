package hello.servlet.web.frontcontroller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/*
String viewPath = "/WEB-INF/views/members.jsp"; 
RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath); 
dispatcher.forward(request, response);
가 모든 컨트롤러에서 겹치니까 따로 빼서 객체로 만들고 포워드하겠다
*/
public class MyView {
    private String viewPath;

    public MyView(String viewPath) {
        this.viewPath = viewPath;
    }

    public void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request,response);
    }

    //render 오버로딩
    public void render(Map<String ,Object> model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //ModelView의 Model을 request에다가 넣기
        modelToRequestAttribute(model, request);

        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request,response);
    }

    private static void modelToRequestAttribute(Map<String, Object> model, HttpServletRequest request) {
        model.forEach((key, value) -> request.setAttribute(key,value));
    }
}
