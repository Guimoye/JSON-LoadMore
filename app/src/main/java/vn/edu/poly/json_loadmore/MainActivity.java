package vn.edu.poly.json_loadmore;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import vn.edu.poly.json_loadmore.app.AppController;
import vn.edu.poly.json_loadmore.util.MyToast;

/**
 * Created by locntid on 4/21/16.
 */
public class MainActivity extends Activity {
    final String FOLDER = "https://c44d66307091b6c80a73dbc12bb43977fd218636.googledrive.com/host/0B8xMtEUiWTOxenFDV2ZEUThHVDg/"; /// server
//    String file_name = "1.json";
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter adapter;
    int index = 1;
    boolean loading;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        list = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem+visibleItemCount>= totalItemCount){
//                    MyToast.showToast(MainActivity.this,"listview ended");
                    if (index <= 5 && !loading){
                        loadJson(FOLDER+index+".json");
                        MyToast.showToast(MainActivity.this,"Load page "+ index);
                        index++;
                    }
                }
            }
        });


//        listView.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,
//                new String[]{"a","b","c"}));// -> test list view -> ok

        //loadJson(FOLDER+file_name);
    }

    private void loadJson(String url){
       dialog  = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();
//        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                // result
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //fail
//                MyToast.showToast(MainActivity.this,error.getMessage());
//                error.printStackTrace();
//            }
//        });
        loading = true;
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length();i++){
                    try {
                        String result = response.getString(i);
                        list.add(result);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter= new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,list);
                listView.setAdapter(adapter);
               // MyToast.showToast(MainActivity.this,list.get(0));//->ok
                loading = false;
                dialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyToast.showToast(MainActivity.this,error.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(req);
    }
}
