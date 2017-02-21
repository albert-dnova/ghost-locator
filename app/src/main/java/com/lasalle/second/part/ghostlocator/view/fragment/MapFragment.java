package com.lasalle.second.part.ghostlocator.view.fragment;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lasalle.second.part.ghostlocator.R;

import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private MapView map;

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        map = (MapView) view.findViewById(R.id.map);
        map.onCreate(savedInstanceState);

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        map.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLngBounds bounds = new LatLngBounds(
                new LatLng(41.338669, 2.086029),
                new LatLng(41.450961, 2.22801));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0));

        Geocoder coder = new Geocoder(getContext());



        try {
            if(!coder.isPresent()) {
                throw new Exception("Coder is not present!");
            }

            List<Address> address = coder.getFromLocationName("Carrer dels quatre camins 30, 08022, Barcelona", 5);
            if (!address.isEmpty()) {
                Address location = address.get(0);
                googleMap.addMarker(
                        new MarkerOptions()
                                .position(new LatLng(location.getLatitude(), location.getLongitude())));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        googleMap.getUiSettings().setZoomControlsEnabled(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(map != null) {
            map.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(map != null) {
            map.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(map != null) {
            map.onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(map != null) {
            map.onDestroy();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if(map != null) {
            map.onLowMemory();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(map != null) {
            map.onStop();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(map != null) {
            map.onSaveInstanceState(outState);
        }
    }

}
