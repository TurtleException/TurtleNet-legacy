package de.turtle_exception.turtlenet.api.entities.chat;

import de.turtle_exception.fancyformat.FormatText;
import de.turtle_exception.turtlenet.api.annotations.Field;
import de.turtle_exception.turtlenet.api.annotations.FieldMap;
import de.turtle_exception.turtlenet.api.annotations.Reference;
import de.turtle_exception.turtlenet.api.annotations.Resource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@SuppressWarnings("unused")
@Resource(path = "systemMessage")
public interface SystemMessage extends MessageEntity {
    @Field(name = "content")
    @NotNull FormatText getContent();

    @Field(name = "image_url")
    @Nullable String getImageUrl();

    @Field(name = "sections")
    @FieldMap(keyType = String.class, valueType = String.class)
    @Reference(to = "section", nested = true)
    @NotNull Map<String, String> getSections();
}
