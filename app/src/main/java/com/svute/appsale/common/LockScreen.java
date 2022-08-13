package com.svute.appsale.common;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lviet on 8/13/2022.
 */
public class LockScreen {
    public static void disableLL(ViewGroup layout,boolean lock) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            child.setEnabled(lock);
            child.setClickable(lock);
            if (child instanceof ViewGroup)
                disableLL((ViewGroup) child, lock);
        }
    }
}

