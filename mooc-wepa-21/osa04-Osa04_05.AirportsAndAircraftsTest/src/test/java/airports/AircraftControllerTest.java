package airports;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class AircraftControllerTest {

    @Autowired
    private AircraftRepository aircraftRepository;
  
    @Autowired
    private MockMvc mockMvc;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    // At the end of the test, delete everything from database.
    @After
    public void tearDown() throws Exception {
        aircraftRepository.deleteAll();
    }

    @Test
    public void testStatusIsOKAndModelParametersExist() throws Exception {
        mockMvc.perform(get("/aircrafts"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("aircrafts"))
                .andExpect(model().attributeExists("airports"));
    }

    @Test
    public void testNewAircraftIsFoundInDatabaseAfterPost() throws Exception {
        // Ensure that database is empty
        aircraftRepository.deleteAll();

        // Add an aircraft through POST method
        mockMvc.perform(post("/aircrafts").param("name", "HA-LOL"))
                .andExpect(status().is3xxRedirection());

        // Fetch all aircrafts from database. Should be only 1.
        List<Aircraft> aircrafts = aircraftRepository.findAll();
        // Test that only one aircraft does exist.
        assertEquals(1, aircrafts.size());
        // Test that the aircraft is the newly added.
        aircrafts.get(0).getName().equals("HA-LOL");
    }

    @Test
    public void testNewAircraftIsFoundInDatabaseAfterPostAndGet() throws Exception {
        List<Aircraft> aircrafts;

        // Ensure that database is empty
        aircraftRepository.deleteAll();

        // Add an aircraft through POST method
        mockMvc.perform(post("/aircrafts").param("name", "XP-55"))
                .andExpect(status().is3xxRedirection());

        // Fetch aircrafts through GET method. Should be only one.
        MvcResult result = mockMvc.perform(get("/aircrafts")).andReturn();

        aircrafts = (List) result.getModelAndView().getModel().get("aircrafts");

        // Test that only one aircraft does exist.
        assertEquals(1, aircrafts.size());

        // Test that the aircraft is the newly added.
        assertEquals(aircrafts.get(0).getName(), "XP-55");
    }

}
