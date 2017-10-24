package com.raquel500c.mysurveymap.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.raquel500c.mysurveymap.R;

/**
 * Created by Raquel500c on 25/03/2016.
 */

public class MapsFragment extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);

        // Obtener el SupportMapFragment y recibir una notificación cuando el mapa está listo para se
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    /**
     * Manipulamos el mapa una vez disponible.
     * Esta llamada se activa cuando el mapa está listo para ser utilizado.
     * Aquí es donde podemos añadir marcadores o líneas , añadir detectores o mover la cámara.
     * Si los servicios de Google Play no están instalados en el dispositivo, se pedirá al usuario instalarlos.
     * Dentro de SupportMapFragment . Este método sólo se activará una vez que el usuario tiene
     * Instalados los servicios de Google Play y se devuelve a la aplicación.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //En este caso, por ejemplo, Añadimos un marcador al insti y mueve la cámara a su dirección
        LatLng iesBarajas = new LatLng(40.450802, -3.599619);
        CameraPosition camPos = new CameraPosition.Builder()
                .target(iesBarajas )
                .zoom (10)
                .bearing (45) //orientación
                .tilt(40)//grados camara
                .build();
        CameraUpdate camUpd3= CameraUpdateFactory.newCameraPosition(camPos);
        mMap.animateCamera(camUpd3);
        mMap.addMarker(new MarkerOptions()
                .position(iesBarajas)
                .title("IES BARAJAS " + iesBarajas.toString()));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(iesBarajas));

        //El usuario podrá incluir nuevos marcadores al hacer un LongClick
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.bar))
                        .anchor(0.0f, 1.0f)
                        .position(latLng)
                        .title("Nuevo Marcador"));

            }
        });
            //devuelve un mensaje al hacer clic sobre un marcador existente en el mapa
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(getApplicationContext(), ( marker.getPosition().toString()), Toast.LENGTH_LONG).show();
                return false;
            }
        });

    }
}
