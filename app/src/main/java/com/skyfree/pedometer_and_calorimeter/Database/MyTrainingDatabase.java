package com.skyfree.pedometer_and_calorimeter.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.skyfree.pedometer_and_calorimeter.Object.TrainingInformation;

import java.util.ArrayList;

public class MyTrainingDatabase extends SQLiteOpenHelper{
    private static final String DB_NAME = "pedometer_and_calorimeter.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "traningInformation";
    private static final String KEY_ID = "id";
    private static final String KEY_STEPS = "steps";
    private static final String KEY_DISTANCE = "distance";
    private static final String KEY_CALORIES = "calories";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIMETRAINING = "timeTraining";

    public MyTrainingDatabase(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    public MyTrainingDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void add(TrainingInformation information){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();

        value.put(KEY_STEPS, information.getSteps());
        value.put(KEY_DISTANCE, information.getDistance());
        value.put(KEY_CALORIES, information.getCalories());
        value.put(KEY_DATE, information.getDate());
        value.put(KEY_TIMETRAINING, information.getTimeTraining());
        db.insert(TABLE_NAME, null, value);

        db.close();
    }

    public void delete(TrainingInformation information){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + "=?", new String[] {String.valueOf(information.getId())});
        db.close();
    }

    public ArrayList<TrainingInformation> getAllInformation() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<TrainingInformation> informationList = new ArrayList();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                TrainingInformation information = new TrainingInformation();
                information.setId(cursor.getInt(0));
                information.setSteps(cursor.getInt(1));
                information.setDistance(cursor.getFloat(2));
                information.setCalories(cursor.getFloat(3));
                information.setDate(cursor.getString(4));
                information.setTimeTraining(cursor.getString(5));
                // Thêm vào danh sách.
                informationList.add(information);
            } while (cursor.moveToNext());
        }
        // return note list
        return informationList;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_trainingInformation_table = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER, %s FLOAT, %s FLOAT, %s TEXT, %s TEXT)", TABLE_NAME, KEY_ID, KEY_STEPS, KEY_DISTANCE, KEY_CALORIES, KEY_DATE, KEY_TIMETRAINING);
        sqLiteDatabase.execSQL(create_trainingInformation_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String drop_trainingInformation_table = String.format("Drop table if exist!", TABLE_NAME);
        sqLiteDatabase.execSQL(drop_trainingInformation_table);

    }
}
