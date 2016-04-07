package com.example.drss.dbadapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DBUtils {
    Context context;
    final static String DB_NAME="class_recipe";
    final static String DB_DIR="database";
    private String databasePath;
    private SQLiteDatabase database;

    public DBUtils(Context context) {

        this.context = context;
        databasePath = getDBPath();
    }

    public SQLiteDatabase getDB(){
        if (database==null) {
            database = SQLiteDatabase.openDatabase(databasePath, null,
                    SQLiteDatabase.OPEN_READWRITE);
        }
        return database;
    }

    public void closeDB(){
        if (database != null && database.isOpen()){
            database.close();
        }
    }

    public void initDB() {
        File file = new File(databasePath);
        if (!file.exists()) {
            copyDB();
        }
    }

    public String getDBPath() {
        String dbPath = "";
        String packageName = context.getPackageName();
        try {
            ApplicationInfo applicationInfo = context.getPackageManager()
                    .getApplicationInfo(packageName,
                            PackageManager.GET_META_DATA);

            String dbDir = applicationInfo.dataDir + File.separator + DB_DIR;
            File file = new File(dbDir);
            if (!file.exists()) {
                file.mkdir();
            }
            dbPath = applicationInfo.dataDir + File.separator + DB_DIR
                    + File.separator + DB_NAME;

        } catch (PackageManager.NameNotFoundException e) {

        }
        return dbPath;
    }

    public void copyDB(){
        try {
            InputStream inputStream = context.getAssets().open("class_recipe.sqlite");
            if(databasePath != null){
                File file = new File(databasePath);
                if(!file.exists()){
                    file.createNewFile();
                }

                FileOutputStream outputStream = new FileOutputStream(file);
                byte[] buffer = new byte[1024 * 4];
                int count = 0;
                while( (count = inputStream.read(buffer)) != -1 ){
                    outputStream.write(buffer, 0, count);
                }
                outputStream.close();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}