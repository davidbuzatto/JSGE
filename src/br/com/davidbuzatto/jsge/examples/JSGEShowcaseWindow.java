/*
 * Copyright (C) 2024 Prof. Dr. David Buzatto
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package br.com.davidbuzatto.jsge.examples;

import br.com.davidbuzatto.jsge.examples.ball.BouncingBallExample;
import br.com.davidbuzatto.jsge.examples.camera.CameraExample;
import br.com.davidbuzatto.jsge.examples.particles.ParticlesExample;
import br.com.davidbuzatto.jsge.utils.Utils;
import javax.swing.JOptionPane;

/**
 * Janela do showcase (vitrine) da JSGE.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class JSGEShowcaseWindow extends javax.swing.JFrame {

    /**
     * Creates new form JSGEShowcaseFrame
     */
    public JSGEShowcaseWindow() {
        initComponents();
        setTitle( String.format( "JSGE v%s Showcase", Utils.getVersion() ) );
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblHeader = new javax.swing.JLabel();
        btnDrawingPrimitiveMethods = new javax.swing.JButton();
        btnDrawingPrimitiveObjects = new javax.swing.JButton();
        btnCollisionDetection = new javax.swing.JButton();
        btnImageLoadingAndProcessing = new javax.swing.JButton();
        btnUserInteraction = new javax.swing.JButton();
        btnColorMethods = new javax.swing.JButton();
        btnBouncingBall = new javax.swing.JButton();
        btnParticles = new javax.swing.JButton();
        btnCamera = new javax.swing.JButton();
        btnSounds = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblHeader.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHeader.setText("JSGE Showcase - Prof. Dr. David Buzatto");

        btnDrawingPrimitiveMethods.setText("Drawing with Primitives Methods");
        btnDrawingPrimitiveMethods.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDrawingPrimitiveMethodsActionPerformed(evt);
            }
        });

        btnDrawingPrimitiveObjects.setText("Drawing with Primitive Objects");
        btnDrawingPrimitiveObjects.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDrawingPrimitiveObjectsActionPerformed(evt);
            }
        });

        btnCollisionDetection.setText("Collision Detection and Points at Lines and Curves");
        btnCollisionDetection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCollisionDetectionActionPerformed(evt);
            }
        });

        btnImageLoadingAndProcessing.setText("Image Loading and Processing");
        btnImageLoadingAndProcessing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImageLoadingAndProcessingActionPerformed(evt);
            }
        });

        btnUserInteraction.setText("User Interaction");
        btnUserInteraction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserInteractionActionPerformed(evt);
            }
        });

        btnColorMethods.setText("Color Methods");
        btnColorMethods.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnColorMethodsActionPerformed(evt);
            }
        });

        btnBouncingBall.setText("Bouncing Ball");
        btnBouncingBall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBouncingBallActionPerformed(evt);
            }
        });

        btnParticles.setText("Particles");
        btnParticles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnParticlesActionPerformed(evt);
            }
        });

        btnCamera.setText("Camera");
        btnCamera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCameraActionPerformed(evt);
            }
        });

        btnSounds.setText("Sounds");
        btnSounds.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSoundsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDrawingPrimitiveMethods, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDrawingPrimitiveObjects, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCollisionDetection, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnImageLoadingAndProcessing, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUserInteraction, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnColorMethods, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBouncingBall, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnParticles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHeader, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                    .addComponent(btnCamera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSounds, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDrawingPrimitiveMethods)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDrawingPrimitiveObjects)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCollisionDetection)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnImageLoadingAndProcessing)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserInteraction)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnColorMethods)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBouncingBall)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnParticles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCamera)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSounds)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDrawingPrimitiveMethodsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDrawingPrimitiveMethodsActionPerformed
        new DrawingWithPrimitivesMethodsExample().setDefaultCloseOperation( DISPOSE_ON_CLOSE );
    }//GEN-LAST:event_btnDrawingPrimitiveMethodsActionPerformed

    private void btnDrawingPrimitiveObjectsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDrawingPrimitiveObjectsActionPerformed
        new DrawingWithPrimitivesObjectsExample().setDefaultCloseOperation( DISPOSE_ON_CLOSE );
    }//GEN-LAST:event_btnDrawingPrimitiveObjectsActionPerformed

    private void btnCollisionDetectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCollisionDetectionActionPerformed
        new CollisionDetectionExample().setDefaultCloseOperation( DISPOSE_ON_CLOSE );
    }//GEN-LAST:event_btnCollisionDetectionActionPerformed

    private void btnImageLoadingAndProcessingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImageLoadingAndProcessingActionPerformed
        new ImageLoadingProcessingExample().setDefaultCloseOperation( DISPOSE_ON_CLOSE );
    }//GEN-LAST:event_btnImageLoadingAndProcessingActionPerformed

    private void btnUserInteractionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserInteractionActionPerformed
        new UserInteractionExample().setDefaultCloseOperation( DISPOSE_ON_CLOSE );
    }//GEN-LAST:event_btnUserInteractionActionPerformed

    private void btnColorMethodsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnColorMethodsActionPerformed
        new ColorMethodsExample().setDefaultCloseOperation( DISPOSE_ON_CLOSE );
    }//GEN-LAST:event_btnColorMethodsActionPerformed

    private void btnBouncingBallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBouncingBallActionPerformed
        new BouncingBallExample().setDefaultCloseOperation( DISPOSE_ON_CLOSE );
    }//GEN-LAST:event_btnBouncingBallActionPerformed

    private void btnParticlesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnParticlesActionPerformed
        new ParticlesExample().setDefaultCloseOperation( DISPOSE_ON_CLOSE );
    }//GEN-LAST:event_btnParticlesActionPerformed

    private void btnCameraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCameraActionPerformed
        new CameraExample().setDefaultCloseOperation( DISPOSE_ON_CLOSE );
    }//GEN-LAST:event_btnCameraActionPerformed

    private void btnSoundsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSoundsActionPerformed
        JOptionPane.showMessageDialog( this, "Not implemented yet." );
    }//GEN-LAST:event_btnSoundsActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main( String args[] ) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for ( javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels() ) {
                if ( "Nimbus".equals( info.getName() ) ) {
                    javax.swing.UIManager.setLookAndFeel( info.getClassName() );
                    break;
                }
            }
        } catch ( ClassNotFoundException ex ) {
            java.util.logging.Logger.getLogger(JSGEShowcaseWindow.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
        } catch ( InstantiationException ex ) {
            java.util.logging.Logger.getLogger(JSGEShowcaseWindow.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
        } catch ( IllegalAccessException ex ) {
            java.util.logging.Logger.getLogger(JSGEShowcaseWindow.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
        } catch ( javax.swing.UnsupportedLookAndFeelException ex ) {
            java.util.logging.Logger.getLogger(JSGEShowcaseWindow.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JSGEShowcaseWindow().setVisible( true );
            }
        } );
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBouncingBall;
    private javax.swing.JButton btnCamera;
    private javax.swing.JButton btnCollisionDetection;
    private javax.swing.JButton btnColorMethods;
    private javax.swing.JButton btnDrawingPrimitiveMethods;
    private javax.swing.JButton btnDrawingPrimitiveObjects;
    private javax.swing.JButton btnImageLoadingAndProcessing;
    private javax.swing.JButton btnParticles;
    private javax.swing.JButton btnSounds;
    private javax.swing.JButton btnUserInteraction;
    private javax.swing.JLabel lblHeader;
    // End of variables declaration//GEN-END:variables
}
