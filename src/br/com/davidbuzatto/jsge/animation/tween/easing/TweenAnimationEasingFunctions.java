/*
 * Copyright (C) 2025 Prof. Dr. David Buzatto
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
package br.com.davidbuzatto.jsge.animation.tween.easing;

import java.util.function.DoubleFunction;

/**
 * Interface que contém funções de suavização modeladas como DoubleFunctions.
 * Referência: https://easings.net/
 *
 * @author Prof. Dr. David Buzatto
 */
public interface TweenAnimationEasingFunctions {

    /***
     * Função de suavização.
     */
    public static final DoubleFunction<Double> easeInSine = ( x ) -> {
        return 1 - Math.cos( ( x * Math.PI ) / 2 );
    };

    /***
     * Função de suavização.
     */
    public static final DoubleFunction<Double> easeOutSine = ( x ) -> {
        return Math.sin( ( x * Math.PI ) / 2 );
    };

    /***
     * Função de suavização.
     */
    public static final DoubleFunction<Double> easeInOutSine = ( x ) -> {
        return -( Math.cos( Math.PI * x ) - 1 ) / 2;
    };

    /***
     * Função de suavização.
     */
    public static final DoubleFunction<Double> easeInQuad = ( x ) -> {
        return x * x;
    };

    /***
     * Função de suavização.
     */
    public static final DoubleFunction<Double> easeOutQuad = ( x ) -> {
        return 1 - ( 1 - x ) * ( 1 - x );
    };

    /***
     * Função de suavização.
     */
    public static final DoubleFunction<Double> easeInOutQuad = ( x ) -> {
        return x < 0.5 ? 2 * x * x : 1 - Math.pow( -2 * x + 2, 2 ) / 2;
    };

    /***
     * Função de suavização.
     */
    public static final DoubleFunction<Double> easeInCubic = ( x ) -> {
        return x * x * x;
    };

    /***
     * Função de suavização.
     */
    public static final DoubleFunction<Double> easeOutCubic = ( x ) -> {
        return 1 - Math.pow( 1 - x, 3 );
    };

    /***
     * Função de suavização.
     */
    public static final DoubleFunction<Double> easeInOutCubic = ( x ) -> {
        return x < 0.5 ? 4 * x * x * x : 1 - Math.pow( -2 * x + 2, 3 ) / 2;
    };

    /***
     * Função de suavização.
     */
    public static final DoubleFunction<Double> easeInQuart = ( x ) -> {
        return x * x * x * x;
    };

    /***
     * Função de suavização.
     */
    public static final DoubleFunction<Double> easeOutQuart = ( x ) -> {
        return 1 - Math.pow( 1 - x, 4 );
    };

    /***
     * Função de suavização.
     */
    public static final DoubleFunction<Double> easeInOutQuart = ( x ) -> {
        return x < 0.5 ? 8 * x * x * x * x : 1 - Math.pow( -2 * x + 2, 4 ) / 2;
    };

    /***
     * Função de suavização.
     */
    public static final DoubleFunction<Double> easeInQuint = ( x ) -> {
        return x * x * x * x * x;
    };

    /***
     * Função de suavização.
     */
    public static final DoubleFunction<Double> easeOutQuint = ( x ) -> {
        return 1 - Math.pow( 1 - x, 5 );
    };

    /***
     * Função de suavização.
     */
    public static final DoubleFunction<Double> easeInOutQuint = ( x ) -> {
        return x < 0.5 ? 16 * x * x * x * x * x : 1 - Math.pow( -2 * x + 2, 5 ) / 2;
    };

    /***
     * Função de suavização.
     */
    public static final DoubleFunction<Double> easeInEtpo = ( x ) -> {
        return x == 0 ? 0 : Math.pow( 2, 10 * x - 10 );
    };

    /***
     * Função de suavização.
     */
    public static final DoubleFunction<Double> easeOutEtpo = ( x ) -> {
        return x == 1 ? 1 : 1 - Math.pow( 2, -10 * x );
    };

    /***
     * Função de suavização.
     */
    public static final DoubleFunction<Double> easeInOutEtpo = ( x ) -> {
        return x == 0
            ? 0
            : x == 1
                ? 1
                : x < 0.5 ? Math.pow( 2, 20 * x - 10 ) / 2
                    : ( 2 - Math.pow( 2, -20 * x + 10 ) ) / 2;
    };

