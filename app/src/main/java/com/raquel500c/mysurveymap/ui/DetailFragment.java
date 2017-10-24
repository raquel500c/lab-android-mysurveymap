package com.raquel500c.mysurveymap.ui;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raquel500c.mysurveymap.R;
import com.raquel500c.mysurveymap.miProviderBD.ContratoProvider;

/**
 * Fragmento para el detalle de la actividad
 */
public class DetailFragment extends Fragment {

    /**
     * Textos del layout
     */
    private TextView descripcion, categoria, nombre, valoracion;

    /**
     * Identificador de la actividad
     */
    private long id;

    public DetailFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        // Obtención de views
        descripcion = (TextView) view.findViewById(R.id.descripcion_text);
        categoria = (TextView) view.findViewById(R.id.categoria_text);
        nombre = (TextView) view.findViewById(R.id.nombre_text);
        valoracion = (TextView) view.findViewById(R.id.estado_text);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        id = getActivity().getIntent().getLongExtra(ContratoProvider.Columnas._ID, -1);
        updateView(id);  // Actualizar la vista con los datos del lugar
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_edit:
                beginUpdate(); // Actualizar
                return true;
            case R.id.action_delete:
                deleteData(); // Eliminar
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * Elimina registro actual
     */
    private void deleteData() {
        Uri uri = ContentUris.withAppendedId(ContratoProvider.CONTENT_URI, id);
        getActivity().getContentResolver().delete(
                uri,
                null,
                null
        );
    }

    /**
     * Envía todos los datos del lugar hacia el formulario
     * de actualización
     */
    private void beginUpdate() {
        getActivity()
                .startActivity(
                        new Intent(getActivity(), UpdateActivity.class)
                                .putExtra(ContratoProvider.Columnas._ID, id)
                                .putExtra(ContratoProvider.Columnas.DESCRIPCION, descripcion.getText())
                                .putExtra(ContratoProvider.Columnas.CATEGORIA, categoria.getText())
                                .putExtra(ContratoProvider.Columnas.NOMBRE, nombre.getText())
                                .putExtra(ContratoProvider.Columnas.VALORACION, valoracion.getText())
                );
    }


    /**
     * Actualiza los textos del layout
     *
     * @param id Identificador de la actividad
     */
    private void updateView(long id) {
        if (id == -1) {
            descripcion.setText("");
            categoria.setText("");
            nombre.setText("");
            valoracion.setText("");

            return;
        }

        Uri uri = ContentUris.withAppendedId(ContratoProvider.CONTENT_URI, id);
        Cursor c = getActivity().getContentResolver().query(
                uri,
                null, null, null, null);

        if (c != null && !c.moveToFirst()) return;


        String descripcion_text = c.getString(c.getColumnIndex(ContratoProvider.Columnas.DESCRIPCION));
        String categoria_text = c.getString(c.getColumnIndex(ContratoProvider.Columnas.CATEGORIA));
        String entidad_text = c.getString(c.getColumnIndex(ContratoProvider.Columnas.NOMBRE));
        String estado_text = c.getString(c.getColumnIndex(ContratoProvider.Columnas.VALORACION));

        descripcion.setText(descripcion_text);
        categoria.setText(categoria_text);
        nombre.setText(entidad_text);
        valoracion.setText(estado_text);

        c.close(); // Liberar memoria del cursor
    }
}
