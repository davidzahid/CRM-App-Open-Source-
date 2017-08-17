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

package mx.zahid.developer.misclientes.database;

/**
 * Created by developer on 27/03/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import mx.zahid.developer.misclientes.MainActivity;
import mx.zahid.developer.misclientes.R;
import mx.zahid.developer.misclientes.crud.inventario.CreateNote;
import mx.zahid.developer.misclientes.login.loginUsers;

/**
 * ESTA CLASE SE ENCARGARA DEL ESQUEMA
 * Y DE LOS METODOS CRUD (CREAR, BORRAR, INSERTAR, ACTUALIZAR)
 *
 */
public class DataBaseManager {
    //cursos para el login
    private Cursor cursorLogin;
    private Cursor cursorLoginupdate;

    private Cursor cursorCliente;
    private Cursor cursorProducto;
    private Cursor cursorReparacion;
    private Cursor cursorPedido;
    private Cursor cursorVenta;
    /*NOMBRE DE LA TABLA*/
    public static final String TABLE_CLIENTES = "clientes_table";
    public static final String TABLE_PROVEEDORES = "proveedores_table";
    public static final String TABLE_COBROS = "cobros_table";
    public static final String TABLE_USUARIOS = "usuarios_table";
    //TABLAS POR COMPLETAR
    public static final String TABLE_PRODUCTOS = "productos_table";
    public static final String TABLE_PEDIDOS  = "pedidos_table";
    public static final String TABLE_REPARACIONES = "reparaciones_table";
    public static final String TABLE_VENTAS = "ventas_table";

    //public static final String TABLE_ROLES = "roles_table";



    /*COLUMNAS O DATOS QUE CONTENDRA LA TABLA ROLES*/
   // public static final String CN_ID_ROLES = "_id";
   // public static final String CN_ROL = "_rol";
   // public static final String CN_ESTADO_CLIENTE_ID = "_id_role_usuario";






    /*COLUMNAS O DATOS QUE CONTENDRA LA TABLA CLIENTES*/
    public static final String CN_ID = "_id";
    public static final String CN_NAME = "_nombres";
    public static final String CN_DIR = "_direccion";
    public static final String CN_TEL = "_telefono";
    public static final String CN_C_DEBE = "_deuda";


    /*COLUMNAS O DATOS QUE CONTENDRA LA TABLA PROVEEDORES*/
    public static final String CN_ID_PRO = "_id";
    public static final String CN_NAME_PRO = "_nombres";
    public static final String CN_DIR_PRO = "_direccion";
    public static final String CN_TEL_PRO = "_telefono";
    public static final String CN_C_DEBE_PRO = "_deuda";

    /*COLUMNAS O DATOS QUE CONTENDRA LA TABLA COBROS*/
    public static final String CN_ID_COBROS = "_id";
    public static final String CN_COBRO_CANTIDAD = "_cantidad_cobro";
    public static final String CN_COBRO_FECHA = "_fecha";
    public static final String CN_COBRO_DETALLES = "_concepto";
    public static final String CN_COBRO_CLIENTES_ID = "_id_cliente";

    /*COLUMNAS O DATOS QUE CONTENDRA LA TABLA USUARIOS*/
    public static final String CN_ID_USUARIOS = "_id";
    public static final String CN_USUARIOS_NAME = "_usuario_name";
    public static final String CN_USUARIOS_USER = "_usuario_user";
    public static final String CN_USUARIOS_PASSWORD = "_usuario_password";
    public static final String CN_USUARIOS_ROL = "_usuario_rol";


    /*COLUMNA O DATOS QUE CONTRANDEN LA TABLA DE PRODUCTOS*/
    public static final String CN_ID_PRODUCTOS = "_id";
    public static final String CN_FOTO_PRODUCTO = "_foto_producto";
    public static final String CN_NOMBRE_PRODUCTO = "_nombre_producto";
    public static final String CN_DESCRIPCION_PRODUCTO = "_descripcion_producto";

    /*COLUMNA O DATOS QUE CONTRANDEN LA TABLA DE PEDIDOS*/
    public static final String CN_ID_PEDIDO = "_id";
    public static final String CN_FECHA_PEDIDO = "_fecha_de_pedido";
    public static final String CN_FECHA_ENTREGA_PEDIDO  = "_fecha_de_entrega";
    public static final String CN_DETALLES_PEDIDO = "_detalle_pedido";
    public static final String CN_ESTADO_PEDIDO = "_estado_pedido";
    public static final String CN_COSTO_PEDIDO = "_costo_pedido";
    public static final String CN_CANTIDAD_PEDIDO = "_cantidad_pedido";
    public static final String CN_TOTAL_PEDIDO = "_total_pedido";
    public static final String CN_ID_PRODUCTO_PEDIDO = "pedido_id_producto_fk";
    public static final String CN_ID_CLIENTE_PEDIDO = "pedido_id_cliente_fk";


    /*COLUMNA O DATOS QUE CONTRANDEN LA TABLA DE REPARACIONES*/
    public static final String CN_ID_REPARACION = "_id";
    public static final String CN_FECHA_RECEPCION_REPARACION = "_fecha_de_recepcion";
    public static final String CN_FECHA_ENTREGA_REPARACION  = "_fecha_de_entrega_reparacion";
    public static final String CN_DETALLES_REPARACION = "_detalle_repacion";
    public static final String CN_ESTADO_REPARACION = "_estado_reparacion";
    public static final String CN_COSTO_REPARACION = "_costo_reparacion";
    public static final String CN_CANTIDAD_REPARACION = "_cantidad_reparacion";
    public static final String CN_TOTAL_REPARACION = "_total_reparacion";
    public static final String CN_ID_CLIENTE_REPARACION = "reparacion_id_cliente_fk";


    /*COLUMNA O DATOS QUE CONTRANDEN LA TABLA DE PEDIDOS*/
    public static final String CN_ID_VENTA = "_id";
    public static final String CN_FECHA_ENTREGA_VENTA = "_fecha_de_entrega_venta";
    public static final String CN_PRODUCTO_VENTA = "_producto_venta";
    public static final String CN_ESTADO_VENTA = "_estado_venta";
    public static final String CN_COSTO_VENTA = "_costo_venta";
    public static final String CN_CANTIDAD_VENTA = "_cantidad_venta";
    public static final String CN_TOTAL_VENTA = "_total_venta";
    public static final String CN_ID_PRODUCTO_VENTA = "venta_id_producto_fk";
    public static final String CN_ID_CLIENTE_VENTA = "venta_id_cliente_fk";




    /**
     * UNA VES HECHOS LOS CAMPOS REQUERIMOS LAS SENTENCIA PARA
     * HACER LA TABLA
     */
    //LA SIGUIENTE ES LA SENTENCIA SQL QUE SE UTILIZA
    public static final String CREATE_TABLE_CLIENTES = " CREATE TABLE " +TABLE_CLIENTES+ " ( "
            + CN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CN_NAME + " TEXT NOT NULL,"
            + CN_DIR + " TEXT NOT NULL,"
            + CN_TEL + " TEXT NOT NULL,"
            + CN_C_DEBE + " TEXT);";


