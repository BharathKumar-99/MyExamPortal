package com.jrcreations.myexamportal.LoginandSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.jrcreations.myexamportal.LoginandSignup.Login_Screen;
import com.jrcreations.myexamportal.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Signup_Screen extends AppCompatActivity {
    TextView login;
    EditText email,password,name,phone,date,confirm_password;
    final Calendar myCalendar= Calendar.getInstance();
    Button signup;
    private FirebaseAuth mAuth;
    boolean emailvalid,passvalid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);

        Objects.requireNonNull(getSupportActionBar()).hide();
        mAuth = FirebaseAuth.getInstance();

        login=findViewById(R.id.loginret);
        email=findViewById(R.id.signupemail);
        password=findViewById(R.id.signuppassword);
        name=findViewById(R.id.signupname);
        phone=findViewById(R.id.signupphone);
        date=findViewById(R.id.date);
        confirm_password=findViewById(R.id.signupconfirmpassword);
        signup=findViewById(R.id.signup);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateandTime = sdf.format(new Date());
        date.setText(currentDateandTime);



        date.setOnClickListener(v->{
            new DatePickerDialog(this,date3,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH)
                    ,myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });




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
                createAccount(emailstr,passswordstr);

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

        if(pass.equals(conpass) && pass.length()>=6){
            return true;
        }
        else{
            password.setError("Password Mismatch");
            return false;
        }

    }

    private void createAccount(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "createUserWithEmail:success");
                        Toast.makeText(this, "Successfully Created", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.d("TAG", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(Signup_Screen.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();

                    }
                });
}

    DatePickerDialog.OnDateSetListener date3 = (view, year, month, day) -> {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH,month);
        myCalendar.set(Calendar.DAY_OF_MONTH,day);
        updateLabel();
    };
    private void updateLabel(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        date.setText(dateFormat.format(myCalendar.getTime()));
    }
}