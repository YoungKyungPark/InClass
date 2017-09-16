package com.parkyk.recycler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * 리싸이클러뷰를 이용한 리스트 처리 구성요소
 * 1. Recyclerview : 리스트를 표현하는 가장 바탕이 되는 뷰
 * 2. custom cell : 리스트에 표현된 하나 하나의 cell(View)을 담당한다.(레이아웃을 직접만든다)
 * 3. Data : 리스트에 포함된 하나 하나의 실질적 정보 (ex) 거래 내역 1건
 * 4. View Holder : Cell 과 Data를 하나로 묶어서 하나의 덩어리로 처리하는 역할을 담당한다.
 * 5. Adapter : 2번,4번,3번 모든 요소를 묶어서 1번과 연결하는 역할 담당한다.
 * 6. LayoutManager : 1번의 모습,방향,특성을 결정한다.
 */

public class MainActivity extends AppCompatActivity {
    RecyclerView  recyclerview;
    ProgressBar progressBar;
    // 3.Data 편의상 통신 후 받은 raw 데이터라고 가정함
    String trxData =  "{\"code\":1, \"body\":[{\"amt\":100000, \"sender\":\"홍길동\"},{\"amt\":3000, \"sender\":\"종로5가\"}]}";
    //trxData 를 파싱해서 담는 클래스(일종의 그릇)
    // 통신 후 raw 데이터를 파싱해서 나온 결과가 이 변수이다.
    BankModel3 resData;
    TrxAdapter trxAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // xml에 포함된 뷰를 찾아서 변수로 참조값 셋탕
        recyclerview = (RecyclerView)findViewById(R.id.recyclerview);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        trxAdapter = new TrxAdapter();
        // 6번 방향 설정
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        // 최종 결함
        recyclerview.setAdapter(trxAdapter);
        test();
    }

    public void onSearch(View view)
    {
        // 사용자가 입출금 거래내역 조회 버튼을 누르면 호출
        // 통신 했다고 치고, --> 받은 raw 데이터를 파싱해서 resdata로 담고
        resData = new Gson().fromJson(trxData,BankModel3.class);
        //recycler view를 갱신하면 시뮬레이션이 완성됨
        // 아답타에게 데이터가 변경되었음을 알려라
        // 화면을 갱신하라.
        trxAdapter.notifyDataSetChanged();

    }
    class TrxAdapter extends RecyclerView.Adapter<TrxViewHolder>
    {

        @Override
        public TrxViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //XML을 뷰로 만들어야 함
            View view = getLayoutInflater().inflate(R.layout.cell_trx_layout,parent,false);
            return new TrxViewHolder(view);
        }
        // 데이터를 뷰홀더에 던져서 셀에 보낸사람, 금액을 셋팅한다.
        @Override
        public void onBindViewHolder(TrxViewHolder holder, int position) {
            holder.bindToPost(resData.getBody().get(position));
        }

        @Override
        public int getItemCount() {
            return resData==null ? 0 : resData.getBody().size();
        }
    }
    class TrxViewHolder extends RecyclerView.ViewHolder
    {
        TextView sender, amt;
        public TrxViewHolder(View itemView) {
            super(itemView);
            // 커스텀 뷰에서 뷰들을 접근할때에는
            //커스텀 뷰.findViewByID(아이디)
            sender = (TextView)itemView.findViewById(R.id.sender);
            amt    = (TextView)itemView.findViewById(R.id.amt);
        }
        //  거래내역 1건이 와서 셀 하나를 셋팅한다.
        public void bindToPost(BankModel1 trx)
        {
            sender.setText(trx.getSender());
            amt.setText(""+trx.getAmt());
        }
    }
    public void  test()
    {
        String src = "{\"amt\":100000, \"sender\":\"홍길동\"}";
        BankModel1 bankModel1 = new Gson().fromJson(src,BankModel1.class);
        Log.i("SH",bankModel1.getSender()+",  "+bankModel1.getAmt());

        src = "{\"code\":1, \"body\":{\"amt\":100000, \"sender\":\"홍길동\"}}\n";
        BankModel2 bankModel2 = new Gson().fromJson(src,BankModel2.class);
        Log.i("SH",bankModel2.body.getSender()+",  "+bankModel2.body.getAmt());

        src = "{\"code\":1, \"body\":[{\"amt\":100000, \"sender\":\"홍길동\"},{\"amt\":3000, \"sender\":\"종로5가\"}]}";
        BankModel3 bankModel3 = new Gson().fromJson(src,BankModel3.class);
        for(BankModel1 bank : bankModel3.getBody())
        {
            Log.i("SH",bank.getSender()+",  "+bank.getAmt());
        }
    }

    class BankModel3
    {
        int code;
        ArrayList<BankModel1> body;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public ArrayList<BankModel1> getBody() {
            return body;
        }

        public void setBody(ArrayList<BankModel1> body) {
            this.body = body;
        }
    }
    class BankModel2
    {
        int code;
        BankModel1 body;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public BankModel1 getBody() {
            return body;
        }

        public void setBody(BankModel1 body) {
            this.body = body;
        }
    }
    class BankModel1
    {
        int amt;
        String sender;

        public int getAmt() {
            return amt;
        }

        public void setAmt(int amt) {
            this.amt = amt;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }
    }
}
