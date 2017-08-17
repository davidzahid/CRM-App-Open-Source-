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

package mx.zahid.developer.misclientes.crud.clientes;


import android.support.design.widget.Snackbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import mx.zahid.developer.misclientes.R;
import mx.zahid.developer.misclientes.database.DataBaseManager;

public class agregar_cliente extends AppCompatActivity {

    private Button add_cliente;
    public DataBaseManager managerc;

    /*TEXTVIEW DE LA TABLA CLIENTES**/
    private TextView txt_nombre,txt_direccion, txt_telefono, txt_deuda;


    private boolean fragmentTransaction = false;
    private android.app.Fragment fragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_cliente);

        managerc = new DataBaseManager(this);
        //BOTÓN PARA AGREGAR NUEVOS CLIENTES
        add_cliente = (Button) findViewById(R.id.btn_add_cliente);
        //LLAMADO DE LOS TEXTVIEW
        txt_nombre = (TextView) findViewById(R.id.txt_add_nombre);
        txt_direccion = (TextView) findViewById(R.id.txt_add_direccion);
        txt_telefono = (TextView) findViewById(R.id.txt_add_telefono);
        txt_deuda = (TextView) findViewById(R.id.txt_add_deuda);

        InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txt_nombre.getWindowToken(), 0);

        //ESCUCHA AL BOTON ADD CLIENTE


        add_cliente = (Button) findViewById(R.id.btn_add_cliente);
        add_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                if(txt_nombre == null && txt_direccion == null && txt_telefono == null || txt_deuda ==null){


                }else {
                    managerc.insertarCliente(txt_nombre.getText().toString(),
                            txt_direccion.getText().toString(), txt_telefono.getText().toString(), Float.parseFloat(txt_deuda.getText().toString()));
                    Snackbar.make(view, "Cliente Agregado Correctamente", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    txt_nombre.setText("");
                    txt_direccion.setText("");
                    txt_telefono.setText("");
                    txt_deuda.setText("");

                    //regresar a la vista del fragmento clientes
                    //siempre hay que declarar las activitys que tengamos en
                    //androidmaifiest.xml

                   if(getFragmentManager().getBackStackEntryCount() == 0){
                        //super.onBackPressed();

                      //finish();



                        /*SOLUCIONAR!!!!!!!!!!!!!!!!*/
                        //escribir aqui la solucion
                        //para que al regresar se actualice el listview



                       finish();
                    }
                    else{ //si no manda el fragment anterior


                        getFragmentManager().popBackStack();
                    }

                /*    android.app.Fragment currentFragment = getFragmentManager().findFragmentByTag("lista_clientes");
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.detach(currentFragment);
                    fragmentTransaction.attach(currentFragment);
                    fragmentTransaction.commit();*/

                }  }catch (Exception e){

                    Toast error =
                            Toast.makeText(getApplicationContext(),
                                    "Debes llenar los campos correctamente, por favor verifica la información e intenta nuevamente", Toast.LENGTH_LONG);

                    error.show();
                }

            }
        });




    }
}
