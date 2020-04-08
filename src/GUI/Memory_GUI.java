/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Compiler.ASMCompiler;
import javax.swing.ScrollPaneConstants;
import mips.processor.Memory;

/**
 *
 * @author parke
 */
public class Memory_GUI extends javax.swing.JFrame {

    private static boolean autoUpdate;
    ComboBoxSearchable cbs;

    public Memory_GUI() {
        initComponents();
        memoryPositionScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        memoryPositionScrollPane.getVerticalScrollBar().setModel(valsScrollPane.getVerticalScrollBar().getModel());
        cbs = new ComboBoxSearchable(memoryLables, ASMCompiler.getMemoryLables());
        updateMemoryValues();
        this.show(true);
    }

    private synchronized void startAutoUpdate() {
        this.autoUpdate = true;

        new Thread(new autoUpdateMemory(this)).start();
    }

    private synchronized boolean isAutoUpdate() {
        return this.autoUpdate;
    }

    public class autoUpdateMemory implements Runnable {

        Memory_GUI gui;

        public autoUpdateMemory(Memory_GUI gui) {
            this.gui = gui;
            Thread.currentThread().setName("Memory GUI autoupdate");

        }

        public void run() {
            while (gui.isAutoUpdate()) {
                gui.updateMemoryValues();
                try {
                    Thread.sleep(10);
                } catch (Exception e) {

                }
            }
        }
    };

    private synchronized void stopAutoUpdate() {
        this.autoUpdate = false;
    }

    public void updateMemoryValues() {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                int size;
                int startingPos;
                try {
                    size = Integer.parseInt(length.getText());

                    if (size > 1000) {
                        size = 1000;
                    }

                } catch (Exception e) {
                    size = 0;
                }

                try {
                    startingPos = Integer.parseInt(startingPosition.getText());
                } catch (Exception e) {
                    startingPos = 0;
                }

                Object[][] tempData = new Object[size][4];
                Object[][] tempMemPos = new Object[size][1];

                for (int i = startingPos; i < size + startingPos; i++) {

                    tempMemPos[i - startingPos][0] = String.format("%8s", Integer.toHexString(i)).replaceAll(" ", "0");

                    int val = Memory.superGetByte(i) & 0xFF;
                    tempData[i - startingPos][0] = val;
                    tempData[i - startingPos][1] = String.format("%8s", Integer.toBinaryString(val)).replaceAll(" ", "0");
                    tempData[i - startingPos][2] = String.format("%2s", Integer.toHexString(val)).replaceAll(" ", "0");
                    tempData[i - startingPos][3] = (char) val;
                }
                memoryPositionTable.setModel(new javax.swing.table.DefaultTableModel(tempMemPos,
                        new String[]{
                            "Memory Position"
                        }
                ));

                valsTable.setModel(new javax.swing.table.DefaultTableModel(tempData,
                        new String[]{
                            "Dec", "Bin", "Hex", "ASCII"
                        }
                ));

            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        valsScrollPane = new javax.swing.JScrollPane();
        valsTable = new javax.swing.JTable();
        memoryPositionScrollPane = new javax.swing.JScrollPane();
        memoryPositionTable = new javax.swing.JTable();
        StuffHolder = new javax.swing.JPanel();
        startingPosition = new javax.swing.JFormattedTextField();
        startingPositionLable = new javax.swing.JLabel();
        lengthLable = new javax.swing.JLabel();
        length = new javax.swing.JFormattedTextField();
        updateButton = new javax.swing.JButton();
        autoUpdateButton = new javax.swing.JToggleButton();
        memoryLables = new javax.swing.JComboBox<>();
        memoryPointersLable = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);

        valsScrollPane.setBorder(null);

        valsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Dec", "Bin", "Hex", "ASCII"
            }
        ));
        valsTable.setToolTipText("");
        valsScrollPane.setViewportView(valsTable);

        memoryPositionScrollPane.setBorder(null);

        memoryPositionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Memory Position"
            }
        ));
        memoryPositionTable.setEnabled(false);
        memoryPositionScrollPane.setViewportView(memoryPositionTable);

        startingPosition.setText("0");

        startingPositionLable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        startingPositionLable.setText("Mem Position");
        startingPositionLable.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        startingPositionLable.setMaximumSize(new java.awt.Dimension(80, 16));
        startingPositionLable.setMinimumSize(new java.awt.Dimension(80, 16));
        startingPositionLable.setPreferredSize(new java.awt.Dimension(80, 16));

        lengthLable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lengthLable.setText("Length");
        lengthLable.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        length.setText("10");

        updateButton.setText("Update");
        updateButton.setFocusable(false);
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        autoUpdateButton.setText("Auto Update");
        autoUpdateButton.setFocusable(false);
        autoUpdateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autoUpdateButtonActionPerformed(evt);
            }
        });

        memoryLables.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        memoryLables.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                memoryLablesActionPerformed(evt);
            }
        });

        memoryPointersLable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        memoryPointersLable.setText("Memory Lables");
        memoryPointersLable.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout StuffHolderLayout = new javax.swing.GroupLayout(StuffHolder);
        StuffHolder.setLayout(StuffHolderLayout);
        StuffHolderLayout.setHorizontalGroup(
            StuffHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, StuffHolderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(StuffHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(StuffHolderLayout.createSequentialGroup()
                        .addComponent(startingPosition, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(length, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(StuffHolderLayout.createSequentialGroup()
                        .addComponent(startingPositionLable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lengthLable, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(updateButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(autoUpdateButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(StuffHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(memoryLables, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(memoryPointersLable, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
                .addContainerGap())
        );
        StuffHolderLayout.setVerticalGroup(
            StuffHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StuffHolderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(StuffHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(StuffHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(startingPositionLable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lengthLable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(memoryPointersLable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(StuffHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startingPosition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(length, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(memoryLables, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(StuffHolderLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(StuffHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateButton)
                    .addComponent(autoUpdateButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(StuffHolder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(memoryPositionScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(valsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(StuffHolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(valsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
                    .addComponent(memoryPositionScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        updateMemoryValues();
    }//GEN-LAST:event_updateButtonActionPerformed

    private void autoUpdateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autoUpdateButtonActionPerformed
        if (autoUpdateButton.isSelected()) {
            startAutoUpdate();
        } else {
            stopAutoUpdate();
        }
    }//GEN-LAST:event_autoUpdateButtonActionPerformed

    private void memoryLablesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_memoryLablesActionPerformed
        startingPosition.setText(Integer.toString(ASMCompiler.getByteIndexOfMemoryLable(cbs.getEnteredText(), -1)));
        updateMemoryValues();
    }//GEN-LAST:event_memoryLablesActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Memory_GUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Memory_GUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Memory_GUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Memory_GUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Memory_GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel StuffHolder;
    private javax.swing.JToggleButton autoUpdateButton;
    private javax.swing.JFormattedTextField length;
    private javax.swing.JLabel lengthLable;
    private javax.swing.JComboBox<String> memoryLables;
    private javax.swing.JLabel memoryPointersLable;
    private javax.swing.JScrollPane memoryPositionScrollPane;
    private javax.swing.JTable memoryPositionTable;
    private javax.swing.JFormattedTextField startingPosition;
    private javax.swing.JLabel startingPositionLable;
    private javax.swing.JButton updateButton;
    private javax.swing.JScrollPane valsScrollPane;
    private javax.swing.JTable valsTable;
    // End of variables declaration//GEN-END:variables
}
