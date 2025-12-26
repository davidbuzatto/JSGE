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
package br.com.davidbuzatto.jsge.turtle;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.math.MathUtils;
import java.awt.BasicStroke;
import java.awt.Paint;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Classe que encapsula um componente de Gráfico de Tartaruga (Turtle Graphics).
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Turtle {
    
    private List<TurtleState> frames;
    private Deque<TurtleStateSnapshot> snapshots;
    private TurtleState currentState;
    
    /**
     * Cria uma nova Tartaruga na origem, com pincel na cor preta e ângulo inicial
     * igual a -90 graus.
     */
    public Turtle() {
        reset();
    }
    
    /**
     * Cria uma nova Tartaruga com pincel na cor preta e ângulo inicial igual a
     * -90 graus em uma posição definida.
     * 
     * @param x Coordenada x do vértice superior esquerdo.
     * @param y Coordenada y do vértice superior esquerdo.
     */
    public Turtle( double x, double y ) {
        reset( x, y );
    }
    
    /**
     * Cria uma nova Tartaruga com pincel na cor preta em uma posição definida
     * com um ângulo inicial.
     * 
     * @param x Coordenada x do vértice superior esquerdo.
     * @param y Coordenada y do vértice superior esquerdo.
     * @param angle Ângulo inicial em graus (sentido horário).
     */
    public Turtle( double x, double y, double angle ) {
        reset( x, y, angle );
    }
    
    /**
     * Cria uma nova Tartaruga com ângulo inicial igual a -90 graus em uma
     * posição definida com cor inicial.
     * 
     * @param x Coordenada x do vértice superior esquerdo.
     * @param y Coordenada y do vértice superior esquerdo.
     * @param paint Paint inicial.
     */
    public Turtle( double x, double y, Paint paint ) {
        reset( x, y, paint );
    }
    
    /**
     * Cria uma nova Tartaruga.
     * 
     * @param x Coordenada x do vértice superior esquerdo.
     * @param y Coordenada y do vértice superior esquerdo.
     * @param angle Ângulo inicial em graus (sentido horário).
     * @param paint Paint inicial.
     */
    public Turtle( double x, double y, double angle, Paint paint ) {
        reset( x, y, angle, paint );
    }
    
    /**
     * Cria uma nova Tartaruga.
     * 
     * @param x Coordenada x do vértice superior esquerdo.
     * @param y Coordenada y do vértice superior esquerdo.
     * @param angle Ângulo inicial em graus (sentido horário).
     * @param paint Paint inicial.
     * @param stroke Formato inicial do pincel.
     * @param brushDown Se o pincel está abaixado.
     */
    public Turtle( double x, double y, double angle, Paint paint, BasicStroke stroke, boolean brushDown ) {
        reset( x, y, angle, paint, stroke, brushDown );
    }
    
    /**
     * Move a tartaruga.
     * 
     * @param x Coordenada x de destino.
     * @param y Coordenada y de destino.
     */
    public void moveTo( double x, double y ) {
        addFrame(
            x, 
            y, 
            currentState.angle(), 
            currentState.paint(), 
            currentState.stroke(),
            currentState.brushDown(),
            TurtleOperation.DRAW
        );
    }
    
    /**
     * Move a Tartaruga para frente.
     * 
     * @param length Comprimento da movimentação.
     */
    public void moveForward( double length ) {
        addFrame(
            currentState.x() + length * Math.cos( currentState.angle() * MathUtils.DEG2RAD ),
            currentState.y() + length * Math.sin( currentState.angle() * MathUtils.DEG2RAD ),
            currentState.angle(),
            currentState.paint(),
            currentState.stroke(),
            currentState.brushDown(),
            TurtleOperation.DRAW
        );
    }
    
    /**
     * Move a Tartaruga para trás.
     * 
     * @param length Comprimento da movimentação.
     */
    public void moveBackward( double length ) {
        addFrame(
            currentState.x() - length * Math.cos( currentState.angle() * MathUtils.DEG2RAD ),
            currentState.y() - length * Math.sin( currentState.angle() * MathUtils.DEG2RAD ),
            currentState.angle(),
            currentState.paint(),
            currentState.stroke(),
            currentState.brushDown(),
            TurtleOperation.DRAW
        );
    }
    
    /**
     * Move a Tartaruga para esquerda.
     * 
     * @param length Comprimento da movimentação.
     */
    public void moveLeft( double length ) {
        addFrame(
            currentState.x() + length * Math.cos( ( currentState.angle() - 90 ) * MathUtils.DEG2RAD ),
            currentState.y() + length * Math.sin( ( currentState.angle() - 90 ) * MathUtils.DEG2RAD ),
            currentState.angle(),
            currentState.paint(),
            currentState.stroke(),
            currentState.brushDown(),
            TurtleOperation.DRAW
        );
    }
    
    /**
     * Move a Tartaruga para direita.
     * 
     * @param length Comprimento da movimentação.
     */
    public void moveRight( double length ) {
        addFrame(
            currentState.x() + length * Math.cos( ( currentState.angle() + 90 ) * MathUtils.DEG2RAD ),
            currentState.y() + length * Math.sin( ( currentState.angle() + 90 ) * MathUtils.DEG2RAD ),
            currentState.angle(),
            currentState.paint(),
            currentState.stroke(),
            currentState.brushDown(),
            TurtleOperation.DRAW
        );
    }
    
    /**
     * Gira a Tartaruga.
     * 
     * @param amount Quantidade de graus (sentido horário).
     */
    public void rotate( double amount ) {
        addFrame(
            currentState.x(), 
            currentState.y(), 
            currentState.angle() + amount, 
            currentState.paint(), 
            currentState.stroke(),
            currentState.brushDown(),
            TurtleOperation.CONFIG
        );
    }
    
    /**
     * Configura a rotação da tartaruga.
     * 
     * @param angle Ângulo em graus (sentido horário).
     */
    public void setRotation( double angle ) {
        addFrame(
            currentState.x(), 
            currentState.y(), 
            angle, 
            currentState.paint(), 
            currentState.stroke(),
            currentState.brushDown(),
            TurtleOperation.CONFIG
        );
    }
    
    /**
     * Levanta o pincel.
     */
    public void raiseBrush() {
        addFrame(
            currentState.x(), 
            currentState.y(), 
            currentState.angle(), 
            currentState.paint(), 
            currentState.stroke(),
            false,
            TurtleOperation.CONFIG
        );
    }
    
    /**
     * Abaixa o pincel.
     */
    public void lowerBrush() {
        addFrame(
            currentState.x(), 
            currentState.y(), 
            currentState.angle(), 
            currentState.paint(), 
            currentState.stroke(),
            true,
            TurtleOperation.CONFIG
        );
    }
    
    /**
     * Alterna o estado do pincel (levantado ou abaixado).
     */
    public void toggleBrush() {
        addFrame(
            currentState.x(), 
            currentState.y(), 
            currentState.angle(), 
            currentState.paint(), 
            currentState.stroke(),
            !currentState.brushDown(),
            TurtleOperation.CONFIG
        );
    }
    
    /**
     * Configura a cor do pincel.
     * 
     * @param paint Qualquer objeto paint válido que será usado nas operações
     * de desenho.
     */
    public void setBrushPaint( Paint paint ) {
        addFrame(
            currentState.x(), 
            currentState.y(), 
            currentState.angle(), 
            paint, 
            currentState.stroke(),
            currentState.brushDown(),
            TurtleOperation.CONFIG
        );
    }
    
    /**
     * Aumenta a grossura do pincel em um pixel.
     */
    public void increaseBrushWidth() {
        addFrame( 
            currentState.x(), 
            currentState.y(), 
            currentState.angle(), 
            currentState.paint(), 
            new BasicStroke( currentState.stroke().getLineWidth() + 1 ),
            currentState.brushDown(),
            TurtleOperation.CONFIG
        );
    }
    
    /**
     * Diminiu a grossura do pincel em um pixel.
     */
    public void decreaseBrushWidth() {
        float lineWidth = currentState.stroke().getLineWidth() - 1;
        if ( lineWidth > 0.0f ) {
            addFrame( 
                currentState.x(), 
                currentState.y(), 
                currentState.angle(), 
                currentState.paint(), 
                new BasicStroke( lineWidth ),
                currentState.brushDown(),
                TurtleOperation.CONFIG
            );
        }
    }
    
    /**
     * Configura a largura do pincel.
     * 
     * @param width A nova largura do pincel.
     */
    public void setBrushWidth( double width ) {
        if ( width < 0 ) {
            width = 0;
        }
        addFrame( 
            currentState.x(), 
            currentState.y(), 
            currentState.angle(), 
            currentState.paint(), 
            new BasicStroke( (float) width ),
            currentState.brushDown(),
            TurtleOperation.CONFIG
        );
    }
    
    /**
     * Configura o formato do pincel.
     * 
     * @param stroke Novo formato do pincel.
     */
    public void setBrushStroke( BasicStroke stroke ) {
        addFrame( 
            currentState.x(), 
            currentState.y(), 
            currentState.angle(), 
            currentState.paint(), 
            stroke,
            currentState.brushDown(),
            TurtleOperation.CONFIG
        );
    }
    
    /**
     * Salva o estado atual da Tartaruga.
     */
    public void save() {
        snapshots.push( 
            new TurtleStateSnapshot(
                new TurtleState(
                    currentState.x(),
                    currentState.y(),
                    currentState.angle(), 
                    currentState.paint(),
                    currentState.stroke(),
                    currentState.brushDown(),
                    currentState.operation()
                ),
                frames.size()
            )
        );
    }
    
    /**
     * Restaura o último estado salvo da Tartaruga (caso exista), não removendo
     * os quadros gerados até o momento do salvamento.
     */
    public void restoreNotPurge() {
        if ( !snapshots.isEmpty() ) {
            currentState = snapshots.pop().state();
        }
    }
    
    /**
     * Restaura o último estado salvo da Tartaruga (caso exista), removendo
     * os quadros gerados até o momento do salvamento.
     */
    public void restore() {
        if ( !snapshots.isEmpty() ) {
            TurtleStateSnapshot snapshot = snapshots.pop();
            currentState = snapshot.state();
            while ( frames.size() > snapshot.frameCount() ) {
                frames.removeLast();
            }
        }
    }
    
    /**
     * Reconfigura a Tartaruga na origem, com pincel na cor preta e ângulo inicial
     * igual a -90 graus.
     */
    public void reset() {
        reset( 0, 0 );
    }
    
    /**
     * Reconfigura a Tartaruga com pincel na cor preta e ângulo inicial igual a
     * -90 graus em uma posição definida.
     * 
     * @param x Coordenada x do vértice superior esquerdo.
     * @param y Coordenada y do vértice superior esquerdo.
     */
    public void reset( double x, double y ) {
        reset( x, y, -90, EngineFrame.BLACK, new BasicStroke( 1 ), true );
    }
    
    /**
     * Reconfigura a Tartaruga com pincel na cor preta em uma posição definida
     * com um ângulo inicial.
     * 
     * @param x Coordenada x do vértice superior esquerdo.
     * @param y Coordenada y do vértice superior esquerdo.
     * @param angle Ângulo inicial em graus (sentido horário).
     */
    public void reset( double x, double y, double angle ) {
        reset( x, y, angle, EngineFrame.BLACK, new BasicStroke( 1 ), true );
    }
    
    /**
     * Reconfigura a Tartaruga com ângulo inicial igual a -90 graus em uma
     * posição definida com cor inicial.
     * 
     * @param x Coordenada x do vértice superior esquerdo.
     * @param y Coordenada y do vértice superior esquerdo.
     * @param paint Paint inicial.
     */
    public void reset( double x, double y, Paint paint ) {
        reset( x, y, -90, paint, new BasicStroke( 1 ), true );
    }
    
    /**
     * Reconfigura a Tartaruga.
     * 
     * @param x Coordenada x do vértice superior esquerdo.
     * @param y Coordenada y do vértice superior esquerdo.
     * @param angle Ângulo inicial em graus (sentido horário).
     * @param paint Paint inicial.
     */
    public void reset( double x, double y, double angle, Paint paint ) {
        reset( x, y, angle, paint, new BasicStroke( 1 ), true );
    }
    
    /**
     * Reconfigura a Tartaruga.
     * 
     * @param x Coordenada x do vértice superior esquerdo.
     * @param y Coordenada y do vértice superior esquerdo.
     * @param angle Ângulo inicial em graus (sentido horário).
     * @param paint Paint inicial.
     * @param stroke Formato inicial do pincel.
     * @param brushDown Se o pincel está abaixado.
     */
    public void reset( double x, double y, double angle, Paint paint, BasicStroke stroke, boolean brushDown ) {
        frames = new ArrayList<>();
        snapshots = new ArrayDeque<>();
        addFrame( x, y, angle, paint, stroke, brushDown, TurtleOperation.CONFIG );
    }
    
    /**
     * Desenha todos os quadros da Tartaruga.
     * 
     * @param engine EngineFrame utilizada.
     */
    public void draw( EngineFrame engine ) {
        
        if ( frames.size() <= 1 ) {
            return;
        }
        
        draw( 1, frames.size() - 1, engine );
        
    }
    
    /**
     * Desenha do primeiro quadro até um limite.
     * 
     * @param until Quadro final.
     * @param engine EngineFrame utilizada.
     */
    public void draw( int until, EngineFrame engine ) {
        
        if ( frames.size() <= 1 ) {
            return;
        }
        
        draw( 1, until, engine );
        
    }
    
    /**
     * Desenha um intervalo de quadros.
     * 
     * @param from Quadro inicial.
     * @param until Quadro final.
     * @param engine EngineFrame utilizada.
     */
    public void draw( int from, int until, EngineFrame engine  ) {
        
        if ( frames.isEmpty() ) {
            return;
        }
        
        int startIndex = from - 1;
        int endIndex = until - 1;
        
        if ( from < 1 ) {
            throw new IllegalArgumentException( "from must be greater than zero" );
        }
        
        if ( until < 1 ) {
            throw new IllegalArgumentException( "until must be greater than zero" );
        }
        
        if ( from > until ) {
            throw new IllegalArgumentException( "from must be lesser or equal than until" );
        }
        
        if ( startIndex >= frames.size() ) {
            return;
        }
        
        if ( endIndex >= frames.size() - 1 ) {
            endIndex = frames.size() - 2;
            if ( endIndex < startIndex ) {
                return;
            }
        }
        
        BasicStroke originalStroke = engine.getStroke();
        
        for ( int i = startIndex; i <= endIndex; i++ ) {
            TurtleState f1 = frames.get( i );
            TurtleState f2 = frames.get( i + 1 );
            if ( f2.brushDown() && f2.operation() == TurtleOperation.DRAW ) {
                engine.setStroke( f2.stroke() );
                engine.drawLine( f1.x(), f1.y(), f2.x(), f2.y(), f2.paint() );
            }
        }
        
        engine.setStroke( originalStroke );
        
    }
    
    /**
     * Obtém o estado atual da Tartaruga.
     * 
     * @return O estado atual da Tartaruga.
     */
    public TurtleState getCurrentState() {
        return currentState;
    }
    
    /**
     * Obtém a quantidade de quadros da Tartaruga.
     * 
     * @return A quantidade de quadros da Tartaruga.
     */
    public int getFrameCount() {
        return frames.size() - 1;
    }
    
    /**
     * Adiciona um novo quadro.
     */
    private void addFrame( double x, double y, double angle, Paint paint, BasicStroke bs, boolean brushDown, TurtleOperation operation ) {
        currentState = new TurtleState( x, y, angle, paint, bs, brushDown, operation );
        frames.add( currentState );
    }
    
    /**
     * Record para armazenar o estado salvo da Tartaruga.
     */
    private static record TurtleStateSnapshot( TurtleState state, int frameCount ) {
    }
    
}
