/*******************************************************************************
 * Copyright (c) 2016 Kiel University and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Kiel University - initial API and implementation
 *******************************************************************************/
package org.eclipse.elk.alg.layered.compaction.oned;

import com.google.common.math.DoubleMath;

/**
 * Internal Class for tolerance affected double comparisons.
 */
public final class CompareFuzzy {
    static final double TOLERANCE = 0.0001;
    
    private CompareFuzzy() {
    }

    // SUPPRESS CHECKSTYLE NEXT 20 Javadoc
    public static boolean eq(final double d1, final double d2) {
        return DoubleMath.fuzzyEquals(d1, d2, TOLERANCE);
    }

    public static boolean gt(final double d1, final double d2) {
        return DoubleMath.fuzzyCompare(d1, d2, TOLERANCE) > 0;
    }

    public static boolean lt(final double d1, final double d2) {
        return DoubleMath.fuzzyCompare(d1, d2, TOLERANCE) < 0;
    }

    public static boolean ge(final double d1, final double d2) {
        return DoubleMath.fuzzyCompare(d1, d2, TOLERANCE) >= 0;
    }

    public static boolean le(final double d1, final double d2) {
        return DoubleMath.fuzzyCompare(d1, d2, TOLERANCE) <= 0;
    }
}