    //LA SIGUIENTE ES LA SENTENCIA SQL QUE SE UTILIZA
    public static final String CREATE_TABLE_PROVEEDORES = " CREATE TABLE " +TABLE_PROVEEDORES+ " ( "
            + CN_ID_PRO + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CN_NAME_PRO + " TEXT NOT NULL,"
            + CN_DIR_PRO + " TEXT NOT NULL,"
            + CN_TEL_PRO + " TEXT NOT NULL,"
            + CN_C_DEBE_PRO + " TEXT);";

    public static final String CREATE_TABLE_COBROS = " CREATE TABLE " +TABLE_COBROS+ " ( "
            + CN_ID_COBROS + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CN_COBRO_CANTIDAD + " TEXT NOT NULL,"
            + CN_COBRO_FECHA + " DATETIME NOT NULL,"
            + CN_COBRO_DETALLES + " TEXT,"
            + CN_COBRO_CLIENTES_ID + " INTEGER NOT NULL);";

    public static final String CREATE_TABLE_USUARIOS = " CREATE TABLE " +TABLE_USUARIOS+ " ( "
            + CN_ID_USUARIOS + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CN_USUARIOS_NAME + " TEXT NOT NULL,"
            + CN_USUARIOS_USER + " TEXT NOT NULL,"
            + CN_USUARIOS_PASSWORD + " TEXT NOT NULL,"
            + CN_USUARIOS_ROL + " INTEGER NOT NULL);";

    public static final String CREATE_TABLE_PRODUCTOS = " CREATE TABLE " +TABLE_PRODUCTOS+ " ( "
            + CN_ID_PRODUCTOS + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        //    + CN_FOTO_PRODUCTO + " BLOB,"
            + CN_NOMBRE_PRODUCTO + " TEXT NOT NULL,"
            + CN_DESCRIPCION_PRODUCTO + " TEXT);";

    public static final String CREATE_TABLE_PEDIDOS = " CREATE TABLE " +TABLE_PEDIDOS+ " ( "
            + CN_ID_PEDIDO + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CN_FECHA_PEDIDO + " DATETIME,"
            + CN_FECHA_ENTREGA_PEDIDO + " DATETIME,"
            + CN_DETALLES_PEDIDO + "  TEXT,"
            + CN_ESTADO_PEDIDO + " TEXT NOT NULL,"
            + CN_COSTO_PEDIDO + " TEXT,"
            + CN_CANTIDAD_PEDIDO + " TEXT,"
            + CN_TOTAL_PEDIDO + " TEXT,"
            + CN_ID_PRODUCTO_PEDIDO + " TEXT NULL,"
            + CN_ID_CLIENTE_PEDIDO + " INT NULL);";

    public static final String CREATE_TABLE_REPARACIONES = " CREATE TABLE " +TABLE_REPARACIONES+ " ( "
            + CN_ID_REPARACION + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CN_FECHA_RECEPCION_REPARACION + " DATETIME,"
            + CN_FECHA_ENTREGA_REPARACION + " DATETIME,"
            + CN_DETALLES_REPARACION + "  TEXT,"
            + CN_ESTADO_REPARACION + " TEXT NOT NULL,"
            + CN_COSTO_REPARACION + " TEXT,"
            + CN_FOTO_PRODUCTO + " BLOB,"
            + CN_CANTIDAD_REPARACION + " TEXT,"
            + CN_TOTAL_REPARACION + " TEXT,"
            + CN_ID_CLIENTE_REPARACION + " INT NULL);";



    public static final String CREATE_TABLE_VENTAS = " CREATE TABLE " +TABLE_VENTAS+ " ( "
            + CN_ID_VENTA + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CN_FECHA_ENTREGA_VENTA + " DATETIME,"
            + CN_PRODUCTO_VENTA + "  TEXT,"
            + CN_ESTADO_VENTA + " TEXT NOT NULL,"
            + CN_COSTO_VENTA + " TEXT,"
            + CN_CANTIDAD_VENTA + " TEXT,"
            + CN_TOTAL_VENTA + " TEXT,"
            + CN_ID_PRODUCTO_VENTA + " TEXT NULL,"
            + CN_ID_CLIENTE_VENTA + " INT NULL);";



    //ahora que ya tenemos creada la tabla,  debemos ir a
    // nuestra clase DbHelper.java  que nos ayuda a crear la
    //base de datos en onCreate


    //generamos el constructor de databasemanager


    //hacemos nuestros objetos privados
    private DbHelper helper;
    private SQLiteDatabase db;
    public DataBaseManager(Context context) {
        /**
         * UNA VEZ CREADA LA BASE DE DATOS EN
         * DATABASEMANAGER.JAVA PROCEDEMOS A INSTANCIAR
         * */
        /*HAY QUE PASARLE EL CONTEXTO QUE EN ESTE CASO ES this*/
        helper = new DbHelper(context);

        /*CON ESTO TODABIA NO SE CREA LA BASE DE DATOS*/
        /*HASTA QUE DEFINIMOS LA BASE DE DATOS*/

        /*si la base de datos no existe
        * getwritabledatabse crea la base de datos
        * y la devuelve en modo escritura, si ya existe solamente
        * la devuelve*/
        db = helper.getWritableDatabase();
    }




    /**
     * Definimos lista de columnas de la tabla para utilizarla en las consultas a la base de datos
     */

    /********************************************
     *                  CLIENTES  CRUD          *
     *                                          *
     ********************************************/
    //CREACION DE METODOS DE ELIMINAR, ACTUALIZAR, INSERTAR
    //en esta funcion insertaremos datos
    /**
     *a la tabla cliente
     * */

    //metodo mas eficiente para insertar

    /**
     * METODO CLIENTE CREAR
     * */
    public ContentValues generarContentValues(String nombres, String direccion, String telefono, Float deuda){
        ContentValues valores = new ContentValues();
        valores.put(CN_NAME, nombres);
        valores.put(CN_DIR, direccion);
        valores.put(CN_TEL, telefono);
        valores.put(CN_C_DEBE, deuda);

        //retorno de los valores
        return valores;
    }

    public void insertarCliente(String nombres, String direccion, String telefono, Float deuda){
        db.insert(TABLE_CLIENTES, null, generarContentValues(nombres,direccion,telefono,deuda));

    };
    /**
     * METODO CLIENTE LEER
     * */
    //CARGA LOS CLIENTES EN UN LISTVIEW
    public Cursor cargarClientes()
    {
        String[] columnas = new String[]{ CN_ID, CN_NAME, CN_DIR, CN_TEL, CN_C_DEBE} ;
        return db.query(TABLE_CLIENTES, columnas, null, null, null, null,  CN_NAME+" ASC", null);

    }
    //BUSCAR LOS CLIENTES CON UN TEXTVIEW
    public Cursor buscarCliente(String nombre){
        String[] columnas = new String[]{ CN_ID, CN_NAME, CN_DIR, CN_TEL, CN_C_DEBE} ;

        //Seleccionaremos uno de los metodos de la query
        //aL AGREGAR "=?" donde es la CLAUSALA WHERE que buscara dentro de la base de datos

        //LIKE ? servira como filtro letra por letra de izquierda a derecha
        String []selectionArgs = {nombre+"%",nombre+"%"};

      return db.query(TABLE_CLIENTES, columnas, CN_NAME + " LIKE ? OR "+CN_DIR + " LIKE ?", selectionArgs, null, null, CN_NAME+" DESC");

       // return db.query(TABLE_CLIENTES, columnas, CN_NAME + " LIKE ? OR "+ CN_DIR + " LIKE ?", new String[] {nombre+"%",nombre+"%"}, null, null, null);



    }


