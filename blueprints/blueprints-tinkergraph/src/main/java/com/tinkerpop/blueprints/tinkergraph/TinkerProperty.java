package com.tinkerpop.blueprints.tinkergraph;

import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Property;
import com.tinkerpop.blueprints.computer.GraphComputer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public abstract class TinkerProperty<V> implements Property<V> {

    private Map<String, Object> annotations = new HashMap<>();
    private final Element element;
    private final String key;
    private final V value;

    protected TinkerGraphComputer.State state = TinkerGraphComputer.State.STANDARD;
    private TinkerAnnotationMemory annotationMemory;

    public TinkerProperty(final Element element, final String key, final V value) {
        this.element = element;
        this.key = key;
        this.value = value;
    }

    protected TinkerProperty(final TinkerProperty<V> property, final TinkerGraphComputer.State state, final TinkerAnnotationMemory annotationMemory) {
        this(property.getElement(), property.getKey(), property.getValue());
        this.state = state;
        this.annotations = property.annotations;
        this.annotationMemory = annotationMemory;
    }

    public <E extends Element> E getElement() {
        return (E) this.element;
    }

    public String getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

    public boolean isPresent() {
        return null != this.value;
    }

    public <V> void setAnnotation(final String key, final V value) {
        if (this.state == TinkerGraphComputer.State.STANDARD) {
            this.annotations.put(key, value);
        } else if (this.state == TinkerGraphComputer.State.CENTRIC) {
            if (this.annotationMemory.isComputeKey(key)) {
                this.annotationMemory.setAnnotation(this, key, value);
            } else
                throw GraphComputer.Exceptions.providedKeyIsNotAComputeKey(key);
        } else {
            throw GraphComputer.Exceptions.adjacentVertexAnnotationsCanNotBeWritten();
        }
    }

    public <V> Optional<V> getAnnotation(final String key) {
        if (this.state == TinkerGraphComputer.State.STANDARD) {
            return Optional.ofNullable((V) this.annotations.get(key));
        } else if (this.state == TinkerGraphComputer.State.CENTRIC) {
            if (this.annotationMemory.isComputeKey(key))
                return this.annotationMemory.getAnnotation(this, key);
            else
                return Optional.ofNullable((V) this.annotations.get(key));
        } else {
            throw GraphComputer.Exceptions.adjacentVertexAnnotationsCanNotBeRead();
        }
    }

    public TinkerProperty<V> createClone(final TinkerGraphComputer.State state, final TinkerAnnotationMemory annotationMemory) {
        return new TinkerProperty<V>(this, state, annotationMemory) {
            @Override
            public void remove() {
                throw new UnsupportedOperationException("Property removal is not supported");
            }
        };
    }

    public abstract void remove();
}