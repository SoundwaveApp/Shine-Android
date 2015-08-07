package com.soundwave.sunshine;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.soundwave.shine.LogMode;
import com.soundwave.shine.Registration;
import com.soundwave.shine.Shine;
import com.soundwave.sunshine.api.ApiClient;
import com.soundwave.sunshine.model.User;

import java.util.Calendar;
import java.util.Date;

import static android.text.format.DateUtils.FORMAT_ABBREV_ALL;
import static android.text.format.DateUtils.FORMAT_SHOW_YEAR;
import static android.text.format.DateUtils.formatDateTime;

public class MainActivity extends AppCompatActivity implements ApiClient.Callback,
        View.OnClickListener, View.OnFocusChangeListener {

    private Button register;
    private EditText email;
    private EditText fullname;
    private EditText gender;
    private EditText dob;

    private ApiClient apiClient;
    private Date dateOfBirth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Shine.initialize(this, "YOUR_DEV_KEY", LogMode.TO_FILE);

        apiClient = new ApiClient(this);

        register = (Button) findViewById(R.id.register);
        email = (EditText) findViewById(R.id.email);
        fullname = (EditText) findViewById(R.id.full_name);
        gender = (EditText) findViewById(R.id.gender);
        dob = (EditText) findViewById(R.id.dob);

        register.setOnClickListener(this);

        dob.setOnClickListener(this);
        dob.setOnFocusChangeListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        apiClient.setCallback(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        apiClient.setCallback(null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                register();
                break;
            case R.id.dob:
                pickDateOfBirth();
        }
    }

    private void register() {
        Log.d("MainActivity", "Registering...");
        showProgressDialog();
        apiClient.register(email.getText().toString(), fullname.getText().toString(), dateOfBirth,
                gender.getText().toString());
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void onRegisterSuccess(User user) {
        Log.d("MainActivity", String.format("User registered %s", user));
        registerWithShine(user);
        progressDialog.dismiss();
        goToWelcomeActivity(user);
    }

    private void registerWithShine(User user) {
        Registration registration = new Registration()
                .withUserId(user.getId())
                .withEmail(user.getEmail())
                .withFirstName(user.getFirstName())
                .withMiddleName(user.getMiddleName())
                .withLastName(user.getLastName())
                .withGender(user.getGender())
                .withDateOfBirth(user.getDateOfBirth());

        Shine.registerIdentifiedUser(this, registration);
    }

    private void goToWelcomeActivity(User user) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.putExtra(WelcomeActivity.USER, user);
        startActivity(intent);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            pickDateOfBirth();
        }
    }

    private void pickDateOfBirth() {
        final Calendar calendar = Calendar.getInstance();
        if (dateOfBirth != null) {
            calendar.setTime(dateOfBirth);
        }

        DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateOfBirth = newDate.getTime();
                dob.setText(formatDateTime(MainActivity.this, dateOfBirth.getTime(),
                        FORMAT_ABBREV_ALL | FORMAT_SHOW_YEAR));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.show();
    }
}
