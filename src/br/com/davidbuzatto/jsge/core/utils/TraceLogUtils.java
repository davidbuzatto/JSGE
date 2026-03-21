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

/**
 * Class with static methods for managing the engine's logging methods.
 *
 * @author Prof. Dr. David Buzatto
 */
public abstract class TraceLogUtils {

    /**
     * One fewer Javadoc warning :D
     */
    private TraceLogUtils() {
    }

    //**************************************************************************
    // Log levels.
    //**************************************************************************

    /** Constant to disable the logging system. */
    public static final int LOG_NONE           = 0;

    /** Constant for INFO level logs. */
    public static final int LOG_INFO           = 1;

    /** Constant for WARNING level logs. */
    public static final int LOG_WARNING        = 2;

    /** Constant for ERROR level logs. */
    public static final int LOG_ERROR          = 3;

    /** Constant for FATAL level logs. */
    public static final int LOG_FATAL          = 4;

    /** Constant to log at any level. */
    public static final int LOG_ALL            = 5;

    /**
     * Current log level of the engine.
     */
    private static int traceLogLevel = TraceLogUtils.LOG_ALL;

    /**
     * Emits a log message to the error output stream.
     *
     * @param logLevel The log level.
     * @param text The text to be emitted.
     * @param args The arguments for text formatting.
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
     * Emits an INFO level log message to the error output stream.
     *
     * @param text The text to be emitted.
     * @param args The arguments for text formatting.
     */
    public static void traceLogInfo( String text, Object... args ) {
        traceLog( LOG_INFO, text, args );
    }
    
    /**
     * Emits a WARNING level log message to the error output stream.
     *
     * @param text The text to be emitted.
     * @param args The arguments for text formatting.
     */
    public static void traceLogWarning( String text, Object... args ) {
        traceLog( LOG_WARNING, text, args );
    }
    
    /**
     * Emits an ERROR level log message to the error output stream.
     *
     * @param text The text to be emitted.
     * @param args The arguments for text formatting.
     */
    public static void traceLogError( String text, Object... args ) {
        traceLog( LOG_ERROR, text, args );
    }
    
    /**
     * Emits a FATAL level log message to the error output stream.
     *
     * @param text The text to be emitted.
     * @param args The arguments for text formatting.
     */
    public static void traceLogFatal( String text, Object... args ) {
        traceLog( LOG_FATAL, text, args );
    }
    
    /**
     * Sets the log level for the engine's logging system.
     *
     * @param logLevel The log level.
     */
    public static void setTraceLogLevel( int logLevel ) {
        if ( logLevel >= LOG_NONE && logLevel <= LOG_ALL ) {
            traceLogLevel = logLevel;
        }
    }
    
}
