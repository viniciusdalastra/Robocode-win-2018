package br.edu.unidavi;

import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;
import robocode.HitByBulletEvent;
import robocode.*;
import java.awt.*;
import robocode.Robot;

public class Vinicius extends Robot {

    @Override
    public void run() {
        setBodyColor(Color.BLACK);
        setGunColor(Color.BLACK);
        setRadarColor(Color.BLACK);
        setScanColor(Color.BLACK);

        while (true) {
            turnRadarRight(360);
            ahead(100);
            turnGunRight(360);
            back(100);
        }
    }

    public void onHitWall(HitWallEvent evento) {
        turnRight(90);
        ahead(200);
    }

    public void onHitByBullet(HitByBulletEvent evento) {
        turnLeft(90);
        back(50);
    }

    public void onScannedRobot(ScannedRobotEvent evento) {
        alcaEMiraDeTiro(evento.getBearing());

        disparoNormal(evento.getEnergy(), evento.getBearing());
    }

    public void onHitRobot(HitRobotEvent evento) {
        alcaEMiraDeTiro(evento.getBearing());
        disparoFatal(evento.getEnergy());
    }

    public void disparoNormal(double minhaEnergia, double distancia) {
        if (distancia > 100 || minhaEnergia < 30) {
            fire(1);
        } else if (distancia > 50) {
            fire(2);
        } else {
            disparoFatal(minhaEnergia);
        }
    }

    public void disparoFatal(double energia) {
        double energiaTiro = (energia / 4) + .1; // utiliza um quarto da energia para o tiro!
        fire(energiaTiro);
    }

    public void alcaEMiraDeTiro(double distancia) {
        double positionRobot = getHeading() + distancia - getGunHeading(); // localiza o robo que colidiu

        if (!(positionRobot > -180 && positionRobot <= 180)) {
            while (positionRobot <= -180) {
                positionRobot += 360;
            }
            while (positionRobot > 180) {
                positionRobot -= 360;
            }
        }

        turnGunRight(positionRobot);
    }
}
