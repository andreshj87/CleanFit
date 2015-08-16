package com.zireck.projectk.domain.interactor;

import com.zireck.projectk.domain.executor.PostExecutionThread;
import com.zireck.projectk.domain.executor.ThreadExecutor;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Abstract class for an Interactor.
 * This interface represents an execution unit for different use cases (this means any use case in
 * the application should implement this contract).
 *
 * By convention, each Interactor implementation will return the result using a {@link Subscriber}
 * that will execute its job in a background thread and will post the result in the UI thread.
 */
public abstract class Interactor {

    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mPostExecutionThread;

    private Subscription mSubscription = Subscriptions.empty();

    protected Interactor(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        mThreadExecutor = threadExecutor;
        mPostExecutionThread = postExecutionThread;
    }

    /**
     * Builds an {@link Observable} which will be used when executing the current {@link Interactor}.
     */
    protected abstract Observable buildInteractorObservable();

    /**
     * Executes the current interactor.
     *
     * @param interactorSubscriber The object who will be listening to the observable built with
     *                             {@link #buildInteractorObservable()}.
     */
    @SuppressWarnings("unchecked")
    public void execute(Subscriber interactorSubscriber) {
        mSubscription = buildInteractorObservable()
                .subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mPostExecutionThread.getScheduler())
                .subscribe(interactorSubscriber);
    }

    /**
     * Unsubscribes from current {@link Subscription}.
     */
    public void unsubscribe() {
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
