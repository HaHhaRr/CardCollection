package ru.hahharr.cardcollection.models.primitives;

import org.hibernate.annotations.IdGeneratorType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@IdGeneratorType(IdGenerator.class)
@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomId {
}
