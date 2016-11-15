package com.mycompany.pureweather1.pureweather1.activity;


import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;


import com.mycompany.pureweather1.R;
import com.mycompany.pureweather1.pureweather1.base.BaseActivity;
import com.mycompany.pureweather1.pureweather1.fragment.AboutFragment;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by Lenovo on 2016/4/22.
 */
public class AboutActivity extends BaseActivity{
        public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.about_toolbar);
        toolbar.setTitle("关于");
        toolbar.setNavigationIcon(R.mipmap.ic_keyboard_arrow_left_white_24dp);
        setSupportActionBar(toolbar);



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //改变状态栏的颜色
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintResource(R.color.colorPrimary);
        tintManager.setStatusBarTintEnabled(true);

        getFragmentManager().beginTransaction().replace(R.id.about_framelayout, new AboutFragment()).commit();
    }
}
