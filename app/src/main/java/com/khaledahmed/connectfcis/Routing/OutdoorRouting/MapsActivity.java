package com.khaledahmed.connectfcis.Routing.OutdoorRouting;

import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.khaledahmed.connectfcis.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList markerPoints = new ArrayList();
    private MarkerOptions landMarks;
    private LatLng fcis;
    private LocationManager locationManager;
    private MyLocationListener locationListener;
    private LatLng position = null;
    private RadioButton srcDestButton;
    private RadioButton gpsDestButton;
    private ImageButton locationButton;
    private ImageButton setMarkersBtn;
    private Spinner destinationSpinner;

    private LatLng currentLocation;

    private LatLng[] campusLandMarks = {
            new LatLng(30.078231, 31.284991),
            new LatLng(30.077339, 31.286418),
            new LatLng(30.075751, 31.281086),
            new LatLng(30.075269, 31.276789),
            new LatLng(30.074962, 31.287625),
            new LatLng(30.073551, 31.288280),
            new LatLng(30.076615, 31.290586),
            new LatLng(30.075770, 31.288634),
            new LatLng(30.076694, 31.287252),
            new LatLng(30.075317, 31.291173),
            new LatLng(30.064547, 31.278807),
            new LatLng(30.074187, 31.293756),
            new LatLng(30.077739, 31.283993)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        srcDestButton = (RadioButton) findViewById(R.id.src_to_dest_rb);
//        gpsDestButton = (RadioButton) findViewById(R.id.gps_to_dest_rb);
        locationButton = (ImageButton) findViewById(R.id.myLocation_button);
        setMarkersBtn = (ImageButton) findViewById(R.id.setMarkersBtn);
        locationListener = new MyLocationListener(getApplicationContext());
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        destinationSpinner = (Spinner) findViewById(R.id.dests);

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 0, locationListener);
        } catch (SecurityException ex) {
            Toast.makeText(getApplicationContext(), "You are not allowed to access current location", Toast.LENGTH_SHORT).show();

        }

        fcis = new LatLng(30.078222, 31.284938);
        currentLocation = fcis;

        setMarkersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (LatLng l : campusLandMarks) {
                    landMarks = new MarkerOptions();
                    landMarks.position(l);
                    landMarks.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    mMap.addMarker(landMarks);
                }
            }
        });
        destinationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (markerPoints.size() > 1) {
                    markerPoints.clear();
                    mMap.clear();
                }

                String destName = destinationSpinner.getSelectedItem().toString();
                LatLng latLng = null;
                switch (destName) {
                    case "Dentistry":
                        latLng = new LatLng(30.074187, 31.293756);
                        break;
                    case "Law":
                        latLng = new LatLng(30.077339, 31.286418);
                        break;
                    case "Medicine":
                        latLng = new LatLng(30.075751, 31.281086);
                        break;
                    case "Arts":
                        latLng = new LatLng(30.076694, 31.287252);
                        break;
                    case "Sciences":
                        latLng = new LatLng(30.077739, 31.283993);
                        break;
                    case "Commerce":
                        latLng = new LatLng(30.074962, 31.287625);
                        break;
                }

                // Adding new item to the ArrayList
                markerPoints.add(latLng);

                // Creating MarkerOptions
                MarkerOptions options = new MarkerOptions();

                // Setting the position of the marker
                options.position(latLng);

                if (markerPoints.size() == 1) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                } else if (markerPoints.size() == 2) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                }

                // Add new marker to the Google Map Android API V2
                mMap.addMarker(options);

                // Checks, whether start and end locations are captured
                if (markerPoints.size() >= 2) {
                    //LatLng origin = (LatLng) markerPoints.get(0);
                    LatLng origin = currentLocation;
                    LatLng dest = (LatLng) markerPoints.get(1);

                    if (markerPoints.size() == 1) {
                        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    } else if (markerPoints.size() == 2) {
                        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                    }

                    // Getting URL to the Google Directions API
                    String url = getDirectionsUrl(origin, dest);

                    DownloadTask downloadTask = new DownloadTask();

                    // Start downloading json data from Google Directions API
                    downloadTask.execute(url);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void addMarkers() {

    }

    public void updateCamera(float bearing) {
        CameraPosition currentPlace = new CameraPosition.Builder()
                .target(fcis)
                .bearing(bearing).tilt(65.5f).zoom(18f).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(currentPlace));

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        for (LatLng l : campusLandMarks) {
            landMarks = new MarkerOptions();
            landMarks.position(l);
            landMarks.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mMap.addMarker(landMarks);
        }

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                Geocoder coder = new Geocoder(getApplicationContext());
                List<Address> addressList;
                Location location = null;

                try {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                } catch (SecurityException ex) {
                    Toast.makeText(getApplicationContext(), "You are not allowed to access the current location", Toast.LENGTH_SHORT).show();
                }

                if (location != null) {
                    position = new LatLng(location.getLatitude(), location.getLongitude());
                    try {
                        addressList = coder.getFromLocation(position.latitude, position.longitude, 1);
                        if (!addressList.isEmpty()) {
                            String address = "";
                            for (int i = 0; i < addressList.get(0).getMaxAddressLineIndex(); i++) {
                                address += addressList.get(0).getAddressLine(i) + ", ";
                            }
                            mMap.addMarker(new MarkerOptions().position(position).title("My Location").snippet(address)).setDraggable(true);
                            currentLocation = position;
                        }
                    } catch (IOException ex) {
                        mMap.addMarker(new MarkerOptions().position(position).title("My Location"));
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
                } else {
                    Toast.makeText(getApplicationContext(), "please wait until your position is determined", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(fcis, 17));
        //updateCamera(180);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (markerPoints.size() > 1) {
                    markerPoints.clear();
                    mMap.clear();
                }

                // Adding new item to the ArrayList
                markerPoints.add(latLng);

                // Creating MarkerOptions
                MarkerOptions options = new MarkerOptions();

                // Setting the position of the marker
                options.position(latLng);

                if (markerPoints.size() == 1) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                } else if (markerPoints.size() == 2) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                }

                // Add new marker to the Google Map Android API V2
                mMap.addMarker(options);

                // Checks, whether start and end locations are captured
                if (markerPoints.size() >= 2) {
                    LatLng origin = (LatLng) markerPoints.get(0);
                    LatLng dest = (LatLng) markerPoints.get(1);

                    // Getting URL to the Google Directions API
                    String url = getDirectionsUrl(origin, dest);

                    DownloadTask downloadTask = new DownloadTask();

                    // Start downloading json data from Google Directions API
                    downloadTask.execute(url);
                }
            }
        });

    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();


            parserTask.execute(result);

        }
    }


    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(Color.RED);
                lineOptions.geodesic(true);

            }

// Drawing polyline in the Google Map for the i-th route
            mMap.addPolyline(lineOptions);
        }
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}
