package z.medicaltests;


import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 */
public class ButtonMenu extends Fragment implements View.OnClickListener{

    private ButtonMenuListener listener;
    private static final String TAG = "Button";
    private String File="";
    private int Size;
    private String Text ="";

    interface  ButtonMenuListener {
        void onButtonClickAllQuestions(int Size, String Filez);
        void  onButtonClickExamMode(int Size, String File);
    }

    public ButtonMenu() {
        // Required empty public constructor
    }

    public void setMessage(String message, String Text, AssetManager assetManager) {
        File = message;
        XmlTestLoader loader = new XmlTestLoader(message, assetManager);
        Size = loader.getSize();
        this.Text = Text;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View layout = inflater.inflate(R.layout.fragment_button_menu, container, false);

        if (savedInstanceState != null) {
            File = savedInstanceState.getString("file");
            Size = savedInstanceState.getInt("size_int");
            Text = savedInstanceState.getString("text");
        }

        Button allQuestions = layout.findViewById(R.id.allQuestions);
        allQuestions.setOnClickListener(this);

        Button examMode = layout.findViewById(R.id.ExamMode);
        examMode.setOnClickListener(this);

        return layout;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (ButtonMenuListener) context;
        Log.v(TAG, "Context");
    }

    @Override
    public  void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onClick(View view) {

        Log.v(TAG, "Click");

        switch (view.getId()) {

            case R.id.allQuestions:
                if (listener != null) {
                    listener.onButtonClickAllQuestions(Size, File);
                }
                break;
            case R.id.ExamMode:
                if (listener != null) {
                    listener.onButtonClickExamMode(Size, File);
                }
                break;
        }
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file", File);
        outState.putInt("size_int", Size);
        outState.putString("text", Text);
    }

}
