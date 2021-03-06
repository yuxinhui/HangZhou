package com.jinfukeji.jinyihuiup.fargment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.jinfukeji.jinyihuiup.JinYiHuiApplication;
import com.jinfukeji.jinyihuiup.R;
import com.jinfukeji.jinyihuiup.activity.PlayVideoActivity;
import com.jinfukeji.jinyihuiup.adapter.XueYuanAdapter;
import com.jinfukeji.jinyihuiup.bean.XueYuanData;
import com.jinfukeji.jinyihuiup.utils.DialogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/31.
 */
public class XueyuanActivity extends Fragment {
    private ListView xueyuan_lv;
    private XueYuanData.DataBean mData=new XueYuanData.DataBean();
    private XueYuanAdapter mAdapter;
    private ArrayList<XueYuanData.DataBean.ResultBean> mBeen=new ArrayList<>();
    private String url= JinYiHuiApplication.URL_BOOT+"video/selectVideo?pageNo=1";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.xueyuan_fg, container,false);
        initData();
        initView(view);
        return view;
    }

    private void initView(View view) {
        xueyuan_lv= (ListView) view.findViewById(R.id.xueyuan_lv);
        mAdapter=new XueYuanAdapter(mBeen,getActivity());
        xueyuan_lv.setAdapter(mAdapter);
        xueyuan_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), PlayVideoActivity.class);
                intent.putExtra("url",mBeen.get(position).getUrl());
                startActivity(intent);
            }
        });
    }

    private void initData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        final ProgressDialog dialog= ProgressDialog.show(getActivity(),"学院界面","加载ing.......");
        StringRequest request=new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Gson gson=new Gson();
                        XueYuanData xueYuanData = gson.fromJson(s, XueYuanData.class);
                        ArrayList<XueYuanData.DataBean.ResultBean> list = (ArrayList<XueYuanData.DataBean.ResultBean>) xueYuanData.getData().getResult();
                        mBeen.addAll(list);
                        mAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        DialogUtils.createToasdt(getActivity(),"学院界面加载失败请检查网络连接");
                        dialog.dismiss();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("title","EIA公布在即");
                return map;
            }
        };
        requestQueue.add(request);
    }
    private void finishActivity(){
        this.finishActivity();
    }
}
