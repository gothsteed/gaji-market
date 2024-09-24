package com.gaji.app.keyword.service;


public interface AlertSubject<T> {
    void attach(AlertObserver<T> observer);
    void detach(AlertObserver<T> observer);
    void notify(T message);
}
