package Controllers;

import db.DBHelper;
import db.Seeds;
import models.Paddock;
import spark.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;

public class PaddockController {

    public static void main(String[] args) {
        Seeds.seedDb();

        get("/paddocks", (request, response) -> {
            Map model = new HashMap();
            List<Paddock> paddocks = DBHelper.getAllInstances(Paddock.class);

            model.put("paddocks", paddocks);
            return model;
        });
    }

}
