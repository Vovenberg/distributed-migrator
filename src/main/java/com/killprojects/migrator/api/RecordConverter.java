package com.killprojects.migrator.api;

import java.io.Serializable;

public interface RecordConverter<T> extends Serializable {

    T convert(String recordText);
}
