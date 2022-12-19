package com.example.bookstore;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Adapter pAdapter;
    private List<mask> ListBook=new ArrayList<>();
    Spinner spinnerFilter;
    String [] Filter={"Без фильтрации","По возрастанию","По убыванию"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView drinkView=findViewById(R.id.BD);
        pAdapter=new Adapter(MainActivity.this,ListBook);
        drinkView.setAdapter(pAdapter);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Filter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFilter=findViewById(R.id.filter);
        spinnerFilter.setAdapter(adapter);



        new GetBook().execute();
    }
    private class GetBook extends AsyncTask<Void,Void,String>
    {


        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL ( "https://ngknn.ru:5001/ngknn/ЗлобиновРС/api/BookStores");
                HttpURLConnection connection=(HttpURLConnection) url.openConnection();

                BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result=new StringBuilder();
                String line="";

                while ((line=reader.readLine())!=null){
                    result.append(line);
                }
                return result.toString();
            }
            catch (Exception exception)
            {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                {
                    JSONArray tempArray= new JSONArray(s);
                    for (int i=0;i<tempArray.length();i++)
                    {
                        JSONObject productJson=tempArray.getJSONObject(i);
                        mask tempBookStore = new mask(
                                productJson.getInt("id"),
                                productJson.getString("name_book"),
                                productJson.getInt("price"),
                                productJson.getString("picture")
                        );
                        ListBook.add(tempBookStore);
                        pAdapter.notifyDataSetInvalidated();
                    }
                }
            }
            catch (Exception ignored)
            {

            }
        }
    }

    public void Add_bt(View v)
    {
        Intent intent = new Intent(this, ADD.class);
        startActivity(intent);
    }
}