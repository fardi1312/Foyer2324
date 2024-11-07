package tn.esprit.spring.BlocChambreTests.Chambre.Junit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.TypeChambre;
import tn.esprit.spring.DAO.Repositories.ChambreRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
 class ChambreRepositoryTest {

    @Autowired
    private ChambreRepository chambreRepository;

    private Chambre chambre;

    @BeforeEach
    public void setUp() {
        // Set up a Bloc instance for the Chambre entity
        Bloc bloc = new Bloc();
        bloc.setNomBloc("Bloc A");

        // Create a Chambre object with the Bloc
        chambre = Chambre.builder()
                .numeroChambre(500)
                .typeC(TypeChambre.SIMPLE) // TypeChambre is assumed to be an enum with values like SIMPLE, DOUBLE, etc.
                .bloc(bloc)
                .build();
    }



    @Test
     void testReadChambre() {
        Chambre savedChambre = chambreRepository.save(chambre);  // First, create and save the chambre
        Chambre foundChambre = chambreRepository.findById(savedChambre.getIdChambre()).orElse(null);
        System.out.println("Found Chambre: " + foundChambre);  // Print the found chambre to console
        assertNotNull(foundChambre, "Chambre should be found");
        assertEquals(savedChambre.getNumeroChambre(), foundChambre.getNumeroChambre(), "Chambre numbers should match");
    }

    @Test
     void testUpdateChambre() {
        Chambre savedChambre = chambreRepository.save(chambre);  // Save a chambre
        savedChambre.setNumeroChambre(102);  // Update the chambre number
        Chambre updatedChambre = chambreRepository.save(savedChambre);
        System.out.println("Updated Chambre: " + updatedChambre);  // Print the updated chambre to console
        assertEquals(102, updatedChambre.getNumeroChambre(), "The chambre number should be updated to 102");
    }

    @Test
     void testDeleteChambre() {
        Chambre savedChambre = chambreRepository.save(chambre);  // Save a chambre
        long id = savedChambre.getIdChambre();
        chambreRepository.deleteById(id);  // Delete the chambre by id
        Chambre deletedChambre = chambreRepository.findById(id).orElse(null);
        System.out.println("Deleted Chambre: " + deletedChambre);  // Print the result after deletion (should be null)
        assertNull(deletedChambre, "Chambre should be null after deletion");
    }
}
