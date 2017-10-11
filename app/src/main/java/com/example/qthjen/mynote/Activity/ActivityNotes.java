package com.example.qthjen.mynote.Activity;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.icu.util.Calendar;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qthjen.mynote.Fragment.FragmentAddImage;
import com.example.qthjen.mynote.Model.Notes;
import com.example.qthjen.mynote.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ActivityNotes extends AppCompatActivity {

    Toolbar tbar_notes;
    EditText et_note_title, et_note_image;
    ImageView iv_trashNote, iv_takeImage, iv_image;
    TextView tv_dateImage;

    boolean showTakeImage = false;

    final int REQUEST_CODE_CAMERA = 1;
    final int REQUEST_CODE_FILE = 2;

    FragmentManager fragmentManager = getFragmentManager();

    private int id;
    private String title;
    private String note;
    private String date;
    private byte[] image;

    private String dateUpdate;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        FindView();
        FinishToolbar(tbar_notes);
        GetIntentSer();
        Event();
        setCalendar();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setCalendar() {

        Calendar calendar = Calendar.getInstance();

        int ngay = calendar.get(Calendar.DAY_OF_WEEK);
        String nameDate = "";

        switch ( ngay) {

            case 2: nameDate = "Monday";break;
            case 3: nameDate = "Tuesday";break;
            case 4: nameDate = "Wednesday";break;
            case 5: nameDate = "Thursday";break;
            case 6: nameDate = "Friday";break;
            case 7: nameDate = "Saturday";break;

            default: nameDate = "Sunday";break;
        }

        date = nameDate + " " + calendar.get(Calendar.DATE) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR);
        String today = calendar.get(Calendar.DATE) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR);

        tv_dateImage.setText("Today " + today);

        dateUpdate = date;

    }

    private void Event() {

        iv_trashNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.dataBase.QueryData("DELETE FROM Notes WHERE Id = '" + id + "'");
                startActivity(new Intent(ActivityNotes.this, MainActivity.class));
            }
        });

        iv_takeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ( showTakeImage == false ) {

                    FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
                    fragmentTransaction1.addToBackStack("takeImage");    // add into stack
                    fragmentTransaction1.add(R.id.fragment_father, new FragmentAddImage(), "TakeImage");

                    fragmentTransaction1.commit();
                    showTakeImage = true;

                } else {

                    FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
                    FragmentAddImage fragmentAddImage = (FragmentAddImage) getFragmentManager().findFragmentByTag("TakeImage");

                    if ( fragmentAddImage != null) {
                        fragmentTransaction1.remove(fragmentAddImage);
                        fragmentTransaction1.commit();
                    } else {
                        Toast.makeText(ActivityNotes.this, "Fragment is null", Toast.LENGTH_SHORT).show();
                    }

                    showTakeImage = false;

                }

            }
        });

        et_note_title.setText(title);
        et_note_image.setText(note);

        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

        iv_image.setImageBitmap(bitmap);

    }

    public void TakeByCamera(View view) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FragmentAddImage fragmentAddImage = (FragmentAddImage) getFragmentManager().findFragmentByTag("TakeImage");

        fragmentTransaction.remove(fragmentAddImage);
        fragmentTransaction.commit();

        ActivityCompat.requestPermissions(ActivityNotes.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);

        showTakeImage = false;

    }

    public void TakeByFile(View view) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FragmentAddImage fragmentAddImage = (FragmentAddImage) getFragmentManager().findFragmentByTag("TakeImage");

        fragmentTransaction.remove(fragmentAddImage);
        fragmentTransaction.commit();

        ActivityCompat.requestPermissions(ActivityNotes.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_FILE);

        showTakeImage = false;

    }

    private void FindView() {
        tbar_notes = (Toolbar) findViewById(R.id.tbar_notes);
        et_note_title = (EditText) findViewById(R.id.et_note_title);
        iv_trashNote = (ImageView) findViewById(R.id.iv_trashNote);
        iv_takeImage = (ImageView) findViewById(R.id.iv_takeImage);
        tv_dateImage = (TextView) findViewById(R.id.tv_dateImage);
        et_note_image = (EditText) findViewById(R.id.et_note_image);
        iv_image = (ImageView) findViewById(R.id.iv_image);
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

    private void GetIntentSer() {

        Intent intent = getIntent();
        Notes notes = (Notes) intent.getSerializableExtra("mynotes");

        id = notes.getId();
        title = notes.getTitle();
        note = notes.getNote();
        date = notes.getDate();
        image = notes.getImage();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_done) {

            String Titlee = et_note_title.getText().toString().trim();
            String Notee = et_note_image.getText().toString().trim();

            byte[] byteImage = new byte[0];
            /** null pointer exception **/
            try {

                if (iv_image.getDrawable() != null) {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) iv_image.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                    if (bitmap.getWidth() > bitmap.getHeight()) {
                        getResizedBitmap(bitmap, 780, 540).compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    } else if (bitmap.getWidth() < bitmap.getHeight()) {
                        getResizedBitmap(bitmap, 540, 780).compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    } else {
                        getResizedBitmap(bitmap, 720, 720).compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    }

                    byteImage = byteArrayOutputStream.toByteArray();
                }

            } catch (Exception e) {}

            if (Titlee.isEmpty() == false && Notee.isEmpty() == false) {
                MainActivity.dataBase.UPDATE_ITEMS(Titlee, Notee, dateUpdate, byteImage, id);
                startActivity(new Intent(ActivityNotes.this, MainActivity.class));
            } else {
                Toast.makeText(ActivityNotes.this, "Invites you enter a title and a note. Please!", Toast.LENGTH_SHORT).show();
            }

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ( requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null ) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            iv_image.setImageBitmap(bitmap);
        }

        if ( requestCode == REQUEST_CODE_FILE && resultCode == RESULT_OK && data != null ) {

            Uri uri = data.getData();

            try {

                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                iv_image.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch ( requestCode ) {

            case REQUEST_CODE_CAMERA:

                if ( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CODE_CAMERA);
                } else {
                    Toast.makeText(ActivityNotes.this, "You have not given permission", Toast.LENGTH_SHORT).show();
                }

                break;

            case REQUEST_CODE_FILE:

                if ( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_CODE_FILE);
                } else {
                    Toast.makeText(ActivityNotes.this, "You have not given permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
