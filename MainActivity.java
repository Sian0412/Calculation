package com.example.calculation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button[] btnNums = new Button[10];
    private int[] btnNumId = {R.id.btnN0,R.id.btnN1,R.id.btnN2,R.id.btnN3,R.id.btnN4,R.id.btnN5,R.id.btnN6
            ,R.id.btnN7,R.id.btnN8,R.id.btnN9,};//數字
    private Button[] btns = new Button[5];
    private int[] btnsId = {R.id.btnAdd,R.id.btnSub,R.id.btnMult,R.id.btnDiv,R.id.btnDot};//運算子
    private TextView tvResult,tvFormula;
    private Button btnEqu,btnClean,btnDel;
    private int initial = 0,Dotinitial = 0; //第一次為0 被點擊過1 第三次以後2
    private int cal = 0 ;// + =1 ; - = 2 ;  × = 3 ;  ÷ = 4
    private String str1,str2;//暫存數字
    private float ans,result,x;//ans計算用 result結果用 x判斷除數不為0
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        tvFormula.setText("0");
        tvResult.setText("0");
    }
    //顯示
    private void MySet(String set){
        if(tvFormula.getText() == "0" || tvResult.getText() == "0"){
            tvFormula.setText("");
            tvResult.setText("");
        }
        tvFormula.setText(tvFormula.getText().toString()+set);
        tvResult.setText(tvResult.getText().toString()+set);
    }
    //刪除
    private void MyDel(){
        String Formula,lastword,Result;// 暫存字串用
        lastword = tvFormula.getText().toString();
        if(tvFormula.length()>0){
            Formula = tvFormula.getText().toString();
            char lastchar = lastword.charAt(tvFormula.length()-1);
            if(lastchar == '+' || lastchar == '-' || lastchar == '×' ||lastchar  == '÷'){
                cal = 0;
                if(initial != 0){
                    initial--;
                }
                tvResult.setText(tvFormula.getText());
            }else if(lastchar == '.'){
                Dotinitial = 0;
            }
            tvFormula.setText(Formula.substring(0,Formula.length()-1));
        }
        if(tvResult.length()>0){
            Result = tvResult.getText().toString();
            tvResult.setText(Result.substring(0,Result.length()-1));
        }
        if(tvResult.length() ==0 && tvFormula.length() ==0){
            tvFormula.setText("0");
            tvResult.setText("0");
            cal = 0;
            initial = 0;
        }
    }
    //清除
    private void MyClean(){
        tvFormula.setText("0");
        tvResult.setText("0");
        cal = 0;
        initial = 0;
    }

    // + - * / 運算過程
    private void MyFormula(String s,int c){
            if (c == 1 || c == 2 || c==3) {
                if (initial == 0) {
                    MyPlan_0(s,c);
                } else if (initial == 1) {
                    MyPlan_1(s,c);
                } else {
                    MyPlan_2(s,c);
                }
            }else{
                if (initial == 0) {
                    MyPlan_0(s,c);
                } else if (initial == 1) {
                    x = Float.parseFloat(tvResult.getText().toString());
                    if (x != 0) {
                        MyPlan_1(s,c);
                    } else {
                        Toast.makeText(MainActivity.this, "不可除以0", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    x = Float.parseFloat(tvResult.getText().toString());
                    if (x != 0) {
                        MyPlan_2(s,c);
                    } else {
                        Toast.makeText(MainActivity.this, "不可除以0", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    }
    //第1次 運算被點擊
    private void MyPlan_0(String s ,int c){
        str1 = tvResult.getText().toString();
        tvResult.setText("");
        tvFormula.setText(tvFormula.getText().toString() + s);
        initial = 1;
        cal = c;
    }
    //第二次 運算被點擊
    private void MyPlan_1(String s,int c){
        str2 = tvResult.getText().toString();
        if(c == 1){
            ans = Float.parseFloat(str1) + Float.parseFloat(str2);
        }else if(c == 2){
            ans = Float.parseFloat(str1) - Float.parseFloat(str2);
        }else if(c == 3){
            ans = Float.parseFloat(str1) * Float.parseFloat(str2);
        }else{
            ans = Float.parseFloat(str1) / Float.parseFloat(str2);
        }
        tvResult.setText("");
        tvFormula.setText(ans + s);
        initial = 2;
        cal = c;
    }
    //兩次以上運算被點擊
    private void MyPlan_2(String s ,int c){
        str1 = String.valueOf(ans);
        str2 = tvResult.getText().toString();
        if(c == 1){
            ans = Float.parseFloat(str1) + Float.parseFloat(str2);
        }else if(c == 2){
            ans = Float.parseFloat(str1) - Float.parseFloat(str2);
        }else if(c == 3){
            ans = Float.parseFloat(str1) * Float.parseFloat(str2);
        }else{
            ans = Float.parseFloat(str1) / Float.parseFloat(str2);
        }
        tvResult.setText("");
        tvFormula.setText(ans + s);
        initial = 2;
        cal = c;
    }
    //答案
    private void MyEqu(){
        if(cal !=0) {
            if(tvResult.getText().toString().equals("")){
                str2 = "0";
            }else{
                str2 = tvResult.getText().toString();
            }
            if (cal == 1) {
                if (initial == 1) {
                    result = Float.parseFloat(str1) + Float.parseFloat(str2);
                } else {
                    result = ans + Float.parseFloat(str2);
                }
                tvResult.setText(String.valueOf(result));
                tvFormula.setText("");
                str1 = tvResult.getText().toString();
                cal = 0;
                initial = 0;
            } else if (cal == 2) {
                if (initial != 0) {
                    result = Float.parseFloat(str1) - Float.parseFloat(str2);
                } else {
                    result = ans - Float.parseFloat(str2);
                }
                tvResult.setText(String.valueOf(result));
                tvFormula.setText("");
                str1 = tvResult.getText().toString();
                cal = 0;
                initial = 0;
            } else if (cal == 3) {
                if (initial != 0) {
                    result = Float.parseFloat(str1) * Float.parseFloat(str2);
                } else {
                    result = ans * Float.parseFloat(str2);
                }
                tvResult.setText(String.valueOf(result));
                tvFormula.setText("");
                str1 = tvResult.getText().toString();
                cal = 0;
                initial = 0;
            } else {
                x = Float.parseFloat(tvResult.getText().toString());
                if (x != 0) {
                    if (initial != 0) {
                        result = Float.parseFloat(str1) / Float.parseFloat(str2);
                    } else {
                        result = ans * Float.parseFloat(str2);
                    }
                    tvResult.setText(String.valueOf(result));
                    tvFormula.setText("");
                    str1 = tvResult.getText().toString();
                    cal = 0;
                    initial = 0;
                } else {
                    Toast.makeText(MainActivity.this, "不可除以0", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    //實作 監聽
    private void findView() {
        tvResult = findViewById(R.id.tvResult);
        tvResult.setOnClickListener(this);
        tvFormula = findViewById(R.id.tvFormula);
        tvFormula.setOnClickListener(this);
        btnEqu = findViewById(R.id.btnEqu);
        btnEqu.setOnClickListener(this);
        btnClean = findViewById(R.id.btnClean);
        btnClean.setOnClickListener(this);
        btnDel = findViewById(R.id.btnDel);
        btnDel.setOnClickListener(this);
        for(int i=0;i<btnNums.length;i++){
            btnNums[i] = findViewById(btnNumId[i]);
            btnNums[i].setOnClickListener(this);
        }
        for(int i=0;i<btns.length;i++){
            btns[i] = findViewById(btnsId[i]);
            btns[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        String lastword;
        lastword = tvFormula.getText().toString();
        switch (v.getId()) {
            case R.id.btnN0:
                MySet("0");
                break;
            case R.id.btnN1:
                MySet("1");
                break;
            case R.id.btnN2:
                MySet("2");
                break;
            case R.id.btnN3:
                MySet("3");
                break;
            case R.id.btnN4:
                MySet("4");
                break;
            case R.id.btnN5:
                MySet("5");
                break;
            case R.id.btnN6:
                MySet("6");
                break;
            case R.id.btnN7:
                MySet("7");
                break;
            case R.id.btnN8:
                MySet("8");
                break;
            case R.id.btnN9:
                MySet("9");
                break;
            case R.id.btnAdd:
                if(tvFormula.getText().equals("")){
                    tvFormula.setText(tvResult.getText());
                    if (cal == 1 || cal == 0) {
                        MyFormula("+", 1);
                    }
                }else{
                    char add = lastword.charAt(tvFormula.length()-1);
                    if(add != '+'){
                        if (cal == 1 || cal == 0) {
                            MyFormula("+", 1);
                        }
                    }
                }
                break;
            case R.id.btnSub:
                if(tvFormula.getText().equals("")){
                    tvFormula.setText(tvResult.getText());
                    if (cal == 2 || cal == 0) {
                        MyFormula("-", 2);
                    }
                }else{
                    char sub = lastword.charAt(tvFormula.length()-1);
                    if(sub!='-'){
                        if (cal == 2 || cal == 0) {
                            MyFormula("-", 2);
                        }
                    }
                }
                break;
            case R.id.btnMult:
                if(tvFormula.getText().equals("")){
                    tvFormula.setText(tvResult.getText());
                    if (cal == 3 || cal == 0) {
                        MyFormula("×", 3);
                    }
                }else{
                    char mult = lastword.charAt(tvFormula.length()-1);
                    if(mult != '×'){
                        if (cal == 3 || cal == 0) {
                            MyFormula("×", 3);
                        }
                    }
                }
                break;
            case R.id.btnDiv:
                if(tvFormula.getText().equals("")){
                    tvFormula.setText(tvFormula.getText());
                    if (cal == 4 || cal == 0) {
                        MyFormula("÷", 4);
                    }
                }else{
                    char div = lastword.charAt(tvFormula.length()-1);
                    if(div != '÷'){
                        if (cal == 4 || cal == 0) {
                            MyFormula("÷", 4);
                        }
                    }
                }
                break;
            case R.id.btnDot:
                if (Dotinitial == 0) {
                    MySet(".");
                    Dotinitial = 1;
                }
                break;
            case R.id.btnClean:
                MyClean();
                break;
            case R.id.btnDel:
                MyDel();
                break;
            case R.id.btnEqu:
                MyEqu();
                break;
            }

    }
}
