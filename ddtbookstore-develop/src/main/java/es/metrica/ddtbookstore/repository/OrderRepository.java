package es.metrica.ddtbookstore.repository;

import es.metrica.ddtbookstore.model.OrderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<OrderDTO, Long> {

}
