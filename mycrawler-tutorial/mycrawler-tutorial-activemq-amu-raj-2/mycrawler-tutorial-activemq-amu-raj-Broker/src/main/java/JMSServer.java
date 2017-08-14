/**
 * Created by AL on 2015-01-28.
 */
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.usage.SystemUsage;

import com.mycrawler.common.utils.LoggerUtils;

public class JMSServer {
    public static void main(String[] args) throws Exception {
    	LoggerUtils.init();
        BrokerService brokerService = new BrokerService();
        brokerService.addConnector("tcp://localhost:61616");
        SystemUsage systemUsage = brokerService.getSystemUsage();
        systemUsage.getMemoryUsage().setLimit(1024L * 1024 * 1024 * 1); // 1 GB
        systemUsage.getTempUsage().setLimit(1024L * 1024 * 1024 * 10); // 50 GB
        systemUsage.getStoreUsage().setLimit(1024L * 1024 * 1024 * 20); // 100 GB
        systemUsage.getJobSchedulerUsage().setLimit(1024L * 1024 * 1024 * 10); // 50 GB
        brokerService.start();
    }
}

