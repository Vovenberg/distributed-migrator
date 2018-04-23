package com.killprojects.migrator;

import com.killprojects.migrator.dto.OnlineStatistics;

public interface MigrationService {

    void startMigrate();

    OnlineStatistics getOnlineStatistics();

    void resend();

    void finishMigrate();

    //
    void pause();

    void stop();
}
