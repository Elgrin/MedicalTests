package z.medicaltests;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Random;

     public   class MainWindow extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, XmlReader.XmlReaderListener,
        ButtonMenu.ButtonMenuListener,
        TestSettings.TestSettingsListener,
        TestFragmentCheckBox.TestFragmentCheckBoxListener,
        ResultPage.ResultPageListener,
        TestFragmentCheckBox.TestFragmentBarListener,
        TestFragmentCheckBox.onBackClickListener {

    //Переменные для заполнения выдвижной панели


    private FirebaseAnalytics mFirebaseAnalytics;

    //private ListView drawerList;
    //private DrawerLayout drawerLayout;
    private static final String TAG = "MAIN";
    //private boolean MenuFlag = false;
    private String[] titles;
    private int currentPosition = 0;


    public void BarDrawerTrue(boolean MenuFlag) {
        //this.MenuFlag = MenuFlag;
        invalidateOptionsMenu();
    }

    @Override
    public void onBackClick() {
        getFragmentManager().popBackStack();
        selectedItem(0);
    }
    public void BarDrawer(String Name, String Path, TestStructure Questions,
                          boolean Show, int Max, int Mode, int MistakesIndexesArray[], int AbsoluteSize, int Mass[]) {

        TestFragmentCheckBox fragment;
        fragment = new TestFragmentCheckBox();

        XmlTestLoader loader = new XmlTestLoader(Path, getAssets(), Mass[0]);
        TestStructure Question = loader.getTestStructure();

        fragment.SetMessage(Name, Path,
                Question,
                1, Show, false, 0, Max, Mode, null, AbsoluteSize, Mass);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, "fragment");
        //ft.disallowAddToBackStack();

        try {
            FragmentManager fragMan = getSupportFragmentManager();
            Fragment frag = fragMan.findFragmentByTag("fragment");

            if (frag.getClass().toString().equals("class z.medicaltests.TestFragmentCheckBox")) {
                for(int i = 0; i < getFragmentManager().getBackStackEntryCount(); ++i) {
                    getFragmentManager().popBackStack();
                }
            }
        }
        catch (Exception e) {Log.v("Error", "Error in popstack");}

        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_advertisment, menu);

        //MenuItem item = menu.getItem(0);
        //item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);

        //if (!MenuFlag)
        {
            for (int i = 0; i < menu.size(); i++)
                menu.getItem(i).setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);
    }


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

        for (int i = 0; i < mass.length; i++) {
            Log.v("RAND", Integer.toString(mass[i]));
        }

        /*
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
        }*/

        return mass;
    }

    protected static int[] Randomize(int Size) {
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

        fragment.SetMessage(getResources().getString(R.string.subjects), getAssets());

        ///Временно вынесено сюда
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, "fragment");

        try {
            FragmentManager fragMan = getSupportFragmentManager();
            Fragment frag = fragMan.findFragmentByTag("fragment");

            if (frag.getClass().toString().equals("class z.medicaltests.TestFragmentCheckBox")) {
                for(int i = 0; i < getFragmentManager().getBackStackEntryCount(); ++i) {
                    getFragmentManager().popBackStack();
                };
            }
        }
        catch (Exception e) {Log.v("Error", "Error in popstack");}

        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

    }


    @Override
    public void onButtonCommitListener(String Name, String Path, boolean Show,
                                               TestStructure Question,
                                               int Number,
                                               int RighAnswers,
                                               int Max,
                                               int Mode,
                                               int[] MistakesIndexesArray,
                                               int AbsoluteSize, int Mass[]) {

        if ((Max + 1) == (Number)) {

            ResultPage fragment;
            fragment = new ResultPage();
            fragment.setMessage(RighAnswers, Max,
                    Name,
                    Path,
                    Show,
                    Max,
                    Mode,
                    MistakesIndexesArray, Mass, AbsoluteSize);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            FragmentManager f = getSupportFragmentManager();
            f.popBackStack();
            ft.replace(R.id.content_frame, fragment, "fragment");

            try {
                FragmentManager fragMan = getSupportFragmentManager();
                Fragment frag = fragMan.findFragmentByTag("fragment");

                if (frag.getClass().toString().equals("class z.medicaltests.TestFragmentCheckBox")) {
                    for(int i = 0; i < getFragmentManager().getBackStackEntryCount(); ++i) {
                        getFragmentManager().popBackStack();
                    }
                }
            }
            catch (Exception e) {Log.v("Error", "Error in popstack");}

            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();

        } else {


            TestFragmentCheckBox fragment;
            fragment = new TestFragmentCheckBox();

            XmlTestLoader loader = new XmlTestLoader(Path, getAssets(), Mass[Number-1]);
            TestStructure Question_1 = loader.getTestStructure();
            fragment.SetMessage(Name, Path,
                    Question_1,
                    Number, Show, false, RighAnswers, Max, Mode,
                    MistakesIndexesArray,
                    AbsoluteSize, Mass);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment, "fragment");
            //ft.disallowAddToBackStack();

            try {
                FragmentManager fragMan = getSupportFragmentManager();
                Fragment frag = fragMan.findFragmentByTag("fragment");

                if (frag.getClass().toString().equals("class z.medicaltests.TestFragmentCheckBox")) {
                    for(int i = 0; i < getFragmentManager().getBackStackEntryCount(); ++i) {
                        getFragmentManager().popBackStack();
                    }
                }
            }
            catch (Exception e) {Log.v("Error", "Error in popstack");}

            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }

    }

    @Override
    public void onButtonSettingsCommitListener(boolean Show, int Size_all, int Size_exam, String File, int Mode) {

        int mass[];


        if (Mode == 0) {
            mass = Randomize(Size_exam);
            //text.append(" " + Integer.toString(Size_all) + " ");
        } else {
            //ИЗМЕНИТЬ ПРИ СДАЧЕ - уже
            mass = Randomize(Size_exam, Size_all);
            //mass = Randomize(Size_exam);
        }

        //mass=Randomize(Size_exam, Size_all);

         /*
         text.setText("");
        for(int i = 0; i < mass.length; i++) {
            text.append(Integer.toString(mass[i]));
        }*/


        TestFragmentCheckBox fragment;
        fragment = new TestFragmentCheckBox();

        XmlTestLoader loader = new XmlTestLoader(File, getAssets(), mass[0]);
        TestStructure Question = loader.getTestStructure();
        String Path = loader.getPath();
        String Name = loader.getName();
        fragment.SetMessage(Name, Path,
                Question,
                1, Show, false, 0, Size_exam, Mode, null, Size_exam, mass);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, "fragment");

        try {
            FragmentManager fragMan = getSupportFragmentManager();
            Fragment frag = fragMan.findFragmentByTag("fragment");

            if (frag.getClass().toString().equals("class z.medicaltests.TestFragmentCheckBox")) {
                for(int i = 0; i < getFragmentManager().getBackStackEntryCount(); ++i) {
                    getFragmentManager().popBackStack();
                }
            }
        }
        catch (Exception e) {Log.v("Error", "Error in popstack");}

        //ft.disallowAddToBackStack();
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

    }

    @Override
    public void onButtonClickAllQuestions(int Size, String File) {


        TestSettings fragment;
        fragment = new TestSettings();
        fragment.SetMessage(File, Size,
                getResources().getString(R.string.text_mistakes_1),
                getResources().getString(R.string.text_mistakes_2));

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, "fragment");

        try {
            FragmentManager fragMan = getSupportFragmentManager();
            Fragment frag = fragMan.findFragmentByTag("fragment");

            if (frag.getClass().toString().equals("class z.medicaltests.TestFragmentCheckBox")) {
                for(int i = 0; i < getFragmentManager().getBackStackEntryCount(); ++i) {
                    getFragmentManager().popBackStack();
                };
            }
        }
        catch (Exception e) {Log.v("Error", "Error in popstack");}

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

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, "fragment");

        try {
            FragmentManager fragMan = getSupportFragmentManager();
            Fragment frag = fragMan.findFragmentByTag("fragment");

            if (frag.getClass().toString().equals("class z.medicaltests.TestFragmentCheckBox")) {
                for(int i = 0; i < getFragmentManager().getBackStackEntryCount(); ++i) {
                    getFragmentManager().popBackStack();
                }
            }
        }
        catch (Exception e) {Log.v("Error", "Error in popstack");}

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
                fragment.SetMessage(Files[(int) id], getAssets());

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment, "fragment");

                try {
                    FragmentManager fragMan = getSupportFragmentManager();
                    Fragment frag = fragMan.findFragmentByTag("fragment");

                    if (frag.getClass().toString().equals("class z.medicaltests.TestFragmentCheckBox")) {
                        for(int i = 0; i < getFragmentManager().getBackStackEntryCount(); ++i) {
                            getFragmentManager().popBackStack();
                        }
                    }
                }
                catch (Exception e) {Log.v("Error", "Error in popstack");}

                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
            else {
                toastMessage("Доступно только в платной версии!");
            }
        }

        if(Test[0] !=null){
            if(!(Test[(int)id].equals(""))){

                /*ИЗМЕНИТЬ!*.*/

                ButtonMenu fragment;
                fragment = new ButtonMenu();
                fragment.setMessage((Test[(int) id]),
                        getResources().getString(R.string.mode_text), getAssets());

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment, "fragment");

                try {
                    FragmentManager fragMan = getSupportFragmentManager();
                    Fragment frag = fragMan.findFragmentByTag("fragment");

                    if (frag.getClass().toString().equals("class z.medicaltests.TestFragmentCheckBox")) {
                        for(int i = 0; i < getFragmentManager().getBackStackEntryCount(); ++i) {
                            getFragmentManager().popBackStack();
                        }
                    }
                }
                catch (Exception e) {Log.v("Error", "Error in popstack");}

                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();

                /*
                String File = (Test[(int) id]);
                XmlTestLoader loader = new XmlTestLoader(File, getAssets());
                int Size = loader.getSize();
                onButtonClickExamMode(Size, File);*/

            }
            else {
                toastMessage("Доступно только в платной версии!");
            }
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            onNavigationItemSelected(navigationView.getMenu().getItem(0));
        }
        else {
            currentPosition = savedInstanceState.getInt("currentPosition)");
            onNavigationItemSelected(navigationView.getMenu().getItem(currentPosition));
        }


        //Обеспечение корректного отображения
        // заголовка меню при нажатии кнопки возврата
        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        FragmentManager fragMan = getSupportFragmentManager();
                        Fragment fragment = fragMan.findFragmentByTag("fragment");

                        if (fragment instanceof AboutFragment) {
                            currentPosition = 3;
                        }
                        else if (fragment instanceof HelpFragment) {
                            currentPosition = 2;
                        }
                        else if (fragment instanceof SavedTests) {
                            currentPosition = 1;
                        }
                        else {
                            currentPosition = 0;
                        }


                       // drawerList.setItemChecked(currentPosition, false);
                    }

                }
        );

        //Блок рекламы

        final AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mAdView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                mAdView.setVisibility(View.GONE);
            }
        });
    }

    protected void Alert(String Error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Сообщение:")
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

    private void toastMessage(String message) {

        AlertDialog.Builder ad;
        ad = new AlertDialog.Builder(this);

        ad.setTitle("Сообщение:");  // заголовок
        ad.setMessage(message); // сообщение
        ad.setCancelable(false);

        ad.setPositiveButton("Перейти в магазин", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {

                //final String appPackageName = getPackageName(); //
                final String appPackageName = "z.medicaltests";

                Log.v("Name", getPackageName());
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }

            }
        });
        ad.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {

            }
        });

        ad.show();
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            FragmentManager fragMan = getSupportFragmentManager();
            Fragment fragment = fragMan.findFragmentByTag("fragment");

            Log.v("COUNT", fragment.getClass().toString());
            Log.v("COUNT", Integer.toString(getFragmentManager().getBackStackEntryCount()));
            if (fragment.getClass().toString().equals("class z.medicaltests.TestFragmentCheckBox")) {
                Log.v("COUNT", "Haha");
            }
        /*
        if (fragment instanceof TestFragmentCheckBox
                || fragment instanceof Check_Boxes
                || fragment instanceof ResultPage
                //|| fragMan.getBackStackEntryCount() == 1)
        ) {
            //Alert("z");
            // super.onBackPressed();
        }
        else {
            if(fragMan.getBackStackEntryCount() <= 1) {
                finish();
            }
                else
                    super.onBackPressed();
        }*/

            //FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content_frame);
            //frameLayout.removeAllViews();

            if (fragMan.getBackStackEntryCount() <= 1) {
                if (fragment.getClass().toString().equals("class z.medicaltests.XmlReader")) {
                    finish();
                } else {
                    getFragmentManager().popBackStack();
                    selectedItem(0);
                }
                Log.v("COUNT", "YES");
            } else {
                super.onBackPressed();
                Log.v("COUNT", "No");
            }
        }
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Если выдвижная панель открыта, скрыть элементы, связанные с контентом
        //boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
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
        currentPosition = position;
        switch (position) {

            case 1: {

                XmlSavedReader loader = new XmlSavedReader(getBaseContext());

                SavedTests fragment;
                fragment = new SavedTests();
                fragment.setMessage(loader.getBundle());
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment, "fragment");

                try {
                    FragmentManager fragMan = getSupportFragmentManager();
                    Fragment frag = fragMan.findFragmentByTag("fragment");

                    if (frag.getClass().toString().equals("class z.medicaltests.TestFragmentCheckBox")) {
                        for(int i = 0; i < getFragmentManager().getBackStackEntryCount(); ++i) {
                            getFragmentManager().popBackStack();
                        }
                    }
                }
                catch (Exception e) {Log.v("Error", "Error in popstack");}

                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();

                //fragment.SetMessage(getResources().getString(R.string.subjects));
                break;
            }
            case 2: {
                //Справка
                //fragment =
                HelpFragment fragment = new HelpFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment, "fragment");

                try {
                    FragmentManager fragMan = getSupportFragmentManager();
                    Fragment frag = fragMan.findFragmentByTag("fragment");

                    if (frag.getClass().toString().equals("class z.medicaltests.TestFragmentCheckBox")) {
                        for(int i = 0; i < getFragmentManager().getBackStackEntryCount(); ++i) {
                            getFragmentManager().popBackStack();
                        }
                    }
                }
                catch (Exception e) {Log.v("Error", "Error in popstack");}

                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();

                break;
            }
            case 3: {
                //О приложении
                //fragment = new XmlReader();

                AboutFragment fragment = new AboutFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment, "fragment");

                try {
                    FragmentManager fragMan = getSupportFragmentManager();
                    Fragment frag = fragMan.findFragmentByTag("fragment");

                    if (frag.getClass().toString().equals("class z.medicaltests.TestFragmentCheckBox")) {
                        for(int i = 0; i < getFragmentManager().getBackStackEntryCount(); ++i) {
                            getFragmentManager().popBackStack();
                        }
                    }
                }
                catch (Exception e) {Log.v("Error", "Error in popstack");}

                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();

                break;
            }
            default: {
                XmlReader fragment;
                fragment = new XmlReader();

                fragment.SetMessage(getResources().getString(R.string.subjects), getAssets());

                ///Временно вынесено сюда
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment, "fragment");

                try {
                    FragmentManager fragMan = getSupportFragmentManager();
                    Fragment frag = fragMan.findFragmentByTag("fragment");

                    if (frag.getClass().toString().equals("class z.medicaltests.TestFragmentCheckBox")) {
                        for(int i = 0; i < getFragmentManager().getBackStackEntryCount(); ++i) {
                            getFragmentManager().popBackStack();
                        }
                    }
                }
                catch (Exception e) {Log.v("Error", "Error in popstack");}

                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();

            }


        }
        //drawerList.setItemChecked(currentPosition, false);
        //drawerLayout.closeDrawer(drawerList);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", currentPosition);
        Log.v(TAG, "as");
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.tests) {

            currentPosition = 0;

            XmlReader fragment;
            fragment = new XmlReader();
            fragment.SetMessage(getResources().getString(R.string.subjects), getAssets());
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment, "fragment");
            ft.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            Log.v("Entry", Integer.toString(fragmentManager.getBackStackEntryCount()));
            if(fragmentManager.getBackStackEntryCount() != 0) {
                ft.addToBackStack(null);
            }
            ft.commit();

        } else if (id == R.id.bookmarks) {

            currentPosition = 1;

            SavedTests fragment;
            fragment = new SavedTests();
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment, "fragment");
            ft.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();

        } else if (id == R.id.faq) {

            currentPosition = 2;

            HelpFragment fragment;
            fragment = new HelpFragment();
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment, "fragment");
            ft.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();


        } else if (id == R.id.about) {

            currentPosition = 3;

            AboutFragment fragment;
            fragment = new AboutFragment();
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment, "fragment");
            ft.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
