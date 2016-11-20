package com.xu.hookmeup.Util;

import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by marcin on 13.11.16.
 */

public class Interpolators {

    // Interpolator
    public static final Interpolator FOSIInterpolator = new FastOutSlowInInterpolator();
    public static final Interpolator LInterpolator = new LinearInterpolator();
}
