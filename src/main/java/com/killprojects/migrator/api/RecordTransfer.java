package com.killprojects.migrator.api;

import com.killprojects.migrator.dto.TransferResult;

import java.io.Serializable;

public interface RecordTransfer<T> extends Serializable {

    TransferResult transfer(T businessEntity);
}
