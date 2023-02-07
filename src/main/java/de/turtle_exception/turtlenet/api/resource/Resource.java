package de.turtle_exception.turtlenet.api.resource;

import de.turtle_exception.turtlenet.api.resource.fields.Field;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public final class Resource {
    private final Field<?>[] fields;
    private final Resource parent;
    private final int hashCode;

    // TODO: should this be accessible to other classes than Turtle?
    public Resource(@NotNull Field<?>... fields) throws IllegalArgumentException {
        this.parent = null;
        // copy to secure integrity
        this.fields = Arrays.copyOf(fields, fields.length);
        this.hashCode = generateHashCode(this.fields);
        this.validateKeys();
    }

    public Resource(@NotNull Resource parent, @NotNull Field<?>... fields) throws IllegalArgumentException {
        this(parent, fields, null);
    }

    public Resource(@NotNull Resource parent, @NotNull Field<?>[] fields, Field<?>[] overwrite) throws IllegalArgumentException {
        this.parent = parent;

        Field<?>[] parentFields = parent.getFields();
        this.fields = new Field[parentFields.length + fields.length];

        System.arraycopy(parentFields, 0, this.fields, 0, parentFields.length);
        System.arraycopy(fields, 0, this.fields, parentFields.length, fields.length);

        if (overwrite != null) {
            overwrites:
            for (Field<?> field : overwrite) {
                for (int i = 0; i < this.fields.length; i++) {
                    if (!this.fields[i].getKey().equals(field.getKey())) continue;

                    /* - CHECKS */
                    if (!this.fields[i].isNullable() && field.isNullable())
                        throw new IllegalArgumentException("Overwrite for non-null field '" + field.getKey() + "' is marked as nullable.");
                    if (this.fields[i].isFinal() && !field.isFinal())
                        throw new IllegalArgumentException("Overwrite for final field '" + field.getKey() + "' is not final.");
                    if (this.fields[i].getType().isAssignableFrom(field.getType()))
                        throw new IllegalArgumentException("Type collision on overwrite for field '" + field.getKey() + "'.");

                    this.fields[i] = field;
                    continue overwrites;
                }
            }
        }

        this.hashCode = generateHashCode(this.fields);
        this.validateKeys();
    }

    private void validateKeys() throws IllegalArgumentException {
        for (int i = 0; i < this.fields.length; i++) {
            for (int j = i + 1; j < this.fields.length; j++) {
                if (!fields[i].getKey().equals(fields[j].getKey())) continue;

                throw new IllegalArgumentException("Duplicate key: '" + fields[i].getKey() + "'");
            }
        }
    }

    private static int generateHashCode(@NotNull Field<?>[] fields) {
        Object[] args = new Object[fields.length * 2];
        for (int i = 0; i < fields.length; i += 2)
            args[i] = fields[i].getKey();
        for (int i = 1; i < fields.length; i += 2)
            args[i] = fields[i].getType();
        return Arrays.hashCode(args);
    }

    /* - - - */

    public Field<?>[] getFields() {
        return fields;
    }

    public @NotNull String[] getKeys() {
        String[] arr = new String[fields.length];
        for (int i = 0; i < fields.length; i++)
            arr[i] = fields[i].getKey();
        return arr;
    }

    public @Nullable Resource getParent() {
        return parent;
    }

    public boolean isParent(@NotNull Resource resource) {
        Resource current = parent;
        while (current != null) {
            if (current.equals(resource))
                return true;
            current = current.getParent();
        }
        return false;
    }

    public int getHashCode() {
        return this.hashCode;
    }
}
