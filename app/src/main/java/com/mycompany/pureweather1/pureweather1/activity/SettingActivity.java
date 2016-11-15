package com.mycompany.pureweather1.pureweather1.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.mycompany.pureweather1.R;
import com.mycompany.pureweather1.pureweather1.base.BaseActivity;
import com.mycompany.pureweather1.pureweather1.fragment.AboutFragment;
import com.mycompany.pureweather1.pureweather1.fragment.FrequencyPickerFragment;
import com.mycompany.pureweather1.pureweather1.fragment.SettingFragment;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class SettingActivity extends BaseActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.setting_toolbar);
        toolbar.setTitle("设置");
        toolbar.setNavigationIcon(R.mipmap.ic_keyboard_arrow_left_white_24dp);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, null);
                finish();
            }
        });

        //改变状态栏的颜色
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintResource(R.color.colorPrimary);
        tintManager.setStatusBarTintEnabled(true);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.setting_framelayout, new SettingFragment())
                .commit();



    }
    public void onBackPressed(){
        //Log.d("SettingActivity销毁", "确认销毁");
        setResult(RESULT_OK, null);
        finish();
    }
}
