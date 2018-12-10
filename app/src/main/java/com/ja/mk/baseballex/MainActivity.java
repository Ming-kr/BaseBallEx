package com.ja.mk.baseballex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ja.mk.baseballex.commons.NumberDataController;
import com.ja.mk.baseballex.commons.ResultData;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //숫자 버튼
        //이벤트 연결
        Button btn1 = this.findViewById(R.id.button);
        Button btn2 = this.findViewById(R.id.button2);
        Button btn3 = this.findViewById(R.id.button3);
        Button btn4 = this.findViewById(R.id.button4);
        Button btn5 = this.findViewById(R.id.button5);
        Button btn6 = this.findViewById(R.id.button6);
        Button btn7 = this.findViewById(R.id.button7);
        Button btn8 = this.findViewById(R.id.button8);
        Button btn9 = this.findViewById(R.id.button9);
        Button btn0 = this.findViewById(R.id.button0);

        ButtonEvent buttonEvent = new ButtonEvent(this);
        btn1.setOnClickListener(buttonEvent);
        btn2.setOnClickListener(buttonEvent);
        btn3.setOnClickListener(buttonEvent);
        btn4.setOnClickListener(buttonEvent);
        btn5.setOnClickListener(buttonEvent);
        btn6.setOnClickListener(buttonEvent);
        btn7.setOnClickListener(buttonEvent);
        btn8.setOnClickListener(buttonEvent);
        btn9.setOnClickListener(buttonEvent);
        btn0.setOnClickListener(buttonEvent);

        //다음 버튼
        //이벤트 연결
        Button buttonNext = this.findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ResultActivity.class);
                v.getContext().startActivity(intent);
                v.setVisibility(View.INVISIBLE);
            }
        });

        //다시하기 버튼
        //이벤트 연결
        Button buttonReset = this.findViewById(R.id.buttonReset);
        buttonReset.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //모든 번호 보여주기...
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                activity.findViewById(R.id.button).setVisibility(View.VISIBLE);
                activity.findViewById(R.id.button2).setVisibility(View.VISIBLE);
                activity.findViewById(R.id.button3).setVisibility(View.VISIBLE);
                activity.findViewById(R.id.button4).setVisibility(View.VISIBLE);
                activity.findViewById(R.id.button5).setVisibility(View.VISIBLE);
                activity.findViewById(R.id.button6).setVisibility(View.VISIBLE);
                activity.findViewById(R.id.button7).setVisibility(View.VISIBLE);
                activity.findViewById(R.id.button8).setVisibility(View.VISIBLE);
                activity.findViewById(R.id.button9).setVisibility(View.VISIBLE);
                activity.findViewById(R.id.button0).setVisibility(View.VISIBLE);

                activity.findViewById(R.id.buttonNext).setVisibility(View.INVISIBLE);
                activity.findViewById(R.id.buttonReset).setVisibility(View.INVISIBLE);

                ((TextView)activity.findViewById(R.id.textViewInputData)).setText("");

                //리셋
                NumberDataController.newNumberData();
            }
        });

        Button buttonTest = this.findViewById(R.id.buttonTest);
        buttonTest.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"번호 출력 : " + NumberDataController.getNumberData(),Toast.LENGTH_SHORT).show();
            }
        });




        //버튼 히든...
        buttonNext.setVisibility(View.INVISIBLE);
        buttonReset.setVisibility(View.INVISIBLE);

        //번호 초기화
        NumberDataController.newNumberData();
    }
}


class ButtonEvent implements View.OnClickListener{

    private MainActivity mainActivity;

    public ButtonEvent(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @Override
    public void onClick(View v) {
        TextView textViewInputData = mainActivity.findViewById(R.id.textViewInputData);
        Button eventBtn = (Button)v;
        eventBtn.setVisibility(View.INVISIBLE);

        String strValue = textViewInputData.getText().toString();

        strValue += eventBtn.getText().toString();

        if(strValue.length() >= 4){

            //이스터 에그
            if(strValue.equals("2103")){
                Button btnTest = mainActivity.findViewById(R.id.buttonTest);
                btnTest.setVisibility(View.VISIBLE);
            }


            //번호 확인
            ResultData resultData = NumberDataController.equalsNumber(strValue);

            if(resultData.isFinish()){
                //피니쉬...
                //메세지 처리...
                String result = "추카 합니다!! 클리어 성공~~!!";
                result += "\r\n";
                result += "Try Count : " + NumberDataController.getTryCount();
                result += "\r\n";
                result += "Try Time : " + NumberDataController.getTryTime() + "s";

                Toast.makeText(mainActivity,result,Toast.LENGTH_SHORT).show();

                ((TextView)mainActivity.findViewById(R.id.textViewResult)).setText(result);
                //기록 추가
                //다시하기
                Button buttonNext = mainActivity.findViewById(R.id.buttonNext);
                Button buttonReset = mainActivity.findViewById(R.id.buttonReset);

                buttonNext.setVisibility(View.VISIBLE);
                buttonReset.setVisibility(View.VISIBLE);

                //숫자 번호 숨기기...
                Button btn = mainActivity.findViewById(R.id.button);
                btn.setVisibility(View.INVISIBLE);
                btn = mainActivity.findViewById(R.id.button2);
                btn.setVisibility(View.INVISIBLE);
                btn = mainActivity.findViewById(R.id.button3);
                btn.setVisibility(View.INVISIBLE);
                btn = mainActivity.findViewById(R.id.button4);
                btn.setVisibility(View.INVISIBLE);
                btn = mainActivity.findViewById(R.id.button5);
                btn.setVisibility(View.INVISIBLE);
                btn = mainActivity.findViewById(R.id.button6);
                btn.setVisibility(View.INVISIBLE);
                btn = mainActivity.findViewById(R.id.button7);
                btn.setVisibility(View.INVISIBLE);
                btn = mainActivity.findViewById(R.id.button8);
                btn.setVisibility(View.INVISIBLE);
                btn = mainActivity.findViewById(R.id.button9);
                btn.setVisibility(View.INVISIBLE);
                btn = mainActivity.findViewById(R.id.button0);
                btn.setVisibility(View.INVISIBLE);

            }else{
                //결과 출력
                TextView textViewResult = mainActivity.findViewById(R.id.textViewResult);
                String strResult = "";
                strResult += resultData.getStrike() +
                        "스트라이크 , " +
                        resultData.getBall() +
                        "볼";

                textViewResult.setText(strResult);

                strValue = "";

                //모든 번호 보여주기...
                Button btn = mainActivity.findViewById(R.id.button);
                btn.setVisibility(View.VISIBLE);
                btn = mainActivity.findViewById(R.id.button2);
                btn.setVisibility(View.VISIBLE);
                btn = mainActivity.findViewById(R.id.button3);
                btn.setVisibility(View.VISIBLE);
                btn = mainActivity.findViewById(R.id.button4);
                btn.setVisibility(View.VISIBLE);
                btn = mainActivity.findViewById(R.id.button5);
                btn.setVisibility(View.VISIBLE);
                btn = mainActivity.findViewById(R.id.button6);
                btn.setVisibility(View.VISIBLE);
                btn = mainActivity.findViewById(R.id.button7);
                btn.setVisibility(View.VISIBLE);
                btn = mainActivity.findViewById(R.id.button8);
                btn.setVisibility(View.VISIBLE);
                btn = mainActivity.findViewById(R.id.button9);
                btn.setVisibility(View.VISIBLE);
                btn = mainActivity.findViewById(R.id.button0);
                btn.setVisibility(View.VISIBLE);

            }
        }

        textViewInputData.setText(strValue);
    }
}



