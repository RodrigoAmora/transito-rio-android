package br.com.rodrigoamora.transitorio.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import br.com.rodrigoamora.transitorio.R;
import br.com.rodrigoamora.transitorio.delegate.Delegate;
import br.com.rodrigoamora.transitorio.model.Onibus;
import br.com.rodrigoamora.transitorio.task.OnibusTask;
import br.com.rodrigoamora.transitorio.ui.activity.MainActivity;
import br.com.rodrigoamora.transitorio.util.GPSUtil;

public class OnibusFragment extends Fragment implements Delegate<List<Onibus>>, OnMapReadyCallback, LocationListener, View.OnClickListener {

    List<Onibus> onibusList;
    OnibusTask onibusTask;

    CardView cardViewProgressBar;
    FloatingActionButton fabLocation;
    GoogleMap googleMap;
    SupportMapFragment mapFragment;
    MainActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_onibus, container, false);
        cardViewProgressBar = rootView.findViewById(R.id.progress_bar);
        fabLocation = rootView.findViewById(R.id.fab_location);
        fabLocation.setOnClickListener(this);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (MainActivity) getActivity();
        buscarOnibus();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.fab_location:
                filtrarOnibusProximos();
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void error() {
        mapFragment.getMapAsync(this);
        cardViewProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void success(List<Onibus> onibusList) {
        this.onibusList = onibusList;
        this.mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (onibusList != null && !onibusList.isEmpty()) {
            this.googleMap = googleMap;
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            googleMap.setMyLocationEnabled(true);

            for (Onibus machineShop : onibusList) {
                LatLng latLng = new LatLng(machineShop.getLatidude(), machineShop.getLongitude());
                googleMap.addMarker(new MarkerOptions()
                        .title(machineShop.getLinha()+" - "+machineShop.getOrdem())
                        //.snippet(getArguments().getString("desc"))
                        .position(latLng));
                //googleMap.setInfoWindowAdapter(new InfoWindowCustom(getActivity(), machineShop));
            }

            int size = onibusList.size();
            LatLng latLng = new LatLng(onibusList.get(size-1).getLatidude(), onibusList.get(size - 1).getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 15);
            googleMap.moveCamera(update);
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        } else {
            LatLng latLng = new LatLng(-22.9076612, -43.1920286);
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 15);
            googleMap.moveCamera(update);
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
        cardViewProgressBar.setVisibility(View.GONE);
    }

    private void buscarOnibus() {
        cardViewProgressBar.setVisibility(View.VISIBLE);
        onibusTask = new OnibusTask(this);
        onibusTask.execute();
    }

    private void filtrarOnibusProximos() {
        if (!onibusList.isEmpty()) {
            Location myLocation = getLocation(activity);
            if (myLocation != null) {
                List<Onibus> onibusProximos = new ArrayList();
                for (int i = 0; i < onibusList.size(); i++) {
                    Onibus onibus = onibusList.get(i);
                    Double lat = onibus.getLatidude();
                    Double lng = onibus.getLongitude();

                    Location locationOption = new Location(Context.LOCATION_SERVICE);
                    locationOption.setLatitude(lat);
                    locationOption.setLongitude(lng);

                    Float distance = 0f;
                    distance = myLocation.distanceTo(locationOption);
                    if (distance <= 1500) {
                        onibusProximos.add(onibus);
                    }
                }

                if (onibusProximos.isEmpty()) {
                    Toast.makeText(activity, getString(R.string.alert_sem_onibus_proximo), Toast.LENGTH_LONG).show();
                } else {
                    onibusList.clear();
                    onibusList = onibusProximos;

                    LatLng latLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                    CircleOptions circleOptions = new CircleOptions();
                    circleOptions.center(latLng);
                    circleOptions.fillColor(R.color.colorPrimary);
                    circleOptions.radius(1500);
                    circleOptions.strokeColor(Color.BLUE);
                    circleOptions.strokeWidth(2.0f);
                    googleMap.addCircle(circleOptions);

                    MarkerOptions marker = new MarkerOptions().position(latLng).title(getString(R.string.voce_esta_aqui));
                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    googleMap.addMarker(marker);

                    mapFragment.getMapAsync(this);
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    public Location getLocation(Context context) {
        if (GPSUtil.gpsIsEnable(context)) {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                return location;
            }

            return null;
        }

        Toast.makeText(context, getString(R.string.alert_gps_desativado), Toast.LENGTH_LONG).show();
        return null;
    }

}
