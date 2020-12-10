package com.example.duan1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    EditText edtEmail;
    TextInputLayout edtPassword, edtRePassword;
    FirebaseAuth firebaseAuth;
    Button btnSignUp, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.pass);
        edtRePassword = findViewById(R.id.RetypePassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnCancel = findViewById(R.id.btnCancel);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });


    }

    public void SignUp() {
        firebaseAuth = FirebaseAuth.getInstance();
        final String Email = edtEmail.getText().toString();
        String Password = edtPassword.getEditText().getText().toString();
        String rePassword = edtRePassword.getEditText().getText().toString();
        if (Email.isEmpty()) {
            edtEmail.setError("Don't Empty Email");
        } else if (Password.isEmpty()) {
            edtPassword.setError("Don't Empty Password");
        } else if (!rePassword.equals(Password)) {
            edtRePassword.setError("Password And RePassword Doesn't Match");
        } else {
            if (Password.equals(rePassword)) {
                edtEmail.setError(null);
                edtRePassword.setError(null);
                edtPassword.setError(null);
                firebaseAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //FirebaseUser user = firebaseAuth.getCurrentUser();
                            createUser(Email);
                            Toast.makeText(SignUpActivity.this, "Sign-Up Succesfully", Toast.LENGTH_SHORT).show();
                        } else {

                        }
                    }

                });
            }
        }
    }

    private void createUser(String email) {
        User user = new User("customer", email, "", "", "", "", "active");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
        databaseReference.push().setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SignUpActivity.this, "Successfully!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}