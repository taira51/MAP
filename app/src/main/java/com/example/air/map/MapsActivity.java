package com.example.air.map;


import android.app.AlertDialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;


public class MapsActivity extends FragmentActivity implements LocationListener,OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager mLocationManager;
    ImageButton cameraIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //カメラアイコン
        cameraIcon = (ImageButton)findViewById(R.id.cameraIcon);
        cameraIcon.setVisibility(View.INVISIBLE);

        //MapsInitializerの初期化（例外処理してない）
        MapsInitializer.initialize(this);
//        try {
//            MapsInitializer.initialize(this);
//        } catch (GooglePlayServicesNotAvailableException e) {
//            Log.d(TAG, "You must update Google Maps."); finish();
//        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);    //現在地に戻るボタン(on)
        mMap.getUiSettings().setMapToolbarEnabled(false);   //mapToolBarの表示(off)
        mMap.getUiSettings().setZoomControlsEnabled(true);  //ズームボタン(on/デバッグ用)


        //スタンプラリーのスポット3点（デバッグ用）
        Marker osakazyo = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(34.684581, 135.526349)).title("大阪城"));
        Marker harukasu = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(34.645842, 135.513971)).title("あべのハルカス"));
        Marker usj = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(34.6671654, 135.4356473)).title("USJ"));
        //infoWindowのカスタマイズ
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });

        //マーカータップ時のイベントハンドラ
        mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                cameraIcon.setVisibility(View.VISIBLE);
                return false;
            }
        });

        //地図上をタップ時のイベントハンドラ
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                cameraIcon.setVisibility(View.INVISIBLE);
            }
        });


        //プロバイダの取得
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);       //高精度
        criteria.setAccuracy(Criteria.NO_REQUIREMENT);

        String provider = mLocationManager.getBestProvider(criteria, true);

        if(mLocationManager.isProviderEnabled(provider)){
            System.out.println("プロバイダ情報---------");
            System.out.println("取得したプロバイダ:"+provider);
        }

        LatLng start_position = new LatLng(21.308889,-157.826111);
        mMap.addMarker(new MarkerOptions()
                .position(start_position)
                .title("ホノルル")
                .snippet("START_POSITION"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(start_position));

        mLocationManager.requestLocationUpdates(provider, 1, 1, this);

    }

    @Override   //位置座標を取得したら引数に渡して呼び出される
    public void onLocationChanged(Location location) {
//        //位置情報を取得したらダイアログを表示する
//        new AlertDialog.Builder(this)
//                .setTitle("GPS")
//                .setMessage("位置情報を取得しました")
//                .setPositiveButton("OK", null)
//                .show();

        //カメラを現在地に移動
        LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
        float[] results = new float[1];
        Location.distanceBetween(location.getLatitude(), location.getLongitude(), 34.684581, 135.526349, results);

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

    @Override
    public void onStop() {
        super.onStop();
        mLocationManager.removeUpdates(this);    // 位置情報の更新を止める
    }
}