package feup.ei0902635.passenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import feup.ei0902635.R;

public class Buy extends Activity {
	
	Button checkBuy;
	NumberPicker np_T1;
	NumberPicker np_T2;
	NumberPicker np_T3;
	int t1;
	int t2;
	int t3;
	
	String _t1;
	String _t2;
	String _t3;
	String price;
	String bonus;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buy);
		
		np_initiate();
		
		checkBuy = (Button) findViewById(R.id.checkBuy);
		checkBuy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
	           	 
	           	  t1 = np_T1.getValue();
	           	  t2 = np_T2.getValue();
	           	  t3 = np_T3.getValue();
	           	 
	           	 
	           	
            	new Thread(new GetTicketInfo()).start();
            	
	
		
	}});}
	
	public class GetTicketInfo implements Runnable {

		  public void run() {
		   HttpURLConnection con = null;
		   String payload = "Error";
		   //String result = "Error";
		   
		  

		   try {
		    URL url = new URL("http://paginas.fe.up.pt/~ei09035/CMOV/ticketsprice.php?t1="+t1+"&t2="+t2+"&t3="+t3);

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
		      // result = "valid";
		       price = jsonObject.getString("price");
		       _t1 = jsonObject.getString("t1");
		       _t2 = jsonObject.getString("t2");
		       _t3 = jsonObject.getString("t3");
		       bonus = jsonObject.getString("bonus");
		      }
		      catch (JSONException e) {
		      // result = "";
		       
		      }
		     
		   }
		   //final String r = payload;
		

		   runOnUiThread(new Runnable() {
		    public void run() {
		    		//checkBuy.setText(price);
		    		
		           	 Intent i = new Intent(getApplicationContext(), CheckBuy.class);
		           	 i.putExtra("_t1",_t1);
		           	 i.putExtra("_t2",_t2);
		           	 i.putExtra("_t3",_t3);
		           	 i.putExtra("_price",price);
		             i.putExtra("_bonus",bonus);
		             startActivity(i);
		    }
		   });
		  }

		 }

		
	
	
	
	
	
	
	private void np_initiate() {
		// TODO Auto-generated method stub
		np_T1 = (NumberPicker) findViewById(R.id.np_T1);
		String[] nums = new String[11];
		for(int i=0; i<nums.length; i++)
		       nums[i] = Integer.toString(i);

		np_T1.setMinValue(0);
		np_T1.setMaxValue(10);
		np_T1.setWrapSelectorWheel(false);
		np_T1.setDisplayedValues(nums);
		np_T1.setValue(0);
		
		np_T2 = (NumberPicker) findViewById(R.id.np_T2);


		np_T2.setMinValue(0);
		np_T2.setMaxValue(10);
		np_T2.setWrapSelectorWheel(false);
		np_T2.setDisplayedValues(nums);
		np_T2.setValue(0);
		
	    np_T3 = (NumberPicker) findViewById(R.id.np_T3);


		np_T3.setMinValue(0);
		np_T3.setMaxValue(10);
		np_T3.setWrapSelectorWheel(false);
		np_T3.setDisplayedValues(nums);
		np_T3.setValue(0);
	}

		
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.buy, menu);
		return true;
	}

}
