package me.olivine.dynamicradiogroup;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckedTextView;

import me.olivine.widget.DynamicGridLayout;
import me.olivine.widget.DynamicRadioGroupLayout;
import me.olivine.widget.dynamicradiogroup.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    View mBtnAdd;
    DynamicRadioGroupLayout mRadioGroupLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnAdd = findViewById(R.id.add);
        mRadioGroupLayout = findViewById(R.id.radioGroup);
        mRadioGroupLayout.setSelectMode(DynamicRadioGroupLayout.Mode.SINGLE);
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DynamicGridLayout.LayoutParams lp = new DynamicGridLayout.LayoutParams(DynamicGridLayout.LayoutParams.WRAP_CONTENT, DynamicGridLayout.LayoutParams.WRAP_CONTENT);
                lp.rightMargin = 20;
                mRadioGroupLayout.addView(createCheckedTextView(getApplicationContext(), "No." + (mRadioGroupLayout.getChildCount() + 1)), lp);
            }
        });
        mRadioGroupLayout.setOnCheckedChangeListener(new DynamicRadioGroupLayout.SimpleOnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(DynamicRadioGroupLayout parent, View child, boolean checkable) {
                Log.i(TAG, ((CheckedTextView) child).getText() + " checkable");
            }
        });
    }

    private CheckedTextView createCheckedTextView(Context context, String name) {
        CheckedTextView tv = new CheckedTextView(context);
        tv.setBackgroundResource(R.drawable.bg_selector);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        tv.setTextColor(Color.WHITE);
        tv.setText(name);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        return tv;
    }
}
