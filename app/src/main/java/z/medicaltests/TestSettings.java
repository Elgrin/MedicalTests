package z.medicaltests;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestSettings extends Fragment implements View.OnClickListener {

    private String File;
    private int Size;
    private String Text;
    private boolean Flag;
    private int Mode;
    //private  int Mass[];


    private static final String TAG = "SETTINGS";
    private TestSettings.TestSettingsListener listener;

    interface TestSettingsListener {
        void onButtonSettingsCommitListener(boolean show, int Size_all, int Size_exam, String File, int Mode);
    }

    public TestSettings() {
        // Required empty public constructor
    }

    public void SetMessage(String File, int Size,
                           String Text_1) {
        this.File = File;
        this.Size = Size;
        Text = Text_1 + " " + Integer.toString(Size);
        Flag = false;
        Mode = 0;
    }

    public  void SetMessage(String File, int Size,
                            String Text,
                            String Text_1) {
        this.File = File;
        this.Size = Size;
        this.Text = Text_1 + " " + Integer.toString(Size) + "\n" + Text;
        Flag = true;
        Mode = 1;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View layout = inflater.inflate(R.layout.fragment_test_settings, container, false);

        if (savedInstanceState != null) {
            File = savedInstanceState.getString("file");
            Size = savedInstanceState.getInt("size_int");
            Text = savedInstanceState.getString("text");
            Flag = savedInstanceState.getBoolean("flag");
            Mode = savedInstanceState.getInt("mode");
            //Mass = savedInstanceState.getIntArray("mass");
        }

        TextView textView = layout.findViewById(R.id.Size);
        textView.setText(Text);

        Button commit = layout.findViewById(R.id.commit_settings);
        commit.setOnClickListener(this);

        return layout;
    }

    @Override
    public  void onStart() {
        super.onStart();


        View view = getView();
        if (view!=null) {
            if(!Flag) {
                EditText editText = view.findViewById(R.id.Exam_size);
                editText.setVisibility(View.GONE);

            }

        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (TestSettings.TestSettingsListener) context;
        Log.v(TAG, "Context");
    }
    @Override
    public  void onDetach() {
        super.onDetach();
        listener = null;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file", File);
        outState.putInt("size_int", Size);
        outState.putString("text", Text);
        outState.putBoolean("flag", Flag);
        outState.putInt("mode", Mode);
    }


    private void Alert(String Error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Ошибка!")
                .setMessage(Error)
                .setCancelable(false)
                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onClick(View view) {

        Log.v(TAG, "Click");

        View View = getView();

        if (View != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            int Size_exam;
            CheckBox checkBox = View.findViewById(R.id.Show_mistakes);
            boolean show = checkBox.isChecked();

            if (Flag) {
                EditText editText = View.findViewById(R.id.Exam_size);

                try {
                    Size_exam = Integer.parseInt(editText.getText().toString());
                } catch (Exception e) {
                    //Size_exam = 0;
                    Alert("Не выбрано количество вопросов.");
                    return;
                }
            } else {
                Size_exam = Size;
            }

            if (Size_exam > Size) {
                EditText editText = View.findViewById(R.id.Exam_size);
                editText.setText(null);
                Alert("Количество вопросов для теста больше доступного.");

            } else {
                if (Size_exam == 0) {
                    EditText editText = View.findViewById(R.id.Exam_size);
                    editText.setText(null);
                    Alert("Количество вопросов в тесте не должно равняться 0");
                    return;
                }
                if (listener != null) {
                    View layout = getView();
                    Button commit = layout.findViewById(R.id.commit_settings);
                    //commit.setText("Загрузка вопросов");
                    commit.setEnabled(false);

                    listener.onButtonSettingsCommitListener(show, Size, Size_exam, File, Mode);
                }
            }
        }


    }

}
