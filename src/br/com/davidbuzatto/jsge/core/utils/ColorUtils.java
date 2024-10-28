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
package br.com.davidbuzatto.jsge.core.utils;

import br.com.davidbuzatto.jsge.math.MathUtils;
import java.awt.Color;

/**
 * Classe com métodos estáticos utilitários relacionados às cores.
 * 
 * Várias implementações são baseadas na raylib e em seus módulos
 * (www.raylib.com).
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ColorUtils {
    
    /**
     * Aplica transparência (alpha) na cor.
     * 
     * @param color A cor base.
     * @param alpha A quantidade de transparência entre 0.0 e 1.0
     * @return A nova cor.
     */
    public static Color fade( Color color, double alpha ) {
        return new Color( 
            color.getRed(), color.getGreen(), color.getBlue(), 
            MathUtils.clamp( (int) ( 255 * alpha ), 0, 255 ) );
    }

    /**
     * Obtém os valores HSV (hue/saturation/value - matiz/saturação/valor) de uma
     * cor. Os intervalos são h: [0..360], s: [0..1] e v: [0..1].
     * 
     * @param color Uma cor.
     * @return Os valores HSV na forma de uma array de três elementos. Os valores
     * seguem na ordem.
     */
    public static double[] colorToHSV( Color color ) {
        float[] hsb = Color.RGBtoHSB( color.getRed(), color.getGreen(), color.getBlue(), null );
        return new double[] { hsb[0] * 360, hsb[1], hsb[2] };
    }

    /**
     * Obtém uma cor a par dos valores HSV (hue/saturation/value - matiz/saturação/valor).
     * Os intervalos são h: [0..360], s: [0..1] e v: [0..1].
     * 
     * @param hue matiz [0..360]
     * @param saturation [0..1]
     * @param value [0..1]
     * @return Uma cor com tais parâmetros.
     */
    public static Color colorFromHSV( double hue, double saturation, double value ) {
        return new Color( Color.HSBtoRGB( (float) ( hue / 360.0 ), (float) saturation, (float) value ) );
    }

    /**
     * Multiplica uma cor por uma tonalidade.
     * 
     * @param color A cor base.
     * @param tint A tonalidade.
     * @return Uma nova cor multiplicada pela tonalidade.
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
     * Obtém uma cor com correção em relação ao brilho. O fator de brilho vai de 
     * -1.0 a 1.0.
     * 
     * @param color Uma cor.
     * @param brightness O fator de brilho de -1.0 a 1.0.
     * @return Uma nova cor corrigida.
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
     * Obtém uma cor com correção em relação ao contraste. O fator de contraste vai de 
     * -1.0 a 1.0.
     * 
     * @param color Uma cor.
     * @param contrast O fator de contraste de -1.0 a 1.0.
     * @return Uma nova cor corrigida.
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
     * Aplica transparência (alpha) na cor.
     * 
     * @param color A cor base.
     * @param alpha A quantidade de transparência entre 0.0 e 1.0
     * @return A nova cor.
     */
    public static Color colorAlpha( Color color, double alpha ) {
        return fade( color, alpha );
    }

    // Get Color structure from hexadecimal value
    // 0xAARRGGBB
    /**
     * Obtém uma cor a partir de um inteido em hexadecimal na forma 0xAARRGGBB, onde:
     *     AA: canal alfa de 00 a FF;
     *     RR: canal vermelho de 00 a FF;
     *     GG: canal verde de 00 a FF;
     *     BB: canal azul de 00 a FF;
     * 
     * Exemplo:
     *     0xFF00FF00: verde com alfa máximo (sem transparência)
     * 
     * @param hexValue Um valor inteiro em hexadecimal.
     * @return A cor relativa ao inteiro passado.
     */
    public static Color getColor( int hexValue ) {
        return new Color( hexValue, true );
    }

    /**
     * Realiza a interpolação linear entre duas cores.
     * 
     * @param start cor inicial.
     * @param end cor final.
     * @param amount quantidade (0 a 1)
     * @return Uma cor que representa a interpolação linear entre duas cores pontos.
     */
    public static Color lerp( Color start, Color end, double amount ) {
        int r = (int) MathUtils.clamp( MathUtils.lerp( start.getRed(), end.getRed(), amount ), 0, 255 );
        int g = (int) MathUtils.clamp( MathUtils.lerp( start.getGreen(), end.getGreen(), amount ), 0, 255 );
        int b = (int) MathUtils.clamp( MathUtils.lerp( start.getBlue(), end.getBlue(), amount ), 0, 255 );
        int a = (int) MathUtils.clamp( MathUtils.lerp( start.getAlpha(), end.getAlpha(), amount ), 0, 255 );
        return new Color( r, g, b, a );
    }

}
