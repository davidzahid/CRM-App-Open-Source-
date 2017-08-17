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

package mx.zahid.developer.misclientes.crud.ventas;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Random;

import mx.zahid.developer.misclientes.R;
import mx.zahid.developer.misclientes.crud.ventas.agregar_venta;
import mx.zahid.developer.misclientes.database.DataBaseManager;

/**
 * Created by developer on 23/04/2017.
 */

public class listar_ventas_por_cliente extends DialogFragment{
    private String mensaje ="";
    //mandamos llamar desde la clase lista_clientes
    String idcliente;
    String nombre;
    public DataBaseManager manager;

    //mandamos llamar al cursor para el listview de clientes
    private Cursor cursor;

    //declaramos el listview que imprimira los clientes
    private ListView lista;

    private Random generador;
    private int n1,n2,resultado;
    private String result;

    //tambien hacemos uso de SimpleCursorAdapter y lo andamos llamar despues
    private SimpleCursorAdapter adaptador;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //cargar la base de datos

        LayoutInflater lf = getActivity().getLayoutInflater();
        View v = lf.inflate(R.layout.listar_ventas_por_cliente, container, false);
        this.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        Bundle mArgs = getArguments();
        idcliente = mArgs.getString("idClienteDashboard");
        nombre = mArgs.getString("nombreCliente");

        getDialog().setTitle("Ventas para "+nombre);

        manager = new DataBaseManager(v.getContext());

        lista = (ListView) v.findViewById(R.id.listVentasPorCliente);

        // if(manager.CN_ESTADO_PEDIDO == "FALSE") {
        String[] from = new String[]{manager.CN_FECHA_ENTREGA_VENTA, manager.CN_ESTADO_VENTA, manager.CN_ID_CLIENTE_VENTA
                , manager.CN_PRODUCTO_VENTA, manager.CN_TOTAL_VENTA, manager.CN_CANTIDAD_VENTA, manager.CN_COSTO_VENTA };


        //em la siguiente linea indicamos el layout
        //a donde queremos que se muestre el listview


        int[] to = new int[]{R.id.fechaEntregaVentaPorCliente,R.id.estadoVentaPorCliente, R.id.nombreClienteVentaPorCliente, R.id.nombreProductoVentaPorCliente, R.id.totalVentaPorCliente, R.id.cantidadVentaPorCliente, R.id.costoVentaPorCliente};

        cursor = manager.cargarVentasPorCliente(idcliente,"Pendiente");

