package fr.vernoux.lab.doubles;

import fr.vernoux.lab.RandomGenerator;

import java.util.Random;

public class RandomGeneratorDouble implements RandomGenerator {

    private final Random random;

    public RandomGeneratorDouble(long seed) {
        random = new Random(seed);
    }

    @Override
    public int randomInt(int minIncluded, int maxExcluded) {
        return random.nextInt(maxExcluded);
    }
}
