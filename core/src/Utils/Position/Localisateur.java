package Utils.Position;

import com.badlogic.gdx.math.Vector2;

import java.util.*;

public class Localisateur {
    public static TreeMap<Double, Localisable> distanceMapTriee(Vector2 position, List<Localisable> autres) {
        //Tree map avec distances comme clés
        TreeMap<Double, Localisable> dct = new TreeMap<Double, Localisable>();
        //Lier distance et objets
        for (Localisable item : autres) {

            double dist = position.dst(item.getPosition());
            dct.put(dist, item);
        }
        //triee dans l'ordre naturel car treeMap
        return dct;
    }

    public static List<Localisable> getNPlusProches(Vector2 position, List<Localisable> autres, int N) {
        //Recuperer la distance map triee
        TreeMap<Double, Localisable> distmap = distanceMapTriee(position, autres);
        List<Localisable> retour = new ArrayList<>();
        //nombre d'elements a retourner
        int nb = Math.min(N, distmap.size());
        //recuperer l'iterateur
        Iterator iterator = distmap.entrySet().iterator();
        //recuperer les nb premiers
        for (int i = 0; i < nb; i++) {
            Map.Entry<Double, Localisable> mapentry = (Map.Entry<Double, Localisable>) iterator.next();
            retour.add(mapentry.getValue());
        }
        return retour;
    }

    public static TreeMap<Double, Localisable> getPlusProcheQue(Vector2 position, List<Localisable> autres, double DistMax) {
        //Tree map avec distances comme clés
        TreeMap<Double, Localisable> dct = new TreeMap<Double, Localisable>();
        //Lier distance et objets
        for (Localisable item : autres) {
            double dist = position.dst(item.getPosition());
            if (dist < DistMax) {
                dct.put(dist, item);
            }
        }
        return dct;
    }

    public static TreeMap<Double, Localisable> getPlusLoinQue(Vector2 position, List<Localisable> autres, double DistMin) {
        //Tree map avec distances comme clés
        TreeMap<Double, Localisable> dct = new TreeMap<Double, Localisable>();
        //Lier distance et objets
        for (Localisable item : autres) {
            double dist = position.dst(item.getPosition());
            if (dist > DistMin) {
                dct.put(dist, item);
            }
        }
        return dct;
    }

    public static TreeMap<Double, Localisable> getDansSecteurAngulaire(Vector2 position, Vector2 direction, List<Localisable> autres, double ouverture) {
        //Tree map avec angles absolus comme clés
        TreeMap<Double, Localisable> dct = new TreeMap<Double, Localisable>();
        for (Localisable item : autres) {
            //Vecteur de distance entre les deux objets
            Vector2 dist = new Vector2(item.getPosition());
            dist.sub(item.getPosition());

            //Angle absolu en rad entre la direction et le vecteur distance
            double angle = Math.abs(dist.angleRad(direction));
            if (angle < ouverture) {
                dct.put(angle, item);
            }
        }
        //triee dans l'ordre naturel car treeMap
        return dct;
    }

    public static List<Localisable> getDansChampVision(Vector2 position, Vector2 direction, List<Localisable> autres, double ouverture, double distanceVue) {
        List<Localisable> dct = new ArrayList<>();
        for (Localisable item : autres) {
            //Vecteur de distance entre les deux objets
            Vector2 dist = new Vector2(item.getPosition());
            dist.sub(item.getPosition());
            //Angle absolu en rad entre la direction et le vecteur distance
            double angle = Math.abs(dist.angleRad(direction));
            //Si angle et distance ok, garder l'objet
            if (angle < ouverture && dist.len() < distanceVue) {
                dct.add(item);
            }
        }
        return dct;
    }


}
