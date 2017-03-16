package com.example.administrator.keeprunning;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolylineOptions;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2017/1/5.
 */

public class RunningActivity extends AppCompatActivity implements LocationSource,AMapLocationListener {

    private AMap aMap;
    private MapView mapView=null;
    private LatLng oldLatlng;
    private boolean isFirstLatlng;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mlocationOption;
    private static int count=0;
    private static float distance=0;
    private float velocity;

    private Button startbutton;
    private Chronometer timer;
   // private TextView mLocationErrText;
    private TextView mvelocityText;
    private TextView mdistantText;
    //private RadioGroup mGPSModeGroup;
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);
        //setUpMapIfNeeded();
        mapView= (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        isFirstLatlng=true;
        init();
        initButton();
    }

    private void initButton() {
        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFirstLatlng) {   //如果未进入跑步状态
                    isFirstLatlng = false;
                    velocity = 0;
                    distance = 0;
                    timer.setBase(SystemClock.elapsedRealtime());
                    timer.start();
                    startbutton.setText("结束跑步");
                } else {
                    startbutton.setText("开始跑步");
                    isFirstLatlng = true;
                    timer.stop();
                }
            }
        });
    }

    private void init()
    {
        if(aMap==null)
        {
            aMap=mapView.getMap();
            aMap.setLocationSource(this);
            aMap.getUiSettings().setMyLocationButtonEnabled(true);
            aMap.setMyLocationEnabled(true);
            aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
            aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
            aMap.setMapTextZIndex(2);
            SetupLocationStyle();
        }

        mvelocityText= (TextView) findViewById(R.id.running_velocity);
        mdistantText= (TextView) findViewById(R.id.running_distance);
        timer= (Chronometer) findViewById(R.id.running_time);
        startbutton= (Button) findViewById(R.id.start_button);
//        mLocationErrText = (TextView)findViewById(R.id.debug_text);
//        mLocationErrText.setVisibility(View.GONE);
        

    }
    private void setUpMap(LatLng oldData,LatLng newData)
    {
        aMap.addPolyline(new PolylineOptions().add(oldData,newData).geodesic(true).color(Color.GREEN));
    }

    private void SetupLocationStyle(){
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.drawable.gps_point));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(STROKE_COLOR);
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(5);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(FILL_COLOR);
        // 将自定义的 myLocationStyle 对象添加到地图上
        aMap.setMyLocationStyle(myLocationStyle);
    }
    protected void onDestroy()
    {
        super.onDestroy();
        mapView.onDestroy();
        if(null != mlocationClient){
            mlocationClient.onDestroy();
        }
    }

    protected void onResume()
    {
        super.onResume();
        mapView.onResume();
    }
    protected void onPause()
    {
        super.onPause();
        mapView.onPause();
        deactivate();
    }
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                LatLng newLatlng=new LatLng(amapLocation.getLatitude(),amapLocation.getLongitude());
                if(isFirstLatlng)
                {
                    oldLatlng=newLatlng;
                }
                if(oldLatlng!=newLatlng){
                    float newdistance= AMapUtils.calculateLineDistance(oldLatlng,newLatlng);
                    velocity=newdistance/1.5f;
                    distance+=newdistance/1000;
                    setUpMap(oldLatlng,newLatlng);
                    oldLatlng=newLatlng;
                }
                DecimalFormat df = new DecimalFormat("####.##");
                mvelocityText.setText(df.format(velocity)+"m/s");
                mdistantText.setText(df.format(distance)+"KM");
                aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
            }
//            else {
//                String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
//                Log.e("AmapErr",errText);
//                mLocationErrText.setVisibility(View.VISIBLE);
//                mLocationErrText.setText(errText);
//            }
        }
    }
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mlocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mlocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mlocationOption);
            mlocationOption.setOnceLocation(false);
            mlocationOption.setGpsFirst(true);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationOption.setInterval(1000);
            mlocationClient.startLocation();
        }
    }
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }
}
