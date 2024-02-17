package com.stonearchscientific.eris;

import static com.google.common.base.Preconditions.checkNotNull;

public class DefaultFilter<R extends Relatable> extends Filter<R> {
    public DefaultFilter(final boolean up) {
        super(up);
    }
    @Override
    public boolean test(R left, R right) {
        checkNotNull(left);
        checkNotNull(right);
        return up ? left.greaterOrEqual(right) : left.lessOrEqual(right);
    }
}
