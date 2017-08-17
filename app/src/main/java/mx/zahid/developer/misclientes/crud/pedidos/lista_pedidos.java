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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Interpolator;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import mx.zahid.developer.misclientes.R;
import mx.zahid.developer.misclientes.database.DataBaseManager;

public class lista_pedidos extends Fragment {
    public DataBaseManager manager;
    private String mensaje ="";
    //mandamos llamar al cursor para el listview de clientes
    private Cursor cursor;
    //declaramos el listview que imprimira los clientes
    private ListView lista;
    //tambien hacemos uso de SimpleCursorAdapter y lo andamos llamar despues
    private SimpleCursorAdapter adaptador;
    //declaracion del textview para la busqueda de clientes y el boton de busqueda
    private TextView txt_Search;
    public TextView txt_estatus;
    private int textlength = 0;
    private boolean fragmentTransaction = false;
    private Fragment fragmentManager = null;
    //CLIC EN LITSVIEW MENU TESTEO 31 MARZO 2017

    private Random generador;
    private int n1,n2,resultado;
    private String result;

    private float costo_pedido;

    private Button checador;
    public lista_pedidos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        //cargar la base de datos

        LayoutInflater lf = getActivity().getLayoutInflater();
        View v = lf.inflate(R.layout.listar_pedidos, container, false);

        manager = new DataBaseManager(getContext());

        lista = (ListView) v.findViewById(R.id.listPedidos);

        /*ui reference
        txt_estatus = (TextView) v.findViewById(R.id.estadoPedido);
        txt_estatus = (TextView) v;
        txt_estatus.setTextColor(Color.parseColor("#FF0000"));

*/
        //para obtener el
        //nombre de la columna del campo que queremos
        //escribimos el siguiente codigo e inidicamos el campo
        //CN_ID, CN_NAME, CN_SURNAME, CN_DIR, CN_TEL, CN_C_DEBE


        // if(manager.CN_ESTADO_PEDIDO == "FALSE") {
        String[] from = new String[]{manager.CN_FECHA_PEDIDO, manager.CN_FECHA_ENTREGA_PEDIDO, manager.CN_ESTADO_PEDIDO, manager.CN_ID_CLIENTE_PEDIDO, manager.CN_ID_PRODUCTO_PEDIDO, manager.CN_CANTIDAD_PEDIDO, manager.CN_TOTAL_PEDIDO};


        //em la siguiente linea indicamos el layout
        //a donde queremos que se muestre el listview


        int[] to = new int[]{R.id.fechaPedido, R.id.fechaEntrega, R.id.estadoPedido, R.id.nombreClientePedido, R.id.nombreProducto, R.id.cantidadPedidoClientes, R.id.costoPedidoClientes};

        cursor = manager.cargarPedidos("Pendiente");


        //depsues declaramos los dos campos seguido de tableview_usuarios_design
        //condiciones dentro del adaptador
        adaptador = new SimpleCursorAdapter(getContext(), R.layout.tableview_pedidos_design, cursor, from, to, 0) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(R.id.estadoPedido);



                TextView cliente_txt = (TextView) view.findViewById(R.id.nombreClientePedido);


                LinearLayout ll = (LinearLayout) view.findViewById(R.id.designViewPedido);



                if (text.getText().toString().equals("Pendiente")) {

                    text.setTextColor(Color.rgb(255, 255, 255));
                    //ll.setBackgroundColor(Color.rgb(231, 76, 60));
                    ll.setBackgroundDrawable(getResources().getDrawable(R.drawable.pendiente));


                } else if (text.getText().toString().equals("Entregado")) {
                    text.setTextColor(Color.rgb(255, 255, 255));
                    //   ll.setBackgroundColor(Color.rgb(46, 204, 113));
                    ll.setBackgroundDrawable(getResources().getDrawable(R.drawable.entregado));

                }


                manager.consultarCliente(cliente_txt.getText().toString(), view, "listar_pedido");
          //      manager.consultarProducto(producto_txt.getText().toString(), view);

                   /*
                    if(switch_check.isChecked()){
                       // Toast.makeText(getContext(),
                         //       "Cambiado a Pendiente", Toast.LENGTH_SHORT).show();
                        text.setTextColor(Color.RED);
                        text.setText("Pendiente");
                    }else{
                      //  Toast.makeText(getContext(),
                        //        "Cambiado a Entregado", Toast.LENGTH_SHORT).show();
                        text.setTextColor(Color.GREEN);
                        text.setText("Entregado");
                    }*/