        adaptador = new SimpleCursorAdapter(v.getContext(), R.layout.tableview_ventas_por_cliente_design, cursor, from, to, 0){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(R.id.estadoVentaPorCliente);

                TextView cliente_txt = (TextView) view.findViewById(R.id.nombreClienteVentaPorCliente);


                LinearLayout ll = (LinearLayout) view.findViewById(R.id.designViewVentaPorCliente);

                if (text.getText().toString().equals("Pendiente")) {

                    text.setTextColor(Color.rgb(255, 255, 255));
                    //ll.setBackgroundColor(Color.rgb(231, 76, 60));
                    ll.setBackgroundDrawable(getResources().getDrawable(R.drawable.entregado));


                } else if (text.getText().toString().equals("Entregado")) {
                    text.setTextColor(Color.rgb(255, 255, 255));
                    //   ll.setBackgroundColor(Color.rgb(46, 204, 113));
                    ll.setBackgroundDrawable(getResources().getDrawable(R.drawable.venta_guardada));

                }
                manager.consultarCliente(idcliente, view, "listar_ventas_por_cliente");
                return view;
            }
        };


        //por ultimo agregamos lo siguiente
        lista.setAdapter(adaptador);

        // }
        //OBTENER LA POSICION DE UN CLIENTE A TRAVES DEL LIST VIEW
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                final Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the state's capital from this row in the database.
                final String id_venta =
                        cursor.getString(cursor.getColumnIndexOrThrow("_id"));

                final String estatus = cursor.getString((cursor.getColumnIndexOrThrow("_estado_venta")));


                // costo_pedido = cursor.getFloat(cursor.getColumnIndexOrThrow(manager.CN_COSTO_PEDIDO));

                //     final String id_cliente = cursor.getString(cursor.getColumnIndexOrThrow("pedido_id_cliente_fk"));




                generador = new Random();
                n1 = Math.abs(generador.nextInt() % 10);
                n2 = Math.abs(generador.nextInt() % 10);
                resultado = n1 + n2;


                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            //    builder.setMessage("ELIMINAR VENTA");
                builder.setTitle("Elegir una opción");
                builder.setCancelable(false);

                builder.setNeutralButton("Salir",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                                dialog.cancel();
                            }
                        });

                    mensaje ="Eliminar";

                builder.setNegativeButton(mensaje,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
                                b.setTitle("Para ELIMINAR del historial esta venta es necesario realizar la siguiente suma");
                                b.setMessage("¿Cual es la suma de " + n1 + "+" + n2 + "?");

                                final EditText input = new EditText(getActivity());
                                b.setView(input);
                                b.setPositiveButton("Verificar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        try {
                                            if (Integer.parseInt(input.getText().toString()) == resultado) {

                                                if(estatus.contains("Entregado")) {
                                                    manager.eliminarVenta(id_venta);

                                                    //busca los cambios y restable a pendiente
                                                    Cursor c = manager.buscarVentaporCliente("Entregado",idcliente);
                                                    adaptador.changeCursor(c);
                                                }
                                                if(estatus.contains("Pendiente")) {
                                                    manager.eliminarVenta(id_venta);

                                                    //busca los cambios y restable a pendiente
                                                    Cursor c = manager.buscarVentaporCliente("Pendiente",idcliente);
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
                    builder.setPositiveButton("Guardar en Historial",
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


                                                    if (estatus.contains("Entregado")) {
                                                        manager.actualizarEstadoVenta(id_venta, "Pendiente");

                                                        //busca los cambios y restable a pendiente
                                                        Cursor c = manager.buscarVentaporCliente("Entregado", idcliente);
                                                        adaptador.changeCursor(c);
                                                    } else {
                                                        manager.actualizarEstadoVenta(id_venta, "Entregado");
                                                        //busca los cambios y restablece a entregado
                                                        Cursor c = manager.buscarVentaporCliente("Pendiente", idcliente);
                                                        adaptador.changeCursor(c);
                                                    }


                                                }
                                            } catch (Exception e) {
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
                }
                AlertDialog alert = builder.create();
                alert.show();

            }




        });


        //BOTONES UI

        final Button entregadosButton = (Button) v.findViewById(R.id.ventasPendientesPorCliente);
        final Button historialButton = (Button) v.findViewById(R.id.ventasEntregadosPorCliente);

        //clic en el boton de login


        //clic en el boton de login
        entregadosButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Cursor c = manager.cargarVentasPorCliente(idcliente.toString(),"Pendiente");
                //Cambia el cursor
                adaptador.changeCursor(c);


            }
        });



        historialButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Cursor c = manager.cargarVentasPorCliente(idcliente.toString(),"Entregado");
                //Cambia el cursor
                adaptador.changeCursor(c);


            }
        });

        //SI EL BOTON ES UN BOTON DE TIPO FLOATINGBUTTON
        //LA LLAMADA DEBER SER TAMBIEN FLOATINGBUTTON

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fabVenta);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent ven=new Intent(getActivity(),agregar_venta.class);
                ven.putExtra("idClienteListaPorCliente", idcliente);
                startActivity(ven);

            }
        });



        Button close_dash;
        close_dash = (Button) v.findViewById(R.id.btn_close_dash_ventas);
        close_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cerrar fragmento
                getActivity().getFragmentManager().beginTransaction().remove(listar_ventas_por_cliente.this).commit();

            }
        });








        return v;
    }
    @Override
    public void onResume() {
        super.onResume();
        Cursor c = manager.cargarVentasPorCliente(idcliente,"Pendiente");
        adaptador.changeCursor(c);
    }

}

