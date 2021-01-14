package com.lucutovidiu.util;

import com.github.slugify.Slugify;

public class SlugUtil {
    public static String generateSlug(String name) {
        Slugify slg = new Slugify().withLowerCase(true);
        return slg.slugify(name);
    }
}
