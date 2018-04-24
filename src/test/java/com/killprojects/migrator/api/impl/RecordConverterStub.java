package com.killprojects.migrator.api.impl;

import com.killprojects.migrator.api.RecordConverter;
import com.killprojects.migrator.dto.User;
import org.springframework.stereotype.Component;

@Component
public class RecordConverterStub implements RecordConverter<User> {

    @Override
    public User convert(String recordText) {
        String[] values = recordText.split(" ");
        return new User(values[0], Integer.valueOf(values[1]));
    }
}
