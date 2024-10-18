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

import br.com.davidbuzatto.jsge.geom.Point;

/**
 * Representação de uma câmera para controle do processo de desenho.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Camera2D {
    
    public Point target;
    public Point offset;
    public double rotation;
    public double zoom;

    public Camera2D() {
        target = new Point();
        offset = new Point();
        rotation = 0.0;
        zoom = 1.0;
    }
    
    public Camera2D( Point target, Point offset, double rotation, double zoom ) {
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
