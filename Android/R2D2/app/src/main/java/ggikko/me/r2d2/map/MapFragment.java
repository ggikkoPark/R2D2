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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ggikko.me.r2d2.R;

/**
 * 구글 맵 지도 fragment
 */
public class MapFragment extends Fragment {

    GoogleMap map;

    //맵 무빙 테스트 임의 위치
    static final LatLng seolleung = new LatLng(37.504453, 127.048941);
    static final LatLng test1 = new LatLng(37.502879, 127.050058);
    static final LatLng test2 = new LatLng(37.505615, 127.051731);
    static final LatLng test3 = new LatLng(37.503130, 127.046775);
    static final LatLng test4 = new LatLng(37.505921, 127.047955);
    static final LatLng test5 = new LatLng(37.504015, 127.051731);
    static final LatLng test6 = new LatLng(37.504900, 127.045981);
    static final LatLng test7 = new LatLng(37.504281, 127.049669);
    static final LatLng test8 = new LatLng(37.508358, 127.054422);

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);

        MapView mapView = (MapView) view.findViewById(R.id.map);

        /** 사용자가 임의로 나갔다가 들어오더라도 맵에 뿌려져있는 정보를 저장해서 다시 랜더링 함 */
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        /** 맵 초기화 */
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        /** 미완성 맵 세팅 */
        initMapSetting(mapView);

        return view;
    }

    private void initMapSetting(MapView mapView) {
        map = mapView.getMap();
        CameraPosition cameraPosition = new CameraPosition.Builder().target(seolleung).tilt(45).zoom(16).bearing(45).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        // TODO : 지도정보 가져와서 뿌려줘야함
        map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_pointer)).position(test1));
        map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_pointer)).position(test2));
        map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_pointer)).position(test3));
        map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_pointer)).position(test4));
        map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_pointer)).position(test5));
        map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_pointer)).position(test6));
        map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_pointer)).position(test7));
        map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_pointer)).position(test8));

    }

}
