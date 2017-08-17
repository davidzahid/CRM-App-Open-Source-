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

package mx.zahid.developer.misclientes.crud;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import mx.zahid.developer.misclientes.R;
import mx.zahid.developer.misclientes.database.DataBaseManager;

/**
 * Created by developer on 27/06/2017.
 */

public class Settings extends AppCompatActivity {
    public DataBaseManager manager;
    public String id;
    public TextView oldPassword;
    public TextView newPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_layout);
        manager = new DataBaseManager(this);
        id = (String) getIntent().getExtras().getSerializable("id");
        //Toast.makeText(this, "Usuario identificado con la id: "+id, Toast.LENGTH_SHORT).show();
        oldPassword= (TextView) findViewById(R.id.oldPasswordEdit);
        newPassword= (TextView) findViewById(R.id.newPasswordEdit);
        Button btnChange = (Button) findViewById(R.id.change_btn);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(newPassword.getWindowToken(), 0);
        if(oldPassword.length()>1 && newPassword.length()>1) {
            String idU = id;
            String passO = oldPassword.getText().toString();
            String passN = newPassword.getText().toString();
             manager.actualizarUsuario(idU,passO,passN,view);

        }else{
            if(oldPassword.length()<1){
                Snackbar.make(view, "Verifica que el campo de la contraseña actual no este vacio", Snackbar.LENGTH_LONG)
                        .show();
            }
            if(newPassword.length()<1){
                Snackbar.make(view, "Verifica que el campo de la contraseña nueva no este vacio", Snackbar.LENGTH_LONG)
                        .show();
            }
        }

                Log.e("BOTON PRESIONADO", "onClick: ");

            }
        });

    }
}
