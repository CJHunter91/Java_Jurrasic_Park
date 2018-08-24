package Controllers;

import db.DBHelper;
import models.Paddock;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;

public class PaddockController {

    private String htmlTemplate = "/templates/template.vtl";

    public PaddockController(DBHelper dbHelper, VelocityTemplateEngine velocityTemplateEngine) {


        get("/paddocks", (request, response) -> {
            List<Paddock> paddocks = dbHelper.getAllInstances(Paddock.class);

            Map model = createInitialViewModel("/templates/paddocks/paddock_list.vtl");
            model.put("paddocks", paddocks);

            return new ModelAndView(model, htmlTemplate);
        }, velocityTemplateEngine);

        get("/paddocks/new", (request, response) -> {
            Map model = createInitialViewModel("/templates/paddocks/new_paddock.vtl");

            return new ModelAndView(model, htmlTemplate);
        });


    }

    public static Map createInitialViewModel(String routeTemplate){
        Map model = new HashMap();
        model.put("template", routeTemplate);
        return model;
    }

}
