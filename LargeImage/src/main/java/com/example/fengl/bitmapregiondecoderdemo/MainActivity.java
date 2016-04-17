package com.example.fengl.bitmapregiondecoderdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private LargeImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (LargeImageView) findViewById(R.id.largeImageView);
        InputStream is = getResources().openRawResource(+R.mipmap.test);
        mImageView.setInputStream(is);
    }

    @Override
    protected void onDestroy() {
        mImageView = null;
        super.onDestroy();
    }
}
