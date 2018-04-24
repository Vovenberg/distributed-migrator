package com.killprojects.migrator.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EntityContainer<T> implements Serializable {

    private String index;
    private String businessId;

    private String line;
    private T object;
    private TransferResult transferResult;

    public EntityContainer() {
    }

    public EntityContainer(String index, String businessId, T object) {
        this.index = index;
        this.businessId = businessId;
        this.object = object;
    }

    public EntityContainer(String index, String line) {
        this.index = index;
        this.line = line;
    }
}
