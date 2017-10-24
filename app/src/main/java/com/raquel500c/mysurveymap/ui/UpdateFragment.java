package com.raquel500c.mysurveymap.ui;


import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.raquel500c.mysurveymap.R;
import com.raquel500c.mysurveymap.miProviderBD.ContratoProvider;

/**
 * Fragmento con un formulario de actualización
 */
public class UpdateFragment extends Fragment {
    /**
     * Identificador de la actividad
     */
    private long id;
    /**
     * Views del layout
     */
    private EditText descripcion;
    private EditText nombre;
    private Spinner valoracion;
    private Spinner categoria;

    public UpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_insert, container, false);

        // Obtener views
        descripcion = (EditText) view.findViewById(R.id.descripcion_input);
        nombre = (EditText) view.findViewById(R.id.nombre_input);
        valoracion = (Spinner) view.findViewById(R.id.valoracion_spinner);
        categoria = (Spinner) view.findViewById(R.id.categoria_spinner);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        id = getActivity().getIntent().getLongExtra(ContratoProvider.Columnas._ID, -1);
        updateView(); // Cargar datos iniciales
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                updateData(); // Actualizar
                getActivity().finish();
                return true;
            case R.id.action_discard:
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Actualizar datos del lugar
     */
    private void updateData() {

        // Unir Uri principal con identificador
        Uri uri = ContentUris.withAppendedId(ContratoProvider.CONTENT_URI, id);

        ContentValues values = new ContentValues();
        values.put(ContratoProvider.Columnas.DESCRIPCION, descripcion.getText().toString());
        values.put(ContratoProvider.Columnas.NOMBRE, nombre.getText().toString());
        values.put(ContratoProvider.Columnas.VALORACION, valoracion.getSelectedItem().toString());
        values.put(ContratoProvider.Columnas.CATEGORIA, categoria.getSelectedItem().toString());

        // Actualiza datos del Content Provider
        getActivity().getContentResolver().update(
                uri,
                values,
                null,
                null
        );
    }

    /**
     * Carga los datos que provienen desde el detalle
     */
    private void updateView() {
        // Obtener datos del formulario
        Intent i = getActivity().getIntent();
        String descripcion_text = i.getStringExtra(ContratoProvider.Columnas.DESCRIPCION);
        String nombre_text = i.getStringExtra(ContratoProvider.Columnas.NOMBRE);
        String valoracion_text = i.getStringExtra(ContratoProvider.Columnas.VALORACION);
        String categoria_text = i.getStringExtra(ContratoProvider.Columnas.CATEGORIA);

        // Actualizar la vista
        descripcion.setText(descripcion_text);
        nombre.setText(nombre_text);
        valoracion.setSelection(getIndex(valoracion, valoracion_text));
        categoria.setSelection(getIndex(categoria, categoria_text));
    }

    /**
     * Obtiene el indice de un {@link Spinner} según el valor
     * de una cadena
     *
     * @param spinner Instancia del spinner
     * @param value   Cadena a buscar
     * @return Posición donde se encuentra
     */
    private int getIndex(Spinner spinner, String value) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(value)) {
                index = i;
                break;
            }
        }
        return index;
    }
}
