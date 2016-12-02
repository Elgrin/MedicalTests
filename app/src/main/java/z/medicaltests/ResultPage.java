package z.medicaltests;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
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
    private TestStructure[] Questions;
    private int Max;
    private String Path;
    private String Name;
    private int Mode;
    protected boolean Show;
    private int[] MistakesIndexesArray;

    private TestFragmentCheckBox.TestFragmentBarListener barListener;

    static interface ResultPageListener {
        void onButtonResultPageListener();
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.result_page_button: {
                if (listener != null) {
                    listener.onButtonResultPageListener();
                }
                break;
            }
            case R.id.mistakes_page_button: {

                TestStructure newQuestions[] = new TestStructure[MistakesIndexesArray.length-1];

                for(int i = 0; i < MistakesIndexesArray.length-1; i++) {
                    newQuestions[i] = Questions[MistakesIndexesArray[i]];
                }
                Questions = newQuestions;
                Max = Questions.length;
                barListener.BarDrawer(Name, Path, Questions, Show, Max, Mode, null);

                break;
            }
            case R.id.again_page_button: {
                barListener.BarDrawer(Name, Path, Questions, Show, Max, Mode, null);
                break;
            }
        }

    }

    public ResultPage() {
        // Required empty public constructor
    }

    //Name, Path, Questions, 1, Show, false, 0, Max, Mode
    public void setMessage(double Result, int All, TestStructure Questions[],
                           String Name,
                           String Path,
                           boolean Show,
                           int Max,
                           int Mode,
                           int[] MistakesIndexesArray) {
        this.All = All;
        this.Result = Result;
        this.Questions = Questions;
        this.Name = Name;
        this.Path = Path;
        this.Show = Show;
        this.Max = Max;
        this.Mode = Mode;
        this.MistakesIndexesArray = MistakesIndexesArray;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            All = savedInstanceState.getInt("all");
            Result = savedInstanceState.getDouble("result");

            Show = savedInstanceState.getBoolean("show");
            Max = savedInstanceState.getInt("max");
            Path = savedInstanceState.getString("path");
            Name = savedInstanceState.getString("name");
            Mode = savedInstanceState.getInt("mode");
            MistakesIndexesArray = savedInstanceState.getIntArray("MistakesIndexesArray");

            ResultFragmentPacerable question;
            question = savedInstanceState.getParcelable("questions");
            Questions = question.getTestStruscture();
        }

        final View view = inflater.inflate(R.layout.fragment_result_page, container, false);

        Button button = (Button) view.findViewById(R.id.result_page_button);
        button.setOnClickListener(this);

        Button mistakes = (Button) view.findViewById(R.id.mistakes_page_button);
        mistakes.setOnClickListener(this);

        Button again = (Button) view.findViewById(R.id.again_page_button);
        again.setOnClickListener(this);
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
                + Double.toString(All));

        Button mistakes = (Button) view.findViewById(R.id.mistakes_page_button);
        if(MistakesIndexesArray.length==1) {
            mistakes.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        this.listener = (ResultPageListener) context;

        this.barListener = (TestFragmentCheckBox.TestFragmentBarListener) context;


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (ResultPageListener) context;

        this.barListener = (TestFragmentCheckBox.TestFragmentBarListener) context;
    }

    @Override
    public  void onDetach() {
        super.onDetach();
        listener = null;
        barListener = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        //super.onSaveInstanceState(outState);
        //setRetainInstance(true);


        outState.putInt("all", All);
        outState.putDouble("result", Result);
        outState.putBoolean("show", Show);
        outState.putInt("max", Max);
        outState.putString("path", Path);
        outState.putString("name", Name);
        outState.putInt("mode", Mode);
        outState.putIntArray("MistakesIndexesArray", MistakesIndexesArray);

        ResultFragmentPacerable question = new ResultFragmentPacerable();
        question.setTestStruscture(Questions);
        outState.putParcelable("questions", question);
    }

    public class ResultFragmentPacerable implements Parcelable {
        private TestStructure Questions[];
        private int mData;

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel out, int flags) {
            out.writeInt(mData);
        }

        public final Parcelable.Creator<ResultFragmentPacerable> CREATOR
                = new Parcelable.Creator<ResultFragmentPacerable>() {
            public ResultFragmentPacerable createFromParcel(Parcel in) {
                return new ResultFragmentPacerable(in);
            }

            public ResultFragmentPacerable[] newArray(int size) {
                return new ResultFragmentPacerable[size];
            }
        };

        private ResultFragmentPacerable(Parcel in) {
            mData = in.readInt();
        }

        private ResultFragmentPacerable() {
        }

        void setTestStruscture(TestStructure Questions[]) {
            this.Questions = Questions;
        }


        TestStructure[] getTestStruscture() {
            return Questions;
        }

    }

}
