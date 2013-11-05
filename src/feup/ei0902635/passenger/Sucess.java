package feup.ei0902635.passenger;

import feup.ei0902635.R;
import feup.ei0902635.R.id;
import feup.ei0902635.R.layout;
import feup.ei0902635.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Sucess extends Activity {
	
	Button b;
	private static SQLiteDatabase db=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tt);
		
		b = (Button) findViewById(R.id.home);
		b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
           	 	Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });
		
		/*
		db=(new BaseDados(this)).getReadableDatabase();
		Cursor teste=db.rawQuery("SELECT * FROM tickets",null);
		System.out.println(DatabaseUtils.dumpCursorToString(teste));*/
		
		/*
		b = (Button) findViewById(R.id.testeBD);
		b.setText(teste.getInt(0)+"");*/
	}  

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tt, menu);
		return true;
	}

}
