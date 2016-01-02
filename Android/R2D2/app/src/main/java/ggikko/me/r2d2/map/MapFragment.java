package ggikko.me.r2d2.map;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;

import ggikko.me.r2d2.R;

/**
 * 구글 맵 지도 fragment
 */
public class MapFragment extends Fragment {

    private GoogleMap googleMap;

    //맵 무빙 테스트 임의 위치
    static final LatLng WangShipRi = new LatLng(37.561533, 127.037732);

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);

        MapView mapView = (MapView) view.findViewById(R.id.map);

        /** 사용자가 임의로 나갔다가 들어오더라도 맵에 뿌려져있는 정보를 저장해서 다시 랜더링 해*/
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        /** 맵 초기화 */
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mapView.getMap();
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(WangShipRi, 17));


        return view;
    }

}
