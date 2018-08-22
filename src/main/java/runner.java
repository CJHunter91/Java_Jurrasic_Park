import db.DBHelper;
import models.Paddock;

public class runner {
    public static void main(String[] args) {
        Paddock paddock = new Paddock();

        DBHelper.save(paddock);
    }
}
