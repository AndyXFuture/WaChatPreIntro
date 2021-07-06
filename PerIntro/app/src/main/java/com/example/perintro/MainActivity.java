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



    //全局状态量
        //当前被选中的记录
    private String Now_selected = null;
        //当前存放的记录数
    private int Now_Record_Number = 0;
        //奇妙的开关状态
    private boolean Wondeful_State = false;
    private boolean Save_State = false;
        //我的页打开状态
    private boolean my_State = false;


    // 日期 格式化 工具
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

    //Data页控件
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
    //Data页输入控件
    private EditText edit_name ,edit_bir ,edit_phone ,edit_edu ,edit_gra ,edit_major ,edit_target ,edit_skill ,edit_hobby;
    private EditText[] EditGroup = {edit_name,edit_bir,edit_phone, edit_edu,edit_gra,
            edit_major,edit_target, edit_skill,edit_hobby};
    private PersonDBHelper mHelper; // 声明一个用户数据库帮助器的对象
    private String[] PersonList = new String[0];

    //单选按钮视图
    private AlertDialog alertDialog1; //信息框
    private AlertDialog alertDialog2; //单选框
    private AlertDialog alertDialog3; //多选框
    private String[] edu_list = {"初中","高中","专科","本科","硕士","博士"};
    private String[] edu_wonderful_list = {"魂士","魂圣","魂宗","魂王","魂帝","魂斗罗","封号斗罗","超级斗罗","终极斗罗","半步神王","神王"};
    //列表视图
    private String[] gra_list = {"清华大学","电子科技大学","广州番禺职业技术学院","自定义"};
    private String[] gra_wonderful_list = {"霍格沃兹魔法学院","史莱克学院","远月茶料理学院","自定义"};
    private String[] major_list = {"物联网工程","大数据","嵌入式","自定义"};
    private String[] major_wonderful_list = {"黑魔法学","控制系","调味系","自定义"};
    private String[] target_list = {"嵌入式工程师","JAVA攻城狮","PCB设计工程师","Android开发工程师","自定义"};
    private String[] target_wonderful_list = {"黑魔法防御科","控制系战魂师","宫廷料理师","摸鱼带师","自定义"};
    //多选按钮视图
    private String[] skill_list = {"吃饭","睡觉","工作","学习"};
    private String[] skill_wonderful_list = {"背锅","倒立洗头","上班摸鱼","大喊“我是废物”"};
    private String[] hobby_list = {"音乐","绘画","运动","舞蹈"};
    private String[] hobby_wonderful_list = {"爆破","清除全宇宙一半的人口","打响指","毁灭世界"};
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
    //MY页控件
    private ImageView iv_head;
    private Bitmap head;// 头像Bitmap
    private TextView tv_sqlite_my;
    private LinearLayout ly_resume;
    private Handler mHandler;//用于图片缓存
    private TextView tv_name, tv_age, tv_phone, tv_edu, tv_gra, tv_major, tv_target, tv_skill, tv_hobby;


    //HOME列表视图
    private ListView List_home; // 声明一个列表视图对象

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK) {

            // 判断手机系统版本号
            if (Build.VERSION.SDK_INT >= 19) {
                // 4.4及以上系统使用这个方法处理图片
                handleImageOnKitKat(data);
            } else {
                // 4.4以下系统使用这个方法处理图片
                handleImageBeforeKitKat(data);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //去除标题栏
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();}

        initViews();
        initEvents();

    }

    //打开当前活动运行的
    @Override
    protected void onStart() {
        super.onStart();
        // 获得数据库帮助器的实例
        mHelper = PersonDBHelper.getInstance(this, 2);
        // 获得数据库帮助器的实例
        mHelper = PersonDBHelper.getInstance(this, 2);
        // 打开数据库帮助器的写连接
        mHelper.openWriteLink();
        // 以下声明一个用户信息对象，并填写它的各字段值
        PersonInfo info = new PersonInfo();
        info.name = "XFuture";
        info.birthday_YM = "2001.01";
        info.telephoneNumber = "18012341234";
        info.education = "博士";
        info.graduate_University = "霍格沃兹魔法大学";
        info.major = "黑魔法科";
        info.target_Work = "黑魔法防御师";
        info.skill = "躺平";
        info.hobby = "优质睡眠";
        info.update_time = DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss");
        // 执行数据库帮助器的插入操作
        mHelper.insert(info);
        //打开读连接
        mHelper.openReadLink();
        readSQLite();
    }
    private boolean needExit = false; // 是否需要退出App
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { // 按下返回键
            if (needExit) {
                finish(); // 关闭当前页面
            }
            needExit = true;
            Toast.makeText(this, "再按一次返回键退出!", Toast.LENGTH_SHORT).show();
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
        //主页底部栏点击操作响应
            @Override
            public void onPageSelected(int i) {
                int currentItem = viewPager.getCurrentItem();
                resetButtonImg();
                switch(currentItem){
                    case 0:
                        rb_home.setCompoundDrawables(null,home_pure,null,null);
                        rb_home.setTextColor(Color.parseColor("#FFAAAAFF"));
                        text_label.setText("微信");
                        my_State = false;
                        break;
                    case 1:
                        rb_data.setCompoundDrawables(null,data_pure,null,null);
                        rb_data.setTextColor(Color.parseColor("#FFAAAAFF"));
                        text_label.setText("资料");
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
                        text_label.setText("个人网站");
                        my_State = false;
                        break;
                    case 3:
                        rb_my.setCompoundDrawables(null,my_pure,null,null);
                        rb_my.setTextColor(Color.parseColor("#FFAAAAFF"));
                        //将数据库全部列出
                        readSQLite();

                        //标题栏变化
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
        //添加布局
        LayoutInflater inflater = LayoutInflater.from(this);
        View tab01 = inflater.inflate(R.layout.home, null);
        View tab02 = inflater.inflate(R.layout.data, null);
        View tab03 = inflater.inflate(R.layout.discover, null);
        View tab04 = inflater.inflate(R.layout.my, null);
        mViews.add(tab01);
        mViews.add(tab02);
        mViews.add(tab03);
        mViews.add(tab04);

        //绑定
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        //主页顶部栏
        text_label = (TextView) findViewById(R.id.text_label);
        btn_search = (Button) findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
        btn_left = (Button) findViewById(R.id.btn_left);

        //主页底部栏
        rg_bottom = (RadioGroup) findViewById(R.id.rg_bottom);
        rb_my = (RadioButton) findViewById(R.id.rb_my);
        rb_home = (RadioButton) findViewById(R.id.rb_home);
        rb_data = (RadioButton) findViewById(R.id.rb_data);
        rb_discover = (RadioButton) findViewById(R.id.rb_discover);


        //图片资源初始化
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

        //顶部
        btn_search.setCompoundDrawables(null, ic_search_white, null, null);
        btn_add.setCompoundDrawables(null, ic_add_white, null, null);
        //底部
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

        //主页ListView==============================================================================


        ArrayList<App> appList = App.getDefaultList();
        // 构建一个行星队列的列表适配器
        PlanetListAdapter planetListadapter = new PlanetListAdapter(this, appList) {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                super.onItemClick(parent, view, position, id);
                String desc = String.format("您点击了第%d个功能，%s", position + 1, getmPlanetList().get(position).name);
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

        // 从布局视图中获取名叫lv_planet的列表视图
        // 因为控件在其他布局下，所以得从布局变量下绑定
        //List_home = findViewById(R.id.List_home);
        List_home = (ListView) tab01.findViewById(R.id.List_home);
        // 给lv_planet设置行星列表适配器
        List_home.setAdapter(planetListadapter);
        // 给lv_planet设置列表项的点击监听器
        List_home.setOnItemClickListener(planetListadapter);
        // 给lv_planet设置列表项的长按监听器
        List_home.setOnItemLongClickListener(planetListadapter);


        //资料页===================================================================================


        tv_data_tips = tab02.findViewById(R.id.tv_data_tips);
        //可编辑选择
        sw_Edit = tab02.findViewById(R.id.sw_edit);
        sw_Wonderful = tab02.findViewById(R.id.sw_wonderful);
        Tv_EditClose = tab02.findViewById(R.id.tv_wonderful_close);
        Tv_EditOpen = tab02.findViewById(R.id.tv_wonderful_open);
        sw_Save = tab02.findViewById(R.id.sw_save);
        Tv_SaveClose = tab02.findViewById(R.id.tv_sound_close);
        Tv_SaveOpen = tab02.findViewById(R.id.tv_sound_open);
        //输入框
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
        //给编辑框绑定单击监听和焦点
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

        //防止粘贴
        /*edit_name.setKeyListener(null);
        edit_bir.setKeyListener(null);
        edit_phone.setKeyListener(null);
        edit_edu.setKeyListener(null);
        edit_gra.setKeyListener(null);
        edit_major.setKeyListener(null);
        edit_target.setKeyListener(null);
        edit_skill.setKeyListener(null);
        edit_hobby.setKeyListener(null);*/
        //获取人员列表


        //String[] PersonArrayAdpter = {"金星", "木星木星木星", "水星", "火星", "土星", "地球"};


        //Data人员选择下拉框操作
        sp_person = tab02.findViewById(R.id.sp_person);
        PersonList = readSQLiteList();
        //S2:定义数组适配器（ArrayAdapter）
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.item_selected, PersonList);
        //S3:设置数组适配器的下拉布局文件
        arrayAdapter.setDropDownViewResource(R.layout.item_dropdown);
        //S4：把数组适合器配置到Spinner组件
        sp_person.setAdapter(arrayAdapter);
        sp_person.setPrompt("请选择");   //只在dialog样式中显示
        sp_person.setSelection(0);
        sp_person.setOnFocusChangeListener(this);
        sp_person.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //显示选中的记录
                if(i<Now_Record_Number){
                    //关闭可编辑开关
                    sw_Edit.setChecked(false);
                    Now_selected = PersonList[i];
                    //获取当前被选中的记录
                    PersonInfo per = readSQLiteOne(Now_selected);
                    //将记录填充到表中
                    edit_name.setText(per.name);
                    edit_bir.setText(per.birthday_YM);
                    edit_phone.setText(per.telephoneNumber);
                    edit_edu.setText(per.education);
                    edit_gra.setText(per.graduate_University);
                    edit_major.setText(per.major);
                    edit_target.setText(per.target_Work);
                    edit_skill.setText(per.skill);
                    edit_hobby.setText(per.hobby);
                    btn_data_ok.setText("生成简历（有修改则一并保存）");
                }else{
                    //开启可编辑开关
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
                    btn_data_ok.setText("添加记录并生成简历");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //我的页===================================================================================
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




        //发现页===================================================================================
        wb_discover = tab03.findViewById(R.id.wb_discover);
        pg1 = tab03.findViewById(R.id.pg_web);
        //String url = "file:///android_asset/example.html";
        String url = "http://xiaogui888.xyz";
        wb_discover.loadUrl(url);
        WebSettings seting = wb_discover.getSettings();
        seting.setJavaScriptEnabled(true);//设置webview支持javascript脚本
        wb_discover.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO 自动生成的方法存根
                if (newProgress == 100) {
                    pg1.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    pg1.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    pg1.setProgress(newProgress);//设置进度值
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
    //设置Date页输入框的状态
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

    //设置Data页各种开关的响应
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.sw_wonderful://奇妙开关
                swWonderfulOnCheckChanged(b);
                break;
            case R.id.sw_save://保存截图开关
                swSaveOnCheckChanged(b);
                break;
            case R.id.sw_edit://可编辑开关
                swEditOnCheckedChanged((Switch) compoundButton, b);
                break;
            case R.id.rb_high://简历字号-大
                rbBigOnChecked((RadioButton) compoundButton, b);
                break;
            case R.id.rb_low://简历字号-中
                rbSmallOnChecked((RadioButton) compoundButton, b);
                break;
            case R.id.rb_mid://简历字号-小
                rbMidOnChecked((RadioButton) compoundButton, b);
                break;
        }
    }

    //设置简历字号-中
    private void rbMidOnChecked(RadioButton compoundButton, boolean b) {
        if (b) {
            compoundButton.setTextColor(this.getResources().getColor(R.color.white));
        } else {
            compoundButton.setTextColor(this.getResources().getColor(R.color.black));
        }
    }
    //设置简历字号-大
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
    //设置简历字号-小
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

    //可编辑开关的响应事件
    private void swEditOnCheckedChanged(Switch compoundButton, boolean b) {
        if (b) {//开启编辑
            setEditState(true);
            compoundButton.setSwitchTextAppearance(this, R.style.s_true);
        } else {//关闭编辑
            //for(int i=0;i<EditGroup.length;i++){
            //    EditGroup[i].setFocusable(false);
            //    EditGroup[i].setFocusableInTouchMode(false);
            //}
            setEditState(false);
            compoundButton.setSwitchTextAppearance(this, R.style.s_false);
        }
    }
    //奇妙开关的响应事件
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
    //保存截图开关的响应事件
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
                //更换头像
                Intent intent2 = new Intent("android.intent.action.GET_CONTENT");
                intent2.setType("image/*");
                startActivityForResult(intent2, CHOOSE_PHOTO); // 打开相册

                //Intent intent3 = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //startActivityForResult(intent3, REQUEST_CODE_IMAGE);

                //Uri uri = Uri.parse("content://com.android.externalstorage.documents/document/primary:Android%2Fdata%2Fcom.zhuhuix.tmsandroid%2Ffiles%2Fimages");
                //intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                //intent.addCategory(Intent.CATEGORY_OPENABLE);
                //intent.setType("image/*");
                //intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, uri);
                //startActivityForResult(intent, 1);



                break;

            case R.id.btn_add://右上角加号位置
                if(my_State){
                    //分享
                    mHandler = new Handler();

                    ly_resume.setDrawingCacheEnabled(true);
                    Bitmap bitmap = ly_resume.getDrawingCache();
                    // 获取内置SD卡路径
                    String sdCardPath = Environment.getExternalStorageDirectory().getPath();
                    // 图片文件路径
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

                    Intent intentz = new Intent(Intent.ACTION_SEND); // 启动分享发送的属性

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                        Uri contentUri = FileProvider.getUriForFile(getApplication(), "com.example.perintro.FileProvider", file);// xxx修改为自己项目的
                        intentz.putExtra(Intent.EXTRA_STREAM, contentUri);
                    }else {
                        intentz.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));// 分享的内容
                    }

                    intentz.setType("image/png");// 分享发送的数据类型
                    Intent chooser = Intent.createChooser(intentz, "Share screen shot");
                    if(intentz.resolveActivity(getPackageManager()) != null){
                        startActivity(chooser);
                    }

                }else{
                    showToast("无功能");
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
                    //打开滑动框让其选择
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
                    showToast("该记录已成功删除");

                    PersonList = readSQLiteList();
                    //S2:定义数组适配器（ArrayAdapter）
                    ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, R.layout.item_selected, PersonList);
                    //S4：把数组适合器配置到Spinner组件
                    sp_person.setAdapter(arrayAdapter1);

                    readSQLiteList();
                }else{
                    showToast("请先选择要删除的记录");
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
                    showToast("请先填写姓名");
                    return;
                } else if (TextUtils.isEmpty(bir)) {
                    showToast("请先填写出生年月");
                    return;
                } else if (TextUtils.isEmpty(phone)) {
                    showToast("请先填写手机号码");
                    return;
                } else if (TextUtils.isEmpty(edu)) {
                    showToast("请先填写学历");
                    return;
                } else if (TextUtils.isEmpty(gra)) {
                    showToast("请先填写毕业院校");
                    return;
                } else if (TextUtils.isEmpty(major)) {
                    showToast("请先填写专业");
                    return;
                } else if (TextUtils.isEmpty(target)) {
                    showToast("请先填写目标岗位");
                    return;
                }
                // 获得数据库帮助器的实例
                mHelper = PersonDBHelper.getInstance(this, 2);
                // 打开数据库帮助器的写连接
                mHelper.openWriteLink();
                // 以下声明一个用户信息对象，并填写它的各字段值
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
                // 执行数据库帮助器的插入操作
                mHelper.insert(info);
                showToast("数据已写入SQLite数据库");
                //mHelper.closeLink();


                PersonList = readSQLiteList();
                //S2:定义数组适配器（ArrayAdapter）
                ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, R.layout.item_selected, PersonList);
                //S4：把数组适合器配置到Spinner组件
                sp_person.setAdapter(arrayAdapter1);

                sp_person.setSelection(Now_Record_Number);

                //String转Date
                Date  date = null;
                try {
                    date = mFormatter.parse(info.birthday_YM);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar cd = Calendar.getInstance();
                int NowYear = cd.get(Calendar.YEAR);//当前年份
                int NowMonth = cd.get(Calendar.MONTH)+1;//当前月份

                Calendar calendar= Calendar.getInstance();
                calendar.setTime(date);
                int OldYear=calendar.get(Calendar.YEAR);//出生年份
                int OldMonth=calendar.get(Calendar.MONTH)+1;//出生月份

                int NowTime = NowYear*12+NowMonth;
                int OldTime = OldYear*12+OldMonth;
                int age = (NowTime-OldTime)/12;

                tv_name.setText(info.name);
                tv_age.setText(age+"岁");
                tv_phone.setText(info.telephoneNumber);
                tv_edu.setText(info.education);
                tv_gra.setText(info.graduate_University);
                tv_major.setText(info.major);
                tv_target.setText(info.target_Work);
                tv_skill.setText(info.skill);
                tv_hobby.setText("爱好："+info.hobby);
                tv_sqlite_my.setText("该记录更新时间："+info.update_time);

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
                    //弹窗选择时间
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

    //通用 提示
    private void showToast(String desc) {
        Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
    }

    //Data页筛选出生年月后的操作
    @Override
    public void onDateSet(Date date) {
        edit_bir.setText(mFormatter.format(date) + "");
    }
    // 读取数据库中保存的所有用户记录
    private void readSQLite() {
        mHelper = PersonDBHelper.getInstance(this, 2);
        // 打开数据库帮助器的读连接
        mHelper.openReadLink();
        String[] PersonList = new String[0];
        if (mHelper == null) {
            showToast("数据库连接为空");
            return;
        }
        // 执行数据库帮助器的查询操作
        ArrayList<PersonInfo> PersonArray = mHelper.query("1=1");

        Now_Record_Number = PersonArray.size();
        PersonList = new String[PersonArray.size()+1];
        String str = String.format("请选择数据库中的人员（共有%d条记录）:", PersonArray.size());

        String desc = String.format("数据库查询到%d条记录，详情如下：", PersonArray.size());
        for (int i = 0; i < PersonArray.size(); i++) {
            PersonInfo info = PersonArray.get(i);
            desc = String.format("%s\n第%d条记录信息如下：", desc, i + 1);
            PersonList[i] = info.name;
            desc = String.format("%s\n　姓名为%s", desc, info.name);
            desc = String.format("%s\n　出生年月为%s", desc, info.birthday_YM);
            desc = String.format("%s\n　手机号为%s", desc, info.telephoneNumber);
            desc = String.format("%s\n　学历为%s", desc, info.education);
            desc = String.format("%s\n　毕业院校为%s", desc, info.graduate_University);
            desc = String.format("%s\n　专业为%s", desc, info.major);
            desc = String.format("%s\n　目标岗位为%s", desc, info.target_Work);
            desc = String.format("%s\n　技能为%s", desc, info.skill);
            desc = String.format("%s\n　爱好为%s", desc, info.hobby);
            desc = String.format("%s\n　更新时间为%s", desc, info.update_time);
            desc = String.format("%s\n", desc);
        }
        if (PersonArray.size() <= 0) {
            desc = "数据库查询到的记录为空";
            str = "请选择数据库中的人员（共有0条记录）:";
        }
        //tv_sqlite_my.setTextSize(10);
        //tv_sqlite_my.setText(desc);
        tv_data_tips.setText(str);
    }

    private String[] readSQLiteList() {
        mHelper = PersonDBHelper.getInstance(this, 2);
        // 打开数据库帮助器的读连接
        mHelper.openReadLink();
        if (mHelper == null) {
            showToast("数据库连接为空");
            return null;
        }
        // 执行数据库帮助器的查询操作
        ArrayList<PersonInfo> PersonArray = mHelper.query("1=1");
        Now_Record_Number = PersonArray.size();
        String[] PersonSQLiteList = new String[PersonArray.size()+1];
        String str = String.format("请选择数据库中的人员（共有%d条记录）:", PersonArray.size());

        for (int i = 0; i < PersonArray.size(); i++) {
            PersonInfo info = PersonArray.get(i);
            PersonSQLiteList[i] = info.name.toString();
        }
        if (PersonArray.size() <= 0) {
            str = "请选择数据库中的人员（共有0条记录）:";
        }
        tv_data_tips.setText(str);
        PersonSQLiteList[PersonArray.size()] = "自定义";
        return PersonSQLiteList;
    }
    private PersonInfo readSQLiteOne(String name){
        mHelper = PersonDBHelper.getInstance(this, 2);
        // 打开数据库帮助器的读连接
        mHelper.openReadLink();
        if (mHelper == null) {
            showToast("数据库连接为空");
            return null;
        }

        // 执行数据库帮助器的查询操作
        PersonInfo Person = mHelper.queryByName(name);

        return Person;
    }

    //删除数据库指定的记录
    private void deleteSQLiteOne(String name){
        mHelper = PersonDBHelper.getInstance(this, 2);
        // 打开数据库帮助器的读连接
        mHelper.openWriteLink();
        // 删除被指定的记录
        mHelper.delete(name);
        // 关闭数据库连接
        mHelper.closeLink();
        // 打开数据库帮助器的读连接
        mHelper.openReadLink();

        readSQLiteList();
    }


    public void showList(String[] items,EditText et){
        //final String[] items = {"列表1", "列表2", "列表3", "列表4"};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("这是列表框");
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
        //final String[] items = {"单选1", "单选2", "单选3", "单选4"};
        final String[] select = {items[0]};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("这是单选框");
        alertBuilder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                select[0] = items[i];
                //Toast.makeText(MainActivity.this, items[i], Toast.LENGTH_SHORT).show();
            }
        });

        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                et.setText(select[0]);
                alertDialog2.dismiss();
            }
        });

        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog2.dismiss();
            }
        });

        alertDialog2 = alertBuilder.create();
        alertDialog2.show();
    }

    public void showMutilAlertDialog(String[] items,EditText et){
        //final String[] items = {"多选1", "多选2", "多选3", "多选4"};
        final boolean[] Mutil_select = new boolean[items.length];
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("这是多选框");
        /**
         *第一个参数:弹出框的消息集合，一般为字符串集合
         * 第二个参数：默认被选中的，布尔类数组
         * 第三个参数：勾选事件监听
         */
        alertBuilder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                if (isChecked){
                    Mutil_select[i] = true;
                    //Toast.makeText(MainActivity.this, "选择" + items[i], Toast.LENGTH_SHORT).show();
                }else {
                    Mutil_select[i] = false;
                    //Toast.makeText(MainActivity.this, "取消选择" + items[i], Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
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

        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
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
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath); // 根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
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