    public Cursor cargarClientesId(String id)
    {
        String[] columnas = new String[]{ CN_ID, CN_NAME, CN_DIR, CN_TEL, CN_C_DEBE} ;
        return db.query(TABLE_CLIENTES, columnas, "_id="+Integer.parseInt(id), null, null, null, null, null);

    }




    public void consultarCliente(String id, View v, String where_come) {
        //esto funciona para consultar un cliente

       cursorCliente = db.rawQuery("SELECT _nombres, _direccion, _telefono, _deuda FROM "+TABLE_CLIENTES+" WHERE _id="+id+";",null);




       // Integer[] args = new Integer[]{id};
     //   db.execSQL("SELECT _nombres, _direccion, _telefono, _deuda FROM "+ TABLE_CLIENTES + " WHERE id=?", args);
        //detro del cursor ignora el primer valor
        //indica que se mueva a el primero
    if(where_come == "cliente_dashboard"){
      if(cursorCliente.moveToFirst()==true) {

            String nombres = cursorCliente.getString(0);
            String direccion = cursorCliente.getString(1);
            String telefono = cursorCliente.getString(2);
            String deuda = cursorCliente.getString(3);
          //  cargarCobrosbyCliente(id);
            EditText nombreTxt = (EditText) v.findViewById(R.id.nombreCliente);
            nombreTxt.setText(nombres);

            EditText direccionTxt = (EditText) v.findViewById(R.id.direccionCliente);
          direccionTxt.setText(direccion);

            EditText telefonoTxt = (EditText) v.findViewById(R.id.telefonoCliente);
          telefonoTxt.setText(telefono);

            EditText deudaTxt = (EditText) v.findViewById(R.id.deudaCliente);
          deudaTxt.setText(deuda);




        }
    }else if(where_come.toString() == "listar_pedido") {
        if (cursorCliente.moveToFirst() == true) {
            String nombres = cursorCliente.getString(0);
            TextView cliente_txt = (TextView) v.findViewById(R.id.nombreClientePedido);
            cliente_txt.setText(nombres);
        }

    }

    else if(where_come.toString() == "listar_reparacion") {
        if (cursorCliente.moveToFirst() == true) {
            String nombres = cursorCliente.getString(0);
            TextView cliente_txt = (TextView) v.findViewById(R.id.nombreClienteReparacionList);
            cliente_txt.setText(nombres);
        }

    }
    else if(where_come.toString() == "listar_pedido_por_cliente") {
        if (cursorCliente.moveToFirst() == true) {
            String nombres = cursorCliente.getString(0);
            TextView cliente_txt = (TextView) v.findViewById(R.id.nombreClientePedidoPorCliente);
            cliente_txt.setText(nombres);
        }

    }

    else if(where_come.toString() == "listar_reparacion_por_cliente") {
        if (cursorCliente.moveToFirst() == true) {
            String nombres = cursorCliente.getString(0);
            TextView cliente_txt = (TextView) v.findViewById(R.id.nombreClienteReparacionPorCliente);
            cliente_txt.setText(nombres);
        }

    }

    else if(where_come.toString() == "listar_ventas_por_cliente") {
        if (cursorCliente.moveToFirst() == true) {
            String nombres = cursorCliente.getString(0);
            TextView cliente_txt = (TextView) v.findViewById(R.id.nombreClienteVentaPorCliente);
            cliente_txt.setText(nombres);
        }

    }



    }
    /**
     * METODO CLIENTE ACTUALIZAR
     * */

    public void actualizarCliente(String id_cliente, String nombre, String direccion, String telefono, String deuda){

        db.execSQL("UPDATE "+TABLE_CLIENTES+
                " SET _nombres='"+nombre+
                "', _direccion='"+direccion+
                "', _telefono='"+telefono+
                "', _deuda='"+deuda+
                "' WHERE _id="+Integer.parseInt(id_cliente));

    }


    /**
     * METODO CLIENTE BORRAR
     * */

    public void eliminarCliente(String id_cliente){

        db.execSQL("DELETE FROM "+TABLE_CLIENTES+
                " WHERE _id="+Integer.parseInt(id_cliente));

        db.execSQL("DELETE FROM "+TABLE_REPARACIONES+
                " WHERE reparacion_id_cliente_fk="+Integer.parseInt(id_cliente));

        db.execSQL("DELETE FROM "+TABLE_PEDIDOS+
                " WHERE pedido_id_cliente_fk="+Integer.parseInt(id_cliente));

        db.execSQL("DELETE FROM "+TABLE_COBROS+
                " WHERE _id_cliente="+Integer.parseInt(id_cliente));

        db.execSQL("DELETE FROM "+TABLE_VENTAS+
                " WHERE venta_id_cliente_fk="+Integer.parseInt(id_cliente));


    }
    /********************************************
     *                  COBOROS  CRUD           *
     *                                          *
     ********************************************/



    /**
     * METODOS CREAR COBRO
     * */
    public ContentValues generarCobro(float cantidad, String fecha, String detalles, Integer id_cliente){
        ContentValues valores = new ContentValues();
        valores.put(CN_COBRO_CANTIDAD, cantidad);
        valores.put(CN_COBRO_FECHA, fecha);
        valores.put(CN_COBRO_DETALLES, detalles);
        valores.put(CN_COBRO_CLIENTES_ID, id_cliente);

        //retorno de los valores
        return valores;
    }

    public void insertarCobro(float cantidad, String fecha, String detalles, Integer id_cliente){

        //Log.i("El cobro insertado es", "insertarCobro: "+"Cantida: "+cantidad+"fecha: "+fecha+" detalles: "+detalles+" Id Cliente: "+id_cliente);
        db.insert(TABLE_COBROS, null, generarCobro(cantidad,fecha,detalles,id_cliente));
       /* db.execSQL("SELECT -(_deuda-"+cantidad+") FROM "+TABLE_CLIENTES+
                    " WHERE _id = "+id_cliente);*/
        db.execSQL("UPDATE "+TABLE_CLIENTES+
                " SET _deuda=_deuda-"+cantidad+" WHERE _id ="+id_cliente);;
    };
    /**
     * METODOS LEER COBRO
     * */
    public Cursor cargarCobrosbyCliente(String cn_cobro_cliente_id){
        String[] columnas = new String[]{ CN_ID_COBROS, CN_COBRO_CANTIDAD, CN_COBRO_FECHA, CN_COBRO_DETALLES, CN_COBRO_CLIENTES_ID} ;

        //Seleccionaremos uno de los metodos de la query
        //aL AGREGAR "=?" donde es la CLAUSALA WHERE que buscara dentro de la base de datos

        //LIKE ? servira como filtro letra por letra de izquierda a derecha
        String []selectionArgs = {cn_cobro_cliente_id+"%"};
        return db.query(TABLE_COBROS, columnas, CN_COBRO_CLIENTES_ID +"=?", selectionArgs, null, null, null);
    }
    public Cursor cargarCobros()
    {
        String[] columnas = new String[]{ CN_ID_COBROS, CN_COBRO_CANTIDAD, CN_COBRO_FECHA, CN_COBRO_DETALLES, CN_COBRO_CLIENTES_ID} ;
        return db.query(TABLE_COBROS, columnas, null, null, null, null, null, null);
    /*COLUMNAS O DATOS QUE CONTENDRA LA TABLA COBROS*/

    }


