package com.zireck.projectk.presentation.presenter;

import android.support.annotation.NonNull;

import com.zireck.projectk.presentation.view.View;

/**
 * Interface representing a Presenter in a Model-View-Presenter (MVP) pattern.
 */
public interface Presenter {

    /**
     * Method that assigns a View to the Presenter.
     */
    <T extends View> void setView(@NonNull T view);

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onResume() method.
     */
    void resume();

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onPause() method.
     */
    void pause();

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onDestroy() method.
     */
    void destroy();
}
