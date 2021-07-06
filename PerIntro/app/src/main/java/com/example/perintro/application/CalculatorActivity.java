package com.example.perintro.application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.perintro.R;
import com.example.perintro.base.ChatroomActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Logger;

public class CalculatorActivity extends ChatroomActivity implements View.OnClickListener {
        private Button btn_0, btn_1;
        private Button btn_2;
        private Button btn_3;
        private Button btn_4;
        private Button btn_5;
        private Button btn_6;
        private Button btn_7;
        private Button btn_8;
        private Button btn_9;
        private Button btn_spot;
        private Button btn_add;
        private Button btn_sub;
        private Button btn_mul;
        private Button btn_div;
        private Button btn_sur;
        private Button btn_c;
        private Button btn_del;
        private Button btn_switch;
        private Button btn_equal;
        private TextView text_process;
        private TextView text_result;

        private String buffer;
        private int bufferNumber;

        String Backspace;
        Double result;


        private Stack<Character> priStack = new Stack<Character>();// 操作符栈
        private Stack<Double> numStack = new Stack<Double>();;// 操作数栈

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //聊天页的初始化
            setContentView(R.layout.activity_chatroom);
            preInit("计算器",R.layout.app_calculator);
            Calendar cd = Calendar.getInstance();
            initViews();
            text_process.setText(cd.get(Calendar.YEAR)+"-2001");
            onClickEqualButton();
        }

        private void initViews(){
            btn_0=findViewById(R.id.btn_0);
            btn_1=findViewById(R.id.btn_1);
            btn_2=findViewById(R.id.btn_2);
            btn_3=findViewById(R.id.btn_3);
            btn_4=findViewById(R.id.btn_4);
            btn_5=findViewById(R.id.btn_5);
            btn_6=findViewById(R.id.btn_6);
            btn_7=findViewById(R.id.btn_7);
            btn_8=findViewById(R.id.btn_8);
            btn_9=findViewById(R.id.btn_9);
            btn_spot=findViewById(R.id.btn_spot);
            btn_add=findViewById(R.id.btn_add);
            btn_sub=findViewById(R.id.btn_sub);
            btn_mul=findViewById(R.id.btn_mul);
            btn_div=findViewById(R.id.btn_div);
            btn_sur=findViewById(R.id.btn_sur);
            btn_c=findViewById(R.id.btn_c);
            btn_del=findViewById(R.id.btn_del);
            btn_switch=findViewById(R.id.btn_switch);
            btn_equal=findViewById(R.id.btn_equal);
            text_process=findViewById(R.id.text_process);
            text_result=findViewById(R.id.text_result);

            btn_0.setOnClickListener(this);
            btn_0.setOnClickListener(this);
            btn_1.setOnClickListener(this);
            btn_2.setOnClickListener(this);
            btn_3.setOnClickListener(this);
            btn_4.setOnClickListener(this);
            btn_5.setOnClickListener(this);
            btn_6.setOnClickListener(this);
            btn_7.setOnClickListener(this);
            btn_8.setOnClickListener(this);
            btn_9.setOnClickListener(this);
            btn_spot.setOnClickListener(this);
            btn_add.setOnClickListener(this);
            btn_sub.setOnClickListener(this);
            btn_mul.setOnClickListener(this);
            btn_div.setOnClickListener(this);
            btn_sur.setOnClickListener(this);
            btn_c.setOnClickListener(this);
            btn_del.setOnClickListener(this);
            btn_switch.setOnClickListener(this);
            btn_equal.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.btn_0:
                    onClickNumButton("0");
                    break;
                case R.id.btn_1:
                    onClickNumButton("1");
                    break;
                case R.id.btn_2:
                    onClickNumButton("2");
                    break;
                case R.id.btn_3:
                    onClickNumButton("3");
                    break;
                case R.id.btn_4:
                    onClickNumButton("4");
                    break;
                case R.id.btn_5:
                    onClickNumButton("5");
                    break;
                case R.id.btn_6:
                    onClickNumButton("6");
                    break;
                case R.id.btn_7:
                    onClickNumButton("7");
                    break;
                case R.id.btn_8:
                    onClickNumButton("8");
                    break;
                case R.id.btn_9:
                    onClickNumButton("9");
                    break;
                case R.id.btn_spot:
                    onClickPriButton(".");
                    showToast("添加点号后运算可能会出现错误，请谨慎使用");
                    break;
                case R.id.btn_add:
                    onClickPriButton("+");
                    break;
                case R.id.btn_sub:
                    onClickPriButton("-");
                    break;
                case R.id.btn_mul:
                    onClickPriButton("*");
                    break;
                case R.id.btn_div:
                    onClickPriButton("/");
                    break;
                case R.id.btn_sur:
                    showToast("该按钮暂时无功能");
                    //text_process.append("%");
                    //onClickPriButton("%");
                    break;
                case R.id.btn_c:
                    text_result.setText("");
                    text_process.setText("");
                    break;
                case R.id.btn_del:
                    //退格
                    //获取当前的数据
                    Backspace = String.format("%s",text_process.getText());
                    //将当前数据取得子字符串覆盖文本框
                    if(Backspace.length() != 0){
                        text_process.setText(Backspace.substring(0, Backspace.length() - 1));
                    }
                    break;
                case R.id.btn_switch:
                    showToast("该按钮暂时无功能");
                    break;
                case R.id.btn_equal:
                    //等号
                    onClickEqualButton();
                    break;

            }
        }
        /*
        //将值推入栈
        public void pushNumStack(double num){
            numStack.push(num);
        }
        //将符号推入栈
        public void pushPriStack(Character pri){
            priStack.push(pri);
        }
        //将数字推入缓冲区组成值
        public void pushBuffer(String num){
            //if(bufferNumber < 10){
            //    buffer += num;
            //    bufferNumber++;
            //}
        }*/
        //点击数字按钮的功能
        public void onClickNumButton(String num){
            //显示更新
            text_process.append(num);
            //缓冲区更新
            //pushBuffer(num);

            onClickEqualButton();
            text_process.setTextSize(60);
            text_process.setTextColor(this.getResources().getColor(R.color.white));
            text_result.setTextSize(25);
            text_result.setTextColor(this.getResources().getColor(R.color.grey));
        }
        //点击运算符号按钮的功能
        public void onClickPriButton(String pri) {
            //获取当前的数据
            Backspace = String.format("%s",text_process.getText());
            //取出最后一个字符
            if(text_process.getText().length() == 0){
                showToast("运算框为空，无法使用符号");
                return;
            }
            String lastOne = Backspace.substring(Backspace.length() - 1, Backspace.length());
            //判断最后一个字符是否为符号
            if(Backspace.length() != 0 && !lastOne.equals("+") && !lastOne.equals("-") && !lastOne.equals("*") && !lastOne.equals("/") && !lastOne.equals(".")){
                text_process.append(pri);
            }else{
                showToast("请确保最后一位是数字才能添加符号");
            }
            onClickEqualButton();
            text_process.setTextSize(60);
            text_process.setTextColor(this.getResources().getColor(R.color.white));
            text_result.setTextSize(25);
            text_result.setTextColor(this.getResources().getColor(R.color.grey));
            //判断缓冲区
        /*
        if(bufferNumber>0){
            //将缓冲区的数字推入数字栈
            pushNumStack(Double.parseDouble(buffer));
            //将符号入栈
            pushPriStack(pri);
            buffer = null;
        }*/
        }
        //点击等号按钮的功能
        public void onClickEqualButton() {
            //获取当前的数据
            Backspace = String.format("%s",text_process.getText());
            //取得最后一个字符
            String lastOne = Backspace.substring(Backspace.length() - 1, Backspace.length());
            //填入表达式计算结果
            if(!lastOne.equals("+") && !lastOne.equals("-") && !lastOne.equals("*") && !lastOne.equals("/") && !lastOne.equals(".")){
                result = calExp(text_process.getText().toString());
            }else{
                String expression = Backspace.substring(0, Backspace.length() - 1);
                result = calExp(expression);
            }

            //将结果输出到结果字符框
            text_result.setText("= "+String.valueOf(result));
            text_process.setTextSize(25);
            text_process.setTextColor(this.getResources().getColor(R.color.grey));
            text_result.setTextSize(60);
            text_result.setTextColor(this.getResources().getColor(R.color.white));
        }

        /**
         * 运算符优先级map(数字越大，优先级越高)
         */
        static Map<String,Integer> priorityMap = new HashMap<>(4);
        static{
            priorityMap.put("+",1);
            priorityMap.put("-",1);
            priorityMap.put("*",2);
            priorityMap.put("/",2);
            priorityMap.put("(",0);
        }
        /**
         * 计算表达式
         * @param exp（中缀表达式）
         * @return 计算结果
         */
        public static Double calExp(String exp){
            try{
                //logger.info("传入计算表达式：{}", exp);
                List<String> rpnList = transToRPN(exp);
                //logger.info("逆波兰式为：{}",JSON.toJSONString(rpnList));
                Double result = cal(rpnList);
                //logger.info("计算结果为：{}",result);
                return result;
            }catch (Exception e){
                e.printStackTrace();
                //logger.info("表达式计算异常："+exp,e);
            }
            return 0.0;
        }

        /**
         * 转化成逆波兰式
         * @param exp
         * @return
         */
        public static List<String> transToRPN(String exp){
            //操作数栈
            Stack<String> numStack = new Stack<>();
            //运算符栈
            Stack<String> operStack = new Stack<>();
            //转化成字符数组
            char[] expArray = exp.toCharArray();
            StringBuffer sb = new StringBuffer();
            List<String> numOperList = new ArrayList<>();
            //分离运算符和操作数（在运算符和操作数之间添加空格符）
            for (int i = 0; i < expArray.length; i++){
                if (Character.isDigit(expArray[i]) || ".".equals(String.valueOf(expArray[i]))){
                    sb.append(expArray[i]);
                }else{
                    sb.append(" ").append(expArray[i]).append(" ");
                }
            }
            //得到操作数和运算符的数组
            String[] array = sb.toString().trim().split(" ");
            //转化成list
            numOperList = Arrays.asList(array);
            for (int i = 0; i < numOperList.size(); i++) {
                String a = numOperList.get(i);
                //过滤空字符串
                if (a.equals("")){
                    continue;
                }
                if (Character.isDigit(a.charAt(0))){
                    //如果是操作数直接放入操作数栈
                    numStack.push(a);
                }else{
                    if (operStack.isEmpty()){
                        //如果是运算符，且运算符栈是空的，直接将运算符放入运算符栈
                        operStack.push(a);
                        continue;
                    }
                    if (a.equals("(")){
                        //如果是左括号，直接放入运算符栈
                        operStack.push(a);
                        continue;
                    }
                    if (a.equals(")")){
                        //如果是右括号，则运算符栈依次出栈，并放入操作数栈，直到出栈运算符为左括号，并舍弃左括号
                        while (!operStack.peek().toString().equals("(")){
                            numStack.push(operStack.pop());
                        }
                        operStack.pop();
                        continue;
                    }
                    //比较当前运算符和运算符栈顶的运算符
                    //如果栈顶运算符为左括号，则直接放入运算符栈
                    String topOper = operStack.peek();
                    if (topOper.equals("(")){
                        operStack.push(a);
                        continue;
                    }
                    //比较当前运算符的优先级和运算符栈顶运算符的优先级，若大于（等于）栈顶运算符优先级，则直接放入运算符栈
                    if (priorityMap.get(a.toString()) > priorityMap.get(topOper.toString())){
                        operStack.push(a);
                    }else{
                        // 否则，运算符栈顶运算符出栈并放入操作数栈，直到运算符栈顶操作符优先级低于（不包含等于）该运算符优先级
                        do{
                            numStack.push(operStack.pop());
                        }while(!operStack.isEmpty() && priorityMap.get(operStack.peek()) >= priorityMap.get(a));
                        //最后当前运算符放入运算符栈
                        operStack.push(a);
                    }

                }
            }
            //如果上面步骤走完了，运算符栈中还有运算符，则依次出栈，放入到操作数栈
            while(!operStack.isEmpty()){
                numStack.push(operStack.pop());
            }
            return new ArrayList<>(numStack);
        }

        /**
         * 根据逆波兰式计算表达式结果
         * @param list
         * @return
         */
        public static Double cal(List<String> list){
            //System.out.println(JSON.toJSONString(list));
            //计算栈
            Stack<String> s = new Stack<>();
            for (int i = 0; i < list.size(); i++) {
                String a = list.get(i);
                if (Character.isDigit(a.charAt(0))){
                    //如果是数字，则直接入栈
                    s.push(a);
                }else{
                    //如果是操作符，则计算栈栈顶两个元素依次出栈，进行计算，然后将计算结果入栈
                    if (a.equals("+")){
                        Double result = Double.valueOf(s.pop()) + Double.valueOf(s.pop());
                        s.push(String.valueOf(result));
                    }
                    if (a.equals("-")){
                        Double s1 = Double.valueOf(s.pop());
                        Double s2 = Double.valueOf(s.pop());
                        s.push(String.valueOf(s2 - s1));
                    }
                    if (a.equals("*")){
                        Double s1 = Double.valueOf(s.pop());
                        Double s2 = Double.valueOf(s.pop());
                        s.push(String.valueOf(s1*s2));
                    }
                    if (a.equals("/")){
                        Double s1 = Double.valueOf(s.pop());
                        Double s2 = Double.valueOf(s.pop());
                        s.push(String.valueOf(s2 / s1));
                    }
                }
            }
            //计算完成，栈中元素便是计算结果
            return Double.valueOf(s.pop());
        }

    private void showToast(String desc) {
        Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
    }
/*    public static void main(String[] args) {
        //String str = "(50-50*50/50)*5.1/5.1+50*20+15.5*(20-10)/5.0-(2-1)*(5/1.0)-2+(2*2)/ 4.0";
        //String str = "10-1*5-2";
        String str = "(10-3-2-3-1)*(20-10-5)*(3-2-2+2+4)";
        System.out.println(calExp(str));
    }*/
    }