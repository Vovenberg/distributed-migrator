package com.killprojects.migrator.api.impl;

import com.killprojects.migrator.api.RecordTransfer;
import com.killprojects.migrator.dto.TransferResult;
import com.killprojects.migrator.dto.User;
import org.springframework.stereotype.Component;

@Component
public class RecordTransferStub implements RecordTransfer<User> {
    @Override
    public TransferResult transfer(User businessEntity) {
        return new TransferResult();
    }
}
