package com.zireck.calories.presentation.helper;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by Zireck on 25/09/2015.
 */
public class AppBarLayoutSnapBehavior extends AppBarLayout.Behavior {

    private ValueAnimator mAnimator;
    private boolean mNestedScrollStarted = false;

    public AppBarLayoutSnapBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child,
                                       View directTargetChild, View target, int nestedScrollAxes) {
        mNestedScrollStarted = super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
        if (mNestedScrollStarted && mAnimator != null) {
            mAnimator.cancel();
        }
        return mNestedScrollStarted;
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);

        if (!mNestedScrollStarted) {
            return;
        }

        mNestedScrollStarted = false;

        int scrollRange = child.getTotalScrollRange();
        int topOffset = getTopAndBottomOffset();

        if (topOffset <= -scrollRange || topOffset >= 0) {
            // Already fully visible or fully invisible
            return;
        }

        if (topOffset < -(scrollRange / 2f)) {
            // Snap up (to fully invisible)
            animateOffsetTo(-scrollRange);
        } else {
            // Snap down (to fully visible)
            animateOffsetTo(0);
        }
    }

    private void animateOffsetTo(int offset) {
        if (mAnimator == null) {
            mAnimator = new ValueAnimator();
            mAnimator.setInterpolator(new DecelerateInterpolator());
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    setTopAndBottomOffset((int) animation.getAnimatedValue());
                }
            });
        } else {
            mAnimator.cancel();
        }

        mAnimator.setIntValues(getTopAndBottomOffset(), offset);
        mAnimator.start();
    }
}
