package ru.bardinpetr.itmo.lab3.spi;

public class Constants {
    public static final int WILDFLY_HTTP_PORT = 8080;
    public static final int WILDFLY_MANAGEMENT_PORT = 9990;
    public static final String WILDFLY_PASS = "admin";
    public static final String WILDFLY_USER = "admin";

    public static final String DB_HOSTNAME = "db";
    public static final int DB_PORT = 5432;
    public static final String DB_USER = "admin";
    public static final String DB_PASS = "admin";
    public static final String DB_NAME = "test";
    public static final String DB_JNDI_DS = "java:/app/db/PostgresDS";


    public static final class ContainerConfiguration {

        /**
         * The IP address of the remote Wildfly server.
         */
        public static final String MANAGEMENT_ADDRESS_KEY = "managementAddress";

        /**
         * The management port of the remote Wildfly server (which is 9990 by default).
         */
        public static final String MANAGEMENT_PORT_KEY = "managementPort";

        /**
         * The management account user name of the remote Wildfly server.
         */
        public static final String USERNAME_KEY = "username";

        /**
         * The management account password of the remote Wildfly server.
         */
        public static final String PASSWORD_KEY = "password";

        /**
         * The protocol to use for the communication in the test.
         */
        public static final class ServletProtocolDefinition {
            /**
             * Arquillian's name for the Servlet protocol.
             */
            public static final String NAME = "Servlet 5.0";

            /**
             * The IP of the remote Wildfly server.
             */
            public static final String HOST_KEY = "host";

            /**
             * The IP of the remote Wildfly (which is 8080 by default.
             */
            public static final String PORT_KEY = "port";
        }
    }
}
