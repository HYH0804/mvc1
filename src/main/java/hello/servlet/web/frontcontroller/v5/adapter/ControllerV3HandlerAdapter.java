package hello.servlet.web.frontcontroller.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


//어댑터는 request 넘어온걸 paramMap으로 만들고, 핸들러(컨트롤러)에 맞게 process 호출
public class ControllerV3HandlerAdapter implements MyHandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        //ControllerV3 인터페이스를 구현한 구현체 컨트롤러면 True
        return (handler instanceof ControllerV3);
    }

    //frontController에서 Controller 구현체를 호출하는 것 자체를 handle 메서드가 대신 할 것임
    //handler(컨트롤러)를 호출하고 반환을 ModelView로 해줄거임
    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        //일단 supports메서드로 어댑터 가능한지 먼저 확인할거니까 타입 캐스팅해도 상관없
        ControllerV3 controller = (ControllerV3) handler;

        //request로 파라미터 넘겨온걸 paramMap으로 만들고
        Map<String, String> paramMap = createParamMap(request);
        //해당 Controller 구현체 호출 >> model 객체 + viewPath
        ModelView mv = controller.process(paramMap);

        return mv;
    }

    private static Map<String, String> createParamMap(HttpServletRequest request) {
        //paramMap
        Map<String,String> paramMap = new HashMap<>();
        //request,response로 온 파라미터들을 ParamMap으로 바꿔서 이걸 Model로 넘겨주겠다
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)) );
        return paramMap;
    }

}
