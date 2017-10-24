package com.raquel500c.mysurveymap.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.raquel500c.mysurveymap.ActivitiesAdapter;
import com.raquel500c.mysurveymap.R;
import com.raquel500c.mysurveymap.miProviderBD.ContratoProvider;

/**
 * Fragmento principal con lista de actividades
 */
public class MainFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<Cursor> {
    /**
     * Adaptador
     */
    private ActivitiesAdapter adaptador;

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Habilitar al fragmento para contribuir en la action bar
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ImageButton fab = (ImageButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            getActivity()
                                    .startActivity(
                                            new Intent(getActivity(), InsertActivity.class)
                                    );
                    }
                }
        );
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Iniciar adaptador
        adaptador = new ActivitiesAdapter(getActivity());
        // Relacionar adaptador a la lista
        setListAdapter(adaptador);
        // Iniciar Loader
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Consultar todos los registros
        return new CursorLoader(
                getActivity(),
                ContratoProvider.CONTENT_URI,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adaptador.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adaptador.swapCursor(null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        getActivity().startActivity(new Intent(getActivity(), DetailActivity.class)
                .putExtra(ContratoProvider.Columnas._ID, id));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            getLoaderManager().destroyLoader(0);
            if (adaptador != null) {
                adaptador.changeCursor(null);
                adaptador = null;
            }
        } catch (Throwable localThrowable) {
            // Proyectar la excepción
        }
    }
}
