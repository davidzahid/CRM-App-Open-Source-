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

package mx.zahid.developer.misclientes;

import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import android.util.Log;
import android.webkit.MimeTypeMap;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.DriveApi.DriveContentsResult;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi.DriveContentsResult;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder.DriveFileResult;
import com.google.android.gms.drive.MetadataChangeSet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.channels.FileChannel;

import mx.zahid.developer.misclientes.crud.Settings;
import mx.zahid.developer.misclientes.crud.blocdenotas.Simple_NotepadActivity;
import mx.zahid.developer.misclientes.crud.pedidos.lista_pedidos;
import mx.zahid.developer.misclientes.crud.reparaciones.lista_reparaciones_;
import mx.zahid.developer.misclientes.database.DataBaseManager;
import mx.zahid.developer.misclientes.crud.clientes.lista_clientes;
import mx.zahid.developer.misclientes.fragments.Fragment1;
import mx.zahid.developer.misclientes.login.loginUsers;

import static mx.zahid.developer.misclientes.Driver_utils.fileCallback;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CreateFileActivity";

    GoogleApiClient mGAC;
    private static final String SAMPLE_DB_NAME = "testeosql";
    private static final String DB_NAME = "clientes.sqlite";
    public DataBaseManager manager;
    DriveId mDriveId;
    String currentDBPath;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    public static int score;
    public GoogleApiClient getGoogleApiClient() {
        return mGAC;
    }


    //   EditText usuario_txt;
   // EditText password_txt;

    /**
     *
     *
     * TESTEO
     * */
    private Toolbar appbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    boolean fragmentTransaction = false;
    Fragment fragment = null;
    String id;
    /*
    * USAR Y BORRAR TESTEO
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String name = (String) getIntent().getExtras().getSerializable("nombre");
         id = (String) getIntent().getExtras().getSerializable("id");


    /*    mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this FragmentActivity /,
                        this OnConnectionFailedListener /)
                .addApi(Drive.API)
                .addScope(Drive.SCOPE_FILE)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
*/

        /*GD AUTORITHY*/

        /*GD*/




        /*
        *
        * TESTEO
        *
        *
        * */



        /*test de cambiar el valor del textview,
        * pero aun no se ha logrado que funic*/
        try {
            TextView nameTxt = (TextView) findViewById(R.id.textView11);
            nameTxt.setText(name);
        }catch (Exception e){

        }

        appbar = (Toolbar)findViewById(R.id.appbar);
        setSupportActionBar(appbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        navView = (NavigationView)findViewById(R.id.navview);

        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {



                        switch (menuItem.getItemId()) {
                            case R.id.menu_seccion_0:
                                Intent ven2=new Intent(getApplicationContext(), mx.zahid.developer.misclientes.crud.inventario.Simple_NotepadActivity.class);

                                startActivity(ven2);
                                break;
                            case R.id.menu_seccion_1:
                                fragment = new lista_clientes();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_seccion_2:
                                fragment = new lista_pedidos();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_seccion_3:
                                fragment = new lista_reparaciones_();
                                fragmentTransaction = true;
                                break;
                           case R.id.menu_opcion_1:
                               Intent ven=new Intent(getApplicationContext(),Simple_NotepadActivity.class);
                               startActivity(ven);
                                break;
                            case R.id.menu_opcion_2:

                                Intent ven3=new Intent(getApplicationContext(),Settings.class);
                                ven3.putExtra("id",id);
                                startActivity(ven3);
                                break;
                            case R.id.menu_opcion_5:
                                Intent ven5=new Intent(getApplicationContext(),Desarrollador.class);
                                startActivity(ven5);
                                break;
                            case R.id.menu_opcion_3:
                                finish();
                                break;
                        }

                        if(fragmentTransaction) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, fragment)
                                    .commit();

                            menuItem.setChecked(true);
                            getSupportActionBar().setTitle(menuItem.getTitle());
                        }

                        drawerLayout.closeDrawers();

                        return true;
                    }
                });

        /*
        * TERMINA TESTEO USAR Y BORRAR
        * */
        //mandamos llamar nuestro objeto sqlite
        //el objeto ahora se llama manager
        //y es por el cual contralaremos
        //las inserciones, modificiacion y eliminacion
        //de los datos de la base de datos
       // manager = new DataBaseManager(this);





        exportDatabse(DB_NAME);
        copyDBToSDCard();

    }


    public void exportDatabse(String databaseName) {

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();


                    Log.i(TAG, "exportDatabse: se guardo");
                    String currentDBPath = "//data//" + getPackageName() + "//databases//" + databaseName + "";
                    String backupDBPath = "clientes.sqlite";
                    File currentDB = new File(data, currentDBPath);
                    File backupDB = new File(sd, backupDBPath);

                    if (currentDB.exists()) {
                        FileChannel src = new FileInputStream(currentDB).getChannel();
                        FileChannel dst = new FileOutputStream(backupDB).getChannel();
                        dst.transferFrom(src, 0, src.size());
                        src.close();
                        dst.close();
                    }


        } catch (Exception e) {

        }

    }


    public void copyDBToSDCard() {
        try {
            InputStream myInput = new FileInputStream("/data/data/"+getPackageName()+"/databases/"+DB_NAME);

            File file = new File(Environment.getExternalStorageDirectory().getPath()+"/"+DB_NAME);
            if (!file.exists()){
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    Log.i("FO","File creation failed for " + file);
                }
            }

            OutputStream myOutput = new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+"/"+DB_NAME);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer))>0){
                myOutput.write(buffer, 0, length);
            }

            //Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
            Log.i("FO","COPIADO");

        } catch (Exception e) {
            Log.i("FO","exception="+e);
        }


    }
    /*
    * TESTEO
    *
    * */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch(item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /*
    *
    * USAR Y ELIMINAR TESTEO
    * */

    @Override
    protected void onResume() {
        super.onResume();


    }


    final private ResultCallback<DriveContentsResult> driveContentsCallback = new
            ResultCallback<DriveContentsResult>() {
                @Override
                public void onResult(DriveContentsResult result) {
                    if (!result.getStatus().isSuccess()) {
                        Log.i(TAG, "Error while trying to create new file contents");
                        return;
                    }
                    final DriveContents driveContents = result.getDriveContents();

                    // Perform I/O off the UI thread.
                    new Thread() {
                        @Override
                        public void run() {
                            // write content to DriveContents
                            OutputStream outputStream = driveContents.getOutputStream();
                            Writer writer = new OutputStreamWriter(outputStream);
                            try {
                                writer.write("Hello World!");
                                writer.close();
                            } catch (IOException e) {
                                Log.e(TAG, e.getMessage());
                            }

                            MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                                    .setTitle("New file")
                                    .setMimeType("text/plain")
                                    .setStarred(true).build();

                            // create a file on root folder
                            Drive.DriveApi.getRootFolder(getGoogleApiClient())
                                    .createFile(getGoogleApiClient(), changeSet, driveContents)
                                    .setResultCallback(fileCallback);
                        }
                    }.start();
                }
            };
    final private ResultCallback<DriveFileResult> fileCallback = new
            ResultCallback<DriveFileResult>() {
                @Override
                public void onResult(DriveFileResult result) {
                    if (!result.getStatus().isSuccess()) {
                        Log.i(TAG, "Error while trying to create the file");
                        return;
                    }
                    Log.i(TAG, "Created a file with content: " + result.getDriveFile().getDriveId());
                }
            };



/*testeo clic en listview*/

/*GOOGLE DRIVE*/


/*TERMINA GOOGLE DRIVE*/

}
