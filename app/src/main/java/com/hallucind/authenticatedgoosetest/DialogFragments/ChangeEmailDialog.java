package com.hallucind.authenticatedgoosetest.DialogFragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.hallucind.authenticatedgoosetest.R;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class ChangeEmailDialog extends DialogFragment {

    public interface ChangeEmailListener {
        void onChangeEmail(String displayName, String password);
    }

    public ChangeEmailListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_change_email, null);
        final EditText emailTxt = view.findViewById(R.id.email_txt);
        final EditText passwordTxt = view.findViewById(R.id.password_txt);

        builder.setView(view)
                .setTitle("Change Display Name")
                .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String newEmail = emailTxt.getText().toString();
                        String password = passwordTxt.getText().toString();
                        listener.onChangeEmail(newEmail, password);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ChangeEmailListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement the listener you silly goose");
        }
    }
}
