package no.hin.student.mymapapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


public class MainActivity extends Activity
{
    GoogleMap googleMap;
    String TAG = "Prosjekt: ";
    private static final LatLng STOKMARKNES = new LatLng(68.568854, 14.945730);
    private static final LatLng INNHAVET = new LatLng(67.968125 , 15.926599);
    private static final LatLng NARVIK = new LatLng(68.434521,  17.420488);


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        if (googleMap != null) {
            addLines();
        }
    }

    private void addLines() {
        googleMap.addMarker(new MarkerOptions().position(STOKMARKNES).title("Stokmarknes").draggable(true));
        googleMap.addMarker(new MarkerOptions().position(INNHAVET).title("Innhavet").draggable(true));
        googleMap.addMarker(new MarkerOptions().position(NARVIK).title("Narvik").draggable(true));
        googleMap.addPolyline((new PolylineOptions()).add(INNHAVET, NARVIK, STOKMARKNES, INNHAVET).width(5).color(Color.CYAN).geodesic(true));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(STOKMARKNES, 13));
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
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
