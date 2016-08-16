package pres.sample.yu.p2p.common;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by shkstart on 2016/8/12 0012.
 *
 * 提供页面中显示的Activity的管理：AppManager （设置为单例模式）
 * 涉及到activity的添加、删除指定、删除当前、删除所有、返回栈大小的方法
 *
 */
public class AppManager {
    private AppManager() {
    }
    private static AppManager instance=new AppManager();
    public static AppManager getinstance(){
        return instance;
    }
    private Stack<Activity> stack=new Stack<>();
    public void add(Activity activity){
        stack.add(activity);
    }
    public void remove(Activity activity){
        if(activity != null){
            for(int i = stack.size() - 1; i >= 0 ; i--) {
                //判断两个activity所属的类是否一致
                if(activity.getClass() == stack.get(i).getClass()){
                    //销毁当前的activity
                    stack.get(i).finish();
                    //从栈空间移除
                    stack.remove(i);
                }

            }

        }
    }
    /**
     * 删除当前的activity
     */
    public void removeCurrent(){
//        activityStack.remove(activityStack.size() - 1);

        stack.remove(stack.lastElement());
    }

    /**
     * 删除所有的activity
     */
    public void removeAll(){
        for(int i = stack.size() - 1; i >= 0 ; i--) {
            //销毁当前的activity
            stack.get(i).finish();
            //从栈空间移除
            stack.remove(i);
        }
    }

    /**
     * 返回activity栈的大小
     */
    public int getSize(){
        return stack.size();
    }
}
