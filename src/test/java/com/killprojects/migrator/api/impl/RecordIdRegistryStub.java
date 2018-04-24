package com.killprojects.migrator.api.impl;

import com.killprojects.migrator.api.RecordIdRegistry;
import com.killprojects.migrator.dto.User;
import org.springframework.stereotype.Component;

@Component
public class RecordIdRegistryStub implements RecordIdRegistry<User> {
    @Override
    public String defineRecordID(User recordObject) {
        return String.valueOf(recordObject.getExternalId());
    }
}
