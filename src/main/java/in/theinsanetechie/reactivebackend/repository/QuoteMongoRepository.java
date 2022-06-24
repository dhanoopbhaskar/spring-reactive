package in.theinsanetechie.reactivebackend.repository;

import in.theinsanetechie.reactivebackend.domain.Quote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface QuoteMongoRepository extends PagingAndSortingRepository<Quote, String> {
    List<Quote> findAllByIdNotNullOrderByIdAsc(final Pageable page);
}
