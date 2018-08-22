package db;

import models.Paddock;

public class Seeds {
    public static void seedDb() {
        Paddock paddock = new Paddock();

        DBHelper.save(paddock);
    }
}
