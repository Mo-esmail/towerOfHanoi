package com.java;

import javax.swing.JLabel;

/**
 *
 * @author Shehata.Ibrahim
 */
public class Tower extends JLabel {

    private int currentY = 343;
    private int numberOfDisks = 0;
    private final static int DISK_SPACE = 26;

    public void addDisk() {
        numberOfDisks++;
        currentY -= DISK_SPACE;
    }

    public void RemoveDisk() throws EmptyTowerException {
        if (numberOfDisks == 0) {
            throw new EmptyTowerException();
        }
        numberOfDisks--;

        currentY += DISK_SPACE;
    }

    public int getCurrentY() {
        return currentY;
    }
    
}
