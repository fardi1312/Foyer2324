package tn.esprit.spring.BlocChambreTests.Bloc.Mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Repositories.BlocRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

 class BlocRepositoryTest {

    @Mock
    private BlocRepository blocRepository;

    private Bloc testBloc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initializes Mockito annotations

        testBloc = Bloc.builder()
                .nomBloc("TestBloc")
                .capaciteBloc(100)
                .build();
    }

    @Test
     void testSaveBloc() {
        when(blocRepository.save(testBloc)).thenReturn(testBloc);

        Bloc savedBloc = blocRepository.save(testBloc);

        System.out.println("Saved Bloc: " + savedBloc.getNomBloc() + ", Capacity: " + savedBloc.getCapaciteBloc());

        assertNotNull(savedBloc);
        assertEquals("TestBloc", savedBloc.getNomBloc());
        assertEquals(100, savedBloc.getCapaciteBloc());

        verify(blocRepository, times(1)).save(testBloc);
    }

    @Test
     void testFindById() {
        when(blocRepository.findById(testBloc.getIdBloc())).thenReturn(Optional.of(testBloc));

        Bloc foundBloc = blocRepository.findById(testBloc.getIdBloc()).orElse(null);

        System.out.println("Found Bloc by ID: " + (foundBloc != null ? foundBloc.getNomBloc() : "not found"));

        assertNotNull(foundBloc);
        assertEquals("TestBloc", foundBloc.getNomBloc());

        verify(blocRepository, times(1)).findById(testBloc.getIdBloc());
    }

    @Test
     void testUpdateBloc() {
        testBloc.setCapaciteBloc(150);
        when(blocRepository.save(testBloc)).thenReturn(testBloc);

        Bloc updatedBloc = blocRepository.save(testBloc);

        System.out.println("Updated Bloc: " + updatedBloc.getNomBloc() + ", New Capacity: " + updatedBloc.getCapaciteBloc());

        assertNotNull(updatedBloc);
        assertEquals(150, updatedBloc.getCapaciteBloc());

        verify(blocRepository, times(1)).save(testBloc);
    }

    @Test
     void testDeleteBloc() {
        doNothing().when(blocRepository).deleteById(testBloc.getIdBloc());

        blocRepository.deleteById(testBloc.getIdBloc());

        System.out.println("Bloc deleted.");

        verify(blocRepository, times(1)).deleteById(testBloc.getIdBloc());
    }

    @Test
     void testFindAllBlocs() {
        List<Bloc> blocs = Arrays.asList(testBloc, Bloc.builder().nomBloc("AnotherBloc").capaciteBloc(200).build());
        when(blocRepository.findAll()).thenReturn(blocs);

        List<Bloc> foundBlocs = blocRepository.findAll();

        System.out.println("Total Blocs in the Database: " + foundBlocs.size());

        assertNotNull(foundBlocs);
        assertEquals(2, foundBlocs.size());

        verify(blocRepository, times(1)).findAll();
    }
}
