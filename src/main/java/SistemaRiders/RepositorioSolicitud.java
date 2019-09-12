package SistemaRiders;

import org.springframework.data.jpa.repository.JpaRepository;

interface RepositorioSolicitud extends JpaRepository<Solicitud, Long> {
}