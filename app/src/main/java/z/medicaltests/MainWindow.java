package z.medicaltests;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Random;


public class MainWindow extends Activity implements XmlReader.XmlReaderListener,
        ButtonMenu.ButtonMenuListener,
        TestSettings.TestSettingsListener,
        TestFragmentCheckBox.TestFragmentCheckBoxListener,
        ResultPage.ResultPageListener {

    //Переменные для заполнения выдвижной панели

    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private static final String TAG = "MAIN";

    //
    private String[] titles;
    private int currentPosition = 0;


    protected int[] Randomize(int Size, int Up) {
        int mass[] = new int[Size];

        //Up+=1;
        Random rnd = new Random(System.currentTimeMillis());

        first:
        for (int i = 0; i < Size; i++) {

            // Получаем случайное число в диапазоне от min до max (включительно)
            //Size_up-=Size_down;
            mass[i] = (int) (Math.random() * Up + 1);

            for (int j = 0; j < i; j++) {
                if (mass[i] == mass[j]) {
                    i--;
                    //mass[i] = 100;
                    continue first;
                }
            }

        }

        for (int i = 0; i < Size; i++) {

            int min = mass[i];
            int min_i = i;

            for (int j = i + 1; j < Size; j++) {
                //Если находим, запоминаем его индекс
                if (mass[j] < min) {
                    min = mass[j];
                    min_i = j;
                }
            }

            if (i != min_i) {
                int tmp = mass[i];
                mass[i] = mass[min_i];
                mass[min_i] = tmp;
            }
        }

        return mass;
    }

    protected int[] Randomize(int Size) {
        int mass[] = new int[Size];
        for (int i = 0; i < Size; i++) {
            mass[i] = i + 1;
        }
        return mass;
    }


    @Override
    public void onButtonResultPageListener() {


        /*
        FragmentManager fragmentManager = getFragmentManager();

        fragmentManager.popBackStack(0, FragmentManager.POP_BACK_STACK_INCLUSIVE);*/


        XmlReader fragment;
        fragment = new XmlReader();

        fragment.SetMessage(getResources().getString(R.string.subjects),
                getResources().getString(R.string.subjects_text), getAssets());

        ///Временно вынесено сюда
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, "fragment");
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

    }

    @Override
    public void onButtonCheckBoxCommitListener(boolean Show,
                                               TestStructure Questions[],
                                               int Number,
                                               double RighAnswers,
                                               int Max) {

        if ((Max + 1) == (Number)) {

            ResultPage fragment;
            fragment = new ResultPage();
            fragment.setMessage(RighAnswers, Max);

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment, "fragment");
            //ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();

            TextView text = (TextView) findViewById(R.id.it);
            text.setText(Double.toString(RighAnswers));
        } else {

            TextView text = (TextView) findViewById(R.id.it);
            text.setText(Double.toString(RighAnswers) + " " + Integer.toString(Number));

            TestFragmentCheckBox fragment;
            fragment = new TestFragmentCheckBox();
            fragment.SetMessage(Questions, Number, Show, false, RighAnswers, Max);

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment, "fragment");
            //ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }

    }

    @Override
    public void onButtonCommitListener(boolean Show, int Size_all, int Size_exam, String File) {
        TextView text = (TextView) findViewById(R.id.it);

        int mass[];
        if (Size_all == Size_exam) {
            mass = Randomize(Size_exam);
            //text.append(" " + Integer.toString(Size_all) + " ");
        } else {
            mass = Randomize(Size_exam, Size_all);
        }

         /*
         text.setText("");
        for(int i = 0; i < mass.length; i++) {
            text.append(Integer.toString(mass[i]));
        }*/


        XmlTestLoader loader = new XmlTestLoader(File, getAssets(), mass);


        TestStructure Questions[] = loader.getTestStructure();

        TestFragmentCheckBox fragment;
        fragment = new TestFragmentCheckBox();
        fragment.SetMessage(Questions, 1, Show, false, 0, Size_exam);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, "fragment");

        //ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

    }

    @Override
    public void onButtonClickAllQuestions(int Size, String File) {
        TextView text = (TextView) findViewById(R.id.it);

        text.setText(Integer.toString(Size));

        TestSettings fragment;
        fragment = new TestSettings();
        fragment.SetMessage(File, Size,
                getResources().getString(R.string.text_mistakes_1),
                getResources().getString(R.string.text_mistakes_2));

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, "fragment");
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

    }

    @Override
    public void onButtonClickExamMode(int Size, String File) {
        //TextView text = (TextView) findViewById(R.id.it);
        //text.setText("Hello_2");

        TestSettings fragment;
        fragment = new TestSettings();
        fragment.SetMessage(File, Size,

                getResources().getString(R.string.text_mistales_set),
                getResources().getString(R.string.text_mistakes_1),
                getResources().getString(R.string.text_mistakes_2));

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, "fragment");
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    @Override
    public void itemClicked(long id, String[] Files, String[] Test) {

        if(Files[0] != null) {
            if (!(Files[(int) id].equals(""))) {
                XmlReader fragment;
                fragment = new XmlReader();
                fragment.SetMessage(Files[(int) id],
                        getResources().getString(R.string.themes_text), getAssets());


                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment, "fragment");
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        }

        if(Test[0] !=null){
            if(!(Test[(int)id].equals(""))){
                ButtonMenu fragment;
                fragment = new ButtonMenu();
                fragment.setMessage((Test[(int) id]),
                        getResources().getString(R.string.mode_text), getAssets());

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment, "fragment");
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        }


        TextView text = (TextView) findViewById(R.id.it);
        try {
            text.setText(Test[0]);} catch (Exception e) {
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //Заполнение панели строковыми знаечниями
        titles = getResources().getStringArray(R.array.menu);

        drawerList = (ListView) findViewById(R.id.drawer);
        drawerList.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_activated_1,
                titles));

        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        //При первом запуске выбор первого фрагмента
        if (savedInstanceState == null) {
            selectedItem(0);
        }
        else {
            currentPosition = savedInstanceState.getInt("position");
            setActionBarTitle(currentPosition);
        }

        //Создание ActionBarDrawerToggle
        drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer) {
            //Вызывается при переходе выдвижной панели в полностью закрытое состояние.
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            //Вызывается при переходе выдвижной панели в полностью открытое состояние.
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.addDrawerListener(drawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        //Прослушка нажатий кнопок
        drawerList.setOnItemClickListener(new DrawerItemClickListener());


        //Обеспечение корректного отображения
        // заголовка меню при нажатии кнопки возврата
        getFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        FragmentManager fragMan = getFragmentManager();
                        Fragment fragment = fragMan.findFragmentByTag("fragment");
                        if (fragment instanceof XmlReader) {
                            currentPosition = 0;
                        }


                        setActionBarTitle(currentPosition);
                        drawerList.setItemChecked(currentPosition, true);
                    }

                }
        );

    }

    protected void Alert(String Error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
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
    public void onBackPressed() {
        FragmentManager fragMan = getFragmentManager();
        Fragment fragment = fragMan.findFragmentByTag("fragment");

        if (fragment instanceof TestFragmentCheckBox
                || fragment instanceof Check_Boxes
                || fragment instanceof ResultPage) {
            //Alert("z");
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Если выдвижная панель открыта, скрыть элементы, связанные с контентом
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        // menu.findItem(R.id.action_share).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Код для обработки нажатия
            selectedItem(position);
        }
    }

    private void selectedItem(int position) {
        XmlReader fragment;
        currentPosition = position;

        switch (position) {

            case 1: {
                fragment = new XmlReader();
                //fragment.SetMessage(getResources().getString(R.string.subjects));
                break;
            }
            case 2: {
                //О приложении
                fragment = new XmlReader();
                break;
            }
            case 3: {
                //О приложении
                fragment = new XmlReader();
                break;
            }
            default: {
                fragment = new XmlReader();

                fragment.SetMessage(getResources().getString(R.string.subjects),
                        getResources().getString(R.string.subjects_text), getAssets());

                ///Временно вынесено сюда
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment, "fragment");
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();

            }


        }
        setActionBarTitle(position);
        drawerList.setItemChecked(currentPosition, true);
        drawerLayout.closeDrawer(drawerList);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", currentPosition);
    }


    private void setActionBarTitle(int position) {
        String title;
        title = titles[position];
        getActionBar().setTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }*/
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

}
