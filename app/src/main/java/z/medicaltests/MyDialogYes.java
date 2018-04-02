package z.medicaltests;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyDialogYes extends DialogFragment {
    private String Title;
    private String Message;
    public void setTitle(String title) {
        Title = title;
    }

    public void setMessage(String message) {
        Message = message;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            Title = savedInstanceState.getString("title");
            Message = savedInstanceState.getString("message");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(Title)
                .setMessage(Message)
                .setNeutralButton("Принять", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString("title", Title);
        savedInstanceState.putString("message", Message);
    }
}
