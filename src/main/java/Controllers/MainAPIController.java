package Controllers;

import db.DBHelper;
import db.HibernateUtil;
import db.Seeds;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;

public class MainAPIController {
    public static void main(String[] args) {
        DBHelper dbHelper = new DBHelper(HibernateUtil.getSessionFactory());
        VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

        Seeds.seedDb(dbHelper);

        new PaddockController(dbHelper, velocityTemplateEngine);

        get("/", (request, response) -> {
            Map model = new HashMap();
            model.put("template", "/templates/index.vtl");
            return new ModelAndView(model, "/templates/template.vtl");
        }, velocityTemplateEngine);
    }
}
