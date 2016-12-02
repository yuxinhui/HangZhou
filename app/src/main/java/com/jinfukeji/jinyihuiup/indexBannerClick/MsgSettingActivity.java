package com.jinfukeji.jinyihuiup.indexBannerClick;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jinfukeji.jinyihuiup.JinYiHuiApplication;
import com.jinfukeji.jinyihuiup.R;
import com.jinfukeji.jinyihuiup.activity.Denglu;

public class MsgSettingActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView mivReturn,mivZhendong,mivAudio;
    RelativeLayout miandarao;
    Button unLogin;
    boolean isVibrate= JinYiHuiApplication.getInstace().isVirbate(),isRing=JinYiHuiApplication.getInstace().isRing();
    AudioManager am;
    int streamMaxVolume;
    boolean isLogin;
    AlarmReceiver mReceiver;
    NotAlarmReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_setting);
        am = (AudioManager) getSystemService(AUDIO_SERVICE);
        streamMaxVolume = am.getStreamMaxVolume(AudioManager.STREAM_ALARM);
        isLogin = JinYiHuiApplication.getInstace().isLogin();
        mReceiver = new AlarmReceiver();
        IntentFilter filter = new IntentFilter("ChangeRingAndVibrate");
        registerReceiver(mReceiver, filter);
        receiver = new NotAlarmReceiver();
        IntentFilter intentFilter = new IntentFilter("openMsg");
        registerReceiver(receiver, intentFilter);
        initView();
        setOnClickLIstener();
    }

    private void setOnClickLIstener() {
        mivReturn.setOnClickListener(this);
        mivZhendong.setOnClickListener(this);
        mivAudio.setOnClickListener(this);
        miandarao.setOnClickListener(this);
        unLogin.setOnClickListener(this);
    }

    private void initView() {
        mivReturn = (ImageView) findViewById(R.id.msg_setting_back);
        mivZhendong = (ImageView) findViewById(R.id.iv_vibrate_setting);
        mivAudio = (ImageView) findViewById(R.id.iv_audio_setting);
        miandarao = (RelativeLayout) findViewById(R.id.miandarao);
        unLogin = (Button) findViewById(R.id.un_login);
        if(JinYiHuiApplication.getInstace().isOpenMiandarao()){
            mivZhendong.setImageResource(R.mipmap.kaiguan);
            mivZhendong.setClickable(false);
            mivAudio.setImageResource(R.mipmap.kaiguan);
            mivAudio.setClickable(false);
        }else{
            if(!isVibrate){
                mivZhendong.setImageResource(R.mipmap.kaiguan);
            }
            if (!isRing) {
                mivAudio.setImageResource(R.mipmap.kaiguan);
            }
        }
        if (!isLogin) {
            unLogin.setText("点击登陆");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.msg_setting_back:
                this.finish();
                break;
            case R.id.iv_vibrate_setting:
                if(isVibrate){
                    mivZhendong.setImageResource(R.mipmap.kaiguan);
                    am.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION, AudioManager.VIBRATE_SETTING_OFF);
                }else {
                    mivZhendong.setImageResource(R.mipmap.icon_opne);
                    am.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION, AudioManager.VIBRATE_SETTING_ON);
                }
                isVibrate = !isVibrate;
                JinYiHuiApplication.getInstace().setVirbate(isVibrate);
                break;
            case R.id.iv_audio_setting:
                if (isRing) {
                    mivAudio.setImageResource(R.mipmap.kaiguan);
                    am.setStreamVolume(AudioManager.STREAM_ALARM,0,0);
                }else{
                    mivAudio.setImageResource(R.mipmap.icon_opne);
                    am.setStreamVolume(AudioManager.STREAM_ALARM,streamMaxVolume,0);
                }
                isRing = !isRing;
                JinYiHuiApplication.getInstace().setRing(isRing);
                break;
            case R.id.miandarao:
                Intent intent = new Intent(this,MianDaRaoActivity.class);
                startActivity(intent);
                break;
            case R.id.un_login:
                if(isLogin){
                    unLogin.setText("点击登陆");
                    JinYiHuiApplication.getInstace().unLoginclear();
                }else {
                    Intent intent1 = new Intent(this, Denglu.class);
                    startActivity(intent1);
                }
                break;
        }
    }

    class NotAlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            mivZhendong.setImageResource(R.mipmap.icon_opne);
            mivZhendong.setClickable(true);
            mivAudio.setImageResource(R.mipmap.icon_opne);
            mivAudio.setClickable(true);
        }
    }

    class AlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            mivZhendong.setImageResource(R.mipmap.kaiguan);
                            mivZhendong.setClickable(false);
                            mivAudio.setImageResource(R.mipmap.kaiguan);
                            mivAudio.setClickable(false);
                        }
                    });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        unregisterReceiver(receiver);
    }
}
