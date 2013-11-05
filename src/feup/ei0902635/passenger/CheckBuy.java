package feup.ei0902635.passenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;

import android.content.ContentValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import feup.ei0902635.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CheckBuy extends Activity {

	Button testeCheck;
	String _t1;
	String _t2;
	String _t3;
	String _price;
	String _bonus;
	JSONArray _idT1;
	JSONArray _idT2;
	JSONArray _idT3;
	private static SQLiteDatabase db = null;

	public static final String ID = "ID";
	public static final String tipoBilhete = "tipoBilhete";
	public static final String idBus = "idBus";
	public static final Time tempo = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_buy);
		db = (new BaseDados(this)).getWritableDatabase();
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			_t1 = extras.getString("_t1");
			_t2 = extras.getString("_t2");
			_t3 = extras.getString("_t3");
			_price = extras.getString("_price");
			_bonus = extras.getString("_bonus");

		}

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id._checkBuy);
		// LinearLayout layout = (LinearLayout) findViewById(R.id.info);

		if (Integer.parseInt(_t1) > 0) {
			TextView qtddt1 = new TextView(this);
			if (_bonus == "t1") {
				qtddt1.setText(getString(R.string.bilhetes) + " T1: " + _t1
						+ " + Bónus T1");
			} else {
				qtddt1.setText(getString(R.string.bilhetes) + " T1: " + _t1);
			}
			qtddt1.setId(1);
			qtddt1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			((LinearLayout) linearLayout).addView(qtddt1);
		}

		if (Integer.parseInt(_t2) > 0) {
			TextView qtddt2 = new TextView(this);

			if (_bonus == "t2") {
				qtddt2.setText(getString(R.string.bilhetes) + " T2: " + _t2
						+ " + Bónus T2");
			} else {
				qtddt2.setText(getString(R.string.bilhetes) + " T2: " + _t2);
			}

			qtddt2.setId(2);
			qtddt2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			((LinearLayout) linearLayout).addView(qtddt2);
		}

		if (Integer.parseInt(_t3) > 0) {
			TextView qtddt3 = new TextView(this);

			if (_bonus == "t2") {
				qtddt3.setText(getString(R.string.bilhetes) + " T3: " + _t3
						+ " + Bónus T3");
			} else {
				qtddt3.setText(getString(R.string.bilhetes) + " T3: " + _t3);
			}

			qtddt3.setId(3);
			qtddt3.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			((LinearLayout) linearLayout).addView(qtddt3);
		}

		TextView priceT = new TextView(this);
		priceT.setText("Total: " + _price);
		priceT.setId(4);
		priceT.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		((LinearLayout) linearLayout).addView(priceT);

		Button confirm = new Button(this);
		confirm.setText(getString(R.string.confirma));
		confirm.setId(5);
		confirm.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		((LinearLayout) linearLayout).addView(confirm);

		confirm.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new Thread(new ConfirmTicketBuy()).start();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.check_buy, menu);
		return true;
	}

	public class ConfirmTicketBuy implements Runnable {

		public void run() {
			HttpURLConnection con = null;
			String payload = "Error";
			String result = "Error";

			if (_bonus != null) {
				if (_bonus.equals("t1")) {
					int temp = Integer.parseInt(_t1);
					temp++;
					_t1 = temp + "";
				}

				else if (_bonus.equals("t2")) {
					int temp = Integer.parseInt(_t2);
					temp++;
					_t2 = temp + "";
				}

				else if (_bonus.equals("t3")) {
					int temp = Integer.parseInt(_t3);
					temp++;
					_t3 = temp + "";

				}

			}

			try {
				URL url = new URL(
						"http://paginas.fe.up.pt/~ei09035/CMOV/buytickets.php?t1="
								+ _t1 + "&t2=" + _t2 + "&t3=" + _t3);

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
					result = "valid";
					_idT1 = jsonObject.getJSONArray("t1");
					_idT2 = jsonObject.getJSONArray("t2");
					_idT3 = jsonObject.getJSONArray("t3");

				} catch (JSONException e) {
					result = "";

				}

			}

			runOnUiThread(new Runnable() {
				public void run() {

					for (int i = 0; i < _idT1.length(); i++) {
						ContentValues cv = new ContentValues();
						try {
							cv.put(ID, (String) _idT1.get(i));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						cv.put(tipoBilhete, "t1");
						db.insert("tickets", null, cv);
					}

					for (int i = 0; i < _idT2.length(); i++) {
						ContentValues cv = new ContentValues();
						try {
							cv.put(ID, (String) _idT2.get(i));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						cv.put(tipoBilhete, "t2");
						db.insert("tickets", null, cv);
					}

					for (int i = 0; i < _idT3.length(); i++) {
						ContentValues cv = new ContentValues();
						try {
							cv.put(ID, (String) _idT3.get(i));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						cv.put(tipoBilhete, "t3");
						db.insert("tickets", null, cv);
					}
					db.close();
					Intent i = new Intent(getApplicationContext(), Sucess.class);
					startActivity(i);
				}
			});
		}

	}

}
