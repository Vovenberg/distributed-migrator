package com.killprojects.migrator.api;

public interface RecordConverter<T> {

    T convert(String recordText);
}
