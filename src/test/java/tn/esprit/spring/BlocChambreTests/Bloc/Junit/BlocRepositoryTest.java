package tn.esprit.spring.BlocChambreTests.Bloc.Junit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Repositories.BlocRepository;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")  // This uses application-test.properties
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)  // Reset context after each test
 class BlocRepositoryTest {

    @Autowired
    private BlocRepository blocRepository;

    private Bloc testBloc;

    @BeforeEach
     void setUp() {
        testBloc = Bloc.builder()
                .nomBloc("TestBloc")
                .capaciteBloc(100)
                .build();
        blocRepository.save(testBloc);  // Save a test Bloc entity for testing
        System.out.println("Test setup complete with Bloc: " + testBloc.getNomBloc());
    }

    @Test
     void testSaveBloc() {
        Bloc newBloc = Bloc.builder()
                .nomBloc("NewBloc")
                .capaciteBloc(200)
                .build();
        Bloc savedBloc = blocRepository.save(newBloc);

        System.out.println("Saved Bloc: " + savedBloc.getNomBloc() + ", Capacity: " + savedBloc.getCapaciteBloc());

        assertNotNull(savedBloc);
        assertEquals("NewBloc", savedBloc.getNomBloc());
        assertEquals(200, savedBloc.getCapaciteBloc());
        assertTrue(blocRepository.existsById(savedBloc.getIdBloc()));
    }

    @Test
     void testFindById() {
        Bloc foundBloc = blocRepository.findById(testBloc.getIdBloc()).orElse(null);

        System.out.println("Found Bloc by ID: " + (foundBloc != null ? foundBloc.getNomBloc() : "not found"));

        assertNotNull(foundBloc);
        assertEquals("TestBloc", foundBloc.getNomBloc());
    }


/*
    @Test
     void testUpdateBloc() {
        testBloc.setCapaciteBloc(150);
        Bloc updatedBloc = blocRepository.save(testBloc);

        System.out.println("Updated Bloc: " + updatedBloc.getNomBloc() + ", New Capacity: " + updatedBloc.getCapaciteBloc());

        assertNotNull(updatedBloc);
        assertEquals(150, updatedBloc.getCapaciteBloc());
    }*/

    @Test
     void testDeleteBloc() {
        blocRepository.deleteById(testBloc.getIdBloc());

        boolean exists = blocRepository.existsById(testBloc.getIdBloc());
        System.out.println("Bloc deleted: " + !exists);

        assertFalse(exists, "The bloc should be deleted.");
    }

    @Test
     void testFindAllBlocs() {
        var blocs = blocRepository.findAll();
        System.out.println("Total Blocs in the Database: " + blocs.size());

        assertNotNull(blocs);
        assertTrue(blocs.size() > 0, "Expected at least one bloc in the database.");
    }
}
