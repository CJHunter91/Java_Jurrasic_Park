package Controllers;

import db.DBHelper;
import models.Paddock;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import spark.utils.IOUtils;

import java.io.InputStream;
import java.util.*;

import static spark.Spark.get;
import static spark.Spark.post;

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
            model.put("formRedirect", "/paddocks");


            return new ModelAndView(model, htmlTemplate);
        }, velocityTemplateEngine);

        post("/paddocks", (request, response) -> {
            String paddockName= request.queryParams("paddock_name");
            dbHelper.save(new Paddock(paddockName));
            response.redirect("/paddocks");
            return response;
        });


    }

    public static Map createInitialViewModel(String routeTemplate){
        Map model = new HashMap();
        model.put("template", routeTemplate);
        return model;
    }

}
