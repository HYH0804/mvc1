package hello.servlet.web.frontcontroller.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v4.ControllerV4;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV4HandlerAdapter implements MyHandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV4);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        ControllerV4 controller = (ControllerV4) handler;

        //V4는 model을 인자로 보내야됨 , V3는 process에서 ModelView를 만들어서 반환시키지만...
        Map<String, String> paramMap = createParamMap(request);
        Map<String,Object> model = new HashMap<>();
        String viewName = controller.process(paramMap, model);

        //지금 어댑터는 전부 return형을 맞춰야(반환:ModelView) 함.
        ModelView mv = new ModelView(viewName);
        mv.setModel(model);

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
