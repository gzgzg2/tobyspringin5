package springbook.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements ConnectionMaker {

    private int count = 0;
    private ConnectionMaker connectionMaker;

    public CountingConnectionMaker(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        this.count++;
        return connectionMaker.makeConnection();
    }

    public int getCount() {
        return this.count;
    }


}
