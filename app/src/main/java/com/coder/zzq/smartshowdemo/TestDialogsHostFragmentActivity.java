package com.coder.zzq.smartshowdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.coder.zzq.smartshow.dialog.NotificationDialog;
import com.coder.zzq.smartshow.toast.SmartToast;

public class TestDialogsHostFragmentActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView mListView;
    private MyFragment mMyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_dialogs_host_fragment);
        mListView = findViewById(R.id.list_view);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                onSetFragmentNormal();
                break;
            case 1:
                onHideFragment();
                break;
            case 2:
                onDetachFragment();
                break;
            case 3:
                onRemoveFragment();
                break;
            case 4:
                onUserVisibleHintFalseFragment();
                break;
        }
    }

    private void onUserVisibleHintFalseFragment() {
        mMyFragment = resetFragment();
        mMyFragment.setUserVisibleHint(false);
    }

    private void onRemoveFragment() {
        mMyFragment = resetFragment();
        getSupportFragmentManager().beginTransaction()
                .remove(mMyFragment)
                .commit();
    }

    private void onDetachFragment() {
        mMyFragment = resetFragment();
        getSupportFragmentManager().beginTransaction()
                .detach(mMyFragment)
                .commit();
    }

    private void onHideFragment() {
        mMyFragment = resetFragment();
        getSupportFragmentManager().beginTransaction()
                .hide(mMyFragment)
                .commit();
    }


    private void onSetFragmentNormal() {
        mMyFragment = resetFragment();
    }

    private MyFragment resetFragment() {
        MyFragment myFragment = (MyFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (myFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .remove(myFragment)
                    .commit();
        }
        myFragment = new MyFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, myFragment)
                .commit();

        return myFragment;
    }

    public void onShowDialog(View view) {
        new NotificationDialog()
                .message("正常显示Dialog")
                .showInFragment(mMyFragment);
    }
}