    /***
     * Função de suavização.
     */
    public static final DoubleFunction<Double> easeInCirc = ( x ) -> {
        return 1 - Math.sqrt( 1 - Math.pow( x, 2 ) );
    };

    /***
     * Função de suavização.
     */
    public static final DoubleFunction<Double> easeOutCirc = ( x ) -> {
        return Math.sqrt( 1 - Math.pow( x - 1, 2 ) );
    };

    /***
     * Função de suavização.
     */
    public static final DoubleFunction<Double> easeInOutCirc = ( x ) -> {
        return x < 0.5
            ? ( 1 - Math.sqrt( 1 - Math.pow( 2 * x, 2 ) ) ) / 2
            : ( Math.sqrt( 1 - Math.pow( -2 * x + 2, 2 ) ) + 1 ) / 2;
    };

    /***
     * Função de suavização.
     */
    public static final DoubleFunction<Double> easeInBack = ( x ) -> {
        double c1 = 1.70158;
        double c3 = c1 + 1;

        return c3 * x * x * x - c1 * x * x;
    };

    /***
     * Função de suavização.
     */
    public static final DoubleFunction<Double> easeOutBack = ( x ) -> {
        double c1 = 1.70158;
        double c3 = c1 + 1;

        return 1 + c3 * Math.pow( x - 1, 3 ) + c1 * Math.pow( x - 1, 2 );
    };

    /***
     * Função de suavização.
     */
    public static final DoubleFunction<Double> easeInOutBack = ( x ) -> {
        double c1 = 1.70158;
        double c2 = c1 * 1.525;

        return x < 0.5
            ? ( Math.pow( 2 * x, 2 ) * ( ( c2 + 1 ) * 2 * x - c2 ) ) / 2
            : ( Math.pow( 2 * x - 2, 2 ) * ( ( c2 + 1 ) * ( x * 2 - 2 ) + c2 ) + 2 ) / 2;
    };

    /***
     * Função de suavização.
     */
    public static final DoubleFunction<Double> easeInElastic = ( x ) -> {
        double c4 = ( 2 * Math.PI ) / 3;
        return x == 0
            ? 0
            : x == 1
                ? 1
                : -Math.pow( 2, 10 * x - 10 ) * Math.sin( ( x * 10 - 10.75 ) * c4 );
    };

    /***
     * Função de suavização.
     */
    public static final DoubleFunction<Double> easeOutElastic = ( x ) -> {
        double c4 = ( 2 * Math.PI ) / 3;

        return x == 0
            ? 0
            : x == 1
                ? 1
                : Math.pow( 2, -10 * x ) * Math.sin( ( x * 10 - 0.75 ) * c4 ) + 1;
    };

    /***
     * Função de suavização.
     */
    public static final DoubleFunction<Double> easeInOutElastic = ( x ) -> {
        double c5 = ( 2 * Math.PI ) / 4.5;
        return x == 0
            ? 0
            : x == 1
                ? 1
                : x < 0.5
                    ? -( Math.pow( 2, 20 * x - 10 ) * Math.sin( ( 20 * x - 11.125 ) * c5 ) ) / 2
                    : ( Math.pow( 2, -20 * x + 10 ) * Math.sin( ( 20 * x - 11.125 ) * c5 ) ) / 2 + 1;
    };

    /***
     * Função de suavização.
     */
    public static final DoubleFunction<Double> easeInBounce = ( x ) -> {
        return 1 - TweenAnimationEasingFunctions.easeOutBounce.apply( 1 - x );
    };

    /***
     * Função de suavização.
     */
    public static final DoubleFunction<Double> easeOutBounce = ( x ) -> {
        double n1 = 7.5625;
        double d1 = 2.75;
        if ( x < 1 / d1 ) {
            return n1 * x * x;
        } else if ( x < 2 / d1 ) {
            return n1 * ( x -= 1.5 / d1 ) * x + 0.75;
        } else if ( x < 2.5 / d1 ) {
            return n1 * ( x -= 2.25 / d1 ) * x + 0.9375;
        } else {
            return n1 * ( x -= 2.625 / d1 ) * x + 0.984375;
        }
    };

    /***
     * Função de suavização.
     */
    public static final DoubleFunction<Double> easeInOutBounce = ( x ) -> {
        return x < 0.5
            ? ( 1 - TweenAnimationEasingFunctions.easeOutBounce.apply( 1 - 2 * x ) ) / 2
            : ( 1 + TweenAnimationEasingFunctions.easeOutBounce.apply( 2 * x - 1 ) ) / 2;
    };
    
}
