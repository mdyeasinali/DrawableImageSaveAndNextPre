package com.yeasin.pc.drawableimage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int currentPosition = 0;
    private ImageView backgroundPreview;
    Bitmap bitmap;
    OutputStream output;


    private static final List<Integer> backgrounds = new ArrayList<Integer>();
    private static final int TOTAL_IMAGES;
    static {
        backgrounds.add(R.drawable.image);
        backgrounds.add(R.drawable.img);
        backgrounds.add(R.drawable.imga);
        backgrounds.add(R.drawable.imags);
        TOTAL_IMAGES = (backgrounds.size() - 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        backgroundPreview = (ImageView) findViewById(R.id.imageView1);
        // Set the default image to be shown to start with
        changePreviewImage(currentPosition);


    }

    public void gotoPreviousImage(View v) {
        int positionToMoveTo = currentPosition;
        positionToMoveTo--;
        if(positionToMoveTo < 0){
            positionToMoveTo = TOTAL_IMAGES;
        }
        changePreviewImage(positionToMoveTo);
    }

    public void save(View v) {
        Bitmap bm = BitmapFactory.decodeResource(getResources(),R.drawable.img);
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File myDir = new File(root + "/saved_img");
        myDir.mkdirs();
        String fname = "Images-"+ 0 +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream outStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) { throw new RuntimeException(e); }

        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "image/png");
        Log.e("dsfsd", String.valueOf(Uri.fromFile(file)));
        startActivity(intent);
    }



    public void gotoNextImage(View v) {
        int positionToMoveTo = currentPosition;
        positionToMoveTo++;
        if(currentPosition == TOTAL_IMAGES){
            positionToMoveTo = 0;
        }
        changePreviewImage(positionToMoveTo);
    }

    public void changePreviewImage(int pos) {
        currentPosition = pos;
        backgroundPreview.setImageResource(backgrounds.get(pos));
        Log.d("Main", "Current position: "+backgrounds.get(pos));

    }
}
