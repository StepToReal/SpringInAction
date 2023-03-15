package tacos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tacos.Ingredient.Type;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;

@SpringBootApplication
public class TacoCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(TacoCloudApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository repo, TacoRepository tacoRepo) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Ingredient flto = new Ingredient("FLTO", "Flour Tortilla", Type.WRAP);
                Ingredient coto = new Ingredient("COTO", "Corn Tortilla", Type.WRAP);
                Ingredient grbf = new Ingredient("GRBF", "Ground Beef", Type.PROTEIN);
                Ingredient carn = new Ingredient("CARN", "Carnitas", Type.PROTEIN);
                Ingredient tmto = new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES);
                Ingredient letc = new Ingredient("LETC", "Lettuce", Type.VEGGIES);
                Ingredient ched = new Ingredient("CHED", "Cheddar", Type.CHEESE);
                Ingredient jack = new Ingredient("JACK", "Monterrey Jack", Type.CHEESE);
                Ingredient slsa = new Ingredient("SLSA", "Salsa", Type.SAUCE);
                Ingredient srcr = new Ingredient("SRCR", "Sour Cream", Type.SAUCE);

                repo.save(flto);
                repo.save(coto);
                repo.save(grbf);
                repo.save(carn);
                repo.save(tmto);
                repo.save(letc);
                repo.save(ched);
                repo.save(jack);
                repo.save(slsa);
                repo.save(srcr);

                Taco taco1 = new Taco();
                taco1.setName("FCS taco");
                taco1.getIngredients().add(coto);
                taco1.getIngredients().add(carn);
                taco1.getIngredients().add(slsa);
                tacoRepo.save(taco1);

                Taco taco2 = new Taco();
                taco2.setName("CMS taco");
                taco2.getIngredients().add(flto);
                taco2.getIngredients().add(jack);
                taco2.getIngredients().add(srcr);
                tacoRepo.save(taco2);
            }
        };
    }
}
