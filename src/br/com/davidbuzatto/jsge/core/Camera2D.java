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

import br.com.davidbuzatto.jsge.geom.Vector2;

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

    @Override
    public String toString() {
        return "Camera{" + "target=" + target + ", offset=" + offset + ", rotation=" + rotation + ", zoom=" + zoom + '}';
    }
    
}
