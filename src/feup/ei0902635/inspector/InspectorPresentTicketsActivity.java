package feup.ei0902635.inspector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import feup.ei0902635.R;
import feup.ei0902635.others.AlertDialogManager;
import feup.ei0902635.others.Ticket;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class InspectorPresentTicketsActivity extends Activity {

	String busID = "";
	AlertDialogManager alert = new AlertDialogManager();
	ListView ticketsList;
	List<Ticket> tickets = new ArrayList<Ticket>();
	ArrayAdapter<Ticket> adapter = null;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inspector_present_tickets);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			busID = extras.getString("busID");
		}
		System.out.println("busID: " + busID);
		
		new Thread(new GetTicketsFromBus()).start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inspector_present_tickets, menu);
		return true;
	}

	public class GetTicketsFromBus implements Runnable {
		public void run() {
			HttpURLConnection con = null;
			String payload = "Error";
			String result = "Error";
			JSONArray jsontickets = null;
			try {
				URL url = new URL("http://paginas.fe.up.pt/~ei09035/CMOV/gettickets.php?idbus=" + busID);

				con = (HttpURLConnection) url.openConnection();
				con.setReadTimeout(10000);
				con.setConnectTimeout(15000);
				con.setRequestMethod("GET");
				con.setDoInput(true);
				con.connect();

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(con.getInputStream(), "UTF-8"));
				payload = reader.readLine();
				reader.close();
			} catch (IOException e) {

			} finally {
				if (con != null)
					con.disconnect();
			}
			if (payload != "Error") {
				try {
					JSONObject jsonObject = new JSONObject(payload);
					jsontickets = jsonObject.getJSONArray("tickets");
				} catch (JSONException e) {
					result = "";
				}
			}
			final String r = result;
			final JSONArray ar = jsontickets;
			
			runOnUiThread(new Runnable() {
				public void run() {
					if (r.isEmpty()) {
						alert.showAlertDialog(
								InspectorPresentTicketsActivity.this,
								"No tickets..", "No Tickets on this bus", false);
					} else {
						System.out.println(ar.length());
						ticketsList = (ListView) findViewById(R.id.presentTicketsList);
						//adapter = new ArrayAdapter(null, 0);
					    //list.setAdapter(adapter);
						for (int i = 0; i < ar.length(); i++) {
							JSONObject json_data = null;
							try {
								json_data = ar.getJSONObject(i);
							} catch (JSONException e) {
								e.printStackTrace();
							}
							try {
								Ticket t = new Ticket(json_data.getString("idb"), json_data.getString("typeticket"), json_data.getString("timevalidation"));
								tickets.add(t);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
						adapter = new ArrayAdapter<Ticket>(getApplicationContext(), android.R.layout.simple_list_item_1, tickets);
						ticketsList.setAdapter(adapter);
					}
				}
			});
		}

	}

	/*class TicketAdapter extends ArrayAdapter<Ticket> {
		TicketAdapter() {
			super(InspectorPresentTicketsActivity.this, R.layout.activity_inspector_present_tickets, tickets);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			if (row == null) {
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(R.layout.row, parent, false);
			}
			Ticket r = tickets.get(position);
			((TextView) row.findViewById(R.id.title)).setText(r.getName()); // fill
																			// restaurant
																			// name
			((TextView) row.findViewById(R.id.address)).setText(r.getAddress()); // fill
																					// restaurant
																					// address
			ImageView symbol = (ImageView) row.findViewById(R.id.symbol);
			if (r.getType().equals("sit_down")) // fill restaurant symbol
				symbol.setImageResource(R.drawable.ball_red);
			else if (r.getType().equals("take_out"))
				symbol.setImageResource(R.drawable.ball_yellow);
			else
				symbol.setImageResource(R.drawable.ball_green);

			return (row);
		}
	}*/

	/*
	 * public void createTableRow(int id, String idb, String type, String time)
	 * { TableRow tr = new TableRow(this); LayoutParams lp = new
	 * LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	 * tr.setLayoutParams(lp); tr.setId(id);
	 * 
	 * TextView txtIDB = new TextView(this); txtIDB.setLayoutParams(lp);
	 * txtIDB.setBackgroundColor(Color.parseColor("#dcdcdc"));
	 * txtIDB.setTextColor(Color.parseColor("#000000")); txtIDB.setPadding(20,
	 * 0, 0, 0); txtIDB.setGravity(17); txtIDB.setText(idb); TextView txtTYPE =
	 * new TextView(this); txtTYPE.setLayoutParams(lp);
	 * txtTYPE.setBackgroundColor(Color.parseColor("#d3d3d3"));
	 * txtTYPE.setTextColor(Color.parseColor("#000000")); txtTYPE.setPadding(20,
	 * 0, 0, 0); txtTYPE.setGravity(17); txtTYPE.setText(type); TextView txtTIME
	 * = new TextView(this); txtTIME.setLayoutParams(lp);
	 * txtTIME.setBackgroundColor(Color.parseColor("#cac9c9"));
	 * txtTIME.setTextColor(Color.parseColor("#000000")); txtTIME.setPadding(20,
	 * 0, 0, 0); txtTIME.setGravity(17); txtTIME.setText(time);
	 * 
	 * tr.addView(txtIDB); tr.addView(txtTYPE); tr.addView(txtTIME);
	 * 
	 * rl.addView(tr); }
	 */

}
