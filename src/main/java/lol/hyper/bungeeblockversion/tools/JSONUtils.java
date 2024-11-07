/*
 * This file is part of BungeeBlockVersion.
 *
 * BungeeBlockVersion is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BungeeBlockVersion is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BungeeBlockVersion.  If not, see <https://www.gnu.org/licenses/>.
 */

package lol.hyper.bungeeblockversion.tools;

import lol.hyper.bungeeblockversion.BungeeBlockVersion;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

public class JSONUtils {

    private final BungeeBlockVersion bungeeBlockVersion;

    public JSONUtils(BungeeBlockVersion bungeeBlockVersion) {
        this.bungeeBlockVersion = bungeeBlockVersion;
    }

    /**
     * Get a JSONArray from a URL.
     *
     * @param url The URL to get JSON from.
     * @return The response JSONArray. Returns null if there was some issue.
     */
    public JSONArray requestJSONArray(String url) {
        String rawJSON;
        try {
            URLConnection conn = new URL(url).openConnection();
            conn.setRequestProperty("User-Agent", "BungeeBlockVersion " + bungeeBlockVersion.getDescription().getVersion() + " (+https://github.com/hyperdefined/BungeeBlockVersion)");
            conn.connect();

            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            rawJSON = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            reader.close();

        } catch (IOException exception) {
            exception.printStackTrace();
            bungeeBlockVersion.logger.severe("Unable to read URL " + url);
            return null;
        }

        if (rawJSON.isEmpty()) {
            bungeeBlockVersion.logger.severe("Read JSON from " + url + " returned an empty string!");
            return null;
        }

        return new JSONArray(rawJSON);
    }
}
