package com.killprojects.migrator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class EntityContainer<T> implements Serializable {

    private Long index;
    private String businessId;

    private T object;
    private TransferResult transferResult;


}
