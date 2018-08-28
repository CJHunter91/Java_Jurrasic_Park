package Controllers;

import Config.Config;
import HelperFunctions.TemplateHelper;
import db.DBHelper;
import models.Dinosaur;
import models.Paddock;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;

public class DinosaurController {

    private VelocityTemplateEngine velocityTemplateEngine;
    private DBHelper dbHelper;
    private String htmlTemplate = Config.mainHtmlTemplate;

    public DinosaurController(DBHelper dbHelper, VelocityTemplateEngine velocityTemplateEngine) {
        this.dbHelper = dbHelper;
        this.velocityTemplateEngine = velocityTemplateEngine;

        setupEndpoints();
    }

    private void setupEndpoints() {
        get("/dinosaurs", (request, response) -> {
            Map model = TemplateHelper.createInitialViewModel("templates/dinosaurs/dinosaur_list.vtl");

            List<Dinosaur> dinosaurList = dbHelper.getAllInstances(Dinosaur.class);
            model.put("dinos", dinosaurList);

            return new ModelAndView(model, htmlTemplate);
        }, velocityTemplateEngine);

        get("/dinosaurs/new", (request, response) -> {
            Map model = TemplateHelper.createInitialViewModel("templates/dinosaurs/new_dinosaur.vtl");

            List<Paddock> paddockList = dbHelper.getAllInstances(Paddock.class);
            model.put("paddocks", paddockList);

            return new ModelAndView(model, htmlTemplate);
        }, velocityTemplateEngine);
    }
}
