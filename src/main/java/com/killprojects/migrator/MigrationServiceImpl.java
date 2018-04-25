package com.killprojects.migrator;

import com.killprojects.migrator.job.JobService;
import com.killprojects.migrator.dto.OnlineStatistics;
import org.springframework.stereotype.Service;

@Service
public final class MigrationServiceImpl implements MigrationService {

    private final JobService jobService;

    public MigrationServiceImpl(JobService jobService) {
        this.jobService = jobService;
    }

    @Override
    public void startMigrate() {

    }

    @Override
    public OnlineStatistics getOnlineStatistics() {
        return null;
    }

    @Override
    public void resend() {

    }

    @Override
    public void finishMigrate() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }
}
