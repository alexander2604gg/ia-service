package com.ia.alexander.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lote_cultivo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoteCultivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int edadCultivoDias; // int64

    private double hectareas; // float64

    private double phSuelo; // float64

    private double materiaOrganica; // float64

    private double precipitacionMm; // float64

    private double temperaturaPromedioC; // float64

    private double riegosPorSemana; // float64

    private String zona; // object (ej. "Norte", "Sur", etc.)

    private String fertilizacionNpk; // object (ej. "Alta", "Media", "Baja")

    private boolean usoTratamientoFitosanitario; // bool

    private String eficiencia; // object (ej. "Alta", "Media", "Baja")
}