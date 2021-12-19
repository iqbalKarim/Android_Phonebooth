package com.iqbal.karim.iqbalkarim_hw2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    RecyclerView phoneGallery;
    MyRecyclerViewAdapter adapter;
    Intent intent;
    private GestureDetectorCompat mDetector;
    Dialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main);

        try {
            String fileToDatabase = "/data/data/" + getPackageName() + "/databases/"+DatabaseHelper.DATABASE_NAME;
            File file = new File(fileToDatabase);
            File pathToDatabasesFolder = new File("/data/data/" + getPackageName() + "/databases/");
            if (!file.exists()) {
                pathToDatabasesFolder.mkdirs();
                Log.d("BURDA", "BURDA");
                CopyDB( getResources().getAssets().open(DatabaseHelper.DATABASE_NAME),
                        new FileOutputStream(fileToDatabase));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dbHelper = new DatabaseHelper(this);
        Commons.phones = (ArrayList<Phone>) PhonesTable.getAllPhones(dbHelper);

        intent = new Intent(this, SecondActivity.class);
        phoneGallery = findViewById(R.id.phoneGallery);
        adapter = new MyRecyclerViewAdapter(this, intent);
        phoneGallery.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        phoneGallery.setLayoutManager(layoutManager);

        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
    }

    public void CopyDB(InputStream inputStream, OutputStream outputStream) throws IOException {
        // Copy 1K bytes at a time
        byte[] buffer = new byte[1024];
        int length;
        Log.d("BURDA", "BURDA2");

        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
            Log.d("BURDA", "BURDA3");

        }
        inputStream.close();
        outputStream.close();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        public void onLongPress(MotionEvent motionEvent) {
            createDialog();
            customDialog.show();
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            deleteDialog();
            customDialog.show();
            return true;
        }
    }

    private void deleteDialog() {
        customDialog = new Dialog(this);
        customDialog.setContentView(R.layout.delete_item_dialog);

        Spinner phoneSpinner = customDialog.findViewById(R.id.spinner2);
        DeleteSpinnerAdapter adapter = new DeleteSpinnerAdapter(getBaseContext(), R.layout.delete_spinner_layout);
        phoneSpinner.setAdapter(adapter);
        phoneSpinner.setSelection(Commons.phones.size() - 1);

        Button back, deletePhone;

        back = customDialog.findViewById(R.id.button2);
        deletePhone = customDialog.findViewById(R.id.button3);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog.dismiss();
            }
        });
        deletePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Phone delP = (Phone) phoneSpinner.getSelectedItem();
                boolean res = PhonesTable.deletePho(dbHelper, delP.getId());
                if (res) {
                    customDialog.dismiss();
                    updateRecycler();
                }
                else Toast.makeText(MainActivity.this, "failed to delete", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createDialog() {
        customDialog = new Dialog(this);
        customDialog.setContentView(R.layout.add_item_dialog);


        Button back, addPhone;
        EditText etName, etStorage, etDesc, etMemory, etPrice;

        back = customDialog.findViewById(R.id.button2);
        addPhone = customDialog.findViewById(R.id.button3);
        etName = customDialog.findViewById(R.id.etPhoneName);
        etStorage = customDialog.findViewById(R.id.etStorage);
        etDesc = customDialog.findViewById(R.id.etDesc);
        etMemory = customDialog.findViewById(R.id.etMemory);
        etPrice = customDialog.findViewById(R.id.etPrice);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog.dismiss();
            }
        });

        addPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etName.getText().toString().isEmpty() || etDesc.getText().toString().isEmpty() ||
                        etStorage.getText().toString().isEmpty() || etMemory.getText().toString().isEmpty() ||
                        etPrice.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please Enter all necessary information", Toast.LENGTH_SHORT).show();
                }
                else {
                    String name = etName.getText().toString();
                    String desc = etDesc.getText().toString();
                    int storage = Integer.parseInt(etStorage.getText().toString());
                    int memory = Integer.parseInt(etMemory.getText().toString());
                    double price = Double.parseDouble(etPrice.getText().toString());

                    Bitmap bmp = ((BitmapDrawable) getResources().getDrawable(R.drawable.phones)).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte [] img = baos.toByteArray();

                    boolean flag = PhonesTable.insertPho(dbHelper, name, storage, memory, price, desc, img);
                    if (flag) {
                        customDialog.dismiss();
                        updateRecycler();
                    }
                    else Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateRecycler() {
        Commons.phones = (ArrayList<Phone>) PhonesTable.getAllPhones(dbHelper);
        adapter.notifyDataSetChanged();
    }
}