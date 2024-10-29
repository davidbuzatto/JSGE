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
package br.com.davidbuzatto.jsge.core;

import br.com.davidbuzatto.jsge.math.Matrix;
import br.com.davidbuzatto.jsge.math.Vector2;
import br.com.davidbuzatto.jsge.math.Vector3;

/**
 * Representação de uma câmera para controle do processo de desenho.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Camera2D {
    
    /**
     * O alvo da câmera.
     */
    public Vector2 target;
    
    /**
     * O deslocamento da câmera.
     */
    public Vector2 offset;
    
    /**
     * Rotação em graus no sentido horário.
     */
    public double rotation;
    
    /**
     * Fator de zoom.
     */
    public double zoom;

    /**
     * Cria uma nova câmera apontando para a coordenada 0.0; 0.0, com deslocamento
     * igual a zero tanto na vertical, quanto na horizontal, sem rotação e com 
     * fator de zoom igual a 1.0.
     */
    public Camera2D() {
        target = new Vector2();
        offset = new Vector2();
        rotation = 0.0;
        zoom = 1.0;
    }
    
    /**
     * Cria uma nova câmera.
     * 
     * @param target o alvo.
     * @param offset o deslocamento.
     * @param rotation a rotação.
     * @param zoom o zoom.
     */
    public Camera2D( Vector2 target, Vector2 offset, double rotation, double zoom ) {
        this.target = target;
        this.offset = offset;
        this.rotation = rotation;
        this.zoom = zoom;
    }
    
    /**
     * Converte uma coordenada da tela para uma coordenada do mundo 2D de 
     * acordo com o câmera.
     * 
     * @param x A coordenada x da posição da tela.
     * @param y A coordenada y da posição da tela.
     * @return O ponto correspondente do mundo 2D.
     */
    public Vector2 getScreenToWorld( double x, double y ) {
        
        Matrix invMatCamera = getCameraMatrix().invert();
        Vector3 transform = new Vector3( x, y, 0 ).transform( invMatCamera );

        return new Vector2( transform.x, transform.y );
        
    }
    
    /**
     * Converte uma coordenada da tela para uma coordenada do mundo 2D de 
     * acordo com o câmera.
     * 
     * @param point A posição da tela.
     * @return O ponto correspondente do mundo 2D.
     */
    public Vector2 getScreenToWorld( Vector2 point ) {
        return getScreenToWorld( point.x, point.y );
    }
    
    /**
     * Converte uma coordenada do mundo 2D para uma coordenada da tela de 
     * acordo com o câmera.
     * 
     * @param x A coordenada x da posição do mundo 2D.
     * @param y A coordenada y da posição do mundo 2D.
     * @return O ponto correspondente da tela.
     */
    public Vector2 getWorldToScreen( double x, double y ) {
        
        Matrix matCamera = getCameraMatrix();
        Vector3 transform = new Vector3( x, y, 0 ).transform( matCamera );

        return new Vector2( transform.x, transform.y );
        
    }
    
    /**
     * Converte uma coordenada do mundo 2D para uma coordenada da tela de 
     * acordo com o câmera.
     * 
     * @param point A posição do mundo 2D.
     * @return O ponto correspondente da tela.
     */
    public Vector2 getWorldToScreen( Vector2 point ) {
        return getWorldToScreen( point.x, point.y );
    }
    
    /**
     * Obtém a matriz da câmera 2D.
     */
    public Matrix getCameraMatrix() {
        
        // fonte: https://github.com/raysan5/raylib/blob/master/src/rcore.c
        // The camera in world-space is set by
        //   1. Move it to target
        //   2. Rotate by -rotation and scaling by (1/zoom)
        //      When setting higher scaling, it's more intuitive for the world to become bigger (= camera become smaller),
        //      not for the camera getting bigger, hence the invert. Same deal with rotation
        //   3. Move it by (-offset);
        //      Offset defines target transform relative to screen, but since we're effectively "moving" screen (camera)
        //      we need to do it into opposite direction (inverse transform)

        // Having camera transform in world-space, inverse of it gives the modelview transform
        // Since (A*B*C)' = C'*B'*A', the modelview is
        //   1. Move to offset
        //   2. Rotate and Scale
        //   3. Move by -target
        Matrix matOrigin = Matrix.translate( -target.x, -target.y, 0.0 );
        Matrix matRotation = Matrix.rotate( new Vector3( 0.0, 0.0, 1.0 ), Math.toRadians( rotation ) );
        Matrix matScale = Matrix.scale( zoom, zoom, 1.0 );
        Matrix matTranslation = Matrix.translate( offset.x, offset.y, 0.0 );

        return matOrigin.multiply( matScale.multiply( matRotation ) ).multiply( matTranslation );
        
    }
    
    @Override
    public String toString() {
        return "Camera{" + "target=" + target + ", offset=" + offset + ", rotation=" + rotation + ", zoom=" + zoom + '}';
    }
    
}
