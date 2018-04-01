package com.hereisalexius.l3df.entities.tools;

import javafx.beans.property.DoubleProperty;
import javafx.scene.canvas.Canvas;

import java.util.HashMap;
import java.util.Map;

/**
 * Абстрактний клас "Tool" - описує мінімальні параметри,
 * <p>
 * що до інструментарію для редагування нащадків "SpaceMap".
 */
public abstract class Tool {

    // Простір в якому діє інструмент
    private Canvas owner;
    // Мапа(список) прив’язок параметрів
    private final Map<String,DoubleProperty> propertyMap = new HashMap<>();

    /**
     * Конструктор без параметрів.
     * <p>
     * Якщо не вказати посилання на "SpaceMap" через "setOwner(SpaceMap owner)",
     * <p>
     * може виникнути NullPointerException вітповідно "SpaceMap owner"
     */
    public Tool() {
    }

    /**
     * Конструктор з параметром.
     * <p>
     * @param  owner Посілання на простір в якому діє інструмент.
     */
    public Tool(Canvas owner) {
        this.owner = owner;
    }


    /**
     * Геттер "getOwner"
     * <p>
     * @return Посілання на простір в якому діє інструмент.
     */
    public Canvas getOwner() {
        return owner;
    }

    public void setOwner(Canvas owner) {
        this.owner = owner;
    }

    /**
     * Геттер "getPropertyMap"
     * <p>
     * Потрібен для налаштування прив’язок до елементів керування на формі.
     * <p>
     * Звертання до параметру відбувається через текстовий ключ.
     * <p>
     * @return Посілання на таблицю параметрів прив’язок.
     */
    public Map<String, DoubleProperty> getPropertyMap() {
        return propertyMap;
    }

}
