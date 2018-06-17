package com.example.acer.projectfinaledit;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class UsernameActivity extends AppCompatActivity {

    EditText editTextUsername, editTextLastname, editTextBirthday, editTextWeight, editTextHeight;
    RadioGroup radioGroupGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextLastname = (EditText) findViewById(R.id.editTextLastname);
        editTextBirthday = (EditText) findViewById(R.id.editTextBirthday);
        editTextWeight = (EditText) findViewById(R.id.editTextWeight);
        editTextHeight = (EditText) findViewById(R.id.editTextHeight);

        radioGroupGender = (RadioGroup) findViewById(R.id.radioGender);

        findViewById(R.id.buttonRegisterUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on button register
                //here we will register the user to server
                registerUser();
            }
        });
    }

    private void registerUser() {
        final String username = editTextUsername.getText().toString().trim();
        final String lastname = editTextLastname.getText().toString().trim();
        final String birthday = editTextBirthday.getText().toString().trim();
        final String weight = editTextWeight.getText().toString().trim();
        final String height = editTextHeight.getText().toString().trim();

        final String gender = ((RadioButton) findViewById(radioGroupGender.getCheckedRadioButtonId())).getText().toString();

        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Please enter username");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(lastname)) {
            editTextLastname.setError("Please enter lastname");
            editTextLastname.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(birthday)) {
            editTextUsername.setError("Please enter birthday");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(weight)) {
            editTextUsername.setError("Please enter weight");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(height)) {
            editTextUsername.setError("Please enter height");
            editTextUsername.requestFocus();
            return;
        }

        class RegisterUser extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("lastname", lastname);
                params.put("gender", gender);
                params.put("birthday", birthday);
                params.put("weight", weight);
                params.put("height", height);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_REGISTER, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //displaying the progress bar while user registers on the server
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion
                progressBar.setVisibility(View.GONE);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("user");

                        //creating a new user object
                        User user = new User(
                                userJson.getInt("id"),
                                userJson.getString("username"),
                                userJson.getString("lastname"),
                                userJson.getString("email"),
                                userJson.getString("birthday"),
                                userJson.getString("gender"),
                                userJson.getInt("weight"),
                                userJson.getInt("height"),
                                userJson.getString("exlevel"),
                                userJson.getString("diseases"),
                                userJson.getString("foodallergy")
                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        //starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), ExlevelActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //executing the async task
        RegisterUser ru = new RegisterUser();
        ru.execute();
    }
}
