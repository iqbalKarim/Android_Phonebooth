package com.iqbal.karim.iqbalkarim_hw2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

public class SecondActivity extends AppCompatActivity {

    private GestureDetectorCompat mDetector;
    ImageView phoneImage;
    TextView phoneName, phoneMemory, phoneStorage, phonePrice, phoneDesc;
    Phone p;
    DecimalFormat formatter = new DecimalFormat("###,###,###.00");
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_second);

        Intent receivedIntent = getIntent();
        Bundle b = receivedIntent.getExtras();
        p = b.getParcelable("phone");

        bind();

        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
    }

    private void bind() {
        phoneName = findViewById(R.id.tvPhoneName);
        phoneStorage = findViewById(R.id.tvPhoneStorage);
        phoneMemory = findViewById(R.id.tvPhoneMemory);
        phonePrice = findViewById(R.id.tvPhonePrice);
        phoneDesc = findViewById(R.id.tvPhoneDesc);
        phoneImage = findViewById(R.id.phoneImageBig);

        phoneName.setText(p.getName());
        phonePrice.setText(formatter.format(p.getPrice()) + " TL");
        phoneStorage.setText(p.getStorage() + " GB");
        phoneMemory.setText(p.getMemory() + " GB");
        phoneDesc.setText(p.getDescription());
        phoneImage.setImageBitmap(BitmapFactory.decodeByteArray(p.getImage(), 0, p.getImage().length));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void proceedToCheckout(View view) {
        intent = new Intent(this, ThirdActivity.class);
        Bundle b = new Bundle();
        b.putParcelable("phone", p);
        intent.putExtras(b);
        startActivity(intent);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
        public boolean onDoubleTap(MotionEvent event) {
            finish();
            return true;
        }
    }
}