package movies;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ActorTest extends org.fluentlenium.adapter.junit.FluentTest {

    @Autowired
    private ActorRepository actorRepository;

    @LocalServerPort
    private Integer port;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        actorRepository.deleteAll();
    }

    @After
    public void tearDown() throws Exception {
        actorRepository.deleteAll();
    }

    @Test
    public void canAddAndDeleteActor() {
        // Goto page 'actors'.
        goTo("http://localhost:" + port + "/actors");
        // Check that page does not contain text "Uuno Turhapuro".
        assertFalse(pageSource().contains("Uuno Turhapuro"));
        // Search for a field with id "name" and insert value "Uuno Turhapuro".
        find("#name").fill().with("Uuno Turhapuro");
        // Submit the form
        find("form").first().submit();
        // Check that page contains text "Uuno Turhapuro".
        assertTrue(pageSource().contains("Uuno Turhapuro"));
        // Click delete button referring to "Uuno Turhapuro"
        find("form").get(1).submit();
        // Check that page does not contain text "Uuno Turhapuro".
        assertFalse(pageSource().contains("Uuno Turhapuro"));
    }
}
