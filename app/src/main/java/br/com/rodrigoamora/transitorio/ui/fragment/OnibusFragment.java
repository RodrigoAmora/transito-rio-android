package br.com.rodrigoamora.transitorio.ui.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import br.com.rodrigoamora.transitorio.R;
import br.com.rodrigoamora.transitorio.delegate.Delegate;
import br.com.rodrigoamora.transitorio.model.Onibus;
import br.com.rodrigoamora.transitorio.task.OnibusTask;

public class OnibusFragment extends Fragment implements Delegate<List<Onibus>>, OnMapReadyCallback, LocationListener {

    List<Onibus> onibusList;
    OnibusTask onibusTask;
    SupportMapFragment mapFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_onibus, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        buscarOnibus();
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
    }

    @Override
    public void success(List<Onibus> onibusList) {
        this.onibusList = onibusList;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (onibusList != null && !onibusList.isEmpty()) {
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
            //googleMap.setInfoWindowAdapter(new InfoWindowCustom(getActivity(), machineShops.get(size - 1)));
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        } else {
            LatLng latLng = new LatLng(-22.9076612, -43.1920286);
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 15);
            googleMap.moveCamera(update);

            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }

    private void buscarOnibus() {
        onibusTask = new OnibusTask(this);
        onibusTask.execute();
    }

}
