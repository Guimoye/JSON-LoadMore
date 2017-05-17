package vn.edu.poly.json_loadmore.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by locntid on 4/21/16.
 */
public class MyToast {

    public static void showToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
