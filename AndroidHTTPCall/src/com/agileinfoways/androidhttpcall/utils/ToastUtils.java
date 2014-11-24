


package com.agileinfoways.androidhttpcall.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.agileinfoways.androidhttpcall.R;

/**
 * A custom Toast class to toast a message in application, with custom layout. 
 */
public class ToastUtils extends Toast {

    Context context;

    public ToastUtils(Context context) {
        super(context);
        this.context = context;
    }

     public void showToast(String message) {
      
        LayoutInflater myInflator =(LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View myLayout = myInflator.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        TextView text = (TextView) myLayout.findViewById(R.id.txtToastMessage);
        text.setText(message);

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(myLayout);
        toast.show();
    }

    private static ViewGroup findViewById(int toastLayoutRoot) {

        return null;
    }

}