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
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ViewUtils;
import android.support.v7.widget.helper.ItemTouchUIUtil;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Random;

import mx.zahid.developer.misclientes.ListUtils;
import mx.zahid.developer.misclientes.R;
import mx.zahid.developer.misclientes.Utility;
import mx.zahid.developer.misclientes.crud.cobros.agregar_cobro;
import mx.zahid.developer.misclientes.crud.clientes.lista_clientes;
import mx.zahid.developer.misclientes.crud.pedidos.agregar_pedido;
import mx.zahid.developer.misclientes.crud.productos.agregar_producto;
import mx.zahid.developer.misclientes.crud.reparaciones.agregar_reparacion;
import mx.zahid.developer.misclientes.crud.reportes.listar_cobros_por_cliente;
import mx.zahid.developer.misclientes.crud.reportes.listar_pedidos_por_cliente;
import mx.zahid.developer.misclientes.crud.reportes.listar_reparaciones_por_cliente;
import mx.zahid.developer.misclientes.crud.ventas.listar_ventas_por_cliente;
import mx.zahid.developer.misclientes.database.DataBaseManager;

/**
 * Created by developer on 06/04/2017.
 */



public class cliente_dashboard extends DialogFragment {

    public cliente_dashboard() {
        // Required empty public constructor
    }

    //declaramos el listview que imprimira los clientes
    private ListView listaReparaciones, listaPedidos, listaCobros, listaVentas;

    public DataBaseManager manager;
    //mandamos llamar al cursor para el listview de clientes
    private Cursor cursor,cursor2,cursor3,cursor4;
    //tambien hacemos uso de SimpleCursorAdapter y lo mandamos llamar despues
    private SimpleCursorAdapter adaptador,adaptador2,adaptador3,adaptador4;

    boolean fragmentTransaction = false;
    android.app.Fragment fragment = null;

    View v;
    String idcliente;
    String nombre;

    //UI REFERENCIAS


    EditText nombre_text;
    EditText direccion_txt;
    EditText telefono_txt;
    EditText deuda_txt;


    //generador de numeros para confirmacion
    private CoordinatorLayout coordinatorLayout;



    private Random generador;
    private int n1,n2,resultado;
    private String result;


    LinearLayout menuVisible;
    LinearLayout llVentas;
    LinearLayout llPedidos;
    LinearLayout llReparaciones;
    LinearLayout llCobros;

    ToggleButton tgVentas;
    ToggleButton tgPedidos;
    ToggleButton tgReparaciones;
    ToggleButton tgCobros;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            v = inflater.inflate(R.layout.cliente_dashboard, container, false);
        //    getDialog().setTitle("Cliente");



        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

     //   dialog.setContentView(R.layout.dialog_custom);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //se debe inicializar la base de datos

        manager = new DataBaseManager(v.getContext());
        //recibe datos desde una activity a un dialog fragment
        Bundle mArgs = getArguments();
        idcliente = mArgs.getString("idCliente");
        nombre = mArgs.getString("nombreC");
        String direccion = mArgs.getString("direccionC");
        String telefono = mArgs.getString("telefonoC");
        String deuda = mArgs.getString("deudaC");

        //finaliza

        /*metodo anterior*/
        //mandamos llamar desde la clase lista_clientes
        /*idcliente = (String) getActivity().getIntent().getExtras().getSerializable("idCliente");
        String nombre = (String) getActivity().getIntent().getExtras().getSerializable("nombreC");
        String direccion = (String) getActivity().getIntent().getExtras().getSerializable("direccionC");
        String telefono = (String) getActivity().getIntent().getExtras().getSerializable("telefonoC");
        String deuda = (String) getActivity().getIntent().getExtras().getSerializable("deudaC");
        */
        /*finaliza metodo anterior*/





        EditText nombre_text = (EditText) v.findViewById(R.id.nombreCliente);
        EditText direccion_txt = (EditText) v.findViewById(R.id.direccionCliente);
        EditText telefono_txt = (EditText) v.findViewById(R.id.telefonoCliente);
        EditText deuda_txt = (EditText) v.findViewById(R.id.deudaCliente);

        //bloquer inputkeyboard

        //cerrar


      //manager.consultarCliente(idcliente, v, "cliente_dashboard");
        Cursor c = manager.cargarClientesId(idcliente);
        // Get the state's capital from this row in the database.


        if (c.moveToFirst() == true) {
            String nombre_ = c.getString(1);
            String direccion_ = c.getString(2);
            String telefono_ = c.getString(3);
            String deuda_ = c.getString(4);

            nombre_text.setText(nombre_);
            direccion_txt.setText(direccion_);
            telefono_txt.setText(telefono_);
            deuda_txt.setText(deuda_);


        }






