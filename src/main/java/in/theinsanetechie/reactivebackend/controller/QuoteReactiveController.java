package in.theinsanetechie.reactivebackend.controller;

import in.theinsanetechie.reactivebackend.domain.Quote;
import in.theinsanetechie.reactivebackend.repository.QuoteMongoReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
public class QuoteReactiveController {

    private static final int DELAY_PER_ITEM_MS = 100;

    private final QuoteMongoReactiveRepository quoteMongoReactiveRepository;

    @GetMapping("/quotes-reactive")
    public Flux<Quote> getQuoteFlux() {
        return quoteMongoReactiveRepository.findAll()
                .delayElements(Duration.ofMillis(DELAY_PER_ITEM_MS)); // to simulate delay
    }

    @GetMapping("/quotes-reactive-paged")
    public Flux<Quote> getQuoteFlux(final @RequestParam(name = "page") int page,
                                    final @RequestParam(name = "size") int size) {
        return quoteMongoReactiveRepository.findAllByIdNotNullOrderByIdAsc(PageRequest.of(page, size))
                .delayElements(Duration.ofMillis(DELAY_PER_ITEM_MS)); // to simulate delay
    }

}
