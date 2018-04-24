package com.killprojects.migrator.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class User implements Serializable {
    String name;
    Integer externalId;
}
