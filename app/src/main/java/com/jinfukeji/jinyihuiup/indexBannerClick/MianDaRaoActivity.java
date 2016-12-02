package com.jinfukeji.jinyihuiup.indexBannerClick;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jinfukeji.jinyihuiup.JinYiHuiApplication;
import com.jinfukeji.jinyihuiup.R;

import java.util.Calendar;

public class MianDaRaoActivity extends AppCompatActivity implements View.OnClickListener{
    //表示是否开启消息震动和声音的常量
    private static final int OPEN = 0;
    private static final int OPEN_IN_NIGHT = 1;
    private static final int CLOSE = 2;

    ImageView mivReturn,mivOpenmsg,mivOpneinnight,mivClosemsg;
    RelativeLayout mOpenMsg,mOpenInNight,mCloseMsg;
    AudioManager audioManager;
    boolean isOpenMiandarao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mian_da_rao);
        isOpenMiandarao = JinYiHuiApplication.getInstace().isOpenMiandarao();
        initView();
        setOnclickListener();
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

    }

    private void setOnclickListener() {
        mivReturn.setOnClickListener(this);
        mOpenMsg.setOnClickListener(this);
        mOpenInNight.setOnClickListener(this);
        mCloseMsg.setOnClickListener(this);
    }

    private void initView() {
        mivReturn = (ImageView) findViewById(R.id.miandarao_back);
        mivOpenmsg = (ImageView) findViewById(R.id.iv_open_msg);
        mivOpneinnight = (ImageView) findViewById(R.id.iv_openonnight);
        mivClosemsg = (ImageView) findViewById(R.id.iv_close_msg);
        mOpenMsg = (RelativeLayout) findViewById(R.id.open_msg);
        mOpenInNight = (RelativeLayout) findViewById(R.id.openonnight);
        mCloseMsg = (RelativeLayout) findViewById(R.id.close_msg);
        if(JinYiHuiApplication.getInstace().getMIANDAORAO()==OPEN){
            showChecked(mivClosemsg);
        }else if (JinYiHuiApplication.getInstace().getMIANDAORAO()==CLOSE){
            showChecked(mivOpenmsg);
        }else {
            showChecked(mivOpneinnight);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.miandarao_back:
                this.finish();
                break;
            case R.id.open_msg:
                showChecked(mivOpenmsg);
                JinYiHuiApplication.getInstace().setMIANDAORAO(CLOSE);
                JinYiHuiApplication.getInstace().setOpenMiandarao(false);
                JinYiHuiApplication.getInstace().setRing(true);
                JinYiHuiApplication.getInstace().setVirbate(true);
                Intent tent = new Intent("openMsg");
                sendBroadcast(tent);
                break;
            case R.id.openonnight:
               showChecked(mivOpneinnight);
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                if(hour>22||hour<8){
                    audioManager.setStreamVolume(AudioManager.STREAM_ALARM,0,0);
                    JinYiHuiApplication.getInstace().setOpenMiandarao(true);
                }
                JinYiHuiApplication.getInstace().setMIANDAORAO(OPEN_IN_NIGHT);
                JinYiHuiApplication.getInstace().setOpenMiandarao(false);
                break;
            case R.id.close_msg:
                showChecked(mivClosemsg);
                audioManager.setStreamVolume(AudioManager.STREAM_ALARM,0,0);
                audioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION, AudioManager.VIBRATE_SETTING_OFF);
                JinYiHuiApplication.getInstace().setMIANDAORAO(OPEN);
                JinYiHuiApplication.getInstace().setOpenMiandarao(true);
                JinYiHuiApplication.getInstace().setRing(false);
                JinYiHuiApplication.getInstace().setVirbate(false);
                Intent intent = new Intent("ChangeRingAndVibrate");
                sendBroadcast(intent);
                break;
        }
    }

    public void showChecked(ImageView imageView){
        mivOpenmsg.setVisibility(View.GONE);
        mivClosemsg.setVisibility(View.GONE);
        mivOpneinnight.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
    }
}
