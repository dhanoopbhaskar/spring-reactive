package in.theinsanetechie.reactivebackend.controller;

import in.theinsanetechie.reactivebackend.domain.Quote;
import in.theinsanetechie.reactivebackend.repository.QuoteMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QuoteController {

    private static final int DELAY_PER_ITEM_MS = 100;
    private final QuoteMongoRepository quoteMongoRepository;

    @GetMapping("/quotes")
    public Iterable<Quote> getQuotesBlocking() throws Exception {
        Thread.sleep(DELAY_PER_ITEM_MS * quoteMongoRepository.count()); // to simulate delay
        return quoteMongoRepository.findAll();
    }

    @GetMapping("/quotes-paged")
    public Iterable<Quote> getQuotesBlocking(final @RequestParam(name = "page") int page,
                                             final @RequestParam(name = "size") int size) throws Exception {
        Thread.sleep(DELAY_PER_ITEM_MS * size); // to simulate delay
        return quoteMongoRepository.findAllByIdNotNullOrderByIdAsc(PageRequest.of(page, size));
    }
}
