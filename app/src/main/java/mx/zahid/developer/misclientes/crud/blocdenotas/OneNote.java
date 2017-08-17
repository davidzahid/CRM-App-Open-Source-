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

package mx.zahid.developer.misclientes.crud.blocdenotas;



import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import mx.zahid.developer.misclientes.R;
public class OneNote extends Activity {
	
	private static final String TAG = "notepad";
	
	//our views for display note title and content
	private TextView noteTitle;
	private TextView createdAt;
	private TextView noteContent;
	
	//dbhelper
	private DBHelper dbhelper;
	private SQLiteDatabase db;
	
	//default values for title and content variables
	private String title = "defaultTitle";
	private int id = 0;
	private String content = "defaultContent";
	private String date = "date";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.onenote);
		
		//initialization of DBHelper
		dbhelper = new DBHelper(getApplicationContext());
		
		
		noteTitle = (TextView) findViewById(R.id.noteTitle);
		noteContent = (TextView) findViewById(R.id.noteContent);
		createdAt = (TextView) findViewById(R.id.createdAt);
		
		// getting intent
		Intent mIntent = getIntent();
		
		id = mIntent.getIntExtra("id", 0);
		
		
		//getting the readable database
		db = dbhelper.getReadableDatabase();
		
		//getting the note from database
		Cursor c = dbhelper.getNote(db, id);
		//closing the database connection
		//db.close();
		
		//getting the content from cursor
		//getString(1) because first column is noteTitle and second is noteContent and the third column is date
		title = c.getString(0).toString();
		content = c.getString(1).toString();
		date = c.getString(2).toString();
		
		//setting notes to our views
		noteTitle.setText(title);
		noteContent.setText(content);
		createdAt.setText(date);
	}
	

}
