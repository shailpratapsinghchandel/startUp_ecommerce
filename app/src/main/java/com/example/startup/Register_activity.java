package com.example.startup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Register_activity extends AppCompatActivity {

    private Button CreateAccountButton;
    private EditText InputName, InputPhoneNo, InputPassword;
    private ProgressDialog Loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);
        CreateAccountButton=(Button) findViewById(R.id.create_account_Button);
        InputName=(EditText) findViewById(R.id.Name_Input);
        InputPassword=(EditText) findViewById(R.id.password_input);
        InputPhoneNo=(EditText) findViewById(R.id.phone_number_login);
        Loadingbar =new ProgressDialog(this);



        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               CreateAccount();
            }
        });
    }

    private void CreateAccount() {
        String Name=InputName.getText().toString();
        String phone=InputPhoneNo.getText().toString();
        String password=InputPassword.getText().toString();

        if(TextUtils.isEmpty(Name))
        {
            Toast.makeText(this,"please enter your name....", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(this,"please enter your phone....", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"please enter your password....", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Loadingbar.setTitle("create Account");
            Loadingbar.setMessage("please wait while we see your login credencials");
            Loadingbar.setCanceledOnTouchOutside(false);
            Loadingbar.show();
            
            validatephoneno(Name,phone,password);

        }


    }

    private void validatephoneno( final String name,  final String phone,final String password) {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren())
                {
                    if (!(data.child(phone).exists())) {
                        HashMap<String, Object> userDataMap = new HashMap<>();
                        userDataMap.put("phone", phone);
                        userDataMap.put("password", password);
                        userDataMap.put("name", name);
                        RootRef.child("users").child(phone).updateChildren(userDataMap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Register_activity.this, "Congratulation , your account has been created", Toast.LENGTH_SHORT).show();
                                            Loadingbar.dismiss();
                                            Intent intent = new Intent(Register_activity.this, Login_Activity.class);
                                            startActivity(intent);
                                        } else {
                                            Loadingbar.dismiss();
                                            Toast.makeText(Register_activity.this, "Network error! Try again", Toast.LENGTH_SHORT).show();
                                        }


                                    }
                                });

                    }
                    else {
                        Toast.makeText(Register_activity.this, "this" + phone + " already exists", Toast.LENGTH_SHORT).show();
                        Loadingbar.dismiss();
                        Toast.makeText(Register_activity.this, "please try again using another phone no", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register_activity.this, MainActivity.class);
                        startActivity(intent);
                    }
            }
        }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
