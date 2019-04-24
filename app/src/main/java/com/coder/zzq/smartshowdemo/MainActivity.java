package com.coder.zzq.smartshowdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.coder.zzq.smartshow.dialog.ClickListAdapter;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = findViewById(R.id.list_view);
        ClickListAdapter adapter = new ClickListAdapter();
        adapter.setItemCenter(true);
        adapter.setItems(Arrays.asList(
                new String[]{
                        "toast",
                        "type toast",
                        "snackbar",
                        "topbar",
                        "dialog"
                }
        ));
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        onToastClick(view);
                        break;
                    case 1:
                        onTypeToastClick(view);
                        break;
                    case 2:
                        onSnackbarClick(view);
                        break;
                    case 3:
                        onTopBarClick(view);
                        break;
                    case 4:
                        onDialogClick(view);
                        break;
                }
            }
        });
    }

    public void onToastClick(View view) {
        startActivity(new Intent(this, TestToastActivity.class));
    }

    public void onSnackbarClick(View view) {
        startActivity(new Intent(this, TestSnackbarActivity.class));
    }

    public void onTopBarClick(View view) {
        startActivity(new Intent(this, TestTopbarActivity.class));
    }

    public void onTypeToastClick(View view) {
        startActivity(new Intent(this, TestTypeToastActivity.class));
    }

    public void onDialogClick(View view) {
        startActivity(new Intent(this, TestDialogActivity.class));
    }
}
