package feup.ei0902635.others;

import feup.ei0902635.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
 
public class AlertDialogManager {
	public void showAlertDialog(Context context, String title, String message, Boolean status) {
        
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
        .setMessage(message)
        .setIcon((status) ? R.drawable.success : R.drawable.fail)
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        
    }
}