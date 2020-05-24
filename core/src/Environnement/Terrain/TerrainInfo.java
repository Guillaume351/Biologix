package Environnement.Terrain;

import com.badlogic.gdx.math.Vector2;

public class TerrainInfo {
    double altitude;
    Vector2 chgtMilieu;

    public TerrainInfo(double altitude) {
        this.altitude = altitude;
        chgtMilieu = null;
    }

    public TerrainInfo(Vector2 chgtMilieu) {
        this.chgtMilieu = chgtMilieu;
    }

    public TerrainInfo(double altitude, Vector2 chgtMilieu) {
        this.altitude = altitude;
        this.chgtMilieu = chgtMilieu;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public Vector2 getChgtMilieu() {
        return chgtMilieu;
    }

    public void setChgtMilieu(Vector2 chgtMilieu) {
        this.chgtMilieu = chgtMilieu;
    }
}
