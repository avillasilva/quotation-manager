package br.inatel.quotationmanagement.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.inatel.quotationmanagement.controller.dto.QuotationDto;
import br.inatel.quotationmanagement.controller.form.QuotationForm;
import br.inatel.quotationmanagement.exception.StockNotFoundException;
import br.inatel.quotationmanagement.model.Quotation;
import br.inatel.quotationmanagement.repository.QuotationRepository;
import br.inatel.quotationmanagement.repository.QuoteRepository;
import br.inatel.quotationmanagement.service.StockService;

@RestController
@RequestMapping("/quotations")
public class QuotationController {

    private StockService stockService;
    private QuotationRepository quotationRepository;
    private QuoteRepository quoteRepository;

    public QuotationController(StockService stockService, QuotationRepository quotationRepository, QuoteRepository quoteRepository) {
        this.stockService = stockService;
        this.quotationRepository = quotationRepository;
        this.quoteRepository = quoteRepository;
    }

    @GetMapping
    @Cacheable(value = "quotationList")
    public ResponseEntity<?> list() {
        List<Quotation> quotations = quotationRepository.findAll();
        return ResponseEntity.ok(QuotationDto.convert(quotations));
    }

    @GetMapping("/{stockId}")
    public ResponseEntity<?> getByStockId(@PathVariable String stockId) {
        Optional<List<Quotation>> optional = quotationRepository.findAllByStockId(stockId);
        return ResponseEntity.ok(QuotationDto.convert(optional.get()));
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = "quotationList", allEntries = true)
    public ResponseEntity<?> create(@RequestBody @Valid QuotationForm form, UriComponentsBuilder uriBuilder) throws StockNotFoundException {
        stockService.validate(form.getStockId());
        Quotation quotation = form.convert(quoteRepository);
        quotationRepository.save(quotation);
        URI uri = uriBuilder.path("/quotations/{stockId}").buildAndExpand(quotation.getStockId()).toUri();
        return ResponseEntity.created(uri).body(new QuotationDto(quotation));
    }

}
