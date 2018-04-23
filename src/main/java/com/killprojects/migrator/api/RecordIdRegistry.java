package com.killprojects.migrator.api;

import com.killprojects.migrator.dto.RecordId;

public interface RecordIdRegistry<T> {

    String defineRecordID(T recordObject);
}
