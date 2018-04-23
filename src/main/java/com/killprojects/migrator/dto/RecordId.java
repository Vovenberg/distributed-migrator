package com.killprojects.migrator.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class RecordId implements Serializable {

    private String businessId;
    private Long recordIndex;

    private RecordId(String businessId, Long recordIndex) {
        this.businessId = businessId;
        this.recordIndex = recordIndex;
    }

    public static RecordId is(String businessId, Long recordIndex) {
        return new RecordId(businessId, recordIndex);
    }
}
