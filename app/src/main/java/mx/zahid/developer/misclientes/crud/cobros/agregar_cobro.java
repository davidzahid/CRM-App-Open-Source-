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

package mx.zahid.developer.misclientes.crud.cobros;


import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.Snackbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mx.zahid.developer.misclientes.MainActivity;
import mx.zahid.developer.misclientes.R;
import mx.zahid.developer.misclientes.crud.clientes.cliente_dashboard;
import mx.zahid.developer.misclientes.crud.clientes.lista_clientes;
import mx.zahid.developer.misclientes.database.DataBaseManager;

public class agregar_cobro extends AppCompatActivity {

    private Button add_cobro;
    public DataBaseManager managerc;

    //UI REFERENCE
    private TextView fechaCobroTxt;
    private TextView conceptoTxt;
    private TextView cantidadTxt;
    //VARIABLES
    private int mYear, mMonth, mDay;

    private String month;
    private String dayG;

    private boolean fragmentTransaction = false;
    private android.app.Fragment fragment = null;

    //Get data from clientes_dashboard

    //mandamos llamar desde la clase lista_clientes
    String idcliente;

  //  String idcliente = getIntent().getStringExtra("");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_cobro);

       // idcliente = (String) getIntent().getExtras().getSerializable("idClienteDashboard");
        //opcion para recibir de dialogfragment

        idcliente = (String) getIntent().getExtras().getSerializable("idClienteDashboard");
        Toast toast1 =
                Toast.makeText(getApplicationContext(),
                        "EL id del cliente es"+idcliente, Toast.LENGTH_SHORT);

        toast1.show();
        fechaCobroTxt = (TextView) findViewById(R.id.fechaCobroC);
        conceptoTxt = (TextView) findViewById(R.id.txt_add_conceptoCobroC);
        cantidadTxt = (TextView) findViewById(R.id.txt_add_cantidadCobroC);
        add_cobro = (Button) findViewById(R.id.btn_add_cobro_c);

        managerc = new DataBaseManager(this);
        InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(fechaCobroTxt.getWindowToken(), 0);


        //BOTÓN PARA AGREGAR NUEVOS CLIENTES

        //LLAMADO DE LOS TEXTVIEW
      /*  txt_nombre = (TextView) findViewById(R.id.txt_add_nombre);
        txt_direccion = (TextView) findViewById(R.id.txt_add_direccion);
        txt_telefono = (TextView) findViewById(R.id.txt_add_telefono);
        txt_deuda = (TextView) findViewById(R.id.txt_add_deuda);
*/

        //escucha el txt de fechaCobro al momento de dar clic

        fechaCobroTxt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                final Calendar mcurrentDate=Calendar.getInstance();
                mYear=mcurrentDate.get(Calendar.YEAR);
                mMonth=mcurrentDate.get(Calendar.MONTH);
                mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog mDatePicker=new DatePickerDialog(agregar_cobro.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      CODIGO PARA OB  */
                        selectedmonth=selectedmonth+1;
                        Log.i("Fecha", "onDateSet: "+selectedmonth);

                        if(selectedmonth < 10){

                            month = "0" + selectedmonth;
                        }else{

                            month = ""+selectedmonth;
                        }
                        if(selectedday < 10) {

                            dayG = "0" + selectedday;
                        }else{
                            dayG = ""+selectedday;
                        }
                        fechaCobroTxt.setText(selectedyear + "-" + month  + "-" + dayG );
                    }
                },mYear, mMonth, mDay);
                mDatePicker.setTitle("Seleccionar Fecha de Cobro");
                mDatePicker.show();  }
        });
        //ESCUCHA AL BOTON ADD CLIENTE



        add_cobro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {

                    if(Integer.parseInt(cantidadTxt.getText().toString()) >= 0) {
                    //cantidad,fecha,detalles,id_cliente

                    Float cantidadToFloat = Float.parseFloat(cantidadTxt.getText().toString());
                    String fechaToString = fechaCobroTxt.getText().toString();
                    String conceptoToString = conceptoTxt.getText().toString();
                    managerc.insertarCobro(cantidadToFloat, fechaToString, conceptoToString, Integer.parseInt(idcliente));
                //        managerc.insertardPedido("2017-05-01", "2017-05-04", "Payla de 200litros", "Pendiente", (float)1000.1, 1, 1);


                        agregar_cobro.this.finish();
                        fragment = new cliente_dashboard();

                        fragmentTransaction = true;


                    }
                ///    managerf.insertarCobro((float) 45000.20,"2017-01-01","PAGO EN EFECTIVO",1);




                    /*    }else{


                    }*/
                }catch (Exception e){

                    Toast error =
                            Toast.makeText(getApplicationContext(),
                                    "Debes llenar los campos correctamente, por favor verifica la información e intenta nuevamente", Toast.LENGTH_LONG);

                    error.show();
                }


            }
        });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        agregar_cobro.this.finish();


    }
}
