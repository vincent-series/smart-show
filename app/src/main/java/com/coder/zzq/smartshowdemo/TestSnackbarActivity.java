package com.coder.zzq.smartshowdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.coder.zzq.smartshow.dialog.ClickListAdapter;
import com.coder.zzq.smartshow.snackbar.SmartSnackbar;
import com.coder.zzq.smartshow.toast.SmartToast;

import java.util.Arrays;


public class TestSnackbarActivity extends AppCompatActivity {
    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_snackbar);
        mListView = findViewById(R.id.list_view);
        ClickListAdapter adapter = new ClickListAdapter();
        adapter.setItemCenter(true);
        adapter.setItems(Arrays.asList(
                new String[]{
                        "short snackbar",
                        "long snackbar",
                        "indefinite snackbar"
                }
        ));
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        onShortClick(view);
                        break;
                    case 1:
                        onLongClick(view);
                        break;
                    case 2:
                        onIndefiniteClick(view);
                        break;
                }
            }
        });
    }

    public void onNextPageClick(View view) {
        startActivity(new Intent(this, TestSnackbarActivity.class));
    }

    public void onIndefiniteClick(View view) {
        SmartSnackbar.get(this).showIndefinite("为该库Star一下好么", "好的",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SmartToast.showInCenter("Thank you");
                    }
                });

    }

    public void onLongClick(View view) {
        SmartSnackbar.get(this).show(R.string.banana);
    }

    public void onShortClick(View view) {
        SmartSnackbar.get(this).show(R.string.apple);
    }


}
