package com.gaji.app.keyword.service;

public interface AlertObserver<T> {

    void alert(T message);

}