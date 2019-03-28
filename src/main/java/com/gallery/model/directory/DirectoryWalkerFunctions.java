package com.gallery.model.directory;

import java.nio.file.Path;

/**
 * Project: Gallery
 * Class: DirectoryWalkerFunctions
 * Date: 2019-03-26 20:11 [Tuesday]
 *
 * @author Dennis Obukhov
 */
public interface DirectoryWalkerFunctions {
    /**
     * Provides method to test paths to be used in lambdas below
     */
    @FunctionalInterface
    interface RootDirTester {
        boolean isRootDir(Path p);
    }

    @FunctionalInterface
    interface WithinRootDirTester {
        boolean isWithinRootDir(Path p);
    }
}
