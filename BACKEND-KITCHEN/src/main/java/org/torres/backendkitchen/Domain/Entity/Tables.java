    package org.torres.backendkitchen.Domain.Entity;


    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    import org.torres.backendkitchen.Domain.Enum.TableState;

    import java.util.List;
    import java.util.UUID;

    @Entity
    @Data
    @Table(name="tables")
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class Tables {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private UUID id;

        @Column(nullable = false, unique = true)
        private Integer number; // Table number

        @Enumerated(EnumType.ORDINAL)
        @Column(nullable = false)
        private TableState state;

    }
