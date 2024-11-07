package tn.esprit.spring.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "T_BLOC")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Bloc implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idBloc;

    private String nomBloc;

    private long capaciteBloc;
    public Bloc(String nomBloc) {
        this.nomBloc = nomBloc;
    }
    @ManyToOne
    private Foyer foyer;

    @OneToMany(mappedBy = "bloc", fetch = FetchType.EAGER)
    private List<Chambre> chambres = new ArrayList<>(); // No 'transient' here, initialized to avoid nulls

    @Override
    public String toString() {
        return "Bloc{" +
                "idBloc=" + idBloc +
                ", nomBloc='" + nomBloc + '\'' +
                ", capaciteBloc=" + capaciteBloc +
                ", foyer=" + (foyer != null ? foyer.getNomFoyer() : "No Foyer") +
                '}';
    }
}
