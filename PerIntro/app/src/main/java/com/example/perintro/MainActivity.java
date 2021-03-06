package com.example.perintro;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.perintro.adapter.PlanetListAdapter;
import com.example.perintro.application.AboutMe;
import com.example.perintro.application.BannerAnim;
import com.example.perintro.application.BatteyInfo;
import com.example.perintro.application.CalculatorActivity;
import com.example.perintro.application.DatePickerActivity;
import com.example.perintro.application.DateTimeDialog;
import com.example.perintro.application.DateTimeDialogOnlyYMD;
import com.example.perintro.application.SeekBarDemo;
import com.example.perintro.application.SersorActivity;
import com.example.perintro.application.TimePickerActivity;
import com.example.perintro.application.VibratorDevice;
import com.example.perintro.application.VolumeActivity;
import com.example.perintro.base.SearchActivity;
import com.example.perintro.bean.PersonInfo;
import com.example.perintro.bean.App;
import com.example.perintro.database.PersonDBHelper;
import com.example.perintro.util.DateUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,
        CompoundButton.OnCheckedChangeListener, View.OnClickListener, View.OnFocusChangeListener,
        DateTimeDialogOnlyYMD.MyOnDateSetListener, DateTimeDialog.MyOnDateSetListener {



    //???????????????
        //????????????????????????
    private String Now_selected = null;
        //????????????????????????
    private int Now_Record_Number = 0;
        //?????????????????????
    private boolean Wondeful_State = false;
    private boolean Save_State = false;
        //?????????????????????
    private boolean my_State = false;


    // ?????? ????????? ??????
    //private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
    private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy.MM");
    private DateTimeDialogOnlyYMD dateTimeDialogOnlyYM;
    private DateTimeDialog dateTimeDialog;
    private TextView text_label;
    private WebView wb_discover;
    private ProgressBar pg1;
    private Spinner sp_person;

    private ViewPager viewPager;
    private RadioGroup rg_bottom;

    private Drawable my_white;
    private Drawable my_pure;
    private Drawable home_white;
    private Drawable home_pure;
    private Drawable data_white;
    private Drawable data_pure;
    private Drawable discover_white;
    private Drawable discover_pure;

    private Drawable ic_search_white,ic_add_white,ic_refresh_white,ic_share_pure;
    private Drawable ic_fanhui_pure, ic_sandian_pure;
    private Drawable my_fill;

    private Button btn_search,btn_add,btn_left;
    private Button btn_chatroom_left,btn_chatroom_right;

    private RadioButton rb_my;
    private RadioButton rb_home;
    private RadioButton rb_data;
    private RadioButton rb_discover;

    private PagerAdapter mPagerAdapter;
    private int btn_refresh_state = 0;
    private List<View> mViews = new ArrayList<View>();

    //Data?????????
    private TextView tv_data_tips;
    private boolean sw_Edit_State,sw_Wonderful_State,sw_Save_State;
    private Switch sw_Edit;
    private Switch sw_Wonderful;
    private TextView Tv_EditClose;
    private TextView Tv_EditOpen;
    private Switch sw_Save;
    private TextView Tv_SaveClose;
    private TextView Tv_SaveOpen;
    private RadioGroup mRgPicSize;
    private RadioButton mRbHigh;
    private RadioButton mRbMid;
    private RadioButton mRbLow;
    //Data???????????????
    private EditText edit_name ,edit_bir ,edit_phone ,edit_edu ,edit_gra ,edit_major ,edit_target ,edit_skill ,edit_hobby;
    private EditText[] EditGroup = {edit_name,edit_bir,edit_phone, edit_edu,edit_gra,
            edit_major,edit_target, edit_skill,edit_hobby};
    private PersonDBHelper mHelper; // ?????????????????????????????????????????????
    private String[] PersonList = new String[0];

    //??????????????????
    private AlertDialog alertDialog1; //?????????
    private AlertDialog alertDialog2; //?????????
    private AlertDialog alertDialog3; //?????????
    private String[] edu_list = {"??????","??????","??????","??????","??????","??????"};
    private String[] edu_wonderful_list = {"??????","??????","??????","??????","??????","?????????","????????????","????????????","????????????","????????????","??????"};
    //????????????
    private String[] gra_list = {"????????????","??????????????????","??????????????????????????????","?????????"};
    private String[] gra_wonderful_list = {"????????????????????????","???????????????","?????????????????????","?????????"};
    private String[] major_list = {"???????????????","?????????","?????????","?????????"};
    private String[] major_wonderful_list = {"????????????","?????????","?????????","?????????"};
    private String[] target_list = {"??????????????????","JAVA?????????","PCB???????????????","Android???????????????","?????????"};
    private String[] target_wonderful_list = {"??????????????????","??????????????????","???????????????","????????????","?????????"};
    //??????????????????
    private String[] skill_list = {"??????","??????","??????","??????"};
    private String[] skill_wonderful_list = {"??????","????????????","????????????","????????????????????????"};
    private String[] hobby_list = {"??????","??????","??????","??????"};
    private String[] hobby_wonderful_list = {"??????","??????????????????????????????","?????????","????????????"};
    private boolean Bir_focus_state = false;
    private boolean Edu_focus_state = false;
    private boolean Gra_focus_state = false;
    private boolean Major_focus_state = false;
    private boolean Target_focus_state = false;
    private boolean Skill_focus_state = false;
    private boolean Hobby_focus_state = false;







    private Button btn_data_ok , btn_delete;
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    //MY?????????
    private ImageView iv_head;
    private Bitmap head;// ??????Bitmap
    private TextView tv_sqlite_my;
    private LinearLayout ly_resume;
    private Handler mHandler;//??????????????????
    private TextView tv_name, tv_age, tv_phone, tv_edu, tv_gra, tv_major, tv_target, tv_skill, tv_hobby;


    //HOME????????????
    private ListView List_home; // ??????????????????????????????

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK) {

            // ???????????????????????????
            if (Build.VERSION.SDK_INT >= 19) {
                // 4.4?????????????????????????????????????????????
                handleImageOnKitKat(data);
            } else {
                // 4.4??????????????????????????????????????????
                handleImageBeforeKitKat(data);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //???????????????
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();}

        initViews();
        initEvents();

    }

    //???????????????????????????
    @Override
    protected void onStart() {
        super.onStart();
        // ?????????????????????????????????
        mHelper = PersonDBHelper.getInstance(this, 2);
        // ?????????????????????????????????
        mHelper = PersonDBHelper.getInstance(this, 2);
        // ????????????????????????????????????
        mHelper.openWriteLink();
        // ??????????????????????????????????????????????????????????????????
        PersonInfo info = new PersonInfo();
        info.name = "XFuture";
        info.birthday_YM = "2001.01";
        info.telephoneNumber = "18012341234";
        info.education = "??????";
        info.graduate_University = "????????????????????????";
        info.major = "????????????";
        info.target_Work = "??????????????????";
        info.skill = "??????";
        info.hobby = "????????????";
        info.update_time = DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss");
        // ???????????????????????????????????????
        mHelper.insert(info);
        //???????????????
        mHelper.openReadLink();
        readSQLite();
    }
    private boolean needExit = false; // ??????????????????App
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { // ???????????????
            if (needExit) {
                finish(); // ??????????????????
            }
            needExit = true;
            Toast.makeText(this, "???????????????????????????!", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void initEvents(){
        rg_bottom.setOnCheckedChangeListener(this);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }
        //?????????????????????????????????
            @Override
            public void onPageSelected(int i) {
                int currentItem = viewPager.getCurrentItem();
                resetButtonImg();
                switch(currentItem){
                    case 0:
                        rb_home.setCompoundDrawables(null,home_pure,null,null);
                        rb_home.setTextColor(Color.parseColor("#FFAAAAFF"));
                        text_label.setText("??????");
                        my_State = false;
                        break;
                    case 1:
                        rb_data.setCompoundDrawables(null,data_pure,null,null);
                        rb_data.setTextColor(Color.parseColor("#FFAAAAFF"));
                        text_label.setText("??????");
                        my_State = false;
                        break;
                    case 2:
                        rb_discover.setCompoundDrawables(null,discover_pure,null,null);
                        rb_discover.setTextColor(Color.parseColor("#FFAAAAFF"));
                        btn_left.setCompoundDrawables(null, ic_refresh_white, null, null);
                        btn_refresh_state=1;
                        btn_left.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View view) {
                                if(btn_refresh_state==1){
                                    wb_discover.reload();
                                }
                            }
                        });
                        text_label.setText("????????????");
                        my_State = false;
                        break;
                    case 3:
                        rb_my.setCompoundDrawables(null,my_pure,null,null);
                        rb_my.setTextColor(Color.parseColor("#FFAAAAFF"));
                        //????????????????????????
                        readSQLite();

                        //???????????????
                        text_label.setText("");
                        btn_search.setCompoundDrawables(null, null, null, null);
                        btn_add.setCompoundDrawables(null, ic_share_pure, null, null);
                        my_State = true;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initViews() {
        //????????????
        LayoutInflater inflater = LayoutInflater.from(this);
        View tab01 = inflater.inflate(R.layout.home, null);
        View tab02 = inflater.inflate(R.layout.data, null);
        View tab03 = inflater.inflate(R.layout.discover, null);
        View tab04 = inflater.inflate(R.layout.my, null);
        mViews.add(tab01);
        mViews.add(tab02);
        mViews.add(tab03);
        mViews.add(tab04);

        //??????
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        //???????????????
        text_label = (TextView) findViewById(R.id.text_label);
        btn_search = (Button) findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
        btn_left = (Button) findViewById(R.id.btn_left);

        //???????????????
        rg_bottom = (RadioGroup) findViewById(R.id.rg_bottom);
        rb_my = (RadioButton) findViewById(R.id.rb_my);
        rb_home = (RadioButton) findViewById(R.id.rb_home);
        rb_data = (RadioButton) findViewById(R.id.rb_data);
        rb_discover = (RadioButton) findViewById(R.id.rb_discover);


        //?????????????????????
        my_white = getResources().getDrawable(R.drawable.ic_my_white);
        my_white.setBounds(0, 0, 60, 60);
        my_pure = getResources().getDrawable(R.drawable.ic_my_pure);
        my_pure.setBounds(0, 0, 60, 60);
        home_white = getResources().getDrawable(R.drawable.ic_home_white);
        home_white.setBounds(0, 0, 60, 60);
        home_pure = getResources().getDrawable(R.drawable.ic_home_pure);
        home_pure.setBounds(0, 0, 60, 60);
        data_white = getResources().getDrawable(R.drawable.ic_data_white);
        data_white.setBounds(0, 0, 60, 60);
        data_pure = getResources().getDrawable(R.drawable.ic_data_pure);
        data_pure.setBounds(0, 0, 60, 60);
        discover_white = getResources().getDrawable(R.drawable.ic_discover_white);
        discover_white.setBounds(0, 0, 60, 60);
        discover_pure = getResources().getDrawable(R.drawable.ic_discover_pure);
        discover_pure.setBounds(0, 0, 60, 60);
        ic_search_white = getResources().getDrawable(R.drawable.ic_search_white);
        ic_search_white.setBounds(0, 0, 50, 50);
        ic_add_white = getResources().getDrawable(R.drawable.ic_add_white);
        ic_add_white.setBounds(0, 0, 50, 50);
        ic_refresh_white = getResources().getDrawable(R.drawable.ic_refresh_white);
        ic_refresh_white.setBounds(0, 0, 50, 50);
        ic_share_pure = getResources().getDrawable(R.drawable.ic_share);
        ic_share_pure.setBounds(0, 0, 50, 50);

        //??????
        btn_search.setCompoundDrawables(null, ic_search_white, null, null);
        btn_add.setCompoundDrawables(null, ic_add_white, null, null);
        //??????
        resetButtonImg();
        rb_home.setCompoundDrawables(null, home_pure, null, null);
        rb_home.setTextColor(this.getResources().getColor(R.color.mycolor));


        mPagerAdapter = new PagerAdapter() {

            @Override
            public int getCount() {
                return mViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = mViews.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mViews.get(position));
            }
        };
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setOffscreenPageLimit(4);

        //??????ListView==============================================================================


        ArrayList<App> appList = App.getDefaultList();
        // ??????????????????????????????????????????
        PlanetListAdapter planetListadapter = new PlanetListAdapter(this, appList) {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                super.onItemClick(parent, view, position, id);
                String desc = String.format("???????????????%d????????????%s", position + 1, getmPlanetList().get(position).name);
                String label_name = getmPlanetList().get(position).name;
                //Toast.makeText(getmContext(), desc, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, TimePickerActivity.class);
                switch (position) {
                    case 0:
                        intent.setClass(MainActivity.this, TimePickerActivity.class);
                        intent.putExtra("serial",position);
                        startActivity(intent);
                        break;
                    case 1:
                        intent.setClass(MainActivity.this, DatePickerActivity.class);
                        intent.putExtra("serial",position);
                        startActivity(intent);
                        break;
                    case 2:
                        intent.setClass(MainActivity.this, CalculatorActivity.class);
                        intent.putExtra("serial",position);
                        startActivity(intent);
                        break;
                    case 3:
                        intent.setClass(MainActivity.this, BatteyInfo.class);
                        intent.putExtra("serial",position);
                        startActivity(intent);
                        break;
                    case 4:
                        //intent.setClass(MainActivity.this, TimePickerActivity.class);
                        //intent.putExtra("serial",position);
                        //startActivity(intent);
                        break;
                    case 5:
                        intent.setClass(MainActivity.this, BannerAnim.class);
                        intent.putExtra("serial",position);
                        startActivity(intent);
                        break;
                    case 6:
                        intent.setClass(MainActivity.this, VibratorDevice.class);
                        intent.putExtra("serial",position);
                        startActivity(intent);
                        break;
                    case 7:
                        intent.setClass(MainActivity.this, SeekBarDemo.class);
                        intent.putExtra("serial",position);
                        startActivity(intent);
                        break;
                    case 8:
                        intent.setClass(MainActivity.this, VolumeActivity.class);
                        intent.putExtra("serial",position);
                        startActivity(intent);
                        break;
                    case 9:
                        intent.setClass(MainActivity.this, SersorActivity.class);
                        intent.putExtra("serial",position);
                        startActivity(intent);
                        break;
                    case 10:
                        //intent.setClass(MainActivity.this, TimePickerActivity.class);
                        //intent.putExtra("serial",position);
                        //startActivity(intent);
                        break;
                    case 11:
                        intent.setClass(MainActivity.this, AboutMe.class);
                        intent.putExtra("serial",position);
                        startActivity(intent);
                        break;
                }
            }
        };

        // ??????????????????????????????lv_planet???????????????
        // ??????????????????????????????????????????????????????????????????
        //List_home = findViewById(R.id.List_home);
        List_home = (ListView) tab01.findViewById(R.id.List_home);
        // ???lv_planet???????????????????????????
        List_home.setAdapter(planetListadapter);
        // ???lv_planet?????????????????????????????????
        List_home.setOnItemClickListener(planetListadapter);
        // ???lv_planet?????????????????????????????????
        List_home.setOnItemLongClickListener(planetListadapter);


        //?????????===================================================================================


        tv_data_tips = tab02.findViewById(R.id.tv_data_tips);
        //???????????????
        sw_Edit = tab02.findViewById(R.id.sw_edit);
        sw_Wonderful = tab02.findViewById(R.id.sw_wonderful);
        Tv_EditClose = tab02.findViewById(R.id.tv_wonderful_close);
        Tv_EditOpen = tab02.findViewById(R.id.tv_wonderful_open);
        sw_Save = tab02.findViewById(R.id.sw_save);
        Tv_SaveClose = tab02.findViewById(R.id.tv_sound_close);
        Tv_SaveOpen = tab02.findViewById(R.id.tv_sound_open);
        //?????????
        edit_name = tab02.findViewById(R.id.edit_name);
        edit_bir = tab02.findViewById(R.id.edit_bir);
        edit_phone = tab02.findViewById(R.id.edit_phone);
        edit_edu = tab02.findViewById(R.id.edit_edu);
        edit_gra = tab02.findViewById(R.id.edit_gra);
        edit_major = tab02.findViewById(R.id.edit_major);
        edit_target = tab02.findViewById(R.id.edit_target);
        edit_skill = tab02.findViewById(R.id.edit_skill);
        edit_hobby = tab02.findViewById(R.id.edit_hobby);
        setEditState(false);
        btn_delete = tab02.findViewById(R.id.btn_delete);
        btn_data_ok = tab02.findViewById(R.id.btn_data_ok);
        mRgPicSize = tab02.findViewById(R.id.rg_pic_size);

        mRbHigh = tab02.findViewById(R.id.rb_high);
        mRbMid = tab02.findViewById(R.id.rb_mid);
        mRbLow = tab02.findViewById(R.id.rb_low);

        sw_Edit.setOnCheckedChangeListener(this);
        sw_Wonderful.setOnCheckedChangeListener(this);
        sw_Save.setOnCheckedChangeListener(this);

        mRbHigh.setOnCheckedChangeListener(this);
        mRbMid.setOnCheckedChangeListener(this);
        mRbLow.setOnCheckedChangeListener(this);
        //mSwContinuePic.setOnCheckedChangeListener(this);
        //???????????????????????????????????????
        edit_name.setOnClickListener(this);
        edit_bir.setOnClickListener(this);
        dateTimeDialogOnlyYM = new DateTimeDialogOnlyYMD(this, this, false, true, true);
        dateTimeDialog = new DateTimeDialog(this, null, null);
        edit_phone.setOnClickListener(this);
        edit_edu.setOnClickListener(this);
        edit_gra.setOnClickListener(this);
        edit_major.setOnClickListener(this);
        edit_target.setOnClickListener(this);
        edit_skill.setOnClickListener(this);
        edit_hobby.setOnClickListener(this);
        btn_data_ok.setOnClickListener(this);
        btn_delete.setOnClickListener(this);

        edit_name.setOnFocusChangeListener(this);
        edit_bir.setOnFocusChangeListener(this);
        edit_phone.setOnFocusChangeListener(this);
        edit_edu.setOnFocusChangeListener(this);
        edit_gra.setOnFocusChangeListener(this);
        edit_major.setOnFocusChangeListener(this);
        edit_target.setOnFocusChangeListener(this);
        edit_skill.setOnFocusChangeListener(this);
        edit_hobby.setOnFocusChangeListener(this);

        //????????????
        /*edit_name.setKeyListener(null);
        edit_bir.setKeyListener(null);
        edit_phone.setKeyListener(null);
        edit_edu.setKeyListener(null);
        edit_gra.setKeyListener(null);
        edit_major.setKeyListener(null);
        edit_target.setKeyListener(null);
        edit_skill.setKeyListener(null);
        edit_hobby.setKeyListener(null);*/
        //??????????????????


        //String[] PersonArrayAdpter = {"??????", "??????????????????", "??????", "??????", "??????", "??????"};


        //Data???????????????????????????
        sp_person = tab02.findViewById(R.id.sp_person);
        PersonList = readSQLiteList();
        //S2:????????????????????????ArrayAdapter???
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.item_selected, PersonList);
        //S3:??????????????????????????????????????????
        arrayAdapter.setDropDownViewResource(R.layout.item_dropdown);
        //S4??????????????????????????????Spinner??????
        sp_person.setAdapter(arrayAdapter);
        sp_person.setPrompt("?????????");   //??????dialog???????????????
        sp_person.setSelection(0);
        sp_person.setOnFocusChangeListener(this);
        sp_person.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //?????????????????????
                if(i<Now_Record_Number){
                    //?????????????????????
                    sw_Edit.setChecked(false);
                    Now_selected = PersonList[i];
                    //??????????????????????????????
                    PersonInfo per = readSQLiteOne(Now_selected);
                    //????????????????????????
                    edit_name.setText(per.name);
                    edit_bir.setText(per.birthday_YM);
                    edit_phone.setText(per.telephoneNumber);
                    edit_edu.setText(per.education);
                    edit_gra.setText(per.graduate_University);
                    edit_major.setText(per.major);
                    edit_target.setText(per.target_Work);
                    edit_skill.setText(per.skill);
                    edit_hobby.setText(per.hobby);
                    btn_data_ok.setText("??????????????????????????????????????????");
                }else{
                    //?????????????????????
                    sw_Edit.setChecked(true);
                    Now_selected = null;
                    edit_name.setText("");
                    edit_bir.setText("");
                    edit_phone.setText("");
                    edit_edu.setText("");
                    edit_gra.setText("");
                    edit_major.setText("");
                    edit_target.setText("");
                    edit_skill.setText("");
                    edit_hobby.setText("");
                    btn_data_ok.setText("???????????????????????????");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //?????????===================================================================================
        tv_sqlite_my = tab04.findViewById(R.id.tv_sqlite_my);
        tv_name = tab04.findViewById(R.id.resume_name);
        tv_age = tab04.findViewById(R.id.resume_age);
        tv_phone = tab04.findViewById(R.id.resume_phone);
        tv_edu = tab04.findViewById(R.id.resume_edu);
        tv_gra = tab04.findViewById(R.id.resume_gra);
        tv_major = tab04.findViewById(R.id.resume_major);
        tv_target = tab04.findViewById(R.id.resume_target);
        tv_skill = tab04.findViewById(R.id.resume_skill);
        tv_hobby = tab04.findViewById(R.id.resume_hobby);
        ly_resume = tab04.findViewById(R.id.ly_resume);
        iv_head = tab04.findViewById(R.id.iv_head);
        iv_head.setOnClickListener(this);




        //?????????===================================================================================
        wb_discover = tab03.findViewById(R.id.wb_discover);
        pg1 = tab03.findViewById(R.id.pg_web);
        //String url = "file:///android_asset/example.html";
        String url = "http://xiaogui888.xyz";
        wb_discover.loadUrl(url);
        WebSettings seting = wb_discover.getSettings();
        seting.setJavaScriptEnabled(true);//??????webview??????javascript??????
        wb_discover.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO ???????????????????????????
                if (newProgress == 100) {
                    pg1.setVisibility(View.GONE);//??????????????????????????????
                } else {
                    pg1.setVisibility(View.VISIBLE);//????????????????????????????????????
                    pg1.setProgress(newProgress);//???????????????
                }
            }
        });

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        resetButtonImg();
        switch (i) {
            case R.id.rb_home:
                viewPager.setCurrentItem(0);
                break;
            case R.id.rb_data:
                viewPager.setCurrentItem(1);
                break;
            case R.id.rb_discover:
                viewPager.setCurrentItem(2);
                break;
            case R.id.rb_my:
                viewPager.setCurrentItem(3);
                break;
            default:
                break;
        }
    }

    private void resetButtonImg(){
        rb_my.setCompoundDrawables(null,my_white,null,null);
        rb_home.setCompoundDrawables(null,home_white,null,null);
        rb_data.setCompoundDrawables(null,data_white,null,null);
        rb_discover.setCompoundDrawables(null,discover_white,null,null);
        rb_my.setTextColor(this.getResources().getColor(R.color.white));
        rb_home.setTextColor(this.getResources().getColor(R.color.white));
        rb_data.setTextColor(this.getResources().getColor(R.color.white));
        rb_discover.setTextColor(this.getResources().getColor(R.color.white));
        btn_search.setCompoundDrawables(null, ic_search_white, null, null);
        btn_add.setCompoundDrawables(null, ic_add_white, null, null);
        btn_left.setCompoundDrawables(null, null, null, null);
        btn_refresh_state = 0;
    }
    //??????Date?????????????????????
    private void setEditState(boolean b){
        if(b == true){
            sw_Edit_State = true;
        }else{
            sw_Edit_State = false;
        }
            edit_name.setFocusable(b);
            edit_bir.setFocusable(b);
            edit_phone.setFocusable(b);
            edit_edu.setFocusable(b);
            edit_gra.setFocusable(b);
            edit_major.setFocusable(b);
            edit_target.setFocusable(b);
            edit_skill.setFocusable(b);
            edit_hobby.setFocusable(b);

            edit_name.setFocusableInTouchMode(b);
            edit_bir.setFocusableInTouchMode(b);
            edit_phone.setFocusableInTouchMode(b);
            edit_edu.setFocusableInTouchMode(b);
            edit_gra.setFocusableInTouchMode(b);
            edit_major.setFocusableInTouchMode(b);
            edit_target.setFocusableInTouchMode(b);
            edit_skill.setFocusableInTouchMode(b);
            edit_hobby.setFocusableInTouchMode(b);
    }

    //??????Data????????????????????????
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.sw_wonderful://????????????
                swWonderfulOnCheckChanged(b);
                break;
            case R.id.sw_save://??????????????????
                swSaveOnCheckChanged(b);
                break;
            case R.id.sw_edit://???????????????
                swEditOnCheckedChanged((Switch) compoundButton, b);
                break;
            case R.id.rb_high://????????????-???
                rbBigOnChecked((RadioButton) compoundButton, b);
                break;
            case R.id.rb_low://????????????-???
                rbSmallOnChecked((RadioButton) compoundButton, b);
                break;
            case R.id.rb_mid://????????????-???
                rbMidOnChecked((RadioButton) compoundButton, b);
                break;
        }
    }

    //??????????????????-???
    private void rbMidOnChecked(RadioButton compoundButton, boolean b) {
        if (b) {
            compoundButton.setTextColor(this.getResources().getColor(R.color.white));
        } else {
            compoundButton.setTextColor(this.getResources().getColor(R.color.black));
        }
    }
    //??????????????????-???
    private void rbBigOnChecked(RadioButton compoundButton, boolean b) {
        if (b) {
            // ((RadioButton) compoundButton).setBackgroundColor(this.getResources().getColor(R.color.pink));
            compoundButton.setBackground(this.getResources().getDrawable(R.drawable.rb_high_pink_background));
            compoundButton.setTextColor(this.getResources().getColor(android.R.color.white));
        } else {
            // ((RadioButton) compoundButton).setBackgroundColor(this.getResources().getColor(R.color.gray));
            compoundButton.setBackground(this.getResources().getDrawable(R.drawable.rb_high_gray_background));
            compoundButton.setTextColor(this.getResources().getColor(android.R.color.black));
        }
    }
    //??????????????????-???
    private void rbSmallOnChecked(RadioButton compoundButton, boolean b) {
        if (b) {
            // ((RadioButton) compoundButton).setBackgroundColor(this.getResources().getColor(R.color.pink));
            compoundButton.setBackground(this.getResources().getDrawable(R.drawable.rb_low_pink_background));
            compoundButton.setTextColor(this.getResources().getColor(android.R.color.white));
        } else {
            // ((RadioButton) compoundButton).setBackgroundColor(this.getResources().getColor(R.color.gray));
            compoundButton.setBackground(this.getResources().getDrawable(R.drawable.rb_low_gray_backgound));
            compoundButton.setTextColor(this.getResources().getColor(android.R.color.black));
        }
    }

    //??????????????????????????????
    private void swEditOnCheckedChanged(Switch compoundButton, boolean b) {
        if (b) {//????????????
            setEditState(true);
            compoundButton.setSwitchTextAppearance(this, R.style.s_true);
        } else {//????????????
            //for(int i=0;i<EditGroup.length;i++){
            //    EditGroup[i].setFocusable(false);
            //    EditGroup[i].setFocusableInTouchMode(false);
            //}
            setEditState(false);
            compoundButton.setSwitchTextAppearance(this, R.style.s_false);
        }
    }
    //???????????????????????????
    private void swWonderfulOnCheckChanged(boolean b) {
        if (b) {
            sw_Wonderful_State = true;
            Tv_EditOpen.setVisibility(View.VISIBLE);
            Tv_EditClose.setVisibility(View.INVISIBLE);
        } else {
            sw_Wonderful_State = false;
            Tv_EditOpen.setVisibility(View.INVISIBLE);
            Tv_EditClose.setVisibility(View.VISIBLE);
        }
    }
    //?????????????????????????????????
    private void swSaveOnCheckChanged(boolean b) {
        if (b) {
            Save_State = true;
            Tv_SaveOpen.setVisibility(View.VISIBLE);
            Tv_SaveClose.setVisibility(View.INVISIBLE);
        } else {
            Save_State = false;
            Tv_SaveOpen.setVisibility(View.INVISIBLE);
            Tv_SaveClose.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, TimePickerActivity.class);
        switch(view.getId()){

            case R.id.iv_head:
                //????????????
                Intent intent2 = new Intent("android.intent.action.GET_CONTENT");
                intent2.setType("image/*");
                startActivityForResult(intent2, CHOOSE_PHOTO); // ????????????

                //Intent intent3 = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //startActivityForResult(intent3, REQUEST_CODE_IMAGE);

                //Uri uri = Uri.parse("content://com.android.externalstorage.documents/document/primary:Android%2Fdata%2Fcom.zhuhuix.tmsandroid%2Ffiles%2Fimages");
                //intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                //intent.addCategory(Intent.CATEGORY_OPENABLE);
                //intent.setType("image/*");
                //intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, uri);
                //startActivityForResult(intent, 1);



                break;

            case R.id.btn_add://?????????????????????
                if(my_State){
                    //??????
                    mHandler = new Handler();

                    ly_resume.setDrawingCacheEnabled(true);
                    Bitmap bitmap = ly_resume.getDrawingCache();
                    // ????????????SD?????????
                    String sdCardPath = Environment.getExternalStorageDirectory().getPath();
                    // ??????????????????
                    String imagePath = sdCardPath + File.separator + "test.png";
                    File file = new File(imagePath);
                    if (bitmap != null) {
                        try {
                            FileOutputStream os = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                            os.flush();
                            os.close();
                        } catch (Exception e) {
                        }
                    }

                    Intent intentz = new Intent(Intent.ACTION_SEND); // ???????????????????????????

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                        Uri contentUri = FileProvider.getUriForFile(getApplication(), "com.example.perintro.FileProvider", file);// xxx????????????????????????
                        intentz.putExtra(Intent.EXTRA_STREAM, contentUri);
                    }else {
                        intentz.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));// ???????????????
                    }

                    intentz.setType("image/png");// ???????????????????????????
                    Intent chooser = Intent.createChooser(intentz, "Share screen shot");
                    if(intentz.resolveActivity(getPackageManager()) != null){
                        startActivity(chooser);
                    }

                }else{
                    showToast("?????????");
                }
                break;

            case R.id.btn_search:
                if(my_State){
                }else{
                    intent.setClass(MainActivity.this, SearchActivity.class);
                    startActivity(intent);
                }
                break;

            case R.id.edit_name:
                break;
            case R.id.edit_bir:
                if (sw_Edit_State) {
                    //???????????????????????????
                    dateTimeDialogOnlyYM.hideOrShow();
                }
                break;
            case R.id.edit_phone:
                break;
            case R.id.edit_edu:
                break;
            case R.id.edit_gra:
                break;
            case R.id.edit_major:
                break;
            case R.id.edit_target:
                break;
            case R.id.edit_skill:
                break;
            case R.id.edit_hobby:
                break;
            case R.id.btn_delete:
                if(!TextUtils.isEmpty(Now_selected)){
                    deleteSQLiteOne(Now_selected);
                    showToast("????????????????????????");

                    PersonList = readSQLiteList();
                    //S2:????????????????????????ArrayAdapter???
                    ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, R.layout.item_selected, PersonList);
                    //S4??????????????????????????????Spinner??????
                    sp_person.setAdapter(arrayAdapter1);

                    readSQLiteList();
                }else{
                    showToast("??????????????????????????????");
                }
                break;
            case R.id.btn_data_ok:
                String name = edit_name.getText().toString();
                String bir = edit_bir.getText().toString();
                String phone = edit_phone.getText().toString();
                String edu = edit_edu.getText().toString();
                String gra = edit_gra.getText().toString();
                String major = edit_major.getText().toString();
                String target = edit_target.getText().toString();
                String skill = edit_skill.getText().toString();
                String hobby = edit_hobby.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    showToast("??????????????????");
                    return;
                } else if (TextUtils.isEmpty(bir)) {
                    showToast("????????????????????????");
                    return;
                } else if (TextUtils.isEmpty(phone)) {
                    showToast("????????????????????????");
                    return;
                } else if (TextUtils.isEmpty(edu)) {
                    showToast("??????????????????");
                    return;
                } else if (TextUtils.isEmpty(gra)) {
                    showToast("????????????????????????");
                    return;
                } else if (TextUtils.isEmpty(major)) {
                    showToast("??????????????????");
                    return;
                } else if (TextUtils.isEmpty(target)) {
                    showToast("????????????????????????");
                    return;
                }
                // ?????????????????????????????????
                mHelper = PersonDBHelper.getInstance(this, 2);
                // ????????????????????????????????????
                mHelper.openWriteLink();
                // ??????????????????????????????????????????????????????????????????
                PersonInfo info = new PersonInfo();
                info.name = name;
                info.birthday_YM = bir;
                info.telephoneNumber = phone;
                info.education = edu;
                info.graduate_University = gra;
                info.major = major;
                info.target_Work = target;
                info.skill = skill;
                info.hobby = hobby;
                info.update_time = DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss");
                // ???????????????????????????????????????
                mHelper.insert(info);
                showToast("???????????????SQLite?????????");
                //mHelper.closeLink();


                PersonList = readSQLiteList();
                //S2:????????????????????????ArrayAdapter???
                ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, R.layout.item_selected, PersonList);
                //S4??????????????????????????????Spinner??????
                sp_person.setAdapter(arrayAdapter1);

                sp_person.setSelection(Now_Record_Number);

                //String???Date
                Date  date = null;
                try {
                    date = mFormatter.parse(info.birthday_YM);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar cd = Calendar.getInstance();
                int NowYear = cd.get(Calendar.YEAR);//????????????
                int NowMonth = cd.get(Calendar.MONTH)+1;//????????????

                Calendar calendar= Calendar.getInstance();
                calendar.setTime(date);
                int OldYear=calendar.get(Calendar.YEAR);//????????????
                int OldMonth=calendar.get(Calendar.MONTH)+1;//????????????

                int NowTime = NowYear*12+NowMonth;
                int OldTime = OldYear*12+OldMonth;
                int age = (NowTime-OldTime)/12;

                tv_name.setText(info.name);
                tv_age.setText(age+"???");
                tv_phone.setText(info.telephoneNumber);
                tv_edu.setText(info.education);
                tv_gra.setText(info.graduate_University);
                tv_major.setText(info.major);
                tv_target.setText(info.target_Work);
                tv_skill.setText(info.skill);
                tv_hobby.setText("?????????"+info.hobby);
                tv_sqlite_my.setText("????????????????????????"+info.update_time);

                viewPager.setCurrentItem(3);
                break;
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()) {
            case R.id.edit_name:
                break;
            case R.id.edit_bir:
                if(Bir_focus_state){
                    Bir_focus_state = false;
                    break;
                }else{
                    //??????????????????
                    dateTimeDialogOnlyYM.hideOrShow();

                    Bir_focus_state = true;
                }
                break;
            case R.id.edit_phone:
                break;
            case R.id.edit_edu:
                if(Edu_focus_state){
                    Edu_focus_state = false;
                    break;
                }else{
                    if(sw_Wonderful_State)
                        showList(edu_wonderful_list, edit_edu);
                    else
                        showList(edu_list, edit_edu);
                    Edu_focus_state = true;
                }
                break;


            case R.id.edit_gra:
                if(Gra_focus_state){
                    Gra_focus_state = false;
                    break;
                }else{
                    if(sw_Wonderful_State)
                        showList(gra_wonderful_list, edit_gra);
                    else
                        showList(gra_list, edit_gra);
                    Gra_focus_state = true;
                }
                break;


            case R.id.edit_major:
                if(Major_focus_state){
                    Major_focus_state = false;
                    break;
                }else{
                    if(sw_Wonderful_State)
                        showSingleAlertDialog(major_wonderful_list, edit_major);
                    else
                        showSingleAlertDialog(major_list, edit_major);
                    Major_focus_state = true;
                }
                break;


            case R.id.edit_target:
                if(Target_focus_state){
                    Target_focus_state = false;
                }else{
                    if(sw_Wonderful_State)
                        showSingleAlertDialog(target_wonderful_list, edit_target);
                    else
                        showSingleAlertDialog(target_list, edit_target);
                    Target_focus_state = true;
                }
                break;


            case R.id.edit_skill:
                if(Skill_focus_state){
                    Skill_focus_state = false;
                    break;
                }else{
                    if(sw_Wonderful_State)
                        showMutilAlertDialog(skill_wonderful_list, edit_skill);
                    else
                        showMutilAlertDialog(skill_list, edit_skill);
                    Skill_focus_state = true;
                }
                break;


            case R.id.edit_hobby:
                if(Hobby_focus_state){
                    Hobby_focus_state = false;
                    break;
                }else{
                    if(sw_Wonderful_State)
                        showMutilAlertDialog(hobby_wonderful_list, edit_hobby);
                    else
                        showMutilAlertDialog(hobby_list, edit_hobby);
                    Hobby_focus_state = true;
                }
                break;
        }
    }

    //?????? ??????
    private void showToast(String desc) {
        Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
    }

    //Data?????????????????????????????????
    @Override
    public void onDateSet(Date date) {
        edit_bir.setText(mFormatter.format(date) + "");
    }
    // ?????????????????????????????????????????????
    private void readSQLite() {
        mHelper = PersonDBHelper.getInstance(this, 2);
        // ????????????????????????????????????
        mHelper.openReadLink();
        String[] PersonList = new String[0];
        if (mHelper == null) {
            showToast("?????????????????????");
            return;
        }
        // ???????????????????????????????????????
        ArrayList<PersonInfo> PersonArray = mHelper.query("1=1");

        Now_Record_Number = PersonArray.size();
        PersonList = new String[PersonArray.size()+1];
        String str = String.format("???????????????????????????????????????%d????????????:", PersonArray.size());

        String desc = String.format("??????????????????%d???????????????????????????", PersonArray.size());
        for (int i = 0; i < PersonArray.size(); i++) {
            PersonInfo info = PersonArray.get(i);
            desc = String.format("%s\n???%d????????????????????????", desc, i + 1);
            PersonList[i] = info.name;
            desc = String.format("%s\n????????????%s", desc, info.name);
            desc = String.format("%s\n??????????????????%s", desc, info.birthday_YM);
            desc = String.format("%s\n???????????????%s", desc, info.telephoneNumber);
            desc = String.format("%s\n????????????%s", desc, info.education);
            desc = String.format("%s\n??????????????????%s", desc, info.graduate_University);
            desc = String.format("%s\n????????????%s", desc, info.major);
            desc = String.format("%s\n??????????????????%s", desc, info.target_Work);
            desc = String.format("%s\n????????????%s", desc, info.skill);
            desc = String.format("%s\n????????????%s", desc, info.hobby);
            desc = String.format("%s\n??????????????????%s", desc, info.update_time);
            desc = String.format("%s\n", desc);
        }
        if (PersonArray.size() <= 0) {
            desc = "?????????????????????????????????";
            str = "???????????????????????????????????????0????????????:";
        }
        //tv_sqlite_my.setTextSize(10);
        //tv_sqlite_my.setText(desc);
        tv_data_tips.setText(str);
    }

    private String[] readSQLiteList() {
        mHelper = PersonDBHelper.getInstance(this, 2);
        // ????????????????????????????????????
        mHelper.openReadLink();
        if (mHelper == null) {
            showToast("?????????????????????");
            return null;
        }
        // ???????????????????????????????????????
        ArrayList<PersonInfo> PersonArray = mHelper.query("1=1");
        Now_Record_Number = PersonArray.size();
        String[] PersonSQLiteList = new String[PersonArray.size()+1];
        String str = String.format("???????????????????????????????????????%d????????????:", PersonArray.size());

        for (int i = 0; i < PersonArray.size(); i++) {
            PersonInfo info = PersonArray.get(i);
            PersonSQLiteList[i] = info.name.toString();
        }
        if (PersonArray.size() <= 0) {
            str = "???????????????????????????????????????0????????????:";
        }
        tv_data_tips.setText(str);
        PersonSQLiteList[PersonArray.size()] = "?????????";
        return PersonSQLiteList;
    }
    private PersonInfo readSQLiteOne(String name){
        mHelper = PersonDBHelper.getInstance(this, 2);
        // ????????????????????????????????????
        mHelper.openReadLink();
        if (mHelper == null) {
            showToast("?????????????????????");
            return null;
        }

        // ???????????????????????????????????????
        PersonInfo Person = mHelper.queryByName(name);

        return Person;
    }

    //??????????????????????????????
    private void deleteSQLiteOne(String name){
        mHelper = PersonDBHelper.getInstance(this, 2);
        // ????????????????????????????????????
        mHelper.openWriteLink();
        // ????????????????????????
        mHelper.delete(name);
        // ?????????????????????
        mHelper.closeLink();
        // ????????????????????????????????????
        mHelper.openReadLink();

        readSQLiteList();
    }


    public void showList(String[] items,EditText et){
        //final String[] items = {"??????1", "??????2", "??????3", "??????4"};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("???????????????");
        alertBuilder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                et.setText(items[i]);
                //Toast.makeText(MainActivity.this, items[i], Toast.LENGTH_SHORT).show();
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = alertBuilder.create();
        alertDialog1.show();
    }

    public void showSingleAlertDialog(String[] items,EditText et){
        //final String[] items = {"??????1", "??????2", "??????3", "??????4"};
        final String[] select = {items[0]};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("???????????????");
        alertBuilder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                select[0] = items[i];
                //Toast.makeText(MainActivity.this, items[i], Toast.LENGTH_SHORT).show();
            }
        });

        alertBuilder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                et.setText(select[0]);
                alertDialog2.dismiss();
            }
        });

        alertBuilder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog2.dismiss();
            }
        });

        alertDialog2 = alertBuilder.create();
        alertDialog2.show();
    }

    public void showMutilAlertDialog(String[] items,EditText et){
        //final String[] items = {"??????1", "??????2", "??????3", "??????4"};
        final boolean[] Mutil_select = new boolean[items.length];
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("???????????????");
        /**
         *???????????????:???????????????????????????????????????????????????
         * ??????????????????????????????????????????????????????
         * ????????????????????????????????????
         */
        alertBuilder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                if (isChecked){
                    Mutil_select[i] = true;
                    //Toast.makeText(MainActivity.this, "??????" + items[i], Toast.LENGTH_SHORT).show();
                }else {
                    Mutil_select[i] = false;
                    //Toast.makeText(MainActivity.this, "????????????" + items[i], Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertBuilder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String result = "";
                for(int j=0;j<items.length;j++){
                    if(Mutil_select[j]){
                        result = String.format("%s %s", result, items[j]);
                    }
                }
                et.setText(result);
                alertDialog3.dismiss();
            }
        });

        alertBuilder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog3.dismiss();
            }
        });


        alertDialog3 = alertBuilder.create();
        alertDialog3.show();
    }


    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // ?????????document?????????Uri????????????document id??????
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // ????????????????????????id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // ?????????content?????????Uri??????????????????????????????
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // ?????????file?????????Uri?????????????????????????????????
            imagePath = uri.getPath();
        }
        displayImage(imagePath); // ??????????????????????????????
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // ??????Uri???selection??????????????????????????????
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            iv_head.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

}