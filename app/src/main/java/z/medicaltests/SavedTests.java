package z.medicaltests;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SavedTests extends Fragment {


    private SavedBundle savedBundle[];
    private String Strokes[];
    private boolean condition;
    private static final String TAG = "XmlSavedReader";


    public void setMessage(SavedBundle savedBundle[]) {
        this.savedBundle = savedBundle;


        try {
            Strokes = new String[savedBundle.length];
            for (int i = 0; i < savedBundle.length; i++) {

                Strokes[i] = savedBundle[i].getName() +
                ": " + savedBundle[i].getNumber() + " вопрос из "
                        + savedBundle[i].getMaxSize();
                if(savedBundle[i].getShow()) {
                    Strokes[i] +=" Правильные показываются";
                }
                else {
                    Strokes[i] +=" Правильные не показываются";
                }
                condition = true;
            }
        }
        catch (Exception e) {condition = false;}
    }
    public SavedTests() {
        // Required empty public constructor
    }


    @Override
    public void onStart() {
        super.onStart();

        View view = getView();
        if(view!=null) {

            if(condition) {
                ListView listView = (ListView) view.findViewById(R.id.list_saved);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(),
                        android.R.layout.simple_list_item_multiple_choice, Strokes);

            /*
            AdapterView.OnItemClickListener itemClickListener =
                    new AdapterView.OnItemClickListener(){
                        public void onItemClick(AdapterView<?> listView,
                                                View v,
                                                int position,
                                                long id) {
                            if (listener != null) {
                                listener.itemClicked(id, Files, Test);
                            }
                        }
                    };*/

                listView.setAdapter(adapter);
                //listView.setOnItemClickListener(itemClickListener);
            }
            else {
                TextView textView = (TextView) view.findViewById(R.id.ww);
                textView.setText("Нет сохранений");
            }
        }


    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.getItem(2).setVisible(true);
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_all: {
                final String filePath = "Save_massive.xml";
                getActivity().deleteFile(filePath);

                View view = getView();
                ListView listView = (ListView) view.findViewById(R.id.list_saved);
                savedBundle = null;
                Strokes = null;
                listView.setAdapter(null);
                listView.invalidate();
                TextView textView = (TextView) view.findViewById(R.id.ww);
                textView.setText("Нет сохранений");

            }
        }

        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_saved_tests, container, false);
    }


    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }
}
