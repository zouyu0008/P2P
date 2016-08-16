package pres.sample.yu.p2p.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import pres.sample.yu.p2p.R;
import pres.sample.yu.p2p.utils.UIUtils;

/**
 * Created by yu on 2016/8/15.
 */

public class RoundProgress extends View {

    private int roundColor;
    private int roundProgressColor;
    private float roundWidth;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    private int progress;
    private float textSize;
    private int textColor;
    private int max;

    private int width;
    private Paint paint;
    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
    public RoundProgress(Context context) {
        this(context,null);
    }

    public RoundProgress(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RoundProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgress);
        roundColor = typedArray.getColor(R.styleable.RoundProgress_roundColor, Color.GRAY);
        roundProgressColor = typedArray.getColor(R.styleable.RoundProgress_roundProgressColor,Color.RED);
        textColor = typedArray.getColor(R.styleable.RoundProgress_textColor, Color.BLUE);
        roundWidth = typedArray.getDimension(R.styleable.RoundProgress_roundWidth, UIUtils.dp2px(10));
        textSize = typedArray.getDimension(R.styleable.RoundProgress_textSize, UIUtils.dp2px(20));

        progress = typedArray.getInteger(R.styleable.RoundProgress_progress, 70);
        max = typedArray.getInteger(R.styleable.RoundProgress_max,100);
        typedArray.recycle();

        //共同的初始化信息
        paint = new Paint();
        paint.setAntiAlias(true);//消除锯齿
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = this.getMeasuredWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //1.绘制圆环
        //获取圆心坐标
        int cx = width / 2;
        int cy = width / 2;
        int radius = (int) (width / 2 - roundWidth / 2);

        //设置Paint的属性
        paint.setColor(roundColor);
        paint.setStrokeWidth(roundWidth);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawCircle(cx, cy, radius, paint);
        //2.绘制圆弧
        paint.setColor(roundProgressColor);

        RectF bounds = new RectF(roundWidth/2,roundWidth/2,width - roundWidth / 2,width - roundWidth / 2);

        canvas.drawArc(bounds, 0, progress * 360 / max, false, paint);

        //3.绘制文本
        //设置字体大小的操作要优先于设置包裹边框的操作
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setStrokeWidth(0);

        String text = progress * 100 / max + "%";
        Rect rect = new Rect();
        paint.getTextBounds(text,0,text.length(),rect);
        int x = width / 2 - rect.width() / 2;
        int y = width /2 + rect.height() / 2;
        canvas.drawText(text,x,y,paint);

    }
}
