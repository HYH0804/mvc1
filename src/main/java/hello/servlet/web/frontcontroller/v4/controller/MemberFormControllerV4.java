package hello.servlet.web.frontcontroller.v4.controller;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v4.ControllerV4;

import java.util.Map;

public class MemberFormControllerV4 implements ControllerV4 {

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        //Model자체가 인자로 오니까 ModelView 객체 만들어서 여기 안에다가 viewName 넣을 필요도 없음
        return "new-form";
    }
}
