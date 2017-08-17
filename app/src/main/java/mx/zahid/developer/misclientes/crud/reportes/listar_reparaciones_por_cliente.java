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

package mx.zahid.developer.misclientes.crud.reportes;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import mx.zahid.developer.misclientes.R;
import mx.zahid.developer.misclientes.crud.clientes.cliente_dashboard;
import mx.zahid.developer.misclientes.crud.pedidos.agregar_pedido;
import mx.zahid.developer.misclientes.crud.reparaciones.agregar_reparacion;
import mx.zahid.developer.misclientes.database.DataBaseManager;

/**
 * Created by developer on 23/04/2017.
 */

public class listar_reparaciones_por_cliente extends DialogFragment{
    private String mensaje ="";
    //mandamos llamar desde la clase lista_clientes
    String idcliente;
    String nombre;
    public DataBaseManager manager;
    ImageView imgv;
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
        View v = lf.inflate(R.layout.listar_reparaciones_por_cliente, container, false);

        Bundle mArgs = getArguments();
        idcliente = mArgs.getString("idClienteDashboard");
          nombre = mArgs.getString("nombreCliente");
        imgv= (ImageView) v.findViewById(R.id.imageReparacion);
        getDialog().setTitle("Reparaciones de "+nombre);

        manager = new DataBaseManager(v.getContext());

        lista = (ListView) v.findViewById(R.id.listReparacionesPorCliente);

        // if(manager.CN_ESTADO_PEDIDO == "FALSE") {
        String[] from = new String[]{manager.CN_FECHA_RECEPCION_REPARACION, manager.CN_FECHA_ENTREGA_REPARACION, manager.CN_DETALLES_REPARACION, manager.CN_ID_CLIENTE_REPARACION, manager.CN_ESTADO_REPARACION, manager.CN_TOTAL_REPARACION, manager.CN_CANTIDAD_REPARACION};


        //em la siguiente linea indicamos el layout
        //a donde queremos que se muestre el listview


        int[] to = new int[]{R.id.fechaRecepcionPorCliente, R.id.fechaEntregaReparacionPorCliente, R.id.detalleReparacionPorCliente, R.id.nombreClienteReparacionPorCliente, R.id.estadoReparacionPorCliente, R.id.costoReparacionPorCliente, R.id.cantidadReparacionPorCliente};

        cursor = manager.cargarReparacionesPorCliente(idcliente.toString(),"Pendiente");

        adaptador = new SimpleCursorAdapter(v.getContext(), R.layout.tableview_reparaciones_por_cliente_design, cursor, from, to, 0){
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            TextView text = (TextView) view.findViewById(R.id.estadoReparacionPorCliente);

             TextView cliente_txt = (TextView) view.findViewById(R.id.nombreClienteReparacionPorCliente);


            LinearLayout ll = (LinearLayout) view.findViewById(R.id.designViewReparacionesPorCliente);

            if (text.getText().toString().equals("Pendiente")) {

                text.setTextColor(Color.rgb(255, 255, 255));
                //ll.setBackgroundColor(Color.rgb(231, 76, 60));
                ll.setBackgroundDrawable(getResources().getDrawable(R.drawable.pendiente));


            } else if (text.getText().toString().equals("Entregado")) {
                text.setTextColor(Color.rgb(255, 255, 255));
                //   ll.setBackgroundColor(Color.rgb(46, 204, 113));
                ll.setBackgroundDrawable(getResources().getDrawable(R.drawable.entregado));

            }
            manager.consultarCliente(idcliente, view, "listar_reparacion_por_cliente");
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
                final String id_pedido =
                        cursor.getString(cursor.getColumnIndexOrThrow("_id"));

                final String estatus = cursor.getString((cursor.getColumnIndexOrThrow("_estado_reparacion")));

             /*   if(cursor.moveToNext()){
                    byte[] image = cursor.getBlob((cursor.getColumnIndexOrThrow("_foto_producto")));
                    Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
                    imgv.setImageBitmap(bmp);
                }*/
                // costo_pedido = cursor.getFloat(cursor.getColumnIndexOrThrow(manager.CN_COSTO_PEDIDO));

           //     final String id_cliente = cursor.getString(cursor.getColumnIndexOrThrow("pedido_id_cliente_fk"));




                generador = new Random();
                n1 = Math.abs(generador.nextInt() % 10);
                n2 = Math.abs(generador.nextInt() % 10);
                resultado = n1 + n2;


                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setMessage("A continuación se muestran las opciones para el pedido con estatus "+estatus);
                builder.setTitle("Elegir una opción");
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
                    mensaje = "Cancelar Reparacion";
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
                                                manager.eliminarReparacion(id_pedido);

                                                //busca los cambios y restable a pendiente
                                                Cursor c = manager.buscarReparacionporCliente("Entregado",idcliente);
                                                adaptador.changeCursor(c);
                                            }else{
                                                manager.eliminarReparacion(id_pedido);
                                                //busca los cambios y restablece a entregado
                                                Cursor c = manager.buscarReparacionporCliente("Pendiente",idcliente);
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
                builder.setPositiveButton("Cambiar estado",
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
                                                manager.actualizarEstadoReparacion(id_pedido,"Pendiente");

                                                //busca los cambios y restable a pendiente
                                                Cursor c = manager.buscarReparacionporCliente("Entregado",idcliente);
                                                adaptador.changeCursor(c);
                                            }else{
                                                manager.actualizarEstadoReparacion(id_pedido,"Entregado");
                                                //busca los cambios y restablece a entregado
                                                Cursor c = manager.buscarReparacionporCliente("Pendiente",idcliente);
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

        //BOTONES UI
        final Button pendientesButton = (Button) v.findViewById(R.id.reparacionesPendientesPorCliente);
        final Button entregadosButton = (Button) v.findViewById(R.id.reparacionesEntregadosPorCliente);

        //clic en el boton de login

        pendientesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Cursor c = manager.cargarReparacionesPorCliente(idcliente,"Pendiente");
                //Cambia el cursor
                adaptador.changeCursor(c);


            }
        });
        //clic en el boton de login

        entregadosButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Cursor c = manager.cargarReparacionesPorCliente(idcliente,"Entregado");
                //Cambia el cursor
                adaptador.changeCursor(c);


            }
        });

        Button close_dash;
        close_dash = (Button) v.findViewById(R.id.btn_close_dash_reparaciones);
        close_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cerrar fragmento
                getActivity().getFragmentManager().beginTransaction().remove(listar_reparaciones_por_cliente.this).commit();

            }
        });

        //SI EL BOTON ES UN BOTON DE TIPO FLOATINGBUTTON
        //LA LLAMADA DEBER SER TAMBIEN FLOATINGBUTTON

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fabReparacion);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent ven=new Intent(getActivity(),agregar_reparacion.class);
                ven.putExtra("idClienteListaPorCliente", idcliente);
                startActivity(ven);

            }
        });


        return v;
    }
    @Override
    public void onResume() {
        super.onResume();
        Cursor c = manager.cargarReparacionesPorCliente(idcliente,"Pendiente");
        adaptador.changeCursor(c);
    }

}

