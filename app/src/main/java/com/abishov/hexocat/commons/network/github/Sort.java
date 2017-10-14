package com.abishov.hexocat.commons.network.github;

public enum Sort {
    STARS("watchers"),
    FORKS("forks"),
    UPDATED("updated");

    private final String value;

    Sort(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
