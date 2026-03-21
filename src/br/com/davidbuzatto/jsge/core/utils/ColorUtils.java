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
package br.com.davidbuzatto.jsge.core.utils;

import br.com.davidbuzatto.jsge.math.MathUtils;
import java.awt.Color;

/**
 * Interface with static utility methods related to colors.
 *
 * Several implementations are based on raylib and its modules
 * (www.raylib.com).
 *
 * @author Prof. Dr. David Buzatto
 */
public interface ColorUtils {
    
    /**
     * Applies transparency (alpha) to a color.
     *
     * @param color The base color.
     * @param alpha The transparency amount between 0.0 and 1.0.
     * @return The new color.
     */
    public static Color fade( Color color, double alpha ) {
        return new Color( 
            color.getRed(), color.getGreen(), color.getBlue(), 
            MathUtils.clamp( (int) ( 255 * alpha ), 0, 255 ) );
    }

    /**
     * Gets the HSV (hue/saturation/value) values of a color.
     * The ranges are h: [0..360], s: [0..1] and v: [0..1].
     *
     * @param color A color.
     * @return The HSV values as a three-element array, in order.
     */
    public static double[] colorToHSV( Color color ) {
        float[] hsb = Color.RGBtoHSB( color.getRed(), color.getGreen(), color.getBlue(), null );
        return new double[] { hsb[0] * 360, hsb[1], hsb[2] };
    }

    /**
     * Gets a color from HSV (hue/saturation/value) values.
     * The ranges are h: [0..360], s: [0..1] and v: [0..1].
     *
     * @param hue hue [0..360]
     * @param saturation saturation [0..1]
     * @param value value [0..1]
     * @return A color with those parameters.
     */
    public static Color colorFromHSV( double hue, double saturation, double value ) {
        return new Color( Color.HSBtoRGB( (float) ( hue / 360.0 ), (float) saturation, (float) value ) );
    }

    /**
     * Multiplies a color by a tint.
     *
     * @param color The base color.
     * @param tint The tint.
     * @return A new color multiplied by the tint.
     */
    public static Color colorTint( Color color, Color tint ) {
        return new Color( 
            color.getRed() * tint.getRed() / 255,
            color.getGreen() * tint.getGreen() / 255,
            color.getBlue() * tint.getBlue() / 255,
            color.getAlpha() * tint.getAlpha() / 255
        );
    }
    
    /**
     * Generates the inverted color of the given color.
     *
     * @param color The color.
     * @return The inverted color.
     */
    public static Color colorInvert( Color color ) {
        return new Color( 
            255 - color.getRed(), 
            255 - color.getGreen(), 
            255 - color.getBlue(), 
            color.getAlpha()
        );
    }
    
    /**
     * Generates the grayscale version of the given color.
     *
     * @param color The color.
     * @return The grayscale color.
     */
    public static Color colorGrayscale( Color color ) {
        int gray = ( color.getRed() + color.getGreen() + color.getBlue() ) / 3;
        return new Color( gray, gray, gray, color.getAlpha() );
    }

    /**
     * Gets a color with brightness correction. The brightness factor ranges
     * from -1.0 to 1.0.
     *
     * @param color A color.
     * @param brightness The brightness factor from -1.0 to 1.0.
     * @return A new corrected color.
     */
    public static Color colorBrightness( Color color, double brightness ) {

        if ( brightness > 1.0 ) brightness = 1.0;
        else if ( brightness < -1.0 ) brightness = -1.0 ;

        double red = color.getRed();
        double green = color.getGreen();
        double blue = color.getBlue();

        if ( brightness < 0.0 ) {
            brightness = 1.0 + brightness;
            red *= brightness;
            green *= brightness;
            blue *= brightness;
        } else {
            red = ( 255 - red ) * brightness + red;
            green = ( 255 - green ) * brightness + green;
            blue = ( 255 - blue ) * brightness + blue;
        }

        return new Color( (int) red, (int) green, (int) blue, color.getAlpha() );

    }

    /**
     * Gets a color with contrast correction. The contrast factor ranges
     * from -1.0 to 1.0.
     *
     * @param color A color.
     * @param contrast The contrast factor from -1.0 to 1.0.
     * @return A new corrected color.
     */
    public static Color colorContrast( Color color, double contrast ) {

        if ( contrast < -1.0 ) contrast = -1.0;
        else if ( contrast > 1.0 ) contrast = 1.0;

        contrast = ( 1.0 + contrast );
        contrast *= contrast;

        double pR = color.getRed() / 255.0;
        pR -= 0.5;
        pR *= contrast;
        pR += 0.5;
        pR *= 255;
        if ( pR < 0 ) pR = 0;
        else if ( pR > 255 ) pR = 255;

        double pG = color.getGreen() / 255.0;
        pG -= 0.5;
        pG *= contrast;
        pG += 0.5;
        pG *= 255;
        if ( pG < 0 ) pG = 0;
        else if ( pG > 255 ) pG = 255;

        double pB = color.getBlue() / 255.0f;
        pB -= 0.5;
        pB *= contrast;
        pB += 0.5;
        pB *= 255;
        if ( pB < 0 ) pB = 0;
        else if ( pB > 255 ) pB = 255;

        return new Color( (int) pR, (int) pG, (int) pB, color.getAlpha() );

    }

    /**
     * Applies transparency (alpha) to a color.
     *
     * @param color The base color.
     * @param alpha The transparency amount between 0.0 and 1.0.
     * @return The new color.
     */
    public static Color colorAlpha( Color color, double alpha ) {
        return fade( color, alpha );
    }
    
    /**
     * Gets a color from a hexadecimal integer in the form 0xAARRGGBB, where:
     *     AA: alpha channel from 00 to FF;
     *     RR: red channel from 00 to FF;
     *     GG: green channel from 00 to FF;
     *     BB: blue channel from 00 to FF;
     *
     * Example:
     *     0xFF00FF00: green with maximum alpha (no transparency)
     *
     * @param hexValue A hexadecimal integer value.
     * @return The color corresponding to the given integer.
     */
    public static Color getColor( int hexValue ) {
        return new Color( hexValue, true );
    }

    /**
     * Performs linear interpolation between two colors.
     *
     * @param start starting color.
     * @param end ending color.
     * @param amount amount (0 to 1).
     * @return A color representing the linear interpolation between the two colors.
     */
    public static Color lerp( Color start, Color end, double amount ) {
        int r = (int) MathUtils.clamp( MathUtils.lerp( start.getRed(), end.getRed(), amount ), 0, 255 );
        int g = (int) MathUtils.clamp( MathUtils.lerp( start.getGreen(), end.getGreen(), amount ), 0, 255 );
        int b = (int) MathUtils.clamp( MathUtils.lerp( start.getBlue(), end.getBlue(), amount ), 0, 255 );
        int a = (int) MathUtils.clamp( MathUtils.lerp( start.getAlpha(), end.getAlpha(), amount ), 0, 255 );
        return new Color( r, g, b, a );
    }

}
