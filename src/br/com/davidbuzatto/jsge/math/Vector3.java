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
 * Classe para representação de um vetor de três dimensões.
 * 
 * Pode ser usada para retornar valores com três componentes, como pontos
 * 3D etc.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Vector3 {
    
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
     * Cria um novo vetor de três dimensões com valores padrão.
     */
    public Vector3() {
    }

    /**
     * Cria um novo vetor de três dimensões.
     * 
     * @param x coordenada x.
     * @param y coordenada y.
     * @param z coordenada z.
     */
    public Vector3( double x, double y, double z ) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return String.format( "Vector3[%.2f, %.2f, %.2f]", x, y, z );
    }
    
}
