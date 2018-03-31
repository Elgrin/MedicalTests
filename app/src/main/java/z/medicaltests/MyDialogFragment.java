package z.medicaltests;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
/**
 * A simple {@link Fragment} subclass.
 */
public class MyDialogFragment extends DialogFragment {
    private String Title;
    private String Message;
    private int Mode;
    private YesNoListener listener;

    public interface YesNoListener {
        void onYes(int Mode);
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public void setMode(int mode) {
        Mode = mode;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {

            Title = savedInstanceState.getString("title");
            Message = savedInstanceState.getString("message");
            Mode = savedInstanceState.getInt("mode");
        }
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {

            FragmentManager fragMan = getFragmentManager();
            Fragment frag = fragMan.findFragmentByTag("fragment");

            this.listener = (YesNoListener) frag;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(getFragmentManager().toString()
                    + " must implement NoticeDialogListener");
        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(Title)
                .setMessage(Message)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onYes(Mode);
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {

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
        savedInstanceState.putInt("mode", Mode);
    }
}
