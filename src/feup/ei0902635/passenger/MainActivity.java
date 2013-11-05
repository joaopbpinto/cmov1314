package feup.ei0902635.passenger;

import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import feup.ei0902635.R;
import feup.ei0902635.others.AlertDialogManager;
import feup.ei0902635.others.SessionManager;

public class MainActivity extends Activity {

	AlertDialogManager alert = new AlertDialogManager();
	SessionManager session;
	Button btnLogout;
	Button buy;
	Button mytickets;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		session = new SessionManager(getApplicationContext());
		TextView lblName = (TextView) findViewById(R.id.lblName);
        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        session.checkLogin();
        
        HashMap<String, String> user = session.getUserDetails();
        String name = user.get(SessionManager.KEY_NAME);
        lblName.setText(Html.fromHtml("Name: <b>" + name + "</b>"));

        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				session.logoutUser();
			}
		});
        
		buy = (Button) findViewById(R.id.buy);
		buy.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), Buy.class);
				startActivity(i);
				finish();
			}
		});

		mytickets = (Button) findViewById(R.id.mytickets);
		mytickets.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), MyTickets.class);
				startActivity(i);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
