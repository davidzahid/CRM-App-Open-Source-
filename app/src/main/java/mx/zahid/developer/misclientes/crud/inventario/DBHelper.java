/*
 * Copyright (c) 2017. David Zahid Jiménez Grez. All rights reserved.
 *
 * Creative Commons License.
 *
 * CRM App is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your discretion) any later version.
 * CRM App  is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CRM App. If not, see https://www.gnu.org/licenses/.
 */

package mx.zahid.developer.misclientes.crud.inventario;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {

	private Context ctx;
	//version of database
	private static final int version = 1;
	//database name
	private static final String DB_NAME = "inventarioDB";
	//name of table
	private static final String TABLE_NAME = "inventario";
	//column names
	private static final String KEY_ID = "id";
	private static final String KEY_TITLE = "noteTitle";
	private static final String KEY_CONTENT = "noteContent";
	private static final String KEY_DATE = "date";
	//sql query to creating table in database
	private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_TITLE+" TEXT NOT NULL, "+KEY_CONTENT+" TEXT NOT NULL, "+KEY_DATE+" TEXT);";
	
	//contructor of DBHelper
	public DBHelper(Context context) {
		super(context, DB_NAME, null, version);
		this.ctx = context;
	}

	//creating the table in database
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
	}

	
	//in case of upgrade we're dropping the old table, and create the new one
	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		
		db.execSQL("DROP TABLE IF EXIST " + TABLE_NAME);
		
		onCreate(db);
		
	}
	
	//function for adding the note to database
	public void addNote(String title, String content) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		//creating the contentValues object
		//read more here -> http://developer.android.com/reference/android/content/ContentValues.html
		ContentValues cv = new ContentValues();
		cv.put("noteTitle", title);
		cv.put("noteContent", content);
		cv.put("date", new Date().toString());
		
		//inserting the note to database
		db.insert(TABLE_NAME, null, cv);
		
		//closing the database connection
	//	db.close();
		
		//see that all database connection stuff is inside this method
		//so we don't need to open and close db connection outside this class
		
	}
	
	
	//getting all inventario
	public Cursor getNotes(SQLiteDatabase db) {
		//db.query is like normal sql query
		//cursor contains all inventario 
		Cursor c = db.query(TABLE_NAME, new String[] {KEY_TITLE, KEY_CONTENT}, null, null, null, null, "id DESC");
		//moving to the first note
		c.moveToFirst();
		//and returning Cursor object
		return c;
	}
	
	public Cursor getNotes2(SQLiteDatabase db) {
		//db.query is like normal sql query
		//cursor contains all inventario 
		Cursor c = db.query(TABLE_NAME, new String[] {KEY_ID, KEY_TITLE}, null, null, null, null, "id DESC");
		//moving to the first note
		c.moveToFirst();
		//and returning Cursor object
		return c;
	}
	
	public Cursor getNote(SQLiteDatabase db, int id) {		
		Cursor c = db.query(TABLE_NAME, new String[] {KEY_TITLE, KEY_CONTENT, KEY_DATE}, KEY_ID + " = ?", new String[] { String.valueOf(id) }, null, null, null);
		c.moveToFirst();
		return c;
	}
	
	public void removeNote(int id) {
		SQLiteDatabase db = getWritableDatabase();
		db.delete(TABLE_NAME, KEY_ID + " = ?", new String[] { String.valueOf(id) });
	//	db.close();
	}
	
	public void updateNote(String title, String content, String editTitle) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put("noteTitle", title);
		cv.put("noteContent", content);
		cv.put("date", new Date().toString());
		
		db.update(TABLE_NAME, cv, KEY_TITLE + " LIKE '" +  editTitle +  "'", null);
		
	//	db.close();
		
		
	}

}
