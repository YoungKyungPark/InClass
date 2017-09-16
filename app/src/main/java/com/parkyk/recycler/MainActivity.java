package com.parkyk.recycler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // xml에 포함된 뷰를 찾아서 변수로 참조값 셋탕
        recyclerview = (RecyclerView)findViewById(R.id.recyclerview);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        test();
    }
    public void  test()
    {
        String src = "{\"amt\":100000, \"sender\":\"홍길동\"}";
    }
}
