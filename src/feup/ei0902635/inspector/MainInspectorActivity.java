package feup.ei0902635.inspector;

import feup.ei0902635.R;
import feup.ei0902635.R.layout;
import feup.ei0902635.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainInspectorActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_inspector);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_inspector, menu);
		return true;
	}

}
