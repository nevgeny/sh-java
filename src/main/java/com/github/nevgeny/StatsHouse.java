package com.github.nevgeny;

import java.net.SocketException;
import java.net.UnknownHostException;

public class StatsHouse {

    final Transport transport;
    private final String[] defaultTags = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};

    public StatsHouse(String shAddr, int shPort, String env) throws SocketException, UnknownHostException {
        transport = new Transport(shAddr, shPort, env);
    }

    StatsHouse(String env) {
        transport = new Transport(env);
    }


    public class Metric {
        final String name;
        final String[] tagsNames;
        boolean hasEnv;

        public Metric(String name, String... tagsNames) {
            this.name = name;
            if (tagsNames.length > 0) {
                for (int i = 0; i < tagsNames.length; i++) {
                    if (!nonEmpty(tagsNames[i])) {
                        throw new IllegalArgumentException("tag mustn't be null or empty string");
                    }
                    if ("0".equals(tagsNames[i])) {
                        hasEnv = true;
                    }
                }
                this.tagsNames = tagsNames;
            } else {
                this.tagsNames = defaultTags;
            }
        }

        public void count(double count, String... tagsValues) {
            StatsHouse.this.transport.writeCount(this, tagsValues, "", count, 0);
        }

        public void value(double[] value, String... tagsValues) {
            StatsHouse.this.transport.writeValue(this, tagsValues, "", value, 0);
        }

        public void stringTop(String str, String... tagsValues) {
            if (nonEmpty(str)) {
                StatsHouse.this.transport.writeCount(this, tagsValues, str, 1, 0);
            }
        }

        public void unique(long[] value, String... tagsValues) {
            StatsHouse.this.transport.writeUnique(this, tagsValues, "", value, 0);
        }

        boolean defaultKeys() {
            return tagsNames == defaultTags;
        }
    }

    static boolean nonEmpty(String k) {
        return k != null && !"".equals(k);
    }
}
