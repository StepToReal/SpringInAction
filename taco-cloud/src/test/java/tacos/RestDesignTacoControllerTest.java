package tacos;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tacos.data.TacoRepository;
import tacos.web.api.RestDesignTacoController;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class RestDesignTacoControllerTest {

    @Test
    public void shouldReturnRecentTacos() {
        Taco[] tacos = {
                testTaco(1L), testTaco(2L), testTaco(3L), testTaco(4L), testTaco(5L),
                testTaco(6L), testTaco(7L), testTaco(8L), testTaco(9L), testTaco(10L),
                testTaco(11L), testTaco(12L), testTaco(13L), testTaco(14L), testTaco(15L),
                testTaco(16L)
        };

        Flux<Taco> tacoFlux = Flux.just(tacos);

        TacoRepository tacoRepo = Mockito.mock(TacoRepository.class);
        when(tacoRepo.findAll()).thenReturn(tacoFlux);

        WebTestClient testClient = WebTestClient.bindToController(
                new RestDesignTacoController(tacoRepo)).build();

        testClient.get().uri("/design/react-recent")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$").isNotEmpty()
                .jsonPath("$[0].id").isEqualTo(tacos[0].getId().toString())
                .jsonPath("$[0].name").isEqualTo("Taco 1");
    }

    private Taco testTaco(Long number) {
        Taco taco = new Taco();
        taco.setId(number);

        if (number == null) {
            taco.setId(10L);
        }

        taco.setName("Taco " + number);

        return taco;
    }

    @Test
    public void shouldSaveATaco() {
        TacoRepository tacoRepo = Mockito.mock(TacoRepository.class);
        Mono<Taco> unsavedTacoMono = Mono.just(testTaco(null));
        Taco savedTaco = testTaco(null);
        savedTaco.setId(1L);
        Mono<Taco> savedTacoMono = Mono.just(savedTaco);

        when(tacoRepo.save(any())).thenReturn(savedTacoMono);

        WebTestClient testClient = WebTestClient.bindToController(new RestDesignTacoController(tacoRepo)).build();

        testClient.post()
                .uri("/design")
                .contentType(MediaType.APPLICATION_JSON)
                .body(unsavedTacoMono, Taco.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Taco.class)
                .isEqualTo(savedTaco);
    }
}
