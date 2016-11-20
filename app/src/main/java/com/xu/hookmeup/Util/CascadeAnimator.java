package com.xu.hookmeup.Util;

import android.animation.Animator;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by marcin on 13.11.16.
 */

public class CascadeAnimator {

    private final ViewGroup viewGroup;
    private final float offset, offsetIncrement, duration, durationIncrement;

    public CascadeAnimator(ViewGroup viewGroup, float offset, float offsetIncrement, float duration, float durationIncrement) {
        this.viewGroup = viewGroup;

        this.offset = offset;
        this.offsetIncrement = offsetIncrement;
        this.duration = duration;
        this.durationIncrement = durationIncrement;

        float o = offset;

        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);

            view.setTranslationY(o);
            view.setAlpha(0.0f);

            o += offsetIncrement;
        }
    }

    public void animate() {
        float o = offset, d = duration;

        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);

            view.animate()
                    .setInterpolator(Interpolators.FOSIInterpolator)
                    .setDuration((long) d)
                    .translationYBy(-o)
                    .alpha(1.0f);

            o += offsetIncrement;
            d += durationIncrement;
        }
    }

    public void animate(Callback callback) {
        float o = offset, d = duration;

        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);

            view.animate()
                    .setInterpolator(Interpolators.FOSIInterpolator)
                    .setDuration((long) d)
                    .translationYBy(-o)
                    .alpha(1.0f);

            o += offsetIncrement;
            d += durationIncrement;
        }

        callback.execute();
    }

    public void revert(final Callback callback) {
        viewGroup.animate()
                .setInterpolator(Interpolators.FOSIInterpolator)
                .setDuration((long) duration)
                .translationYBy(offset)
                .alpha(0.0f)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        callback.execute();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
    }

    public void revert() {
        viewGroup.animate()
                .setInterpolator(Interpolators.FOSIInterpolator)
                .setDuration((long) duration)
                .translationYBy(offset)
                .alpha(0.0f);
    }
}
