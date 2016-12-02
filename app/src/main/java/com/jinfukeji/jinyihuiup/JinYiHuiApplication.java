package com.jinfukeji.jinyihuiup;

import android.app.Application;

import com.jinfukeji.jinyihuiup.bean.User;

/**
 * Created by "于志渊"
 * 时间:"10:46"
 * 包名:com.jinfukeji.jinyihuiup
 * 描述:用于存放全局变量的类
 */

public class JinYiHuiApplication extends Application{
    private boolean isLogin=false;
    private boolean isRing = true;
    private boolean isVirbate = true;
    private boolean isOpenMiandarao = false;
    private User user = new User();
    private static JinYiHuiApplication instace;
    private int MIANDAORAO;
    public static final String URL_BOOT = "http://114.55.11.183:8080/";
    @Override
    public void onCreate() {
        super.onCreate();
        instace = this;
    }

    public int getMIANDAORAO() {
        return MIANDAORAO;
    }

    public void setMIANDAORAO(int MIANDAORAO) {
        this.MIANDAORAO = MIANDAORAO;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public boolean isRing() {
        return isRing;
    }

    public void setRing(boolean ring) {
        isRing = ring;
    }

    public boolean isVirbate() {
        return isVirbate;
    }

    public void setVirbate(boolean virbate) {
        isVirbate = virbate;
    }

    public boolean isOpenMiandarao() {
        return isOpenMiandarao;
    }

    public void setOpenMiandarao(boolean openMiandarao) {
        isOpenMiandarao = openMiandarao;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static JinYiHuiApplication getInstace() {
        return instace;
    }

    public static void setInstace(JinYiHuiApplication instace) {
        JinYiHuiApplication.instace = instace;
    }

    public void unLoginclear() {
        user = null;
        isLogin = false;
    }

    public static String getUrlBoot() {
        return URL_BOOT;
    }
}
