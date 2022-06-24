package in.theinsanetechie.reactivebackend.config;

import in.theinsanetechie.reactivebackend.domain.Quote;
import in.theinsanetechie.reactivebackend.repository.QuoteMongoReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.Supplier;

@Component
@Slf4j
@RequiredArgsConstructor
public class QuoteDataFromFileLoader implements ApplicationRunner {

    private final QuoteMongoReactiveRepository quoteMongoReactiveRepository;

    /**
     * Since the repository is reactive, we need to block() to wait for the result of the one-element publisher (Mono)
     * containing the number of quotes in the repository (the count method).
     *
     * We apply a reactive pattern to subscribe to the result of save() from the reactive repository.
     * Remember that, if we don’t consume the result, the quote is not stored.
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (quoteMongoReactiveRepository.count().block() == 0L) {
            var idSupplier = getIdSequenceSupplier();
            var bufferedReader = new BufferedReader(
                    new InputStreamReader(getClass()
                                          .getClassLoader()
                                          .getResourceAsStream("quotes.txt"))
            );

            Flux.fromStream(
                bufferedReader.lines()
                              .filter(l -> !l.trim().isEmpty())
                              .map(l -> quoteMongoReactiveRepository.save(
                                    new Quote(idSupplier.get(),
                                            "The Quotes", l))
                              )
            ).subscribe(m -> log.info("New quote loaded: {}", m.block())); // if we don’t consume the result,
                                                                          // the quote is not stored.

            log.info("Repository contains {} entries.",
                    quoteMongoReactiveRepository.count().block());
        }
    }

    private Supplier<String> getIdSequenceSupplier() {
        return new Supplier<>() {
            Long l = 0L;

            @Override
            public String get() {
                // adds padding zeroes
                return String.format("%05d", l++);
            }
        };
    }
}