    public void eliminarCobro(String id_cobro){

        db.delete(TABLE_COBROS,CN_ID_COBROS+" ="+id_cobro,null);



    }
    /**
     * METODO COBRO ACTUALIZAR
     * */

    /**
     * METODO COBRO BORRAR
     * */


    /********************************************
     *                PRODUCTOS CRUD            *
     *                                          *
     ********************************************/
    /**
     * METODOS CREAR PRODUCTO
     * */

        /*COLUMNA O DATOS QUE CONTRANDEN LA TABLA DE PRODUCTOS*/

    public ContentValues productosValues(String nombre_producto, String descripcion_producto){
        ContentValues valores = new ContentValues();
      //  valores.put(CN_NAME, foto_producto);
        valores.put(CN_DIR, nombre_producto);
        valores.put(CN_TEL, descripcion_producto);
        //retorno de los valores
        return valores;
    }

    public void insertarProducto(String nombre_producto, String descripcion_producto){

        db.insert(TABLE_PRODUCTOS, null, productosValues(nombre_producto,descripcion_producto));

    };

    /**
     * METODOS LEER PRODUCTO
     * */
    //CARGA LOS PRODUCTOS EN UN LISTVIEW
        public Cursor cargarProductos()
        {
        String[] columnas = new String[]{ CN_ID_PRODUCTOS, CN_NOMBRE_PRODUCTO, CN_DESCRIPCION_PRODUCTO} ;
        return db.query(TABLE_PRODUCTOS, columnas, null, null, null, null, null, null);
        }
    /**
     * METODOS ACTUALIZAR PRODUCTO
     * */

    public void consultarProducto(String id, View v) {
        //esto funciona para consultar un cliente

        cursorProducto = db.rawQuery("SELECT _nombre_producto, _descripcion_producto FROM "+TABLE_PRODUCTOS+" WHERE _id="+id+";",null);
    /*COLUMNA O DATOS QUE CONTRANDEN LA TABLA DE PRODUCTOS*/

            if (cursorProducto.moveToFirst() == true) {
                String nombreProducto = cursorProducto.getString(0);
                TextView producto_txt = (TextView) v.findViewById(R.id.nombreProducto);
                producto_txt.setText(nombreProducto);
            }

    }

    /**
     * METODOS ELIMINAR PRODUCTO
     * */



    /********************************************
     *              PEDIDOS    CRUD             *
     *                                          *
     ********************************************/
    /**
     * METODOS CREAR PEDIDOS
     * */
    public ContentValues generarPedido(String fechaPedido, String fechaEntrega, String detallePedido, String estadoPedido, float costoPedido, String productoPedidoId, Integer id_cliente, Integer cantidad, Float total ){
        ContentValues valores = new ContentValues();
        valores.put(CN_FECHA_PEDIDO, fechaPedido);
        valores.put(CN_FECHA_ENTREGA_PEDIDO, fechaEntrega);
        valores.put(CN_DETALLES_PEDIDO, detallePedido);
        valores.put(CN_ESTADO_PEDIDO, estadoPedido);
        valores.put(CN_COSTO_PEDIDO, costoPedido);
        valores.put(CN_ID_PRODUCTO_PEDIDO, productoPedidoId);
        valores.put(CN_ID_CLIENTE_PEDIDO, id_cliente);
        valores.put(CN_CANTIDAD_PEDIDO, cantidad);
        valores.put(CN_TOTAL_PEDIDO, total);
        //retorno de los valores
        return valores;
    }

    public void insertarPedido(String fechaPedido, String fechaEntrega, String detallePedido, String estadoPedido, float costoPedido, String productoPedidoId, Integer id_cliente, Integer cantidad, Float total){

           db.insert(TABLE_PEDIDOS, null, generarPedido(fechaPedido,fechaEntrega,detallePedido,estadoPedido,costoPedido,productoPedidoId,id_cliente,cantidad,total));
           db.execSQL("UPDATE "+TABLE_CLIENTES+
                " SET _deuda=_deuda+"+total+" WHERE _id ="+id_cliente);

    }

    public void actualizarEstadoVenta(String id_venta,String estado){
        db.execSQL("UPDATE " + TABLE_VENTAS +
                " SET _estado_venta='" + estado +
                "' WHERE _id=" + Integer.parseInt(id_venta));
    }


    public ContentValues generarVenta(String fechaVenta, String productoVenta, String estadoVenta, float costoVenta ,Integer id_cliente, float totalventa, Integer cantidadventa){
        ContentValues valores = new ContentValues();
        valores.put(CN_FECHA_ENTREGA_VENTA, fechaVenta);
        valores.put(CN_PRODUCTO_VENTA, productoVenta);
        valores.put(CN_ESTADO_VENTA, estadoVenta);
        valores.put(CN_COSTO_VENTA, costoVenta);
        valores.put(CN_ID_CLIENTE_VENTA, id_cliente);
        valores.put(CN_CANTIDAD_VENTA, cantidadventa);
        valores.put(CN_TOTAL_VENTA, totalventa);
        //retorno de los valores
        return valores;
    }

    public void insertarVenta(String fechaVenta, String productoVenta, String estadoVenta, float costoVenta ,Integer id_cliente, float totalventa, Integer cantidadventa){

        db.insert(TABLE_VENTAS, null, generarVenta(fechaVenta,productoVenta,estadoVenta,costoVenta,id_cliente,totalventa,cantidadventa));
        db.execSQL("UPDATE "+TABLE_CLIENTES+
                " SET _deuda=_deuda+"+totalventa+" WHERE _id ="+id_cliente);

    }



