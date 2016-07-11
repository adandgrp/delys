package mx.com.coffeebean_lab.DeliverySwift;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import mx.com.coffeebean_lab.DeliverySwift.R;


public class buscaamigos extends ActionBarActivity {

    GoogleMap googleMap;
    MapView mapView;
    @Override
    protected  void onResume(){
        super.onResume();
        mapView.onResume();
    }
    protected  void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();
    }
    protected void onPause(){
        super.onPause();
        mapView.onPause();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscaamigos);
        mapView = (MapView)findViewById(R.id.mi_mapa);
        mapView.onCreate(savedInstanceState);

        googleMap = mapView.getMap();
        googleMap.setMapType(googleMap.MAP_TYPE_NORMAL);
        googleMap.setMyLocationEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
