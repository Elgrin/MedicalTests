package z.medicaltests;


import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import z.medicaltests.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ButtonMenu extends Fragment implements View.OnClickListener{

    public ButtonMenuListener listener;
    private static final String TAG = "Button";
    private String File="";
    private int Size;
    private String size;
    protected String Text ="";
    static  interface  ButtonMenuListener {
        void onButtonClick(int Size, String size);
    }

    public ButtonMenu() {
        // Required empty public constructor
    }


    public void setMessage(String message, String Text, AssetManager assetManager) {
        File = message;
        XmlTestLoader loader = new XmlTestLoader(message, assetManager);
        Size = loader.getSize();
        size = loader.getS();
        this.Text = Text;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View layout = inflater.inflate(R.layout.fragment_button_menu, container, false);

        Button allQuestions = (Button) layout.findViewById(R.id.allQuestions);
        allQuestions.setOnClickListener(this);

        Button examMode = (Button) layout.findViewById(R.id.ExamMode);
        examMode.setOnClickListener(this);

        return layout;
    }


    @Override
    public  void onStart() {
        super.onStart();

    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        this.listener = (ButtonMenuListener) context;
        Log.v(TAG, "Activity");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (ButtonMenuListener) context;
        Log.v(TAG, "Context");
    }

    @Override
    public void onClick(View view) {

        Log.v(TAG, "Click");

        switch (view.getId()) {

            case R.id.allQuestions:
                onClickAllQuestions();
                if (listener != null) {
                    listener.onButtonClick(Size, size);
                }
                break;
            case R.id.ExamMode:
                onClickExamMode();
                if (listener != null) {
                    listener.onButtonClick(Size, size);
                }
                break;
        }
    }


    public void onClickAllQuestions() {

    }

    public void onClickExamMode() {

    }

}
