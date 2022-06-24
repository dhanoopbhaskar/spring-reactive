package in.theinsanetechie.reactivebackend.repository;

import in.theinsanetechie.reactivebackend.domain.Quote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;

public interface QuoteMongoReactiveRepository extends ReactiveSortingRepository<Quote, String> {
    Flux<Quote> findAllByIdNotNullOrderByIdAsc(final Pageable page);
}
