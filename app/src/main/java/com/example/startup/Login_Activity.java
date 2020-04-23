package com.example.startup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.startup.Model.users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login_Activity extends AppCompatActivity {
    private EditText InputNumber,InputPassword;
    private Button button;
    private  ProgressDialog Loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        button=(Button) findViewById(R.id.login_btn);

        InputPassword=(EditText) findViewById(R.id.password_input);
        InputNumber=(EditText) findViewById(R.id.phone_number_login);
        Loadingbar =new ProgressDialog(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });


    }

    private void loginUser() {
        String phone=InputNumber.getText().toString();
        String password=InputPassword.getText().toString();

          if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(this,"please enter your phone....", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"please enter your password....", Toast.LENGTH_SHORT).show();
        }
          else {
              Loadingbar.setTitle("Login Account");
              Loadingbar.setMessage("please wait while we see your login credencials");
              Loadingbar.setCanceledOnTouchOutside(false);
              Loadingbar.show();

              AllowAccessToAccount(phone,password);
          }
    }

    private void AllowAccessToAccount(final String phone, final String password) {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("users").child(phone).exists()){

                    users usersData=dataSnapshot.child("users").child(phone).getValue(users.class);

                    if(usersData.getPhone().equals(phone)){
                        if(usersData.getPassword().equals(password)){
                            Toast.makeText(Login_Activity.this, "logged in Successfully", Toast.LENGTH_SHORT).show();
                            Loadingbar.dismiss();

                            Intent intent = new Intent(Login_Activity.this, HomePage.class);
                            startActivity(intent);


                        }
                        else{
                            Toast.makeText(Login_Activity.this, "wrong password", Toast.LENGTH_SHORT).show();
                            Loadingbar.dismiss();
                        }
                    }


                }
                else{
                    Toast.makeText(Login_Activity.this, "account with this"+ phone+" no does exists", Toast.LENGTH_SHORT).show();
                    Loadingbar.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

    }
}
