package backend.workers;


import backend.db.Broker;
import backend.db.ConnectionManager;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;



@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
@Startup
public class MainWorker {


    @Resource
    ManagedScheduledExecutorService scheduler;


    @EJB
    Handler handler;

    @PostConstruct
    public void init() {
        System.out.println( " **init** ");
        this.scheduler.schedule(this::start, 2, TimeUnit.SECONDS);
    }

    @PreDestroy
    public void cleanUp() {

    }

    public void start() {

        try {
                List<Long> allStatus2 = Broker.getAllNotPermittedAndReady(1000, ConnectionManager.getFullStopConnection());

                Future<?> f = handler.handle(allStatus2);
                f.get();
            System.out.println( "---------------MainWorker workrs !!!!!----------");

            this.scheduler.schedule(this::start, 20 , TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
