package com.java;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Shehata.Ibrahim
 */
public class TowerDisk extends JLabel {

    private static final int DEFAULT_HEIGHT = 23;
    public static final int DEFAULT_STEP = 3;
    private static final int MAX_HEIGHT = 50;
    private static final int DEFAULT_SLEEP_TIME = 20;
    public static final int DEFAULT_WIDTH=170;

    public TowerDisk() {
        super();
        setBackground(new java.awt.Color(153, 153, 153));
        setForeground(new java.awt.Color(51, 51, 51));
        setBorder(javax.swing.BorderFactory.createEtchedBorder(2, null, new java.awt.Color(102, 102, 102)));
        setOpaque(true);

        setBounds(390, 366, 170, 20);
    }

    public void init(int width, int billerX, int billerWidth, int currentY) {
        setSize(width, DEFAULT_HEIGHT);
        int diskX = billerX;
        diskX -= (width - billerWidth) / 2;
        setLocation(diskX, currentY + DEFAULT_STEP);
    }

    public void takeYStep(final boolean isUp) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setLocation(getX(), isUp ? (getY() - DEFAULT_STEP) : (getY() + DEFAULT_STEP));
            }
        });

    }

    public void takeXStep(final boolean isRight) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setLocation(isRight ? (getX() + DEFAULT_STEP) : getX() - DEFAULT_STEP, getY());
            }
        });

    }

    public void moveToTower(Tower source,Tower destination) {
        boolean destinationReached = false;
        while (!destinationReached) {
            try {
                while (getY() > MAX_HEIGHT) {
                    takeYStep(true);
                    nap();
                }
                setLocation(getX(), MAX_HEIGHT);
                int diskX = destination.getX();
                diskX -= (getWidth() - destination.getWidth()) / 2;
                if (getX() > diskX) {
                    while (getX() > diskX) {
                        takeXStep(false);
                        nap();
                    }
                }else
                if (getX() < diskX) {
                    while (getX() < diskX) {
                        takeXStep(true);
                        nap();
                    }
                }
                setLocation(diskX, getY());
                while (getY() < destination.getCurrentY()) {
                    takeYStep(false);
                    nap();
                }
                 setLocation(diskX, destination.getCurrentY());
                destination.addDisk();
                source.RemoveDisk();
                destinationReached=true;
            } catch (EmptyTowerException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void nap() {
        try {
            Thread.currentThread().sleep(DEFAULT_SLEEP_TIME);
        } catch (InterruptedException ex) {
           ex.printStackTrace();
        }
    }
}
