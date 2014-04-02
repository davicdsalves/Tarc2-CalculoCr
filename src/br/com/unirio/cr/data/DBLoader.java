package br.com.unirio.cr.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public final class DBLoader {

    private static DBLoader instance;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    public DaoSession daoSession;

    private DBLoader(Context context) {
        String dbname = "tarc2db";
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, dbname, null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DBLoader getInstance(Context context) {
        if (instance == null) {
            instance = new DBLoader(context);
        }
        return instance;
    }

    public void reloadSession() {
        daoSession = daoMaster.newSession();
    }
}
