package com.hallucind.authenticatedgoosetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class MainActivity extends AppCompatActivity implements FirebaseListener{

    private final int PICK_IMAGE = 100;

    private FirebaseUser firebaseUser;

    private ImageView imageView;
    private TextView displayNameTxt;
    private TextView useridTxt;
    private TextView emailTxt;
    private TextView verifiedTxt;
    private TextView sendVerificationTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, AuthActivity.class));
        } else {
            firebaseUser = firebaseAuth.getCurrentUser();
        }

        imageView = findViewById(R.id.image);
        useridTxt = findViewById(R.id.uid_txt);
        displayNameTxt = findViewById(R.id.display_name_txt);
        emailTxt = findViewById(R.id.email_txt);
        verifiedTxt = findViewById(R.id.verified_txt);
        sendVerificationTxt = findViewById(R.id.send_verification_txt);

        Uri profilePicture = firebaseUser.getPhotoUrl();
        String uid = firebaseUser.getUid();
        String displayName = firebaseUser.getDisplayName();
        String email = firebaseUser.getEmail();
        String verifiedEmail = firebaseUser.isEmailVerified() ? "Yes" : "No";

        Glide.with(this).load(profilePicture).into(imageView);
        useridTxt.setText(uid);
        displayNameTxt.setText(displayName);
        emailTxt.setText(email);
        verifiedTxt.setText(verifiedEmail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Authenticated Goose");
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change_profile_picture:
                openGallery();
                break;

            case R.id.change_display_name:

                break;

            case R.id.change_email:

                break;

            case R.id.change_password:

                break;

            case R.id.sign_out:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(MainActivity.this, AuthActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
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
                            onChangedProfilePicture(uri);
                        }
                    }
                });
    }

    @Override
    public void onChangedProfilePicture(Uri uri) {
        Glide.with(this).load(uri).into(imageView);
    }
}
