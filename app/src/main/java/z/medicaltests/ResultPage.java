package z.medicaltests;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResultPage extends Fragment implements View.OnClickListener {


    private double Result;
    private int All;
    private ResultPageListener listener;

    static interface ResultPageListener {
        void onButtonResultPageListener();
    }

    @Override
    public void onClick(View view) {

        if (listener != null) {
            listener.onButtonResultPageListener();
        }
    }

    public ResultPage() {
        // Required empty public constructor
    }

    public void setMessage(double Result, int All) {
        this.All = All;
        this.Result = Result;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            All = savedInstanceState.getInt("all");
            Result = savedInstanceState.getDouble("result");
        }

        final View view = inflater.inflate(R.layout.fragment_result_page, container, false);

        Button button = (Button) view.findViewById(R.id.result_page_button);
        button.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        View view = getView();
        TextView textView = (TextView) view.findViewById(R.id.result_text);
        textView.setText("Получено баллов: "
                + Double.toString(Result)
                + " из "
                + Integer.toString(All));
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        this.listener = (ResultPageListener) context;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (ResultPageListener) context;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("all", All);
        outState.putDouble("result", Result);
    }

}
