package com.raquel500c.mysurveymap.miProviderBD;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Contract Class entre el provider y las aplicaciones
 */
public class ContratoProvider {
    /**
     * Autoridad del Content Provider
     */
    private final static String AUTHORITY = "com.raquel500c.mysurveymap.miProviderBD.MyContentProvider";
    /**
     * Representación de la tabla a consultar
     */
    public static final String LUGAR = "lugar";
    /**
     * Tipo MIME que retorna la consulta de una sola fila
     */
    public final static String SINGLE_MIME =
            "vnd.android.cursor.item/vnd." + AUTHORITY + LUGAR;
    /**
     * Tipo MIME que retorna la consulta de {@link #CONTENT_URI}
     */
    public final static String MULTIPLE_MIME =
            "vnd.android.cursor.dir/vnd." + AUTHORITY + LUGAR;
    /**
     * URI de contenido principal
     */
    public final static Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/" + LUGAR);
    /**
     * Comparador de URIs de contenido
     */
    public static final UriMatcher uriMatcher;
    /**
     * Código para URIs de multiples registros
     */
    public static final int ALLROWS = 1;
    /**
     * Código para URIS de un solo registro
     */
    public static final int SINGLE_ROW = 2;


    // Asignación de URIs
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, LUGAR, ALLROWS);
        uriMatcher.addURI(AUTHORITY, LUGAR + "/#", SINGLE_ROW);
    }

    /**
     * Estructura de la tabla
     */
    public static class Columnas implements BaseColumns {

        private Columnas() {
            // Sin instancias
        }

        /**
         * Categoría Lugar
         */
        public final static String CATEGORIA = "categoria";
        /**
         * Descripción Lugar
         */
        public final static String DESCRIPCION = "descripcion";
        /**
         * Nombre lugar
         */
        public final static String NOMBRE = "nombre";
        /**
         * Valoracion
         */
        public final static String VALORACION = "valoracion";

    }
}
