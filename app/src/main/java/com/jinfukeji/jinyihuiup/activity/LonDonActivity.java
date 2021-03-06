package com.jinfukeji.jinyihuiup.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jinfukeji.jinyihuiup.R;
import com.jinfukeji.jinyihuiup.adapter.LonDonJinAdapter;
import com.jinfukeji.jinyihuiup.bean.LondonJinData;
import com.jinfukeji.jinyihuiup.utils.DialogUtils;

import java.util.ArrayList;

/**
 * Created by "于志渊"
 * 时间:"15:04"
 * 包名:com.yuxinhui.text.myapplication.Actiity
 * 描述:
 */
public class LonDonActivity extends Fragment {
    private ListView londonjin_lv;
    private LonDonJinAdapter mLonDonJinAdapter;
    private ArrayList<LondonJinData> mLondonJinDatas = new ArrayList<LondonJinData>();
    private String urlLonDon = "http://pull.api.fxgold.com/realtime/products?codes=IXLEAHD3M,IXLENID3M,IXLECAD3M,IXLENAD3M,IXLEPBD3M,IXLESND3M,LEAAD3M,LEAHD3M,LECAD3M,IXLEZSD3M,LEMOD3M,LENID3M,LEPBD3M,LECOD3M,LEZSD3M,LESND3M";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_londonmetal, null);
        initLonDonData();
        initView(view);
        return view;
    }
    private void initView(View view) {
        londonjin_lv= (ListView) view.findViewById(R.id.londonjin_lv);
        mLonDonJinAdapter=new LonDonJinAdapter(mLondonJinDatas,getActivity());
        londonjin_lv.setAdapter(mLonDonJinAdapter);
    }
    private void initLonDonData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.GET,
                urlLonDon,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Gson gson = new Gson();
                        ArrayList<LondonJinData> list = gson.fromJson(s, new TypeToken<ArrayList<LondonJinData>>() {
                        }.getType());
                        mLonDonJinAdapter.initList(list);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        DialogUtils.createToasdt(getActivity(),"伦敦金加载失败");
                    }
                }
        );
        requestQueue.add(request);
    }
}
