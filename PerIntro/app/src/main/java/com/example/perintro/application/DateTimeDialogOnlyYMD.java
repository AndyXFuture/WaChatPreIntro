package com.example.perintro.application;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import java.util.Calendar;
import java.util.Date;

import com.example.perintro.R;
/**
 * 自定义 日期 选择 器  只能选择 日月年
 * Created by Administrator on 2016/6/22.
 */
public class DateTimeDialogOnlyYMD extends AlertDialog implements View.OnClickListener {

    private DatePicker mDatePicker;
    private MyOnDateSetListener onDateSetListener;

    private Button cancleButton, okButton;

    // 控制 日期
    private int measureWidth;
    // 是否 显示 日选择器   true 显示 ，false 隐藏
    private boolean isDayVisible = true;
    // 是否 显示 月选择器   true 显示 ，false 隐藏
    private boolean isMonthVisible = true;
    // 是否 显示 年选择器   true 显示 ，false 隐藏
    private boolean isYearVisible = true;


    protected DateTimeDialogOnlyYMD(Context context) {
        super(context);
    }

    protected DateTimeDialogOnlyYMD(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    /**
     * @param context        上下文对象
     * @param callBack       选择 监听器
     * @param isDayVisible   日 是否可见
     * @param isMonthVisible 月 是否可见
     * @param isYearVisible  年 是否可见
     */
    public DateTimeDialogOnlyYMD(Context context, MyOnDateSetListener callBack, boolean isDayVisible, boolean isMonthVisible, boolean isYearVisible) {
        super(context);
        this.isDayVisible = isDayVisible;
        this.isMonthVisible = isMonthVisible;
        this.isYearVisible = isYearVisible;
        this.onDateSetListener = callBack;
        this.isDayVisible = isDayVisible;
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_date_picker_dialog, null);
        setView(view);
        mDatePicker = (DatePicker) view.findViewById(R.id.datePicker);
        LinearLayout buttonGroup = (LinearLayout) view.findViewById(R.id.buttonGroup);
        cancleButton = (Button) view.findViewById(R.id.cancelButton);
        okButton = (Button) view.findViewById(R.id.okButton);

        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTime(new Date());

        cancleButton.setOnClickListener(this);
        okButton.setOnClickListener(this);

        // 是否 显示 年
        if (!this.isYearVisible) {
            ((ViewGroup) ((ViewGroup) mDatePicker.getChildAt(0)).getChildAt(0)).getChildAt(0).setVisibility(View.GONE);
        }

        // 是否 显示 月
        if (!this.isMonthVisible) {
            ((ViewGroup) ((ViewGroup) mDatePicker.getChildAt(0)).getChildAt(0)).getChildAt(1).setVisibility(View.GONE);
        }

        // 是否 显示 日
        if (!this.isDayVisible) {
            ((ViewGroup) ((ViewGroup) mDatePicker.getChildAt(0)).getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
        }


        // 设置 显示 宽高
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

        buttonGroup.measure(width, height);
        mDatePicker.measure(width, height);
        if (buttonGroup.getMeasuredWidth() > mDatePicker.getMeasuredWidth()) {
            this.measureWidth = buttonGroup.getMeasuredWidth();
        } else {
            this.measureWidth = mDatePicker.getMeasuredWidth();
        }
//        Log.i("testss", this.measureWidth + "measuredWidth");
    }


    public void hideOrShow() {
        if (this == null) {
            return;
        }
        if (!this.isShowing()) {
            this.show();
            //设置 显示 的 宽高
            WindowManager.LayoutParams attributes = this.getWindow().getAttributes();
            attributes.width = this.measureWidth + 100;
            this.getWindow().setAttributes(attributes);
        } else {
            this.dismiss();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancelButton:
                dismiss();
                break;
            case R.id.okButton:
                onOkButtonClick();
                dismiss();
                break;
        }
    }

    /**
     * 确认 按钮 点击 事件
     */
    private void onOkButtonClick() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, mDatePicker.getYear());
        calendar.set(Calendar.MONTH, mDatePicker.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, mDatePicker.getDayOfMonth());
        calendar.getTime();
        if (onDateSetListener != null) {
            onDateSetListener.onDateSet(calendar.getTime());
        }
        Log.i("testss", mDatePicker.getYear() + "====" + (mDatePicker.getMonth() + 1) + "==" + mDatePicker.getDayOfMonth());
    }

    /**
     * 时间  改变 监听
     */
    public interface MyOnDateSetListener {
        void onDateSet(Date date);
    }

    public interface MyOnDateSetListenr {
    }
}
