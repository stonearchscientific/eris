package com.stonearchscientific.eris;

public class FormatFixture<R extends Relatable<R>> extends DefaultFixture<R> {
    protected StringBuilder sb;
    public FormatFixture() {
        super();
        sb = new StringBuilder();
    }
    @Override
    public String toString() {
        return sb.toString();
    }
}
