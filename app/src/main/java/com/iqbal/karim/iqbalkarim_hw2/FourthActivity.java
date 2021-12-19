package com.iqbal.karim.iqbalkarim_hw2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class FourthActivity extends AppCompatActivity {

    Intent intent;
    Bundle b;
    Phone p;
    String name, email, address;
    int pno;
    private GestureDetectorCompat mDetector;
    Dialog customDialog;

    boolean kvkk = false;
    CheckBox cbKvkk;

    ImageView phoneImg2;
    DecimalFormat formatter = new DecimalFormat("###,###,###.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_fourth);

        intent = getIntent();
        b = intent.getExtras();

        bind();
        mDetector = new GestureDetectorCompat(this, new FourthActivity.MyGestureListener());

        cbKvkk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbKvkk.isChecked()) kvkk = true;
                else kvkk = false;
            }
        });
    }

    private void bind() {
        cbKvkk = findViewById(R.id.cbKvkk);

        //Order Summary
        TextView phoneName, phoneMemory, phoneStorage, phonePrice;
        phoneName = findViewById(R.id.phoneName2);
        phonePrice = findViewById(R.id.phonePrice2);
        phoneMemory = findViewById(R.id.phoneMemory2);
        phoneStorage = findViewById(R.id.phoneStorage2);
        phoneImg2 = findViewById(R.id.phoneImg2);

        p = b.getParcelable("phone");
        phoneName.setText(p.getName());
        phonePrice.setText(formatter.format(p.getPrice()) + " TL");
        phoneStorage.setText(p.getStorage() + " GB");
        phoneMemory.setText(p.getMemory() + " GB");
        phoneImg2.setImageBitmap(BitmapFactory.decodeByteArray(p.getImage(), 0, p.getImage().length));

        //Delivery Information
        TextView deliveryName, deliveryPno, deliveryEmail, deliveryAddress;
        name = b.getString("name");
        email = b.getString("email");
        address = b.getString("address");
        pno = b.getInt("pno");

        deliveryName = findViewById(R.id.summaryName);
        deliveryAddress = findViewById(R.id.summaryAddress);
        deliveryEmail = findViewById(R.id.summaryEmail);
        deliveryPno = findViewById(R.id.summaryNumber);

        deliveryName.setText(name);
        deliveryAddress.setText(address);
        deliveryEmail.setText(email);
        deliveryPno.setText(pno+"");

        //Payment Information
        TextView cardName, cardNum, cardExp, cardCvc;
        cardName = findViewById(R.id.tvCardName);
        cardNum = findViewById(R.id.tvCardNum);
        cardExp = findViewById(R.id.tvCardExp);
        cardCvc = findViewById(R.id.tvCardCvc);

        cardName.setText(Commons.card.getCardholderName());
        cardNum.setText(Commons.card.getCardNumber());
        cardExp.setText(Commons.card.getCardExpiry());
        cardCvc.setText(Commons.card.getCvc());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void placeOrder(View view) {
        if (!kvkk){
            Toast.makeText(FourthActivity.this, "Please agree to the Terms and Conditions", Toast.LENGTH_SHORT).show();
        }else{
            createDialog();
            customDialog.show();
        }
    }

    private void createDialog() {
        customDialog = new Dialog(this);
        customDialog.setContentView(R.layout.dialog);

        TextView dgCardName, dgCardNo, dgCardExp, dgCardCvc;

        dgCardName = customDialog.findViewById(R.id.dialogCardName);
        dgCardNo = customDialog.findViewById(R.id.dialogCardNo);
        dgCardExp = customDialog.findViewById(R.id.dialogCardExp);
        dgCardCvc = customDialog.findViewById(R.id.dialogCardCvc);

        dgCardName.setText(Commons.card.getCardholderName());
        dgCardNo.setText(Commons.card.getCardNumber());
        dgCardExp.setText(Commons.card.getCardExpiry());
        dgCardCvc.setText(Commons.card.getCvc());

        Button btnBack, btnConfirm;
        btnBack = customDialog.findViewById(R.id.dialogBack);
        btnConfirm = customDialog.findViewById(R.id.dialogConfirm);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm();
            }
        });
    }

    private void confirm() {
        customDialog.dismiss();

        AlertDialog.Builder confirmationDialog = new AlertDialog.Builder(this);
        confirmationDialog.setTitle("Congratulations!");
        confirmationDialog.setMessage("Your phone will be delivered within 3 working days!");

        confirmationDialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        confirmationDialog.create();
        confirmationDialog.show();

    }

    private void goBack() {
        finish();
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
        public boolean onDoubleTap(MotionEvent event) {
            finish();
            return true;
        }
    }
}