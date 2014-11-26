package model;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Christophe on 25/11/2014.
 */
public class DBHelper extends SQLiteOpenHelper {

    // Commandes sql pour la création de la base de données
    private static final String DATABASE_CREATE_PAGE = "create table PAGE (" +
            "IDPAGE INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "NOMPAGE TEXT NOT NULL" +
            ");";

    private static final String DATABASE_CREATE_BOUTON = "create table BOUTON (" +
            "IDBOUTON INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "IDPAGE INTEGER, " +
            "NOMBOUTON TEXT NOT NULL, " +
            "FOREIGN KEY (IDPAGE) REFERENCES PAGE(IDPAGE)" +
            ");";

    private static final String DATABASE_NAME = "contents.db";

    public DBHelper(Context context){

        super(context,DATABASE_NAME,null,1);
    }

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DATABASE_CREATE_PAGE);
        db.execSQL(DATABASE_CREATE_BOUTON);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS BOUTON");
        db.execSQL("DROP TABLE IF EXISTS PAGE");
        onCreate(db);
    }

    public void insetPageQuery( String nomPage ){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO PAGE(NOMPAGE) VALUES("+DatabaseUtils.sqlEscapeString(nomPage)+");");
    }

    public void deletePageQuery( int idPage ){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM PAGE WHERE IDPAGE="+idPage+";");
    }

    public Cursor selectPageQuery(){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM PAGE;", null);
        return result;
    }

    public void insertBoutonQuery( int idPage, String nomBouton ){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO BOUTON(IDPAGE, NOMBOUTON) VALUES("+idPage+", "+ DatabaseUtils.sqlEscapeString(nomBouton)+");");
    }

    public void deleteBoutonQuery( int idBouton ){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM BOUTON WHERE IDBOUTON="+idBouton+";");
    }

    public Cursor selectBoutonQuery(){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM BOUTON;", null);
        return result;
    }
}