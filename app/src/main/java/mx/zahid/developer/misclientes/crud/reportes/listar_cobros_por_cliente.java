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
import mx.zahid.developer.misclientes.crud.cobros.agregar_cobro;
import mx.zahid.developer.misclientes.crud.reparaciones.agregar_reparacion;
import mx.zahid.developer.misclientes.database.DataBaseManager;

/**
 * Created by developer on 23/04/2017.
 */

public class listar_cobros_por_cliente extends DialogFragment{

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
        View v = lf.inflate(R.layout.listar_cobros_por_cliente, container, false);

        Bundle mArgs = getArguments();
        idcliente = mArgs.getString("idClienteDashboard");
        nombre = mArgs.getString("nombreCliente");

        getDialog().setTitle("Historal de Cobros de "+nombre);

        manager = new DataBaseManager(v.getContext());

        lista = (ListView) v.findViewById(R.id.listCobrosPorCliente);

        // if(manager.CN_ESTADO_PEDIDO == "FALSE") {
        String[] from = new String[]{manager.CN_COBRO_FECHA, manager.CN_COBRO_DETALLES, manager.CN_COBRO_CANTIDAD, manager.CN_COBRO_CLIENTES_ID};


        //em la siguiente linea indicamos el layout
        //a donde queremos que se muestre el listview


        int[] to = new int[]{R.id.fechaCobroPorCliente, R.id.detalleCobroPorCliente, R.id.cantidadCobradaPorCliente};

        cursor = manager.cargarCobrosPorCliente(idcliente.toString());

        adaptador = new SimpleCursorAdapter(v.getContext(), R.layout.tableview_cobros_por_cliente_design, cursor, from, to, 0);


        //por ultimo agregamos lo siguiente
        lista.setAdapter(adaptador);


        //SI EL BOTON ES UN BOTON DE TIPO FLOATINGBUTTON
        //LA LLAMADA DEBER SER TAMBIEN FLOATINGBUTTON

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fabCobro);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {




                Intent ven=new Intent(getActivity().getApplicationContext(),agregar_cobro.class);

                ven.putExtra("idClienteDashboard", idcliente);

              startActivity(ven);

            }
        });



        Button close_dash;
        close_dash = (Button) v.findViewById(R.id.btn_close_dash_cobros);
        close_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cerrar fragmento
                getActivity().getFragmentManager().beginTransaction().remove(listar_cobros_por_cliente.this).commit();

            }
        });


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                final Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the state's capital from this row in the database.
                final String id_cobro =
                        cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                try{





                    generador = new Random();
                    n1 = Math.abs(generador.nextInt() % 10);
                    n2 = Math.abs(generador.nextInt() % 10);
                    resultado = n1 + n2;


                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setMessage("Eliminar Cobro, Nota: Si eliminas el cobro no será descontado de la cuenta del cliente");
                    builder.setTitle("Elige una opcion");
                    builder.setCancelable(false);

                    builder.setNeutralButton("Salir",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {


                                    dialog.cancel();
                                }
                            });

                    builder.setNegativeButton("Eliminar",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
                                    b.setTitle("Por seguridad, resuelve la siguiente operación matematica");
                                    b.setMessage("¿Cual es la suma de " + n1 + "+" + n2 + "?");

                                    final EditText input = new EditText(getActivity());
                                    b.setView(input);
                                    b.setPositiveButton("Comprobar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int whichButton) {

                                            try {
                                                if (Integer.parseInt(input.getText().toString()) == resultado) {



                                                    manager.eliminarCobro(id_cobro);
                                                    Snackbar.make(getView(), "Eliminado Correctamente", Snackbar.LENGTH_LONG)
                                                            .setActionTextColor(Color.rgb(241, 196, 15))
                                                            .show();


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
                    AlertDialog alert = builder.create();
                    alert.show();





                }
                catch (Exception e){
                    Snackbar.make(getView(), "No se pudo eliminar", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.rgb(241, 196, 15))
                            .show();
                }
            }
        });




        return v;
    }
    @Override
    public void onResume() {
        super.onResume();
        Cursor c = manager.cargarCobrosPorCliente(idcliente);
        adaptador.changeCursor(c);
    }
}

