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

/**
 * Classe com métodos estáticos para gerenciamento dos métodos de logging da
 * engine.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TraceLogUtils {
    
    //**************************************************************************
    // Níveis de log.
    //**************************************************************************
    
    /** Constante para desabilitar o sistema de logs */
    public static final int LOG_NONE           = 0;
    
    /** Constante para logs em nível INFO (informação). */
    public static final int LOG_INFO           = 1;
    
    /** Constante para logs em nível WARNING (aviso). */
    public static final int LOG_WARNING        = 2;
    
    /** Constante para logs em nível ERROR (erro). */
    public static final int LOG_ERROR          = 3;
    
    /** Constante para logs em nível FATAL. */
    public static final int LOG_FATAL          = 4;
    
    /** Constante para realizar o log de quaisquer níveis. */
    public static final int LOG_ALL            = 5;
    
    /**
     * Nível de log atual da engine.
     */
    private static int traceLogLevel = TraceLogUtils.LOG_ALL;
    
    /**
     * Emite uma mensagem de log no stream de saída de erro.
     * 
     * @param logLevel O nível do log.
     * @param text O texto a ser emitido.
     * @param args Os argumentos para a formatação do texto.
     */
    public static void traceLog( int logLevel, String text, Object... args ) {
        if ( logLevel <= traceLogLevel ) {
            String logLevelPrefix = "";
            boolean emit = true;
            switch ( logLevel ) {
                case 1 -> logLevelPrefix = "INFO: ";
                case 2 -> logLevelPrefix = "WARNING: ";
                case 3 -> logLevelPrefix = "ERROR: ";
                case 4 -> logLevelPrefix = "FATAL: ";
                default -> emit = false;
            }
            if ( emit ) {
                System.err.println( logLevelPrefix + String.format( text, args ) );
            }
        }        
    }
    
    /**
     * Emite uma mensagem de log no nível INFO no stream de saída de erro.
     * 
     * @param text O texto a ser emitido.
     * @param args Os argumentos para a formatação do texto.
     */
    public static void traceLogInfo( String text, Object... args ) {
        traceLog( LOG_INFO, text, args );
    }
    
    /**
     * Emite uma mensagem de log no nível WARNING no stream de saída de erro.
     * 
     * @param text O texto a ser emitido.
     * @param args Os argumentos para a formatação do texto.
     */
    public static void traceLogWarning( String text, Object... args ) {
        traceLog( LOG_WARNING, text, args );
    }
    
    /**
     * Emite uma mensagem de log no nível ERROR no stream de saída de erro.
     * 
     * @param text O texto a ser emitido.
     * @param args Os argumentos para a formatação do texto.
     */
    public static void traceLogError( String text, Object... args ) {
        traceLog( LOG_ERROR, text, args );
    }
    
    /**
     * Emite uma mensagem de log no nível FATAL no stream de saída de erro.
     * 
     * @param text O texto a ser emitido.
     * @param args Os argumentos para a formatação do texto.
     */
    public static void traceLogFatal( String text, Object... args ) {
        traceLog( LOG_FATAL, text, args );
    }
    
    /**
     * Configura o nível de log do sistema de loggin da engine.
     * 
     * @param logLevel O nível de log.
     */
    public static void setTraceLogLevel( int logLevel ) {
        if ( logLevel >= LOG_NONE && logLevel <= LOG_ALL ) {
            traceLogLevel = logLevel;
        }
    }
    
}
