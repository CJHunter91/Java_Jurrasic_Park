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
import java.util.UUID;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

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
            model.put("formRedirect", "/dinosaurs");

            return new ModelAndView(model, htmlTemplate);
        }, velocityTemplateEngine);

        get("/dinosaurs/:id/feed", (request, response) -> {
            String dinoId = request.params("id");
            Dinosaur dinosaur = dbHelper.getInstance(Dinosaur.class, UUID.fromString(dinoId));

            dinosaur.feed();
            dbHelper.update(dinosaur);
            response.redirect("/dinosaurs");
            return String.format("Dinosaur %s was successfully fed.", dinosaur.getSpecies());
        });

        get("/dinosaurs/:id/edit", (request, response) -> {
            Map model = TemplateHelper.createInitialViewModel("templates/dinosaurs/update_dinosaur.vtl");

            List<Paddock> paddockList = dbHelper.getAllInstances(Paddock.class);
            model.put("paddocks", paddockList);
            model.put("formRedirect", "/dinosaurs/" + request.params("id"));

            return new ModelAndView(model, htmlTemplate);
        }, velocityTemplateEngine);

        post("/dinosaurs/:id", (request, response) -> {
            String dinoId = request.params("id");
            Dinosaur dinosaur = dbHelper.getInstance(Dinosaur.class, UUID.fromString(dinoId));

            UUID paddock_id = UUID.fromString(request.queryParams("paddock_id"));
            Paddock paddock = dbHelper.getInstance(Paddock.class, paddock_id);

            dinosaur.setPaddock(paddock);
            dbHelper.update(dinosaur);

            response.redirect("/dinosaurs");
            return "Dinosaur updated";
        });

        post("/dinosaurs", (request, response) -> {
            UUID paddockId = UUID.fromString(request.queryParams("paddock_id"));
            String species = request.queryParams("species");

            Paddock assignedPaddock = dbHelper.getInstance(Paddock.class, paddockId);

            Dinosaur newDino = new Dinosaur(species, assignedPaddock);

            dbHelper.save(newDino);

            response.redirect("/dinosaurs");
            return "Dinosaur created!!";
        });
    }
}
