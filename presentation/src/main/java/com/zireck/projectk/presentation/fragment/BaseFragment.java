package com.zireck.projectk.presentation.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zireck.projectk.presentation.activity.BaseActivity;

import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import dagger.ObjectGraph;

/**
 * Created by Zireck on 16/07/2015.
 */
public abstract class BaseFragment extends Fragment {

    private ObjectGraph mFragmentScopeGraph;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        injectDependencies();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getFragmentLayout(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        injectViews(view);
    }

    /**
     * Every fragment has to inflate a layout in the onCreateView method. We have added this method to
     * avoid duplicate all the inflate code in every fragment. You only have to return the layout to
     * inflate in this method when extends BaseFragment.
     */
    protected abstract int getFragmentLayout();

    /**
     * Method used to resolve dependencies provided by Dagger modules. Inject an object to provide
     * every @Inject annotation contained.
     *
     * @param object to inject.
     */
    public void inject(Object object) {
        mFragmentScopeGraph.inject(object);
    }

    /**
     * Get a list of Dagger modules with Fragment scope needed to this Fragment.
     *
     * @return modules with new dependencies to provide.
     */
    protected List<Object> getModules() {
        return new LinkedList<Object>();
    }

    /**
     * Create a new Dagger ObjectGraph to add new dependencies using a plus operation and inject the
     * declared one in the fragment. This new graph will be destroyed once the fragment lifecycle
     * finish.
     *
     * This is the key of how to use Fragment scope dependency injection.
     */
    private void injectDependencies() {
        List<Object> fragmentScopeModules = getModules();
        if (fragmentScopeModules != null && fragmentScopeModules.size() > 0) {
            BaseActivity baseActivity = (BaseActivity) getActivity();
            mFragmentScopeGraph = baseActivity.plus(fragmentScopeModules);
            inject(this);
        }
    }

    /**
     * Replace every field annotated with ButterKnife annotations like @InjectView with the proper
     * value.
     *
     * @param view to extract each widget injected in the fragment.
     */
    private void injectViews(final View view) {
        ButterKnife.bind(this, view);
    }
}
