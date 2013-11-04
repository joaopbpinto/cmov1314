package feup.ei0902635.inspector;

import java.util.HashMap;

import feup.ei0902635.R;
import feup.ei0902635.others.AlertDialogManager;
import feup.ei0902635.others.SessionManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainInspectorActivity extends Activity {

	AlertDialogManager alert = new AlertDialogManager();
	SessionManager session;
	Button btnGetTickets;
	Button btnValidateTicket;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_inspector);
		
		TextView lblNameI = (TextView) findViewById(R.id.lblNameI);
        session = new SessionManager(getApplicationContext());
        btnGetTickets = (Button) findViewById(R.id.gettickets);
        
        session.checkLogin();
        
        HashMap<String, String> user = session.getUserDetails();
        String name = user.get(SessionManager.KEY_NAME);
        lblNameI.setText(Html.fromHtml("Name: <b>" + name + "</b>"));
        
        btnGetTickets.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(), InspectorGetTicketsActivity.class);
				startActivity(i);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_inspector, menu);
		return true;
	}

}