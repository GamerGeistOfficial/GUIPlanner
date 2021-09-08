package com.gamergeist.guiplanner.utils;

public class pair<L,R> {
    private final L first;
    private final R second;

    public pair(L first,R second){
        this.first = first;
        this.second = second;
    }

    public L getFirst(){
        return first;
    }

    public R getSecond(){
        return second;
    }
}
