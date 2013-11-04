package feup.ei0902635.inspector;

import feup.ei0902635.R;
import feup.ei0902635.others.AlertDialogManager;
import feup.ei0902635.others.SessionManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InspectorGetTicketsActivity extends Activity {
	EditText txtbusID;
	AlertDialogManager alert = new AlertDialogManager();
	SessionManager session;
	Button btnRequestTickets;
	String busID = "";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inspector_get_tickets);

        //session = new SessionManager(getApplicationContext());
        //session.checkLogin();
        
        txtbusID = (EditText) findViewById(R.id.busID);
        btnRequestTickets = (Button) findViewById(R.id.requestticketsfrombus);
        btnRequestTickets.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				busID = txtbusID.getText().toString();
				Intent i = new Intent(getApplicationContext(), InspectorPresentTicketsActivity.class);
				i.putExtra("busID", busID);
				startActivity(i);
				finish();
			}
		});
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.inspector_get_tickets, menu);
		return true;
	}

}
