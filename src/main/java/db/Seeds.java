package db;

import models.Paddock;

public class Seeds {
    public static void seedDb(DBHelper dbHelper) {
        Paddock paddock = new Paddock();

        dbHelper.save(paddock);
    }
}
