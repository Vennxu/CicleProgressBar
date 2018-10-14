package com.venn.round.library;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Venn
 * create at 2018/10/12
 * description: 圆形进度条
 */
public class RoundProgressBar extends View {

    private Paint mBackgroundPaint = new Paint();
    private Paint mFullPaint = new Paint();
    private Paint mRoundPaint = new Paint();
    private Paint mThumbOutPaint = new Paint();
    private Paint mCircePaint = new Paint();

    private float radius = 100;
    private float thumbRadius = 15;
    private float thumbOutRadius = 20;

    private float currentProgress = 100;
    private int maxProgress = 100;

    private float bgProgressWidth = 10;
    private float fullProgressWidth = 10;

    private float startAngle = 270;

    private RectF rectF;

    private int colorStart = Color.parseColor("#3B3258");
    private int colorEnd = Color.parseColor("#3385ff");

    private int colorFull = colorStart;

    private int colorThumbOut = Color.parseColor("#A63385ff");
    private int colorThumb = Color.parseColor("#3385ff");

    private int colorBg = Color.parseColor("#eeeeee");

    private ArgbEvaluator evaluator;


    public RoundProgressBar(Context context) {
        this(context, null);

    }

    public RoundProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStyle(context, attrs, defStyleAttr);
    }

    private void initStyle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        if (attrs == null) {
            return;
        }
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);
        colorStart = array.getColor(R.styleable.RoundProgressBar_round_start_color, colorStart);
        colorEnd = array.getColor(R.styleable.RoundProgressBar_round_end_color, colorEnd);
        colorBg = array.getColor(R.styleable.RoundProgressBar_round_bg_color, colorBg);
        colorThumb = array.getColor(R.styleable.RoundProgressBar_round_thumb_color, colorThumb);
        colorThumbOut = array.getColor(R.styleable.RoundProgressBar_round_thumb_out_color, colorThumbOut);
        thumbRadius = array.getDimension(R.styleable.RoundProgressBar_round_thumb_radius, thumbRadius);
        thumbOutRadius = array.getDimension(R.styleable.RoundProgressBar_round_thumb_out_radius, thumbOutRadius);
        radius = array.getDimension(R.styleable.RoundProgressBar_round_radius, radius);
        currentProgress = array.getFloat(R.styleable.RoundProgressBar_round_progress, 0.0f);
        maxProgress = array.getInteger(R.styleable.RoundProgressBar_round_max_progress, maxProgress);
        bgProgressWidth = array.getDimension(R.styleable.RoundProgressBar_round_bg_progress_width, bgProgressWidth);
        fullProgressWidth = array.getDimension(R.styleable.RoundProgressBar_round_full_progress_width, fullProgressWidth);
        startAngle = array.getDimension(R.styleable.RoundProgressBar_round_start_angle, startAngle);
        array.recycle();
    }

    private void initPaint() {
        evaluator = new ArgbEvaluator();
        mBackgroundPaint.setColor(colorBg);
        mBackgroundPaint.setStyle(Paint.Style.STROKE);
        mBackgroundPaint.setStrokeWidth(bgProgressWidth);
        mBackgroundPaint.setAntiAlias(true);

        mFullPaint.setStyle(Paint.Style.STROKE);
        mFullPaint.setStrokeWidth(fullProgressWidth);
        mFullPaint.setAntiAlias(true);

        mCircePaint.setColor(colorStart);

        mRoundPaint.setColor(colorEnd);
        mRoundPaint.setAntiAlias(true);

        mThumbOutPaint.setColor(colorThumbOut);
        mThumbOutPaint.setAntiAlias(true);

        rectF = getRectF();
        mFullPaint.setShader(getGradient());
        mFullPaint.setStrokeCap(Paint.Cap.ROUND);

    }

    private RectF getRectF() {
        float x = thumbOutRadius < thumbRadius ? thumbRadius : thumbOutRadius;
        float y = x;
        return new RectF(x, y, x + (radius * 2), y + (radius * 2));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint();
        drawFull(canvas);
    }

    private void drawFull(Canvas canvas) {
        float angle = getCurrentRotation();
        canvas.drawArc(rectF, startAngle, 360, false, mBackgroundPaint);
        canvas.drawArc(rectF, startAngle, angle, false, mFullPaint);

        float radian = getRadiansAngle(angle, startAngle);

        float x1 = getAngleX(rectF.centerX(), radian, (int) radius);
        float y1 = getAngleY(rectF.centerY(), radian, (int) radius);

        mRoundPaint.setColor(colorFull);
        canvas.drawCircle(x1, y1, thumbRadius, mRoundPaint);
        canvas.drawCircle(x1, y1, thumbOutRadius, mThumbOutPaint);

    }

    private LinearGradient getGradient(){
        float x = thumbOutRadius < thumbRadius ? thumbRadius : thumbOutRadius;
        float y = x;
        rectF = new RectF(x, y, x + (radius * 2), y + (radius * 2) );
//        float radian0 = getRadiansAngle(0, startAngle);
//        float radian1 = getRadiansAngle(180, startAngle);
        float radian = (float) Math.toRadians(90);

        float x0 = getAngleX(rectF.centerX(), -90, radius);
        float y0 = getAngleY(rectF.centerY(), -90, radius);

        float x1 = getAngleX(rectF.centerX(), radian, radius);
        float y1 = getAngleY(rectF.centerY(), radian, radius);

        return new LinearGradient(x0, y0, x1, y1, new int[]{colorStart, colorEnd},
                null, LinearGradient.TileMode.CLAMP);
    }

//    private void drawFullGradient(Canvas canvas, float angle){
//        for (int i = 0; i < angle; i++) {
//            colorFull = (Integer) evaluator.evaluate(i / 360f, colorStart, colorEnd);
//            mFullPaint.setColor(colorFull);
//            if (i < maxProgress * 360) {
//                canvas.drawArc(rectF, (float) (-180 + i), 1.35f, false, mFullPaint);
//            }
//        }
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width;
        int height;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            width = specSize;
        } else {
            width = (int) ((2 * radius) + bgProgressWidth + (thumbRadius * 2));
        }
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            height = specSize;
        } else {
            height = (int) ((2 * radius) + bgProgressWidth + (thumbRadius * 2));
        }
        setMeasuredDimension(width, height);
    }

    private float getCurrentRotation() {
        return 360 * (currentProgress / maxProgress);
    }

    private float getRadiansAngle(float angle, float startAngle) {
        float radiansAngle = angle + startAngle;
        return (float) Math.toRadians(radiansAngle > 360 ? radiansAngle - 360 : radiansAngle);
    }

    private float getAngleX(float centerX, float radian, float radius) {
        return (float) (centerX + Math.cos(radian) * radius);
    }

    private float getAngleY(float centerY, float radian, float radius) {
        return (float) (centerY + Math.sin(radian) * radius);
    }

    public void setCurrentProgress(float currentProgress) {
        this.currentProgress = currentProgress;
        invalidate();
    }
}
