package com.raquel500c.mysurveymap;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raquel500c.mysurveymap.miProviderBD.ContratoProvider;

/**
 * {@link CursorAdapter} personalizado para los datos de los lugares
 *
 */
public class ActivitiesAdapter extends CursorAdapter {

    public ActivitiesAdapter(Context context) {
        super(context, null, 0);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView categoria = (TextView) view.findViewById(R.id.categoria_text);
        categoria.setText(cursor.getString(
                cursor.getColumnIndex(ContratoProvider.Columnas.CATEGORIA)));

        TextView nombre = (TextView) view.findViewById(R.id.nombre_text);
        nombre.setText(cursor.getString(
                cursor.getColumnIndex(ContratoProvider.Columnas.NOMBRE)));

        String valoracion = cursor.getString(
                cursor.getColumnIndex(ContratoProvider.Columnas.VALORACION));

        View indicator = view.findViewById(R.id.indicator);
        switch (valoracion) {
            case "Buena":
                indicator.setBackgroundResource(R.drawable.green_indicator);
                break;
            case "Mala":
                indicator.setBackgroundResource(R.drawable.red_indicator);
                break;
            case "Regular":
                indicator.setBackgroundResource(R.drawable.yellow_indicator);
                break;
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return inflater.inflate(R.layout.item_layout, parent, false);
    }
}
