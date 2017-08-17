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

package mx.zahid.developer.misclientes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static mx.zahid.developer.misclientes.database.DataBaseManager.CREATE_TABLE_VENTAS;

/**
 * Created by developer on 27/03/2017.
 */

public class DbHelper extends SQLiteOpenHelper{

    //////////////////////////////////////////////////////////////////
    /*REQUIERE DE LOS SIGUIENTES PARAMETROS*/                       //
                                                                    //
    /*NOMBRE DEL ARCHIVO DE LA BASE DE DATOS*/                      //
    private static final String DB_NAME = "clientes.sqlite";        //
                                                                    //
    /*CONSTANTE QUE ES LA VERSION DEL ESQUEMA*/                     //
    private static final int DB_SCHEME_VERSION = 2;
    private final String DB_FILEPATH;                               //
    //////////////////////////////////////////////////////////////////





    /*HAY QUE CREAR EL CONSTRAUCTOR DE LA CLASE*/
     /*PASAMOS EL NOMBRE Y LA VERSION AL CONSTRUCTOR*/
    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_SCHEME_VERSION);
        final String packageName = context.getPackageName();
        DB_FILEPATH = "/data/data/" + packageName + "/databases/"+DB_NAME;
    }

    /*FIN DEL CONSTRUSCTOR DE LA CLASE*/

    /*REQUERIMOS DE LOS SIGUIENTES DOS METODOS*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        //creacion de la base de datos
        //una vez creada la tabla
        //metodo exectSQL que ejectura sql
        //MANDAMOS LLAMAR LA TABLA CREADA

        db.execSQL(DataBaseManager.CREATE_TABLE_CLIENTES);
        db.execSQL(DataBaseManager.CREATE_TABLE_PROVEEDORES);
        db.execSQL(DataBaseManager.CREATE_TABLE_COBROS);
        db.execSQL(DataBaseManager.CREATE_TABLE_USUARIOS);
        db.execSQL(DataBaseManager.CREATE_TABLE_PRODUCTOS);
        db.execSQL(DataBaseManager.CREATE_TABLE_PEDIDOS);
        db.execSQL(DataBaseManager.CREATE_TABLE_REPARACIONES);
        db.execSQL(DataBaseManager.CREATE_TABLE_VENTAS);


        /*SIGUIENTE DE ESTO HAY QUE CREAR UNA INSTANCIA
        * DE DBHELPER EN LA ACTIVIDAD PRINCIPAL DE LA APLICACION
        * EN ESTE CASO MAINACTIVITY.JAVA*/


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // db.execSQL(DataBaseManager.CREATE_TABLE_VENTAS);

    }


    public void backupDatabase() throws IOException {

        if (isSDCardWriteable()) {
            // Open your local db as the input stream
            String inFileName = DB_FILEPATH;
            File dbFile = new File(inFileName);
            FileInputStream fis = new FileInputStream(dbFile);

            String outFileName = Environment.getExternalStorageDirectory() + "/clientes";
            // Open the empty db as the output stream
            OutputStream output = new FileOutputStream(outFileName);
            // transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            // Close the streams
            output.flush();
            output.close();
            fis.close();
        }
    }

    private boolean isSDCardWriteable() {
        boolean rc = false;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            rc = true;
        }
        return rc;
    }
    /*FINALIZAN LOS METODOS*/
}
