package tacos.web.api;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacos.Ingredient;
import tacos.data.IngredientRepository;

import java.util.Optional;

@RestController
@RequestMapping(path = "/ingredients", produces = "application/json")
@CrossOrigin(origins = "*")
public class IngredientController {

    private IngredientRepository repo;

    @Autowired
    public IngredientController(IngredientRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public Iterable<Ingredient> allIngredients() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable("id") String id) {
        Ingredient ingredient = repo.findById(id).orElse(null);
        return new ResponseEntity<>(ingredient, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Ingredient> putIngredient(@PathVariable("id") String id, @RequestBody Ingredient ingredient) {
        Optional<Ingredient> origin = repo.findById(id);

        if (origin.isPresent()) {
            origin.get().setName(ingredient.getName());
            repo.save(origin.get());
            return new ResponseEntity<>(origin.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteIngredient(@PathVariable("id") String id) {
        repo.deleteById(id);
    }
}
