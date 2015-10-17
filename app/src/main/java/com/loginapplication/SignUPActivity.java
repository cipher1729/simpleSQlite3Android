package com.loginapplication;

/**
 * Created by cipher1729 on 10/9/2015.
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SignUPActivity extends Activity
{
    EditText editTextUserName,editTextPassword,editTextConfirmPassword;
    Button btnCreateAccount;
    String currUserName, currPassword;
    /*LoginDataBaseAdapter loginDataBaseAdapter;*/
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        editTextUserName=(EditText)findViewById(R.id.editTextUserNameToLogin);
        editTextPassword=(EditText)findViewById(R.id.editTextPasswordToLogin);
        editTextConfirmPassword=(EditText)findViewById(R.id.editTextConfirmPassword);

        btnCreateAccount=(Button)findViewById(R.id.buttonCreateAccount);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                String userName=editTextUserName.getText().toString();
                String password=editTextPassword.getText().toString();
                String confirmPassword=editTextConfirmPassword.getText().toString();

                // check if any of the fields are vaccant
                if(userName.equals("")||password.equals("")||confirmPassword.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
                    return;
                }
                // check if both password matches
                if(!password.equals(confirmPassword))
                {
                    Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    // Save the Data in Database
                    //make post request here
                   /* loginDataBaseAdapter.insertEntry(userName, password);*/
                    currUserName = userName;
                    currPassword = password;
                    makePostRequest();
                   // Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        /*loginDataBaseAdapter.close();*/
    }

    private void makePostRequest()
    {
            Util.insertIntoLogin("http://192.168.0.17:3000/addUser",currUserName, currPassword);

            while(Util.polled==false);
            if(Util.sb.toString().equals("Success!"))
                Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
            else if(Util.sb.toString().equals("Exists!"))
                Toast.makeText(getApplicationContext(), "Account Already Exists! ", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(), "Could not be created ", Toast.LENGTH_LONG).show();
            Util.polled=false;
    }
}
