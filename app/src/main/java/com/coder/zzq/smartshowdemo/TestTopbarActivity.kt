package com.coder.zzq.smartshowdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.coder.vincent.smart_toast.SmartToast;
import com.coder.zzq.smartshow.dialog.ClickListAdapter;
import com.coder.zzq.smartshow.topbar.SmartTopbar;

import java.util.Arrays;

public class TestTopbarActivity extends AppCompatActivity {
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_topbar);
        mListView = findViewById(R.id.list_view);
        ClickListAdapter adapter = new ClickListAdapter();
        adapter.setItemCenter(true);
        adapter.setItems(Arrays.asList(new String[]{
                "short topbar",
                "long topbar",
                "indefinite topbar"
        }));

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
                        onIndefinite(view);
                        break;
                }
            }
        });
    }

    public void onShortClick(View view) {
        SmartTopbar.get(this).show(R.string.apple);
    }

    public void onLongClick(View view) {
        SmartTopbar.get(this).showLong(R.string.banana);
    }

    public void onIndefinite(View view) {
        SmartTopbar.get(this).showIndefinite("为该库Star一下好么", "好的", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmartToast.classic().showAtTop("Thank you");
            }
        });

    }

    public void onNextPageClick(View view) {
        startActivity(new Intent(this, TestTopbarActivity.class));
    }
}
