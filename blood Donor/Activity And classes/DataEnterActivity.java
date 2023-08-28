package gub.app.blooddonor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.util.Calendar;

public class DataEnterActivity extends AppCompatActivity {

    Button button_Insert,dateButton;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    private TextView dateView;
    public  String blood_group;
    EditText name,mobile,address;
    private Spinner dropdown;
    DatabaseReference databaseUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_enter);

        dateView = (TextView) findViewById(R.id.textView3);
        dropdown = findViewById(R.id.bloodGroupID);
        button_Insert=findViewById(R.id.insert_buttonID);
        name=findViewById(R.id.nameID);
        mobile=findViewById(R.id.mobile_umberID);
        dateButton=findViewById(R.id.button1);
        address=findViewById(R.id.address_ID);



        //create a list of items for the spinner.
        String[] items = new String[]{"(A+)", "(A-)", "(B+)", "(B-)", "(O+)", "(O-)", "(AB+)", "(AB-)"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);


        blood_group = dropdown.getSelectedItem().toString();


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        databaseUser= FirebaseDatabase.getInstance().getReference();


        SharedPreferences sharedPreferences = getSharedPreferences("userD", MODE_PRIVATE);
        String nameEU = sharedPreferences.getString("name", "");
        String mobileEU = sharedPreferences.getString("mobile", "");

        name.setText(nameEU);
        mobile.setText(mobileEU);




        button_Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertData();

            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(view);
            }
        });

    }



    private void InsertData() {

        String donorName=name.getText().toString();
        String donorNumber=mobile.getText().toString();
        String donorBloodGroup = dropdown.getSelectedItem().toString();
        String donor_Address=address.getText().toString();
        String lastDonation= showDate(year, month+1, day);
        String id=databaseUser.push().getKey();




        Donor donor=new Donor(donorName,donorNumber,donorBloodGroup,lastDonation,donor_Address);
        databaseUser.child("donors").child(id).setValue(donor)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Data insert Successful",Toast.LENGTH_SHORT).show();

                            //Clean edittext After submit data
                            name.setText("");
                            mobile.setText("");
                            address.setText("");



                        }else
                        {
                            Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();

                        }

                    }
                });






    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    public String showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
        String date = day+" / "+month+" / "+year;

        return date;
    }
}