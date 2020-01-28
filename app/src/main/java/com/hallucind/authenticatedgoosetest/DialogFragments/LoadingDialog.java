package com.hallucind.authenticatedgoosetest.DialogFragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hallucind.authenticatedgoosetest.R;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class LoadingDialog extends DialogFragment {

    private String message;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_loading, null);

        ImageView imageView = view.findViewById(R.id.image);
        TextView textView = view.findViewById(R.id.message);

        Glide.with(getActivity())
                .asGif()
                .load(R.drawable.loadinggoose)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(imageView);

        textView.setText(message);

        builder.setView(view);

        return builder.create();
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
