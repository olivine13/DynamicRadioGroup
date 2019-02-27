# DynamicRadioGroup
A RadioGroup support dynamic add widget which implements Checkable

## Features:
1. Dynamic add checkable view.
2. Support dispatch checked change listener.
3. Support dynaimc add any view in DynamicGridLayout.class
4. no other third library.

## Usage
It is easy to setup like a LinearLayout.
xml:
```
<me.olivine.widget.DynamicRadioGroupLayout
    android:id="@+id/radioGroup"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:lineSpace="3dp"/>
```
then, you can dynamic add checkable view in code.
```
DynamicGridLayout.LayoutParams lp = new DynamicGridLayout.LayoutParam (DynamicGridLayout.LayoutParams.WRAP_CONTENT, DynamicGridLayout.LayoutParams.WRAP_CONTENT;
lp.rightMargin = 20;
mRadioGroupLayout.addView(createCheckedTextView(getApplicationContext(), "No." + (mRadioGroupLayout.getChildCount() + 1)), lp);        
```
and more, you can register listener like this
```
mRadioGroupLayout.setOnCheckedChangeListener(new DynamicRadioGroupLayout.SimpleOnCheckedChangeListener() {
     @Override
    public void onCheckedChanged(DynamicRadioGroupLayout parent, View child, boolean checkable) {
        Log.i(TAG, ((CheckedTextView) child).getText() + " checkable");
    }
});
```