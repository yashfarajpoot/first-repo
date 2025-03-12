package com.faiza1.intent.callback;

import com.faiza1.intent.model.User;

public interface DataCallback<T> {

    void onData(T data);
    void onError(String error);
}
