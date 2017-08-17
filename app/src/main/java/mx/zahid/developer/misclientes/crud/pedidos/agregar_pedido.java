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

package mx.zahid.developer.misclientes.crud.pedidos;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

import mx.zahid.developer.misclientes.R;
import mx.zahid.developer.misclientes.crud.clientes.cliente_dashboard;
import mx.zahid.developer.misclientes.crud.cobros.agregar_cobro;
import mx.zahid.developer.misclientes.database.DataBaseManager;


/**
 * Created by developer on 19/04/2017.
 */

public class agregar_pedido extends AppCompatActivity{


    private Button add_pedido;
    public DataBaseManager managerc;

    //UI REFERENCE
    private TextView fechaPedidoTxt;
    private TextView fechaEntregaPedidonTxt;
    private  TextView detallePedidoTxt;
    private TextView costoPedidoTxt;
    private TextView productoPedidoTxt;
    private EditText cantidadPedido;


    //VARIABLES
    private int mYear, mMonth, mDay;

    private String month;
    private String dayG;

    String idcliente;
    /*VARIABLES USADAS PARA EL SPINNER*/
    private Spinner spinnerStatusPedido;
    String estados[]= {"Pendiente", "Entregado"};
    ArrayAdapter<String> adaptadorEstados;
    String estadoP = "Pendiente";
    //FINALIZA
    //fragment
    private boolean fragmentTransaction = false;
    private android.app.Fragment fragment = null;
    //FINALIZA

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_pedido);

        /*DEBEMOS INICIALIZAR EL MANAGER*/
        managerc = new DataBaseManager(this);
        /*RECIBIMOS EL DATO EXTERNO DE LA CLASE lista_reparaciones_por_cliente*/

        idcliente = (String) getIntent().getExtras().getSerializable("idClienteListaPorCliente");
        //  Toast.makeText(this,"id cliente: "+idcliente,Toast.LENGTH_LONG).show();
        //FINALIZA
        //llamar a los textview de agregar_reparacion
        fechaPedidoTxt = (TextView) findViewById(R.id.fechaPedidoCliente);
        fechaEntregaPedidonTxt = (TextView) findViewById(R.id.fechaEntregaCliente);
        detallePedidoTxt = (TextView) findViewById(R.id.txt_add_detallePedidoCliente);
        costoPedidoTxt = (TextView) findViewById(R.id.txt_add_costoPedidoCliente);
        productoPedidoTxt = (TextView) findViewById(R.id.txt_add_nombreProductoCliente);
        add_pedido = (Button) findViewById(R.id.btn_add_pedidoCliente);
        cantidadPedido = (EditText) findViewById(R.id.txt_add_cantidadCliente);

        InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(fechaPedidoTxt.getWindowToken(), 0);
        /*LLENADO DE SPINNER
        * SE HIZO USO DE UNA ADAPTADOR*/
        spinnerStatusPedido = (Spinner) findViewById(R.id.spinner_select_estadoPedidoCliente);
        adaptadorEstados = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,estados);
        adaptadorEstados.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatusPedido.setAdapter(adaptadorEstados);
        //FINALIZA
        //ESCUCHAR AL ELEMENTO SELECCIONADO DEL SPINNER DE ESTADOS DE REPARACION
        spinnerStatusPedido.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estadoP = adaptadorEstados.getItem(position).toString();

                //  Toast.makeText(getApplicationContext(),"estado: "+estado,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //FINALIZA

        /*OBTENER FECHA DE RECEPCION*/
        fechaPedidoTxt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                final Calendar mcurrentDate=Calendar.getInstance();
                mYear=mcurrentDate.get(Calendar.YEAR);
                mMonth=mcurrentDate.get(Calendar.MONTH);
                mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog mDatePicker=new DatePickerDialog(agregar_pedido.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                         /* CODIGO PARA OBTENER FECHA  */
                        selectedmonth=selectedmonth+1;
                        if(selectedmonth < 10){month = "0" + selectedmonth;
                        }else{month = ""+selectedmonth;}
                        if(selectedday < 10){dayG = "0" + selectedday;
                        }else{dayG = ""+selectedday;}
                        fechaPedidoTxt.setText(selectedyear + "-" + month  + "-" + dayG );
                    }
                },mYear, mMonth, mDay);
                mDatePicker.setTitle("Fecha de Recepción");
                mDatePicker.show();  }
        });
        //FINALIZA

        /*OBTENER FECHA DE ENTREGA*/
        fechaEntregaPedidonTxt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                final Calendar mcurrentDate=Calendar.getInstance();
                mYear=mcurrentDate.get(Calendar.YEAR);
                mMonth=mcurrentDate.get(Calendar.MONTH);
                mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog mDatePicker=new DatePickerDialog(agregar_pedido.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                         /* CODIGO PARA OBTENER FECHA  */
                        selectedmonth=selectedmonth+1;
                        if(selectedmonth < 10){month = "0" + selectedmonth;
                        }else{month = ""+selectedmonth;}
                        if(selectedday < 10){dayG = "0" + selectedday;
                        }else{dayG = ""+selectedday;}
                        fechaEntregaPedidonTxt.setText(selectedyear + "-" + month  + "-" + dayG );
                    }
                },mYear, mMonth, mDay);
                mDatePicker.setTitle("Fecha de Entrega");
                mDatePicker.show();  }
        });
        //FINALIZA

        /*BOTON */
        add_pedido.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

        try{

                if(Integer.parseInt(costoPedidoTxt.getText().toString()) >= 0) {
                    //cantidad,fecha,detalles,id_cliente

                    Float cantidadToFloat = Float.parseFloat(costoPedidoTxt.getText().toString());
                    String fechaPedido = fechaPedidoTxt.getText().toString();
                    String fechaEntrega = fechaEntregaPedidonTxt.getText().toString();
                    String detalleToString = detallePedidoTxt.getText().toString();
                    String productoToString = productoPedidoTxt.getText().toString();
                    Integer cantidadPedidotxt = Integer.parseInt(cantidadPedido.getText().toString());
                    Float totalPedido;
                    if(cantidadPedidotxt >= 1) {
                        totalPedido  = cantidadPedidotxt * cantidadToFloat;
                    }else{
                        cantidadPedidotxt = 1;
                        totalPedido = cantidadToFloat;
                    }


                    managerc.insertarPedido(fechaPedido, fechaEntrega, detalleToString, estadoP, cantidadToFloat, productoToString,Integer.parseInt(idcliente), cantidadPedidotxt, totalPedido);

                    Snackbar.make(v, "Reparación Agregada Correctamente", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.rgb(241, 196, 15))
                            .show();
                    agregar_pedido.this.finish();
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

        agregar_pedido.this.finish();
    }

}
