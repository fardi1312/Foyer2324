package tn.esprit.spring.BlocChambreTests.Chambre;


import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.TypeChambre;
import tn.esprit.spring.DAO.Repositories.ChambreRepository;
import tn.esprit.spring.Services.Chambre.ChambreService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChambreServiceTest {

    @Mock
    private ChambreRepository chambreRepository;

    @InjectMocks
    private ChambreService chambreService;

    private Chambre chambre;

    @BeforeEach
    void setUp() {
        chambre = new Chambre(1L, 101L, TypeChambre.SIMPLE, null, null);
    }

    @Test
    void testAddOrUpdateChambre() {
        // Given
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre);

        // When
        Chambre result = chambreService.addOrUpdate(chambre);

        // Then
        assertNotNull(result);
        assertEquals(chambre.getIdChambre(), result.getIdChambre());
        verify(chambreRepository, times(1)).save(chambre);
    }

    @Test
    void testFindAll() {
        // Given
        when(chambreRepository.findAll()).thenReturn(Arrays.asList(chambre));

        // When
        List<Chambre> result = chambreService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(chambre, result.get(0));
    }

    @Test
    void testFindById() {
        // Given
        when(chambreRepository.findById(1L)).thenReturn(Optional.of(chambre));

        // When
        Chambre result = chambreService.findById(1L);

        // Then
        assertNotNull(result);
        assertEquals(chambre.getIdChambre(), result.getIdChambre());
        verify(chambreRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        // Given
        when(chambreRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            chambreService.findById(1L);
        });

        assertEquals("Chambre with id 1 not found", thrown.getMessage());
    }

    @Test
    void testDeleteById() {
        // Given
        doNothing().when(chambreRepository).deleteById(1L);

        // When
        chambreService.deleteById(1L);

        // Then
        verify(chambreRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetChambresParNomBloc() {
        // Given
        when(chambreRepository.findByBlocNomBloc("Bloc A")).thenReturn(Arrays.asList(chambre));

        // When
        List<Chambre> result = chambreService.getChambresParNomBloc("Bloc A");

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(chambreRepository, times(1)).findByBlocNomBloc("Bloc A");
    }

    @Test
    void testNbChambreParTypeEtBloc() {
        // Given
        when(chambreRepository.countByTypeCAndBlocIdBloc(TypeChambre.SIMPLE, 1L)).thenReturn(5);

        // When
        long result = chambreService.nbChambreParTypeEtBloc(TypeChambre.SIMPLE, 1L);

        // Then
        assertEquals(5, result);
        verify(chambreRepository, times(1)).countByTypeCAndBlocIdBloc(TypeChambre.SIMPLE, 1L);
    }
}
