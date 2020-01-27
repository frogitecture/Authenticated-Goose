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

public class DeleteAccountDialog extends DialogFragment {

    public interface DeleteAccountListener {
        void onDeleteAccount(String password);
    }

    public DeleteAccountListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_delete_account, null);
        final EditText passwordTxt = view.findViewById(R.id.password_txt);

        builder.setView(view)
                .setTitle("Delete account")
                .setMessage("Are you sure you want to delete your account? This action cannot be undone")
                .setPositiveButton("Delete Account", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String password = passwordTxt.getText().toString();
                        listener.onDeleteAccount(password);
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
            listener = (DeleteAccountListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement the listener you silly goose");
        }
    }
}
