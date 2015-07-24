package com.zireck.projectk.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;

import com.zireck.projectk.R;

import butterknife.Bind;

/**
 * Created by Zireck on 24/07/2015.
 */
public class AddFoodFragment extends BaseFragment {

    @Bind(R.id.food_name_text_input_layout)
    TextInputLayout mFoodNameTextInputLayout;
    @Bind(R.id.food_name_edit_text)
    EditText mFoodNameEditText;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFoodNameTextInputLayout.setErrorEnabled(true);
        //mFoodNameTextInputLayout.setError("Incorrect food name!");
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_add_food;
    }
}
