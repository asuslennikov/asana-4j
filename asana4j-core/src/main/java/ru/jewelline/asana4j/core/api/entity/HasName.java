package ru.jewelline.asana4j.core.api.entity;

/**
 * An object which has a special string field - <code>name</code>
 */
public interface HasName {
    /**
     * @return a display name of object
     * @api.field <code>name</code>
     */
    String getName();
}
