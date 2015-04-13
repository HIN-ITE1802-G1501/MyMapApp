package no.hin.student.mymapapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


public class MainActivity extends ActionBarActivity implements GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener {
    GoogleMap googleMap;

    String TAG = "Prosjekt: ";

    private static final LatLng STOKMARKNES = new LatLng(68.568854, 14.945730);
    private static final LatLng INNHAVET = new LatLng(67.968125 , 15.926599);
    private static final LatLng NARVIK = new LatLng(68.434521,  17.420488);

    boolean markerClicked;
    PolylineOptions rectOptions;
    Polyline polyline;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        if (googleMap != null) {
            addLines();
        }


        googleMap.setOnMapClickListener(this);
        googleMap.setOnMapLongClickListener(this);
        googleMap.setOnMarkerClickListener(this);

        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
    }

    private void addLines() {
        googleMap.addMarker(new MarkerOptions().position(STOKMARKNES).title("Stokmarknes").draggable(true));
        googleMap.addMarker(new MarkerOptions().position(INNHAVET).title("Innhavet").draggable(true));
        googleMap.addMarker(new MarkerOptions().position(NARVIK).title("Narvik").draggable(true));
        googleMap.addPolyline((new PolylineOptions()).add(INNHAVET, NARVIK, STOKMARKNES, INNHAVET).width(5).color(Color.CYAN).geodesic(true));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(STOKMARKNES, 13));
    }


    @Override
    protected void onResume() {
        super.onResume();
        final LocationManager locationManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_view)
        {
            if (googleMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL)
                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            else googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }

        if (id == R.id.action_delete)
        {

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapClick(LatLng point) {
        Log.d(TAG, point.toString());
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(point));
        markerClicked = false;
    }

    @Override
    public void onMapLongClick(LatLng point) {
        Log.d(TAG, "New marker added@" + point.toString());
        googleMap.addMarker(new MarkerOptions().position(point).title(point.toString()));
        markerClicked = false;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if(markerClicked){
            if(polyline != null){
                polyline.remove();
                polyline = null;
            }

            rectOptions.add(marker.getPosition());
            rectOptions.color(Color.RED);
            polyline = googleMap.addPolyline(rectOptions);
        }else{
            if(polyline != null){
                polyline.remove();
                polyline = null;
            }

            rectOptions = new PolylineOptions().add(marker.getPosition());
            markerClicked = true;
        }

        return true;
    }
}
