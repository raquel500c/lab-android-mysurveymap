package com.raquel500c.mysurveymap.miProviderBD;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Content Provider personalizado para las actividades
 */
public class MyContentProvider extends android.content.ContentProvider {
    /**
     * Nombre de la base de datos
     */
    private static final String DATABASE_NAME = "lugares.db";
    /**
     * Versión actual de la base de datos
     */
    private static final int DATABASE_VERSION = 1;
    /**
     * Instancia del administrador de BD
     */
    private GestorBD databaseHelper;

    @Override
    public boolean onCreate() {
        // Inicializando gestor BD
        databaseHelper = new GestorBD(
                getContext(),
                DATABASE_NAME,
                null,
                DATABASE_VERSION
        );

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {


        // Obtener base de datos
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        // Comparar Uri
        int match = ContratoProvider.uriMatcher.match(uri);

        Cursor c;

        switch (match) {
            case ContratoProvider.ALLROWS:
                // Consultando todos los registros
                c = db.query(ContratoProvider.LUGAR, projection,
                        selection, selectionArgs,
                        null, null, sortOrder);
                c.setNotificationUri(
                        getContext().getContentResolver(),
                        ContratoProvider.CONTENT_URI);
                break;
            case ContratoProvider.SINGLE_ROW:
                // Consultando un solo registro basado en el Id del Uri
                long videoID = ContentUris.parseId(uri);
                c = db.query(ContratoProvider.LUGAR, projection,
                        ContratoProvider.Columnas._ID + " = " + videoID,
                        selectionArgs, null, null, sortOrder);
                c.setNotificationUri(
                        getContext().getContentResolver(),
                        ContratoProvider.CONTENT_URI);
                break;
            default:
                throw new IllegalArgumentException("URI no soportada: " + uri);
        }
        return c;

    }

    @Override
    public String getType(Uri uri) {
        switch (ContratoProvider.uriMatcher.match(uri)) {
            case ContratoProvider.ALLROWS:
                return ContratoProvider.MULTIPLE_MIME;
            case ContratoProvider.SINGLE_ROW:
                return ContratoProvider.SINGLE_MIME;
            default:
                throw new IllegalArgumentException("Tipo de Lugar desconocido: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // Validar la uri
        if (ContratoProvider.uriMatcher.match(uri) != ContratoProvider.ALLROWS) {
            throw new IllegalArgumentException("URI desconocida : " + uri);
        }
        ContentValues contentValues;
        if (values != null) {
            contentValues = new ContentValues(values);
        } else {
            contentValues = new ContentValues();
        }

        // Si es necesario, verifica los valores

        // Inserción de nueva fila
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long rowId = db.insert(ContratoProvider.LUGAR,
                null, contentValues);
        if (rowId > 0) {
            Uri uri_actividad =
                    ContentUris.withAppendedId(
                            ContratoProvider.CONTENT_URI, rowId);
            getContext().getContentResolver().
                    notifyChange(uri_actividad, null);
            return uri_actividad;
        }
        throw new SQLException("Falla al insertar fila en : " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        int match = ContratoProvider.uriMatcher.match(uri);
        int affected;

        switch (match) {
            case ContratoProvider.ALLROWS:
                affected = db.delete(ContratoProvider.LUGAR,
                        selection,
                        selectionArgs);
                break;
            case ContratoProvider.SINGLE_ROW:
                long videoId = ContentUris.parseId(uri);
                affected = db.delete(ContratoProvider.LUGAR,
                        ContratoProvider.Columnas._ID + "=" + videoId
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                // Notificar cambio asociado a la uri
                getContext().getContentResolver().
                        notifyChange(uri, null);
                break;
            default:
                throw new IllegalArgumentException("Elemento actividad desconocido: " +
                        uri);
        }
        return affected;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int affected;
        switch (ContratoProvider.uriMatcher.match(uri)) {
            case ContratoProvider.ALLROWS:
                affected = db.update(ContratoProvider.LUGAR, values,
                        selection, selectionArgs);
                break;
            case ContratoProvider.SINGLE_ROW:
                String videoId = uri.getPathSegments().get(1);
                affected = db.update(ContratoProvider.LUGAR, values,
                        ContratoProvider.Columnas._ID + "=" + videoId
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("URI desconocida: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return affected;
    }

}

