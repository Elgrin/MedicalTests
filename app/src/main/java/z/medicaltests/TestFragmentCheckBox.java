package z.medicaltests;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragmentCheckBox extends Fragment implements View.OnClickListener {

    protected int Number;
    protected TestStructure Questions[];
    protected double RightAnswers;
    protected boolean Show;
    static protected boolean Colors;
    private Check_Boxes fragment;
    private int Max;

    public static boolean getColor() {
        return Colors;
    }

    public class TestFragmentPacerable implements Parcelable {
        private Check_Boxes fragment;
        private TestStructure Questions[];
        private int mData;

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel out, int flags) {
            out.writeInt(mData);
        }

        public final Parcelable.Creator<TestFragmentPacerable> CREATOR
                = new Parcelable.Creator<TestFragmentPacerable>() {
            public TestFragmentPacerable createFromParcel(Parcel in) {
                return new TestFragmentPacerable(in);
            }

            public TestFragmentPacerable[] newArray(int size) {
                return new TestFragmentPacerable[size];
            }
        };

        private TestFragmentPacerable(Parcel in) {
            mData = in.readInt();
        }

        private TestFragmentPacerable() {
        }

        void setTestStruscture(TestStructure Questions[]) {
            this.Questions = Questions;
        }

        void setCheckBoxes(Check_Boxes fragment) {
            this.fragment = fragment;
        }

        TestStructure[] getTestStruscture() {
            return Questions;
        }

        Check_Boxes getCheckBoxes() {
            return fragment;
        }
    }

    private TestFragmentCheckBox.TestFragmentCheckBoxListener listener;

    private static final String TAG = "Settings";

    static interface TestFragmentCheckBoxListener {
        void onButtonCheckBoxCommitListener(boolean Show,
                                            TestStructure Questions[],
                                            int Number,
                                            double RighAnswers,
                                            int Max);
    }

    public TestFragmentCheckBox() {
        // Required empty public constructor
    }

    public void SetMessage(TestStructure Questions[], int Number, boolean Show, boolean Colors, double RightAnswers, int Max) {
        this.Questions = Questions;
        this.Number = Number;
        this.Show = Show;
        this.Colors = Colors;
        this.RightAnswers = RightAnswers;
        this.Max = Max;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        if (savedInstanceState != null) {
            Number = savedInstanceState.getInt("number");
            RightAnswers = savedInstanceState.getDouble("rightAnswer");
            Show = savedInstanceState.getBoolean("show");
            Colors = savedInstanceState.getBoolean("color");
            Max = savedInstanceState.getInt("max");

            TestFragmentPacerable question;
            question = savedInstanceState.getParcelable("questions");
            Questions = question.getTestStruscture();

            TestFragmentPacerable fragment_box;
            fragment_box = savedInstanceState.getParcelable("fragment");
            fragment = fragment_box.getCheckBoxes();

        } else {
            //Check_Boxes fragment;

            if (Questions[Number - 1].getType() == 1) {
                CheckBox Boxes[] = new CheckBox[Questions[Number - 1].getOptions().length];
                int BackGroundColor[] = new int[Questions[Number - 1].getOptions().length];
                boolean Checked[] = new boolean[Questions[Number - 1].getOptions().length];

                fragment = new Check_Boxes();


                fragment.SetMessage(Questions[Number - 1], Boxes, BackGroundColor, Checked);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_frame, fragment, "fragment");
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        }

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_test_fragment_check_box, container, false);

        Button Next = (Button) view.findViewById(R.id.button_next);
        Next.setOnClickListener(this);

        Button Save = (Button) view.findViewById(R.id.button_save);
        Save.setOnClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        View view = getView();

        Button Next = (Button) view.findViewById(R.id.button_next);
        Button Save = (Button) view.findViewById(R.id.button_save);

        if (Show && !Colors)
            Next.setText(getResources().getString(R.string.button_next_colors));

        if (Show && Colors)
            Save.setVisibility(View.GONE);


        TextView textView = (TextView) view.findViewById(R.id.exercise_text);
        textView.setText(Questions[Number - 1].getText());
        TextView textView_2 = (TextView) view.findViewById(R.id.exercise_number);
        textView_2.setText("Вопрос " + Integer.toString(Number) + " из " + Integer.toString(Max));


    }

    @Override
    public void onClick(View view) {

        if (Show && !Colors) {
            switch (view.getId()) {

                case R.id.button_next: {
                    View v = getView();
                    Button Next = (Button) v.findViewById(R.id.button_next);
                    Next.setText(getResources().getString(R.string.button_next));
                    onClickNext();
                    break;
                }
                case R.id.button_save:
                    onClickSave();
                    break;
            }
            return;
        } else {
            if (Questions[Number - 1].getType() == 1) {
                RightAnswers += fragment.Count();
            }
        }

        switch (view.getId()) {
            case R.id.button_next: {
                if (listener != null) {
                    listener.onButtonCheckBoxCommitListener(Show, Questions, Number + 1, RightAnswers, Max);
                }
            }
        }

    }

    public void onClickNext() {

        if (Questions[Number - 1].getType() == 1) {
            Colors = true;
            fragment.Paint();
            CheckBox[] Boxes = fragment.getCheckBoxes();
            int BackGroundColor[] = fragment.getBackGroundColor();
            boolean Checked[] = fragment.getChecked();

            fragment.SetMessage(Questions[Number - 1], Boxes, BackGroundColor, Checked);

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_frame, fragment, "fragment");
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
    }

    public void onClickSave() {

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
        //setRetainInstance(true);

        savedInstanceState.putInt("number", Number);
        savedInstanceState.putDouble("rightAnswer", RightAnswers);
        savedInstanceState.putBoolean("show", Show);
        savedInstanceState.putBoolean("color", Colors);
        savedInstanceState.putInt("max", Max);

        TestFragmentPacerable question = new TestFragmentPacerable();
        question.setTestStruscture(Questions);

        savedInstanceState.putParcelable("questions", question);

        TestFragmentPacerable fragment_box = new TestFragmentPacerable();
        fragment_box.setCheckBoxes(fragment);
        savedInstanceState.putParcelable("fragment", fragment_box);

    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        this.listener = (TestFragmentCheckBox.TestFragmentCheckBoxListener) context;
        Log.v(TAG, "Activity");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (TestFragmentCheckBox.TestFragmentCheckBoxListener) context;
        Log.v(TAG, "Context");
    }


}
