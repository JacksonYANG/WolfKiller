package com.jacksonyang.wolfkiller;
//打开程序的启动画面
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

import net.youmi.android.AdManager;
import net.youmi.android.nm.sp.SplashViewSettings;
import net.youmi.android.nm.sp.SpotListener;
import net.youmi.android.nm.sp.SpotManager;
import net.youmi.android.nm.sp.SpotRequestListener;

import java.util.ArrayList;
import java.util.List;

public class OpenPage extends AppCompatActivity {

    private RelativeLayout relativeLayout;
    boolean canjump;//设定是否可以跳过广告
    private String appId="0524a20efeb78740";
    private String appSecret="48b9badc8472f599";
    private SplashViewSettings splashViewSettings=new SplashViewSettings();
    private SpotRequestListener spotRequestListener=new SpotRequestListener() {
        @Override
        public void onRequestSuccess() {

        }

        @Override
        public void onRequestFailed(int i) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_page);
        relativeLayout=(RelativeLayout) findViewById(R.id.AdPage);
        SpotManager.getInstance(this).requestSpot(spotRequestListener);

        //权限处理
        //请求权限
        List<String> permissonlist=new ArrayList<>();
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            permissonlist.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            permissonlist.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            permissonlist.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        //权限获取后调用广告启动方法
        if(!permissonlist.isEmpty()){
            String[] permissons=permissonlist.toArray(new String[permissonlist.size()]);
            ActivityCompat.requestPermissions(this,permissons,1);
        }else{
            requestAds();
        }

        //打开程序即启动服务
        Intent serviceintent=new Intent(this,WolfService.class);
        startActivity(serviceintent);

        //设置开启广告页面
        AdManager.getInstance(this).init(appId,appSecret,true);
    }

    //请求开屏广告
    private void requestAds(){
        SpotManager.getInstance(this).showSplash(this, splashViewSettings, new SpotListener() {
            @Override
            public void onShowSuccess() {
                //广告显示完毕
                goPersonSet();
            }

            @Override
            public void onShowFailed(int i) {
                //广告加载失败
                goPersonSet();
            }

            @Override
            public void onSpotClosed() {
                canjump=true;
                goPersonSet();
            }

            @Override
            public void onSpotClicked(boolean b) {
                //广告被点击
            }
        });
    }

    private void goPersonSet(){
        if(canjump){
            //跳转主程序
            splashViewSettings.setTargetClass(PersonSet.class);
            finish();
        } else{
            canjump=true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        canjump=false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(canjump){
            goPersonSet();
        }
        canjump=true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpotManager.getInstance(this).onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0){
                    for(int result:grantResults){
                        if (result!=PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"必须同意所有权限才可使用本狼人杀法官程序",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestAds();
                }   else{
                    Toast.makeText(this,"发生未知错误，请在桌面手动重启程序",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }
}
