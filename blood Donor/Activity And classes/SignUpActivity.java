package gub.app.blooddonor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {


    public EditText emailTextview, passwordTextView,nameInput,mobileInput;
    Button Btn;
    private ProgressBar progressbar;
    FirebaseAuth mAuth;

    TextView textViewSignupIntoExistAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        mAuth = FirebaseAuth.getInstance();

        emailTextview = findViewById(R.id.emailID);
        passwordTextView = findViewById(R.id.passID);
        Btn = findViewById(R.id.registerBtnID);
        progressbar = findViewById(R.id.progressbar);
        textViewSignupIntoExistAccount = findViewById(R.id.existAccountID);
        nameInput=findViewById(R.id.inputTextName);
        mobileInput=findViewById(R.id.inputTextmobile);


        textViewSignupIntoExistAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
            }
        });
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });


    }

    public void registerNewUser() {

        progressbar.setVisibility(View.VISIBLE);

        String email, password,name,mobile;
        email = emailTextview.getText().toString();
        password = passwordTextView.getText().toString();
        name=nameInput.getText().toString();
        mobile=mobileInput.getText().toString();


        // Validations for input name and password
        if (TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getApplicationContext(), "Please enter email!!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!!", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(SignUpActivity.this, "Enter Your Full Name", Toast.LENGTH_SHORT).show();
            return ;
        }
        if (TextUtils.isEmpty(mobile)) {
            Toast.makeText(SignUpActivity.this, "Enter Your Full Name", Toast.LENGTH_SHORT).show();
            return ;
        }



        // create new user
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    UserData data = new UserData(name, mobile);
                    FirebaseDatabase.getInstance().getReference("UserData")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(data).
                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    //    progressbar GONE

                                    Toast.makeText(SignUpActivity.this, "Successful Registered", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                                    startActivity(intent);

                                    SharedPreferences sharedPreferences = getSharedPreferences("userD", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("name", name);
                                    editor.putString("mobile", mobile);
                                    editor.apply();


                                }
                            });


                    // hide the progress bar
                    progressbar.setVisibility(View.GONE);


                } else {

                    // Registration failed
                    Toast.makeText(getApplicationContext(), "Registration failed!!" + " Please try again later", Toast.LENGTH_LONG).show();

                    // hide the progress bar
                    progressbar.setVisibility(View.GONE);
                }
            }
        });
    }
}