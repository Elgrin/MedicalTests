package z.medicaltests;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class Check_Boxes extends Fragment implements View.OnClickListener,
        CheckBox.OnCheckedChangeListener {


    private TestStructure Question;
    private String[] Options;
    private boolean[] Flags;
    //private CheckBox Boxes[];
    private boolean Checked[];
    private int BackGroundColor[];
    private static final String TAG = "CHECK_BOXES";
    //private Check_Boxes.Check_BoxesListener listener;

    /*public boolean[] getChecked() {
        return Checked;
    }*/

    /*public int[] getBackGroundColor() {
        return BackGroundColor;
    }*/


    public int Count() {
        for(int i = 0; i < Options.length; i++) {

            /*
            if(Boxes[i].isChecked() != Flags[i]) {
                return 0;
            }*/
            if(Question.getFlags()[i]!=Checked[i]) {
                return 0;
            }

        }
        return 1;
        /*
        double RighAnswer = 0;
        int Counter = 0;
        int Rights = 0;
        int All = 0;
        int Uncounter = 0;
        View view = getView();


        for (int i = 0; i < Options.length; i++) {

            if (Flags[i]) Rights++;
            if (Boxes[i].isChecked()) All++;

            if (Boxes[i].isChecked() && Flags[i]) {
                //Boxes[i].setBackgroundColor(Color.GREEN);
                Counter++;
            }

            if (Boxes[i].isChecked() && !Flags[i]) {
                Uncounter++;
            }


        }



        if(Rights == 0) Rights = 1;
        double w = (Counter * 100) / Rights;
        if (All == 0) All = 1;
        double m = (Uncounter * 100) / Rights;
        //Log.v(TAG, Double.toString(Counter) + "   " + Double.toString(Uncounter));
        //Log.v(TAG, Double.toString(Rights) + "   " + Double.toString(All));
        Log.v(TAG, Double.toString(w) + "   " + Double.toString(m));

        w -= m;
        if (w <= 50) {
            return 0;
        } else {
            return new
                    BigDecimal(w / 100).
                    setScale(2, RoundingMode.UP).doubleValue();
        }
        /*
        if(Counter < 0) {
            return 0;
        }
        double w = (Counter*100)/(Rights);
        double m = (Uncounter*100)/(Rights);
        w-=m;

        if(w > 50.0) {
            return (RighAnswer = w/100);
        }*/



    }

    public void Paint() {

        View view = getView();

        if(view!=null) {
            for (int i = 0; i < Options.length; i++) {
                CheckBox checkBox = view.findViewById(i);

                if(checkBox!=null) {

                    Log.v("NETTT", checkBox.isChecked() + "  " + Checked[i]);

                    if ( Flags[i]) {
                        checkBox.setTextColor(Color.GREEN);
                    }
                    if (Checked[i] && Flags[i]) {
                        //checkBox.setTextColor(Color.GREEN);
                        Log.v("NETTT", "GR");
                    }

                    if (Checked[i] && !Flags[i]) {
                        //checkBox.setTextColor(Color.RED);
                        Log.v("NETTT", "R");
                    }

                    if (!Checked[i] && Flags[i]) {
                        //checkBox.setTextColor(Color.RED);
                        Log.v("NETTT", "R");
                    }


                    checkBox.setEnabled(false);
                }

            }
        }

    }

    public Check_Boxes() {
        // Required empty public constructor
    }

    public void SetMessage(TestStructure Question, int[] BackGroundColor, boolean[] Checked) {
        this.Question = Question;
        Options = Question.getOptions();
        Flags = Question.getFlags();

        this.Checked = Checked;
        this.BackGroundColor = BackGroundColor;
        //this.Boxes = Boxes;

        Checked = new boolean[Question.getFlags().length];
        for(int i = 0; i < Checked.length; i++) {
            Checked[i] = false;
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        int v = buttonView.getId();
        //Boxes[v].setChecked(buttonView.isChecked());
        Checked[v] = (buttonView.isChecked());

    }

    @Override
    public void onClick(View view) {
        Log.v(TAG, "onClick");
        Paint();

    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            Options = savedInstanceState.getStringArray("options");
            Flags = savedInstanceState.getBooleanArray("flags");

            /*
            CheckBoxesParcerable question;
            question = savedInstanceState.getParcelable("question");
            Question = question.getTestStruscture();

            CheckBoxesParcerable boxes;
            boxes = savedInstanceState.getParcelable("boxes");
            */
            //Boxes = boxes.getCheckBoxes();

            Question = savedInstanceState.getParcelable("question");
            Checked = savedInstanceState.getBooleanArray("checked");
            BackGroundColor = savedInstanceState.getIntArray("backgroundcolor");


            //Log.v(TAG, "Haha " + Boolean.toString(Checked[4]));
            //Log.v(TAG, "Haha2 " + Integer.toString(Boxes.length));

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //final View view = inflater.inflate(R.layout.fragment_check__boxes, container, false);

        return inflater.inflate(R.layout.fragment_check__boxes, container, false);
    }


    public void onStart() {
        super.onStart();

        Log.v(TAG, "onStart");

        View view = getView();

        if(view!=null) {
            LinearLayout layout = view.findViewById(R.id.check_box_layout);
            layout.removeAllViews();

            CheckBox checkBoxes[] = new CheckBox[Question.getOptions().length];
            //View v_0 = new View(getActivity());
            // v_0.setMinimumHeight(1);
            //v_0.setBackgroundColor(Color.GRAY);
            //layout.addView(v_0);


            View[] v = new View[Question.getOptions().length];

            for (int i = 0; i < Question.getOptions().length; i++) {
                Log.v("COUNT", getActivity().getClass().toString());
                checkBoxes[i] = new CheckBox(getActivity());
                v[i] = new View(getActivity());
            }

            for (int i = 0; i < Question.getOptions().length; i++) {
                checkBoxes[i].setText(Options[i]);
                checkBoxes[i].setTextSize(20);
                checkBoxes[i].setId(i);
                checkBoxes[i].setGravity(Gravity.FILL_HORIZONTAL);

                checkBoxes[i].setChecked(Checked[i]);
                checkBoxes[i].setOnCheckedChangeListener(this);
                v[i].setBackgroundColor(Color.GRAY);
                layout.addView(checkBoxes[i]);
                //layout.addView(v[i]);
                //view.addView(hr, new ViewGroup.LayoutParams( ViewGroup.LayoutParams.FILL_PARENT, 1));
                layout.addView(v[i], new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT ,1));
                //Log.v(TAG, Options[i]);
            }

            if (TestFragmentCheckBox.getColor()) {
                Paint();
            }
        }

        //TestFragmentCheckBox.Save(this);
    }

    /*
    public class CheckBoxesParcerable implements Parcelable {
        private TestStructure Questions;
        private int mData;

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel out, int flags) {
            out.writeInt(mData);
        }

        public final Parcelable.Creator<CheckBoxesParcerable> CREATOR
                = new Parcelable.Creator<CheckBoxesParcerable>() {
            public CheckBoxesParcerable createFromParcel(Parcel in) {
                return new CheckBoxesParcerable(in);
            }

            public CheckBoxesParcerable[] newArray(int size) {
                return new CheckBoxesParcerable[size];
            }
        };

        private CheckBoxesParcerable(Parcel in) {
            mData = in.readInt();
        }

        private CheckBoxesParcerable() {
        }

        void setTestStruscture(TestStructure Questions) {
            this.Questions = Questions;
        }

        TestStructure getTestStruscture() {
            return Questions;
        }

    }
    */

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putStringArray("options", Options);
        savedInstanceState.putBooleanArray("flags", Flags);

        /*
        CheckBoxesParcerable question = new CheckBoxesParcerable();
        question.setTestStruscture(Question);
        savedInstanceState.putParcelable("question", question);
*/
        savedInstanceState.putParcelable("question", Question);
        savedInstanceState.putBooleanArray("checked", Checked);
        savedInstanceState.putIntArray("backgroundcolor", BackGroundColor);

    }


}