    public Cursor cargarVentasPorCliente(String id,String estado)
    {
        String[] columnas = new String[]{ CN_ID_VENTA, CN_FECHA_ENTREGA_VENTA, CN_PRODUCTO_VENTA, CN_ID_CLIENTE_VENTA, CN_ESTADO_VENTA,CN_COSTO_VENTA, CN_CANTIDAD_VENTA, CN_TOTAL_VENTA} ;
        //en ésta buscabamos de acuerdo al estado con un tipo where
        // return db.query(TABLE_PEDIDOS, columnas, "_estado_pedido='Entregado'", null, null, null, null, null);

        //  String []selectionArgs = {id+"%"};

        //    return db.query(TABLE_PEDIDOS, columnas, CN_ID_CLIENTE_PEDIDO + " LIKE ?", selectionArgs, null, null, CN_FECHA_ENTREGA_PEDIDO+" DESC");
        String selectionArgs = id;


        return db.query(TABLE_VENTAS, columnas, CN_ID_CLIENTE_VENTA + " = ? AND "+CN_ESTADO_VENTA + " = ?", new String[] {selectionArgs,estado}, null, null, CN_FECHA_ENTREGA_VENTA+" ASC");




    }
    public Cursor cargarVentasPorClienteDashboard(String id,String estado)
    {
        String[] columnas = new String[]{ CN_ID_VENTA, CN_FECHA_ENTREGA_VENTA, CN_PRODUCTO_VENTA, CN_ID_CLIENTE_VENTA, CN_ESTADO_VENTA,CN_COSTO_VENTA, CN_CANTIDAD_VENTA, CN_TOTAL_VENTA} ;
        //en ésta buscabamos de acuerdo al estado con un tipo where
        // return db.query(TABLE_PEDIDOS, columnas, "_estado_pedido='Entregado'", null, null, null, null, null);

        //  String []selectionArgs = {id+"%"};

        //    return db.query(TABLE_PEDIDOS, columnas, CN_ID_CLIENTE_PEDIDO + " LIKE ?", selectionArgs, null, null, CN_FECHA_ENTREGA_PEDIDO+" DESC");
        String selectionArgs = id;

        return db.query(TABLE_VENTAS, columnas, CN_ID_CLIENTE_VENTA + " = ? AND "+CN_ESTADO_VENTA + " = ?", new String[] {selectionArgs,estado}, null, null, CN_FECHA_ENTREGA_VENTA+" ASC");


    }

    public void eliminarVenta(String id_venta){


            db.delete(TABLE_VENTAS,CN_ID_VENTA+" ="+id_venta,null);



    }
    public Cursor buscarVentaporCliente(String estadoVenta, String idcliente){
        String[] columnas = new String[]{CN_ID_VENTA, CN_FECHA_ENTREGA_VENTA, CN_PRODUCTO_VENTA, CN_ID_CLIENTE_VENTA, CN_ESTADO_VENTA,CN_COSTO_VENTA, CN_CANTIDAD_VENTA, CN_TOTAL_VENTA} ;


        //  return db.query(TABLE_PEDIDOS, columnas, CN_ESTADO_PEDIDO + " LIKE ?", selectionArgs, null, null, CN_FECHA_ENTREGA_PEDIDO+" DESC");
        return db.query(TABLE_VENTAS, columnas, CN_ID_CLIENTE_VENTA + " = ? AND "+CN_ESTADO_VENTA + " = ?", new String[] {idcliente,estadoVenta}, null, null, CN_FECHA_ENTREGA_VENTA+" ASC");

    }

    public void cargarCostoPedidoaCliente(String id_cliente, float costoPedido){
        db.execSQL("UPDATE "+TABLE_CLIENTES+
                " SET _deuda=_deuda+"+costoPedido+" WHERE _id ="+id_cliente);
    }
    public void descargarCostoPedidoaCliente(String id_cliente, float costoPedido){
        db.execSQL("UPDATE "+TABLE_CLIENTES+
                " SET _deuda=_deuda-"+costoPedido+" WHERE _id ="+id_cliente);
    }

    /**
     * METODOS LEER PEDIDOS
     * */
        //CARGA LOS PEDIDOS EN UN LISTVIEW
        public Cursor cargarPedidos(String estado)
        {
        String[] columnas = new String[]{ CN_ID_PEDIDO, CN_FECHA_PEDIDO, CN_FECHA_ENTREGA_PEDIDO, CN_ID_PRODUCTO_PEDIDO, CN_ID_CLIENTE_PEDIDO, CN_ESTADO_PEDIDO,CN_COSTO_PEDIDO, CN_CANTIDAD_PEDIDO, CN_TOTAL_PEDIDO} ;
        //en ésta buscabamos de acuerdo al estado con un tipo where
       // return db.query(TABLE_PEDIDOS, columnas, "_estado_pedido='Entregado'", null, null, null, null, null);

            String []selectionArgs = {estado+"%"};
        return db.query(TABLE_PEDIDOS, columnas, CN_ESTADO_PEDIDO+ " LIKE ?", selectionArgs, null, null, CN_FECHA_ENTREGA_PEDIDO+" ASC", null);



        }

        public Cursor cargarPedidosPorCliente(String id,String estado)
        {
        String[] columnas = new String[]{ CN_ID_PEDIDO, CN_FECHA_PEDIDO, CN_FECHA_ENTREGA_PEDIDO, CN_ID_PRODUCTO_PEDIDO, CN_ID_CLIENTE_PEDIDO, CN_ESTADO_PEDIDO,CN_COSTO_PEDIDO, CN_CANTIDAD_PEDIDO, CN_TOTAL_PEDIDO} ;
        //en ésta buscabamos de acuerdo al estado con un tipo where
        // return db.query(TABLE_PEDIDOS, columnas, "_estado_pedido='Entregado'", null, null, null, null, null);

          //  String []selectionArgs = {id+"%"};

        //    return db.query(TABLE_PEDIDOS, columnas, CN_ID_CLIENTE_PEDIDO + " LIKE ?", selectionArgs, null, null, CN_FECHA_ENTREGA_PEDIDO+" DESC");
            String selectionArgs = id;


            return db.query(TABLE_PEDIDOS, columnas, CN_ID_CLIENTE_PEDIDO + " = ? AND "+CN_ESTADO_PEDIDO + " = ?", new String[] {selectionArgs,estado}, null, null, CN_FECHA_ENTREGA_PEDIDO+" ASC");




        }
    public Cursor cargarPedidosPorClienteDashboard(String id,String estado)
    {
        String[] columnas = new String[]{ CN_ID_PEDIDO, CN_CANTIDAD_PEDIDO, CN_ID_PRODUCTO_PEDIDO, CN_COSTO_PEDIDO, CN_FECHA_ENTREGA_PEDIDO, CN_ID_CLIENTE_PEDIDO, CN_ESTADO_PEDIDO, CN_TOTAL_PEDIDO, CN_CANTIDAD_PEDIDO} ;
        //en ésta buscabamos de acuerdo al estado con un tipo where
        // return db.query(TABLE_PEDIDOS, columnas, "_estado_pedido='Entregado'", null, null, null, null, null);

        //  String []selectionArgs = {id+"%"};

        //    return db.query(TABLE_PEDIDOS, columnas, CN_ID_CLIENTE_PEDIDO + " LIKE ?", selectionArgs, null, null, CN_FECHA_ENTREGA_PEDIDO+" DESC");
        String selectionArgs = id;

        return db.query(TABLE_PEDIDOS, columnas, CN_ID_CLIENTE_PEDIDO + " = ? AND "+CN_ESTADO_PEDIDO + " = ?", new String[] {selectionArgs,estado}, null, null, CN_FECHA_ENTREGA_PEDIDO+" ASC");


    }


