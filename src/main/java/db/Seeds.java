package db;

import models.Dinosaur;
import models.Paddock;

public class Seeds {
    public static void seedDb(DBHelper dbHelper) {
        Paddock paddock = new Paddock("Paddock 1");

        Dinosaur dino1 = new Dinosaur("Velociraptor", paddock);


        dbHelper.save(paddock);
        dbHelper.save(dino1);
    }
}
