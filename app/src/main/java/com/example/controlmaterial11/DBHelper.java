package com.example.controlmaterial11;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    //CREACION DE LA BASE DE DATOS
    private static final String DATABASE_NAME = "Reportes_material.db";
    private static final int DATABASE_VERSION = 3;

    //CREACION DE LAS TABLAS A USAR EN LA BASE DE DATOS
    private static final String TABLE_LOGIN = "Login";
    private static final String TABLE_REPORTES = "Reportes";
    private static final String TABLE_DEPARTAMENTOS = "Departamentos";
    private static final String TABLE_TIPOSUELO = "Tipo_suelo";


    //TABLA DE LOGIN
    public static final String COLUMN_ID_USUARIO = "Id_Usuario";
    private static final String COLUMN_USERNAME = "Usuario";
    private static final String COLUMN_PASSWORD = "Contraseña";

    //TABLA DE REPORTES
    public static final String COLUMN_ID_TICKET = "Id_ticket";
    public static final String COLUMN_DEPARTAMENTO = "Departamento";
    public static final String COLUMN_FECHA_ASIGNACION = "Fecha_asignacion";
    public static final String COLUMN_FECHA_REPARACION = "Fecha_reparacion";
    public static final String COLUMN_COLONIA = "Colonia";
    public static final String COLUMN_TIPO_SUELO = "Tipo_suelo";
    public static final String COLUMN_DIRECCION = "Direccion";
    public static final String COLUMN_REPORTANTE = "Reportante";
    public static final String COLUMN_TELEFONO_REPORTANTE = "Telefono_reportante";
    public static final String COLUMN_REPARADOR = "Reparador";
    public static final String COLUMN_MATERIAL = "Material";
    public static final String COLUMN_IMAGEN_ANTES = "Imagen_antes";
    public static final String COLUMN_IMAGEN_DESPUES = "Imagen_despues";

    //TABLA DE DEPARTAMENTOS
    public static final  String COLUMN_ID_DEPARTAMENTO = "Id_Departamento";
    public  static final String COLUMN_NOMBRE_DEPARTAMENTO = "Nombre_Departamento";

    //TABLA DE TIPO DE SUELO, creacion de la tabla de tipo de suelo para cargar el spinner
    public static final String COLUMN_ID_TIPO_SUELO = "Id_Tipo_Suelo";
    public static final String COLUMN_TIPO_De_SUELO = "Tipo_Suelo";


    

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");

        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
                + COLUMN_ID_USUARIO + " INTEGER PRIMARY KEY, "
                + COLUMN_USERNAME + " TEXT, "
                + COLUMN_PASSWORD + " TEXT)";
        db.execSQL(CREATE_LOGIN_TABLE);

        String CREATE_REPORTES_TABLE = "CREATE TABLE " + TABLE_REPORTES + "("
                + COLUMN_ID_TICKET + " INTEGER PRIMARY KEY, "
                + COLUMN_ID_USUARIO + " INTEGER NOT NULL, "
                + COLUMN_DEPARTAMENTO + " TEXT NOT NULL, "
                + COLUMN_FECHA_ASIGNACION + " DATE NOT NULL, "
                + COLUMN_FECHA_REPARACION + " DATE NOT NULL, "
                + COLUMN_COLONIA + " TEXT NOT NULL, "
                + COLUMN_TIPO_SUELO + " TEXT NOT NULL, "
                + COLUMN_DIRECCION + " TEXT NOT NULL, "
                + COLUMN_REPORTANTE + " TEXT NOT NULL, "
                + COLUMN_TELEFONO_REPORTANTE + " TEXT NOT NULL, "
                + COLUMN_REPARADOR + " TEXT NOT NULL, "
                + COLUMN_MATERIAL + " TEXT NOT NULL, "
                + COLUMN_IMAGEN_ANTES + " BLOB, "
                + COLUMN_IMAGEN_DESPUES + " BLOB, "
                + "FOREIGN KEY (" + COLUMN_ID_USUARIO + ") REFERENCES " + TABLE_LOGIN + "(" + COLUMN_ID_USUARIO + ")"
                + " ON DELETE CASCADE ON UPDATE CASCADE)";
        db.execSQL(CREATE_REPORTES_TABLE);

        String CREATE_DEPARTAMENTOS_TABLE = "CREATE TABLE " + TABLE_DEPARTAMENTOS + " ("
                + COLUMN_ID_DEPARTAMENTO + " INTEGER PRIMARY KEY, "
                + COLUMN_NOMBRE_DEPARTAMENTO + " TEXT NOT NULL)";

        db.execSQL(CREATE_DEPARTAMENTOS_TABLE);

        String insertDepartamentos = "INSERT INTO " + TABLE_DEPARTAMENTOS + " (" + COLUMN_ID_DEPARTAMENTO + ", " + COLUMN_NOMBRE_DEPARTAMENTO + ") " +
                "VALUES (1 , 'CORTES'), " +
                "(2 , 'FUGAS'), " +
                "(3, 'RECONEXIONES'), " +
                "(4, 'PADRON DE USUARIOS')";
        db.execSQL(insertDepartamentos);

        String insertTipo_Suelo = "INSERT INTO " + TABLE_TIPOSUELO + " (" + COLUMN_ID_TIPO_SUELO + ", " + COLUMN_TIPO_De_SUELO + ") " +
                "VALUES (1, 'TERRACERIA'), " +
                "(2, 'ASFALTO'), " +
                "(3, 'CONCRETO')";
        db.execSQL(insertTipo_Suelo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_REPORTES + " ADD COLUMN " + COLUMN_DEPARTAMENTO + " TEXT NOT NULL");
        }

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPORTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEPARTAMENTOS);
        db.execSQL("DROP TABLE IF EXISTS "  + TABLE_TIPOSUELO);
        onCreate(db);
    }


    private byte[] reducirImagen(Uri imageUri, Context context) throws IOException {
        // Obtener el tamaño original de la imagen
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // No carga la imagen en memoria
        BitmapFactory.decodeStream(context.getContentResolver().openInputStream(imageUri), null, options);

        // Calcular el tamaño necesario para redimensionar
        final int REQUIRED_WIDTH = 800;
        final int REQUIRED_HEIGHT = 800;
        int inSampleSize = 1;

        // Determinar si es necesario redimensionar la imagen
        if (options.outHeight > REQUIRED_HEIGHT || options.outWidth > REQUIRED_WIDTH) {
            final int halfHeight = options.outHeight / 2;
            final int halfWidth = options.outWidth / 2;

            // Calcular el tamaño de muestra
            while ((halfHeight / inSampleSize) >= REQUIRED_HEIGHT && (halfWidth / inSampleSize) >= REQUIRED_WIDTH) {
                inSampleSize *= 2;
            }
        }

        // Cargar la imagen con el tamaño calculado
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false; // Ahora carga la imagen
        Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(imageUri), null, options);

        // Comprimir la imagen y asegurarte de que su tamaño no supere el límite
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int calidadCompresion = 75; // Puedes ajustar la calidad según tus necesidades

        // Realizar la compresión
        bitmap.compress(Bitmap.CompressFormat.JPEG, calidadCompresion, outputStream);

        // Comprobar el tamaño del byte array resultante y reducir la calidad si es necesario
        byte[] imagenReducida = outputStream.toByteArray();

        while (imagenReducida.length > 1024 * 1024) { // Establece un límite de 1MB
            outputStream.reset(); // Reiniciar el OutputStream
            calidadCompresion -= 5; // Reducir la calidad
            if (calidadCompresion < 0) {
                break; // Si la calidad es menor que 0, romper el ciclo
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, calidadCompresion, outputStream);
            imagenReducida = outputStream.toByteArray(); // Obtener el nuevo byte array
        }

        bitmap.recycle(); // Liberar recursos
        outputStream.close(); // Cerrar el OutputStream

        return imagenReducida;
    }

    public boolean insertarReporte(int id_usuario, String fecha_asignacion, String fecha_reparacion, String colonia,
                                   String tipo_suelo, String direccion, String reportante, String telefono_reportante,
                                   String reparador, String material, Uri imagenAntesUri, Uri imagenDespuesUri, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_USUARIO, id_usuario);
        values.put(COLUMN_FECHA_ASIGNACION, fecha_asignacion);
        values.put(COLUMN_FECHA_REPARACION, fecha_reparacion);
        values.put(COLUMN_COLONIA, colonia);
        values.put(COLUMN_TIPO_SUELO, tipo_suelo);
        values.put(COLUMN_DIRECCION, direccion);
        values.put(COLUMN_REPORTANTE, reportante);
        values.put(COLUMN_TELEFONO_REPORTANTE, telefono_reportante);
        values.put(COLUMN_REPARADOR, reparador);
        values.put(COLUMN_MATERIAL, material);

        try {
            if (imagenAntesUri != null) {
                byte[] imagenAntes = reducirImagen(imagenAntesUri, context);
                values.put(COLUMN_IMAGEN_ANTES, imagenAntes);
            }
            if (imagenDespuesUri != null) {
                byte[] imagenDespues = reducirImagen(imagenDespuesUri, context);
                values.put(COLUMN_IMAGEN_DESPUES, imagenDespues);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        long result = db.insert(TABLE_REPORTES, null, values);
        // db.close(); // Cerrar la base de datos después de insertar <--- (comentado)
        return result != -1;
    }

    public boolean insertarUsuario(String username, String password, String id_usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_USUARIO, id_usuario);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_LOGIN, null, values);
        // db.close(); // Cerrar la base de datos después de usarla <--- (comentado)
        return result != -1;
    }

    public Cursor buscarReporte(int Id_ticket) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT Id_ticket, Departamento,Fecha_asignacion, Fecha_reparacion, Colonia, Tipo_suelo, Direccion, Reportante, " +
                    "Telefono_reportante, Reparador, Material, Imagen_antes, Imagen_despues " +
                    "FROM " + TABLE_REPORTES + " WHERE Id_ticket = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(Id_ticket)});
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cursor; // Retorna el cursor sin cerrarlo
    }

    public boolean eliminarReporte(int id_ticket) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = db.delete(TABLE_REPORTES, COLUMN_ID_TICKET + "=?", new String[]{String.valueOf(id_ticket)}) > 0;
        // db.close(); // Cerrar la base de datos después de usarla <--- (comentado)
        return result;
    }

    public boolean verificarUsuario(String usuario, String clave) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean existeUsuario = false;
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM " + TABLE_LOGIN + " WHERE " + COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
            cursor = db.rawQuery(query, new String[]{usuario, clave});
            existeUsuario = cursor.getCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close(); // Cerrar cursor <---
            }
            // db.close(); // Cerrar la base de datos después de usarla <--- (comentado)
        }

        return existeUsuario;
    }

    public List<Reporte> obtenerTodosLosReportes() {
        List<Reporte> reportes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT Id_ticket, Colonia, Direccion FROM " + TABLE_REPORTES;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                try {
                    // Usa getColumnIndexOrThrow para manejar mejor los errores
                    String idTicket = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID_TICKET));
                    String colonia = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COLONIA));
                    String direccion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIRECCION));

                    Reporte reporte = new Reporte(idTicket, colonia, direccion);
                    reportes.add(reporte);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();  // Esto te permitirá ver si alguna columna no existe
                }
            } while (cursor.moveToNext());
        }
        cursor.close(); // Cerrar cursor <---
        // db.close(); // Cerrar la base de datos <--- (comentado)

        return reportes;
    }

    public List<Reporte_sincronizar> obtenerTodosLosReportesParaSincronizar() {
        List<Reporte_sincronizar> reportes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT Id_ticket, Departamento, Fecha_asignacion, Fecha_reparacion, Colonia, Tipo_suelo, Direccion, " +
                "Reportante, Telefono_reportante, Reparador, Material, Imagen_antes, Imagen_despues " +
                "FROM " + TABLE_REPORTES;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                try {
                    String idTicket = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID_TICKET));
                    String Departamento = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEPARTAMENTO));
                    String fechaAsignacion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FECHA_ASIGNACION));
                    String fechaReparacion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FECHA_REPARACION));
                    String colonia = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COLONIA));
                    String tipoSuelo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIPO_SUELO));
                    String direccion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIRECCION));
                    String reportante = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REPORTANTE));
                    String telefonoReportante = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TELEFONO_REPORTANTE));
                    String reparador = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REPARADOR));
                    String material = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MATERIAL));

                    byte[] imagenAntes = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGEN_ANTES));
                    byte[] imagenDespues = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGEN_DESPUES));

                    // Cambiamos a Reporte_sincronizar
                    Reporte_sincronizar reporte = new Reporte_sincronizar(
                            idTicket, Departamento,fechaAsignacion, fechaReparacion, colonia, tipoSuelo,
                            direccion, reportante, telefonoReportante, reparador,
                            material, imagenAntes, imagenDespues
                    );
                    reportes.add(reporte);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();  // Esto te permitirá ver si alguna columna no existe
                }
            } while (cursor.moveToNext());
        }
        cursor.close(); // Cerrar cursor

        return reportes;
    }

    public int obtenerIdUsuario(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        int idUsuario = -1; // Valor predeterminado en caso de que no se encuentre el usuario
        Cursor cursor = null;

        try {
            String query = "SELECT " + COLUMN_ID_USUARIO + " FROM " + TABLE_LOGIN + " WHERE " + COLUMN_USERNAME + " = ?";
            cursor = db.rawQuery(query, new String[]{username});
            if (cursor.moveToFirst()) {
                // Usa getColumnIndexOrThrow para manejar mejor el error
                int columnIndex = cursor.getColumnIndexOrThrow(COLUMN_ID_USUARIO);
                idUsuario = cursor.getInt(columnIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close(); // Cerrar cursor
            }
            // db.close(); // Cerrar la base de datos si es necesario
        }
        return idUsuario;
    }

    public Cursor obtenerDatosParaSincronizar() {
        SQLiteDatabase db = this.getReadableDatabase();
        // Obtener todos los registros de la tabla reportes
        return db.rawQuery("SELECT * FROM " + TABLE_REPORTES, null);
    }

    // Método para borrar todos los reportes de SQLite
    public void borrarTodosLosReportes() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("reportes", null, null);
        db.close();
    }


    public boolean actualizarReporte(int id_ticket,String Departamento,String colonia, String direccion, String reportante,
                                     String telefonoReportante, String tipoSuelo, String reparador,
                                     String material,String fechaAsignacion, String fechaReparacion, byte[] imagenAntes, byte[] imagenDespues) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Departamento", Departamento);
        values.put("Colonia", colonia);
        values.put("Direccion", direccion);
        values.put("Reportante", reportante);
        values.put("Telefono_reportante", telefonoReportante);
        values.put("Tipo_suelo", tipoSuelo);
        values.put("Reparador", reparador);
        values.put("Material", material);
        values.put("Fecha_asignacion", fechaAsignacion);
        values.put("Fecha_reparacion", fechaReparacion);
        values.put("Imagen_antes", imagenAntes);
        values.put("Imagen_despues", imagenDespues);

        // Actualizar la fila con el ID del ticket
        int rowsAffected = db.update("reportes", values, "Id_ticket = ?", new String[]{String.valueOf(id_ticket)});
        db.close(); //cerrar curor <---
        return rowsAffected > 0;  // Retorna true si se actualizaron filas
    }

    public void insertarDepartamentos(SQLiteDatabase db) {
        db.beginTransaction();  // Inicia la transacción

        try {
            // Consulta SQL para insertar los departamentos
            String sql = "INSERT INTO Departamentos (Id_Departamento, Nombre_Departamento) VALUES (?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);

            // Insertar el primer departamento
            statement.clearBindings();
            statement.bindLong(1, 5221);  // Id_Departamento
            statement.bindString(2, "REZAGO");  // Nombre_Departamento
            statement.execute();

            // Insertar el segundo departamento
            statement.clearBindings();
            statement.bindLong(1, 5241);
                
            statement.bindString(2, "PADRON DE USUARIOS");
            statement.execute();

            // Insertar el tercer departamento
            statement.clearBindings();
            statement.bindLong(1, 5401);
            statement.bindString(2, "SANEAMIENTO");
            statement.execute();

            // Confirmar la transacción
            db.setTransactionSuccessful();

            System.out.println("Departamentos insertados exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();  // Manejo de errores
        } finally {
            db.endTransaction();  // Termina la transacción, sea exitosa o no
        }
    }

    public void insertarTipoSuelo(SQLiteDatabase db){
        try{
            String sql = "INSERT INTO Tipo_suelo (Id_Tipo_Suelo, Tipo_Suelo) VALUES (?,?)";
            SQLiteStatement statement = db.compileStatement(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<String> getDepartamentos() {
        List<String> departamentos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_NOMBRE_DEPARTAMENTO + " FROM " + TABLE_DEPARTAMENTOS;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String nombreDepartamento = cursor.getString(0);
                departamentos.add(nombreDepartamento);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return departamentos;
    }

    public List<String> getTipo_suelo() {
        List<String> tipo_suelo = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_TIPO_De_SUELO + " FROM " + TABLE_TIPOSUELO;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String tipoSuelo = cursor.getString(0);
                tipo_suelo.add(tipoSuelo);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return tipo_suelo;
    }


}

