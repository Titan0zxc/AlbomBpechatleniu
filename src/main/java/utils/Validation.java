package utils;

import java.io.File;

public class Validation {

    public static boolean proveritFail(File fail) {
        if (fail == null) return false;
        if (!fail.exists()) return false;
        if (!fail.isFile()) return false;
        if (!fail.canRead()) return false;

        return ImageUtils.podderzhivaetsyaFormat(fail.getName());
    }

    public static boolean proveritPapku(File papka) {
        if (papka == null) return false;
        return papka.exists() && papka.isDirectory() && papka.canRead();
    }

    public static boolean proveritMasshtab(double masshtab) {
        return masshtab >= 0.5 && masshtab <= 3.0;
    }

    public static boolean proveritId(String id) {
        return id != null && !id.trim().isEmpty();
    }
}