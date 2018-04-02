package z.medicaltests;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;

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
public class TestFragmentCheckBox extends Fragment implements View.OnClickListener,
MyDialogFragment.YesNoListener{

    private int Number;
    private TestStructure Question;
    private int RightAnswers;
    private boolean Show;
    private static boolean Colors;
    private int Max;
    private String Path;
    private String Name;
    private int Mode;
    private int MistakesIndexesArray[];
    private int AbsoluteSize;
    private int Mass[];

    private static final String TAG = "TestFragmentCheckBox";
    private TestFragmentBarListener barListener;
    private onBackClickListener backClickListener;

    public static boolean getColor() {
        return Colors;
    }

    @Override
    public void onYes(int mode) {

        DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        if(mode == 0) {

            try {
                FragmentManager fragMan = getFragmentManager();
                Fragment frag = fragMan.findFragmentByTag("fragment");

                if (frag.getClass().toString().equals("class z.medicaltests.TestFragmentCheckBox")) {
                    for(int i = 0; i < getFragmentManager().getBackStackEntryCount(); ++i) {
                        getFragmentManager().popBackStack();
                    }
                    Log.v("Error", "Okie dokie_outside" + getFragmentManager().getBackStackEntryCount());
                }
            }
            catch (Exception e) {Log.v("Error", "Error in popstack");}

            barListener.BarDrawer(Name, Path, Show, Max, Mode,
                    AbsoluteSize, Mass);
        }
        else {
            if(mode == 1) {
                onClickSave();
            }
            else {
                if(mode == 2) {

                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:" + getResources().
                            getString(R.string.email)));
                    intent.putExtra(Intent.EXTRA_SUBJECT,
                            getResources().
                                    getString(R.string.header));
                    intent.putExtra(Intent.EXTRA_TEXT, "Найдена ошибка" + "\n\nНомер вопроса: " + Question.getID() + ". Тема: " + Name + ". Путь: " + Path + "\n\nВаши комментарии:\n");
                    startActivity(intent);

                }
                else {
                    if(mode == 3) {
                        try {
                            FragmentManager fragMan = getFragmentManager();
                            Fragment frag = fragMan.findFragmentByTag("fragment");

                            if (frag.getClass().toString().equals("class z.medicaltests.TestFragmentCheckBox")) {
                                for(int i = 0; i < getFragmentManager().getBackStackEntryCount(); ++i) {
                                    getFragmentManager().popBackStack();
                                }
                                Log.v("Error", "Okie dokie_outside" + getFragmentManager().getBackStackEntryCount());
                            }
                        }
                        catch (Exception e) {Log.v("Error", "Error in popstack");}
                        onClickSave();
                        backClickListener.onBackClick();
                    }
                }
            }
        }

    }

    interface TestFragmentBarListener {
        void BarDrawerTrue();
        void BarDrawer(String Name, String Path, boolean Show, int Max, int Mode,
                              int AbsoluteSize, int Mass[]);
    }

    interface onBackClickListener {
        void onBackClick();
    }

    private TestFragmentCheckBox.TestFragmentCheckBoxListener listener;


    interface TestFragmentCheckBoxListener {
        void onButtonCommitListener(String Name, String Path,
                                            boolean Show,
                                            int Number,
                                            int RighAnswers,
                                            int Max,
                                            int Mode,
                                            int[] MistakesIndexesArray,
                                            int AbsoluteSize,
                                            int Mass[]);
    }

    public TestFragmentCheckBox() {
        // Required empty public constructor
    }

    public void SetMessage(String Name,
                           String Path,
                           TestStructure Question,
                           int Number,
                           boolean Show,
                           boolean Colors,
                           int RightAnswers,
                           int Max,
                           int Mode,
                           int[] MistakesIndexesArray,
                           int AbsoluteSize,
                           int Mass[]) {
        this.Path = Path;
        this.Number = Number;
        this.Show = Show;
        this.Colors = Colors;
        this.RightAnswers = RightAnswers;
        this.Max = Max;
        this.Name = Name;
        this.Mode = Mode;
        this.MistakesIndexesArray = MistakesIndexesArray;
        this.AbsoluteSize = AbsoluteSize;
        this.Question = Question;
        this.Mass = Mass;

        if (!(MistakesIndexesArray == null)){
            this.MistakesIndexesArray = MistakesIndexesArray;
        }

        Log.i("TAG", "keyCode: " + AbsoluteSize + " " + Max);
    }


    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (savedInstanceState != null) {

            Number = savedInstanceState.getInt("number");
            RightAnswers = savedInstanceState.getInt("rightAnswer");
            Show = savedInstanceState.getBoolean("show");
            Colors = savedInstanceState.getBoolean("color");
            Max = savedInstanceState.getInt("max");
            Path = savedInstanceState.getString("path");
            Name = savedInstanceState.getString("name");
            Mode = savedInstanceState.getInt("mode");
            MistakesIndexesArray = savedInstanceState.getIntArray("mistakesIndexesArray");
            AbsoluteSize = savedInstanceState.getInt("AbsoluteSize");
            Mass = savedInstanceState.getIntArray("mass");

            Question = savedInstanceState.getParcelable("question");


        } else {
            //Check_Boxes fragment;
            //Question = null;
            try {
                if (Question.getType() == 1) {
                    //CheckBox Boxes[] = new CheckBox[Question.getOptions().length];
                    int BackGroundColor[] = new int[Question.getOptions().length];
                    boolean Checked[] = new boolean[Question.getOptions().length];

                    Check_Boxes fragment = new Check_Boxes();


                    fragment.SetMessage(Question, BackGroundColor, Checked);

                    FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_frame, fragment, "fragment");
                    //ft.disallowAddToBackStack();
                    ft.addToBackStack(null);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
                }
                else {
                    if (Question.getType() == 2) {
                /*
                CheckBox Boxes[] = new CheckBox[Questions[Number - 1].getOptions().length];
                int BackGroundColor[] = new int[Questions[Number - 1].getOptions().length];
                boolean Checked[] = new boolean[Questions[Number - 1].getOptions().length];
                */

                        MultipleChoicesFragment  fragmentMultiple = new MultipleChoicesFragment();

                        fragmentMultiple.SetMessage(Question);

                        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment_frame, fragmentMultiple, "fragment");
                        //ft.disallowAddToBackStack();
                        ft.addToBackStack(null);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.commit();
                    }
                }
            }
            catch (Exception e)
            {
                Crashlytics.setString("NullPointerException", "getType()");
                Crashlytics.log(0, "Number RightAnswers Show Colors Max Path Name AbsoluteSize",
                        Integer.toString(Mass[Number-1]) + " "
                +Integer.toString(RightAnswers) + " "
                +Boolean.toString(Show) + " "
                +Boolean.toString(Colors) + " "
                +Integer.toString(Max) + " "
                +Path + " "
                +Name + " "
                +Integer.toString(AbsoluteSize));
            }

        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.getItem(0).setVisible(true);
        menu.getItem(1).setVisible(true);
        menu.getItem(4).setVisible(true);
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (Colors)
            menu.getItem(1).setVisible(false);
        //если надо отключить отображение сохранения в режиме экзамена
        else
            menu.getItem(1).setVisible(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings: {
                //barListener.BarDrawer(Name, Path, Questions, Show, Max, Mode);
                //toastMessage(getResources().
                        //getString(R.string.alert_dialogue_again_header), 0);

                MyDialogFragment dialog = new MyDialogFragment();
                dialog.setTitle(getResources().
                        getString(R.string.alert_header));
                dialog.setMessage(getResources().
                        getString(R.string.alert_dialogue_again_header));
                dialog.setMode(0);
                dialog.show(getFragmentManager(), "tag");
                break;

            }
            case R.id.action_save: {
                    //onClickSave();
                //toastMessage(getResources().
                        //getString(R.string.alert_dialogue_save_header), 1);

                MyDialogFragment dialog = new MyDialogFragment();
                dialog.setTitle(getResources().
                        getString(R.string.alert_header));
                dialog.setMessage(getResources().
                        getString(R.string.alert_dialogue_save_header));
                dialog.setMode(1);
                dialog.show(getFragmentManager(), "tag");

                break;
            }

            case R.id.action_bug_report: {
                //onClickSave();
                //toastMessage(getResources().
                        //getString(R.string.alert_dialogue_bug_report), 2);

                MyDialogFragment dialog = new MyDialogFragment();
                dialog.setTitle(getResources().
                        getString(R.string.alert_header));
                dialog.setMessage(getResources().
                        getString(R.string.alert_dialogue_bug_report));
                dialog.setMode(2);
                dialog.show(getFragmentManager(), "tag");

                break;
            }
        }

        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_test_fragment_check_box, container, false);

        Button Next = view.findViewById(R.id.button_next);
        Log.v("COUNT", this.getClass().toString());
        Next.setOnClickListener(this);

        Button Save = view.findViewById(R.id.button_save);
        Save.setOnClickListener(this);

        view.setFocusableInTouchMode(true);
        view.requestFocus();

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("TAG", "keyCode: " + keyCode);
                if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP)  {
                    Log.i("TAG", "onKey Back listener is working!!!");
                    Log.v("COUNT", Integer.toString(getFragmentManager().getBackStackEntryCount()));

                    DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    }
                    else {
                        //toastMessage(getResources().
                                //getString(R.string.alert_dialogue_save_out), 3);

                        MyDialogFragment dialog = new MyDialogFragment();
                        dialog.setTitle(getResources().
                                getString(R.string.alert_header));
                        dialog.setMessage(getResources().
                                getString(R.string.alert_dialogue_save_out));
                        dialog.setMode(3);
                        dialog.show(getFragmentManager(), "tag");

                        Log.v("COUNT", Integer.toString(getFragmentManager().getBackStackEntryCount()));
                    }
                    return true;
                } else {
                    return false;
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        View view = getView();
        if(view!=null) {

            Button Next = view.findViewById(R.id.button_next);
            Button Save = view.findViewById(R.id.button_save);
            //Next.setText(Integer.toString(MistakesIndexesArray.length));

            if (Show && !Colors)
                Next.setText(getResources().getString(R.string.button_next_colors));

            Save.setVisibility(View.GONE);


            TextView textView = view.findViewById(R.id.exercise_text);
            textView.setText(Question.getText());
            textView.setGravity(Gravity.CENTER_HORIZONTAL);

            TextView textView_2 = view.findViewById(R.id.exercise_number);
            String txt = "Вопрос " + Integer.toString(Number) + " из " + Integer.toString(Max);
            textView_2.setText(txt);


            barListener.BarDrawerTrue();

        }

    }


    private void mistakesArray(int digit) {


        if(digit!= 1) {
            //int i = MistakesIndexesArray.length;

            //MistakesIndexesArray[i-1] = Number-1;

            if(MistakesIndexesArray ==null) {
                MistakesIndexesArray = new int[0];
            }

            MistakesIndexesArray = Arrays.copyOf(MistakesIndexesArray, MistakesIndexesArray.length+1);
            //int t[] = new int[MistakesIndexesArray.length+1];

            /*
            for(int j = 0; j < MistakesIndexesArray.length; j++) {
                t[j] = MistakesIndexesArray[j];
            }*/
            int i = MistakesIndexesArray.length;
            MistakesIndexesArray[i-1] = Question.getID()-1;
            Log.v(TAG, Integer.toString(Number-1));
        }


    }
    @Override
    public void onClick(View view) {

        if (!Colors) {
            switch (view.getId()) {

                case R.id.button_next: {
                    if(Show) {
                        View v = getView();

                        if(v!=null) {
                            Button Next = v.findViewById(R.id.button_next);
                            Next.setText(getResources().getString(R.string.button_next));
                            onClickNext();
                        }
                        break;
                    }
                    else {

                        if (Question.getType() == 1) {

                            FragmentManager fragMan = getChildFragmentManager();
                            Fragment fragment = fragMan.findFragmentByTag("fragment");
                            Check_Boxes s = (Check_Boxes) fragment;

                            int right = s.Count();
                            RightAnswers += s.Count();
                            mistakesArray(right);
                        }
                        if (Question.getType() == 2) {
                            //fragmentMultiple = Frag_Multiple;

                            FragmentManager fragMan = getChildFragmentManager();
                            Fragment fragment = fragMan.findFragmentByTag("fragment");
                            MultipleChoicesFragment s = (MultipleChoicesFragment) fragment;

                            int right = s.Count();
                            RightAnswers += right;
                            mistakesArray(right);
                        }

                        if (listener != null) {
                            listener.onButtonCommitListener(Name, Path, Show,
                                    Number + 1, RightAnswers, Max, Mode, MistakesIndexesArray,
                                    AbsoluteSize, Mass);
                        }
                        break;
                    }
                }
                case R.id.button_save: {
                    //onClickSave();
                    //Log.v(TAG, "Save");
                    break;
                }
            }
            return;
        }
        else {
            if (Question.getType() == 1) {

                FragmentManager fragMan = getChildFragmentManager();
                Fragment fragment = fragMan.findFragmentByTag("fragment");
                Check_Boxes s = (Check_Boxes) fragment;

                int right = s.Count();
                RightAnswers += right;
                mistakesArray(right);
            }
            if (Question.getType() == 2) {

                FragmentManager fragMan = getChildFragmentManager();
                Fragment fragment = fragMan.findFragmentByTag("fragment");
                MultipleChoicesFragment s = (MultipleChoicesFragment) fragment;

                //fragmentMultiple = Frag_Multiple;
                int right = s.Count();
                RightAnswers += right;
                mistakesArray(right);
            }
        }

        switch (view.getId()) {

            case R.id.button_next: {

                try {
                    FragmentManager fragMan = getFragmentManager();
                    Fragment frag = fragMan.findFragmentByTag("fragment");

                    if (frag.getClass().toString().equals("class z.medicaltests.TestFragmentCheckBox")) {
                        for(int i = 0; i < getFragmentManager().getBackStackEntryCount(); ++i) {
                            getFragmentManager().popBackStack();
                        }
                    }
                    Log.v("Error", "Okie dokie_outside " + getFragmentManager().getBackStackEntryCount());
                }
                catch (Exception e) {Log.v("Error", "Error in popstack");}

                if (listener != null) {
                    listener.onButtonCommitListener(Name, Path, Show,
                            Number + 1, RightAnswers, Max, Mode, MistakesIndexesArray,
                            AbsoluteSize, Mass);
                }
                break;
            }
        }

    }

    private void onClickNext() {

        if (Question.getType() == 1) {
            Colors = true;

            View view = getView();
            //fragment = Frag;

            if(view!=null) {
                Button Save = view.findViewById(R.id.button_save);
                Save.setVisibility(View.GONE);

                getActivity().invalidateOptionsMenu();

                //fragment.Paint();

                FragmentManager fragMan = getChildFragmentManager();
                Fragment fragment = fragMan.findFragmentByTag("fragment");
                Check_Boxes s = (Check_Boxes) fragment;
                s.Paint();
            }

            Log.v(TAG, "Paint");
        }
        if (Question.getType() == 2) {
            Colors = true;

            //fragmentMultiple = Frag_Multiple;
            getActivity().invalidateOptionsMenu();
            //fragmentMultiple.Paint();
            FragmentManager fragMan = getChildFragmentManager();
            Fragment fragment = fragMan.findFragmentByTag("fragment");
            MultipleChoicesFragment s = (MultipleChoicesFragment) fragment;
            s.Paint();

            Log.v(TAG, "Paint");
        }
    }

    private void onClickSave() {

        Log.v(TAG, "Save");
        final String filePath = "Save_massive.xml";
        boolean exist = false;

         //FileInputStream fin;
        //getActivity().deleteFile(filePath);
        //Button Save = (Button) view.findViewById(R.id.button_save);

        String massive[] = getActivity().fileList();
        for (String s:massive) {
            if(s.equals(filePath)) {
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

        }

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
            NameElementRightAnswers.appendChild(doc.createTextNode(Integer.toString(RightAnswers)));
            Log.v(TAG, "4");
            Element NameElementMaxSize = doc.createElement("max");
            NameElementMaxSize.appendChild(doc.createTextNode(Integer.toString(Max)));
            Element NameElementName = doc.createElement("name");
            Log.v(TAG, Name);
            NameElementName.appendChild(doc.createTextNode(Name));


            Log.v(TAG, "Massive");
            Element NameElementMassive = doc.createElement("massive");
            NameElementMassive.setAttribute("size", Integer.toString(Mass.length));

            for (int s:Mass) {
                Element element = doc.createElement("element");
                element.appendChild(doc.createTextNode(Integer.toString(s)));
                NameElementMassive.appendChild(element);
            }

            // NameElementMassive.setAttribute("size", Integer.toString(Questions.length));

            Log.v(TAG, "Mistakes");
            Element NameElementMistakes = doc.createElement("mistakes");
            if(!(MistakesIndexesArray == null)) {
                NameElementMistakes.setAttribute("size", Integer.toString(MistakesIndexesArray.length));


                Log.v(TAG + "A", "Mistakes");
                Log.v(TAG + "A", Integer.toString(MistakesIndexesArray.length));

                for(int s:MistakesIndexesArray) {
                    Log.v(TAG + "A", Integer.toString(s + 1));
                    Element element = doc.createElement("element");
                    element.appendChild(doc.createTextNode(Integer.toString(s)));
                    NameElementMistakes.appendChild(element);
                }
            }
            else  {
                NameElementMistakes.setAttribute("size", Integer.toString(0));
            }

            Element NameElementAbsoluteSize = doc.createElement("absolute");
            Log.v(TAG, Name);
            NameElementAbsoluteSize.appendChild(doc.createTextNode(Integer.toString(AbsoluteSize)));


            Element NameElementCurID = doc.createElement("curID");
            NameElementCurID.appendChild(doc.createTextNode(Integer.toString(Question.getID())));

            NameElementTitle.appendChild(NameElementPath);
            NameElementTitle.appendChild(NameElementNumber);
            NameElementTitle.appendChild(NameElementShow);
            NameElementTitle.appendChild(NameElementRightAnswers);
            NameElementTitle.appendChild(NameElementMaxSize);
            NameElementTitle.appendChild(NameElementName);
            NameElementTitle.appendChild(NameElementMassive);
            NameElementTitle.appendChild(NameElementMistakes);
            NameElementTitle.appendChild(NameElementAbsoluteSize);
            NameElementTitle.appendChild(NameElementCurID);

            nNode.appendChild(NameElementTitle);

            Transformer transformer = TransformerFactory.newInstance()
                    .newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(getActivity().getFilesDir().getAbsolutePath()+"/" + filePath));
            transformer.transform(source, result);
            is.close();
            Log.v(TAG + "A", "Done");
        }
        catch (Exception e) {
            Log.v(TAG, "Error writing");
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
        //setRetainInstance(true);

        savedInstanceState.putInt("number", Number);
        savedInstanceState.putInt("rightAnswer", RightAnswers);
        savedInstanceState.putBoolean("show", Show);
        savedInstanceState.putBoolean("color", Colors);
        savedInstanceState.putInt("max", Max);
        savedInstanceState.putString("path", Path);
        savedInstanceState.putString("name", Name);
        savedInstanceState.putInt("mode", Mode);
        savedInstanceState.putIntArray("mistakesIndexesArray", MistakesIndexesArray);
        savedInstanceState.putInt("AbsoluteSize", AbsoluteSize);
        savedInstanceState.putParcelable("question", Question);
        savedInstanceState.putIntArray("mass", Mass);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (TestFragmentCheckBox.TestFragmentCheckBoxListener) context;
        this.barListener = (TestFragmentBarListener) context;
        this.backClickListener = (TestFragmentCheckBox.onBackClickListener) context;
        //Log.v(TAG, "Context");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        barListener.BarDrawerTrue();
        barListener = null;
        backClickListener = null;
        Log.v("WAWA", "Detach");
    }


    @Override
    public void onStop() {
        // clear back stack

        //getFragmentManager().popBackStack();

        super.onStop();
        Log.v("WAWA", "Stop");
    }

    @Override
    public void onDestroy() {
        // clear back stack
        super.onDestroy();
        Log.v("WAWA", "Destroy");

    }
}
