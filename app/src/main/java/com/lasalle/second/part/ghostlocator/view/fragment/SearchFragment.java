package com.lasalle.second.part.ghostlocator.view.fragment;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lasalle.second.part.ghostlocator.Manifest;
import com.lasalle.second.part.ghostlocator.R;
import com.lasalle.second.part.ghostlocator.view.util.LocationUtil;

public class SearchFragment extends Fragment implements Button.OnClickListener {

    private LocationUtil locationUtil;

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationUtil = new LocationUtil(getContext());
        if(!locationUtil.subscribeLocationUpdates()) {
            locationUtil.requestPermissions(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        Button button = (Button) view.findViewById(R.id.fragment_search_button_find_location);
        button.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.fragment_search_button_find_location) {
            Location location = locationUtil.getLastKnownLocation();
            if(location != null) {
                locationUtil.unsubscribeLocationUpdates();
                Log.d("SearchFragment", location.getLongitude() + " - " + location.getLatitude());
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        locationUtil.subscribeLocationUpdates();
    }
}
