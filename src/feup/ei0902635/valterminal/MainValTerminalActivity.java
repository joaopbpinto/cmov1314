package feup.ei0902635.valterminal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import feup.ei0902635.R;
import feup.ei0902635.others.AlertDialogManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;

public class MainValTerminalActivity extends Activity {
	Integer ticketID;
	Integer busID;
	AlertDialogManager alert = new AlertDialogManager();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_valterminal);
		new Thread(new GetTicketInfo()).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_val_terminal, menu);
		return true;
	}

	public class GetTicketInfo implements Runnable {

		public void run() {
			HttpURLConnection con = null;
			String payload = "Error";
			String result = "Error";
			String time = "Error";
			try {
				URL url = new URL("http://paginas.fe.up.pt/~ei09035/CMOV/checkticket.php?idb=" + ticketID + "&idbus=" + busID);

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
				if(payload.contains("noticket")){
					result = "noticket";
				}
				else {
					if(payload.contains("notvalid")){
						result = "notvalid";
					}
					else {
						try {
							JSONObject jsonObject = new JSONObject(payload);
							result = "valid";
							time = jsonObject.getString("time");
						}
						catch (JSONException e) {
							result = "";
							time = "";
						}
					}
				}
			}
			final String r = result;
			final String t = time;
			runOnUiThread(new Runnable() {
				public void run() {
					if(r.equals("noticket")){
						alert.showAlertDialog(MainValTerminalActivity.this, "No Ticket..", "Ticket doesn't exist", false);
					}
					else {
						if(r.equals("notvalid")){
							alert.showAlertDialog(MainValTerminalActivity.this, "Not Valid..", "Ticket is not valid anymore", false);
						}
						else {
							AlertDialog.Builder builder = new AlertDialog.Builder(MainValTerminalActivity.this);
					        builder.setTitle("Ticket Validated!")
					        .setMessage("Time: " + t)
					        .setIcon(R.drawable.success)
					        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
					            public void onClick(DialogInterface dialog, int id) {
					            	//TODO do something after
					            }
					        });
					        AlertDialog alert = builder.create();
					        alert.show();
						}
					}
				}
			});
		}

	}

}
