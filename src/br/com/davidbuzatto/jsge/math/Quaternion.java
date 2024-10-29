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
package br.com.davidbuzatto.jsge.math;

/**
 * Classe para representação de um quaternion.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Quaternion {
    
    /**
     * Coordenada x.
     */
    public double x;
    
    /**
     * Coordenada y.
     */
    public double y;
    
    /**
     * Coordenada z.
     */
    public double z;
    
    /**
     * Coordenada w.
     */
    public double w;

    /**
     * Cria um novo quaternion com valores padrão.
     */
    public Quaternion() {
    }

    /**
     * Cria um novo quaternion.
     * 
     * @param x coordenada x.
     * @param y coordenada y.
     * @param z coordenada z.
     * @param w coordenada w.
     */
    public Quaternion( double x, double y, double z, double w ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    @Override
    public String toString() {
        return String.format( "Quaternion[%.2f, %.2f, %.2f, %.2f]", x, y, z, w );
    }
        
}
