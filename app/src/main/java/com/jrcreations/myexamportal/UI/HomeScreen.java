package com.jrcreations.myexamportal.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jrcreations.myexamportal.LoginandSignup.Login_Screen;
import com.jrcreations.myexamportal.R;
import com.jrcreations.myexamportal.UI.Home.Btn;
import com.jrcreations.myexamportal.UI.Home.RecycelviewAdapter;
import com.jrcreations.myexamportal.UI.Home.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeScreen extends AppCompatActivity {
    TextView name;
    private List<Btn> movieList;
    private List<Users> usr;
    RecyclerView recyclerView;
    private RecycelviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        movieList = new ArrayList<Btn>();
        usr = new ArrayList<>();
        name = findViewById(R.id.Name);
        recyclerView = findViewById(R.id.homerecycleview);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String id = user.getUid();

        if (user == null) {
            startActivity(new Intent(this, Login_Screen.class));
        }
        getrow();
        Log.d("TAG", "onCreate:" + id);

       getname(id);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecycelviewAdapter(getApplicationContext(), movieList);
        recyclerView.setAdapter(adapter);
    }


    public void getrow() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String url = "https://myexamportals.com/Php/gethome.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST,url,null, response -> {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject jsonObject = response.getJSONObject(i);

                    Btn movie = new Btn();
                    movie.setName(jsonObject.getString("name"));
                    movieList.add(movie);
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            adapter.notifyDataSetChanged();

        }, error -> Log.d("TAG", error.toString()));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }


    public void getname(String id) {


            //getting the progressbar
            String url = "https://myexamportals.com/Php/getuser.php";
            //creating a string request to send request to the url
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {

                                JSONArray tutorialsArray = new JSONArray(response);


                                for (int i = 0; i < tutorialsArray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject tutorialsObject = tutorialsArray.getJSONObject(i);
                                    Users users = new Users();
                                    users.setUsername(tutorialsObject.getString("name"));
                                    //creating a tutorial object and giving them the values from json object
                                    Log.d("TAG", "onResponse: " + users.getUsername());
                                  name.setText(users.getUsername());

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    error -> {
                        //displaying the error in toast if occur
                        Log.d("TAG", "onErrorResponse: " + error);
                        error.printStackTrace();
                    }) {
                @Override
                public Map getParams() {
                    Map params = new HashMap();

                    params.put("id", id);

                    return params;
                }
            };

            //creating a request queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            //adding the string request to request queue
            requestQueue.add(stringRequest);
        }
    }

