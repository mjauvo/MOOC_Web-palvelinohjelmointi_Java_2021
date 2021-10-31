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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AirportServiceTest {

    @Autowired
    private AirportService airportService;

    @Autowired
    private AirportRepository airportRepository;

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
        airportRepository.deleteAll();
    }

    @Test
    public void testCreatedAirportIsFoundInDatabase() {
        // Ensure that database is empty
        airportRepository.deleteAll();

        // Add an airport. Test if the amount has increased by 1.
        airportService.create("test123", "TestAirport1");
            assertEquals(1, airportRepository.count());

        // Fetch the added airport. Test the parameters.
        Airport testAirport = airportRepository.findAll().get(0);
            assertEquals("test123", testAirport.getIdentifier());
            assertEquals("TestAirport1", testAirport.getName());
    }

    @Test
    public void testAllAirportsAreReturnedFromDatabase() {
        List<Airport> testAirports;
        int testCount = 0;

        // Ensure that database is empty
        airportRepository.deleteAll();

        // Add a few airports.
        airportService.create("test123", "TestAirport1"); testCount++;
        airportService.create("test234", "TestAirport2"); testCount++;
        airportService.create("test345", "TestAirport3"); testCount++;
        airportService.create("test456", "TestAirport4"); testCount++;

        // Fetch all airports from database. Test the amount.
        testAirports = airportService.list();
        assertEquals(testCount, testAirports.size());
    }

    @Test
    public void testCreatesNoDuplicatesInDatabase() {

        // Ensure that database is empty
        airportRepository.deleteAll();

        // Add airports. Test that airports with
        // same name do not increase the amount.
        System.out.println("CHECKING FOR DUPLICATES");
        airportService.create("test123", "TestAirport1");
            assertEquals(1, airportRepository.count());
        airportService.create("test234", "TestAirport1");
            assertEquals(1, airportRepository.count());
        airportService.create("test234", "TestAirport2");
            assertEquals(2, airportRepository.count());
        airportService.create("test123", "TestAirport1");
            assertEquals(2, airportRepository.count());
    }
}
