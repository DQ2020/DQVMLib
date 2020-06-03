package com.open.dqmvvm.annotation;

import androidx.annotation.NonNull;

public class Room extends AutoInit {

    @Init(str = "hello,", num = 2020)
    private Data data;

    @NonNull
    @Override
    public String toString() {
        return data.toString();
    }
}
