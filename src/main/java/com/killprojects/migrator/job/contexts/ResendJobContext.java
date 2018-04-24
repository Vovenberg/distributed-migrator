package com.killprojects.migrator.job.contexts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ResendJobContext {

    private String inputPath;
    private String outputPath;

}
