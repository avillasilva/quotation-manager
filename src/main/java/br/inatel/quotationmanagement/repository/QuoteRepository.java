package br.inatel.quotationmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.inatel.quotationmanagement.model.Quote;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

	List<Quote> findAllByQuotationId(Long i);
}
