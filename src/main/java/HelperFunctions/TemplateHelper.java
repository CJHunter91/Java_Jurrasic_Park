package HelperFunctions;

import java.util.HashMap;
import java.util.Map;

public class TemplateHelper {

    public static Map createInitialViewModel(String routeTemplate){
        Map model = new HashMap();
        model.put("template", routeTemplate);
        return model;
    }
}