    public Cursor cargarCobrosPorCliente(String id)
    {

        String[] columnas = new String[]{ CN_ID_COBROS, CN_COBRO_FECHA, CN_COBRO_DETALLES, CN_COBRO_CANTIDAD, CN_COBRO_CLIENTES_ID} ;
        //en ésta buscabamos de acuerdo al estado con un tipo where
        // return db.query(TABLE_PEDIDOS, columnas, "_estado_pedido='Entregado'", null, null, null, null, null);

        //  String []selectionArgs = {id+"%"};

        //    return db.query(TABLE_PEDIDOS, columnas, CN_ID_CLIENTE_PEDIDO + " LIKE ?", selectionArgs, null, null, CN_FECHA_ENTREGA_PEDIDO+" DESC");
        String selectionArgs = id;


        return db.query(TABLE_COBROS, columnas, CN_COBRO_CLIENTES_ID + " = ?", new String[] {selectionArgs}, null, null, CN_COBRO_FECHA+" DESC");


    }
    public Cursor cargarCobrosPorClienteDashboard(String id)
    {

        String[] columnas = new String[]{ CN_ID_COBROS, CN_COBRO_FECHA, CN_COBRO_CANTIDAD, CN_COBRO_CLIENTES_ID} ;
        //en ésta buscabamos de acuerdo al estado con un tipo where
        // return db.query(TABLE_PEDIDOS, columnas, "_estado_pedido='Entregado'", null, null, null, null, null);

        //  String []selectionArgs = {id+"%"};

        //    return db.query(TABLE_PEDIDOS, columnas, CN_ID_CLIENTE_PEDIDO + " LIKE ?", selectionArgs, null, null, CN_FECHA_ENTREGA_PEDIDO+" DESC");
        String selectionArgs = id;


        return db.query(TABLE_COBROS, columnas, CN_COBRO_CLIENTES_ID + " = ?", new String[] {selectionArgs}, null, null, CN_COBRO_FECHA+" DESC");


    }
    public Cursor cargarReparacionesPorCliente(String id,String estado)
    {
        String[] columnas = new String[]{ CN_ID_REPARACION, CN_FECHA_RECEPCION_REPARACION, CN_FECHA_ENTREGA_REPARACION, CN_DETALLES_REPARACION, CN_ID_CLIENTE_REPARACION, CN_ESTADO_REPARACION, CN_TOTAL_REPARACION, CN_CANTIDAD_REPARACION } ;

        //en ésta buscabamos de acuerdo al estado con un tipo where
        // return db.query(TABLE_PEDIDOS, columnas, "_estado_pedido='Entregado'", null, null, null, null, null);

        //  String []selectionArgs = {id+"%"};

        //    return db.query(TABLE_PEDIDOS, columnas, CN_ID_CLIENTE_PEDIDO + " LIKE ?", selectionArgs, null, null, CN_FECHA_ENTREGA_PEDIDO+" DESC");
        String selectionArgs = id;

        return db.query(TABLE_REPARACIONES, columnas, CN_ID_CLIENTE_REPARACION + " = ? AND "+CN_ESTADO_REPARACION + " = ?", new String[] {selectionArgs,estado}, null, null, CN_FECHA_ENTREGA_REPARACION+" ASC");

    }


    public Cursor cargarReparacionesPorClienteDashboard(String id,String estado)
    {
        String[] columnas = new String[]{ CN_ID_REPARACION, CN_CANTIDAD_REPARACION, CN_DETALLES_REPARACION, CN_COSTO_REPARACION, CN_ESTADO_REPARACION, CN_FECHA_ENTREGA_REPARACION, CN_TOTAL_REPARACION, CN_CANTIDAD_REPARACION} ;

        //en ésta buscabamos de acuerdo al estado con un tipo where
        // return db.query(TABLE_PEDIDOS, columnas, "_estado_pedido='Entregado'", null, null, null, null, null);

        //  String []selectionArgs = {id+"%"};

        //    return db.query(TABLE_PEDIDOS, columnas, CN_ID_CLIENTE_PEDIDO + " LIKE ?", selectionArgs, null, null, CN_FECHA_ENTREGA_PEDIDO+" DESC");
        String selectionArgs = id;


        return db.query(TABLE_REPARACIONES, columnas, CN_ID_CLIENTE_REPARACION + " = ? AND "+CN_ESTADO_REPARACION + " = ?", new String[] {selectionArgs,estado}, null, null, CN_FECHA_ENTREGA_REPARACION+" ASC");

    }
    //BUSCAR LOS PEDIDOS
    public Cursor buscarPedido(String estadoPedido){
        String[] columnas = new String[]{ CN_ID_PEDIDO, CN_FECHA_PEDIDO, CN_FECHA_ENTREGA_PEDIDO, CN_ID_PRODUCTO_PEDIDO, CN_ID_CLIENTE_PEDIDO, CN_ESTADO_PEDIDO, CN_TOTAL_PEDIDO, CN_CANTIDAD_PEDIDO, CN_COSTO_PEDIDO} ;

        //Seleccionaremos uno de los metodos de la query
        //aL AGREGAR "=?" donde es la CLAUSALA WHERE que buscara dentro de la base de datos

        //LIKE ? servira como filtro letra por letra de izquierda a derecha
        String []selectionArgs = {estadoPedido+"%"};

        return db.query(TABLE_PEDIDOS, columnas, CN_ESTADO_PEDIDO + " LIKE ?", selectionArgs, null, null, CN_FECHA_ENTREGA_PEDIDO+" ASC");
    }
    public Cursor buscarPedidoporCliente(String estadoPedido, String idcliente){
        String[] columnas = new String[]{ CN_ID_PEDIDO, CN_FECHA_PEDIDO, CN_FECHA_ENTREGA_PEDIDO, CN_ID_PRODUCTO_PEDIDO, CN_ID_CLIENTE_PEDIDO, CN_ESTADO_PEDIDO,CN_TOTAL_PEDIDO, CN_CANTIDAD_PEDIDO, CN_COSTO_PEDIDO} ;



      //  return db.query(TABLE_PEDIDOS, columnas, CN_ESTADO_PEDIDO + " LIKE ?", selectionArgs, null, null, CN_FECHA_ENTREGA_PEDIDO+" DESC");
        return db.query(TABLE_PEDIDOS, columnas, CN_ID_CLIENTE_PEDIDO + " = ? AND "+CN_ESTADO_PEDIDO + " = ?", new String[] {idcliente,estadoPedido}, null, null, CN_FECHA_ENTREGA_PEDIDO+" ASC");

    }

    /**
     * METODOS ACTUALIZAR PEDIDOS
     * */

    public void actualizarEstadoPedido(String id_pedido,String estado){
            db.execSQL("UPDATE " + TABLE_PEDIDOS +
                    " SET _estado_pedido='" + estado +
                    "' WHERE _id=" + Integer.parseInt(id_pedido));
    }


