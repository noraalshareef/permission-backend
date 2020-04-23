package backend.workers;

import backend.db.Broker;
import backend.db.ConnectionManager;

import javax.ejb.*;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Future;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class Handler {

    @Asynchronous
    public Future<?> handle(List<Long> all) {
        for (long id : all) {
            Connection connection = null;
            try {
                connection = ConnectionManager.getFullStopConnection();

                Broker.setPermitted(connection, id);

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NamingException e) {
                e.printStackTrace();
            } finally {
                ConnectionManager.close(connection);
            }


        }

        return new AsyncResult<Object>(null);
    }
}