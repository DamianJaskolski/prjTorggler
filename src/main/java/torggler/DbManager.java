package torggler;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import torggler.tables.TabUsers;
import torggler.tables.TabWetBase;
import torggler.tables.TabWetProduct;
import torggler.tables.TabWetReport;

import java.io.IOException;
import java.sql.SQLException;

public class DbManager {

        // private static final Logger LOGGER = LoggerFactory.getLogger(DbManager.class);

        public static final String URL = "jdbc:postgresql://127.0.0.1:5432/dbTorggler";
        private static final String USER = "postgres";
        private static final String PASSWORD = "dagdam";

        private static ConnectionSource connectionSource;

        public void initDatabase() {
            createConnectionSource();
            // dropTable(); //zakomentuj, żeby nie kasować za każym razem tabel w bazie
            createTable();
            closeConnectionSource();
        }

        private static void createConnectionSource() {

            try {
                connectionSource = new JdbcConnectionSource(URL, USER, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        public static ConnectionSource getConnectionSource() {
            if (connectionSource == null) {
                createConnectionSource();
            }
            return connectionSource;
        }

        public void closeConnectionSource() {
            if (connectionSource != null) {

                try {
                    connectionSource.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void createTable() {

            try {
               // TableUtils.createTableIfNotExists(connectionSource, TabUsers.class);
               // TableUtils.createTableIfNotExists(connectionSource, TabWetBase.class);
               // TableUtils.createTableIfNotExists(connectionSource, TabWetProduct.class);
                TableUtils.createTableIfNotExists(connectionSource, TabWetReport.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

        private void dropTable() {

            try {
                TableUtils.dropTable(connectionSource, TabUsers.class, true);
                TableUtils.dropTable(connectionSource, TabWetBase.class, true);
                TableUtils.dropTable(connectionSource, TabWetProduct.class,true);
                TableUtils.dropTable(connectionSource, TabWetReport.class,true);
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }
        private void refer(){

        }
    }




