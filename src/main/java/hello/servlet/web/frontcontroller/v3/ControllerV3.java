package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;

import java.util.Map;

public interface ControllerV3 {
    //http 의 response request 없애야되다보니 서블릿에 종속적이면 안됨
    //prcess에서 model과 viewPath 수행
    ModelView process (Map<String,String> paramMap);
}
