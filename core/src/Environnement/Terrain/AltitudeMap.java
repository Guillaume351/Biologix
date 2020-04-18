package Environnement.Terrain;

import Environnement.HeightMap;
import com.badlogic.gdx.math.Vector2;

public class AltitudeMap extends HeightMap {
    public boolean LigneDeVue(Vector2 A, Vector2 B, double delta, double h0) {
        //delta : taille d'un pas
        //h0 : elevation de la ligne de vue
        int nbPas = (int) (A.dst(B) / delta);
        //Altitude en a
        double h1 = getValeur(A);
        //Altitude en b
        double h2 = getValeur(B);
        //resultat
        boolean ok = true;
        //parcourir le trajet
        for (int i = 0; i < nbPas; i++) {
            float k = (float) i / (float) nbPas;
            //Altitude du terrain au point du parcours
            double hloc = getValeur(A.lerp(B, k));
            //Altitude de la ligne de vue en ce point du parcours
            double hview = (1.0 - k) * h1 + k * h2;
            if (hview + h0 < hloc) {
                //Si la ligne de vue passe sous le terrain
                ok = false;
                break;
            }
        }
        return ok;
    }
}
