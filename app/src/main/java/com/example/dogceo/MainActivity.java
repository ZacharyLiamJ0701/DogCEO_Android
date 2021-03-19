package com.example.dogceo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.icu.text.Transliterator;
import android.net.Uri;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    static ArrayList<String> genreList = new ArrayList<>();
    static ArrayList<String> pictureList = new ArrayList<>();
    static RequestQueue queue;
    static String apiUrl = "https://dog.ceo/api/breeds/list/all";
    JSONObject jsonObject = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);
        ParseString(apiUrl);
    }

    public void ParseString(String apiUrl) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        createGenreList(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }

    public void createGenreList(String val)
    {
        try
        {
            JSONObject obj = new JSONObject(val);
            JSONObject jsonarray = new JSONObject(obj.get("message").toString());
            Iterator x = jsonarray.keys();
            JSONArray jsonArray = new JSONArray();
            while (x.hasNext())
            {
                String key = (String) x.next();
                jsonArray.put(jsonarray.get(key));
                for (int i = 0; i<jsonArray.length();i++)
                {
                    JSONArray array = new JSONArray(jsonArray.get(i).toString());
                    if (array.length()>0)
                    {
                        for (int j = 0; j < array.length(); j++) {
                            genreList.add(key + " " + array.get(j));
                        }
                    }
                    jsonArray = new JSONArray(new ArrayList<String>());
                }
            }
            createPictureList();
        }
        catch (Throwable t)
        {
            Log.e("My App", "Could not parse malformed JSON: \"" + val + "\"");
        }
    }

    public void createPictureList()
    {
        String gen;
        for (int i = 0; i < genreList.size(); i++) {
            gen = genreList.get(i);

            String url = "https://dog.ceo/api/breed/" + gen.replace(" ","/") + "/images/random";

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response)
                        {
                            String pictureUrl = "";
                            response = response.replace("\\/","/");

                            try
                            {
                                jsonObject = new JSONObject(response);
                                pictureUrl = jsonObject.get("message").toString();
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();

                            }
                            pictureList.add(pictureUrl);
                            if (genreList.size() == pictureList.size())
                                defineRecyclerview();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            queue.add(stringRequest);
        }



    }

    public void defineRecyclerview()
    {

        recyclerView =  findViewById(R.id.dogRecyclerview);
        final ProductAdapter productAdapter = new ProductAdapter(this, Product.getData(genreList,pictureList));
        recyclerView.setAdapter(productAdapter);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        productAdapter.setOnItemClickListener(new ProductAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                /*OLD CODE BELOW.......First line of code will just display the name of the dog again once clicked......Second
                line of code will display the url where the user can go search*/

                /* FIRST LINE OF CODE
                    Toast.makeText(getApplicationContext(),productAdapter.getItem(position).getProductName(),Toast.LENGTH_LONG).show();
                 */

                /* SECOND LINE OF CODE
                    String url = "https://www.bing.com/search?q=" + productAdapter.getItem(position).getProductName();
                    Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();
                 */

                // User will be redirected to the internet

                Uri uri = Uri.parse("https://www.google.com/search?q=" + productAdapter.getItem(position).getProductName());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);


            }

            @Override
            public void onItemLongClick(int position, View v) {

                

            }
        });
    }


}