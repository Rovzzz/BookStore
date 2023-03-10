package com.example.bookstore;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Upd extends AppCompatActivity {

    ImageView imageView;
    EditText Name, Price, Compound;
    String img="";
    mask mask;

    Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        mask=getIntent().getParcelableExtra("BookStore");
        imageView=findViewById(R.id.image_base);

        Name=findViewById(R.id.UpName);
        Name.setText(mask.getName());
        Price=findViewById(R.id.UpPrice);
        Price.setText(Integer.toString(mask.getPrice()));
        imageView.setImageBitmap(getImgBitmap(mask.getImage()));

    }

    private Bitmap getImgBitmap(String encodedImg) {
        if(encodedImg!=null&& !encodedImg.equals("null")) {
            byte[] bytes = new byte[0];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                bytes = Base64.getDecoder().decode(encodedImg);
            }
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        else{
            return BitmapFactory.decodeResource(Upd.this.getResources(),
                    R.drawable.book);
        }

    }


    public void onClickChooseImage(View view)
    {
        getImage();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && data!= null && data.getData()!= null)
        {
            if(resultCode==RESULT_OK)
            {
                Log.d("MyLog","Image URI : "+data.getData());
                imageView.setImageURI(data.getData());
                Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                encodeImage(bitmap);

            }
        }
    }

    private void getImage()
    {
        Intent intentChooser= new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser,1);
    }

    private String encodeImage(Bitmap bitmap) {
        int prevW = 150;
        int prevH = bitmap.getHeight() * prevW / bitmap.getWidth();
        Bitmap b = Bitmap.createScaledBitmap(bitmap, prevW, prevH, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            img=Base64.getEncoder().encodeToString(bytes);
            return img;
        }
        return "";
    }
    public void Update_bt(View v)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(Upd.this);
        builder.setTitle("??????????????????")
                .setMessage("???? ?????????????? ?????? ???????????? ???????????????? ????????????")
                .setCancelable(false)
                .setPositiveButton("????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        putUpdate(img,Name.getText().toString(),Price.getText().toString());
                        new CountDownTimer(1000, 1000) {
                            public void onFinish() {
                                Next();
                            }

                            public void onTick(long millisUntilFinished) {

                            }
                        }.start();

                    }
                })
                .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    private void putUpdate(String image, String  name ,String price)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/ngknn/????????????????????/api/BookStores/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPIUpdate update = retrofit.create(RetrofitAPIUpdate.class);
        DataModal modal = new DataModal(image, name,Integer.parseInt(price));
        Call<DataModal> call = update.updateData(mask.getID(),modal);
        call.enqueue(new Callback<DataModal>() {
            @Override
            public void onResponse(Call<DataModal> call, Response<DataModal> response) {
                Toast.makeText(Upd.this, "???????????? ????????????????", Toast.LENGTH_SHORT).show();
                DataModal responseFromAPI = response.body();
            }

            @Override
            public void onFailure(Call<DataModal> call, Throwable t) {

            }
        });
    }

    public void Delete_bt(View v)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(Upd.this);
        builder.setTitle("??????????????")
                .setMessage("???? ?????????????? ?????? ???????????? ?????????????? ????????????")
                .setCancelable(false)
                .setPositiveButton("????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteDelete();
                        new CountDownTimer(1000, 1000) {
                            public void onFinish() {
                                Next();
                            }

                            public void onTick(long millisUntilFinished) {

                            }
                        }.start();

                    }
                })
                .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    public  void deleteDelete()
    {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/ngknn/????????????????????/api/BookStores/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPIDelete delete = retrofit.create(RetrofitAPIDelete.class);
        Call<DataModal> call = delete.deleteData(mask.getID());

        call.enqueue(new Callback<DataModal>() {
            @Override
            public void onResponse(Call<DataModal> call, Response<DataModal> response) {
                Toast.makeText(Upd.this, "???????????? ??????????????", Toast.LENGTH_SHORT).show();
                DataModal responseFromAPI = response.body();
            }

            @Override
            public void onFailure(Call<DataModal> call, Throwable t) {

            }
        });

    }
    public void Back_bt(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void Next()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}