package com.hallucind.authenticatedgoosetest.DialogFragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hallucind.authenticatedgoosetest.R;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class ChangePasswordDialog extends DialogFragment {

    public interface ChangePasswordListener {
        void onChangePassword(String oldPassword, String newPassword);
    }

    public ChangePasswordListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_change_password, null);
        final EditText oldPasswordTxt = view.findViewById(R.id.old_password_txt);
        final EditText newPasswordTxt = view.findViewById(R.id.new_password_txt);
        final EditText newPasswordAgainTxt = view.findViewById(R.id.new_password_again_txt);

        builder.setView(view)
                .setTitle("Change Display Name")
                .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String oldPassword = oldPasswordTxt.getText().toString();
                        String newPassword = newPasswordTxt.getText().toString();
                        String newPasswordAgain = newPasswordAgainTxt.getText().toString();

                        if (newPassword.equals(newPasswordAgain)) {
                            listener.onChangePassword(oldPassword, newPassword);
                        } else {
                            Toast.makeText(getActivity(), "Passwords don't match", Toast.LENGTH_LONG).show();
                        }
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
            listener = (ChangePasswordListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement the listener you silly goose");
        }
    }
}
