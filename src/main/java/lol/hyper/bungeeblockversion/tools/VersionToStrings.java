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

import net.md_5.bungee.protocol.ProtocolConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class VersionToStrings {

    // Set a list of version strings we can grab via the version number.
    public static final HashMap<Integer, String> versionMap = new HashMap<>();

    static {
        versionMap.put(47, "1.8");
        versionMap.put(107, "1.9");
        versionMap.put(108, "1.9.1");
        versionMap.put(109, "1.9.2");
        versionMap.put(110, "1.9.4");
        versionMap.put(210, "1.10");
        versionMap.put(315, "1.11");
        versionMap.put(316, "1.11.1");
        versionMap.put(335, "1.12");
        versionMap.put(338, "1.12.1");
        versionMap.put(340, "1.12.2");
        versionMap.put(393, "1.13");
        versionMap.put(401, "1.13.1");
        versionMap.put(404, "1.13.2");
        versionMap.put(477, "1.14");
        versionMap.put(480, "1.14.1");
        versionMap.put(485, "1.14.2");
        versionMap.put(490, "1.14.3");
        versionMap.put(498, "1.14.4");
        versionMap.put(573, "1.15");
        versionMap.put(575, "1.15.1");
        versionMap.put(578, "1.15.2");
        versionMap.put(735, "1.16");
        versionMap.put(736, "1.16.1");
        versionMap.put(751, "1.16.2");
        versionMap.put(753, "1.16.3");
        versionMap.put(754, "1.16.4");
        versionMap.put(755, "1.17");
        versionMap.put(756, "1.17.1");
        versionMap.put(757, "1.18");
        versionMap.put(758, "1.18.2");
        versionMap.put(759, "1.19");
        versionMap.put(760, "1.19.2");
        versionMap.put(761, "1.19.3");
        versionMap.put(762, "1.19.4");
        versionMap.put(763, "1.20.1");
    }
    
    /**
     * Builds a string that will show what versions the server supports. Example: 1.8 to 1.14.4
     *
     * @param deniedVersions Versions to deny.
     * @return Returns the string of versions.
     */
    public static String allowedVersions(List<Integer> deniedVersions) {
        List<Integer> allVersions = new ArrayList<>(ProtocolConstants.SUPPORTED_VERSION_IDS);
        allVersions.removeAll(deniedVersions);
        if (allVersions.isEmpty()) {
            return null;
        }
        int minVersion = Collections.min(allVersions);
        int maxVersion = Collections.max(allVersions);

        return versionMap.get(minVersion) + " to " + versionMap.get(maxVersion);
    }
}
