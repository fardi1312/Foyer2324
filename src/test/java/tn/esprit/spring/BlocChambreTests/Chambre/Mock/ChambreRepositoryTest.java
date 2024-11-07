package tn.esprit.spring.BlocChambreTests.Chambre.Mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.TypeChambre;
import tn.esprit.spring.DAO.Repositories.ChambreRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChambreRepositoryTest {

    @Mock
    private ChambreRepository chambreRepository;

    private Chambre chambre;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks

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
        when(chambreRepository.findById(chambre.getIdChambre())).thenReturn(Optional.of(chambre));

        Chambre foundChambre = chambreRepository.findById(chambre.getIdChambre()).orElse(null);

        System.out.println("Found Chambre: " + foundChambre);  // Print the found chambre to console

        assertNotNull(foundChambre, "Chambre should be found");
        assertEquals(chambre.getNumeroChambre(), foundChambre.getNumeroChambre(), "Chambre numbers should match");

        verify(chambreRepository, times(1)).findById(chambre.getIdChambre());
    }

    @Test
    void testUpdateChambre() {
        chambre.setNumeroChambre(102);  // Update the chambre number
        when(chambreRepository.save(chambre)).thenReturn(chambre);

        Chambre updatedChambre = chambreRepository.save(chambre);

        System.out.println("Updated Chambre: " + updatedChambre);  // Print the updated chambre to console

        assertEquals(102, updatedChambre.getNumeroChambre(), "The chambre number should be updated to 102");

        verify(chambreRepository, times(1)).save(chambre);
    }

    @Test
    void testDeleteChambre() {
        doNothing().when(chambreRepository).deleteById(chambre.getIdChambre());

        chambreRepository.deleteById(chambre.getIdChambre());

        System.out.println("Chambre deleted.");

        verify(chambreRepository, times(1)).deleteById(chambre.getIdChambre());
    }

    @Test
    void testFindAllChambres() {
        when(chambreRepository.findAll()).thenReturn(Arrays.asList(chambre, Chambre.builder().numeroChambre(501).typeC(TypeChambre.DOUBLE).bloc(new Bloc()).build()));

        var chambres = chambreRepository.findAll();

        System.out.println("Total Chambres in the Database: " + chambres.size());

        assertNotNull(chambres);
        assertEquals(2, chambres.size());

        verify(chambreRepository, times(1)).findAll();
    }
}
