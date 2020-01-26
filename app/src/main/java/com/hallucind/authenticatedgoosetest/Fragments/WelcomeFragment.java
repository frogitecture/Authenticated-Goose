package com.hallucind.authenticatedgoosetest.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.hallucind.authenticatedgoosetest.AuthActivity;
import com.hallucind.authenticatedgoosetest.MainActivity;
import com.hallucind.authenticatedgoosetest.ProfilePhotoFragment;
import com.hallucind.authenticatedgoosetest.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;

public class WelcomeFragment extends Fragment {

    private PictuteListener pictureListener;
    private final int PICK_IMAGE = 100;
    private FirebaseUser firebaseUser;

    private TextView displayNameTxt;
    private ImageView imageView;
    private Button skipBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_welcome, container, false);

        displayNameTxt = parentView.findViewById(R.id.display_name);
        imageView = parentView.findViewById(R.id.image);
        skipBtn = parentView.findViewById(R.id.skip_btn);

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            firebaseUser = firebaseAuth.getCurrentUser();
        }

        displayNameTxt.setText(firebaseUser.getDisplayName());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        pictureListener = new PictuteListener() {
            @Override
            public void onProfilePictureUpdated() {
                Uri uri = firebaseUser.getPhotoUrl();
                Glide.with(getActivity()).load(uri).into(imageView);
                skipBtn.setBackgroundColor(Color.parseColor("#2c8bff"));
                skipBtn.setText("done");
            }
        };

        return parentView;
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            final Uri imageUri = data.getData();
            updateUserProfilePicture(imageUri);
        }
    }

    private void updateUserProfilePicture(final Uri uri) {
        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();

        firebaseUser.updateProfile(profileChangeRequest)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            pictureListener.onProfilePictureUpdated();
                        }
                    }
                });
    }

    private interface PictuteListener {
        void onProfilePictureUpdated();
    }
}
