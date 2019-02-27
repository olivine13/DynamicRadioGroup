package me.olivine.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;

/**
 * TODO description
 *
 * @author zhenqilai@kugou.net
 * @since 19-2-25
 */
public class DynamicRadioGroupLayout extends DynamicGridLayout {

    public enum Mode {
        SINGLE, MULTIPLE
    }

    private Mode mMode = Mode.SINGLE;

    private OnCheckedChangeListener mOnCheckedChangeListener;

    public DynamicRadioGroupLayout(Context context) {
        this(context, null);
    }

    public DynamicRadioGroupLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DynamicRadioGroupLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
            @Override
            public void onChildViewAdded(final View parent, View child) {
                child.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (mMode) {
                            case SINGLE: {
                                Checkable checkable = (Checkable) v;
                                if (mOnCheckedChangeListener != null && mOnCheckedChangeListener.onInterceptedCheckedChanged(DynamicRadioGroupLayout.this, v, !checkable.isChecked())) {
                                    return;
                                }
                                if (checkable.isChecked()) return;
                                checkable.setChecked(true);
                                // 清除其他项的选中状态
                                final int N = getChildCount();
                                for (int i = 0; i < N; i++) {
                                    View temp = getChildAt(i);
                                    if (v != temp) {
                                        ((Checkable)temp).setChecked(false);
                                    }
                                }
                                if (mOnCheckedChangeListener != null) {
                                    mOnCheckedChangeListener.onCheckedChanged(DynamicRadioGroupLayout.this, v, true);
                                }
                            }
                            break;
                            case MULTIPLE: {
                                ((Checkable) v).toggle();
                                if (mOnCheckedChangeListener != null) {
                                    mOnCheckedChangeListener.onCheckedChanged(DynamicRadioGroupLayout.this, v, ((Checkable) v).isChecked());
                                }
                            }
                            break;
                        }
                    }
                });
            }

            @Override
            public void onChildViewRemoved(View parent, View child) {

            }
        });
    }

    public void setSelectMode(Mode mode) {
        mMode = mode;
    }

    @Override
    public void addView(View child, int position) {
        if (!checkChildIsSupportCheckable(child)) {
            throw new IllegalArgumentException("child must support checkable");
        }
        super.addView(child, position);
    }

    @Override
    public void addView(View child, int width, int height) {
        if (!checkChildIsSupportCheckable(child)) {
            throw new IllegalArgumentException("child must support checkable");
        }
        super.addView(child, width, height);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (!checkChildIsSupportCheckable(child)) {
            throw new IllegalArgumentException("child must support checkable");
        }
        super.addView(child, index, params);
    }

    private boolean checkChildIsSupportCheckable(View v) {
        return v instanceof Checkable;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        mOnCheckedChangeListener = onCheckedChangeListener;
    }

    public interface OnCheckedChangeListener {
        boolean onInterceptedCheckedChanged(DynamicRadioGroupLayout parent, View child, boolean checkable);
        void onCheckedChanged(DynamicRadioGroupLayout parent, View child, boolean checkable);
    }

    public static class SimpleOnCheckedChangeListener implements OnCheckedChangeListener {

        @Override
        public boolean onInterceptedCheckedChanged(DynamicRadioGroupLayout parent, View child, boolean checkable) {
            return false;
        }

        @Override
        public void onCheckedChanged(DynamicRadioGroupLayout parent, View child, boolean checkable) {

        }
    }
}
