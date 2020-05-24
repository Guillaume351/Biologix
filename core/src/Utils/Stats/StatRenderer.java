package Utils.Stats;

import com.badlogic.gdx.graphics.Texture;
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
        Texture texHistoEnergie = statisticien.getHistoriqueEnergetique(512, 512);
        Texture texPiramideAges = statisticien.getHistogrameAges(0, 512, 512);
        Texture texAcc = statisticien.getGraphiqueNbAccouchement(512, 512);
        batch.draw(texHistoEnergie, 0, 0, 512, 512);
        batch.draw(texPiramideAges, 512, 0, 512, 512);
        batch.draw(texAcc, 2 * 512, 0, 512, 512);
    }
}
