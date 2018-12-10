package com.ja.mk.baseballex;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ja.mk.baseballex.commons.RecodeData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RecodeListActivity extends AppCompatActivity implements View.OnClickListener{

    public RecyclerView recyclerViewResultList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recode_list);

        Button buttonClose = this.findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(this);

        recyclerViewResultList = this.findViewById(R.id.recyclerViewResultList);

        //레이아웃 동적 생성
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewResultList.setLayoutManager(layoutManager);

        ArrayList<String> dataList = new ArrayList<String>();

        RecodeDataAdapter adapter = new RecodeDataAdapter(dataList);
        recyclerViewResultList.setAdapter(adapter);



        //서버에서 순위 얻어 오기...
        Thread t = new Thread(new GetRecodeListThread(this));
        t.start();



    }

    @Override
    public void onClick(View v) {
        this.finish();
    }
}


class GetRecodeListThread implements Runnable{

    RecodeListActivity appCompatActivity;

    public GetRecodeListThread(RecodeListActivity appCompatActivity){
        this.appCompatActivity = appCompatActivity;
    }

    @Override
    public void run() {
        //test code
        Log.i("mktest" , "=====test start=====");
        try {
            URL url_obj = new URL("http://13.125.252.31/AndroidRest/getRecodeList");
            HttpURLConnection conn = (HttpURLConnection)url_obj.openConnection();
            conn.setRequestMethod("GET");              // default is GET
            conn.setDoInput(true);
            conn.setUseCaches(false);             // 캐싱데이터를 받을지 안받을지
            conn.setConnectTimeout(15000);        // 통신 타임아웃


            int responseCode = conn.getResponseCode();  // connect, send http reuqest, receive htttp request
            Log.i("mktest" , "resCode : " + responseCode );

            //받은 값 처리....JSON

            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                Log.i("mktest" , "[recvData] " + response.toString());

                JSONArray jarray = new JSONArray(response.toString());

                ArrayList<RecodeData> recodeDataList = new ArrayList<RecodeData>();

                for(int i = 0 ; i < jarray.length() ; i++){
                    JSONObject jObject = jarray.getJSONObject(i);

                    Log.i("mktest" , jarray.getString(0) + " : " + jarray.getString(2));

                    recodeDataList.add(new RecodeData(
                            jObject.getString("bbr_idx"),
                            jObject.getString("bbr_nick"),
                            jObject.getString("bbr_content"),
                            jObject.getString("bbr_trycount"),
                            jObject.getString("bbr_trytime"),
                            jObject.getString("bbr_joindate")
                    ));
                }

                ArrayList<String> dataList = new ArrayList<String>();
                int dataCount = 0;
                for(RecodeData r : recodeDataList){
                    dataCount++;

                    if(dataCount > 30) break;

                    Log.i("mktest" , r.getBbr_idx() + " : " + r.getBbr_content());
                    String temp = "[" + r.getBbr_nick() + "]" + r.getBbr_content();
                    temp += " [시도 시간 : " + r.getBbr_trytime() + "]";
                    temp += " [시도 횟수 : " + r.getBbr_trycount() + "]";
                    dataList.add(temp);

                    RecodeDataAdapter adapter = new RecodeDataAdapter(dataList);
                    appCompatActivity.recyclerViewResultList.setAdapter(adapter);
                }

            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                Log.i("mktest" , "[recvData] " + response.toString());
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        Log.i("mktest" , "=====test end=====");
    }
}


//리사이클 리스트뷰 관련...

//뷰홀더 구글 예제
// Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
class ViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    public TextView mTextView;
    public ViewHolder(TextView v) {
        super(v);
        mTextView = v;
    }
}

//Adapter 정의

class RecodeDataAdapter extends RecyclerView.Adapter<ViewHolder> {

    // 아이템 리스트
    private ArrayList<String> mDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecodeDataAdapter(ArrayList<String> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        //View v = LayoutInflater.from(parent.getContext())
        //        .inflate(R.layout.layout, parent, false);

        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout, parent, false);

        // set the view's size, margins, paddings and layout parameters
        //...
        return new ViewHolder((TextView)root);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}





