package com.jetlang.remote.client;

import com.jetlang.remote.core.ReadTimeoutEvent;
import org.jetlang.channels.Subscribable;
import org.jetlang.channels.Subscriber;
import org.jetlang.core.Callback;
import org.jetlang.core.Disposable;
import org.jetlang.core.DisposingExecutor;
import org.jetlang.core.SynchronousDisposingExecutor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * User: mrettig
 * Date: 4/6/11
 * Time: 5:59 PM
 */
public interface JetlangClient {

    Subscriber<ConnectEvent> getConnectChannel();

    Subscriber<DisconnectEvent> getDisconnectChannel();

    Subscriber<CloseEvent> getCloseChannel();

    Subscriber<ReadTimeoutEvent> getReadTimeoutChannel();

    <T> void publish(String topic, T msg);

    <T> Disposable subscribe(String subject, Subscribable<T> callback);

    <T> Disposable subscribe(String topic, DisposingExecutor clientFiber, Callback<T> cb);

    void start();

    CountDownLatch close(boolean sendLogoutIfStillConnected);

    Disposable request(String reqTopic,
                       Object req,
                       DisposingExecutor executor,
                       Callback<?> callback,
                       Callback<TimeoutControls> timeoutRunnable, int timeout, TimeUnit timeUnit);
}
