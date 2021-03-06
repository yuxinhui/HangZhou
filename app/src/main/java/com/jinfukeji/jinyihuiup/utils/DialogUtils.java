package com.jinfukeji.jinyihuiup.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/7/4.
 */

public class DialogUtils {
    public static void createAlertDialog(Context context, String title, String msg, DialogInterface.OnClickListener onClickListener, DialogInterface.OnClickListener clickListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(msg).setPositiveButton("确定",onClickListener).setNegativeButton("取消", clickListener).create().show();
    }

    public static void createToasdt(Context context, String msg){
        Toast.makeText(context,msg, Toast.LENGTH_LONG).show();
    }
}
