package com.coder.zzq.smartshowdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.coder.zzq.smartshow.dialog.ClickListAdapter;
import com.coder.zzq.smartshow.toast.SmartToast;

import java.util.Arrays;


public class TestToastActivity extends AppCompatActivity {
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toast);
        mListView = findViewById(R.id.list_view);
        ClickListAdapter adapter = new ClickListAdapter();
        adapter.setItemCenter(true);
        adapter.setItems(Arrays.asList(
                getResources().getStringArray(R.array.test_plain_toast_items)
        ));
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        onShowClick(view);
                        break;
                    case 1:
                        onShowAnotherClick(view);
                        break;
                    case 2:
                        onShowAtTopClick(view);
                        break;
                    case 3:
                        onShowInCenterClick(view);
                        break;
                    case 4:
                        onShowAtLocationClick(view);
                        break;
                }
            }
        });
    }

    public void onNextPageClick(View view) {
        startActivity(new Intent(this, TestToastActivity.class));
    }


    public void onShowClick(View view) {
        SmartToast.show(R.string.apple);
    }

    public void onShowAnotherClick(View view) {
        SmartToast.show(R.string.mango);
    }


    public void onShowAtTopClick(View view) {
        SmartToast.showAtTop(R.string.banana);
    }

    public void onShowInCenterClick(View view) {
        SmartToast.showInCenter(R.string.orange);
    }

    public void onShowAtLocationClick(View view) {
        SmartToast.showAtLocation(R.string.litchi, Gravity.LEFT | Gravity.TOP, 10, 90);
    }

}
