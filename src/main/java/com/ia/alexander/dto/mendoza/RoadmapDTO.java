package com.ia.alexander.dto.mendoza;


import lombok.Data;

import java.util.List;

@Data
public class RoadmapDTO {
    private List<Etapa> etapas;

    @Data
    public static class Etapa {
        private String nombre;
        private List<Tarea> tareas;
    }

    @Data
    public static class Tarea {
        private String titulo;
        private String estado; // Pendiente | En progreso | Completado
    }
}