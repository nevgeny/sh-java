package com.github.nevgeny;

import java.net.SocketException;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) throws SocketException, UnknownHostException, InterruptedException {
        var sh = new StatsHouse("127.0.0.1", 13337, "dev");
        var metric = sh.metric("test_jv", "method", "type");
        for (long i = 0; ; i++) {
            metric.count(i, "get", "test");
            metric.value(new double[]{i, i}, "put", "test");
            metric.stringTop("stop", "get", "test");
            metric.unique(new long[]{1});
            Thread.sleep(100);
        }
    }
}