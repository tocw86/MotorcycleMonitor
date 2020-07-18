package com.software4bikers.motorcyclerun;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.se.omapi.Session;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.software4bikers.motorcyclerun.helpers.Helper;
import com.software4bikers.motorcyclerun.repositories.SessionRepository;
import com.software4bikers.motorcyclerun.sqlite.RunSessionDataModel;
import com.software4bikers.motorcyclerun.sqlite.RunSessionModel;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

public class GpxActivity extends AppCompatActivity {
    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView map = null;
    private MyLocationNewOverlay mLocationOverlay;
    public TextView totalDistance;
    public TextView avgSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpx_view);
        this.totalDistance = (TextView) findViewById(R.id.totalDistance);
        this.avgSpeed = (TextView) findViewById(R.id.avgSpeed);
        Bundle extras = getIntent().getExtras();
        String sessionId = extras.getString("sessionId");
        requestPermissionsIfNecessary(new String[]{
                // if you need to show the current location, uncomment the line below
                // Manifest.permission.ACCESS_FINE_LOCATION,
                // WRITE_EXTERNAL_STORAGE is required in order to show the map
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        });
        if (!sessionId.isEmpty()) {
            RunSessionDataModel runSessionDataModel = new RunSessionDataModel(this);
            Cursor res = runSessionDataModel.getRelatedDataWaypoints(sessionId);
            Cursor avgSpeedRes = runSessionDataModel.getAvgSpeedFromRelatedData(sessionId);
            if (res.getCount() > 0) {

                if(avgSpeedRes.getCount() > 0){
                    this.avgSpeed.setText(SessionRepository.getAvgSpeedFromSqlData(avgSpeedRes));
                }

                ArrayList<GeoPoint> waypoints = SessionRepository.getSessionDataModel(res, sessionId);

                if(waypoints.size() > 0){
                    //handle permissions first, before map is created. not depicted here

                    //load/initialize the osmdroid configuration, this can be done
                    Context ctx = getApplicationContext();
                    Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
                    //setting this before the layout is inflated is a good idea
                    //it 'should' ensure that the map has a writable location for the map cache, even without permissions
                    //if no tiles are displayed, you can try overriding the cache path using Configuration.getInstance().setCachePath
                    //see also StorageUtils
                    //note, the load method also sets the HTTP User Agent to your application's package name, abusing osm's
                    //tile servers will get you banned based on this string

                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    //inflate and create the map

                    map = (MapView) findViewById(R.id.map);
                    map.setTileSource(TileSourceFactory.MAPNIK);

                    map.setBuiltInZoomControls(true);
                    map.setMultiTouchControls(true);

                    IMapController mapController = map.getController();
                    mapController.setZoom(17.5);
                    RoadManager roadManager = new OSRMRoadManager(this);
                    mapController.setCenter(waypoints.get(0));
                    Road road = roadManager.getRoad(waypoints);
                    Polyline roadOverlay = RoadManager.buildRoadOverlay(road);
                    roadOverlay.getOutlinePaint().setStrokeWidth(15);
                    map.getOverlays().add(roadOverlay);
                    map.invalidate();
                    this.mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(ctx), map);
                    this.mLocationOverlay.enableMyLocation();

                    //String totalDistance = String.valueOf(Math.round(roadOverlay.getDistance()));

                    String totalDistance = Helper.parseMetersToKm(roadOverlay.getDistance());
                    this.totalDistance.setText(totalDistance);
                }

            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        if(map != null)
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onPause() {
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        if(map != null)
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            permissionsToRequest.add(permissions[i]);
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
}