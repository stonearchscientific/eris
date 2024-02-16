package com.stonearchscientific.eris;

import java.util.Objects;
public abstract class AbstractConcept<T, U, R extends AbstractConcept> implements Relatable<R> {
    protected T extent;
    protected U intent;
    public AbstractConcept(T extent, U intent) {
        this.extent = extent;
        this.intent = intent;
    }
    public T extent() {
        return this.extent;
    }
    public U intent() {
        return this.intent;
    }

    /*
    public boolean greaterOrEqual(Relatable that) {
        if (!(that instanceof AbstractConcept)) {
            throw new IllegalArgumentException("Cannot compare " + this.getClass().getName() + " and " + that.getClass().getName() + ".");
        }
    }

     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(this.extent == null){
            sb.append("()");
        } else {
            sb.append(this.extent);
        }
        if(this.intent == null){
            sb.append("()");
        } else {
            sb.append(this.intent);
        }
        return sb.toString();
    }
}