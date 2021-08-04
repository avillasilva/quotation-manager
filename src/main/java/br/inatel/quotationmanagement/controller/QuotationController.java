package br.inatel.quotationmanagement.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

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
	public List<QuotationDto> list() {
		List<Quotation> quotations = quotationRepository.findAll();
		return QuotationDto.convert(quotations);
	}
	
	@GetMapping("/{stockId}")
	public ResponseEntity<List<QuotationDto>> getByStockId(@PathVariable String stockId) {  
		Optional<List<Quotation>> optional = quotationRepository.findAllByStockId(stockId);
		return ResponseEntity.ok(QuotationDto.convert(optional.get()));
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "quotationList", allEntries = true)
	public ResponseEntity<QuotationDto> create(@RequestBody QuotationForm form, UriComponentsBuilder uriBuilder) {
		if (stockService.getStock(form.getStockId()) == null) {
			return ResponseEntity.notFound().build();
		}
		
		if (form.getQuotes().size() == 0) {
			return ResponseEntity.badRequest().build();
		}
		
		Quotation quotation = form.convert(quoteRepository);
		quotationRepository.save(quotation);
		URI uri = uriBuilder.path("/quotations/{stockId}").buildAndExpand(quotation.getStockId()).toUri();
		return ResponseEntity.created(uri).body(new QuotationDto(quotation));
	}
	
//	private boolean stockIsRegistered(String stockId) {
//		Stock[] stocks = stockService.getAllStocks();
//		for (Stock stock : stocks) {
//			if (stock.getId().equals(stockId)) {
//				return true;
//			}
//		}
//		
//		return false;
//	}
}
