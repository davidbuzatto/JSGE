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
package br.com.davidbuzatto.jsge.image;

import br.com.davidbuzatto.jsge.geom.Arc;
import br.com.davidbuzatto.jsge.geom.Circle;
import br.com.davidbuzatto.jsge.geom.CircleSector;
import br.com.davidbuzatto.jsge.geom.CubicCurve;
import br.com.davidbuzatto.jsge.geom.Ellipse;
import br.com.davidbuzatto.jsge.geom.EllipseSector;
import br.com.davidbuzatto.jsge.geom.Line;
import br.com.davidbuzatto.jsge.geom.Path;
import br.com.davidbuzatto.jsge.geom.Point;
import br.com.davidbuzatto.jsge.geom.Polygon;
import br.com.davidbuzatto.jsge.geom.QuadCurve;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.geom.Ring;
import br.com.davidbuzatto.jsge.geom.RoundRectangle;
import br.com.davidbuzatto.jsge.geom.Triangle;
import br.com.davidbuzatto.jsge.geom.Vector2;
import br.com.davidbuzatto.jsge.utils.MathUtils;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

/**
 * Abstração para as BufferedImages.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Image {
    
    private static Font font;
    private static BasicStroke stroke;
    private static boolean antialiasing;
    
    static {
        resetFont();
        resetStroke();
        disableAntialiasing();
    }
    
    public BufferedImage buffImage;
    
    public Image( BufferedImage buffImage ) {
        this.buffImage = buffImage;
    }
    
    public Image( int width, int height ) {
        this( new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB ) );
    }
    
    public int getWidth() {
        return buffImage.getWidth();
    }
    
    public int getHeight() {
        return buffImage.getHeight();
    }
    
    public Graphics2D createGraphics() {
        
        Graphics2D g2d = (Graphics2D) buffImage.createGraphics();
        
        if ( antialiasing ) {
            g2d.setRenderingHint( 
                    RenderingHints.KEY_ANTIALIASING, 
                    RenderingHints.VALUE_ANTIALIAS_ON );
        }
        
        g2d.setFont( font );
        g2d.setStroke( stroke );
        
        return g2d;
        
    }
    
    public int getRGB( int x, int y ) {
        return buffImage.getRGB( x, y );
    }
    
    public void setRGB( int x, int y, int rgb ) {
        buffImage.setRGB( x, y, rgb );
    }
    
    /***************************************************************************
     * Métodos de desenho
     **************************************************************************/

    /**
     * Desenha um pixel.
     * 
     * @param x coordenada x do pixel.
     * @param y coordenada y do pixel.
     * @param color cor de desenho.
     */
    public void drawPixel( double x, double y, Color color ) {
        Graphics2D g2d = createGraphics();
        g2d.setColor( color );
        g2d.draw( new Line2D.Double( x, y, x, y ) );
        g2d.dispose();
    }

    /**
     * Desenha um pixel.
     * 
     * @param vector vetor do pixel.
     * @param color cor de desenho.
     */
    public void drawPixel( Vector2 vector, Color color ) {
        drawPixel( vector.x, vector.y, color );
    }

    /**
     * Desenha um pixel.
     * 
     * @param point ponto do pixel.
     * @param color cor de desenho.
     */
    public void drawPixel( Point point, Color color ) {
        drawPixel( point.x, point.y, color );
    }

    /**
     * Desenha uma linha.
     * 
     * @param startPosX coordenada x do ponto inicial.
     * @param startPosY coordenada y do ponto inicial.
     * @param endPosX coordenada x do ponto final.
     * @param endPosY coordenada y do ponto final.
     * @param color cor de desenho.
     */
    public void drawLine( double startPosX, double startPosY, double endPosX, double endPosY, Color color ) {
        Graphics2D g2d = createGraphics();
        g2d.setColor( color );
        g2d.draw( new Line2D.Double( startPosX, startPosY, endPosX, endPosY ) );
        g2d.dispose();
    }

    /**
     * Desenha uma linha.
     * 
     * @param startVector vetor inicial.
     * @param endVector vetor final.
     * @param color cor de desenho.
     */
    public void drawLine( Vector2 startVector, Vector2 endVector, Color color ) {
        drawLine( startVector.x, startVector.y, endVector.x, endVector.y, color );
    }

    /**
     * Desenha uma linha.
     * 
     * @param startPoint ponto inicial.
     * @param endPoint ponto final.
     * @param color cor de desenho.
     */
    public void drawLine( Point startPoint, Point endPoint, Color color ) {
        drawLine( startPoint.x, startPoint.y, endPoint.x, endPoint.y, color );
    }

    /**
     * Desenha uma linha.
     * 
     * @param line uma linha.
     * @param color cor de desenho.
     */
    public void drawLine( Line line, Color color ) {
        drawLine( line.x1, line.y1, line.x2, line.y2, color );
    }

    /**
     * Desenha um retângulo.
     * 
     * @param x coordenada x do vértice superior esquerdo do retângulo.
     * @param y coordenada y do vértice superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param color cor de desenho.
     */
    public void drawRectangle( double x, double y, double width, double height, Color color ) {
        Graphics2D g2d = createGraphics();
        g2d.setColor( color );
        g2d.draw( new Rectangle2D.Double( x, y, width, height ) );
        g2d.dispose();
    }

    /**
     * Desenha um retângulo.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height altura.
     * @param color cor de desenho.
     */
    public void drawRectangle( Vector2 pos, double width, double height, Color color ) {
        drawRectangle( pos.x, pos.y, width, height, color );
    }
    
    /**
     * Desenha um retângulo.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height altura.
     * @param color cor de desenho.
     */
    public void drawRectangle( Point pos, double width, double height, Color color ) {
        drawRectangle( pos.x, pos.y, width, height, color );
    }

    /**
     * Desenha um retângulo.
     * 
     * @param rectangle um retângulo.
     * @param color cor de desenho.
     */
    public void drawRectangle( Rectangle rectangle, Color color ) {
        drawRectangle( rectangle.x, rectangle.y, rectangle.width, rectangle.height, color );
    }

    /**
     * Pinta um retângulo.
     * 
     * @param x coordenada x do vértice superior esquerdo do retângulo.
     * @param y coordenada y do vértice superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param color cor de desenho.
     */
    public void fillRectangle( double x, double y, double width, double height, Color color ) {
        Graphics2D g2d = createGraphics();
        g2d.setColor( color );
        g2d.fill( new Rectangle2D.Double( x, y, width, height ) );
        g2d.dispose();
    }

    /**
     * Pinta um retângulo.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height altura.
     * @param color cor de desenho.
     */
    public void fillRectangle( Vector2 pos, double width, double height, Color color ) {
        fillRectangle( pos.x, pos.y, width, height, color );
    }

    /**
     * Pinta um retângulo.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height altura.
     * @param color cor de desenho.
     */
    public void fillRectangle( Point pos, double width, double height, Color color ) {
        fillRectangle( pos.x, pos.y, width, height, color );
    }

    /**
     * Pinta um retângulo.
     * 
     * @param rectangle um retângulo.
     * @param color cor de desenho.
     */
    public void fillRectangle( Rectangle rectangle, Color color ) {
        fillRectangle( rectangle.x, rectangle.y, rectangle.width, rectangle.height, color );
    }

    /**
     * Desenha um retângulo rotacionado.
     * 
     * @param x coordenada x do vértice superior esquerdo do retângulo.
     * @param y coordenada y do vértice superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param originX coordenada x do pivô da rotação.
     * @param originY coordenada y do pivô da rotação.
     * @param rotation rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawRectanglePro( double x, double y, double width, double height, double originX, double originY, double rotation, Color color ) {

        Graphics2D g2d = createGraphics();
        g2d.setColor( color );

        g2d.rotate( Math.toRadians( rotation ), originX, originY );
        g2d.draw( new Rectangle2D.Double( x, y, width, height ) );

        g2d.dispose();

    }

    /**
     * Desenha um retângulo rotacionado.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height algura.
     * @param origin pivô da rotação.
     * @param rotation rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawRectanglePro( Vector2 pos, double width, double height, Point origin, double rotation, Color color ) {
        drawRectanglePro( pos.x, pos.y, width, height, origin.x, origin.y, rotation, color );
    }

    /**
     * Desenha um retângulo rotacionado.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height algura.
     * @param origin pivô da rotação.
     * @param rotation rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawRectanglePro( Point pos, double width, double height, Point origin, double rotation, Color color ) {
        drawRectanglePro( pos.x, pos.y, width, height, origin.x, origin.y, rotation, color );
    }

    /**
     * Desenha um retângulo rotacionado.
     * 
     * @param rectangle um retângulo.
     * @param origin pivô da rotação.
     * @param rotation rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawRectanglePro( Rectangle rectangle, Point origin, double rotation, Color color ) {
        drawRectanglePro( rectangle.x, rectangle.y, rectangle.width, rectangle.height, origin.x, origin.y, rotation, color );
    }

    /**
     * Pinta um retângulo rotacionado.
     * 
     * @param x coordenada x do vértice superior esquerdo do retângulo.
     * @param y coordenada y do vértice superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param originX coordenada x do pivô da rotação.
     * @param originY coordenada y do pivô da rotação.
     * @param rotation rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillRectanglePro( double x, double y, double width, double height, double originX, double originY, double rotation, Color color ) {

        Graphics2D g2d = createGraphics();
        g2d.setColor( color );

        g2d.rotate( Math.toRadians( rotation ), originX, originY );
        g2d.fill( new Rectangle2D.Double( x, y, width, height ) );

        g2d.dispose();

    }

    /**
     * Pinta um retângulo rotacionado.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height algura.
     * @param origin pivô da rotação.
     * @param rotation rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillRectanglePro( Vector2 pos, double width, double height, Point origin, double rotation, Color color ) {
        fillRectanglePro( pos.x, pos.y, width, height, origin.x, origin.y, rotation, color );
    }

    /**
     * Pinta um retângulo rotacionado.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height algura.
     * @param origin pivô da rotação.
     * @param rotation rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillRectanglePro( Point pos, double width, double height, Point origin, double rotation, Color color ) {
        fillRectanglePro( pos.x, pos.y, width, height, origin.x, origin.y, rotation, color );
    }

    /**
     * Pinta um retângulo rotacionado.
     * 
     * @param rectangle um retângulo.
     * @param origin pivô da rotação.
     * @param rotation rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillRectanglePro( Rectangle rectangle, Point origin, double rotation, Color color ) {
        fillRectanglePro( rectangle.x, rectangle.y, rectangle.width, rectangle.height, origin.x, origin.y, rotation, color );
    }

    /**
     * Desenha um retângulo com cantos arredondados.
     * 
     * @param x coordenada x do vértice superior esquerdo do retângulo.
     * @param y coordenada y do vértice superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param roundness arredondamento dos cantos.
     * @param color cor de desenho.
     */
    public void drawRoundRectangle( double x, double y, double width, double height, double roundness, Color color ) {
        Graphics2D g2d = createGraphics();
        g2d.setColor( color );
        g2d.draw( new RoundRectangle2D.Double( x, y, width, height, roundness, roundness ) );
        g2d.dispose();
    }

    /**
     * Desenha um retângulo com cantos arredondados.
     * 
     * @param pos ponto superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param roundness arredondamento dos cantos.
     * @param color cor de desenho.
     */
    public void drawRoundRectangle( Vector2 pos, double width, double height, double roundness, Color color ) {
        drawRoundRectangle( pos.x, pos.y, width, height, roundness, color );
    }

    /**
     * Desenha um retângulo com cantos arredondados.
     * 
     * @param pos ponto superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param roundness arredondamento dos cantos.
     * @param color cor de desenho.
     */
    public void drawRoundRectangle( Point pos, double width, double height, double roundness, Color color ) {
        drawRoundRectangle( pos.x, pos.y, width, height, roundness, color );
    }

    /**
     * Desenha um retângulo com cantos arredondados.
     * 
     * @param roundRectangle um retângulo com os cantos arredondados.
     * @param color cor de desenho.
     */
    public void drawRoundRectangle( RoundRectangle roundRectangle, Color color ) {
        drawRoundRectangle( roundRectangle.x, roundRectangle.y, roundRectangle.width, roundRectangle.height, roundRectangle.roundness, color );
    }

    /**
     * Pinta um retângulo com cantos arredondados.
     * 
     * @param x coordenada x do vértice superior esquerdo do retângulo.
     * @param y coordenada y do vértice superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param roundness arredondamento dos cantos.
     * @param color cor de desenho.
     */
    public void fillRoundRectangle( double x, double y, double width, double height, double roundness, Color color ) {
        Graphics2D g2d = createGraphics();
        g2d.setColor( color );
        g2d.fill( new RoundRectangle2D.Double( x, y, width, height, roundness, roundness ) );
        g2d.dispose();
    }

    /**
     * Pinta um retângulo com cantos arredondados.
     * 
     * @param pos ponto superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param roundness arredondamento dos cantos.
     * @param color cor de desenho.
     */
    public void fillRoundRectangle( Vector2 pos, double width, double height, double roundness, Color color ) {
        fillRoundRectangle( pos.x, pos.y, width, height, roundness, color );
    }

    /**
     * Pinta um retângulo com cantos arredondados.
     * 
     * @param pos ponto superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param roundness arredondamento dos cantos.
     * @param color cor de desenho.
     */
    public void fillRoundRectangle( Point pos, double width, double height, double roundness, Color color ) {
        fillRoundRectangle( pos.x, pos.y, width, height, roundness, color );
    }

    /**
     * Pinta um retângulo com cantos arredondados.
     * 
     * @param roundRectangle um retângulo com os cantos arredondados.
     * @param color cor de desenho.
     */
    public void fillRoundRectangle( RoundRectangle roundRectangle, Color color ) {
        fillRoundRectangle( roundRectangle.x, roundRectangle.y, roundRectangle.width, roundRectangle.height, roundRectangle.roundness, color );
    }

    /**
     * Pinta um retângulo com um gradiente horizontal.
     * 
     * @param x coordenada x do vértice superior esquerdo do retângulo.
     * @param y coordenada y do vértice superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param color1 cor inicial do gradiente.
     * @param color2 cor final do gradiente.
     */
    public void fillRectangleGradientH( double x, double y, double width, double height, Color color1, Color color2 ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( new GradientPaint( (int) x, (int) (y + height / 2), color1, (int) (x + width), (int) (y + height / 2), color2 ) );
        g2d.fill( new Rectangle2D.Double( x, y, width, height ) );
        g2d.dispose();
    }

    /**
     * Pinta um retângulo com um gradiente horizontal.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height altura.
     * @param color1 cor inicial do gradiente.
     * @param color2 cor final do gradiente.
     */
    public void fillRectangleGradientH( Vector2 pos, double width, double height, Color color1, Color color2 ) {
        fillRectangleGradientH( pos.x, pos.y, width, height, color1, color2 );
    }

    /**
     * Pinta um retângulo com um gradiente horizontal.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height altura.
     * @param color1 cor inicial do gradiente.
     * @param color2 cor final do gradiente.
     */
    public void fillRectangleGradientH( Point pos, double width, double height, Color color1, Color color2 ) {
        fillRectangleGradientH( pos.x, pos.y, width, height, color1, color2 );
    }

    /**
     * Pinta um retângulo com um gradiente horizontal.
     * 
     * @param rectangle um retângulo.
     * @param color1 cor inicial do gradiente.
     * @param color2 cor final do gradiente.
     */
    public void fillRectangleGradientH( Rectangle rectangle, Color color1, Color color2 ) {
        fillRectangleGradientH( rectangle.x, rectangle.y, rectangle.width, rectangle.height, color1, color2 );
    }

    /**
     * Pinta um retângulo com um gradiente vertical.
     * 
     * @param x coordenada x do vértice superior esquerdo do retângulo.
     * @param y coordenada y do vértice superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param color1 cor inicial do gradiente.
     * @param color2 cor final do gradiente.
     */
    public void fillRectangleGradientV( double x, double y, double width, double height, Color color1, Color color2 ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( new GradientPaint( (int) (x + width / 2), (int) y, color1, (int) (x + width / 2), (int) (y + height), color2 ) );
        g2d.fill( new Rectangle2D.Double( x, y, width, height ) );
        g2d.dispose();
    }

    /**
     * Pinta um retângulo com um gradiente vertical.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height altura.
     * @param color1 cor inicial do gradiente.
     * @param color2 cor final do gradiente.
     */
    public void fillRectangleGradientV( Point pos, double width, double height, Color color1, Color color2 ) {
        fillRectangleGradientV( pos.x, pos.y, width, height, color1, color2 );
    }

    /**
     * Pinta um retângulo com um gradiente vertical.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height altura.
     * @param color1 cor inicial do gradiente.
     * @param color2 cor final do gradiente.
     */
    public void fillRectangleGradientV( Vector2 pos, double width, double height, Color color1, Color color2 ) {
        fillRectangleGradientV( pos.x, pos.y, width, height, color1, color2 );
    }

    /**
     * Pinta um retângulo com um gradiente vertical.
     * 
     * @param rectangle um retângulo.
     * @param color1 cor inicial do gradiente.
     * @param color2 cor final do gradiente.
     */
    public void fillRectangleGradientV( Rectangle rectangle, Color color1, Color color2 ) {
        fillRectangleGradientV( rectangle.x, rectangle.y, rectangle.width, rectangle.height, color1, color2 );
    }

    /**
     * Desenha um círculo.
     * 
     * @param centerX coordenada x do centro do círculo.
     * @param centerY coordenada y do centro do círculo.
     * @param radius raio.
     * @param color cor de desenho.
     */
    public void drawCircle( double centerX, double centerY, double radius, Color color ) {
        Graphics2D g2d = createGraphics();
        g2d.setColor( color );
        g2d.draw( new Ellipse2D.Double( centerX - radius, centerY - radius, radius * 2, radius * 2 ) );
        g2d.dispose();
    }

    /**
     * Desenha um círculo.
     * 
     * @param center centro do círculo.
     * @param radius raio.
     * @param color cor de desenho.
     */
    public void drawCircle( Vector2 center, double radius, Color color ) {
        drawCircle( center.x, center.y, radius, color );
    }

    /**
     * Desenha um círculo.
     * 
     * @param center centro do círculo.
     * @param radius raio.
     * @param color cor de desenho.
     */
    public void drawCircle( Point center, double radius, Color color ) {
        drawCircle( center.x, center.y, radius, color );
    }

    /**
     * Desenha um círculo.
     * 
     * @param circle um círculo.
     * @param color cor de desenho.
     */
    public void drawCircle( Circle circle, Color color ) {
        drawCircle( circle.x, circle.y, circle.radius, color );
    }

    /**
     * Pinta um círculo.
     * 
     * @param centerX coordenada x do centro do círculo.
     * @param centerY coordenada y do centro do círculo.
     * @param radius raio.
     * @param color cor de desenho.
     */
    public void fillCircle( double centerX, double centerY, double radius, Color color ) {
        Graphics2D g2d = createGraphics();
        g2d.setColor( color );
        g2d.fill( new Ellipse2D.Double( centerX - radius, centerY - radius, radius * 2, radius * 2 ) );
        g2d.dispose();
    }

    /**
     * Pinta um círculo.
     * 
     * @param center centro do círculo.
     * @param radius raio.
     * @param color cor de desenho.
     */
    public void fillCircle( Vector2 center, double radius, Color color ) {
        fillCircle( center.x, center.y, radius, color );
    }

    /**
     * Pinta um círculo.
     * 
     * @param center centro do círculo.
     * @param radius raio.
     * @param color cor de desenho.
     */
    public void fillCircle( Point center, double radius, Color color ) {
        fillCircle( center.x, center.y, radius, color );
    }

    /**
     * Pinta um círculo.
     * 
     * @param circle um círculo.
     * @param color cor de desenho.
     */
    public void fillCircle( Circle circle, Color color ) {
        fillCircle( circle.x, circle.y, circle.radius, color );
    }

    /**
     * Desenha uma elipse.
     * 
     * @param centerX coordenada x do centro da elipse.
     * @param centerY coordenada y do centro da elipse.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param color cor de desenho.
     */
    public void drawEllipse( double centerX, double centerY, double radiusH, double radiusV, Color color ) {
        Graphics2D g2d = createGraphics();
        g2d.setColor( color );
        g2d.draw( new Ellipse2D.Double( centerX - radiusH, centerY - radiusV, radiusH * 2, radiusV * 2 ) );
        g2d.dispose();
    }

    /**
     * Desenha uma elipse.
     * 
     * @param center centro da elipse.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param color cor de desenho.
     */
    public void drawEllipse( Vector2 center, double radiusH, double radiusV, Color color ) {
        drawEllipse( center.x, center.y, radiusH, radiusV, color );
    }

    /**
     * Desenha uma elipse.
     * 
     * @param center centro da elipse.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param color cor de desenho.
     */
    public void drawEllipse( Point center, double radiusH, double radiusV, Color color ) {
        drawEllipse( center.x, center.y, radiusH, radiusV, color );
    }

    /**
     * Desenha uma elipse.
     * 
     * @param ellipse uma elipse.
     * @param color cor de desenho.
     */
    public void drawEllipse( Ellipse ellipse, Color color ) {
        drawEllipse( ellipse.x, ellipse.y, ellipse.radiusH, ellipse.radiusV, color );
    }

    /**
     * Pinta uma elipse.
     * 
     * @param centerX coordenada x do centro da elipse.
     * @param centerY coordenada y do centro da elipse.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param color cor de desenho.
     */
    public void fillEllipse( double centerX, double centerY, double radiusH, double radiusV, Color color ) {
        Graphics2D g2d = createGraphics();
        g2d.setColor( color );
        g2d.fill( new Ellipse2D.Double( centerX - radiusH, centerY - radiusV, radiusH * 2, radiusV * 2 ) );
        g2d.dispose();
    }

    /**
     * Pinta uma elipse.
     * 
     * @param center centro da elipse.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param color cor de desenho.
     */
    public void fillEllipse( Vector2 center, double radiusH, double radiusV, Color color ) {
        fillEllipse( center.x, center.y, radiusH, radiusV, color );
    }

    /**
     * Pinta uma elipse.
     * 
     * @param center centro da elipse.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param color cor de desenho.
     */
    public void fillEllipse( Point center, double radiusH, double radiusV, Color color ) {
        fillEllipse( center.x, center.y, radiusH, radiusV, color );
    }

    /**
     * Pinta uma elipse.
     * 
     * @param ellipse uma elipse.
     * @param color cor de desenho.
     */
    public void fillEllipse( Ellipse ellipse, Color color ) {
        fillEllipse( ellipse.x, ellipse.y, ellipse.radiusH, ellipse.radiusV, color );
    }

    /**
     * Desenha um setor circular.
     * 
     * @param centerX coordenada x do centro.
     * @param centerY coordenada y do centro.
     * @param radius raio.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawCircleSector( double centerX, double centerY, double radius, double startAngle, double endAngle, Color color ) {
        Graphics2D g2d = createGraphics();
        g2d.setColor( color );
        double extent = endAngle - startAngle;
        g2d.draw( new Arc2D.Double( centerX - radius, centerY - radius, radius * 2, radius * 2, startAngle, -extent, Arc2D.PIE ) );
        g2d.dispose();
    }

    /**
     * Desenha um setor circular.
     * 
     * @param center ponto do centro.
     * @param radius raio.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawCircleSector( Vector2 center, double radius, double startAngle, double endAngle, Color color ) {
        drawCircleSector( center.x, center.y, radius, startAngle, endAngle, color );
    }

    /**
     * Desenha um setor circular.
     * 
     * @param center ponto do centro.
     * @param radius raio.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawCircleSector( Point center, double radius, double startAngle, double endAngle, Color color ) {
        drawCircleSector( center.x, center.y, radius, startAngle, endAngle, color );
    }

    /**
     * Desenha um setor circular.
     * 
     * @param circle um círculo.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawCircleSector( Circle circle, double startAngle, double endAngle, Color color ) {
        drawCircleSector( circle.x, circle.y, circle.radius, startAngle, endAngle, color );
    }

    /**
     * Desenha um setor circular.
     * 
     * @param circleSector um setor circular.
     * @param color cor de desenho.
     */
    public void drawCircleSector( CircleSector circleSector, Color color ) {
        drawCircleSector( circleSector.x, circleSector.y, circleSector.radius, circleSector.startAngle, circleSector.endAngle, color );
    }

    /**
     * Pinta um setor circular.
     * 
     * @param centerX coordenada x do centro.
     * @param centerY coordenada y do centro.
     * @param radius raio.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillCircleSector( double centerX, double centerY, double radius, double startAngle, double endAngle, Color color ) {
        Graphics2D g2d = createGraphics();
        g2d.setColor( color );
        double extent = endAngle - startAngle;
        g2d.fill( new Arc2D.Double( centerX - radius, centerY - radius, radius * 2, radius * 2, startAngle, -extent, Arc2D.PIE ) );
        g2d.dispose();
    }

    /**
     * Pinta um setor circular.
     * 
     * @param center ponto do centro.
     * @param radius raio.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillCircleSector( Vector2 center, double radius, double startAngle, double endAngle, Color color ) {
        fillCircleSector( center.x, center.y, radius, startAngle, endAngle, color );
    }

    /**
     * Pinta um setor circular.
     * 
     * @param center ponto do centro.
     * @param radius raio.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillCircleSector( Point center, double radius, double startAngle, double endAngle, Color color ) {
        fillCircleSector( center.x, center.y, radius, startAngle, endAngle, color );
    }

    /**
     * Pinta um setor circular.
     * 
     * @param circle um círculo.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillCircleSector( Circle circle, double startAngle, double endAngle, Color color ) {
        fillCircleSector( circle.x, circle.y, circle.radius, startAngle, endAngle, color );
    }

    /**
     * Pinta um setor circular.
     * 
     * @param circleSector um setor circular.
     * @param color cor de desenho.
     */
    public void fillCircleSector( CircleSector circleSector, Color color ) {
        fillCircleSector( circleSector.x, circleSector.y, circleSector.radius, circleSector.startAngle, circleSector.endAngle, color );
    }

    /**
     * Desenha um setor de uma elipse.
     * 
     * @param centerX coordenada x do centro.
     * @param centerY coordenada y do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawEllipseSector( double centerX, double centerY, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        Graphics2D g2d = createGraphics();
        g2d.setColor( color );
        double extent = endAngle - startAngle;
        g2d.draw( new Arc2D.Double( centerX - radiusH, centerY - radiusV, radiusH * 2, radiusV * 2, startAngle, -extent, Arc2D.PIE ) );
        g2d.dispose();
    }

    /**
     * Desenha um setor de uma elipse.
     * 
     * @param center ponto do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawEllipseSector( Vector2 center, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        drawEllipseSector( center.x, center.y, radiusH, radiusV, startAngle, endAngle, color );
    }

    /**
     * Desenha um setor de uma elipse.
     * 
     * @param center ponto do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawEllipseSector( Point center, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        drawEllipseSector( center.x, center.y, radiusH, radiusV, startAngle, endAngle, color );
    }

    /**
     * Desenha um setor de uma elipse.
     * 
     * @param ellipse uma elipse.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawEllipseSector( Ellipse ellipse, double startAngle, double endAngle, Color color ) {
        drawEllipseSector( ellipse.x, ellipse.y, ellipse.radiusH, ellipse.radiusV, startAngle, endAngle, color );
    }

    /**
     * Desenha um setor de uma elipse.
     * 
     * @param ellipseSector um setor de uma elipse.
     * @param color cor de desenho.
     */
    public void drawEllipseSector( EllipseSector ellipseSector, Color color ) {
        drawEllipseSector( ellipseSector.x, ellipseSector.y, ellipseSector.radiusH, ellipseSector.radiusV, ellipseSector.startAngle, ellipseSector.endAngle, color );
    }

    /**
     * Pinta um setor de uma elipse.
     * 
     * @param centerX coordenada x do centro.
     * @param centerY coordenada y do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillEllipseSector( double centerX, double centerY, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        Graphics2D g2d = createGraphics();
        g2d.setColor( color );
        double extent = endAngle - startAngle;
        g2d.fill( new Arc2D.Double( centerX - radiusH, centerY - radiusV, radiusH * 2, radiusV * 2, startAngle, -extent, Arc2D.PIE ) );
        g2d.dispose();
    }

    /**
     * Pinta um setor de uma elipse.
     * 
     * @param center ponto do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillEllipseSector( Vector2 center, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        fillEllipseSector( center.x, center.y, radiusH, radiusV, startAngle, endAngle, color );
    }

    /**
     * Pinta um setor de uma elipse.
     * 
     * @param center ponto do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillEllipseSector( Point center, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        fillEllipseSector( center.x, center.y, radiusH, radiusV, startAngle, endAngle, color );
    }

    /**
     * Pinta um setor de uma elipse.
     * 
     * @param ellipse uma elipse.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillEllipseSector( Ellipse ellipse, double startAngle, double endAngle, Color color ) {
        fillEllipseSector( ellipse.x, ellipse.y, ellipse.radiusH, ellipse.radiusV, startAngle, endAngle, color );
    }

    /**
     * Pinta um setor de uma elipse.
     * 
     * @param ellipseSector um setor de uma elipse.
     * @param color cor de desenho.
     */
    public void fillEllipseSector( EllipseSector ellipseSector, Color color ) {
        fillEllipseSector( ellipseSector.x, ellipseSector.y, ellipseSector.radiusH, ellipseSector.radiusV, ellipseSector.startAngle, ellipseSector.endAngle, color );
    }

    /**
     * Desenha um arco.
     * 
     * @param centerX coordenada x do centro.
     * @param centerY coordenada y do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawArc( double centerX, double centerY, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        Graphics2D g2d = createGraphics();
        g2d.setColor( color );
        double extent = endAngle - startAngle;
        g2d.draw( new Arc2D.Double( centerX - radiusH, centerY - radiusV, radiusH * 2, radiusV * 2, startAngle, -extent, Arc2D.OPEN ) );
        g2d.dispose();
    }

    /**
     * Desenha um arco.
     * 
     * @param center ponto do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawArc( Vector2 center, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        drawArc( center.x, center.y, radiusH, radiusV, startAngle, endAngle, color );
    }

    /**
     * Desenha um arco.
     * 
     * @param center ponto do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawArc( Point center, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        drawArc( center.x, center.y, radiusH, radiusV, startAngle, endAngle, color );
    }

    /**
     * Desenha um arco
     * 
     * @param arc um arco.
     * @param color cor de desenho.
     */
    public void drawArc( Arc arc, Color color ) {
        drawArc( arc.x, arc.y, arc.radiusH, arc.radiusV, arc.startAngle, arc.endAngle, color );
    }

    /**
     * Pinta um arco.
     * 
     * @param centerX coordenada x do centro.
     * @param centerY coordenada y do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillArc( double centerX, double centerY, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        Graphics2D g2d = createGraphics();
        g2d.setColor( color );
        double extent = endAngle - startAngle;
        g2d.fill( new Arc2D.Double( centerX - radiusH, centerY - radiusV, radiusH * 2, radiusV * 2, startAngle, -extent, Arc2D.CHORD ) );
        g2d.dispose();
    }

    /**
     * Pinta um arco.
     * 
     * @param center ponto do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillArc( Vector2 center, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        fillArc( center.x, center.y, radiusH, radiusV, startAngle, endAngle, color );
    }

    /**
     * Pinta um arco.
     * 
     * @param center ponto do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillArc( Point center, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        fillArc( center.x, center.y, radiusH, radiusV, startAngle, endAngle, color );
    }

    /**
     * Pinta um arco
     * 
     * @param arc um arco.
     * @param color cor de desenho.
     */
    public void fillArc( Arc arc, Color color ) {
        fillArc( arc.x, arc.y, arc.radiusH, arc.radiusV, arc.startAngle, arc.endAngle, color );
    }

    /**
     * Desenha um anel.
     * 
     * @param centerX coordenada x do centro.
     * @param centerY coordenada y do centro.
     * @param innerRadius raio interno.
     * @param outerRadius raio externo.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawRing( double centerX, double centerY, double innerRadius, double outerRadius, double startAngle, double endAngle, Color color ) {
        Graphics2D g2d = createGraphics();
        g2d.setColor( color );
        g2d.draw( MathUtils.createRing( centerX, centerY, innerRadius, outerRadius, startAngle, endAngle ) );
        g2d.dispose();
    }

    /**
     * Desenha um anel.
     * 
     * @param center centro do anel.
     * @param innerRadius raio interno.
     * @param outerRadius raio externo.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawRing( Vector2 center, double innerRadius, double outerRadius, double startAngle, double endAngle, Color color ) {
        drawRing( center.x, center.y, innerRadius, outerRadius, startAngle, endAngle, color );
    }

    /**
     * Desenha um anel.
     * 
     * @param center centro do anel.
     * @param innerRadius raio interno.
     * @param outerRadius raio externo.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawRing( Point center, double innerRadius, double outerRadius, double startAngle, double endAngle, Color color ) {
        drawRing( center.x, center.y, innerRadius, outerRadius, startAngle, endAngle, color );
    }

    /**
     * Desenha um anel.
     * 
     * @param ring um anel.
     * @param color cor de desenho.
     */
    public void drawRing( Ring ring, Color color ) {
        drawRing( ring.x, ring.y, ring.innerRadius, ring.outerRadius, ring.startAngle, ring.endAngle, color );
    }

    /**
     * Pinta um anel.
     * 
     * @param centerX coordenada x do centro.
     * @param centerY coordenada y do centro.
     * @param innerRadius raio interno.
     * @param outerRadius raio externo.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillRing( double centerX, double centerY, double innerRadius, double outerRadius, double startAngle, double endAngle, Color color ) {
        Graphics2D g2d = createGraphics();
        g2d.setColor( color );
        g2d.fill( MathUtils.createRing( centerX, centerY, innerRadius, outerRadius, startAngle, endAngle ) );
        g2d.dispose();
    }

    /**
     * Pinta um anel.
     * 
     * @param center centro do anel.
     * @param innerRadius raio interno.
     * @param outerRadius raio externo.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillRing( Vector2 center, double innerRadius, double outerRadius, double startAngle, double endAngle, Color color ) {
        fillRing( center.x, center.y, innerRadius, outerRadius, startAngle, endAngle, color );
    }

    /**
     * Pinta um anel.
     * 
     * @param center centro do anel.
     * @param innerRadius raio interno.
     * @param outerRadius raio externo.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillRing( Point center, double innerRadius, double outerRadius, double startAngle, double endAngle, Color color ) {
        fillRing( center.x, center.y, innerRadius, outerRadius, startAngle, endAngle, color );
    }

    /**
     * Pinta um anel.
     * 
     * @param ring um anel.
     * @param color cor de desenho.
     */
    public void fillRing( Ring ring, Color color ) {
        fillRing( ring.x, ring.y, ring.innerRadius, ring.outerRadius, ring.startAngle, ring.endAngle, color );
    }

    /**
     * Desenha um triângulo.
     * 
     * @param v1x coordenada x do primeiro vértice.
     * @param v1y coordenada y do primeiro vértice.
     * @param v2x coordenada x do segundo vértice.
     * @param v2y coordenada y do segundo vértice.
     * @param v3x coordenada x do terceiro vértice.
     * @param v3y coordenada y do terceiro vértice.
     * @param color cor de desenho.
     */
    public void drawTriangle( double v1x, double v1y, double v2x, double v2y, double v3x, double v3y, Color color ) {
        Graphics2D g2d = createGraphics();
        g2d.setColor( color );
        g2d.draw( MathUtils.createTriangle( v1x, v1y, v2x, v2y, v3x, v3y ) );
        g2d.dispose();
    }

    /**
     * Desenha um triângulo.
     * 
     * @param v1 primeiro vértice.
     * @param v2 segundo vértice.
     * @param v3 terceiro vértice.
     * @param color cor de desenho.
     */
    public void drawTriangle( Vector2 v1, Vector2 v2, Vector2 v3, Color color ) {
        drawTriangle( v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, color );
    }

    /**
     * Desenha um triângulo.
     * 
     * @param v1 primeiro vértice.
     * @param v2 segundo vértice.
     * @param v3 terceiro vértice.
     * @param color cor de desenho.
     */
    public void drawTriangle( Point v1, Point v2, Point v3, Color color ) {
        drawTriangle( v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, color );
    }

    /**
     * Desenha um triângulo.
     * 
     * @param triangle um triângulo.
     * @param color cor de desenho.
     */
    public void drawTriangle( Triangle triangle, Color color ) {
        drawTriangle( triangle.x1, triangle.y1, triangle.x2, triangle.y2, triangle.x3, triangle.y3, color );
    }

    /**
     * Pinta um triângulo.
     * 
     * @param v1x coordenada x do primeiro vértice.
     * @param v1y coordenada y do primeiro vértice.
     * @param v2x coordenada x do segundo vértice.
     * @param v2y coordenada y do segundo vértice.
     * @param v3x coordenada x do terceiro vértice.
     * @param v3y coordenada y do terceiro vértice.
     * @param color cor de desenho.
     */
    public void fillTriangle( double v1x, double v1y, double v2x, double v2y, double v3x, double v3y, Color color ) {
        Graphics2D g2d = createGraphics();
        g2d.setColor( color );
        g2d.fill( MathUtils.createTriangle( v1x, v1y, v2x, v2y, v3x, v3y ) );
        g2d.dispose();
    }

    /**
     * Pinta um triângulo.
     * 
     * @param v1 primeiro vértice.
     * @param v2 segundo vértice.
     * @param v3 terceiro vértice.
     * @param color cor de desenho.
     */
    public void fillTriangle( Vector2 v1, Vector2 v2, Vector2 v3, Color color ) {
        fillTriangle( v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, color );
    }

    /**
     * Pinta um triângulo.
     * 
     * @param v1 primeiro vértice.
     * @param v2 segundo vértice.
     * @param v3 terceiro vértice.
     * @param color cor de desenho.
     */
    public void fillTriangle( Point v1, Point v2, Point v3, Color color ) {
        fillTriangle( v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, color );
    }

    /**
     * Pinta um triângulo.
     * 
     * @param triangle um triângulo.
     * @param color cor de desenho.
     */
    public void fillTriangle( Triangle triangle, Color color ) {
        fillTriangle( triangle.x1, triangle.y1, triangle.x2, triangle.y2, triangle.x3, triangle.y3, color );
    }

    /**
     * Desenha um polígono regular.
     * 
     * @param centerX coordenada x do centro do polígono.
     * @param centerY coordenada y do centro do polígono.
     * @param sides quantidade de lados.
     * @param radius raio.
     * @param rotation rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawPolygon( double centerX, double centerY, double sides, double radius, double rotation, Color color ) {
        Graphics2D g2d = createGraphics();
        g2d.setColor( color );
        g2d.draw( MathUtils.createPolygon( centerX, centerY, sides, radius, rotation ) );
        g2d.dispose();
    }

    /**
     * Desenha um polígono regular.
     * 
     * @param center centro do polígono.
     * @param sides quantidade de lados.
     * @param radius raio.
     * @param rotation rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawPolygon( Vector2 center, double sides, double radius, double rotation, Color color ) {
        drawPolygon( center.x, center.y, sides, radius, rotation, color );
    }

    /**
     * Desenha um polígono regular.
     * 
     * @param center centro do polígono.
     * @param sides quantidade de lados.
     * @param radius raio.
     * @param rotation rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawPolygon( Point center, double sides, double radius, double rotation, Color color ) {
        drawPolygon( center.x, center.y, sides, radius, rotation, color );
    }

    /**
     * Desenha um polígono regular.
     * 
     * @param polygon um polígono regular.
     * @param color cor de desenho.
     */
    public void drawPolygon( Polygon polygon, Color color ) {
        drawPolygon( polygon.x, polygon.y, polygon.sides, polygon.radius, polygon.rotation, color );
    }

    /**
     * Pinta um polígono regular.
     * 
     * @param centerX coordenada x do centro do polígono.
     * @param centerY coordenada y do centro do polígono.
     * @param sides quantidade de lados.
     * @param radius raio.
     * @param rotation rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillPolygon( double centerX, double centerY, double sides, double radius, double rotation, Color color ) {
        Graphics2D g2d = createGraphics();
        g2d.setColor( color );
        g2d.fill( MathUtils.createPolygon( centerX, centerY, sides, radius, rotation ) );
        g2d.dispose();
    }

    /**
     * Pinta um polígono regular.
     * 
     * @param center centro do polígono.
     * @param sides quantidade de lados.
     * @param radius raio.
     * @param rotation rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillPolygon( Vector2 center, double sides, double radius, double rotation, Color color ) {
        fillPolygon( center.x, center.y, sides, radius, rotation, color );
    }

    /**
     * Pinta um polígono regular.
     * 
     * @param center centro do polígono.
     * @param sides quantidade de lados.
     * @param radius raio.
     * @param rotation rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillPolygon( Point center, double sides, double radius, double rotation, Color color ) {
        fillPolygon( center.x, center.y, sides, radius, rotation, color );
    }

    /**
     * Pinta um polígono regular.
     * 
     * @param polygon um polígono regular.
     * @param color cor de desenho.
     */
    public void fillPolygon( Polygon polygon, Color color ) {
        fillPolygon( polygon.x, polygon.y, polygon.sides, polygon.radius, polygon.rotation, color );
    }

    /**
     * Desenha um caminho.
     * 
     * @param path caminho a ser desenhado.
     * @param color cor de desenho.
     */
    public void drawPath( Path path, Color color ) {
        Graphics2D g2d = createGraphics();
        g2d.setColor( color );
        g2d.draw( path.path );
        g2d.dispose();
    }

    /**
     * Ponta um caminho.
     * 
     * @param path caminho a ser desenhado.
     * @param color cor de desenho.
     */
    public void fillPath( Path path, Color color ) {
        Graphics2D g2d = createGraphics();
        g2d.setColor( color );
        g2d.fill( path.path );
        g2d.dispose();
    }

    
    
    /***************************************************************************
     * Métodos de desenhos de curvas.
     **************************************************************************/

    /**
     * Desenha uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1x coordenada x do ponto inicial.
     * @param p1y coordenada y do ponto inicial.
     * @param cx coordenada x do ponto de controle.
     * @param cy coordenada y do ponto de controle.
     * @param p2x coordenada x do ponto final.
     * @param p2y coordenada y do ponto final.
     * @param color cor de desenhho.
     */
    public void drawQuadCurve( double p1x, double p1y, double cx, double cy, double p2x, double p2y, Color color ) {
        Graphics2D g2d = createGraphics();
        g2d.setColor( color );
        g2d.draw( new QuadCurve2D.Double( p1x, p1y, cx, cy, p2x, p2y ) );
        g2d.dispose();
    }

    /**
     * Desenha uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1 ponto inicial.
     * @param c ponto de controle.
     * @param p2 ponto final.
     * @param color cor de desenhho.
     */
    public void drawQuadCurve( Vector2 p1, Vector2 c, Vector2 p2, Color color ) {
        drawQuadCurve( p1.x, p1.y, c.x, c.y, p2.x, p2.y, color );
    }

    /**
     * Desenha uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1 ponto inicial.
     * @param c ponto de controle.
     * @param p2 ponto final.
     * @param color cor de desenhho.
     */
    public void drawQuadCurve( Point p1, Point c, Point p2, Color color ) {
        drawQuadCurve( p1.x, p1.y, c.x, c.y, p2.x, p2.y, color );
    }

    /**
     * Desenha uma curva quadrática (curva Bézier quadrática).
     * 
     * @param quadCurve uma curva Bézier quadrática.
     * @param color cor de desenhho.
     */
    public void drawQuadCurve( QuadCurve quadCurve, Color color ) {
        drawQuadCurve( quadCurve.x1, quadCurve.y1, quadCurve.cx, quadCurve.cy, quadCurve.x2, quadCurve.y2, color );
    }

    /**
     * Pinta uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1x coordenada x do ponto inicial.
     * @param p1y coordenada y do ponto inicial.
     * @param cx coordenada x do ponto de controle.
     * @param cy coordenada y do ponto de controle.
     * @param p2x coordenada x do ponto final.
     * @param p2y coordenada y do ponto final.
     * @param color cor de desenhho.
     */
    public void fillQuadCurve( double p1x, double p1y, double cx, double cy, double p2x, double p2y, Color color ) {
        Graphics2D g2d = createGraphics();
        g2d.setColor( color );
        g2d.fill( new QuadCurve2D.Double( p1x, p1y, cx, cy, p2x, p2y ) );
        g2d.dispose();
    }

    /**
     * Pinta uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1 ponto inicial.
     * @param c ponto de controle.
     * @param p2 ponto final.
     * @param color cor de desenhho.
     */
    public void fillQuadCurve( Vector2 p1, Vector2 c, Vector2 p2, Color color ) {
        fillQuadCurve( p1.x, p1.y, c.x, c.y, p2.x, p2.y, color );
    }

    /**
     * Pinta uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1 ponto inicial.
     * @param c ponto de controle.
     * @param p2 ponto final.
     * @param color cor de desenhho.
     */
    public void fillQuadCurve( Point p1, Point c, Point p2, Color color ) {
        fillQuadCurve( p1.x, p1.y, c.x, c.y, p2.x, p2.y, color );
    }

    /**
     * Pinta uma curva quadrática (curva Bézier quadrática).
     * 
     * @param quadCurve uma curva Bézier quadrática.
     * @param color cor de desenhho.
     */
    public void fillQuadCurve( QuadCurve quadCurve, Color color ) {
        fillQuadCurve( quadCurve.x1, quadCurve.y1, quadCurve.cx, quadCurve.cy, quadCurve.x2, quadCurve.y2, color );
    }

    /**
     * Desenha uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1x coordenada x do ponto inicial.
     * @param p1y coordenada y do ponto inicial.
     * @param c1x coordenada x do primeiro ponto de controle.
     * @param c1y coordenada y do primeiro ponto de controle.
     * @param c2x coordenada x do segundo ponto de controle.
     * @param c2y coordenada y do segundo ponto de controle.
     * @param p2x coordenada x do ponto final.
     * @param p2y coordenada y do ponto final.
     * @param color cor de desenhho.
     */
    public void drawCubicCurve( double p1x, double p1y, double c1x, double c1y, double c2x, double c2y, double p2x, double p2y, Color color ) {
        Graphics2D g2d = createGraphics();
        g2d.setColor( color );
        g2d.draw( new CubicCurve2D.Double( p1x, p1y, c1x, c1y, c2x, c2y, p2x, p2y ) );
        g2d.dispose();
    }

    /**
     * Desenha uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1 ponto inicial.
     * @param c1 primeiro ponto de controle.
     * @param c2 segundo ponto de controle.
     * @param p2 ponto final.
     * @param color cor de desenhho.
     */
    public void drawCubicCurve( Vector2 p1, Vector2 c1, Vector2 c2, Vector2 p2, Color color ) {
        drawCubicCurve( p1.x, p1.y, c1.x, c1.y, c2.x, c2.y, p2.x, p2.y, color );
    }

    /**
     * Desenha uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1 ponto inicial.
     * @param c1 primeiro ponto de controle.
     * @param c2 segundo ponto de controle.
     * @param p2 ponto final.
     * @param color cor de desenhho.
     */
    public void drawCubicCurve( Point p1, Point c1, Point c2, Point p2, Color color ) {
        drawCubicCurve( p1.x, p1.y, c1.x, c1.y, c2.x, c2.y, p2.x, p2.y, color );
    }

    /**
     * Desenha uma curva cúbica (curva Bézier cúbica).
     * 
     * @param cubicCurve uma curva Bézier cúbica.
     * @param color cor de desenhho.
     */
    public void drawCubicCurve( CubicCurve cubicCurve, Color color ) {
        drawCubicCurve( cubicCurve.x1, cubicCurve.y1, cubicCurve.c1x, cubicCurve.c1y, cubicCurve.c2x, cubicCurve.c2y, cubicCurve.x2, cubicCurve.y2, color );
    }

    /**
     * Pinta uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1x coordenada x do ponto inicial.
     * @param p1y coordenada y do ponto inicial.
     * @param c1x coordenada x do primeiro ponto de controle.
     * @param c1y coordenada y do primeiro ponto de controle.
     * @param c2x coordenada x do segundo ponto de controle.
     * @param c2y coordenada y do segundo ponto de controle.
     * @param p2x coordenada x do ponto final.
     * @param p2y coordenada y do ponto final.
     * @param color cor de desenhho.
     */
    public void fillCubicCurve( double p1x, double p1y, double c1x, double c1y, double c2x, double c2y, double p2x, double p2y, Color color ) {
        Graphics2D g2d = createGraphics();
        g2d.setColor( color );
        g2d.fill( new CubicCurve2D.Double( p1x, p1y, c1x, c1y, c2x, c2y, p2x, p2y ) );
        g2d.dispose();
    }

    /**
     * Pinta uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1 ponto inicial.
     * @param c1 primeiro ponto de controle.
     * @param c2 segundo ponto de controle.
     * @param p2 ponto final.
     * @param color cor de desenhho.
     */
    public void fillCubicCurve( Vector2 p1, Vector2 c1, Vector2 c2, Vector2 p2, Color color ) {
        fillCubicCurve( p1.x, p1.y, c1.x, c1.y, c2.x, c2.y, p2.x, p2.y, color );
    }

    /**
     * Pinta uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1 ponto inicial.
     * @param c1 primeiro ponto de controle.
     * @param c2 segundo ponto de controle.
     * @param p2 ponto final.
     * @param color cor de desenhho.
     */
    public void fillCubicCurve( Point p1, Point c1, Point c2, Point p2, Color color ) {
        fillCubicCurve( p1.x, p1.y, c1.x, c1.y, c2.x, c2.y, p2.x, p2.y, color );
    }

    /**
     * Pinta uma curva cúbica (curva Bézier cúbica).
     * 
     * @param cubicCurve uma curva Bézier cúbica.
     * @param color cor de desenhho.
     */
    public void fillCubicCurve( CubicCurve cubicCurve, Color color ) {
        fillCubicCurve( cubicCurve.x1, cubicCurve.y1, cubicCurve.c1x, cubicCurve.c1y, cubicCurve.c2x, cubicCurve.c2y, cubicCurve.x2, cubicCurve.y2, color );
    }

    /***************************************************************************
     * Métodos de desenho de texto.
     **************************************************************************/

    /**
     * Desenha um texto usando o tamanho de fonte corrente.
     * 
     * @param text o texto a ser desenhado.
     * @param x coordenada x do início do desenho do texto.
     * @param y coordenada y do início do desenho do texto.
     * @param color cor de desenho.
     */
    public void drawText( String text, double x, double y, Color color ) {
        Graphics2D g2d = createGraphics();
        g2d.setColor( color );
        Rectangle2D r = g2d.getFontMetrics().getStringBounds( text, g2d );
        g2d.drawString( text, (int) x, (int) ( y + r.getHeight() / 2 ) );
        g2d.dispose();
    }
    
    /**
     * Desenha um texto rotacionado usando o tamanho de fonte corrente.
     * 
     * @param text o texto a ser desenhado.
     * @param x coordenada x do início do desenho do texto.
     * @param y coordenada y do início do desenho do texto.
     * * @param rotation ângulo de rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawText( String text, double x, double y, double rotation, Color color ) {
        drawText( text, x, y, 0, 0, rotation, color );
    }

    /**
     * Desenha um texto rotacionado usando o tamanho de fonte corrente.
     * 
     * @param text o texto a ser desenhado.
     * @param x coordenada x do início do desenho do texto.
     * @param y coordenada y do início do desenho do texto.
     * @param originX coordenada x do pivô de rotação.
     * @param originY coordenada y do pivô de rotação.
     * @param rotation ângulo de rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawText( String text, double x, double y, double originX, double originY, double rotation, Color color ) {
        Graphics2D g2d = createGraphics();
        g2d.setColor( color );
        g2d.rotate( Math.toRadians( rotation ), originX, originY );
        Rectangle2D r = g2d.getFontMetrics().getStringBounds( text, g2d );
        g2d.drawString( text, (int) x, (int) ( y + r.getHeight() / 2 ) );
        g2d.dispose();
    }

    /**
     * Desenha um texto.
     * 
     * @param text o texto a ser desenhado.
     * @param x coordenada x do início do desenho do texto.
     * @param y coordenada y do início do desenho do texto.
     * @param fontSize tamanho da fonte.
     * @param color cor de desenho.
     */
    public void drawText( String text, double x, double y, int fontSize, Color color ) {
        Graphics2D g2d = createGraphics();
        g2d.setColor( color );
        g2d.setFont( g2d.getFont().deriveFont( (float) fontSize ) );
        Rectangle2D r = g2d.getFontMetrics().getStringBounds( text, g2d );
        g2d.drawString( text, (int) x, (int) ( y + r.getHeight() / 2 ) );
        g2d.dispose();
    }
    
    /**
     * Desenha um texto rotacionado.
     * 
     * @param text o texto a ser desenhado.
     * @param x coordenada x do início do desenho do texto.
     * @param y coordenada y do início do desenho do texto.
     * @param rotation ângulo de rotação em graus (sentido horário).
     * @param fontSize tamanho da fonte.
     * @param color cor de desenho.
     */
    public void drawText( String text, double x, double y, double rotation, int fontSize, Color color ) {
        drawText( text, x, y, 0, 0, rotation, fontSize, color );
    }
    
    /**
     * Desenha um texto rotacionado.
     * 
     * @param text o texto a ser desenhado.
     * @param x coordenada x do início do desenho do texto.
     * @param y coordenada y do início do desenho do texto.
     * @param originX coordenada x do pivô de rotação.
     * @param originY coordenada y do pivô de rotação.
     * @param rotation ângulo de rotação em graus (sentido horário).
     * @param fontSize tamanho da fonte.
     * @param color cor de desenho.
     */
    public void drawText( String text, double x, double y, double originX, double originY, double rotation, int fontSize, Color color ) {
        Graphics2D g2d = createGraphics();
        g2d.setColor( color );
        g2d.setFont( g2d.getFont().deriveFont( (float) fontSize ) );
        g2d.rotate( Math.toRadians( rotation ), originX, originY );
        Rectangle2D r = g2d.getFontMetrics().getStringBounds( text, g2d );
        g2d.drawString( text, (int) x, (int) ( y + r.getHeight() / 2 ) );
        g2d.dispose();
    }

    /**
     * Desenha um texto usando o tamanho de fonte corrente.
     * 
     * @param text o texto a ser desenhado.
     * @param point ponto do inicio do desenho do texto.
     * @param color cor de desenho.
     */
    public void drawText( String text, Point point, Color color ) {
        drawText( text, point.x, point.y, color );
    }
    
    /**
     * Desenha um texto rotacionado usando o tamanho de fonte corrente.
     * 
     * @param text o texto a ser desenhado.
     * @param point ponto do inicio do desenho do texto.
     * @param rotation ângulo de rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawText( String text, Point point, double rotation, Color color ) {
        drawText( text, point.x, point.y, 0, 0, rotation, color );
    }

    /**
     * Desenha um texto rotacionado usando o tamanho de fonte corrente.
     * 
     * @param text o texto a ser desenhado.
     * @param point ponto do inicio do desenho do texto.
     * @param origin ponto do pivô de rotação.
     * @param rotation ângulo de rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawText( String text, Point point, Point origin, double rotation, Color color ) {
        drawText( text, point.x, point.y, origin.x, origin.y, rotation, color );
    }

    /**
     * Desenha um texto.
     * 
     * @param text o texto a ser desenhado.
     * @param point ponto do inicio do desenho do texto.
     * @param fontSize tamanho da fonte.
     * @param color cor de desenho.
     */
    public void drawText( String text, Point point, int fontSize, Color color ) {
        drawText( text, point.x, point.y, fontSize, color );
    }
    
    /**
     * Desenha um texto rotacionado.
     * 
     * @param text o texto a ser desenhado.
     * @param point ponto do inicio do desenho do texto.
     * @param rotation ângulo de rotação em graus (sentido horário).
     * @param fontSize tamanho da fonte.
     * @param color cor de desenho.
     */
    public void drawText( String text, Point point, double rotation, int fontSize, Color color ) {
        drawText( text, point.x, point.y, 0, 0, rotation, fontSize, color );
    }

    /**
     * Desenha um texto.
     * 
     * @param text o texto a ser desenhado.
     * @param point ponto do inicio do desenho do texto.
     * @param origin ponto do pivô de rotação.
     * @param rotation ângulo de rotação em graus (sentido horário).
     * @param fontSize tamanho da fonte.
     * @param color cor de desenho.
     */
    public void drawText( String text, Point point, Point origin, double rotation, int fontSize, Color color ) {
        drawText( text, point.x, point.y, origin.x, origin.y, rotation, fontSize, color );
    }

    /**
     * Desenha um texto usando o tamanho de fonte corrente.
     * 
     * @param text o texto a ser desenhado.
     * @param point ponto do inicio do desenho do texto.
     * @param color cor de desenho.
     */
    public void drawText( String text, Vector2 point, Color color ) {
        drawText( text, point.x, point.y, color );
    }
    
    /**
     * Desenha um texto rotacionado usando o tamanho de fonte corrente.
     * 
     * @param text o texto a ser desenhado.
     * @param point ponto do inicio do desenho do texto.
     * @param rotation ângulo de rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawText( String text, Vector2 point, double rotation, Color color ) {
        drawText( text, point.x, point.y, 0, 0, rotation, color );
    }

    /**
     * Desenha um texto rotacionado usando o tamanho de fonte corrente.
     * 
     * @param text o texto a ser desenhado.
     * @param point ponto do inicio do desenho do texto.
     * @param origin ponto do pivô de rotação.
     * @param rotation ângulo de rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawText( String text, Vector2 point, Vector2 origin, double rotation, Color color ) {
        drawText( text, point.x, point.y, origin.x, origin.y, rotation, color );
    }

    /**
     * Desenha um texto.
     * 
     * @param text o texto a ser desenhado.
     * @param point ponto do inicio do desenho do texto.
     * @param fontSize tamanho da fonte.
     * @param color cor de desenho.
     */
    public void drawText( String text, Vector2 point, int fontSize, Color color ) {
        drawText( text, point.x, point.y, fontSize, color );
    }
    
    /**
     * Desenha um texto rotacionado.
     * 
     * @param text o texto a ser desenhado.
     * @param point ponto do inicio do desenho do texto.
     * @param rotation ângulo de rotação em graus (sentido horário).
     * @param fontSize tamanho da fonte.
     * @param color cor de desenho.
     */
    public void drawText( String text, Vector2 point, double rotation, int fontSize, Color color ) {
        drawText( text, point.x, point.y, 0, 0, rotation, fontSize, color );
    }

    /**
     * Desenha um texto rotacionado.
     * 
     * @param text o texto a ser desenhado.
     * @param point ponto do inicio do desenho do texto.
     * @param origin ponto do pivô de rotação.
     * @param rotation ângulo de rotação em graus (sentido horário).
     * @param fontSize tamanho da fonte.
     * @param color cor de desenho.
     */
    public void drawText( String text, Vector2 point, Vector2 origin, double rotation, int fontSize, Color color ) {
        drawText( text, point.x, point.y, origin.x, origin.y, rotation, fontSize, color );
    }
    
    /***************************************************************************
     * Métodos para gerenciamento de fonte e contornos para o contexto gráfico
     * das imagens.
     **************************************************************************/
    
    /**
     * Reconfigura a fonte corrente que é usada para os processos de desenho
     * do contexto gráfico das imagens.
     */
    public static void resetFont() {
        font = new Font( Font.MONOSPACED, Font.PLAIN, 10 );
    }
    
    /**
     * Configura o nome da fonte corrente que é usada para os processos de desenho
     * do contexto gráfico das imagens.
     * 
     * @param name o nome da fonte.
     */
    public static void setFontName( String name ) {
        font = new Font( name, font.getStyle(), font.getSize() );
    }
    
    /**
     * Configura o estilo da fonte corrente que é usada para os processos de desenho
     * do contexto gráfico das imagens.
     * 
     * @param style o estilo da fonte.
     */
    public static void setFontStyle( int style ) {
        font = font.deriveFont( style );
    }
    
    /**
     * Configura o tamanho da fonte corrente que é usada para os processos de desenho
     * do contexto gráfico das imagens.
     * 
     * @param size o tamanho da fonte.
     */
    public static void setFontSize( int size ) {
        font = font.deriveFont( (float) size );
    }
    
    /**
     * Configura a fonte corrente que é usada para os processos de desenho
     * do contexto gráfico das imagens.
     * 
     * @param font a nova fonte.
     */
    public static void setFont( Font font ) {
        Image.font = font;
    }
    
    /**
     * Reconfigura a contorno corrente que é usado para os processos de desenho
     * do contexto gráfico das imagens.
     */
    public static void resetStroke() {
        stroke = new BasicStroke( 1 );
    }
    
    /**
     * Configura a largura do desenho da linha do contorno corrente que é usado
     * para os processos de desenho do contexto gráfico das imagens.
     * 
     * @param width A largura da linha do contorno padrão.
     */
    public static void setStrokeWidth( float width ) {
        stroke = new BasicStroke( width, stroke.getEndCap(), stroke.getLineJoin() );
    }
    
    /**
     * Configura o modelo de desenho do fim das linhas do contorno corrente que é usado
     * para os processos de desenho do contexto gráfico das imagens.
     * 
     * @param endCap O novo modelo de desenho.
     */
    public static void setStrokeEndCap( int endCap ) {
        stroke = new BasicStroke( stroke.getLineWidth(), endCap, stroke.getLineJoin() );
    }
    
    /**
     * Configura o modelo de junção de linhas do contorno corrente que é usado
     * para os processos de desenho do contexto gráfico das imagens.
     * 
     * @param lineJoin O novo modelo de junção de linhas.
     */
    public static void setStrokeLineJoin( int lineJoin ) {
        stroke = new BasicStroke( stroke.getLineWidth(), stroke.getEndCap(), lineJoin );
    }
    
    /**
     * Configura o contorno corrente que é usada para os processos de desenho
     * do contexto gráfico das imagens.
     * 
     * @param stroke o novo contorno.
     */
    public static void setStroke( BasicStroke stroke ) {
        Image.stroke = stroke;
    }
    
    /**
     * Liga a suavização para os processos de desenho do contexto gráfico das
     * imagens.
     */
    public static void enableAntialiasing() {
        antialiasing = true;
    }
    
    /**
     * Desliga a suavização para os processos de desenho do contexto gráfico das
     * imagens.
     */
    public static void disableAntialiasing() {
        antialiasing = false;
    }
    
}
