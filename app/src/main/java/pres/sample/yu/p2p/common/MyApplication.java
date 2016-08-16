package pres.sample.yu.p2p.common;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by shkstart on 2016/8/12 0012.
 */
public class MyApplication extends Application {

    public static Context context;
    public static Handler handler;
    public static Thread mainThread;//主线程
    public static int mainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        handler = new Handler();
        mainThread = Thread.currentThread();//当前实例化MyApplication的线程，就是主线程
        mainThreadId = android.os.Process.myTid();//获取当前线程的id
        CrashHandler.getIntence().init(this);

    }
}
