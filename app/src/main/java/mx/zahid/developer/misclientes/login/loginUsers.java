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

package mx.zahid.developer.misclientes.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;

import mx.zahid.developer.misclientes.MainActivity;
import mx.zahid.developer.misclientes.R;
import mx.zahid.developer.misclientes.database.DataBaseManager;

public class loginUsers extends AppCompatActivity {

    public DataBaseManager managerf;
    EditText usuario_txt;
    EditText password_txt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_user);
        //mandamos llamar nuestro objeto sqlite
        //el objeto ahora se llama manager
        //y es por el cual contralaremos
        //las inserciones, modificiacion y eliminacion
        //de los datos de la base de datos
        managerf = new DataBaseManager(this);
        //ff
 // managerf.insertarUsuario("Administrador Global","1","1",0);
  /*  managerf.insertarUsuario("Irving Grez Larios","administrador","admin",0);*/
   //  managerf.insertarCliente("Saul Hernandez Juarez","San Antonio #13, Col. Zimapan","7747440296", (float) 0);
     //  managerf.insertarCliente("Samuel Ramirez ","San Peters 41, Mexico, Durango","4125125", (float) 0);
      /*  managerf.insertarCliente("Pedro Fernandez ","La roma 132, zacualtipan","521614", (float) 5000);
        managerf.insertarCliente("Gustavo Hernandez ","Estacion 2, mexico","8572343", (float) 5000);*/
      //  managerf.insertarCliente("Elizabeth Sanchez ","Tiquillo 312, Tasco","5236236", (float) 12000);
//        managerf.insertarCliente("Anastacia Rivera ","San Popular 13, Enormal ","5236236", (float) 4120.50);

      // managerf.insertarCobro((float) 1000.20,"2017-01-01","PAGO EN EFECTIVO",1);
     /*  managerf.insertarCobro(123,"2017-01-01","ningun detalle",1);
        managerf.insertarCobro((float) 120.20,"2017-01-01","PAGO EN EFECTIVO",1);
        managerf.insertarCobro(322,"2017-01-01","ningun detalle",2);
        managerf.insertarCobro((float) 340.10,"2017-01-01","PAGO EN EFECTIVO",6);
        managerf.insertarCobro((float)433.50,"2017-01-01","ningun detalle",3);
        managerf.insertarCobro((float) 350.40,"2017-01-01","PAGO EN EFECTIVO",5);
        managerf.insertarCobro((float)30.66,"2017-01-01","ningun detalle",4);
        managerf.insertarCobro((float) 523.44,"2017-01-01","PAGO EN EFECTIVO",7);*/
     //   managerf.insertarPedido("2017-05-06", "2017-06-12", "Payla", "Entregado", (float)4000, "Caso de 70 Litros", 1);
       // managerf.insertarReparacion("2017-05-06","2017-05-10","Caso en malas condiciones","Pendiente",(float) 950, 1);

      //  managerf.insertarPedido("2017-05-06", "2017-06-12", "Payla", "Entregado", (float)1000.1, "", 3);
       // managerf.insertarPedido("2017-05-06", "2017-06-12", "Payla Grande ", "Pendiente", (float)1000.1, "", 3);
  //      managerf.insertarPedido("2017-05-06", "2017-06-12", "Payla Grande ", "Pendiente", (float)1000.1, "", 1);


      /*  managerf.insertarPedido("2017-05-06", "2017-06-12", "Payla", "Entregado", (float)2000, "", 1);
        managerf.insertarPedido("2017-05-06", "2017-06-13", "Payla", "Entregado", (float)1100, "", 1);
        managerf.insertarPedido("2017-05-06", "2017-06-14", "Payla", "Entregado", (float)100, "", 2);
        managerf.insertarPedido("2017-05-06", "2017-06-15", "Payla", "Entregado", (float)900, "", 1);
        managerf.insertarPedido("2017-05-06", "2017-06-16", "Payla", "Entregado", (float)400, "", 2);

        managerf.insertarPedido("2017-05-06", "2017-06-17", "Payla", "Pendiente", (float)230, "", 1);
        managerf.insertarPedido("2017-05-06", "2017-06-18", "Payla", "Pendiente", (float)400, "", 2);
        managerf.insertarPedido("2017-05-06", "2017-06-19", "Payla", "Pendiente", (float)100, "", 1);
        managerf.insertarPedido("2017-05-06", "2017-06-20", "Payla", "Pendiente", (float)300, "", 2);
        managerf.insertarPedido("2017-05-06", "2017-06-21", "Payla", "Pendiente", (float)211, "", 1);
       managerf.insertarReparacion("2017-05-06","2017-05-10","Mal Estado","Pendiente",(float) 200, 1);
       managerf.insertarReparacion("2017-05-06","2017-05-10","Buen Estado","Entregado",(float) 100, 1);
       managerf.insertarReparacion("2017-05-06","2017-05-10","Mal Estado","Pendiente",(float) 1300, 2);
       managerf.insertarReparacion("2017-05-06","2017-05-10","Mal Estado","Entregado",(float) 300, 2);


   managerf.insertarUsuario("David Jimenez","admin","admin",0);*/


      //  managerf.insertarUsuario("David Jimenez","admin","admin",0);

     //   managerf.insertarReparacionTest("2017-05-06","2017-05-10","Mal Estado","Pendiente",(float) 300, 1,"4");
        usuario_txt = (EditText) findViewById(R.id.username_txt);
        password_txt = (EditText) findViewById(R.id.password_txt);


        //clic en el boton de login
        final Button button = (Button) findViewById(R.id.btn_login);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                String userName=usuario_txt.getText().toString();
                String password=password_txt.getText().toString();

                managerf.consultarUsuario(userName,password,getApplicationContext());
                usuario_txt.setText("");
                password_txt.setText("");

            }
        });






    }
}
