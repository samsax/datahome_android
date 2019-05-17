package mx.datahome.moviefinder;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import mx.datahome.moviefinder.pojo.Search;
import mx.datahome.moviefinder.pojo.SearchResponse;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText editText;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private int page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
        editText = findViewById(R.id.text);
        final Button button = findViewById(R.id.button);
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        button.setOnClickListener(this);

    }

    private void showResponse(SearchResponse searchResponse) {
        if(searchResponse.getSearch()!=null) {
            mAdapter = new MyAdapter(searchResponse);
            recyclerView.setAdapter(mAdapter);
        }else{
            editText.setError("Sin resultados");
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button){
            page++;
            searchMovies(editText.getText().toString(), page);
        }else if(v.getId()==R.id.fab){
            page = 1;
            searchMovies(editText.getText().toString(), page);
        }
    }

    public void searchMovies(String textSearch, int page){
        final Gson gson = new Gson();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://www.omdbapi.com/?s="+textSearch+"&page="+page+"&apikey=dd22ce1a";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        SearchResponse searchResponse = gson.fromJson(response, SearchResponse.class);
                        showResponse(searchResponse);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                editText.setError("Error en la respuesta");
            }
        });
        queue.add(stringRequest);
    }
}
