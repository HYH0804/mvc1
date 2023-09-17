package hello.servlet.web.frontcontroller;

import java.util.HashMap;
import java.util.Map;


//v2까지는 Myview 객체 안에 viewName만 넣었는데 이제는 request, response에 넣은 Model 까지 넣어야되니까 Map에다가 넣는거
//모델 + viewPath
public class ModelView {
    private String viewName;

    //Map<String(model 객체 이름) , Object(model 객체) >
    private Map<String,Object> model = new HashMap<>();

    public ModelView(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }
}
