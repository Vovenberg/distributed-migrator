package com.killprojects.migrator.api;

import com.killprojects.migrator.dto.RecordId;

import java.io.Serializable;

public interface RecordIdRegistry<T> extends Serializable {

    String defineRecordID(T recordObject);
}
