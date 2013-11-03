package feup.ei0902635;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

import feup.ei0902635.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	EditText txtUsername, txtPassword;
	Button btnLogin;
	AlertDialogManager alert = new AlertDialogManager();
	SessionManager session;
	String username = "", password = "";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		session = new SessionManager(getApplicationContext());

		txtUsername = (EditText) findViewById(R.id.txtUsername);
		txtPassword = (EditText) findViewById(R.id.txtPassword);

		Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				username = txtUsername.getText().toString();
				password = txtPassword.getText().toString();
				if (username.trim().length() > 0 && password.trim().length() > 0) {
					new Thread(new GetLoginInfo()).start();
				}
				else {
					alert.showAlertDialog(LoginActivity.this, "Login failed..", "Please enter username and password", false);
				}

			}
		});
	}
	
	public class GetLoginInfo implements Runnable {
		public void run() {
			HttpURLConnection con = null;
			String payload = "Error";
			String result = "Error";
			String name = "", email = "";
			try {
				URL url = new URL("http://paginas.fe.up.pt/~ei09035/CMOV/login.php?username=" + username + "&password=" + password);

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
				try {
					JSONObject jsonObject = new JSONObject(payload);
					result = jsonObject.getString("goodlogin");
					name = jsonObject.getString("name");
					email = jsonObject.getString("email");
				}
				catch (JSONException e) {
					result = "";
				}
			}
			final String r = result;
			final String n = name;
			final String e = email;
			runOnUiThread(new Runnable() {
				public void run() {
					if(r.isEmpty()){
						alert.showAlertDialog(LoginActivity.this, "Login failed..", "Username/Password is incorrect", false);
					}
					else {
						session.createLoginSession(n, e);
						// Staring MainActivity
						Intent i = new Intent(getApplicationContext(), MainActivity.class);
						startActivity(i);
						finish();
					}
				}
			});
		}

	}
	
}