        //checar
        /*Cursor cur = (Cursor) manager.cargarClientes();
        cur.move(Integer.parseInt(idcliente)-1);
        String a = cur.getString(1);
        Toast.makeText(v.getContext(),"a:"+a,Toast.LENGTH_LONG).show();*/
        //manager.consultarCliente(Integer.parseInt(idcliente));



        //ACTIONS

        Button delete_cliente;
        delete_cliente = (Button) v.findViewById(R.id.btn_eliminar);
        delete_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //eliminar cliente
                generarNumeros("eliminar");

            }
        });

        Button update_cliente;
        update_cliente = (Button) v.findViewById(R.id.btn_actualizar);
        update_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //actualizar un cliente

               generarNumeros("actualizar");

            }
        });





        //UI BUTTONS METHODS


        Button add_pedido;
        add_pedido = (Button) v.findViewById(R.id.btn_pedidos_c);
        add_pedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrir para agregar cobro de cobro
             //  Intent ven=new Intent(v.getContext(),listar_pedidos_por_cliente.class);
              //ven.putExtra("idClienteDashboard", idcliente);

             // startActivity(ven);
                android.app.FragmentManager fm = getActivity().getFragmentManager();
                listar_pedidos_por_cliente dialog2 = new listar_pedidos_por_cliente();

                Bundle args = new Bundle();
                args.putString("idClienteDashboard", idcliente);
                args.putString("nombreCliente", nombre);
                //  dialog.setStyle(DialogFragment.STYLE_NORMAL,R.style.Theme_AppCompat_DialogWhenLarge);
                dialog2.setArguments(args);

                dialog2.show(fm, "Hola");


            }
        });

        Button add_venta;
        add_venta = (Button) v.findViewById(R.id.btn_ventas);
        add_venta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrir para agregar cobro de cobro
                //  Intent ven=new Intent(v.getContext(),listar_pedidos_por_cliente.class);
                //ven.putExtra("idClienteDashboard", idcliente);

                // startActivity(ven);
                android.app.FragmentManager fm = getActivity().getFragmentManager();
                listar_ventas_por_cliente dialog2 = new listar_ventas_por_cliente();

                Bundle args = new Bundle();
                args.putString("idClienteDashboard", idcliente);
                args.putString("nombreCliente", nombre);
                //  dialog.setStyle(DialogFragment.STYLE_NORMAL,R.style.Theme_AppCompat_DialogWhenLarge);
                dialog2.setArguments(args);

                dialog2.show(fm, "Hola");


            }
        });

        Button add_reparacion;
        add_reparacion = (Button) v.findViewById(R.id.btn_reparaciones_c);
        add_reparacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrir para agregar cobro de cobro




                android.app.FragmentManager fm = getActivity().getFragmentManager();
                listar_reparaciones_por_cliente dialog2 = new listar_reparaciones_por_cliente();

                Bundle args = new Bundle();
                args.putString("idClienteDashboard", idcliente);
                args.putString("nombreCliente", nombre);
                //  dialog.setStyle(DialogFragment.STYLE_NORMAL,R.style.Theme_AppCompat_DialogWhenLarge);
                dialog2.setArguments(args);

                dialog2.show(fm, "Hola");

                //Intent ven=new Intent(v.getContext(),agregar_reparacion.class);
               // ven.putExtra("idClienteDashboard", idcliente);

               // startActivity(ven);


            }
        });

        Button add_cobro;
        add_cobro = (Button) v.findViewById(R.id.btn_cobros);
        add_cobro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrir para agregar cobro de cobro
              //  Intent ven=new Intent(getActivity().getApplicationContext(),agregar_cobro.class);

               // ven.putExtra("idClienteDashboard", idcliente);

                //startActivity(ven);
                android.app.FragmentManager fm = getActivity().getFragmentManager();
                listar_cobros_por_cliente dialog2 = new listar_cobros_por_cliente();

                Bundle args = new Bundle();
                args.putString("idClienteDashboard", idcliente);
                Toast.makeText(getActivity(),"cliente"+idcliente,Toast.LENGTH_LONG).show();

                args.putString("nombreCliente", nombre);
                //  dialog.setStyle(DialogFragment.STYLE_NORMAL,R.style.Theme_AppCompat_DialogWhenLarge);
                dialog2.setArguments(args);

                dialog2.show(fm, "Hola");





            }
        });

        Button close_dash;
        close_dash = (Button) v.findViewById(R.id.btn_close_dash);
        close_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //cerrar fragmento
                getActivity().getFragmentManager().beginTransaction().remove(cliente_dashboard.this).commit();

            }
        });
        menuVisible = (LinearLayout) v.findViewById(R.id.menuGone);
        ToggleButton toggleMenu;
        toggleMenu = (ToggleButton) v.findViewById(R.id.menuToggle);
        toggleMenu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //Your code when checked
                    menuVisible.setVisibility(View.VISIBLE);
                } else {
                    //Your code when unchecked
                    menuVisible.setVisibility(View.GONE);
                }
            }
        });





        llVentas = (LinearLayout) v.findViewById(R.id.VentasLinearLayaout);
        llPedidos = (LinearLayout) v.findViewById(R.id.PedidosLinearLayaout);
        llReparaciones = (LinearLayout) v.findViewById(R.id.ReparacionesLinearLayaout);
        llCobros = (LinearLayout) v.findViewById(R.id.CobrosLinearLayaout);
        //finaliza inflar
 /*

        * lista reparaciones*/


        tgVentas = (ToggleButton) v.findViewById(R.id.minVentasBtn);
        tgVentas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //Your code when checked
                    llVentas.setVisibility(View.GONE);
                } else {
                    //Your code when unchecked
                    llVentas.setVisibility(View.VISIBLE);
                }
            }
        });

        tgPedidos = (ToggleButton) v.findViewById(R.id.minPedidosBtn);
        tgPedidos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //Your code when checked
                    llPedidos.setVisibility(View.GONE);
                } else {
                    //Your code when unchecked
                    llPedidos.setVisibility(View.VISIBLE);
                }
            }
        });



        tgReparaciones = (ToggleButton) v.findViewById(R.id.minReparacionesBtn);
        tgReparaciones.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //Your code when checked
                    llReparaciones.setVisibility(View.GONE);
                } else {
                    //Your code when unchecked
                    llReparaciones.setVisibility(View.VISIBLE);
                }
            }
        });



        tgCobros = (ToggleButton) v.findViewById(R.id.minCobrosBtn);
        tgCobros.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //Your code when checked
                    llCobros.setVisibility(View.GONE);
                } else {
                    //Your code when unchecked
                    llCobros.setVisibility(View.VISIBLE);
                }
            }
        });



        // listas



        //em la siguiente linea indicamos el layout
        //a donde queremos que se muestre el listview

        listaVentas = (ListView) v.findViewById(R.id.listVentasPorClienteDashboard);

        String[] fromVenta = new String[]{manager.CN_CANTIDAD_VENTA, manager.CN_PRODUCTO_VENTA, manager.CN_COSTO_VENTA, manager.CN_TOTAL_VENTA};

        int[] toVenta = new int[]{R.id.CantidadVentaTxt, R.id.DescripcionVentaTxt,R.id.CostoVentaTxt, R.id.totalVentatxt};
        cursor4 = manager.cargarVentasPorClienteDashboard(idcliente.toString(),"Pendiente");
        adaptador4 = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.tableview_ventas_por_cliente_dashboarddesign, cursor4, fromVenta, toVenta, 0){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view4 = super.getView(position, convertView, parent);

                llVentas.setVisibility(View.VISIBLE);
                tgVentas.setVisibility(View.VISIBLE);
                return view4;
            }
        };



        listaPedidos = (ListView) v.findViewById(R.id.listPedidosPorClienteDashboard);

        String[] fromPedido = new String[]{manager.CN_CANTIDAD_PEDIDO, manager.CN_ID_PRODUCTO_PEDIDO, manager.CN_COSTO_PEDIDO, manager.CN_TOTAL_PEDIDO};

        int[] toPedido = new int[]{R.id.CantidadPedidoTxt, R.id.DescripcionPedidoTxt,R.id.CostoPedidoTxt, R.id.totalPedidotxt};
        cursor2 = manager.cargarPedidosPorClienteDashboard(idcliente.toString(),"Pendiente");
        adaptador2 = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.tableview_pedidos_por_cliente_dashboarddesign, cursor2, fromPedido, toPedido, 0){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view1 = super.getView(position, convertView, parent);

                llPedidos.setVisibility(View.VISIBLE);
                tgPedidos.setVisibility(View.VISIBLE);
                return view1;
            }
        };

        listaReparaciones = (ListView) v.findViewById(R.id.listReparacionesPorClienteDashboard);
        String[] from = new String[]{manager.CN_CANTIDAD_REPARACION, manager.CN_DETALLES_REPARACION, manager.CN_COSTO_REPARACION, manager.CN_TOTAL_REPARACION};

        int[] to = new int[]{R.id.CantidadReparacionTxt, R.id.DescripcionReparacionTxt,R.id.CostoReparacionTxt, R.id.totalReparaciontxt};

        cursor = manager.cargarReparacionesPorClienteDashboard(idcliente.toString(),"Pendiente");

        adaptador = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.tableview_reparaciones_por_cliente_dashboarddesign, cursor, from, to, 0){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view2 = super.getView(position, convertView, parent);

                llReparaciones.setVisibility(View.VISIBLE);
                tgReparaciones.setVisibility(View.VISIBLE);
                return view2;
            }
        };

        listaCobros = (ListView) v.findViewById(R.id.listCobrosPorClienteDashboard);
        String[] fromCobro = new String[]{manager.CN_COBRO_FECHA, manager.CN_COBRO_CANTIDAD};
        int[] toCobro = new int[]{R.id.FechaCobroTxt, R.id.AbonoCobroTxt};

        cursor3 = manager.cargarCobrosPorClienteDashboard(idcliente.toString());

        adaptador3 = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.tableview_cobros_por_cliente_dashboarddesign, cursor3, fromCobro, toCobro, 0){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view3 = super.getView(position, convertView, parent);
                llCobros.setVisibility(View.VISIBLE);
                tgCobros.setVisibility(View.VISIBLE);
                return view3;
            }
        };


        listaVentas.setAdapter(adaptador4);
        //llamamos a ala clase Utility
        // y asignamos el tamaño dependiendo el numero de items
        //  Utility.setListViewHeightBasedOnChildren(listaPedidos);
        ListUtils.setDynamicHeight(listaVentas);



        listaPedidos.setAdapter(adaptador2);
        //llamamos a ala clase Utility
        // y asignamos el tamaño dependiendo el numero de items
      //  Utility.setListViewHeightBasedOnChildren(listaPedidos);
        ListUtils.setDynamicHeight(listaPedidos);


        //por ultimo agregamos lo siguiente
        listaReparaciones.setAdapter(adaptador);
     //   Utility.setListViewHeightBasedOnChildren(listaReparaciones);
        ListUtils.setDynamicHeight(listaReparaciones);



        listaCobros.setAdapter(adaptador3);
     //   Utility.setListViewHeightBasedOnChildren(listaCobros);
        ListUtils.setDynamicHeight(listaCobros);
        /*finaliza*/

      /*




*/

        return v;
        }

    //generador de numeros para cofirmacion cuando
    //se da clic en eliminar o actualizar
    void generarNumeros(final String texto){
        generador = new Random();
        n1 = Math.abs(generador.nextInt() % 10);
        n2 = Math.abs(generador.nextInt() % 10);
        resultado = n1 + n2;




        final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

        builder.setMessage("¿Estás seguro que quieres "+texto+"?"+"\nLos cambios generados son irreversibles");
        builder.setTitle("¡Advertencia!");
        builder.setCancelable(false);
        builder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // metodo que se debe implementar


                        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
                        b.setTitle("Para continuar es necesario realizar la siguiente operacion");
                        b.setMessage("¿Cual es la suma de "+n1+"+"+n2+"?");

                        final EditText input = new EditText(getActivity());
                        b.setView(input);
                        b.setPositiveButton("Verificar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {

                                try {
                                    if (Integer.parseInt(input.getText().toString()) == resultado) {
                                        Snackbar.make(v, "La operación fue completada correctamente", Snackbar.LENGTH_LONG)
                                                .setActionTextColor(Color.rgb(46, 204, 113))
                                                .show();
                                        if(texto.toString() == "eliminar"){
                                                manager.eliminarCliente(idcliente);
                                            //cerrar fragmento
                                            getActivity().getFragmentManager().beginTransaction().remove(cliente_dashboard.this).commit();

                                        }else if(texto.toString() == "actualizar"){

                                            nombre_text = (EditText) v.findViewById(R.id.nombreCliente);
                                            direccion_txt = (EditText) v.findViewById(R.id.direccionCliente);
                                            telefono_txt = (EditText) v.findViewById(R.id.telefonoCliente);
                                            deuda_txt = (EditText) v.findViewById(R.id.deudaCliente);


                                            manager.actualizarCliente(idcliente,nombre_text.getText().toString(),
                                                    direccion_txt.getText().toString(),telefono_txt.getText().toString(),
                                                    deuda_txt.getText().toString());

                                        }

                                    } else {
                                        Snackbar.make(v, "Necesita verificar que realmente quiere "+texto, Snackbar.LENGTH_LONG)
                                                .setActionTextColor(Color.rgb(231, 76, 60))
                                                .show();

                                    }
                               }catch (Exception e){
                                    Snackbar.make(v, "Operacion Incorrecta", Snackbar.LENGTH_LONG)
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Cursor c = manager.cargarClientesId(idcliente);
        if (c.moveToFirst() == true) {
            String nombre_ = c.getString(1);
            String direccion_ = c.getString(2);
            String telefono_ = c.getString(3);
            String deuda_ = c.getString(4);

            nombre_text.setText(nombre_);
            direccion_txt.setText(direccion_);
            telefono_txt.setText(telefono_);
            deuda_txt.setText(deuda_);
        }
    }


}
