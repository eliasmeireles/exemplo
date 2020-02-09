package com.example.canvas.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.canvas.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CustomView extends View {

    private static final int SQUARE_SIZE_DEF = 200;
    private static final int CIRCLE_PAINT_SIZE = 10;
    private Rect mRectSquare;
    private Paint mPaintSquare;
    private Paint textPaint;
    private Paint mCirclePaint;
    private Paint mCirclePaintCenter;
    private int mSquareColor;
    private int mSquareSize;
    List<Integer> chartValues = new ArrayList<>();

    public CustomView(Context context) {
        super(context);
        init(null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }


    private void init(@Nullable AttributeSet set) {
        mPaintSquare = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setAntiAlias(true);
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(35);
        mCirclePaintCenter = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaintCenter.setAntiAlias(true);

        mRectSquare = new Rect();

        if (set == null) {
            return;
        }

        TypedArray typedArray = getContext().obtainStyledAttributes(set, R.styleable.CustomView);
        mSquareColor = typedArray.getColor(R.styleable.CustomView_square_color, Color.GREEN);
        mSquareSize = typedArray.getDimensionPixelSize(R.styleable.CustomView_square_size, SQUARE_SIZE_DEF);
        mCirclePaint.setColor(Color.parseColor("#00ccff"));
        mCirclePaintCenter.setColor(Color.RED);

        mPaintSquare.setColor(mSquareColor);
        typedArray.recycle();
    }

    public void swapColor() {
        mPaintSquare.setColor(mPaintSquare.getColor() == mSquareColor ? Color.RED : mSquareColor);
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int centerScreenXAxis = getWidth() / 2;
        int centerScreenYAxis = getHeight() / 2;

        mRectSquare.left = 0;
        mRectSquare.top = getHeight() - (getHeight() - 85);
        mRectSquare.right = getWidth() - (getWidth() - 85);
        mRectSquare.bottom = getHeight();

//        canvas.drawRect(mRectSquare, mPaintSquare);
//        xAxisTopToYAxisRight(canvas);
//        xAxisBottomToYAxisLeft(canvas);
//        yAxisLeftToXAxisTop(canvas);
//        yAxisRightToXAxisBottom(canvas);
        chartBar(canvas);
    }

    public void setChartValues(List<Integer> values) {
        this.chartValues = values;
        postInvalidate();
    }

    private void chartBar(Canvas canvas) {

        int barSize = 85;
        int leftCount = 0;

        for (int i = 0; i < chartValues.size(); i++) {
            int maximumHeight = getMaximumHeight(i);

            Rect rect = new Rect();
            rect.left = leftCount;
            rect.top = (getHeight() - maximumHeight);
            rect.right = barSize + leftCount;
            rect.bottom = getHeight();
            canvas.drawRect(rect, mPaintSquare);
            canvas.rotate(-65, leftCount, (getHeight() - maximumHeight));
            canvas.drawText(String.valueOf(chartValues.get(i)), leftCount + 25, (getHeight() - maximumHeight) + 15, textPaint);
            canvas.translate(0, 0);
            canvas.save();
            canvas.restore();
            canvas.rotate(65, leftCount, (getHeight() - maximumHeight));
            leftCount += barSize + 6;
        }

    }

    private int getMaximumHeight(int index) {
        int highestValue = getHighestValue(chartValues);
        Integer value = chartValues.get(index);
        int height = getHeight() - 150;

        if (highestValue >= height) {
            double valuePercent = ((highestValue - height) * 100) / highestValue;
            double realPercent = valuePercent / 100;

            if (highestValue != value) {
                int displayBarSize = value - ((int) ((double) value * realPercent));
                return displayBarSize;
            } else {
                return height;
            }
        }

        return value;

    }

    private int getHighestValue(List<Integer> chartValues) {
        ArrayList<Integer> integers = new ArrayList<>(chartValues);
        Collections.sort(integers);
        Collections.reverse(integers);
        return integers.get(0);
    }

    private void xAxisTopToYAxisRight(Canvas canvas) {

        int height = getHeight();
        int width = getWidth();
        int size = height < width ? height / 2 : width / 2;

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, size, mCirclePaint);
    }

    private void xAxisBottomToYAxisLeft(Canvas canvas) {

        int height = getHeight();
        int width = getWidth();
        int size = (height < width ? height / 2 : width / 2) - 1;

        int colorStart = 0;
        for (int i = size; i > 0; i -= 2) {
            if (i > (size / 2)) {
                colorStart = colorStart < 255 ? (colorStart + 1) : colorStart;
            } else {
                colorStart = colorStart > 0 ? (colorStart - 1) : colorStart;
            }

            int rgbColor = Color.rgb(colorStart, 0, 0);
            mCirclePaintCenter.setColor(rgbColor);
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, i, mCirclePaintCenter);
            Log.e("SIZE", String.valueOf(colorStart));
        }
    }

    private void yAxisLeftToXAxisTop(Canvas canvas) {
        canvas.drawCircle(CIRCLE_PAINT_SIZE, getHeight() / 2, CIRCLE_PAINT_SIZE, mCirclePaint);
    }

    private void yAxisRightToXAxisBottom(Canvas canvas) {
        canvas.drawCircle(getWidth() - CIRCLE_PAINT_SIZE, getHeight() / 2, CIRCLE_PAINT_SIZE, mCirclePaint);
    }
}
