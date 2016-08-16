package pres.sample.yu.p2p.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * Created by yu on 2016/8/14.
 * 实现布局上下拖动并越界返回（难点）
 *
 */
//TODO 越界返回
public class MyScrollView extends ScrollView {
    private View childView;
    public MyScrollView(Context context) {
        this(context,null);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //onFinishInflate 当View中所有的子控件均被映射成xml后触发
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    Rect normal = new Rect();
    private boolean isFinishAnimation = true;//是否动画结束


    int lastY;
    int lastX,downX,downY;
    //父视图MyScrollView要对子视图拦截操作
    //返回值为true：表示拦截成功。反之，表示拦截失败
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercept = false;
        int eventX = (int) ev.getX();
        int eventY = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN :
                downX = lastX = eventX;
                downY = lastY = eventY;

                break;
            case MotionEvent.ACTION_MOVE:

                int totalX = Math.abs(eventX - downX);
                int totalY = Math.abs(eventY - downY);

                if(totalY > totalX && totalY > 10){
                    isIntercept = true;
                }

                lastX = eventX;
                lastY = eventY;
                break;
        }

        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //如果子视图为空且动画未完成
        if(childView == null || !isFinishAnimation){
            //表示会分发事件
            return super.onTouchEvent(ev);
        }

        int eventY = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN :
                lastY = eventY;
                break;

            case MotionEvent.ACTION_MOVE:
                //移动的数值
                int dy = eventY - lastY;

                //在移动的过程中，对ScrollView中的子视图不断的重新定位
                if(isNeedMove()){
                    if(normal.isEmpty()){//如果normal没有被赋过值，第一次调用childView的layout()之前赋值
                        normal.set(childView.getLeft(),childView.getTop(),childView.getRight(),childView.getBottom());
                    }

                    childView.layout(childView.getLeft(),childView.getTop() + dy / 2,childView.getRight(),childView.getBottom() + dy / 2);
                }

                lastY = eventY;
                break;
            case MotionEvent.ACTION_UP:
                if(isNeedAnimation()){
                    animation();
                }

                break;
        }

        return true;
    }

    /**
     * 实现y轴方向上视图的平移
     */
    private void animation() {

        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, normal.top - childView.getTop());
        animation.setDuration(300);
//        animation.setFillAfter(true);//错误的：不应该使用此种方式保留视图的最终位置
        childView.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isFinishAnimation = false;
            }
            //动画结束时的回调
            @Override
            public void onAnimationEnd(Animation animation) {
                //清除动画
                childView.clearAnimation();
                //将布局指定在最初的位置
                childView.layout(normal.left,normal.top,normal.right,normal.bottom);
                //清除normal
                normal.setEmpty();
                //表示动画结束
                isFinishAnimation = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    /**
     * 判断是否需要移动
     * @return
     */
    private boolean isNeedAnimation() {
        return !normal.isEmpty();
    }

    /**
     * 返回是否需要重新布局
     * @return
     */
    private boolean isNeedMove() {
        //获取子视图的高度
        int measuredHeight = childView.getMeasuredHeight();
        //获取当前ScrollView的高度。如果ScrollView是match_parent。则获取的就是屏幕的高度
        int height = this.getHeight();
        Log.e("TAG", "measureHeight = " + measuredHeight + ",height = " + height);

        //得到子视图和ScrollView的高度的差值
        int offSet = measuredHeight - height;

        //获取垂直方向上移动的数值：上移为+，下移为-
        int scrollY = childView.getScrollY();
        //当scrollY == 0：初始化的状态
        //scrollY == offSet：滑动到SrollView底部的状态
        if(scrollY == 0 || scrollY == offSet){
            return true;
        }

        return false;
    }

}
