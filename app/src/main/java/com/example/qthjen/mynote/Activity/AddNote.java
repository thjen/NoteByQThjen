package com.example.qthjen.mynote.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.qthjen.mynote.Fragment.FragmentAddImage;
import com.example.qthjen.mynote.Fragment.FragmentTitle;
import com.example.qthjen.mynote.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddNote extends AppCompatActivity {

    Toolbar tbar_addnote;

    final int Request_camera = 1;
    final int Request_takeimage = 2;

    EditText et_note;
    Button bt_add, bt_addImage;
    public static ImageView image_add;

    FragmentManager fragmentManager = getFragmentManager();

    boolean checkShowChooseTakeImage = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnote);

        findView();

        FinishToolbar(tbar_addnote);

        EventButton();

        EventAdd();

    }

    private void findView() {

        tbar_addnote = (Toolbar) findViewById(R.id.tbar_addnote);

        et_note = (EditText) findViewById(R.id.et_note);
        bt_add = (Button) findViewById(R.id.bt_add);

        image_add = (ImageView) findViewById(R.id.image_add);

        bt_addImage = (Button) findViewById(R.id.bt_addImage);

    }

    private Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {

        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        //bm.recycle();
        return resizedBitmap;

    }

    private void EventAdd() {

        bt_add.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                String title = FragmentTitle.textinput_title.getText().toString();
                String note = et_note.getText().toString();
                String mdate;

                /** set date **/
                android.icu.util.Calendar calendar = android.icu.util.Calendar.getInstance();

                int date = calendar.get(android.icu.util.Calendar.DAY_OF_WEEK);
                String nameDate = "";

                switch (date) {

                    case 2:
                        nameDate = "Monday";
                        break;
                    case 3:
                        nameDate = "Tuesday";
                        break;
                    case 4:
                        nameDate = "Wednesday";
                        break;
                    case 5:
                        nameDate = "Thursday";
                        break;
                    case 6:
                        nameDate = "Friday";
                        break;
                    case 7:
                        nameDate = "Saturday";
                        break;

                    default:
                        nameDate = "Sunday";
                        break;
                }

                mdate = nameDate + " " + calendar.get(android.icu.util.Calendar.DATE) + "/" + calendar.get(android.icu.util.Calendar.MONTH) + "/" + calendar.get(android.icu.util.Calendar.YEAR);

                byte[] imagee = new byte[0];
                
                if ( image_add.getDrawable() != null ) {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) image_add.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();

                    if ( bitmap.getWidth() > bitmap.getHeight() ) {
                        getResizedBitmap(bitmap, 780, 540).compress(Bitmap.CompressFormat.JPEG, 100, byteArray);
                    } else if ( bitmap.getWidth() < bitmap.getHeight() ) {
                        getResizedBitmap(bitmap, 540, 780).compress(Bitmap.CompressFormat.JPEG, 100, byteArray);
                    } else {
                        getResizedBitmap(bitmap, 720, 720).compress(Bitmap.CompressFormat.JPEG, 100, byteArray);
                    }

                    imagee = byteArray.toByteArray();
                }

                if (title.isEmpty() == false && note.isEmpty() == false) {
                    MainActivity.dataBase.INSERT_ITEMS(title, note, mdate, imagee);
                    startActivity(new Intent(AddNote.this, MainActivity.class));
                } else {
                    Toast.makeText(AddNote.this, "Invites you enter a title and a note. Please!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void EventButton() {

        bt_addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ( checkShowChooseTakeImage == false ) {

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.addToBackStack("takeImage");
                    fragmentTransaction.add(R.id.ll_addImage, new FragmentAddImage(), "TakeImage");

                    fragmentTransaction.commit();

                    checkShowChooseTakeImage = true;

                } else {

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    FragmentAddImage fragmentAddImage = (FragmentAddImage) getFragmentManager().findFragmentByTag("TakeImage");

                    if ( fragmentAddImage != null ) {
                        fragmentTransaction.remove(fragmentAddImage);
                        fragmentTransaction.commit();
                    } else {
                        Toast.makeText(AddNote.this, "Fragment is null", Toast.LENGTH_SHORT).show();
                    }

                    checkShowChooseTakeImage = false;

                }

            }
        });

    }

    public void TakeByCamera (View view ) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FragmentAddImage fragmentAddImage = (FragmentAddImage) getFragmentManager().findFragmentByTag("TakeImage");

        fragmentTransaction.remove(fragmentAddImage);
        fragmentTransaction.commit();

        ActivityCompat.requestPermissions(AddNote.this, new String[]{Manifest.permission.CAMERA}, Request_camera);

        checkShowChooseTakeImage = false;

    }

    public void TakeByFile (View view ) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FragmentAddImage fragmentAddImage = (FragmentAddImage) getFragmentManager().findFragmentByTag("TakeImage");

        fragmentTransaction.remove(fragmentAddImage);
        fragmentTransaction.commit();

        ActivityCompat.requestPermissions(AddNote.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Request_takeimage);

        checkShowChooseTakeImage = false;

    }

    private void FinishToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch ( requestCode ) {

            case Request_camera:
                if ( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, Request_camera);
                } else {
                    Toast.makeText(this, "You have not given permission", Toast.LENGTH_SHORT).show();
                }
                break;

            case Request_takeimage:
                if ( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, Request_takeimage);
                } else {
                    Toast.makeText(this, "You have not given permission", Toast.LENGTH_SHORT).show();
                }
                break;

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ( requestCode == Request_camera && resultCode == RESULT_OK && data != null ) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            image_add.setImageBitmap(bitmap);

        }

        if ( requestCode == Request_takeimage && resultCode == RESULT_OK && data != null ) {

            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                image_add.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
