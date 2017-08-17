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

package mx.zahid.developer.misclientes.crud.productos;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;

import mx.zahid.developer.misclientes.R;
import mx.zahid.developer.misclientes.database.DataBaseManager;

public class agregar_producto extends AppCompatActivity {

    private Button add_producto;
    public DataBaseManager managerc;
    /*TEXTVIEW DE LA TABLA CLIENTES**/
    private TextView txt_nombre_producto,txt_descripcion_producto;



    private boolean fragmentTransaction = false;
    private android.app.Fragment fragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_producto);

        managerc = new DataBaseManager(this);
        //BOTÓN PARA AGREGAR NUEVOS CLIENTES
        add_producto = (Button) findViewById(R.id.btn_add_producto);
        //LLAMADO DE LOS TEXTVIEW
        txt_nombre_producto = (TextView) findViewById(R.id.txt_add_nombreproducto);

        txt_descripcion_producto = (TextView) findViewById(R.id.txt_add_description);


        //ESCUCHA AL BOTON ADD CLIENTE


        add_producto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                if(txt_nombre_producto == null && txt_descripcion_producto == null){


                }else {

                    managerc.insertarProducto(txt_nombre_producto.getText().toString(),txt_descripcion_producto.getText().toString());

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

                }}catch (Exception e){

                    Toast error =
                            Toast.makeText(getApplicationContext(),
                                    "Debes llenar al menos un campo para poder agregar un cliente", Toast.LENGTH_LONG);

                    error.show();
                }
            }
        });




    }
}
