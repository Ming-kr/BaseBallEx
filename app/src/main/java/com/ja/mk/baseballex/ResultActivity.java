package com.ja.mk.baseballex;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ja.mk.baseballex.commons.NumberDataController;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class ResultActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView textViewResultData = this.findViewById(R.id.textViewResultData);
        String tempResultData = "시도 횟수 : ";
        tempResultData += NumberDataController.getTryCount();
        tempResultData += "\r\n";
        tempResultData += "시도 시간 : ";
        tempResultData += NumberDataController.getTryTime();

        textViewResultData.setText(tempResultData);

        //버튼 이벤트 등록
        Button buttonSendData = this.findViewById(R.id.buttonSendData);
        buttonSendData.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        EditText editTextName = this.findViewById(R.id.editTextName);
        EditText editTextContent = this.findViewById(R.id.editTextContent);


        //서버에 전송
        String nick = editTextName.getText().toString();
        String content = editTextContent.getText().toString();
        String tryCount = "" + NumberDataController.getTryCount();
        String tryTime = "" + NumberDataController.getTryTime();

        if(nick == null || nick.equals("") || content == null || content.equals("")){
            Toast.makeText(v.getContext(),"닉네임 혹은 내용을 채워 주세요~",Toast.LENGTH_SHORT).show();
            return;
        }

        /*
        //값 설정
        final ArrayList<NameValuePair> paramList = new ArrayList<NameValuePair>();
        paramList.add(new BasicNameValuePair("m_idx","1"));
        paramList.add(new BasicNameValuePair("c_title","androidtestest"));
        paramList.add(new BasicNameValuePair("c_content","androidtestest"));

        final String urlPath = "http://13.125.252.31/writeContentFormAction";
        */
        //메인 쓰레스에서 네트워크 처리 불가...
        //쓰레드 생성...
        Thread t = new Thread(new InsertRecodeDataThread(nick,content,tryCount,tryTime));

        t.start();

        Intent intent = new Intent(this,RecodeListActivity.class);
        this.startActivity(intent);

        this.finish();
    }

/*
    public void requestPost(String requestUrl, ArrayList<NameValuePair> paramList){
        Log.i("mktest" , "=====서버전송 시작=====");

        try{
            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost(requestUrl);
            request.setEntity(new UrlEncodedFormEntity(paramList,"UTF-8"));

            client.execute(request);

        }catch (Exception e){
            e.printStackTrace();
        }

        Log.i("mktest" , "=====서버전송 끝=====");
    }
*/
}


class InsertRecodeDataThread implements Runnable{
    private String nick;
    private String content;
    private String tryCount;
    private String tryTime;

    public InsertRecodeDataThread(String nick , String content , String tryCount , String tryTime){
        this.nick = nick;
        this.content = content;
        this.tryCount = tryCount;
        this.tryTime = tryTime;
    }

    @Override
    public void run() {
        //test code
        Log.i("mktest" , "=====test start=====");
        try {
            URL url_obj = new URL("http://13.125.252.31/AndroidRest/insertRecode");
            HttpURLConnection conn = (HttpURLConnection)url_obj.openConnection();
            //con.setRequestMethod("GET");              // default is GET
            //conn.setRequestProperty("content-type", "application/json");
            conn.setRequestMethod("POST");              // default is GET
            //con.setDoInput(true);                            // default is true
            conn.setDoOutput(true);                          //default is false
            //InputStream in = con.getInputStream();
            OutputStream out = conn.getOutputStream();  // internally change to 'POST'

            //OutputStreamWriter writer = new OutputStreamWriter(out);
            //HashMap<String,String> mapParam = new HashMap<String,String>();
            //mapParam.put("bbr_nick" , nick);
            //mapParam.put("bbr_content" , content);
            //mapParam.put("bbr_trycount" , tryCount);
            //mapParam.put("bbr_trytime" , tryTime);
            //writer.write(mapParam.toString());
            //writer.flush();

            String sendData = "bbr_nick=" +  nick;
            sendData += "&bbr_content=" + content;
            sendData += "&bbr_trycount=" + tryCount;
            sendData += "&bbr_trytime=" + tryTime;

            out.write(sendData.getBytes("utf-8"));
            out.close();

            int resCode = conn.getResponseCode();  // connect, send http reuqest, receive htttp request
            Log.i("mktest" , "resCode : " + resCode );

            //받은 값 처리....JSON


        }catch (Exception e){
            e.printStackTrace();
        }

        Log.i("mktest" , "=====test end=====");
        //requestPost(urlPath , paramList); //어? 이게 왜 되지??
    }
}