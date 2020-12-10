package com.example.duan1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1.Activity.HomeUserActivity;
import com.example.duan1.ActivityAdmin.HomeAdminActivity;
import com.example.duan1.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin, btnRegister;
    EditText edtUsername, edtPassword;
    TextInputLayout textInputLayout;
    String username, password;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    CheckBox checkBox;

    public static void saveObjectToSharedPreference(Context context, String preferenceFileName, String serializedObjectKey, Object object) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName, 0);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        final Gson gson = new Gson();
        String serializedObject = gson.toJson(object);
        sharedPreferencesEditor.putString(serializedObjectKey, serializedObject);
        sharedPreferencesEditor.apply();
    }

    public static <GenericClass> GenericClass getSavedObjectFromPreference(Context context, String preferenceFileName, String preferenceKey, Class<GenericClass> classType) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName, 0);
        if (sharedPreferences.contains(preferenceKey)) {
            final Gson gson = new Gson();
            return gson.fromJson(sharedPreferences.getString(preferenceKey, ""), classType);
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        edtUsername = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        textInputLayout = findViewById(R.id.pass);
        checkBox = findViewById(R.id.checkbox);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Date date = new Date();
        Log.e("Date format", "onCreate: " + simpleDateFormat.format(date));
        Log.e("Time stamp", "onCreate: " + date.getTime());
        Log.e("Reversed", "onCreate: " + simpleDateFormat.format(new Date(date.getTime())));

        checkLogin();
        //LoadLogin();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });
/*
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString();
                password = textInputLayout.getEditText().getText().toString();
                if (username.equalsIgnoreCase("Admin") && password.equals("admin")) {
                    Intent i = new Intent(LoginActivity.this, HomeAdmin.class);
                    startActivity(i);
                    finish();
                } else {
                    Login();
                }
            }
        });*/
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = edtUsername.getText().toString();
                String password = textInputLayout.getEditText().getText().toString();
                firebaseAuth.signInWithEmailAndPassword(username, password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                saveUser(username);
                            }

                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "Failed... please try again!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void saveUser(String email) {
        databaseReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    User user = item.getValue(User.class);
                    user.setId(item.getKey());
                    Log.e("ZZ", "onDataChange: " + user.toString());
                    saveObjectToSharedPreference(getApplicationContext(), "User", "User", user);
                }
                checkUser();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void checkLogin() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            checkUser();
        } else {
            loadAccount();
        }
    }

    private void checkUser() {
        User user = getSavedObjectFromPreference(this, "User", "User", User.class);
        if (user != null) {
            if (user.getRole().equalsIgnoreCase("customer")) {
                if (user.getStatus().equalsIgnoreCase("blocked")) {
                    Toast.makeText(getApplicationContext(), "Tài khoản đã bị khóa.\nVui lòng liên hệ admin@kingfood.com để được hỗ trợ", Toast.LENGTH_SHORT).show();

                    FirebaseAuth.getInstance().signOut();
                } else {
                    Intent i = new Intent(LoginActivity.this, HomeUserActivity.class);
                    startActivity(i);
                    saveAccount();
                    finish();
                }
            } else if (user.getRole().equalsIgnoreCase("admin")) {
                Intent i = new Intent(LoginActivity.this, HomeAdminActivity.class);
                startActivity(i);
                saveAccount();
                finish();
            }
        } else {
            Toast.makeText(this, "Error... Please use another account!", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
        }
    }

    private void loadAccount() {
        SharedPreferences pref = getSharedPreferences("infoUser.dat", MODE_PRIVATE);
        boolean check = pref.getBoolean("check", false);
        if (check) {
            edtUsername.setText(pref.getString("username", ""));
            edtPassword.setText(pref.getString("password", ""));
            checkBox.setChecked(check);
        }
    }

    private void saveAccount() {
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        SharedPreferences preferences = getSharedPreferences("infoUser.dat", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (checkBox.isChecked()) {
            editor.putString("username", username);
            editor.putString("password", password);
            editor.putBoolean("check", true);
        } else {
            editor.clear();
        }
        editor.commit();
    }

    public void Login() {
        firebaseAuth = FirebaseAuth.getInstance();
        username = edtUsername.getText().toString();
        password = textInputLayout.getEditText().getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            edtUsername.setError("Don't Empty Email or Password");
        } else {
            edtUsername.setError(null);
            final boolean check = checkBox.isChecked();
            firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        SaveUser(username, password, check);
                        Intent i2 = new Intent(LoginActivity.this, HomeUserActivity.class);
                        startActivity(i2);
                        finish();
                        Toast.makeText(LoginActivity.this, "Login Succesfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }

    private void SaveUser(String username, String password, boolean check) {
        SharedPreferences preferences = getSharedPreferences("infoUser.dat", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (check) {
            editor.putString("username", username);
            editor.putString("password", password);
            editor.putBoolean("check", check);
        } else {
            editor.clear();
        }
        editor.commit();
    }

    private void LoadLogin() {
        SharedPreferences pref = getSharedPreferences("infoUser.dat", MODE_PRIVATE);
        boolean check = pref.getBoolean("check", false);
        if (check) {
            edtUsername.setText(pref.getString("username", ""));
            edtPassword.setText(pref.getString("password", ""));
            checkBox.setChecked(check);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}