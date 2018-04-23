package com.killprojects.migrator.api.impl;

import com.killprojects.migrator.api.RecordConverter;
import com.killprojects.migrator.job.actions.User;
import org.springframework.stereotype.Component;

@Component
public class RecordConverterStub implements RecordConverter<User>{

    @Override
    public User convert(String recordText) {
        return null;
    }
}
