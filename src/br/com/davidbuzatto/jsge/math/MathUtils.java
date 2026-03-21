/*
 * Copyright (C) 2026 Prof. Dr. David Buzatto
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

import java.util.Random;

/**
 * Interface with static utility methods related to mathematics.
 *
 * Several implementations are based on raylib and its modules
 * (www.raylib.com). In this case, mainly raymath.
 *
 * @author Prof. Dr. David Buzatto
 */
public interface MathUtils {
    
    /**
     * Pseudorandom number generator.
     */
    public static final Random RANDOM_GEN = new Random();
    
    /**
     * Constant for converting degrees to radians.
     */
    public static final double DEG2RAD = Math.PI / 180.0;
    
    /**
     * Constant for converting radians to degrees.
     */
    public static final double RAD2DEG = 180.0 / Math.PI;

    /**
     * Performs linear interpolation between two values.
     *
     * @param start Start value.
     * @param end End value.
     * @param amount Amount (0 to 1)
     * @return The linear interpolation between two values.
     */
    public static double lerp( double start, double end, double amount ) {
        return start + amount * ( end - start );
    }
    
    /**
     * Performs inverse linear interpolation between two values.
     *
     * @param start Start value.
     * @param end End value.
     * @param value The value.
     * @return The percentage of the value relative to the interval between start and end.
     */
    public static double inverseLerp( double start, double end, double value ) {
        return ( value - start ) / ( end - start );
    }

    /**
     * Clamps a value between two values.
     *
     * @param value The value.
     * @param min The minimum value.
     * @param max The maximum value.
     * @return The value clamped between minimum and maximum.
     */
    public static int clamp( int value, int min, int max ) {
        int result = value < min ? min : value;
        if ( result > max ) result = max;
        return result;
    }    

    /**
     * Clamps a value between two values.
     *
     * @param value The value.
     * @param min The minimum value.
     * @param max The maximum value.
     * @return The value clamped between minimum and maximum.
     */
    public static double clamp( double value, double min, double max ) {
        double result = value < min ? min : value;
        if ( result > max ) result = max;
        return result;
    }    

    /**
     * Normalizes the value within the given range.
     *
     * @param value The value.
     * @param start The start value.
     * @param end The end value.
     * @return The value normalized between the start and end values.
     */
    public static double normalize( double value, double start, double end ) {
        return ( value - start ) / ( end - start );
    }

    /**
     * Remaps a value from an input range to an output range.
     *
     * @param value The value.
     * @param inputStart The start value of the input range.
     * @param inputEnd The end value of the input range.
     * @param outputStart The start value of the output range.
     * @param outputEnd The end value of the output range.
     * @return The remapped value.
     */
    public static double remap( double value, double inputStart, double inputEnd, double outputStart, double outputEnd ) {
        return ( value - inputStart ) / ( inputEnd - inputStart ) * ( outputEnd - outputStart ) + outputStart;
    }

    /**
     * Wraps a value between a minimum and maximum value.
     *
     * @param value The value.
     * @param min The minimum value.
     * @param max The maximum value.
     * @return The wrapped value.
     */
    public static double wrap( double value, double min, double max ) {
        return value - ( max - min ) * Math.floor( ( value - min ) / ( max - min ) );
    }

    /**
     * Generates a pseudorandom number between min (inclusive) and max (inclusive).
     *
     * @param min Start of the range.
     * @param max End of the range.
     * @return A pseudorandom number between min (inclusive) and max (inclusive).
     */
    public static int getRandomValue( int min, int max ) {
        return RANDOM_GEN.nextInt( min, max + 1 );
    }

    /**
     * Sets the random seed of the random number generator.
     *
     * @param seed The seed to be used.
     */
    public static void setRandomSeed( long seed ) {
        RANDOM_GEN.setSeed( seed );
    }
    
}
