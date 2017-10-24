package com.raquel500c.mysurveymap.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.raquel500c.mysurveymap.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            MainFragment fragment = new MainFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, fragment, MainFragment.class.getSimpleName())
                    .commit();
        }
    }
}
