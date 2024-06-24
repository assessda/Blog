package com.xc.myblog.common.lang;

import ch.qos.logback.core.boolex.EvaluationException;

import java.util.Map;

public class ValueEntry implements Map.Entry {
    private Object key;
    private Object value;


    public ValueEntry(Object key, Object value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public Object getKey() {
        return this.key;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public Object setValue(Object value) {
        this.value = value;
        return this;
    }
}
