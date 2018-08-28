package db;

import models.Paddock;

public class Seeds {
    public static void seedDb(DBHelper dbHelper) {
        Paddock paddock = new Paddock("Paddock 1");

        dbHelper.save(paddock);
    }
}
