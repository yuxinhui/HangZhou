package com.jinfukeji.jinyihuiup.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.jinfukeji.jinyihuiup.R;
import com.jinfukeji.jinyihuiup.utils.DialogUtils;
import com.jinfukeji.jinyihuiup.utils.Player;

public class PlayVideoActivity extends AppCompatActivity {
    private SurfaceView surfaceView;
    ImageView mivPlay;
    private SeekBar skbProgress;
    private Player player;
    String url;
    private boolean isPlayed = false, isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play_video);
        checkWifi();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        surfaceView = (SurfaceView) this.findViewById(R.id.surfaceView1);

        mivPlay = (ImageView) findViewById(R.id.play);
        mivPlay.setOnClickListener(new ClickEvent());

        skbProgress = (SeekBar) this.findViewById(R.id.skbProgress);
        skbProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());
        player = new Player(surfaceView, skbProgress);
    }

    class ClickEvent implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            if (isPlaying) {
                mivPlay.setImageResource(R.mipmap.icon_play);
                player.pause();
            } else {
                mivPlay.setImageResource(R.mipmap.icon_pause);
                if (isPlayed) {
                    player.play();
                } else {
                    player.playUrl(url);
                    isPlayed = true;
                }
            }
            isPlaying = !isPlaying;
        }
    }

    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
        int progress;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            // 原本是(progress/seekBar.getMax())*player.mediaPlayer.getDuration()
            this.progress = progress * player.mediaPlayer.getDuration()
                    / seekBar.getMax();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字
            player.mediaPlayer.seekTo(progress);
        }
    }

    @Override
    public void finish() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (isPlaying) {
            player.stop();
        }
        super.finish();
    }

    private boolean checkWifi() {
        boolean isWifiConnect = true;
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = cm.getAllNetworkInfo();
        for (int i = 0; i < networkInfos.length; i++) {
            if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
                if (networkInfos[i].getType() == cm.TYPE_MOBILE) {
                    DialogUtils.createAlertDialog(this, null, "当前使用非wifi，继续使用将会产生流量\n是否继续", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    isWifiConnect = false;
                }
                if (networkInfos[i].getType() == cm.TYPE_WIFI) {
                    isWifiConnect = true;
                }
            }
        }
        return isWifiConnect;
    }
}