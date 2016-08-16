package pres.sample.yu.p2p.common;

import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by yu on 2016/8/13.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler{
    private Context mContext;
    private CrashHandler(){

    }
    private static CrashHandler crashHandler=null;
    public static CrashHandler getIntence(){
        if(crashHandler==null) {
            crashHandler=new CrashHandler();
        }
        return crashHandler;
    }
    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        if(throwable==null) {
            defaultUncaughtExceptionHandler.uncaughtException(thread, throwable);
        }else {
            collectionException(throwable);
        }
    }

    private void collectionException(final Throwable throwable) {
        new Thread(){
            public void run(){
                Looper.prepare();
                Toast.makeText(MyApplication.context, "亲，应用错误，两秒后退出", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        saveEXInfo(throwable);
        try {
            Thread.sleep(2000);
            AppManager.getinstance().removeAll();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void saveEXInfo(Throwable throwable) {
        String message = throwable.getMessage();
        String phone=Build.DEVICE + "--" + Build.PRODUCT + "---" + Build.MODEL + "--" + Build.VERSION.SDK_INT;

    }
    public void init(Context context){
        mContext = context;
//        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
//        Thread.setDefaultUncaughtExceptionHandler(this);
    }

}
