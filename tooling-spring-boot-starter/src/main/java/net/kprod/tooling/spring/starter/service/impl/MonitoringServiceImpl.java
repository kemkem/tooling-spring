package net.kprod.tooling.spring.starter.service.impl;

import net.kprod.tooling.spring.starter.service.MonitoringService;
import org.apache.juli.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.UUID;

/** {@inheritDoc} */
@Service
public class MonitoringServiceImpl implements MonitoringService {
    public static final String MONITORING_PROCESS_NAME = "_processName";
    public static final String MONITORING_PROCESS_ID = "_processId";
    public static final String MONITORING_PROCESS_ID_SHORT = "_processIdShort";
    private Logger LOG = LoggerFactory.getLogger(MonitoringService.class);

    protected final static int LOG_DATA_ID_LENGTH = 36;
    protected final static int LOG_DATA_ID_SHORT_LENGTH = 12;
    protected final static int LOG_DATA_ID_SHORT_DIFF = LOG_DATA_ID_LENGTH - LOG_DATA_ID_SHORT_LENGTH;

    @Override
    public String start(String processName) {
        String processId = UUID.randomUUID().toString();
        MDC.clear();

        MDC.put(MONITORING_PROCESS_NAME, processName);
        MDC.put(MONITORING_PROCESS_ID, processId);
        MDC.put(MONITORING_PROCESS_ID_SHORT, this.getShortLogDataId(processId));

        LOG.info("Start monitoring processName [{}] id [{}]",
                processName,
                processId);

        return processId;
    }

    @Override
    public void end() {
        LOG.info("End monitoring processName [{}] id [{}]",
                this.getProcessName(),
                this.getProcessId());
    }

    private String getProcessId() {
        return MDC.get(MONITORING_PROCESS_ID);
    }

    private String getProcessName() {
        return MDC.get(MONITORING_PROCESS_NAME);
    }

    private String getShortLogDataId(String logDataId) {
        if(logDataId == null || logDataId.length() < LOG_DATA_ID_SHORT_DIFF) {
            return logDataId;
        }
        return logDataId.substring(LOG_DATA_ID_SHORT_DIFF);
    }
}
