package z.medicaltests;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultPage extends Fragment implements View.OnClickListener {


    private int Result;
    private int All;
    private ResultPageListener listener;
    private TestStructure Question;
    private int Max;
    private String Path;
    private String Name;
    private int Mode;
    protected boolean Show;
    private int[] MistakesIndexesArray;
    private int AbsoluteSize;
    private int Mass[];

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

                int newMass[] = new int[MistakesIndexesArray.length] ;

                for(int i = 0; i < MistakesIndexesArray.length; i++) {
                    newMass[i] = MistakesIndexesArray[i]+1;
                    Log.v("Baba", Integer.toString(newMass[i]));
                }


                Mass = newMass;
                int iter []= {Mass[0]};
                Max = Mass.length;

                XmlTestLoader loader = new XmlTestLoader(Path, getActivity().getAssets(), Mass[0]);
                TestStructure Question = loader.getTestStructure();

                Log.v("Baba", Integer.toString(AbsoluteSize)  +" " + Path + " " + Mass[0]);
                barListener.BarDrawer(Name, Path, Question, Show, Max, Mode, null, AbsoluteSize, Mass);

                break;
            }
            case R.id.again_page_button: {
                barListener.BarDrawer(Name, Path, Question, Show, Max, Mode, null, AbsoluteSize, Mass);
                break;
            }


        }

    }

    public ResultPage() {
        // Required empty public constructor
    }

    //Name, Path, Questions, 1, Show, false, 0, Max, Mode
    public void setMessage(int Result, int All,
                           String Name,
                           String Path,
                           boolean Show,
                           int Max,
                           int Mode,
                           int[] MistakesIndexesArray,
                           int[] Mass,
                           int AbsoluteSize) {
        this.All = All;
        this.Result = Result;
        this.Name = Name;
        this.Path = Path;
        this.Show = Show;
        this.Max = Max;
        this.Mode = Mode;
        this.MistakesIndexesArray = MistakesIndexesArray;
        this.Mass = Mass;
        this.AbsoluteSize = AbsoluteSize;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            All = savedInstanceState.getInt("all");
            Result = savedInstanceState.getInt("result");

            Show = savedInstanceState.getBoolean("show");
            Max = savedInstanceState.getInt("max");
            Path = savedInstanceState.getString("path");
            Name = savedInstanceState.getString("name");
            Mode = savedInstanceState.getInt("mode");
            MistakesIndexesArray = savedInstanceState.getIntArray("MistakesIndexesArray");
            AbsoluteSize = savedInstanceState.getInt("AbsoluteSize");
            Mass = savedInstanceState.getIntArray("mass");


        }

        final View view = inflater.inflate(R.layout.fragment_result_page, container, false);

        Button button = (Button) view.findViewById(R.id.result_page_button);
        button.setOnClickListener(this);

        Button mistakes = (Button) view.findViewById(R.id.mistakes_page_button);
        mistakes.setOnClickListener(this);

        Button again = (Button) view.findViewById(R.id.again_page_button);
        again.setOnClickListener(this);

        Button start = (Button) view.findViewById(R.id.main_page_button);
        start.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        View view = getView();
        TextView textView = (TextView) view.findViewById(R.id.result_text);
        textView.setText("Получено баллов: "
                + Integer.toString(Result)
                + " из "
                + Integer.toString(All));

        Button mistakes = (Button) view.findViewById(R.id.mistakes_page_button);

        Button main = (Button) view.findViewById(R.id.main_page_button);
        main.setVisibility(View.GONE);

        if(MistakesIndexesArray == null)  {
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

        super.onSaveInstanceState(outState);
        //setRetainInstance(true);

        outState.putInt("all", All);
        outState.putInt("result", Result);
        outState.putBoolean("show", Show);
        outState.putInt("max", Max);
        outState.putString("path", Path);
        outState.putString("name", Name);
        outState.putInt("mode", Mode);
        outState.putIntArray("MistakesIndexesArray", MistakesIndexesArray);
        outState.putInt("AbsoluteSize", AbsoluteSize);
        outState.putIntArray("mass", Mass);

    }

}
