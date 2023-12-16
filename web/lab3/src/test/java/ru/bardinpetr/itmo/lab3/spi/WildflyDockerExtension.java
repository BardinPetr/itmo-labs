//package ru.bardinpetr.itmo.lab3.spi;
//
//import org.jboss.arquillian.container.spi.ContainerRegistry;
//import org.jboss.arquillian.container.spi.client.protocol.ProtocolDescription;
//import org.jboss.arquillian.core.api.annotation.Observes;
//import org.jboss.arquillian.core.spi.LoadableExtension;
//import org.jboss.arquillian.core.spi.ServiceLoader;
//import org.jboss.as.arquillian.container.Authentication;
//import org.jboss.as.controller.client.ModelControllerClient;
//import org.jboss.as.controller.client.ModelControllerClientConfiguration;
//import org.jboss.as.controller.client.OperationBuilder;
//import org.jboss.as.controller.client.helpers.ClientConstants;
//import org.jboss.dmr.ModelNode;
//import org.jboss.shrinkwrap.resolver.api.maven.Maven;
//import org.testcontainers.containers.GenericContainer;
//import org.testcontainers.containers.Network;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.containers.wait.strategy.Wait;
//import org.testcontainers.images.builder.ImageFromDockerfile;
//import org.wildfly.plugin.core.Deployment;
//import org.wildfly.plugin.core.DeploymentManager;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.time.Duration;
//
//import static ru.bardinpetr.itmo.lab3.spi.Constants.ContainerConfiguration.*;
//import static ru.bardinpetr.itmo.lab3.spi.Constants.*;
//
//public final class WildflyDockerExtension implements LoadableExtension {
//
//    @Override
//    public void register(ExtensionBuilder builder) {
//        builder.observer(LoadContainerConfiguration.class);
//    }
//
//    /**
//     * Helper class to register an Arquillian observer.
//     */
//    public static class LoadContainerConfiguration {
//        private static final String POSTGRESQL_JDBC_PACKAGE = "org.postgresql:postgresql:42.6.0";
//        private static final String WILDFLY_DOCKER_IMAGE = "quay.io/wildfly/wildfly:29.0.1.Final-jdk17";
//        private static final String DB_DOCKER_IMAGE = "postgres:15-alpine";
//
//        /**
//         * Method which observes {@link ContainerRegistry}. Gets called by Arquillian at startup time.
//         */
//        public void registerInstance(@Observes ContainerRegistry registry, ServiceLoader serviceLoader) throws IOException {
//            var network = Network.newNetwork();
//            var db = createDB(network);
//            var wf = createWildfly(network);
//
//            addDatasourceToWildflyContainer(wf, db);
//            configureArquillianForRemoteWildfly(wf, registry);
//        }
//
//        public PostgreSQLContainer<?> createDB(Network network) {
//            @SuppressWarnings("resource")
//            var db = new PostgreSQLContainer<>(DB_DOCKER_IMAGE)
//                    .withNetwork(network)
//                    .withNetworkAliases(DB_HOSTNAME)
//                    .withExposedPorts(DB_PORT)
//                    .withDatabaseName(DB_NAME)
//                    .withUsername(DB_USER)
//                    .withPassword(DB_PASS);
//            db.start();
//            return db;
//        }
//
//        public GenericContainer<?> createWildfly(Network network) {
//            @SuppressWarnings("resource")
//            var wf = new GenericContainer<>(
//                    new ImageFromDockerfile()
//                            .withDockerfileFromBuilder(builder ->
//                                    builder
//                                            .from(WILDFLY_DOCKER_IMAGE)
//                                            .user("jboss")
//                                            .run("/opt/jboss/wildfly/bin/add-user.sh " + WILDFLY_USER + " " + WILDFLY_PASS + " --silent")
//                                            .cmd("/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0")
//                                            .build()
//                            )
//            )
//                    .withNetwork(network)
//                    .withExposedPorts(WILDFLY_MANAGEMENT_PORT, WILDFLY_HTTP_PORT)
//                    .withStartupTimeout(Duration.ofMinutes(3))
//                    .waitingFor(Wait.forLogMessage(".*WFLYSRV0025.*", 1)); // "...wildfly started in..."
//            wf.start();
//            return wf;
//        }
//
//        private void addDatasourceToWildflyContainer(GenericContainer<?> wildflyContainer, PostgreSQLContainer<?> db) throws IOException {
//            Authentication.username = WILDFLY_USER;
//            Authentication.password = WILDFLY_PASS;
//
//            var clientConfig = new ModelControllerClientConfiguration.Builder()
//                    .setHostName(wildflyContainer.getHost())
//                    .setPort(wildflyContainer.getMappedPort(WILDFLY_MANAGEMENT_PORT))
//                    .setHandler(Authentication.getCallbackHandler())
//                    .build();
//
//            var client = ModelControllerClient.Factory.create(clientConfig);
//
//            var driverFilename = "postgres.jar";
//            var driver = Maven
//                    .resolver()
//                    .resolve(POSTGRESQL_JDBC_PACKAGE)
//                    .withoutTransitivity()
//                    .asSingleFile();
//
//            var deploymentManager = DeploymentManager.Factory.create(client);
//deploymentManager.forceDeploy(Deployment.of(new FileInputStream(driver), driverFilename));
//deploymentManager.forceDeploy(Deployment.of(Maven.resolver().resolve( "org.eclipse.persistence:org.eclipse.persistence.jpa:4.0.2").withoutTransitivity().asSingleFile()));
//deploymentManager.forceDeploy(Deployment.of(Maven.resolver().resolve( "org.eclipse.persistence:eclipselink:4.0.2").withoutTransitivity().asSingleFile()));
//
//            var request = new ModelNode();
//            request.get(ClientConstants.OP).set(ClientConstants.ADD);
//            request.get(ClientConstants.OP_ADDR).add("subsystem", "datasources");
//            request.get(ClientConstants.OP_ADDR).add("data-source", DB_JNDI_DS);
//            request.get("jndi-name").set(DB_JNDI_DS);
//            request.get("connection-url").set(db.getJdbcUrl().replace("localhost", DB_HOSTNAME));
//            request.get("driver-class").set("org.postgresql.Driver");
//            request.get("driver-name").set(driverFilename);
//            request.get("user-name").set(DB_USER);
//            request.get("password").set(DB_PASS);
//            request.get("pool-name").set("pool_ds");
//
//            client.execute(new OperationBuilder(request).build());
//        }
//
//        private void configureArquillianForRemoteWildfly(GenericContainer<?> paramWildflyContainer, ContainerRegistry registry) {
//            var wfManagementPort = String.valueOf(paramWildflyContainer.getMappedPort(WILDFLY_MANAGEMENT_PORT));
//            var wfPort = String.valueOf(paramWildflyContainer.getMappedPort(WILDFLY_HTTP_PORT));
//            var wfHost = paramWildflyContainer.getHost();
//
//            var container = registry
//                    .getContainers()
//                    .iterator()
//                    .next();
//
//            container.getContainerConfiguration()
//                    .property(MANAGEMENT_ADDRESS_KEY, wfHost)
//                    .property(MANAGEMENT_PORT_KEY, wfManagementPort)
//                    .property(USERNAME_KEY, WILDFLY_USER)
//                    .property(PASSWORD_KEY, WILDFLY_PASS);
//
//            var conf = container.getProtocolConfiguration(new ProtocolDescription(ServletProtocolDefinition.NAME));
//            conf.property(ServletProtocolDefinition.HOST_KEY, wfHost);
//            conf.property(ServletProtocolDefinition.PORT_KEY, wfPort);
//        }
//
//    }
//}