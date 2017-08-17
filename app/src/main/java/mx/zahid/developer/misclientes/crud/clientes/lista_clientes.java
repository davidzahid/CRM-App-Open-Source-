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
import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import mx.zahid.developer.misclientes.MainActivity;
import mx.zahid.developer.misclientes.R;
import mx.zahid.developer.misclientes.database.DataBaseManager;

public class lista_clientes extends Fragment {

    public lista_clientes() {
        // Required empty public constructor

    }

    public DataBaseManager manager;

    //mandamos llamar al cursor para el listview de clientes
    private Cursor cursor;
    //declaramos el listview que imprimira los clientes
    private ListView lista;
    //tambien hacemos uso de SimpleCursorAdapter y lo andamos llamar despues
    private SimpleCursorAdapter adaptador;
    //declaracion del textview para la busqueda de clientes y el boton de busqueda
    private TextView txt_Search;
    private int textlength = 0;
    private boolean fragmentTransaction = false;
    private Fragment fragmentManager = null;

    //CLIC EN LITSVIEW MENU TESTEO 31 MARZO 2017




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


         //cargar la base de datos

        LayoutInflater lf = getActivity().getLayoutInflater();
        View v = lf.inflate(R.layout.listar_clientes, container, false);

        manager = new DataBaseManager(getContext());

        lista = (ListView) v.findViewById(R.id.listClientes);
        txt_Search = (TextView) v.findViewById(R.id.txt_search);

        //para obtener el
        //nombre de la columna del campo que queremos
        //escribimos el siguiente codigo e inidicamos el campo
        //CN_ID, CN_NAME, CN_SURNAME, CN_DIR, CN_TEL, CN_C_DEBE

        String[] from = new String[]{manager.CN_NAME, manager.CN_DIR, manager.CN_TEL, manager.CN_C_DEBE.toString()};


        //em la siguiente linea indicamos el layout
        //a donde queremos que se muestre el listview


        int[] to = new int[]{R.id.nombreCliente, R.id.direccionCliente, R.id.telefonoCliente,R.id.deudaCliente};
        cursor = manager.cargarClientes();
        //depsues declaramos los dos campos seguido de two_line_list_item
        adaptador = new SimpleCursorAdapter(getContext(),R.layout.tableview_clientes_design,cursor,from,to,0){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                return view;
            }
        };
        //por ultimo agregamos lo siguiente
        lista.setAdapter(adaptador);



        //termina adaptador de listview


        //OBTENER LA POSICION DE UN CLIENTE A TRAVES DEL LIST VIEW
        lista.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {


                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the state's capital from this row in the database.
                String idcliente =
                        cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                String nombres =
                        cursor.getString(cursor.getColumnIndexOrThrow("_nombres"));

                String direccion =
                        cursor.getString(cursor.getColumnIndexOrThrow("_direccion"));
                String telefono =
                        cursor.getString(cursor.getColumnIndexOrThrow("_telefono"));
                String deuda =
                        cursor.getString(cursor.getColumnIndexOrThrow("_deuda"));




                Toast.makeText(getContext(),
                        idcliente, Toast.LENGTH_SHORT).show();

                //manager.consultarCliente(idcliente, getContext().getApplicationContext());

                //ABRE LA ACTIVIDAD PRINCIPAL DE LA APLICACION
              /*  Intent ven=new Intent(getContext().getApplicationContext(),cliente_dashboard.class);
                ven.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //pasa datos a la actividad que se abrira
                //en este caso a mainac
                //
                ven.putExtra("idCliente", idcliente);
                ven.putExtra("nombreC", nombres);
                ven.putExtra("direccionC", direccion);
                ven.putExtra("telefonoC", telefono);
                ven.putExtra("deudaC", deuda);
                getContext().startActivity(ven);
*/


              /*PASAR DATOS Y ABRIR UN DIALOG FRAGMENT*/
                FragmentManager fm = getActivity().getFragmentManager();
                cliente_dashboard dialog = new cliente_dashboard();
                Bundle args = new Bundle();

                args.putString("idCliente", idcliente);
                args.putString("nombreC", nombres);
                args.putString("direccionC", direccion);
                args.putString("telefonoC", telefono);
                args.putString("deudaC", deuda);


              //  dialog.setStyle(DialogFragment.STYLE_NORMAL,R.style.Theme_AppCompat_DialogWhenLarge);

                dialog.setArguments(args);
                dialog.show(fm, "Hola");
            /*TERMINA DIALOG FRAGMENT*/

            }
        });



                //SI EL BOTON ES UN BOTON DE TIPO FLOATINGBUTTON
        //LA LLAMADA DEBER SER TAMBIEN FLOATINGBUTTON

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //ABRE LA ACTIVIDAD PRINCIPAL DE LA APLICACION

                //siempre hay que declarar las activitys que tengamos en
                //androidmaifiest.xml
                Intent ven=new Intent(getActivity(),agregar_cliente.class);

                //pasa datos a la actividad que se abrira
                //en este caso a mainac
                //
                startActivity(ven);
              /*  Snackbar.make(view, "Se presionó el boton AGREGAR", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });


           /*texto testeo 2*/
        txt_Search.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                // Abstract Method of TextWatcher Interface.
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // Abstract Method of TextWatcher Interface.
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textlength = txt_Search.getText().length();
                //array_sort.clear();
                Cursor c = manager.buscarCliente(txt_Search.getText().toString());
                //Cambia el cursor
                adaptador.changeCursor(c);


         }
        });




        return v;


    }

    @Override
    public void onResume() {
        super.onResume();
        Cursor c = manager.cargarClientes();
        adaptador.changeCursor(c);
    }
}
