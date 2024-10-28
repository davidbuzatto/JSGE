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
 * Uma matriz.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Matrix {
    
    public double m0, m4, m8, m12;  // primeira linha
    public double m1, m5, m9, m13;  // segunda linha
    public double m2, m6, m10, m14; // terceira linha
    public double m3, m7, m11, m15; // quarta linha

    /**
     * Cria uma nova matriz com valores padrão.
     */
    public Matrix() {
    }

    /**
     * Cria uma nova matriz.
     * 
     * @param m0 valor da linha 1, coluna 1
     * @param m4 valor da linha 1, coluna 2
     * @param m8 valor da linha 1, coluna 3
     * @param m12 valor da linha 1, coluna 4
     * @param m1 valor da linha 2, coluna 1
     * @param m5 valor da linha 2, coluna 2
     * @param m9 valor da linha 2, coluna 3
     * @param m13 valor da linha 2, coluna 4
     * @param m2 valor da linha 3, coluna 1
     * @param m6 valor da linha 3, coluna 2
     * @param m10 valor da linha 3, coluna 3
     * @param m14 valor da linha 3, coluna 4
     * @param m3 valor da linha 4, coluna 1
     * @param m7 valor da linha 4, coluna 2
     * @param m11 valor da linha 4, coluna 3
     * @param m15 valor da linha 4, coluna 4
     */
    public Matrix( double m0, double m4, double m8, double m12, double m1, double m5, double m9, double m13, double m2, double m6, double m10, double m14, double m3, double m7, double m11, double m15 ) {
        this.m0 = m0;
        this.m4 = m4;
        this.m8 = m8;
        this.m12 = m12;
        this.m1 = m1;
        this.m5 = m5;
        this.m9 = m9;
        this.m13 = m13;
        this.m2 = m2;
        this.m6 = m6;
        this.m10 = m10;
        this.m14 = m14;
        this.m3 = m3;
        this.m7 = m7;
        this.m11 = m11;
        this.m15 = m15;
    }

    @Override
    public String toString() {
        return String.format( 
                """
                Matrix[
                    %.2f, %.2f, %.2f, %.2f
                    %.2f, %.2f, %.2f, %.2f
                    %.2f, %.2f, %.2f, %.2f
                    %.2f, %.2f, %.2f, %.2f
                ]
                """, 
                m0, m4, m8, m12,
                m1, m5, m9, m13,
                m2, m6, m10, m14,
                m3, m7, m11, m15
        );
    }
    
}
