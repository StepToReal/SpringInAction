package tacos.web.api;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tacos.Order;
import tacos.Taco;
import tacos.data.OrderRepository;
import tacos.data.TacoRepository;
import tacos.web.DesignTacoController;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/design", produces = "application/json")
@CrossOrigin(origins = "*")
public class RestDesignTacoController {
    private TacoRepository tacoRepo;
//    private OrderRepository orderRepo;

    public RestDesignTacoController(TacoRepository tacoRepo) {
        this.tacoRepo = tacoRepo;
//        this.orderRepo = orderRepo;
    }

//    @GetMapping("/recent")
//    public CollectionModel<TacoResource> recentTacos() {
//        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
//
//        List<Taco> tacos = tacoRepo.findAll(page).getContent();
//
//        CollectionModel<TacoResource> collectionModel = new TacoResourceAssembler().toCollectionModel(tacos);
//
////        CollectionModel<EntityModel<Taco>> collectionModel = CollectionModel.wrap(tacos);
//
////        collectionModel.add(
////                linkTo(RestDesignTacoController.class)
////                        .slash("recent")
////                        .withRel("recents"));
//
//        // 위와 동일한 작업이나 method를 지정하여 하드코딩을 제거할 수 있음.
//        collectionModel.add(linkTo(methodOn(RestDesignTacoController.class).recentTacos())
//                .withRel("recents"));
//
//        return collectionModel;
//    }

    @GetMapping("/react-recent")
    public Flux<Taco> reactRecentTacos() {
        return tacoRepo.findAll().take(12);
    }

    @GetMapping("/{id}")
    public Mono<Taco> tacoById(@PathVariable("id") Long id) {
        return tacoRepo.findById(id);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Taco> postTaco(@RequestBody Taco taco) {
        return tacoRepo.save(taco);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
//        Optional<Taco> optTaco = tacoRepo.findById(id);
//        if (optTaco.isPresent()) {
//            return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//    }
//
//    @PostMapping(consumes = "application/json")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Taco postTaco(@RequestBody Taco taco) {
//        return tacoRepo.save(taco);
//    }
//
//    @PutMapping("/{orderId}")
//    public Order putOrder(@RequestBody Order order) {
//        return orderRepo.save(order);
//    }
//
//    @PatchMapping(value = "/{orderId}", consumes = "applicaiton/json")
//    public Order patchOrder(@PathVariable("orderId") Long orderId, @RequestBody Order patch) {
//        Order order = orderRepo.findById(orderId).get();
//
//        if (patch.getDeliveryName() != null) {
//            order.setDeliveryName(patch.getDeliveryName());
//        }
//        if (patch.getDeliveryStreet() != null) {
//            order.setDeliveryStreet(patch.getDeliveryStreet());
//        }
//        if (patch.getDeliveryCity() != null) {
//            order.setDeliveryCity(patch.getDeliveryCity());
//        }
//        if (patch.getDeliveryState() != null) {
//            order.setDeliveryState(patch.getDeliveryState());
//        }
//        if (patch.getDeliveryZip() != null) {
//            order.setDeliveryZip(patch.getDeliveryZip());
//        }
//        if (patch.getCcNumber() != null) {
//            order.setCcNumber(patch.getCcNumber());
//        }
//        if (patch.getCcExpiration() != null) {
//            order.setCcExpiration(patch.getCcExpiration());
//        }
//        if (patch.getCcCVV() != null) {
//            order.setCcCVV(patch.getCcCVV());
//        }
//
//        return orderRepo.save(order);
//    }
//
//    @DeleteMapping("/{orderId}")
//    @ResponseStatus(code = HttpStatus.NO_CONTENT)
//    public void deleteOrder(@PathVariable("orderId") Long orderId) {
//        try {
//            orderRepo.deleteById(orderId);
//        } catch (EmptyResultDataAccessException e) {}
//    }
}
