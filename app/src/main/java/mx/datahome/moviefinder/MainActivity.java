package mx.datahome.moviefinder;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private ListView listView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
        editText = findViewById(R.id.text);
        final Button button = findViewById(R.id.button);
        listView = findViewById(R.id.list);

        button.setOnClickListener(this);

    }

    private void showResponse(SearchResponse searchResponse) {
        List arrayNames = getArrayNames(searchResponse);
        if(arrayNames.size()>0) {
            listView.setAdapter(new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1,arrayNames ));
        }else{
            editText.setError("Sin resultados");
        }
    }

    public List getArrayNames(SearchResponse searchResponse){
        List<Search> search = searchResponse.getSearch();
        List names = new ArrayList();
        if(search != null){
            for (Search movie:search
            ) {
                names.add(movie.getTitle());
            }
        }
        return names;
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button){
            Intent intent = new Intent(this,DetailActivity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.fab){
            searchMovies(editText.getText().toString());
        }
    }

    public void searchMovies(String textSearch){
        final Gson gson = new Gson();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://www.omdbapi.com/?s="+textSearch+"&page=1&apikey=dd22ce1a";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        textView.setText("La respuesta es: "+ response.substring(0,500));
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
