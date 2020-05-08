package Environnement;

import Utils.Perlin.PerlinGenerator;
import Utils.Perlin.PerlinParams;
import com.badlogic.gdx.math.Vector2;

public class HeightMap {
    private PerlinParams params;

    private double min;
    private double max;

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public HeightMap(PerlinParams params, double min, double max) {
        this.params = params;
        this.min = min;
        this.max = max;
    }

    public double getValeur(double x, double y) {
        return min + (max - min) * (1.0 + PerlinGenerator.perlin2D(x, y, params)) / 2.0;
    }

    public double getValeur(Vector2 A) {
        return getValeur(A.x, A.y);
    }

    public PerlinParams getParams() {
        return params;
    }
}