                return view;
            }
        };


        //por ultimo agregamos lo siguiente
        lista.setAdapter(adaptador);


        //termina adaptador de listview

        // }
        //OBTENER LA POSICION DE UN CLIENTE A TRAVES DEL LIST VIEW
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                final Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the state's capital from this row in the database.
                final String id_pedido =
                        cursor.getString(cursor.getColumnIndexOrThrow("_id"));

                final String estatus = cursor.getString((cursor.getColumnIndexOrThrow("_estado_pedido")));


                generador = new Random();
                n1 = Math.abs(generador.nextInt() % 10);
                n2 = Math.abs(generador.nextInt() % 10);
                resultado = n1 + n2;


                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setMessage("¿Estás seguro que quieres actualizar" + "\nLos cambios generados son irreversibles");
                builder.setTitle("¡Advertencia!");
                builder.setCancelable(false);

                builder.setNeutralButton("Salir",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                                dialog.cancel();
                            }
                        });
                if(estatus.contains("Entregado")) {
                    mensaje ="Eliminar";
                }else{
                    mensaje = "Cancelar Pedido";
                }
                builder.setNegativeButton(mensaje,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
                                b.setTitle("Para CANCELAR EL PEDIDO es necesario realizar la siguiente suma");
                                b.setMessage("¿Cual es la suma de " + n1 + "+" + n2 + "?");

                                final EditText input = new EditText(getActivity());
                                b.setView(input);
                                b.setPositiveButton("Verificar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        try {
                                        if (Integer.parseInt(input.getText().toString()) == resultado) {

                                            if(estatus.contains("Entregado")) {
                                                manager.eliminarPedido(id_pedido);

                                                //busca los cambios y restable a pendiente
                                                Cursor c = manager.buscarPedido("Entregado");
                                                adaptador.changeCursor(c);
                                            }else{
                                                manager.eliminarPedido(id_pedido);
                                                //busca los cambios y restablece a entregado
                                                Cursor c = manager.buscarPedido("Pendiente");
                                                adaptador.changeCursor(c);
                                            }




                                        }
                                }catch (Exception e){
                                    Snackbar.make(getView(), "Operacion Incorrecta", Snackbar.LENGTH_LONG)
                                            .setActionTextColor(Color.rgb(241, 196, 15))
                                            .show();
                                }


                                    }


                                });
                                b.setNegativeButton("Cancelar", null);
                                b.create().show();
                            }
                        });
                if(estatus.contains("Pendiente")) {
                builder.setPositiveButton("CAMBIAR ESTADO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // metodo que se debe implementar


                                AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
                                b.setTitle("Para continuar es necesario realizar la siguiente operacion");
                                b.setMessage("¿Cual es la suma de " + n1 + "+" + n2 + "?");

                                final EditText input = new EditText(getActivity());
                                b.setView(input);
                                b.setPositiveButton("Verificar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        try {
                                        if (Integer.parseInt(input.getText().toString()) == resultado) {


                                            if(estatus.contains("Entregado")) {
                                                manager.actualizarEstadoPedido(id_pedido,"Pendiente");





                                                //busca los cambios y restable a pendiente
                                                Cursor c = manager.buscarPedido("Entregado");
                                                adaptador.changeCursor(c);
                                            }else{
                                                manager.actualizarEstadoPedido(id_pedido,"Entregado");
                                                //busca los cambios y restablece a entregado
                                                Cursor c = manager.buscarPedido("Pendiente");
                                                adaptador.changeCursor(c);
                                            }




                                        }
                                }catch (Exception e){
                                    Snackbar.make(getView(), "Operacion Incorrecta", Snackbar.LENGTH_LONG)
                                            .setActionTextColor(Color.rgb(241, 196, 15))
                                            .show();
                                }


                                    }


                                });
                                b.setNegativeButton("Cancelar", null);
                                b.create().show();
                            }
                        });}
                AlertDialog alert = builder.create();
                alert.show();

            }




        });


        //clic en el boton de todos
      //  final Button todosButton = (Button) v.findViewById(R.id.todosPedidos);
        final Button pendientesButton = (Button) v.findViewById(R.id.pedidosPendientes);
        final Button entregadosButton = (Button) v.findViewById(R.id.pedidosEntregados);

      /*  todosButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Cursor c = manager.cargarPedidos();
                adaptador.changeCursor(c);


            }
        });
*/
        //clic en el boton de login

        pendientesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Cursor c = manager.buscarPedido("Pendiente");
                //Cambia el cursor
                adaptador.changeCursor(c);


            }
        });
        //clic en el boton de login

        entregadosButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Cursor c = manager.buscarPedido("Entregado");
                //Cambia el cursor
                adaptador.changeCursor(c);


            }
        });


        //SI EL BOTON ES UN BOTON DE TIPO FLOATINGBUTTON
        //LA LLAMADA DEBER SER TAMBIEN FLOATINGBUTTON
/*
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
        //          }
//        });


           /*texto testeo 2*/
/*
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
        });*/



        return v;


    }



}
