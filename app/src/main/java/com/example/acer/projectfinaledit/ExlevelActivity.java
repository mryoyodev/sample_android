package com.example.acer.projectfinaledit;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ExlevelActivity extends AppCompatActivity {

    RadioGroup radioGroupExlevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exlevel);

        radioGroupExlevel = (RadioGroup)findViewById(R.id.radioGroupExlevel);
    }

    public void exlevel(View view) {
        final String exlevel = ((RadioButton) findViewById(radioGroupExlevel.getCheckedRadioButtonId())).getText().toString();

        String userExlevel ="";
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()){
            case R.id.radioExlevel_00:
                if (checked)
                    userExlevel = "00";
                break;
            case R.id.radioExlevel_01:
                if (checked)
                    userExlevel = "01";
                break;
            case R.id.radioExlevel_02:
                if (checked)
                    userExlevel = "02";
                break;
            case R.id.radioExlevel_03:
                if (checked)
                    userExlevel = "03";
                break;
        }

        class ExlevelUser extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();
                params.put("exlevel", exlevel);
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
                        startActivity(new Intent(getApplicationContext(), DiseasesActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        ExlevelUser ru = new ExlevelUser();
        ru.execute();
    }
}
