package com.auth0.android.lock.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;

public abstract class TestUtils {

    public static int getLockThemeResourceId(Context context, @AttrRes int attrResId) {
        TypedArray a = context.obtainStyledAttributes(com.auth0.android.lock.R.style.Lock_Theme, new int[]{attrResId});
        final int index = a.getResourceId(0, 0);
        a.recycle();
        return index;
    }
}
