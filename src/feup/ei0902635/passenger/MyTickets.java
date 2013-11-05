package feup.ei0902635.passenger;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import feup.ei0902635.R;

public class MyTickets extends Activity {

	ListView b;
	private static SQLiteDatabase db=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_tickets);
		
		b = (ListView) findViewById(R.id.ListaBilhetes);
		db=(new BaseDados(this)).getReadableDatabase();
		Cursor teste=db.rawQuery("SELECT id AS _id, tipoBilhete FROM tickets",null);
		
		
		String[] from ={"_id" , "tipoBilhete"};
		int[] to = {android.R.id.text1, android.R.id.text2};
		SimpleCursorAdapter c = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, teste, from, to, 0);
		b.setAdapter(c);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_tickets, menu);
		return true;
	}

}
