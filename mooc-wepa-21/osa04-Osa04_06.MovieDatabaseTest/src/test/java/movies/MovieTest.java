package movies;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieTest extends org.fluentlenium.adapter.junit.FluentTest {

    @Autowired
    private MovieRepository movieRepository;

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
        movieRepository.deleteAll();
    }

    @After
    public void tearDown() throws Exception {
        movieRepository.deleteAll();
    }

    @Test
    public void canAddMovieAndActor() {
        // Goto page 'movies'.
        goTo("http://localhost:" + port + "/movies");
        // Check that page does not contain text "Uuno Epsanjassa".
        assertFalse(pageSource().contains("Uuno Epsanjassa"));
        // Check that page does not contain text "Uuno Turhapuro".
        assertFalse(pageSource().contains("Uuno Turhapuro"));
        // Search for a field with id "name" and insert value "Uuno Epsanjassa".
        find("#name").fill().with("Uuno Epsanjassa");
        // Search for a field with id "lengthInMinutes" and insert value "92".
        find("#lengthInMinutes").fill().with("92");
        // Submit the form
        find("form").first().submit();
        // Check that page contains text "Uuno Epsanjassa".
        assertTrue(pageSource().contains("Uuno Epsanjassa"));
        // Check that page does not contain text "Uuno Turhapuro".
        assertFalse(pageSource().contains("Uuno Turhapuro"));

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
        // Search for a link with text "Uuno Turhapuro" and click it.
        find(new By.ByXPath("//a[text()='Uuno Turhapuro']")).click();
        // Search for a button with id "add-to-movie" and click it.
        find("#add-to-movie").click();

        // Goto page 'movies'.
        goTo("http://localhost:" + port + "/movies");
        // Check that page contains text "Uuno Epsanjassa".
        assertTrue(pageSource().contains("Uuno Epsanjassa"));
        // Check that page contains text "Uuno Turhapuro".
        assertTrue(pageSource().contains("Uuno Turhapuro"));
    }
}
