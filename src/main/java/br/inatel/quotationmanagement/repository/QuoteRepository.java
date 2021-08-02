package br.inatel.quotationmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.inatel.quotationmanagement.model.Quote;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

}
