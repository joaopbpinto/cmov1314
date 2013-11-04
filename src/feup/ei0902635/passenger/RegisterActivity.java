package feup.ei0902635.passenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


import feup.ei0902635.R;
import feup.ei0902635.others.AlertDialogManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends Activity {
	EditText regUsername, regPassword, regEmail, regName, regCCType, regCCNumber, regCCDate;
	Button btnRegister;
	AlertDialogManager alert = new AlertDialogManager();
	String username = "", password = "", name = "", email = "", cctype = "", ccdate = "", ccnumber = "";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		

		regUsername = (EditText) findViewById(R.id.regUsername);
		regPassword = (EditText) findViewById(R.id.regPassword);
		regEmail = (EditText) findViewById(R.id.regEmail);
		regName = (EditText) findViewById(R.id.regName);
		regCCType = (EditText) findViewById(R.id.regCCType);
		regCCNumber = (EditText) findViewById(R.id.regCCNumber);
		regCCDate = (EditText) findViewById(R.id.regCCDate);

		btnRegister = (Button) findViewById(R.id.btnRegister);
		btnRegister.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				username = regUsername.getText().toString();
				password = regPassword.getText().toString();
				name = regName.getText().toString();
				email = regEmail.getText().toString();
				cctype = regCCType.getText().toString();
				ccnumber = regCCNumber.getText().toString();
				ccdate = regCCDate.getText().toString();
				if (username.trim().length() > 0 && password.trim().length() > 0 && name.trim().length() > 0 && email.trim().length() > 0 && cctype.trim().length() > 0 && !ccnumber.equals(0) && ccdate.trim().length() > 0) {
					new Thread(new Register()).start();
				}
				else {
					alert.showAlertDialog(RegisterActivity.this, "Register failed..", "Please fill in all fields", false);
				}

			}
		});
	}
	
	public class Register implements Runnable {
		public void run() {
			HttpURLConnection con = null;
			String payload = "Error";
			String result = "Error";
			try {
				String nome = URLEncoder.encode(name, "UTF-8");
				URL url = new URL("http://paginas.fe.up.pt/~ei09035/CMOV/register.php?username=" + username + "&password=" + password + "&name=" + nome + "&email=" + email + "&cctype=" + cctype + "&ccnumber=" + ccnumber + "&ccvalidity=" + ccdate);
				System.out.println(url);
				con = (HttpURLConnection) url.openConnection();
				con.setReadTimeout(10000);
				con.setConnectTimeout(15000);
				con.setRequestMethod("GET");
				con.setDoInput(true);
				con.connect();

				BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
				payload = reader.readLine();
				reader.close();
			}
			catch (IOException e) {
				
			}
			finally {
				if (con != null)
					con.disconnect();
			}
			if (payload != "Error"){
				if(payload.contains("userused")){
					result = "userused";
				}
				else
					if(payload.contains("emailused")){
						result = "emailused";
					}
					else
						if(payload.contains("ccnumberused")){
							result = "ccnumberused";
						}
						else
							if(payload.contains("ccvalidityexpire")){
								result = "ccvalidityexpire";
							}
							else
								result = "success";
			}
			final String r = result;
			runOnUiThread(new Runnable() {
				public void run() {
					if(r.equals("userused")){
						alert.showAlertDialog(RegisterActivity.this, "Register failed..", "Username already in use", false);
					}
					else
						if(r.equals("emailused")){
							alert.showAlertDialog(RegisterActivity.this, "Register failed..", "Email already in use", false);
						}
						else
							if(r.equals("ccnumberused")){
								alert.showAlertDialog(RegisterActivity.this, "Register failed..", "Credit Card already in use", false);
							}
							else
								if(r.equals("ccvalidityexpire")){
									alert.showAlertDialog(RegisterActivity.this, "Register failed..", "Credit Card expired", false);
								}
								else {
							        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
							        builder.setTitle("Register successful")
							        .setMessage("Register successfully done!")
							        .setIcon(R.drawable.success)
							        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
							            public void onClick(DialogInterface dialog, int id) {
							            	Intent i = new Intent(getApplicationContext(), LoginActivity.class);
											startActivity(i);
											finish();
							            }
							        });
							        AlertDialog alert = builder.create();
							        alert.show();
							    }
					}
			});
		}

	}
	
}
