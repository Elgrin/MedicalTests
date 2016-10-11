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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import static android.content.Context.MODE_APPEND;


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
    private  static  Check_Boxes Frag;
    private int Max;
    private String Path;
    private String Name;
    private static final String TAG = "TestFragmentCheckBox";
    private TestFragmentBarListener barListener;


    interface TestFragmentBarListener {
        public void BarDrawerTrue(boolean MenuFlag);
        public void BarDrawer(String Name, String Path, TestStructure Questions[],boolean Show, int Max);
    }

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


    static interface TestFragmentCheckBoxListener {
        void onButtonCheckBoxCommitListener(String Name, String Path,
                                            boolean Show,
                                            TestStructure Questions[],
                                            int Number,
                                            double RighAnswers,
                                            int Max);
    }


    public static void Save(Check_Boxes fragment) {
        Frag= fragment;
    }

    public TestFragmentCheckBox() {
        // Required empty public constructor
    }

    public void SetMessage(String Name, String Path, TestStructure Questions[], int Number, boolean Show, boolean Colors, double RightAnswers, int Max) {
        this.Path = Path;
        this.Questions = Questions;
        this.Number = Number;
        this.Show = Show;
        this.Colors = Colors;
        this.RightAnswers = RightAnswers;
        this.Max = Max;
        this.Name = Name;
    }


    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (savedInstanceState != null) {

            Number = savedInstanceState.getInt("number");
            RightAnswers = savedInstanceState.getDouble("rightAnswer");
            Show = savedInstanceState.getBoolean("show");
            Colors = savedInstanceState.getBoolean("color");
            Max = savedInstanceState.getInt("max");
            Path = savedInstanceState.getString("path");
            Name = savedInstanceState.getString("name");

            TestFragmentPacerable question;
            question = savedInstanceState.getParcelable("questions");
            Questions = question.getTestStruscture();

            TestFragmentPacerable fragment_box;
            fragment_box = savedInstanceState.getParcelable("fragment");
            fragment = fragment_box.getCheckBoxes();

            /*
            if (Questions[Number - 1].getType() == 1) {
                //View view = getView();

                CheckBox[] Boxes = fragment.getCheckBoxes();
                int BackGroundColor[] = fragment.getBackGroundColor();
                boolean Checked[] = fragment.getChecked();

                fragment.SetMessage(Questions[Number - 1], Boxes, BackGroundColor, Checked);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_frame, fragment, "fragment");
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }*/

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

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.getItem(0).setVisible(true);
        menu.getItem(1).setVisible(true);
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings: {
                barListener.BarDrawer(Name, Path, Questions, Show, Max);
            }
        }

        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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


        if (Colors)
            Save.setVisibility(View.GONE);


        TextView textView = (TextView) view.findViewById(R.id.exercise_text);
        textView.setText(Questions[Number - 1].getText());
        TextView textView_2 = (TextView) view.findViewById(R.id.exercise_number);
        textView_2.setText("Вопрос " + Integer.toString(Number) + " из " + Integer.toString(Max));


        barListener.BarDrawerTrue(true);
    }

    @Override
    public void onClick(View view) {

        if (!Colors) {
            switch (view.getId()) {

                case R.id.button_next: {
                    if(Show) {
                        View v = getView();
                        Button Next = (Button) v.findViewById(R.id.button_next);
                        Next.setText(getResources().getString(R.string.button_next));
                        onClickNext();
                        break;
                    }
                }
                case R.id.button_save: {
                    onClickSave();
                    //Log.v(TAG, "Save");
                    break;
                }
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
                    listener.onButtonCheckBoxCommitListener(Name, Path, Show, Questions, Number + 1, RightAnswers, Max);
                }
                break;
            }
        }

    }

    public void onClickNext() {

        if (Questions[Number - 1].getType() == 1) {
            Colors = true;

            View view = getView();
            fragment = Frag;

            Button Save = (Button) view.findViewById(R.id.button_save);
            Save.setVisibility(View.GONE);
            fragment.Paint();
            Log.v(TAG, "Paint");
            //fragment.SetMessage(Questions[Number - 1], Boxes, BackGroundColor, Checked);

            /*
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_frame, fragment, "fragment");
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();*/

        }
    }

    public void onClickSave() {

        Log.v(TAG, "Save");
        final String filePath = "Save_massive.xml";
        boolean exist = false;

         //FileInputStream fin;
        //getActivity().deleteFile(filePath);

        View view = getView();
        Button Save = (Button) view.findViewById(R.id.button_save);

        String massive[] = getActivity().fileList();
        for(int i = 0; i < massive.length; i++) {
            if(massive[i].equals(filePath)) {
                exist = true;
                break;
            }
        }

        if(!exist) {
            //.setText("Net");

            FileOutputStream fos;

            try {
                String newXmlFileName = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?><save all=\"0\"></save>";
                fos = getActivity().
                        openFileOutput(filePath, MODE_APPEND);
                fos.write(newXmlFileName.getBytes());
                fos.close();
            }
            catch (Exception e) {
                Log.v(TAG, "Error");
            }

            exist = true;
        }
        //getActivity().deleteFile(filePath);
        if(exist) {


            try {
                InputStream is = getActivity().openFileInput(filePath);


                //Save.setText(getActivity().getFilesDir().getAbsolutePath()+"/" + filePath);

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(is);

                Node nNode = doc.getFirstChild();
                String allString = ((Element) nNode).getAttribute("all");
                int allInt = Integer.parseInt(allString);
                //Save.setText(allString);
                ((Element) nNode).setAttribute("all", Integer.toString(allInt+1));


                Element NameElementTitle=doc.createElement("unit");
                //NameElementTitle.appendChild(doc.createTextNode("true")); //Внутренний текст
                NameElementTitle.setAttribute("id", Integer.toString(allInt));
                Log.v(TAG, "0");

                Element NameElementPath = doc.createElement("path");
                Log.v(TAG, Path);
                NameElementPath.appendChild(doc.createTextNode(Path));
                Log.v(TAG, "1");
                Element NameElementNumber = doc.createElement("number");
                NameElementNumber.appendChild(doc.createTextNode(Integer.toString(Number)));
                Log.v(TAG, "2");
                Element NameElementShow = doc.createElement("show");
                NameElementShow.appendChild(doc.createTextNode(Boolean.toString(Show)));
                Log.v(TAG, "3");
                Element NameElementRightAnswers = doc.createElement("answers");
                NameElementRightAnswers.appendChild(doc.createTextNode(Double.toString(RightAnswers)));
                Log.v(TAG, "4");
                Element NameElementMaxSize = doc.createElement("max");
                NameElementMaxSize.appendChild(doc.createTextNode(Integer.toString(Max)));
                Element NameElementName = doc.createElement("name");
                Log.v(TAG, Name);
                NameElementName.appendChild(doc.createTextNode(Name));


                NameElementTitle.appendChild(NameElementPath);
                NameElementTitle.appendChild(NameElementNumber);
                NameElementTitle.appendChild(NameElementShow);
                NameElementTitle.appendChild(NameElementRightAnswers);
                NameElementTitle.appendChild(NameElementMaxSize);
                NameElementTitle.appendChild(NameElementName);

                nNode.appendChild(NameElementTitle);

                Transformer transformer = TransformerFactory.newInstance()
                        .newTransformer();
                transformer.setOutputProperty(OutputKeys.METHOD, "xml");
                transformer.setOutputProperty(OutputKeys.INDENT, "no");
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(getActivity().getFilesDir().getAbsolutePath()+"/" + filePath));
                transformer.transform(source, result);
                is.close();

                /*
                FileOutputStream fos;
                fos = getActivity().openFileOutput(filePath, MODE_APPEND);
                String Massive = "<unit id = \"" + allInt + "\">" +
                        "<path>" + Path + "</path>" +
                        "<number>" + Integer.toString(Number)+"</number>" +
                        "<show>" + Boolean.toString(Show)+"</show>" +
                        "<answers>" + Double.toString(RightAnswers)+"</answers>" +
                        "<max>" + Integer.toString(Max)+"</max>" +
                        "</unit>";
                fos.write(Massive.getBytes());*/

            }
            catch (Exception e) {
                Log.v(TAG, "Error writing");
            }
        }
        else {
        }



        /*
         try {

             /**
              * Проверить, существует ли файл, затем, если нет -
              * fos = getActivity().
              openFileOutput(filePath, MODE_APPEND); - чтобы он создался.
              Записать туду шапку и первичный тег
              Затем уже работать с XmlReader
              */
             //InputStream is = getActivity().openFileInput(FileName + ".xml");

             //InputStream is = getActivity().openFileInput(filePath);
        /*
            fin = getActivity().openFileInput(filePath);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String (bytes);
            Log.v(TAG, text);


        }
    /*
        catch(IOException ex) {

             Log.v(TAG, "Error");
        }*/


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
        savedInstanceState.putString("path", Path);
        savedInstanceState.putString("name", Name);

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
        this.barListener = (TestFragmentBarListener) context;
        //Log.v(TAG, "Activity");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (TestFragmentCheckBox.TestFragmentCheckBoxListener) context;
        this.barListener = (TestFragmentBarListener) context;
        //Log.v(TAG, "Context");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;

        barListener.BarDrawerTrue(false);
        barListener = null;
    }


}
