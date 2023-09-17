package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name="frontControllerServletV5",urlPatterns = "/front-controller/v5/*")
public class FrontControllerV5 extends HttpServlet {

    //기존 코드
    //private Map<String, ControllerV4> controllerMap = new HashMap<>();
    //핸들러(컨트롤러) 매핑정보
    private final Map<String,Object> handlerMappingMap = new HashMap<>();

    //어댑터 보유 목록
    private final List<MyHandlerAdapter> handlerAdapters=new ArrayList<>();

    public FrontControllerV5() {
        initHandlerMappingMap();

        //리스트에 v3어댑터 있다고 추가
        initHandlerAdapters();
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form",new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save",new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members",new MemberListControllerV3());

        //V4추가
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form",new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save",new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members",new MemberListControllerV4());
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //요청url 가지고 핸들러 찾기 ------------------handler 매핑정보

        Object handler = getHandler(request); //url에 맞는 컨트롤러 가져오고 (v3인지 v5인지 모르니까 Object로)

        if(handler == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //-----------------handler 매핑정보-------------

        //---------handler(컨트롤러)에 맞는 handleradapter 찾아와-------
        MyHandlerAdapter adapter = getHandlerAdapter(handler); //어댑터 가져옴
        ModelView mv = adapter.handle(request, response, handler); //어댑터의 핸들러(컨트롤러) 호출
        //--------handler(컨트롤러)에 맞는 handleradapter 찾아와-------

        //viewResolver
        MyView view = new MyView("/WEB-INF/views/" + mv.getViewName() + ".jsp");
        //Myview에는 모델 멤버변수가 없으니 인자로 넘겨주자.
        view.render(mv.getModel(),request,response);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if(adapter.supports(handler)){ //어댑터가 핸들러 지원하면
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler="+handler);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }
}
