package com.hallucind.authenticatedgoosetest.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hallucind.authenticatedgoosetest.AuthActivity;
import com.hallucind.authenticatedgoosetest.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {

    private TextView registerTxt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_login, container, false);

        registerTxt = parentView.findViewById(R.id.not_registered_txt);
        registerTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AuthActivity)getActivity()).changeActivity(new RegisterFragment());
            }
        });

        return parentView;
    }
}