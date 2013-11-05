package feup.ei0902635.passenger;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class BaseDados extends SQLiteOpenHelper {
	private static final String DATABASE_NAME="db";
	public static final String TITLE="title";
	public static final String VALUE="value";
	
	public BaseDados(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE tickets (id TEXT PRIMARY KEY, tipoBilhete TEXT,  idBus TEXT, tempoA TIME);");

		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		android.util.Log.w("Constants", "Upgrading database, which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS constants");
		onCreate(db);
	}
}