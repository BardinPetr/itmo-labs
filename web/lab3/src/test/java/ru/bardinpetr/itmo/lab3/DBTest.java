//package ru.bardinpetr.itmo.lab3;
//
//import jakarta.inject.Inject;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import lombok.extern.slf4j.Slf4j;
//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.junit5.ArquillianExtension;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.gradle.archive.importer.embedded.EmbeddedGradleImporter;
//import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
//import org.jboss.shrinkwrap.api.spec.WebArchive;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import ru.bardinpetr.itmo.lab3.data.dao.impl.UserDAO;
//
//@Slf4j
//@ExtendWith(ArquillianExtension.class)
//public class DBTest {
//
//    @PersistenceContext
//    EntityManager em;
//
//    @Inject
//    UserDAO userDAO;
//
//    @Deployment
//    public static EnterpriseArchive createDeployment() {
////        var war = ShrinkWrap.create(WebArchive.class, "app.war")
////                .addPackage(App.class.getPackage())
////                .addAsResource("persistence.xml", "META-INF/persistence.xml")
////                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
////                .addAsLibraries();
////        ShrinkWrap.create(EmbeddedGradleImporter.class).forThisProjectDirectory().
//        var war = ShrinkWrap
//                .create(EmbeddedGradleImporter.class)
//                .forThisProjectDirectory()
//                .importBuildOutput()
//                .as(WebArchive.class);
////        WebArchive war = ShrinkWrap.create(WebArchive.class) //
////                .addClasses(DemoA.class, DemoB.class) //
////                .addClasses(DBTest.class);
//
//        var ear = ShrinkWrap.create(EnterpriseArchive.class) //
//                .addAsModule(war);
//        return ear;
//    }
//
//    @BeforeAll
//    static void beforeAll() {
//    }
//
//    @AfterAll
//    static void afterAll() {
//    }
//
//    @BeforeEach
//    void setUp() {
//
//    }
//
//    @Test
//    void shouldCreateUsers() {
//        log.info("em {}", em);
//        log.info("u: {}", userDAO);
//        log.info("u: {}", userDAO.findAll());
////        var user = new User();
////        user.setLogin("user1");
////        user.setPasswordHash("pass1");
////        userDAO.insert(user);
////
////        var q = em.createQuery("select u from app_user u where u.login = :login");
////        q.setParameter("login", user.getLogin());
////
////        var users = q.getResultList();
////        assertEquals(users.size(), 1);
////        var dbUser = (User) users.get(0);
////        assertEquals(dbUser.getLogin(), user.getLogin());
////        assertEquals(dbUser.getPasswordHash(), user.getPasswordHash());
//    }
//}
