/*
 * Copyright 1999,2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.catalina.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Utility class to generate HTTP dates.
 *
 * @author Remy Maucherat
 */
public final class FastHttpDateFormat {


    // -------------------------------------------------------------- Variables


    protected final static TimeZone gmtZone = TimeZone.getTimeZone("GMT");
    /**
     * HTTP date format.
     */
    protected static SimpleDateFormat format =
            new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
    /**
     * Instant on which the currentDate object was generated.
     */
    protected static long currentDateGenerated = 0L;
    /**
     * Current formatted date.
     */
    protected static String currentDate = null;
    /**
     * Date cache.
     */
    protected static HashMap dateCache = new HashMap();

    /**
     * GMT timezone - all HTTP dates are on GMT
     */
    static {
        format.setTimeZone(gmtZone);
    }


    // --------------------------------------------------------- Public Methods

    /**
     * Get the current date in HTTP format.
     */
    public static String getCurrentDate() {

        long now = System.currentTimeMillis();
        if ((now - currentDateGenerated) > 1000) {
            synchronized (format) {
                if ((now - currentDateGenerated) > 1000) {
                    currentDateGenerated = now;
                    currentDate = format.format(new Date(now));
                }
            }
        }
        return currentDate;

    }


    /**
     * Get the HTTP format of the specified date.
     */
    public static String getDate(Date date) {

        String cachedDate = (String) dateCache.get(date);
        if (cachedDate != null)
            return cachedDate;

        String newDate = null;
        synchronized (format) {
            newDate = format.format(date);
            dateCache.put(date, newDate);
        }
        return newDate;

    }


}