    public void actualizarEstadoPedidoPorCliente(String id_pedido,String estado,String idcliente){
        db.execSQL("UPDATE " + TABLE_PEDIDOS +
                " SET _estado_pedido='" + estado +
                "' WHERE _id=" + Integer.parseInt(id_pedido) + " AND pedido_id_cliente_fk="+idcliente);
    }

    /**
     * METODOS ELIMINAR PEDIDOS
     * */
    public void eliminarPedido(String id_pedido){

        cursorPedido = db.rawQuery("SELECT _total_pedido, pedido_id_cliente_fk, _estado_pedido FROM "+TABLE_PEDIDOS+" WHERE _id="+id_pedido+";",null);
    /*COLUMNA O DATOS QUE CONTRANDEN LA TABLA DE PRODUCTOS*/

        if (cursorPedido.moveToFirst() == true) {
            String totalpedido = cursorPedido.getString(0);
            String clienteid = cursorPedido.getString(1);
            String estatus = cursorPedido.getString(2);

            db.delete(TABLE_PEDIDOS,CN_ID_PEDIDO+" ="+id_pedido,null);
            if(estatus.contains("Pendiente")) {
                db.execSQL("UPDATE "+TABLE_CLIENTES+
                        " SET _deuda=_deuda-"+totalpedido+" WHERE _id ="+clienteid);
            }
        }



    }
    /********************************************
     *              REPARACIONES CRUD           *
     *                                          *
     ********************************************/
    /**
     * METODOS CREAR REPARACIONES
     * */

    public ContentValues generarReparacion(String fechaRecepcionReparacion, String fechaEntregaReparacion, String detalleReparacion, String estadoReparacion, float costoReparacion, Integer id_cliente, byte[] imagen, Integer cantidad, float total){
        ContentValues valores = new ContentValues();
        valores.put(CN_FECHA_RECEPCION_REPARACION, fechaRecepcionReparacion);
        valores.put(CN_FECHA_ENTREGA_REPARACION, fechaEntregaReparacion);
        valores.put(CN_DETALLES_REPARACION, detalleReparacion);
        valores.put(CN_ESTADO_REPARACION, estadoReparacion);
        valores.put(CN_COSTO_REPARACION, costoReparacion);
        valores.put(CN_ID_CLIENTE_REPARACION, id_cliente);
        valores.put(CN_FOTO_PRODUCTO, imagen);
        valores.put(CN_CANTIDAD_REPARACION, cantidad);
        valores.put(CN_TOTAL_REPARACION, total);

        //retorno de los valores
        return valores;
    }

    public void insertarReparacion(String fechaRecepcionReparacion, String fechaEntregaReparacion, String detalleReparacion, String estadoReparacion, float costoReparacion, Integer id_cliente, byte[] imagen, Integer cantidad, float total){

        db.insert(TABLE_REPARACIONES, null, generarReparacion(fechaRecepcionReparacion,fechaEntregaReparacion,detalleReparacion,estadoReparacion,costoReparacion,id_cliente, imagen, cantidad, total));
       /* db.execSQL("SELECT -(_deuda-"+cantidad+") FROM "+TABLE_CLIENTES+
                    " WHERE _id = "+id_cliente);*/
        db.execSQL("UPDATE "+TABLE_CLIENTES+
                " SET _deuda=_deuda+"+total+" WHERE _id ="+id_cliente);
    }



    public ContentValues generarReparacionTest(String fechaRecepcionReparacion, String fechaEntregaReparacion, String detalleReparacion, String estadoReparacion, float costoReparacion, Integer id_cliente, String Cantidad){
        ContentValues valores = new ContentValues();



        valores.put(CN_FECHA_RECEPCION_REPARACION, fechaRecepcionReparacion);
        valores.put(CN_FECHA_ENTREGA_REPARACION, fechaEntregaReparacion);
        valores.put(CN_DETALLES_REPARACION, detalleReparacion);
        valores.put(CN_ESTADO_REPARACION, estadoReparacion);
        valores.put(CN_COSTO_REPARACION, costoReparacion);
        valores.put(CN_ID_CLIENTE_REPARACION, id_cliente);
        valores.put(CN_CANTIDAD_REPARACION, Cantidad);

        //retorno de los valores
        return valores;
    }
    public void insertarReparacionTest(String fechaRecepcionReparacion, String fechaEntregaReparacion, String detalleReparacion, String estadoReparacion, float costoReparacion, Integer id_cliente, String Cantidad){

        db.insert(TABLE_REPARACIONES, null, generarReparacionTest(fechaRecepcionReparacion,fechaEntregaReparacion,detalleReparacion,estadoReparacion,costoReparacion,id_cliente,Cantidad));
       /* db.execSQL("SELECT -(_deuda-"+cantidad+") FROM "+TABLE_CLIENTES+
                    " WHERE _id = "+id_cliente);*/
        db.execSQL("UPDATE "+TABLE_CLIENTES+
                " SET _deuda=_deuda+"+costoReparacion+" WHERE _id ="+id_cliente);
    }

    /**
     * METODOS LEER REPARACIONES
     * */



    //CARGA LOS REPARACIONES EN UN LISTVIEW
    public Cursor cargarReparaciones(String estado)
    {
        String[] columnas = new String[]{ CN_ID_REPARACION, CN_FECHA_RECEPCION_REPARACION, CN_FECHA_ENTREGA_REPARACION, CN_DETALLES_REPARACION, CN_ID_CLIENTE_REPARACION, CN_ESTADO_REPARACION, CN_TOTAL_REPARACION, CN_CANTIDAD_REPARACION} ;

        //en ésta buscabamos de acuerdo al estado con un tipo where
        // return db.query(TABLE_PEDIDOS, columnas, "_estado_pedido='Entregado'", null, null, null, null, null);
        String []selectionArgs = {estado+"%"};
        return db.query(TABLE_REPARACIONES, columnas, CN_ESTADO_REPARACION+ " LIKE ?", selectionArgs, null, null, null, null);


    }


    //BUSCAR LOS PEDIDOS
    public Cursor buscarReparacion(String estadoReparacion){
        String[] columnas = new String[]{ CN_ID_REPARACION, CN_FECHA_RECEPCION_REPARACION, CN_FECHA_ENTREGA_REPARACION, CN_DETALLES_REPARACION, CN_ID_CLIENTE_REPARACION, CN_ESTADO_REPARACION, CN_TOTAL_REPARACION, CN_CANTIDAD_REPARACION} ;
        //Seleccionaremos uno de los metodos de la query
        //aL AGREGAR "=?" donde es la CLAUSALA WHERE que buscara dentro de la base de datos
        //LIKE ? servira como filtro letra por letra de izquierda a derecha
        String []selectionArgs = {estadoReparacion+"%"};
        return db.query(TABLE_REPARACIONES, columnas, CN_ESTADO_REPARACION + " LIKE ?", selectionArgs, null, null, CN_FECHA_ENTREGA_REPARACION+" ASC");

    }

