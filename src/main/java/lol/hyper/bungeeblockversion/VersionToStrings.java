package lol.hyper.bungeeblockversion;

import java.util.Collections;
import java.util.HashMap;

public class VersionToStrings {

    // Set a list of version strings we can grab via the version number.
    public static HashMap<Integer, String> versionStrings = new HashMap<>();

    public static void init() {
        versionStrings.put(47, "1.8");
        versionStrings.put(107, "1.9");
        versionStrings.put(108, "1.9.1");
        versionStrings.put(109, "1.9.2");
        versionStrings.put(110, "1.9.4");
        versionStrings.put(210, "1.10");
        versionStrings.put(315, "1.11");
        versionStrings.put(316, "1.11.1");
        versionStrings.put(335, "1.12");
        versionStrings.put(338, "1.12.1");
        versionStrings.put(340, "1.12.2");
        versionStrings.put(393, "1.13");
        versionStrings.put(401, "1.13.1");
        versionStrings.put(404, "1.13.2");
        versionStrings.put(477, "1.14");
        versionStrings.put(480, "1.14.1");
        versionStrings.put(485, "1.14.2");
        versionStrings.put(490, "1.14.3");
        versionStrings.put(498, "1.14.4");
        versionStrings.put(573, "1.15");
        versionStrings.put(575, "1.15.1");
        versionStrings.put(578, "1.15.2");
        versionStrings.put(735, "1.16");
        versionStrings.put(736, "1.16.1");
        versionStrings.put(751, "1.16.2");
        versionStrings.put(752, "1.16.3");
    }

    public static String versionBuilder(Integer[] versions) {
        if (versions.length > 1) {
            int minVersion = Collections.min(ConfigHandler.versions);
            int maxVersion = Collections.max(ConfigHandler.versions);
            return versionStrings.get(minVersion) + " to " + versionStrings.get(maxVersion);
        } else {
            return versionStrings.get(versions[0]);
        }
    }
}
