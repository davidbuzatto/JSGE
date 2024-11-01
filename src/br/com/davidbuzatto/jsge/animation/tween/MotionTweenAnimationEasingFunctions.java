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
package br.com.davidbuzatto.jsge.animation.tween;

import java.util.function.DoubleFunction;

/**
 * Classe que contém funções de suavização modeladas como DoubleFunctions.
 * Referência: https://easings.net/
 *
 * @author Prof. Dr. David Buzatto
 */
public class MotionTweenAnimationEasingFunctions {

    public static DoubleFunction<Double> easeInSine = ( t ) -> {
        return 1 - Math.cos( ( t * Math.PI ) / 2 );
    };

    public static DoubleFunction<Double> easeOutSine = ( t ) -> {
        return Math.sin( ( t * Math.PI ) / 2 );
    };

    public static DoubleFunction<Double> easeInOutSine = ( t ) -> {
        return -( Math.cos( Math.PI * t ) - 1 ) / 2;
    };

    public static DoubleFunction<Double> easeInQuad = ( t ) -> {
        return t * t;
    };

    public static DoubleFunction<Double> easeOutQuad = ( t ) -> {
        return 1 - ( 1 - t ) * ( 1 - t );
    };

    public static DoubleFunction<Double> easeInOutQuad = ( t ) -> {
        return t < 0.5 ? 2 * t * t : 1 - Math.pow( -2 * t + 2, 2 ) / 2;
    };

    public static DoubleFunction<Double> easeInCubic = ( t ) -> {
        return t * t * t;
    };

    public static DoubleFunction<Double> easeOutCubic = ( t ) -> {
        return 1 - Math.pow( 1 - t, 3 );
    };

    public static DoubleFunction<Double> easeInOutCubic = ( t ) -> {
        return t < 0.5 ? 4 * t * t * t : 1 - Math.pow( -2 * t + 2, 3 ) / 2;
    };

    public static DoubleFunction<Double> easeInQuart = ( t ) -> {
        return t * t * t * t;
    };

    public static DoubleFunction<Double> easeOutQuart = ( t ) -> {
        return 1 - Math.pow( 1 - t, 4 );
    };

    public static DoubleFunction<Double> easeInOutQuart = ( t ) -> {
        return t < 0.5 ? 8 * t * t * t * t : 1 - Math.pow( -2 * t + 2, 4 ) / 2;
    };

    public static DoubleFunction<Double> easeInQuint = ( t ) -> {
        return t * t * t * t * t;
    };

    public static DoubleFunction<Double> easeOutQuint = ( t ) -> {
        return 1 - Math.pow( 1 - t, 5 );
    };

    public static DoubleFunction<Double> easeInOutQuint = ( t ) -> {
        return t < 0.5 ? 16 * t * t * t * t * t : 1 - Math.pow( -2 * t + 2, 5 ) / 2;
    };

    public static DoubleFunction<Double> easeInEtpo = ( t ) -> {
        return t == 0 ? 0 : Math.pow( 2, 10 * t - 10 );
    };

    public static DoubleFunction<Double> easeOutEtpo = ( t ) -> {
        return t == 1 ? 1 : 1 - Math.pow( 2, -10 * t );
    };

    public static DoubleFunction<Double> easeInOutEtpo = ( t ) -> {
        return t == 0
            ? 0
            : t == 1
                ? 1
                : t < 0.5 ? Math.pow( 2, 20 * t - 10 ) / 2
                    : ( 2 - Math.pow( 2, -20 * t + 10 ) ) / 2;
    };

    public static DoubleFunction<Double> easeInCirc = ( t ) -> {
        return 1 - Math.sqrt( 1 - Math.pow( t, 2 ) );
    };

    public static DoubleFunction<Double> easeOutCirc = ( t ) -> {
        return Math.sqrt( 1 - Math.pow( t - 1, 2 ) );
    };

    public static DoubleFunction<Double> easeInOutCirc = ( t ) -> {
        return t < 0.5
            ? ( 1 - Math.sqrt( 1 - Math.pow( 2 * t, 2 ) ) ) / 2
            : ( Math.sqrt( 1 - Math.pow( -2 * t + 2, 2 ) ) + 1 ) / 2;
    };

    public static DoubleFunction<Double> easeInBack = ( t ) -> {
        double c1 = 1.70158;
        double c3 = c1 + 1;

        return c3 * t * t * t - c1 * t * t;
    };

    public static DoubleFunction<Double> easeOutBack = ( t ) -> {
        double c1 = 1.70158;
        double c3 = c1 + 1;

        return 1 + c3 * Math.pow( t - 1, 3 ) + c1 * Math.pow( t - 1, 2 );
    };

    public static DoubleFunction<Double> easeInOutBack = ( t ) -> {
        double c1 = 1.70158;
        double c2 = c1 * 1.525;

        return t < 0.5
            ? ( Math.pow( 2 * t, 2 ) * ( ( c2 + 1 ) * 2 * t - c2 ) ) / 2
            : ( Math.pow( 2 * t - 2, 2 ) * ( ( c2 + 1 ) * ( t * 2 - 2 ) + c2 ) + 2 ) / 2;
    };

    public static DoubleFunction<Double> easeInElastic = ( t ) -> {
        double c4 = ( 2 * Math.PI ) / 3;
        return t == 0
            ? 0
            : t == 1
                ? 1
                : -Math.pow( 2, 10 * t - 10 ) * Math.sin( ( t * 10 - 10.75 ) * c4 );
    };

    public static DoubleFunction<Double> easeOutElastic = ( t ) -> {
        double c4 = ( 2 * Math.PI ) / 3;

        return t == 0
            ? 0
            : t == 1
                ? 1
                : Math.pow( 2, -10 * t ) * Math.sin( ( t * 10 - 0.75 ) * c4 ) + 1;
    };

    public static DoubleFunction<Double> easeInOutElastic = ( t ) -> {
        double c5 = ( 2 * Math.PI ) / 4.5;
        return t == 0
            ? 0
            : t == 1
                ? 1
                : t < 0.5
                    ? -( Math.pow( 2, 20 * t - 10 ) * Math.sin( ( 20 * t - 11.125 ) * c5 ) ) / 2
                    : ( Math.pow( 2, -20 * t + 10 ) * Math.sin( ( 20 * t - 11.125 ) * c5 ) ) / 2 + 1;
    };

    public static DoubleFunction<Double> easeInBounce = ( t ) -> {
        return 1 - MotionTweenAnimationEasingFunctions.easeOutBounce.apply( 1 - t );
    };

    public static DoubleFunction<Double> easeOutBounce = ( t ) -> {
        double n1 = 7.5625;
        double d1 = 2.75;
        if ( t < 1 / d1 ) {
            return n1 * t * t;
        } else if ( t < 2 / d1 ) {
            return n1 * ( t -= 1.5 / d1 ) * t + 0.75;
        } else if ( t < 2.5 / d1 ) {
            return n1 * ( t -= 2.25 / d1 ) * t + 0.9375;
        } else {
            return n1 * ( t -= 2.625 / d1 ) * t + 0.984375;
        }
    };

    public static DoubleFunction<Double> easeInOutBounce = ( t ) -> {
        return t < 0.5
            ? ( 1 - MotionTweenAnimationEasingFunctions.easeOutBounce.apply( 1 - 2 * t ) ) / 2
            : ( 1 + MotionTweenAnimationEasingFunctions.easeOutBounce.apply( 2 * t - 1 ) ) / 2;
    };
    
}
