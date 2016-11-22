//package com.example.air.map;
//
//
//import android.content.Context;
//import android.graphics.Camera;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//
//public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
//    private Camera mCam;
//
//    public CameraPreview(Context context,Camera cam){
//        super(context);
//        mCam = cam;
//
//        SurfaceHolder holder = getHolder();
//        holder.addCallback(this);
//        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//    }
//}
