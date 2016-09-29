package z.medicaltests;


import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 * A simple {@link Fragment} subclass.
 */
public class Check_Boxes extends Fragment implements View.OnClickListener {


    protected TestStructure Question;
    protected String[] Options;
    protected boolean[] Flags;
    private CheckBox Boxes[];
    private boolean Checked[];
    private int BackGroundColor[];
    private static final String TAG = "CHECK_BOXES";


    public CheckBox[] getCheckBoxes() {
        return Boxes;
    }

    public boolean[] getChecked() {
        return Checked;
    }

    public int[] getBackGroundColor() {
        return BackGroundColor;
    }


    public double Count() {

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

        double w = (Counter * 100) / Rights;
        if (All == 0) All = 1;
        double m = (Uncounter * 100) / All;
        Log.v(TAG, Double.toString(Counter) + "   " + Double.toString(Uncounter));
        Log.v(TAG, Double.toString(Rights) + "   " + Double.toString(All));
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
        }
*/
    }

    public double Paint() {

        //double RighAnswer = 0;
        int Counter = 0;

        for (int i = 0; i < Options.length; i++) {

            if (Boxes[i].isChecked() && Flags[i]) {
                Boxes[i].setTextColor(Color.GREEN);
                Counter++;
            }

            if (Boxes[i].isChecked() && !Flags[i]) {
                Boxes[i].setTextColor(Color.RED);
            }

            if (!Boxes[i].isChecked() && Flags[i]) {
                Boxes[i].setTextColor(Color.RED);
            }


            Boxes[i].setEnabled(false);

        }

        if (Counter == Options.length) {
            return (1);
        }
        if ((Counter * 2) >= Options.length) {
            return (0.5);
        }

        return 0;
    }

    public Check_Boxes() {
        // Required empty public constructor
    }

    public void SetMessage(TestStructure Question, CheckBox[] Boxes, int[] BackGroundColor, boolean[] Checked) {
        this.Question = Question;
        Options = Question.getOptions();
        Flags = Question.getFlags();

        this.Checked = Checked;
        this.BackGroundColor = BackGroundColor;
        this.Boxes = Boxes;
    }


    @Override
    public void onClick(View view) {
        int v = view.getId();

        CheckBox box = (CheckBox) view.findViewById(v);
        Boxes[v].setChecked(box.isChecked());
        Checked[v] = box.isChecked();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (savedInstanceState != null) {
            Options = savedInstanceState.getStringArray("options");
            Flags = savedInstanceState.getBooleanArray("flags");

            CheckBoxesParcerable question;
            question = savedInstanceState.getParcelable("question");
            Question = question.getTestStruscture();

            CheckBoxesParcerable boxes;
            boxes = savedInstanceState.getParcelable("boxes");
            Boxes = boxes.getCheckBoxes();

            Checked = savedInstanceState.getBooleanArray("checked");
            BackGroundColor = savedInstanceState.getIntArray("backgroundcolor");

            //Log.v(TAG, "Haha " + Boolean.toString(Checked[4]));
            //Log.v(TAG, "Haha2 " + Integer.toString(Boxes.length));

        }

        return inflater.inflate(R.layout.fragment_check__boxes, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();

        View view = getView();
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.check_box_layout);
        //layout.setVerticalGravity();
        /**
         * LayoutParams lp = new LayoutParams( LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT );
         lp.gravity = Gravity.CENTER;
         */

        layout.removeAllViews();

        CheckBox checkBoxes[] = new CheckBox[Question.getOptions().length];
        View v_0 = new View(getActivity());
        v_0.setMinimumHeight(1);
        v_0.setBackgroundColor(Color.GRAY);
        layout.addView(v_0);


        View[] v = new View[Question.getOptions().length];

        for (int i = 0; i < Question.getOptions().length; i++) {
            checkBoxes[i] = new CheckBox(getActivity());
            v[i] = new View(getActivity());
        }


        for (int i = 0; i < Question.getOptions().length; i++) {
            checkBoxes[i].setText(Options[i]);
            checkBoxes[i].setTextSize(20);
            checkBoxes[i].setId(i);
            Log.v(TAG, "CHECKED " + Boolean.toString(Checked[i]));
            try {
                checkBoxes[i].setChecked(Checked[i]);
            } catch (Exception e) {
            }

            try {
                if (TestFragmentCheckBox.getColor()) {
                    Paint();
                    if (checkBoxes[i].isChecked() && Flags[i]) {
                        checkBoxes[i].setTextColor(Color.GREEN);
                    }

                    if (checkBoxes[i].isChecked() && !Flags[i]) {
                        checkBoxes[i].setTextColor(Color.RED);
                    }

                    if (!checkBoxes[i].isChecked() && Flags[i]) {
                        checkBoxes[i].setTextColor(Color.RED);
                    }

                }
                //checkBoxes[i].setBackgroundColor(BackGroundColor[i]);
                Log.v(TAG, "CHECKBOXES " + Boolean.toString(checkBoxes[i].isChecked()));
            } catch (Exception e) {
            }
            try {
                checkBoxes[i].setEnabled(Boxes[i].isEnabled());
            } catch (Exception e) {
            }

            checkBoxes[i].setOnClickListener(this);

            v[i].setMinimumHeight(1);
            v[i].setBackgroundColor(Color.GRAY);
            layout.addView(checkBoxes[i]);
            layout.addView(v[i]);
        }

        Boxes = checkBoxes;
    }

    public class CheckBoxesParcerable implements Parcelable {
        private CheckBox Boxes[];
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

        void setCheckBoxes(CheckBox Boxes[]) {
            this.Boxes = Boxes;
        }

        TestStructure getTestStruscture() {
            return Questions;
        }

        CheckBox[] getCheckBoxes() {
            return Boxes;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);

        //setRetainInstance(true);

        savedInstanceState.putStringArray("options", Options);
        savedInstanceState.putBooleanArray("flags", Flags);

        CheckBoxesParcerable question = new CheckBoxesParcerable();
        question.setTestStruscture(Question);
        savedInstanceState.putParcelable("question", question);

        CheckBoxesParcerable boxes = new CheckBoxesParcerable();
        boxes.setCheckBoxes(Boxes);
        savedInstanceState.putParcelable("boxes", boxes);


        try {
            for (int i = 0; i < Options.length; i++) {
                Checked[i] = Boxes[i].isChecked();
                try {
                    ColorDrawable viewColor = (ColorDrawable) Boxes[i].getBackground();
                    BackGroundColor[i] = viewColor.getColor();
                } catch (Exception e) {
                }
            }


        } catch (Exception e) {
        }

        savedInstanceState.putBooleanArray("checked", Checked);
        savedInstanceState.putIntArray("backgroundcolor", BackGroundColor);

    }

}
