package com.gmail.markdevw.wetube.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gmail.markdevw.wetube.R;
import com.gmail.markdevw.wetube.WeTubeApplication;

/**
 * Created by Mark on 3/24/2015.
 */

public class MainActivity extends Activity implements View.OnClickListener{

    Button searchButton;
    EditText searchBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchButton = (Button) findViewById(R.id.activity_main_search_button);
        searchBox = (EditText) findViewById(R.id.activity_main_search_video);

        searchButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String search = searchBox.getText().toString();
        if(search.isEmpty()){
            Toast.makeText(this, "Enter a search keyword first", Toast.LENGTH_LONG).show();
        }else{
            WeTubeApplication.getSharedDataSource().searchForVideos(search);
        }


    }
}
