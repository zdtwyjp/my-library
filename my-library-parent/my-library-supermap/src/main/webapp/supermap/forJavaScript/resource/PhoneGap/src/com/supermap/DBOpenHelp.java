/**
 * 
 */
package com.supermap;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
  
public class DBOpenHelp extends SQLiteOpenHelper { 
    private static final int DATABASEVERSION = 1;  
      
    /* 
     * ¹¹Ôìº¯Êý 
     */  
    public DBOpenHelp(Context context,String DATABASENAME) {  
        super(context, DATABASENAME, null, DATABASEVERSION);  
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//db.execSQL("create table IF NOT EXISTS localimg (id integer primary key autoincrement, key varchar(20), data blob)");
		//db.execSQL("create table IF NOT EXISTS keymap (id integer primary key autoincrement, key varchar(20))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
}
