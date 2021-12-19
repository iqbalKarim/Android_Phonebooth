package com.iqbal.karim.iqbalkarim_hw2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity {

    EditText etName, etNum, etEmail, etAddress;
    TextView phoneName3, phonePrice3, phoneMemory3, phoneStorage3;
    Spinner cardsSpn;
    MySpinnerAdapter spnAdapter;
    ImageView phoneImage3;
    Phone p;
    private GestureDetectorCompat mDetector;
    DecimalFormat formatter = new DecimalFormat("###,###,###.00");
    private String jsonStr;
    private JSONArray cards;
    private JSONObject allJSON;
    private ArrayList<CreditCard> creditCards=new ArrayList();
    Intent intent;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_third);

        Intent receivedIntent = getIntent();
        b = receivedIntent.getExtras();
        p = b.getParcelable("phone");

        bind();

        mDetector = new GestureDetectorCompat(this, new ThirdActivity.MyGestureListener());

        jsonStr = loadFileFromAssets("cards.json");
        new GetCardsJSON().execute();

        cardsSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Commons.setCard(creditCards.get(i));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void reviewOrder(View view) {
        String name, email, address;
        int pno;

        etName = findViewById(R.id.editTextName);
        etNum = findViewById(R.id.editTextPhone);
        etEmail = findViewById(R.id.editTextEmailAddress);
        etAddress = findViewById(R.id.editTextAddress);

        name = etName.getText().toString();
        email = etEmail.getText().toString();
        address = etAddress.getText().toString();

        if (name.isEmpty() || email.isEmpty() || address.isEmpty() || etName.getText().toString().isEmpty()) {
            Toast.makeText(ThirdActivity.this, "Please fill all necessary information.", Toast.LENGTH_SHORT).show();
        }
        else{
            pno = Integer.parseInt(etNum.getText().toString());
            intent = new Intent(ThirdActivity.this, FourthActivity.class);
            b.putString("name", name);
            b.putString("email", email);
            b.putString("address", address);
            b.putInt("pno", pno);
            intent.putExtras(b);
            startActivity(intent);
        }

    }

    private class GetCardsJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            if (jsonStr != null) {
                try {
                    allJSON = new JSONObject(jsonStr);
                    cards = allJSON.getJSONArray("cards");

                    for (int i = 0; i < cards.length(); i++) {

                        JSONObject s = cards.getJSONObject(i);

                        String cardholder = s.getString("cardholder");
                        String cardNumber = s.getString("cardNumber");
                        String cardCvc = s.getString("cvc");
                        String cardExpiry = s.getString("expiryDate");

                        CreditCard stu = new CreditCard(cardholder, cardNumber, cardCvc, cardExpiry);

                        creditCards.add(stu);
                    }
                } catch (JSONException ee) {
                    ee.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (creditCards != null) {
                spnAdapter = new MySpinnerAdapter(getBaseContext(), R.layout.spinner_layout, creditCards);
                cardsSpn.setAdapter(spnAdapter);
            } else
                Toast.makeText(ThirdActivity.this, "Not Found", Toast.LENGTH_LONG).show();
        }

    }

    private void bind() {
        phoneName3 = findViewById(R.id.phoneName3);
        phonePrice3 = findViewById(R.id.phonePrice3);
        phoneMemory3 = findViewById(R.id.phoneMemory3);
        phoneStorage3 = findViewById(R.id.phoneStorage3);
        phoneImage3 = findViewById(R.id.phoneImg3);
        cardsSpn = findViewById(R.id.spinner);

        phoneName3.setText(p.getName());
        phonePrice3.setText(formatter.format(p.getPrice()) + " TL");
        phoneStorage3.setText(p.getStorage() + " GB");
        phoneMemory3.setText(p.getMemory() + " GB");
        phoneImage3.setImageBitmap(BitmapFactory.decodeByteArray(p.getImage(), 0, p.getImage().length));
    }

    private String loadFileFromAssets(String fileName) {
        String file = null;
        try {
            InputStream is = getBaseContext().getAssets().open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();

            file = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return file;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
        public boolean onDoubleTap(MotionEvent event) {
            finish();
            return true;
        }
    }
}