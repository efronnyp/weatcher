package com.levarech.weatcher.view;

import android.support.v7.app.AppCompatActivity;

import com.levarech.weatcher.WeatcherApplication;
import com.levarech.weatcher.internal.di.components.ApplicationComponent;

/**
 * Created by efronny on 7/7/17.
 * Base activity.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected ApplicationComponent getApplicationComponent() {
        return ((WeatcherApplication) getApplication()).getApplicationComponent();
    }
}
