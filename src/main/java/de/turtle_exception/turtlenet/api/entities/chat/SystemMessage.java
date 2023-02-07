package de.turtle_exception.turtlenet.api.entities.chat;

import de.turtle_exception.fancyformat.FormatText;
import de.turtle_exception.turtlenet.api.TurtleClient;
import de.turtle_exception.turtlenet.api.exceptions.ResourceInheritanceException;
import de.turtle_exception.turtlenet.api.resource.JsonSerializer;
import de.turtle_exception.turtlenet.api.resource.Resource;
import de.turtle_exception.turtlenet.api.resource.fields.Field;
import de.turtle_exception.turtlenet.api.resource.fields.MapField;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unused")
public class SystemMessage extends MessageEntity {
    protected static final Resource RESOURCE = new Resource(MessageEntity.RESOURCE,
            new Field<>(FormatText.class, "content", false, false, /* TODO */ null),
            new Field<>(String.class, "image_url", true, false, JsonSerializer.DEFAULT_STRING),
            new MapField<>(Map.class, ConcurrentHashMap::new, String.class, String.class, "sections", false, false, JsonSerializer.DEFAULT_STRING, JsonSerializer.DEFAULT_STRING)
    );

    public SystemMessage(@NotNull TurtleClient client, @NotNull Resource resource, @NotNull ArrayList<Object> values) throws ResourceInheritanceException {
        super(client, resource, values);
    }

    public final @NotNull FormatText getContent() {
        return this.entity.get("content", FormatText.class);
    }

    public final @Nullable String getImageUrl() {
        return this.entity.get("image_url", String.class);
    }

    @SuppressWarnings("unchecked")
    public final @NotNull Map<String, String> getSections() {
        return Map.copyOf(this.entity.get("sections", Map.class));
    }
}
