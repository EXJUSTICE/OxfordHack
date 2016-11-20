package com.xu.hookmeup.Util;

import android.animation.Animator;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by marcin on 13.11.16.
 */

public class CascadeAnimator {

    private ViewGroup viewGroup;
    private List<View> viewList;
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

    public CascadeAnimator(List<View> viewList, float offset, float offsetIncrement, float duration, float durationIncrement) {
        this.viewList = viewList;

        this.offset = offset;
        this.offsetIncrement = offsetIncrement;
        this.duration = duration;
        this.durationIncrement = durationIncrement;

        float o = offset;

        for(View view : viewList) {
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

    public void animateList(Callback callback) {
        float o = offset, d = duration;

        for(View view : viewList) {
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

    public void revertList(final Callback callback) {

        for(int i = 0; i < viewList.size() - 2; i++) {
            View view = viewList.get(i);
            view.animate()
                    .setInterpolator(Interpolators.FOSIInterpolator)
                    .setDuration((long) duration)
                    .translationYBy(offset)
                    .alpha(0.0f);
        }

        viewList.get(viewList.size()-1)
                .animate()
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
