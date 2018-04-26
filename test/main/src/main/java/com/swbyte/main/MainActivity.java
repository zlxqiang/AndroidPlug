package com.swbyte.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.plugin.source.PluginSourceManager;

public class MainActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);

        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this,
                new PermissionsResultAction() {
                    @Override
                    public void onGranted() {
                        // Proceed with initialization
                    }

                    @Override
                    public void onDenied(String permission) {
                        // Notify the user that you need all of the permissions
                    }
                });
        findViewById(R.id.btn).setOnClickListener(this);
        findViewById(R.id.activityClick).setOnClickListener(this);
        findViewById(R.id.contentPrivderClick).setOnClickListener(this);
        findViewById(R.id.contentPrivderClick).setOnClickListener(this);
        findViewById(R.id.serviceClick).setOnClickListener(this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                new PluginSourceManager(AppApplication.mContext);
                break;
            case R.id.activityClick:
                try {
                    startActivity(new Intent(getApplicationContext(), Class.forName("com.swbyte.activity.MainActivity")));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.contentPrivderClick:
                try {
                    startActivity(new Intent(getApplicationContext(), Class.forName("com.swbyte.contentprovider.MainActivity")));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.serviceClick:
                try {
                    startActivity(new Intent(getApplicationContext(), Class.forName("com.swbyte.service.MainActivity")));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;

        }
    }
}
