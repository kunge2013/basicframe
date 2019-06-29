package com.basic.core.service;

public interface ICache {
    public Object putData(Object key, Object value);
    public Object getData(Object key);
}
