package me.olivine.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * TODO description
 *
 * @author zhenqilai@kugou.net
 * @since 18-10-31
 */
public class DynamicGridLayout extends ViewGroup {

    private int mLineSpace = 0;
    private int mLayerHeight = 0;
    private int mLayer = 0;

    public DynamicGridLayout(Context context) {
        this(context, null);
    }

    public DynamicGridLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DynamicGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DynamicGridLayout);
        mLineSpace = a.getDimensionPixelSize(R.styleable.DynamicGridLayout_lineSpace, 0);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mLayer = 1;
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        switch (heightSpecMode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                mLayerHeight = getSuggestedMinimumHeight();
                break;
            case MeasureSpec.EXACTLY:
                mLayerHeight = heightSpecSize;
                break;
        }
        int maxChildHeight = 0;
        int tmpWidth = 0;
        final int N = getChildCount();
        for (int i = 0; i < N; i++) {
            View v = getChildAt(i);
            if (v.getVisibility() == View.GONE) continue;
            LayoutParams lp = (LayoutParams) v.getLayoutParams();
            measureChild(v, widthMeasureSpec, heightMeasureSpec);
            int current = v.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            maxChildHeight = maxChildHeight < current ? current : maxChildHeight;
            if (mLayerHeight <= maxChildHeight) {
                mLayerHeight = maxChildHeight;
            }
            int curWidth = v.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            tmpWidth += v.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            if (tmpWidth > width) {
                mLayer++;
                tmpWidth = curWidth;
            }
        }
        setMeasuredDimension(width, heightMeasureSpec == MeasureSpec.EXACTLY ? heightSpecSize : mLayerHeight * mLayer + Math.max(0, mLayer - 1) * mLineSpace);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        layoutChildren(l, r, t, b);
    }

    private void layoutChildren(int l, int r, int t, int b) {
        final int N = getChildCount();
        int offsetX = 0;
        int offsetY = 0;

        for (int i = 0; i < N; i++) {
            View v = getChildAt(i);
            if (v.getVisibility() == View.GONE) {
                continue;
            }
            int w = v.getMeasuredWidth();
            int h = v.getMeasuredHeight();
            LayoutParams lp = (LayoutParams) v.getLayoutParams();
            int marginTop = (mLayerHeight - lp.topMargin - lp.bottomMargin - h) / 2 + lp.topMargin;
            if (offsetX + w + lp.leftMargin > getMeasuredWidth()) {
                offsetX = 0;
                offsetY += mLayerHeight + mLineSpace;
                int childLeft = offsetX + lp.leftMargin;
                v.layout(childLeft, offsetY + marginTop, childLeft + w, offsetY + marginTop + h);
            } else {
                int childLeft = offsetX + lp.leftMargin;
                v.layout(childLeft, offsetY + marginTop, childLeft + w, offsetY + marginTop + h);
            }
            offsetX += w + lp.leftMargin + lp.rightMargin;
        }
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p.width, p.height);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    public int getLineSpace() {
        return mLineSpace;
    }

    public void setLineSpace(int lineSpace) {
        mLineSpace = lineSpace;
    }

    public static final class LayoutParams extends MarginLayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }
    }
}
