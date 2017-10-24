package com.raquel500c.mysurveymap.miProviderBD;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Clase envoltura para el gestor de Bases de datos
 */
class GestorBD extends SQLiteOpenHelper {


    public GestorBD(Context context,
                    String nam,
                    SQLiteDatabase.CursorFactory factory,
                    int vers) {
        super(context, nam, factory, vers);
    }


    public void onCreate(SQLiteDatabase database) {
        crearTabla(database); // Crear la tabla "lugar"
        cargaDatosPrueba(database); // Cargar 5 registros de prueba
    }

    /**
     * Crear tabla en la base de datos
     *
     * @param database Instancia de la base de datos
     */
    private void crearTabla(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContratoProvider.LUGAR + " (" +
                ContratoProvider.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContratoProvider.Columnas.CATEGORIA + " TEXT, " +
                ContratoProvider.Columnas.VALORACION + " TEXT, " +
                ContratoProvider.Columnas.NOMBRE + " TEXT, " +
                ContratoProvider.Columnas.DESCRIPCION + " TEXT);";
        database.execSQL(cmd);
    }

    /**
     * Carga datos de ejemplo en la tabla
     * @param database Instancia de la base de datos
     */
    private void cargaDatosPrueba(SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(ContratoProvider.Columnas.CATEGORIA, "Restaurante");
        values.put(ContratoProvider.Columnas.VALORACION, "Mala");
        values.put(ContratoProvider.Columnas.NOMBRE, "Mesón Juan");
        values.put(ContratoProvider.Columnas.DESCRIPCION, "Comida grasienta");
        database.insert(ContratoProvider.LUGAR, null, values);

        values = new ContentValues();
        values.put(ContratoProvider.Columnas.CATEGORIA, "Copas");
        values.put(ContratoProvider.Columnas.VALORACION, "Buena");
        values.put(ContratoProvider.Columnas.NOMBRE, "Terraza Círculo Bellas Artes");
        values.put(ContratoProvider.Columnas.DESCRIPCION, "Tranquilo, terraza");
        database.insert(ContratoProvider.LUGAR, null, values);

        values = new ContentValues();
        values.put(ContratoProvider.Columnas.CATEGORIA, "Educación");
        values.put(ContratoProvider.Columnas.VALORACION, "Buena");
        values.put(ContratoProvider.Columnas.NOMBRE, "IES Barajas");
        values.put(ContratoProvider.Columnas.DESCRIPCION, "Módulos FP");
        database.insert(ContratoProvider.LUGAR, null, values);

        values = new ContentValues();
        values.put(ContratoProvider.Columnas.CATEGORIA, "Naturaleza");
        values.put(ContratoProvider.Columnas.VALORACION, "Buena");
        values.put(ContratoProvider.Columnas.NOMBRE, "Parque del Capricho");
        values.put(ContratoProvider.Columnas.DESCRIPCION, "Parque bla bla");
        database.insert(ContratoProvider.LUGAR, null, values);

        values = new ContentValues();
        values.put(ContratoProvider.Columnas.CATEGORIA, "Restaurante");
        values.put(ContratoProvider.Columnas.VALORACION, "Regular");
        values.put(ContratoProvider.Columnas.NOMBRE, "Casa Manolo");
        values.put(ContratoProvider.Columnas.DESCRIPCION, "Económico, de batalla");
        database.insert(ContratoProvider.LUGAR, null, values);
    }

    @Override // Actualizaciones versión
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) throws SQLiteException {
        db.execSQL("DROP TABLE IF EXISTS " + ContratoProvider.LUGAR);
        onCreate(db);
    }
}
