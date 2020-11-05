/*
 * Copyright (C) 2020 Team Gateship-One
 * (Hendrik Borghorst & Frederik Luetkes)
 *
 * The AUTHORS.md file contains a detailed contributors list:
 * <https://github.com/gateship-one/odyssey/blob/master/AUTHORS.md>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.gateshipone.odyssey.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

public class NetworkUtils {

    /**
     * Checks the current network state if an artwork download is allowed.
     *
     * @param context     The current context to resolve the networkinfo
     * @param onlyUseWifi Flag if only a wifi connection is a valid network state
     * @return true if a download is allowed else false
     */
    public static boolean isDownloadAllowed(final Context context, final boolean onlyUseWifi) {
        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null) {
            return false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final Network activeNetwork = cm.getActiveNetwork();

            if (activeNetwork != null) {
                final NetworkCapabilities networkCapabilities = cm.getNetworkCapabilities(activeNetwork);

                if (networkCapabilities != null) {
                    final boolean isWifi = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET);

                    return !(onlyUseWifi && !isWifi);
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            final NetworkInfo networkInfo = cm.getActiveNetworkInfo();

            if (networkInfo != null) {
                final boolean isWifi = networkInfo.getType() == ConnectivityManager.TYPE_WIFI || networkInfo.getType() == ConnectivityManager.TYPE_ETHERNET;

                return !(onlyUseWifi && !isWifi);
            } else {
                return false;
            }
        }
    }
}
