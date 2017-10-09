package com.levir.armazenamentointerno;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btn_nova =  (Button) findViewById(R.id.btn_nova);
        btn_nova.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,1);
            }
        });
        Button btn_ver = (Button) findViewById(R.id.btn_ver);
        btn_ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileInputStream fotoArray = null;
                try {
                    fotoArray = openFileInput("foto");
                    BitmapFactory.Options options = new  BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    Bitmap image = BitmapFactory.decodeStream(fotoArray,null,options);

                    ImageView im = (ImageView) findViewById(R.id.imageView);
                    im.setImageBitmap(image);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && data != null && resultCode == RESULT_OK){
            FileOutputStream fotoArray;
            try {
                fotoArray = openFileOutput("foto", Context.MODE_PRIVATE);
                Bitmap bitmap =(Bitmap)data.getExtras().get("data");
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,fotoArray);
                fotoArray.flush();
                fotoArray.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
