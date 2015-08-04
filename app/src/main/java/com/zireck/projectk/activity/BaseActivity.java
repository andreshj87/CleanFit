package com.zireck.projectk.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zireck.projectk.application.App;
import com.zireck.projectk.module.ActivityModule;

import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import dagger.ObjectGraph;

/**
 * Created by Zireck on 16/07/2015.
 */
public class BaseActivity extends AppCompatActivity {

    private ObjectGraph mActivityScopeGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        injectViews();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        injectViews();
    }

    public ObjectGraph plus(List<Object> modules) {
        return mActivityScopeGraph.plus(modules.toArray());
    }

    /**
     * Method used to resolve dependencies provided by Dagger modules. Inject an object to provide
     * every @Inject annotation contained.
     *
     * @param object to inject.
     */
    public void inject(Object object) {
        mActivityScopeGraph.inject(object);
    }

    /**
     * Get a list of Dagger modules with Activity scope needed to this Activity.
     *
     * @return modules with new dependencies to provide.
     */
    protected List<Object> getModules() {
        return new LinkedList<Object>();
    }

    /**
     * Create a new Dagger ObjectGraph to add new dependencies using a plus operation and inject the
     * declared one in the activity. This new graph will be destroyed once the activity lifecycle
     * finish.
     *
     * This is the key of how to use Activity scope dependency injection.
     */
    private void injectDependencies() {
        App app = (App) getApplication();
        List<Object> activityScopeModules = getModules();
        activityScopeModules.add(new ActivityModule(this));
        mActivityScopeGraph = app.plus(activityScopeModules);
        inject(this);
    }

    /**
     * Replace every field annotated with ButterKnife annotations like @InjectView with the proper
     * value.
     */
    private void injectViews() {
        ButterKnife.bind(this);
    }
}
