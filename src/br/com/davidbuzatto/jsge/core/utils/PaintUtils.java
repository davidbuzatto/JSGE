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

import br.com.davidbuzatto.jsge.geom.Circle;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.image.Image;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;

/**
 * Classe com métodos estáticos utilitários criação de diversos tipos de Paints.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class PaintUtils {
    
    /**
     * Enumeração para os tipos de cyclos para os gradientes.
     */
    public static enum CycleMethod {
        
        /**
         * Sem ciclo.
         */
        NO_CYCLE( MultipleGradientPaint.CycleMethod.NO_CYCLE ),
        
        /**
         * Ciclo em reflexo.
         */
        REFLECT( MultipleGradientPaint.CycleMethod.REFLECT ),
        
        /**
         * Ciclo em repetição.
         */
        REPEAT( MultipleGradientPaint.CycleMethod.REPEAT );
        
        private final MultipleGradientPaint.CycleMethod method;
        
        private CycleMethod( MultipleGradientPaint.CycleMethod method ) {
            this.method = method;
        }
        
    }
    
    
    
    //**************************************************************************
    // Paints de gradiente (GradientPaint).
    //**************************************************************************
    /**
     * Cria um paint do tipo gradiente de duas cores com início e fim.
     * 
     * @param startX Coordenada x do ponto inicial.
     * @param startY Coordenada y do ponto inicial.
     * @param endX Coordenada x do ponto final.
     * @param endY Coordenada y do ponto final.
     * @param startColor Cor inicial.
     * @param endColor Cor final.
     * @param cyclic Se o gradiente é cíclico.
     * @return Um paint do tipo gradiente.
     */
    public static Paint getGradientPaint( double startX, double startY, double endX, double endY, Color startColor, Color endColor, boolean cyclic ) {
        return new GradientPaint( (float) startX, (float) startY, startColor, (float) endX, (float) endY, endColor, cyclic );
    }
    
    /**
     * Cria um paint do tipo gradiente acíclico de duas cores com início e fim.
     * 
     * @param startX Coordenada x do ponto inicial.
     * @param startY Coordenada y do ponto inicial.
     * @param endX Coordenada x do ponto final.
     * @param endY Coordenada y do ponto final.
     * @param startColor Cor inicial.
     * @param endColor Cor final.
     * @return Um paint do tipo gradiente acíclico.
     */
    public static Paint getGradientPaint( double startX, double startY, double endX, double endY, Color startColor, Color endColor ) {
        return new GradientPaint( (float) startX, (float) startY, startColor, (float) endX, (float) endY, endColor, false );
    }
    
    /**
     * Cria um paint do tipo gradiente de duas cores com início e fim.
     * 
     * @param start Ponto inicial.
     * @param end Ponto final.
     * @param startColor Cor inicial.
     * @param endColor Cor final.
     * @param cyclic Se o gradiente é cíclico.
     * @return Um paint do tipo gradiente.
     */
    public static Paint getGradientPaint( Vector2 start, Vector2 end, Color startColor, Color endColor, boolean cyclic ) {
        return getGradientPaint( start.x, start.y, end.x, end.y, startColor, endColor, cyclic );
    }
    
    /**
     * Cria um paint do tipo gradiente acíclico de duas cores com início e fim.
     * 
     * @param start Ponto inicial.
     * @param end Ponto final.
     * @param startColor Cor inicial.
     * @param endColor Cor final.
     * @return Um paint do tipo gradiente acíclico.
     */
    public static Paint getGradientPaint( Vector2 start, Vector2 end, Color startColor, Color endColor ) {
        return getGradientPaint( start.x, start.y, end.x, end.y, startColor, endColor, false );
    }
    
    
    
    //**************************************************************************
    // Paints de gradiente horizontal (GradientPaint).
    //**************************************************************************
    /**
     * Cria um gradiente horizontal de duas cores.
     * 
     * @param x Coordenada x do ponto inicial.
     * @param y Coordenada y do ponto inicial.
     * @param width Largura.
     * @param height Altura.
     * @param startColor Cor inicial.
     * @param endColor Cor final.
     * @param cyclic Se o gradiente é cíclico.
     * @return Um gradiente horizontal de duas cores.
     */
    public static Paint getHorizontalGradientPaint( double x, double y, double width, double height, Color startColor, Color endColor, boolean cyclic ) {
        return new GradientPaint( (float) x, (float) (y + height / 2), startColor, (float) (x + width), (float) (y + height / 2), endColor, cyclic );
    }
    
    /**
     * Cria um gradiente horizontal acíclico de duas cores.
     * 
     * @param x Coordenada x do ponto inicial.
     * @param y Coordenada y do ponto inicial.
     * @param width Largura.
     * @param height Altura.
     * @param startColor Cor inicial.
     * @param endColor Cor final.
     * @return Um gradiente horizontal acíclico de duas cores.
     */
    public static Paint getHorizontalGradientPaint( double x, double y, double width, double height, Color startColor, Color endColor ) {
        return getHorizontalGradientPaint( x, y, width, height, startColor, endColor, false );
    }
    
    /**
     * Cria um gradiente horizontal de duas cores.
     * 
     * @param pos Ponto inicial.
     * @param dim Dimensão do gradiente (largura e altura).
     * @param startColor Cor inicial.
     * @param endColor Cor final.
     * @param cyclic Se o gradiente é cíclico.
     * @return Um gradiente horizontal de duas cores.
     */
    public static Paint getHorizontalGradientPaint( Vector2 pos, Vector2 dim, Color startColor, Color endColor, boolean cyclic ) {
        return getHorizontalGradientPaint( pos.x, pos.y, dim.x, dim.y, startColor, endColor, cyclic );
    }
    
    /**
     * Cria um gradiente horizontal acíclico de duas cores.
     * 
     * @param pos Ponto inicial.
     * @param dim Dimensão do gradiente (largura e altura).
     * @param startColor Cor inicial.
     * @param endColor Cor final.
     * @return Um gradiente horizontal acíclico de duas cores.
     */
    public static Paint getHorizontalGradientPaint( Vector2 pos, Vector2 dim, Color startColor, Color endColor ) {
        return getHorizontalGradientPaint( pos.x, pos.y, dim.x, dim.y, startColor, endColor, false );
    }
    
    /**
     * Cria um gradiente horizontal de duas cores.
     * 
     * @param rect Retângulo do gradiente.
     * @param startColor Cor inicial.
     * @param endColor Cor final.
     * @param cyclic Se o gradiente é cíclico.
     * @return Um gradiente horizontal de duas cores.
     */
    public static Paint getHorizontalGradientPaint( Rectangle rect, Color startColor, Color endColor, boolean cyclic ) {
        return getHorizontalGradientPaint( rect.x, rect.y, rect.width, rect.height, startColor, endColor, cyclic );
    }
    
    /**
     * Cria um gradiente horizontal acíclico de duas cores.
     * 
     * @param rect Retângulo do gradiente.
     * @param startColor Cor inicial.
     * @param endColor Cor final.
     * @return Um gradiente horizontal acíclico de duas cores.
     */
    public static Paint getHorizontalGradientPaint( Rectangle rect, Color startColor, Color endColor ) {
        return getHorizontalGradientPaint( rect.x, rect.y, rect.width, rect.height, startColor, endColor, false );
    }
    
    
    
    //**************************************************************************
    // Paints de gradiente vertical (GradientPaint).
    //**************************************************************************
    /**
     * Cria um gradiente vertical de duas cores.
     * 
     * @param x Coordenada x do ponto inicial.
     * @param y Coordenada y do ponto inicial.
     * @param width Largura.
     * @param height Altura.
     * @param startColor Cor inicial.
     * @param endColor Cor final.
     * @param cyclic Se o gradiente é cíclico.
     * @return Um gradiente vertical de duas cores.
     */
    public static Paint getVerticalGradientPaint( double x, double y, double width, double height, Color startColor, Color endColor, boolean cyclic ) {
        return new GradientPaint( (float) (x + width / 2), (float) y, startColor, (float) (x + width / 2), (float) (y + height), endColor, cyclic );
    }
    
    /**
     * Cria um gradiente vertical acíclico de duas cores.
     * 
     * @param x Coordenada x do ponto inicial.
     * @param y Coordenada y do ponto inicial.
     * @param width Largura.
     * @param height Altura.
     * @param startColor Cor inicial.
     * @param endColor Cor final.
     * @return Um gradiente vertical acíclico de duas cores.
     */
    public static Paint getVerticalGradientPaint( double x, double y, double width, double height, Color startColor, Color endColor ) {
        return getVerticalGradientPaint( x, y, width, height, startColor, endColor, false );
    }
    
    /**
     * Cria um gradiente vertical de duas cores.
     * 
     * @param pos Ponto inicial.
     * @param dim Dimensão do gradiente (largura e altura).
     * @param startColor Cor inicial.
     * @param endColor Cor final.
     * @param cyclic Se o gradiente é cíclico.
     * @return Um gradiente vertical de duas cores.
     */
    public static Paint getVerticalGradientPaint( Vector2 pos, Vector2 dim, Color startColor, Color endColor, boolean cyclic ) {
        return getVerticalGradientPaint( pos.x, pos.y, dim.x, dim.y, startColor, endColor, cyclic );
    }
    
    /**
     * Cria um gradiente vertical acíclico de duas cores.
     * 
     * @param pos Ponto inicial.
     * @param dim Dimensão do gradiente (largura e altura).
     * @param startColor Cor inicial.
     * @param endColor Cor final.
     * @return Um gradiente vertical acíclico de duas cores.
     */
    public static Paint getVerticalGradientPaint( Vector2 pos, Vector2 dim, Color startColor, Color endColor ) {
        return getVerticalGradientPaint( pos.x, pos.y, dim.x, dim.y, startColor, endColor, false );
    }
    
    /**
     * Cria um gradiente vertical de duas cores.
     * 
     * @param rect Retângulo do gradiente.
     * @param startColor Cor inicial.
     * @param endColor Cor final.
     * @param cyclic Se o gradiente é cíclico.
     * @return Um gradiente vertical de duas cores.
     */
    public static Paint getVerticalGradientPaint( Rectangle rect, Color startColor, Color endColor, boolean cyclic ) {
        return getVerticalGradientPaint( rect.x, rect.y, rect.width, rect.height, startColor, endColor, cyclic );
    }
    
    /**
     * Cria um gradiente vertical acíclico de duas cores.
     * 
     * @param rect Retângulo do gradiente.
     * @param startColor Cor inicial.
     * @param endColor Cor final.
     * @return Um gradiente vertical acíclico de duas cores.
     */
    public static Paint getVerticalGradientPaint( Rectangle rect, Color startColor, Color endColor ) {
        return getVerticalGradientPaint( rect.x, rect.y, rect.width, rect.height, startColor, endColor, false );
    }
    
    
    
    //**************************************************************************
    // Paints de gradiente linear (LinearGradientPaint).
    //**************************************************************************
    /**
     * Cria um gradiente linear de várias cores.
     * 
     * @param startX Coordenada x do ponto inicial.
     * @param startY Coordenada y do ponto inicial.
     * @param endX Coordenada x do ponto final.
     * @param endY Coordenada y do ponto final.
     * @param fractions Posições percentuais das fronteiras das cores.
     * @param colors Cores.
     * @param cycleMethod Qual tipo de ciclo deve ser empregado.
     * @return Um gradiente linear.
     */
    public static Paint getLinearGradientPaint( double startX, double startY, double endX, double endY, float[] fractions, Color[] colors, CycleMethod cycleMethod ) {
        return new LinearGradientPaint( (float) startX, (float) startY, (float) endX, (float) endY, fractions, colors, cycleMethod.method );
    }
    
    /**
     * Cria um gradiente linear acíclico de várias cores.
     * 
     * @param startX Coordenada x do ponto inicial.
     * @param startY Coordenada y do ponto inicial.
     * @param endX Coordenada x do ponto final.
     * @param endY Coordenada y do ponto final.
     * @param fractions Posições percentuais das fronteiras das cores.
     * @param colors Cores.
     * @return Um gradiente linear acíclico.
     */
    public static Paint getLinearGradientPaint( double startX, double startY, double endX, double endY, float[] fractions, Color[] colors ) {
        return getLinearGradientPaint( startX, startY, endX, endY, fractions, colors, CycleMethod.NO_CYCLE );
    }
    
    /**
     * Cria um gradiente linear de várias cores.
     * 
     * @param start Ponto inicial.
     * @param end Ponto final.
     * @param fractions Posições percentuais das fronteiras das cores.
     * @param colors Cores.
     * @param cycleMethod Qual tipo de ciclo deve ser empregado.
     * @return Um gradiente linear.
     */
    public static Paint getLinearGradientPaint( Vector2 start, Vector2 end, float[] fractions, Color[] colors, CycleMethod cycleMethod ) {
        return getLinearGradientPaint( start.x, start.y, end.x, end.y, fractions, colors, cycleMethod );
    }
    
    /**
     * Cria um gradiente linear acíclico de várias cores.
     * 
     * @param start Ponto inicial.
     * @param end Ponto final.
     * @param fractions Posições percentuais das fronteiras das cores.
     * @param colors Cores.
     * @return Um gradiente linear acíclico.
     */
    public static Paint getLinearGradientPaint( Vector2 start, Vector2 end, float[] fractions, Color[] colors ) {
        return getLinearGradientPaint( start.x, start.y, end.x, end.y, fractions, colors, CycleMethod.NO_CYCLE );
    }
    
    
    
    //**************************************************************************
    // Paints de gradiente radial (RadialGradientPaint).
    //**************************************************************************
    /**
     * Cria um gradiente radial de várias cores.
     * 
     * @param cx Coordenada x do centro.
     * @param cy Coordenada y do centro.
     * @param radius Raio.
     * @param fractions Posições percentuais das fronteiras das cores.
     * @param colors Cores.
     * @param cycleMethod Qual tipo de ciclo deve ser empregado.
     * @return Um gradiente radial.
     */
    public static Paint getRadialGradientPaint( double cx, double cy, double radius, float[] fractions, Color[] colors, CycleMethod cycleMethod ) {
        return new RadialGradientPaint( (float) cx, (float) cy, (float) radius, fractions, colors, cycleMethod.method );
    }
    
    /**
     * Cria um gradiente radial acíclico de várias cores.
     * 
     * @param cx Coordenada x do centro.
     * @param cy Coordenada y do centro.
     * @param radius Raio.
     * @param fractions Posições percentuais das fronteiras das cores.
     * @param colors Cores.
     * @return Um gradiente radial acíclico.
     */
    public static Paint getRadialGradientPaint( double cx, double cy, double radius, float[] fractions, Color[] colors ) {
        return new RadialGradientPaint( (float) cx, (float) cy, (float) radius, fractions, colors );
    }
    
    /**
     * Cria um gradiente radial de várias cores.
     * 
     * @param cx Coordenada x do centro.
     * @param cy Coordenada y do centro.
     * @param radius Raio.
     * @param fx Coordenada x do foco.
     * @param fy Coordenada y do foco.
     * @param fractions Posições percentuais das fronteiras das cores.
     * @param colors Cores.
     * @param cycleMethod Qual tipo de ciclo deve ser empregado.
     * @return Um gradiente radial.
     */
    public static Paint getRadialGradientPaint( double cx, double cy, double radius, double fx, double fy, float[] fractions, Color[] colors, CycleMethod cycleMethod ) {
        return new RadialGradientPaint( (float) cx, (float) cy, (float) radius, (float) fx, (float) fy, fractions, colors, cycleMethod.method );
    }
    
    /**
     * Cria um gradiente radial acíclico de várias cores.
     * 
     * @param cx Coordenada x do centro.
     * @param cy Coordenada y do centro.
     * @param radius Raio.
     * @param fx Coordenada x do foco.
     * @param fy Coordenada y do foco.
     * @param fractions Posições percentuais das fronteiras das cores.
     * @param colors Cores.
     * @return Um gradiente radial acíclico.
     */
    public static Paint getRadialGradientPaint( double cx, double cy, double radius, double fx, double fy, float[] fractions, Color[] colors ) {
        return new RadialGradientPaint( (float) cx, (float) cy, (float) radius, (float) fx, (float) fy, fractions, colors, CycleMethod.NO_CYCLE.method );
    }
    
    /**
     * Cria um gradiente radial de várias cores.
     * 
     * @param center Centro.
     * @param radius Raio.
     * @param fractions Posições percentuais das fronteiras das cores.
     * @param colors Cores.
     * @param cycleMethod Qual tipo de ciclo deve ser empregado.
     * @return Um gradiente radial.
     */
    public static Paint getRadialGradientPaint( Vector2 center, double radius, float[] fractions, Color[] colors, CycleMethod cycleMethod ) {
        return new RadialGradientPaint( (float) center.x, (float) center.x, (float) radius, fractions, colors, cycleMethod.method );
    }
    
    /**
     * Cria um gradiente radial acíclico de várias cores.
     * 
     * @param center Centro.
     * @param radius Raio.
     * @param fractions Posições percentuais das fronteiras das cores.
     * @param colors Cores.
     * @return Um gradiente radial acíclico.
     */
    public static Paint getRadialGradientPaint( Vector2 center, double radius, float[] fractions, Color[] colors ) {
        return new RadialGradientPaint( (float) center.x, (float) center.x, (float) radius, fractions, colors );
    }
    
    /**
     * Cria um gradiente radial de várias cores.
     * 
     * @param center Centro.
     * @param radius Raio.
     * @param focus
     * @param fractions Posições percentuais das fronteiras das cores.
     * @param colors Cores.
     * @param cycleMethod Qual tipo de ciclo deve ser empregado.
     * @return Um gradiente radial.
     */
    public static Paint getRadialGradientPaint( Vector2 center, double radius, Vector2 focus, float[] fractions, Color[] colors, CycleMethod cycleMethod ) {
        return new RadialGradientPaint( (float) center.x, (float) center.y, (float) radius, (float) focus.x, (float) focus.y, fractions, colors, cycleMethod.method );
    }
    
    /**
     * Cria um gradiente radial acíclico de várias cores.
     * 
     * @param center Centro.
     * @param radius Raio.
     * @param focus
     * @param fractions Posições percentuais das fronteiras das cores.
     * @param colors Cores.
     * @return Um gradiente radial acíclico.
     */
    public static Paint getRadialGradientPaint( Vector2 center, double radius, Vector2 focus, float[] fractions, Color[] colors ) {
        return new RadialGradientPaint( (float) center.x, (float) center.y, (float) radius, (float) focus.x, (float) focus.y, fractions, colors, CycleMethod.NO_CYCLE.method );
    }
    
    /**
     * Cria um gradiente radial de várias cores.
     * 
     * @param circle Círculo.
     * @param fractions Posições percentuais das fronteiras das cores.
     * @param colors Cores.
     * @param cycleMethod Qual tipo de ciclo deve ser empregado.
     * @return Um gradiente radial.
     */
    public static Paint getRadialGradientPaint( Circle circle, float[] fractions, Color[] colors, CycleMethod cycleMethod ) {
        return new RadialGradientPaint( (float) circle.x, (float) circle.x, (float) circle.radius, fractions, colors, cycleMethod.method );
    }
    
    /**
     * Cria um gradiente radial acíclico de várias cores.
     * 
     * @param circle Círculo.
     * @param fractions Posições percentuais das fronteiras das cores.
     * @param colors Cores.
     * @return Um gradiente radial acíclico.
     */
    public static Paint getRadialGradientPaint( Circle circle, float[] fractions, Color[] colors ) {
        return new RadialGradientPaint( (float) circle.x, (float) circle.x, (float) circle.radius, fractions, colors );
    }
    
    /**
     * Cria um gradiente radial de várias cores.
     * 
     * @param circle Círculo.
     * @param focus Ponto do foco.
     * @param fractions Posições percentuais das fronteiras das cores.
     * @param colors Cores.
     * @param cycleMethod Qual tipo de ciclo deve ser empregado.
     * @return Um gradiente radial.
     */
    public static Paint getRadialGradientPaint( Circle circle, Vector2 focus, float[] fractions, Color[] colors, CycleMethod cycleMethod ) {
        return new RadialGradientPaint( (float) circle.x, (float) circle.x, (float) circle.radius, (float) focus.x, (float) focus.y, fractions, colors, cycleMethod.method );
    }
    
    /**
     * Cria um gradiente radial acíclico de várias cores.
     * 
     * @param circle Círculo.
     * @param focus Ponto do foco.
     * @param fractions Posições percentuais das fronteiras das cores.
     * @param colors Cores.
     * @return Um gradiente radial acíclico.
     */
    public static Paint getRadialGradientPaint( Circle circle, Vector2 focus, float[] fractions, Color[] colors ) {
        return new RadialGradientPaint( (float) circle.x, (float) circle.x, (float) circle.radius, (float) focus.x, (float) focus.y, fractions, colors, CycleMethod.NO_CYCLE.method );
    }
    
    /**
     * Cria um gradiente radial de várias cores.
     * 
     * @param gradientBounds Retângulo dos limites do gradiente
     * @param fractions Posições percentuais das fronteiras das cores.
     * @param colors Cores.
     * @param cycleMethod Qual tipo de ciclo deve ser empregado.
     * @return Um gradiente radial.
     */
    public static Paint getRadialGradientPaint( Rectangle gradientBounds, float[] fractions, Color[] colors, CycleMethod cycleMethod ) {
        return new RadialGradientPaint( new Rectangle2D.Double( gradientBounds.x, gradientBounds.y, gradientBounds.width, gradientBounds.height ), fractions, colors, cycleMethod.method );
    }
    
    /**
     * Cria um gradiente radial acíclico de várias cores.
     * 
     * @param gradientBounds Retângulo dos limites do gradiente
     * @param fractions Posições percentuais das fronteiras das cores.
     * @param colors Cores.
     * @return Um gradiente radial acíclico.
     */
    public static Paint getRadialGradientPaint( Rectangle gradientBounds, float[] fractions, Color[] colors ) {
        return new RadialGradientPaint( new Rectangle2D.Double( gradientBounds.x, gradientBounds.y, gradientBounds.width, gradientBounds.height ), fractions, colors, CycleMethod.NO_CYCLE.method );
    }
    
    
    
    //**************************************************************************
    // Paints de imagem (TexturePaint).
    //**************************************************************************
    /**
     * Cria um paint de imagem.
     * 
     * @param image Imagem.
     * @param anchor Retângulo que representa a âncora.
     * @return Um paint de imagem.
     */
    public static Paint getImagePaint( Image image, Rectangle anchor ) {
        return new TexturePaint( image.buffImage, new Rectangle2D.Double( anchor.x, anchor.y, anchor.width, anchor.height ) );
    }
    
    /**
     * Cria um paint de imagem.
     * 
     * @param image Imagem.
     * @param anchorPos Posição do retângulo que representa a âncora.
     * @param anchorDim Dimenões do retângulo que representa a âncora.
     * @return Um paint de imagem.
     */
    public static Paint getImagePaint( Image image, Vector2 anchorPos, Vector2 anchorDim ) {
        return new TexturePaint( image.buffImage, new Rectangle2D.Double( anchorPos.x, anchorPos.y, anchorDim.x, anchorDim.y ) );
    }
    
    /**
     * Cria um paint de imagem.
     * 
     * @param image Imagem.
     * @param anchorX Coordenada x do retângulo que representa a âncora.
     * @param anchorY Coordenada y do retângulo que representa a âncora.
     * @param anchorWidth Largura do retângulo que representa a âncora.
     * @param anchorHeight Altura do retângulo que representa a âncora.
     * @return Um paint de imagem.
     */
    public static Paint getImagePaint( Image image, double anchorX, double anchorY, double anchorWidth, double anchorHeight ) {
        return new TexturePaint( image.buffImage, new Rectangle2D.Double( anchorX, anchorY, anchorWidth, anchorHeight ) );
    }
    
}
