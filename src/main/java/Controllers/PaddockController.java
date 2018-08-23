package Controllers;

import db.DBHelper;
import db.HibernateUtil;
import db.Seeds;
import models.Paddock;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;

public class PaddockController {

    public static void main(String[] args) {
        DBHelper dbHelper = new DBHelper(HibernateUtil.getSessionFactory());
        Seeds.seedDb(dbHelper);
        VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

        get("/paddocks", (request, response) -> {
            Map model = new HashMap();
            List<Paddock> paddocks = dbHelper.getAllInstances(Paddock.class);
            model.put("template", "/templates/paddocks/paddock_list.vtl");
            model.put("paddocks", paddocks);
            return new ModelAndView(model, "/templates/template.vtl");
        }, velocityTemplateEngine);
    }

    public void testMethod(){

    }

}
