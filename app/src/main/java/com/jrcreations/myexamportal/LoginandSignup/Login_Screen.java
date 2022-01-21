package com.jrcreations.myexamportal.LoginandSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jrcreations.myexamportal.R;
import com.jrcreations.myexamportal.UI.HomeScreen;

import java.util.Objects;

public class Login_Screen extends AppCompatActivity {
    TextView registernow;
    EditText email, password;
    Button login;
    private FirebaseAuth mAuth;
    boolean emailvalid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        Objects.requireNonNull(getSupportActionBar()).hide();
        mAuth = FirebaseAuth.getInstance();
        registernow = findViewById(R.id.register);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);


        //login btn
        login.setOnClickListener(v -> {
            String emailstr, pass;

            emailstr = email.getText().toString();
            pass = password.getText().toString();
            emailvalid = validator(emailstr);
            if(pass.length()<=5){
                password.setError("Password is too short");
            }
            if (emailvalid && pass.length() <= 6) {
              signIn(emailstr,pass);
              email.setText("");
              password.setText("");
            }
        });


        //register btn
        registernow.setOnClickListener(v -> startActivity(new Intent(this, Signup_Screen.class)));


    }

    protected boolean validator(String emailtest) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (emailtest.matches(emailPattern)) {
            Log.d("Tag1","valid");
            return true;

        } else {
            email.setError("Email Invalid");
            return false;
        }

    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, (OnCompleteListener<AuthResult>) task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        startActivity(new Intent(this, HomeScreen.class));
                        finishAffinity();

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.d("TAG1", "signInWithEmail:failure", task.getException());
                        Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();

                    }
                });


    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(this, HomeScreen.class));
            finishAffinity();
        }
    }


}
