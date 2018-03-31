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
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MultipleChoicesFragment extends Fragment {

    private TestStructure Question;
    private boolean Checked[];

    private int Relations_ID[];
    private int Answers_ID[];
    private String parentsNumbers[];

    private static final String TAG = "MULTIPLE_CHOICES";

    public MultipleChoicesFragment() {
        // Required empty public constructor
    }


    public int Count() {

        for(int i = 0; i < Checked.length; i++) {
            if(Question.getRelations()[i]!=Checked[i]) {
                return 0;
            }
        }
        return 1;
        /*
        double Rights = 0;
        double small_Right = 0;
        int counter = 0;
        int iterator = 0;
        int Uncounter = 0;
        int All = 0;

        View view = getView();
        for(int i = 0; i < Checked.length; i++) {

            CheckBox checkBox = (CheckBox) view.findViewById(i);
            checkBox.setEnabled(false);
            counter++;

            if(Question.getRelations()[i]) {
                iterator++;
            }
            if(Checked[i]) {
                All++;
            }

            if(Checked[i] && Question.getRelations()[i]) {
                small_Right++;
            }
            if(Checked[i] && !Question.getRelations()[i]) {
                Uncounter++;
            }

            Log.v("CCC_CCC", " i = " + i + " counter =" + counter);

            if(counter == Question.getChildren().length) {


                if(iterator == 0) iterator = 1;
                double w = (small_Right * 100) / iterator;
                if (All == 0) All = 1;
                double m = (Uncounter * 100) / iterator;

                w -= m;
                if (w <= 50) {
                    Rights+=0;
                } else {
                    Rights += new
                            BigDecimal(w / 100).
                            setScale(2, RoundingMode.UP).doubleValue();
                }


                /*
                double w = ((small_Right*100)/iterator);
                if (w <= 50) {
                    Rights+=0;
                } else {
                    Rights += new
                            BigDecimal(w / 100).
                            setScale(2, RoundingMode.UP).doubleValue();
                }*/

        /*
                Log.v("CCC", " counter = " + counter + " small_Right = " + small_Right + " Rights = " + Rights + " w = " + w);
                counter = 0;
                small_Right = 0;
                iterator = 0;
                Uncounter = 0;
                All = 0;
            }

        }

        double Result;
        double w  = ((Rights*100)/Question.getParents().length);

        if (w <= 50) {
            Result = 0;
        } else {
            Result = new
                    BigDecimal(w / 100).
                    setScale(2, RoundingMode.UP).doubleValue();

                */
        //}

        //return Result;

        /*
        int Rights = 0;
        int All = Checked.length;

        View view = getView();
        for(int i = 0; i < Checked.length; i++) {

            CheckBox checkBox = (CheckBox) view.findViewById(i);
            checkBox.setEnabled(false);

            if(Checked[i]==Question.getRelations()[i]) {
                Rights++;
            }
            else {
                //checkBox.setBackgroundColor(Color.RED);
            }

        }

        /*
        for (int i = 0; i < Question.getChildren().length; i++) {
            Spinner spinner = (Spinner) view.findViewById(Relations_ID[i]);
            if((spinner.getSelectedItemPosition() + 1)== Question.getRelations()[i]) {
                Rights++;
            }
        }
        */

        /*
        double w = (double) ((Rights*100)/All);


        if (w <= 50) {
            return 0;
        } else {
            return new
                    BigDecimal(w / 100).
                    setScale(2, RoundingMode.UP).doubleValue();
        }*/


    }
    public void Paint() {

        View view = getView();
        if(view!=null) {
            for(int i = 0; i < Checked.length; i++) {

                CheckBox checkBox = view.findViewById(i);
                checkBox.setEnabled(false);

                if (Question.getRelations()[i]) {
                    checkBox.setTextColor(Color.GREEN);
                }

            }
        }
    }


    public void SetMessage(TestStructure Question){
        this.Question=Question;
        parentsNumbers = new String[Question.getParents().length];

        for(int j = 0; j < Question.getParents().length; j++) {
            parentsNumbers[j] = Question.getParents()[j];
        }

        //Relations = new int[(3*Question.getChildren().length)];
        Relations_ID = new int[Question.getChildren().length];
        Answers_ID = new int[Question.getChildren().length];
        /*
        for(int j = 0; j < (3*Question.getChildren().length); j++) {
            Relations[j] = 0;
        }*/
        Checked = new boolean[Question.getChildren().length
                *Question.getParents().length];

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            parentsNumbers = savedInstanceState.getStringArray("parentsNumbers");
            Relations_ID = savedInstanceState.getIntArray("relations_id");
            Answers_ID = savedInstanceState.getIntArray("answers_id");
            //Relations = savedInstanceState.getIntArray("relations");
            Checked = savedInstanceState.getBooleanArray("Checked");

            /*
            MultipleParcerable question;
            question = savedInstanceState.getParcelable("question");
            Question = question.getTestStruscture();*/
            Question = savedInstanceState.getParcelable("question");

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_multiple_choices, container, false);
    }


    public void onStart() {
        super.onStart();

        Log.v(TAG, "onStart");

        View view = getView();

        if(view!=null) {

            LinearLayout layout = view.findViewById(R.id.multiple_layout);
            layout.removeAllViews();

            TextView parentsStrings[] = new TextView[Question.getParents().length];
            //TextView childrenStrings[] = new TextView[Question.getChildren().length];

            CheckBox variants[] = new CheckBox[Question.getParents().length
                    *Question.getChildren().length];
            //Spinner relations[] = new Spinner[Question.getRelations().length];
            //TextView answers[] = new TextView[Question.getRelations().length];

            for (int i = 0; i < Question.getParents().length; i++) {
                parentsStrings[i] = new TextView(getActivity());
            }
            for (int i = 0; i < Question.getParents().length
                    *Question.getChildren().length; i++) {
                variants[i] = new CheckBox(getActivity());
            }



            int ID =0;
            int counter = 0;

            for (int i = 0; i < Question.getParents().length; i++) {

                parentsStrings[i].setText(Question.getParents()[i]);
                parentsStrings[i].setTextSize(20);
                //parentsStrings[i].setId(ID);
                parentsStrings[i].setGravity(Gravity.FILL_HORIZONTAL);
                //v.setMinimumHeight(1);
                //v.setBackgroundColor(Color.GRAY);
                layout.addView(parentsStrings[i]);
                //layout.addView(v);

                for(int j = 0; j < Question.getChildren().length; j++, counter++) {
                    variants[counter].setText(Question.getChildren()[j]);
                    variants[counter].setTextSize(20);
                    variants[counter].setGravity(Gravity.FILL_HORIZONTAL);
                    variants[counter].setChecked(Checked[counter]);
                    variants[counter].setId(counter);

                    variants[counter].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                            int v = buttonView.getId();
                            Checked[v] = (buttonView.isChecked());
                        }
                    });



                    layout.addView(variants[counter]);
                    ID++;
                }
                //ID++;
            }
            //layout.addView(v);


