package com.oyp.sort.utils.stroke.utils;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 笔画数据库的工具类，将已有的asserts下的本地db写入到APP的db中去
 */
public class StrokeDataBaseHelper extends SQLiteOpenHelper {

    /**
     * 表的结构
     * CREATE TABLE IF NOT EXISTS "BI_HUA_BEAN" (
     * "_id" INTEGER PRIMARY KEY ,
     * "CHINESE" TEXT,
     * "SUM" TEXT,
     * codePointAt INTEGER
     * );
     */
    public final static String TABLE_NAME = "BI_HUA_BEAN";
    //几个列名
    public final static String COLUMN_ID = "_id";
    public final static String COLUMN_CHINESE = "CHINESE";
    public final static String COLUMN_SUM = "SUM";
    public final static String COLUMN_CODEPOINTAT = "codePointAt";
    //The Android's default system path of your application database.
    private final static String DB_PATH = "/data/data/com.oyp.sort/databases/";
    //数据库名
    private final static String DB_NAME = "ChinessStroke.db";
    private final Context myContext;
    private SQLiteDatabase myDataBase;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    public StrokeDataBaseHelper(Context context) {

        super(context, DB_NAME, null, 3);
        this.myContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     */
    public void createDataBase() {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            //do nothing - database already exist
        } else {
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            //database does't exist yet.
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {
        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if (myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 2) {//如果版本是2.0的，升级下面的内容或修改
            String sql_upgrade = "alter table BI_HUA_BEAN add codePointAt INTEGER";//增加一个列sex
            db.execSQL(sql_upgrade);
            Log.i("onUpgrade", "你在没有卸载的情况下，在线更新了版本2.0,同时列表增加了一个列sex");
        }
    }
    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.
}
