package com.ntsua.thelife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {
    

    String TAG = "Facebook";
    CallbackManager callbackManager;
    LoginButton loginButton;
    Button btnShow;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnShow = findViewById(R.id.buttonShow);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProfile();
            }
        });
        // Initialize Facebook Login button
        callbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.login_button);
        //loginButton.setReadPermissions("email");
        loginButton.setPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            //FirebaseUser user = auth.getCurrentUser();
                            //updateUI(user);
                            showProfile();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

    private void showProfile() {
//        FirebaseUser user = auth.getCurrentUser();
//        //Toast.makeText(this, user.getDisplayName(), Toast.LENGTH_SHORT).show();
//        //startActivity(new Intent(this, MainActivity.class));
//        DatabaseReference data = FirebaseDatabase.getInstance().getReference();
//        data.child("name").setValue("NTSuaaaaaaaa");
//
//
//        //Toast.makeText(this, data.getRoot().toString(), Toast.LENGTH_SHORT).show();
//
//        data.child("name").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                Toast.makeText(LoginActivity.this, snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });
        startActivity(new Intent(this, MainActivity.class));

    }

    void gotoGame()
    {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() != null)
        {
            //Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
//            DatabaseReference data = FirebaseDatabase.getInstance().getReference();
//            data.child("name").setValue("NTSua");
//            Toast.makeText(this, "Set Data", Toast.LENGTH_SHORT).show();
            //showProfile();
            gotoGame();
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.boy);
//
//            ShareButton btn = findViewById(R.id.buttonShareLogin);
//            SharePhoto photo = new  SharePhoto.Builder()
//                    .setBitmap(bitmap)
//                    .build();
//
//            SharePhotoContent sharePhotoContent = new SharePhotoContent.Builder()
//                    .addPhoto(photo)
//                    .build();
//
//            btn.setShareContent(sharePhotoContent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}