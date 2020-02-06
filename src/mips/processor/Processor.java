/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mips.processor;

import GUI.Main_GUI;
import mips.FileWriteReader;
import static mips.processor.InstructionDecode.runInstruction;
import static mips.processor.Memory.getWord;
import static mips.processor.Registers.getPc;

/**
 *
 * @author parke
 */
public class Processor implements Runnable {

    private static boolean isRunning = false;
    private static long instructionsRan = 0;
    private static int delay;

    public static long getInstructionsRan() {
        return instructionsRan;
    }

    public static synchronized void stop() {
        Main_GUI.refresh();
        isRunning = false;
    }

    public static void reset() {
        stop();
        instructionsRan = 0;
        Registers.reset();
        FileWriteReader.reloadMXNFile();
        Main_GUI.refresh();
    }

    public static void setDelay(int delay) {
        Processor.delay = delay;
    }

    public static synchronized int getDelay() {
        return delay;
    }

    private static synchronized boolean isRunning() {
        return isRunning;
    }

    public static synchronized void start() {
        if (!isRunning) {
            Processor runnable = new Processor();
            Thread thread = new Thread(runnable);
            thread.setName("Processor");
            isRunning = true;
            thread.start();
        }
    }

    @Override
    public void run() {
        while (isRunning()) {
            singleStep();
            delayNano(getDelay());
        }
    }

    private static void delayNano(long time) {

        long start = System.nanoTime();
        long end = 0;
        do {
            end = System.nanoTime();
        } while (start + time >= end);
    }

    public static int getOpCode() {
        return getWord(getPc());
    }

    public static void singleStep() {
        if(!runInstruction(getOpCode())){
                        Main_GUI.stop();
            Main_GUI.infoBox("Error", "invalid OpCode at " + Registers.getPc());
        }
        instructionsRan++;
    }

}