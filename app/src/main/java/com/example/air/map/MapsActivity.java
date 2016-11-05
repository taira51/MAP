package com.example.air.map;


import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements LocationListener,OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //GPSから位置情報の取得
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //GPSプロバイダの取得
        String provider = LocationManager.GPS_PROVIDER;

        if(mLocationManager.isProviderEnabled(provider)){
            System.out.println("プロバイダ情報---------");
            System.out.println("取得したプロバイダ:"+provider);
        }

        mLocationManager.requestLocationUpdates(provider, 5000, 10, this);


        //マーカー
        LatLng sydney = new LatLng(34.68639, 135.52);     //マーカーを大阪の位置情報で指定（仮）
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //test
        System.out.println("MAPテストSTART！！！！！！！！！！！！！！");
        System.out.println("LatLngの内容を表示:"+ sydney.toString());
        System.out.println("MAPテストFINISH！！！！！！！！！！！！！！");

    }

    @Override
    public void onStop() {
        super.onStop();
        mLocationManager.removeUpdates(this);    // 位置情報の更新を止める
    }

    @Override   //位置座標を取得出来たら引数に渡して呼び出される
    public void onLocationChanged(Location location) {
        System.out.println("いぇああああああああああ");
        System.out.println("緯度:" + location.getLatitude());
        System.out.println("軽度:" + location.getLongitude());

    }

    @Override
    public void onProviderDisabled(String provider) {
    }


    @Override   //プロバイダが利用可能になった時に呼び出される
    public void onProviderEnabled(String provider) {
        System.out.println("プロバイダが有効になりました:"+provider);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}
