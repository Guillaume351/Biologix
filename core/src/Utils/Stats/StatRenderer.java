package Utils.Stats;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StatRenderer {
    SpriteBatch batch;
    Statisticien statisticien;

    public StatRenderer(Statisticien statisticien, SpriteBatch batch) {
        this.batch = batch;
        this.statisticien = statisticien;
    }

    public void rendu() {
        batch.begin();
        afficherGraphique();
        batch.end();
    }

    public void afficherGraphique() {
        batch.draw(statisticien.getHistoriqueEnergetique(512, 512), 0, 0, 300, 300);
    }
}
