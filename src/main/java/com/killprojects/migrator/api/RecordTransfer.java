package com.killprojects.migrator.api;

import com.killprojects.migrator.dto.TransferResult;

public interface RecordTransfer<T> {

    TransferResult transfer(T businessEntity);
}
