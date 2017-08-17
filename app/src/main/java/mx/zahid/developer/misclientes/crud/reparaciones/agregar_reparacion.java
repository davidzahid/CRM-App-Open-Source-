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

package mx.zahid.developer.misclientes.crud.reparaciones;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import mx.zahid.developer.misclientes.R;
import mx.zahid.developer.misclientes.crud.clientes.cliente_dashboard;
import mx.zahid.developer.misclientes.crud.cobros.agregar_cobro;
import mx.zahid.developer.misclientes.database.DataBaseManager;


/**
 * Created by developer on 19/04/2017.
 */

public class agregar_reparacion extends AppCompatActivity{


    private Button add_reparacion, imgrs;
    public DataBaseManager managerc;
    ImageView imageview;
    byte[] byteArray;
    private static int RESULT_LOAD_IMAGE = 1;
    //UI REFERENCE
    private TextView fechaRecepcionReparacionTxt;
    private TextView fechaEntregaReparacionTxt;
    private  TextView detalleReparacionTxt;
    private EditText costoReparacionTxt;
    private EditText cantidadReparacion;

    final int SELECT_PICTURE = 1;


    //VARIABLES
    private int mYear, mMonth, mDay;

    private String month;
    private String dayG;

    String idcliente;
    /*VARIABLES USADAS PARA EL SPINNER*/
    private Spinner spinnerStatus;
    String estados[]= {"Pendiente", "Entregado"};
    ArrayAdapter<String> adaptadorEstados;
    String estadoR = "Pendiente";
    //FINALIZA
    //fragment
    private boolean fragmentTransaction = false;
    private android.app.Fragment fragment = null;
    //FINALIZA

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_reparacion);

        /*DEBEMOS INICIALIZAR EL MANAGER*/
        managerc = new DataBaseManager(this);
        /*RECIBIMOS EL DATO EXTERNO DE LA CLASE lista_reparaciones_por_cliente*/

        idcliente = (String) getIntent().getExtras().getSerializable("idClienteListaPorCliente");
      //  Toast.makeText(this,"id cliente: "+idcliente,Toast.LENGTH_LONG).show();
        //FINALIZA
        //llamar a los textview de agregar_reparacion
        fechaRecepcionReparacionTxt = (TextView) findViewById(R.id.fechaRecepcionReparacionCliente);
        fechaEntregaReparacionTxt = (TextView) findViewById(R.id.fechaEntregaReparacionCliente);
        detalleReparacionTxt = (TextView) findViewById(R.id.txt_add_detalleReparacionCliente);
        costoReparacionTxt = (EditText) findViewById(R.id.txt_add_costoReparacionCliente);
        add_reparacion = (Button) findViewById(R.id.btn_add_reparacionCliente);
        cantidadReparacion = (EditText) findViewById(R.id.txt_cantidad_reparacion);
        imgrs = (Button) findViewById(R.id.ImgResourcesBtn);
        InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(fechaRecepcionReparacionTxt.getWindowToken(), 0);
        /*LLENADO DE SPINNER
        * SE HIZO USO DE UNA ADAPTADOR*/
        spinnerStatus = (Spinner) findViewById(R.id.spinner_select_estadoReparacion);
        adaptadorEstados = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,estados);
        adaptadorEstados.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adaptadorEstados);
        //FINALIZA



        //ESCUCHAR AL ELEMENTO SELECCIONADO DEL SPINNER DE ESTADOS DE REPARACION




        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estadoR = adaptadorEstados.getItem(position).toString();

                //  Toast.makeText(getApplicationContext(),"estado: "+estado,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        //FINALIZA

        /*OBTENER FECHA DE RECEPCION*/
        fechaRecepcionReparacionTxt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                final Calendar mcurrentDate=Calendar.getInstance();
                mYear=mcurrentDate.get(Calendar.YEAR);
                mMonth=mcurrentDate.get(Calendar.MONTH);
                mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog mDatePicker=new DatePickerDialog(agregar_reparacion.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                         /* CODIGO PARA OBTENER FECHA  */
                        selectedmonth=selectedmonth+1;
                        if(selectedmonth < 10){month = "0" + selectedmonth;
                        }else{month = ""+selectedmonth;}
                        if(selectedday < 10){dayG = "0" + selectedday;
                        }else{dayG = ""+selectedday;}
                        fechaRecepcionReparacionTxt.setText(selectedyear + "-" + month  + "-" + dayG );
                    }
                },mYear, mMonth, mDay);
                mDatePicker.setTitle("Fecha de Recepción");
                mDatePicker.show();  }
        });
        //FINALIZA

        /*OBTENER FECHA DE ENTREGA*/
        fechaEntregaReparacionTxt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                final Calendar mcurrentDate=Calendar.getInstance();
                mYear=mcurrentDate.get(Calendar.YEAR);
                mMonth=mcurrentDate.get(Calendar.MONTH);
                mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog mDatePicker=new DatePickerDialog(agregar_reparacion.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                         /* CODIGO PARA OBTENER FECHA  */
                        selectedmonth=selectedmonth+1;
                        if(selectedmonth < 10){month = "0" + selectedmonth;
                        }else{month = ""+selectedmonth;}
                        if(selectedday < 10){dayG = "0" + selectedday;
                        }else{dayG = ""+selectedday;}
                        fechaEntregaReparacionTxt.setText(selectedyear + "-" + month  + "-" + dayG );
                    }
                },mYear, mMonth, mDay);
                mDatePicker.setTitle("Fecha de Entrega");
                mDatePicker.show();  }
        });
        //FINALIZA


        imgrs.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

         /* Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.putExtra("crop", "true");
                intent.putExtra("scale", true);
                intent.putExtra("outputX", 256);
                intent.putExtra("outputY", 256);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("return-data", true);

                startActivityForResult(intent, 1);

                //   try{ super.onActivityResult(); }catch (Exception e){}


            }*/
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                intent.putExtra("scale", true);
                intent.putExtra("outputX", 512);
                intent.putExtra("outputY", 512);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
          /*      Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
                */
            }

        });



        /*BOTON */
        add_reparacion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            try{
                    if(Integer.parseInt(costoReparacionTxt.getText().toString()) >= 0) {
                        //cantidad,fecha,detalles,id_cliente

                        Float cantidadToFloat = Float.parseFloat(costoReparacionTxt.getText().toString());
                        String fechaRecepcion = fechaRecepcionReparacionTxt.getText().toString();
                        String fechaEntrega = fechaEntregaReparacionTxt.getText().toString();
                        String conceptoToString = detalleReparacionTxt.getText().toString();
                        Integer cantidadReparacionToString = Integer.parseInt(cantidadReparacion.getText().toString());
                        Float totalReparacionToString;
                        if(cantidadReparacionToString >= 1) {
                            totalReparacionToString  = cantidadReparacionToString * cantidadToFloat;
                        }else{
                            cantidadReparacionToString = 1;
                            totalReparacionToString = cantidadToFloat;
                        }
                        managerc.insertarReparacion(fechaRecepcion,fechaEntrega,conceptoToString,estadoR,cantidadToFloat,Integer.parseInt(idcliente),byteArray, cantidadReparacionToString, totalReparacionToString);
                       Snackbar.make(v, "Reparación Agregada Correctamente", Snackbar.LENGTH_LONG)
                                .setActionTextColor(Color.rgb(241, 196, 15))
                                .show();
                        agregar_reparacion.this.finish();
                        fragment = new cliente_dashboard();
                        fragmentTransaction = true;
                    }
            }catch (Exception e){

                Toast error =
                        Toast.makeText(getApplicationContext(),
                                "Debes llenar los campos correctamente, por favor verifica la información e intenta nuevamente", Toast.LENGTH_LONG);

                error.show();
            }


            }
        });

        //FINALIZA

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        agregar_reparacion.this.finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
             imageview = (ImageView) findViewById(R.id.imageView);
            imageview.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            Bitmap bmp = BitmapFactory.decodeFile(picturePath);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byteArray = stream.toByteArray();
        }



    }

}
