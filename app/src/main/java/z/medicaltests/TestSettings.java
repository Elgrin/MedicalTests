package z.medicaltests;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class TestSettings extends Fragment {

    private String File;
    private int Size;
    String Text;
    boolean Flag;
    private static final String TAG = "Settings";



    public TestSettings() {
        // Required empty public constructor
    }

    public void SetMessage(String File, int Size,
                           String Text_1, String Text_2) {
        this.File = File;
        this.Size = Size;
        Text = Text_1 + " " + Integer.toString(Size) + Text_2;
        Flag = false;

    }

    public  void SetMessage(String File, int Size,
                            String Text_1) {
        this.File = File;
        this.Size = Size;
        Text = Text_1;
        Flag = true;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        return inflater.inflate(R.layout.fragment_test_settings, container, false);
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);

        Log.v(TAG, "Activity");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.v(TAG, "Context");
    }

}