    public Cursor buscarReparacionporCliente(String estadoPedido, String idcliente){
        String[] columnas = new String[]{ CN_ID_REPARACION, CN_FECHA_RECEPCION_REPARACION, CN_FECHA_ENTREGA_REPARACION, CN_DETALLES_REPARACION, CN_ID_CLIENTE_REPARACION, CN_ESTADO_REPARACION, CN_TOTAL_REPARACION, CN_CANTIDAD_REPARACION } ;



        //  return db.query(TABLE_PEDIDOS, columnas, CN_ESTADO_PEDIDO + " LIKE ?", selectionArgs, null, null, CN_FECHA_ENTREGA_PEDIDO+" DESC");
        return db.query(TABLE_REPARACIONES, columnas, CN_ID_CLIENTE_REPARACION + " = ? AND "+CN_ESTADO_REPARACION + " = ?", new String[] {idcliente,estadoPedido}, null, null, CN_FECHA_ENTREGA_REPARACION+" ASC");

    }

    /**
     * METODOS ACTUALIZAR REPARACIONES
     * */

    public void actualizarEstadoReparacion(String id_reparacion,String estado){
        db.execSQL("UPDATE " + TABLE_REPARACIONES +
                " SET _estado_reparacion='" + estado +
                "' WHERE _id=" + Integer.parseInt(id_reparacion));
    }

    /**
     * METODOS ELIMINAR REPARACIONES
     * */
    public void eliminarReparacion(String id_reparacion){


        cursorReparacion = db.rawQuery("SELECT _total_reparacion, reparacion_id_cliente_fk, _estado_reparacion FROM "+TABLE_REPARACIONES+" WHERE _id="+id_reparacion+";",null);
    /*COLUMNA O DATOS QUE CONTRANDEN LA TABLA DE PRODUCTOS*/

        if (cursorReparacion.moveToFirst() == true) {
            String totalreparacion = cursorReparacion.getString(0);
            String clienteid = cursorReparacion.getString(1);
            String estatus = cursorReparacion.getString(2);

            db.delete(TABLE_REPARACIONES,CN_ID_REPARACION+" ="+id_reparacion,null);
            if(estatus.contains("Pendiente")) {
            db.execSQL("UPDATE "+TABLE_CLIENTES+
                    " SET _deuda=_deuda-"+totalreparacion+" WHERE _id ="+clienteid);
            }
        }


    }
    /********************************************
     *                USUARIOS CRUD            *
     *                                          *
     ********************************************/
    /**
     * METODOS CREAR USUARIOS
     * */


    /**
     * Devuelve cursor con todos las columnas de la tabla
     */
    public ContentValues generarUsuario(String nombreU,String usuario, String password, Integer uRol){
        ContentValues valores = new ContentValues();

        valores.put(CN_USUARIOS_NAME, nombreU);
        valores.put(CN_USUARIOS_USER, usuario);
        valores.put(CN_USUARIOS_PASSWORD, password);
        valores.put(CN_USUARIOS_ROL, uRol);
        //retorno de los valores
        return valores;
    }
    //INSERTAR USUARIO
    public void insertarUsuario(String nombreU,String usuario, String password, Integer uRol){
        db.insert(TABLE_USUARIOS, null, generarUsuario(nombreU,usuario,password,uRol));

    };



    /**
     * METODOS LEER USUARIOS
     * */
    //CARGA LOS USUARIOS EN UN LISTVIEW
    public Cursor cargarUsuarios(){
        String[] columnas = new String[]{CN_ID_USUARIOS, CN_USUARIOS_NAME, CN_USUARIOS_USER, CN_USUARIOS_PASSWORD, CN_USUARIOS_ROL};
        return db.query(TABLE_USUARIOS, columnas, null, null, null, null, null, null);
    }

    public void consultarUsuario(String usuario, String password, Context context){
        //esto funciona para el login
        cursorLogin = db.rawQuery("SELECT _usuario_user, _usuario_password, _usuario_name, _usuario_rol, _id FROM "+TABLE_USUARIOS+" WHERE _usuario_user='"+usuario+"' AND _usuario_password='"+password+"';",null);
        if(cursorLogin.moveToFirst()==true){
            //capturamos los valores del cursos y lo almacenamos en variable
            String usua=cursorLogin.getString(0);
            String pass=cursorLogin.getString(1);
            String name=cursorLogin.getString(2);
            String rol=cursorLogin.getString(3);
            String id=cursorLogin.getString(4);
            //preguntamos si los datos ingresados son iguales
            if (usuario.equals(usua)&&password.equals(pass)){
              /*  Toast toast1 =
                        Toast.makeText(context,
                                "USUARIO CORRECTO \n TU NOMBRE ES: "+name, Toast.LENGTH_LONG);
                toast1.show();*/
                //cambiar nombre de usuario del drawer
                //con esto y con el siguiente parametro dentro del
                //string %1$s podemos asignar un nombre al header
                //  String text = String.format(context.getApplicationContext().getResources().getString(R.string.usuario), name);
                //si son iguales entonces vamos a otra ventana
                if(rol.contains("0")){
                    //ABRE LA ACTIVIDAD PRINCIPAL DE LA APLICACION
                    Intent ven=new Intent(context.getApplicationContext(),MainActivity.class);
                    ven.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //pasa datos a la actividad que se abrira
                    //en este caso a mainac
                    ven.putExtra("nombre", ""+name);
                    ven.putExtra("id", id);
                    context.startActivity(ven);
                }else{ Toast toast2 =
                        Toast.makeText(context,
                                "LO SIENTO NO TIENES ACCESO A ESTO", Toast.LENGTH_LONG);

                    toast2.show();}

            }
        }
        else{
            Toast toast1 =
                    Toast.makeText(context,
                            "SIN ACCESO", Toast.LENGTH_LONG);

            toast1.show();

        }
    }


    public void actualizarUsuario(String usuario, String passwordOld, String passwordNew, View v) {

        cursorLoginupdate = db.rawQuery("SELECT _usuario_user, _usuario_password, _usuario_name, _usuario_rol FROM " + TABLE_USUARIOS + " WHERE _id='" + Integer.parseInt(usuario) + "';", null);
        if (cursorLoginupdate.moveToFirst() == true) {
            String pass = cursorLoginupdate.getString(1);



                if (passwordOld.equals(pass)) {
                    db.execSQL("UPDATE " + TABLE_USUARIOS +
                            " SET _usuario_password='" + passwordNew +
                            "' WHERE _id=" + Integer.parseInt(usuario));
                    Snackbar.make(v, "La contraeña ha sido cambiada correctamente", Snackbar.LENGTH_LONG)
                            .show();
                    Intent ven=new Intent(v.getContext(),loginUsers.class);
                    ven.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //pasa datos a la actividad que se abrira
                    //en este caso a mainac

                    v.getContext().startActivity(ven);

                } else {
                    Snackbar.make(v, "Verifica que la contraeña actual es la correcta e intenta nuevamente", Snackbar.LENGTH_LONG)
                            .show();
                }

        }
    }

    /**
     * METODOS ACTUALIZAR USUARIOS
     * */

    /**
     * METODOS ELIMINAR USUARIOS
     * */

}
