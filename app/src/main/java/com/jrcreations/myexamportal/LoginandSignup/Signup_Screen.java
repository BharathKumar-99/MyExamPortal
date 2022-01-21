package com.jrcreations.myexamportal.LoginandSignup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jrcreations.myexamportal.LoginandSignup.Login_Screen;
import com.jrcreations.myexamportal.R;

import java.util.Objects;

public class Signup_Screen extends AppCompatActivity {
    TextView login;
    EditText email,password,name,phone,date,confirm_password;
    Button signup;
    boolean emailvalid,passvalid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);

        Objects.requireNonNull(getSupportActionBar()).hide();


        login=findViewById(R.id.loginret);
        email=findViewById(R.id.signupemail);
        password=findViewById(R.id.signuppassword);
        name=findViewById(R.id.signupname);
        phone=findViewById(R.id.signupphone);
        date=findViewById(R.id.date);
        confirm_password=findViewById(R.id.signupconfirmpassword);
        signup=findViewById(R.id.signup);

        signup.setOnClickListener(v->{
            String emailstr,passswordstr,namestr,phonestr,confirmstr;

            emailstr=email.getText().toString();
            passswordstr=password.getText().toString();
            namestr=name.getText().toString();
            phonestr=phone.getText().toString();
            confirmstr= confirm_password.getText().toString();

            emailvalid = validator(emailstr);
            passvalid=passwordmatch(passswordstr,confirmstr);

            if(emailvalid && passvalid){
                //save credential
            }

        });


        login.setOnClickListener(v->{
            startActivity(new Intent(this, Login_Screen.class));
            finishAffinity();
        });
    }

    protected boolean validator(String emailtest){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (emailtest.matches(emailPattern))
        {
            return true;
        }
        else
        {
            email.setError("Email Invalid");
            return false;
        }

    }
    protected boolean passwordmatch(String pass,String conpass){
        if(pass == conpass){
            return true;
        }
        else
            return false;
    }
}