/*
            int ID_2 = 0;
            int ID_3 = 0;
            for (int i = 0; i < Question.getChildren().length; i++, ID++,ID_2++,ID_3++) {
                childrenStrings[i].setText(Question.getChildren()[i]);
                childrenStrings[i].setTextSize(20);
                childrenStrings[i].setId(ID);


                ID++;
                relations[i].setId(ID);

                Log.v("OSPA", Integer.toString(relations[i].getId()));
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                        (getActivity(), android.R.layout.simple_spinner_dropdown_item, parentsNumbers); //selected item will look like a spinner set from XML

                relations[i].setAdapter(spinnerArrayAdapter);

                Relations_ID[ID_2] = ID;
                relations[i].setSelection(Relations[ID_2]);


                relations[i].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {
                        View v = getView();
                        TextView textView = (TextView) v.findViewById(R.id.www);
                        //textView.setText(Integer.toString(parent.getId()));
                        //textView.append(" " + Integer.toString(position) + " " + view.getId());

                        for(int i =0; i< Relations_ID.length; i++) {
                            if(Relations_ID[i]==parent.getId()) {
                                Relations[i]=position;
                                break;
                            }
                        }
                    } // to close the onItemSelected
                    public void onNothingSelected(AdapterView<?> parent)
                    {

                    }
                });


                ID++;

                answers[i].setId(ID);
                //ArrayAdapter<String> spinnerArrayAdapter_2 = new ArrayAdapter<String>
                        //(getActivity(), android.R.layout.simple_spinner_dropdown_item, parentsNumbers); //selected item will look like a spinner set from XML

                /*
                //answers[i].setAdapter(spinnerArrayAdapter_2);
                Answers_ID[ID_3] = ID;
                answers[i].setText(Question.getParents()[Question.getRelations()[i]-1]);
                */
                //answers[i].setEnabled(false);
            /*
                answers[i].setVisibility(View.GONE);
                answers[i].setTextSize(20);

                childrenStrings[i].setGravity(Gravity.FILL_HORIZONTAL);
                layout.addView(childrenStrings[i]);
                //layout.addView(relations[i]);
                layout.addView(answers[i]);

            }


*/
            if (TestFragmentCheckBox.getColor()) {
                Paint();
            }
        }

    }

/*
    public class MultipleParcerable implements Parcelable {
        private TestStructure Questions;
        private int mData;

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel out, int flags) {
            out.writeInt(mData);
        }

        public final Parcelable.Creator<MultipleParcerable> CREATOR
                = new Parcelable.Creator<MultipleParcerable>() {
            public MultipleParcerable createFromParcel(Parcel in) {
                return new MultipleParcerable(in);
            }

            public MultipleParcerable[] newArray(int size) {
                return new MultipleParcerable[size];
            }
        };

        private MultipleParcerable(Parcel in) {
            mData = in.readInt();
        }

        private MultipleParcerable() {
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

        savedInstanceState.putStringArray("parentsNumbers", parentsNumbers);
        //savedInstanceState.putIntArray("relations", Relations);
        savedInstanceState.putIntArray("relations_id", Relations_ID);
        savedInstanceState.putIntArray("answers_id", Answers_ID);
        savedInstanceState.putBooleanArray("Checked", Checked);

        /*
        MultipleParcerable boxes = new MultipleParcerable();
        boxes.setTestStruscture(Question);
        savedInstanceState.putParcelable("question", boxes);
        */
        savedInstanceState.putParcelable("question", Question);
    }

}
