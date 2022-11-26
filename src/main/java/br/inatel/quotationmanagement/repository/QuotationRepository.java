package br.inatel.quotationmanagement.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.inatel.quotationmanagement.model.Quotation;

public interface QuotationRepository extends JpaRepository<Quotation, UUID> {

    Optional<List<Quotation>> findAllByStockId(String stockId);

}
