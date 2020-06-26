package com.example.bookmarkimdb.ui.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

public class BDSQLite extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "BOOKMARKDATABASE";
    private static final String TABLE_MOVIES = "MOVIES";

    private static final String ID = "id";
    private static final String PHOTO_PATH = "photo_path";
    private static final String LOCATION = "location";

    private static final String[] COLUMNS = {ID, PHOTO_PATH, LOCATION };

    public BDSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE "+ TABLE_MOVIES +" (" +
                ID + " STRING," +
                PHOTO_PATH + " TEXT," +
                LOCATION + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        this.onCreate(db);
    }

    public void addMovies(Movies movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, movie.getId());
        values.put(PHOTO_PATH, movie.getPhotoPath());
        values.put(LOCATION, movie.getLocation());

        db.insert(TABLE_MOVIES, null, values);
        db.close();
    }

    public Movies getMovie(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MOVIES, // a. tabela
                COLUMNS, // b. colunas
                " id = ?", // c. colunas para comparar
                new String[] { id }, // d. parâmetros
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        if (cursor == null) {
            return null;
        }
        else {
            cursor.moveToFirst();
            return cursorToMovie(cursor);
        }
    }

    private Movies cursorToMovie(Cursor cursor) {
        Movies movie = new Movies();
        movie.setId(cursor.getString(0));
        movie.setPhotoPath(cursor.getString(1));
        movie.setLocation(cursor.getString(2));
        return movie;
    }

    public ArrayList<Movies> getAllMovies() {
        ArrayList<Movies> moviesList = new ArrayList<Movies>();
        String query = "SELECT * FROM " + TABLE_MOVIES + " ORDER BY " + ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                moviesList.add(cursorToMovie(cursor));
            } while (cursor.moveToNext());
        }
        return moviesList;
    }

    public int updateMovie(Movies movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, movie.getId());
        values.put(PHOTO_PATH, movie.getPhotoPath());
        values.put(LOCATION, movie.getLocation());

        int i = db.update(TABLE_MOVIES, //tabela
                values, // valores
                ID + " = ?", // colunas para comparar
                new String[] { movie.getId() }); //param P COMPARAÇÂO
        db.close(); // fecha conexao com o banco
        return i; // número de linhas modificadas
    }

    public int deleteMovie(Movies movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        File file = new File(movie.getPhotoPath());
        file.delete();
        int i = db.delete(TABLE_MOVIES, //tabela
                ID + " = ?", // colunas para comparar
                new String[] { movie.getId().trim() });
        db.close();
        return i;
    }

}