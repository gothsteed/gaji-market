package com.gaji.app.keyword.service;

public interface AlertSubject {
    void attach(AlertObserver observer);
    void detach(AlertObserver observer);
    void notify(String message);
}
