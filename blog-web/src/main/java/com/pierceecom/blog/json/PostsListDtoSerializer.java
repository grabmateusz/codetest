package com.pierceecom.blog.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.pierceecom.blog.dto.PostDto;
import com.pierceecom.blog.dto.PostsListDto;
import java.io.IOException;

/**
 * This serializer is created only to fulfill API design.
 * It introduces issue, which can't occur in real application - XML and JSON have different format.
 */
public class PostsListDtoSerializer extends StdSerializer<PostsListDto> {

  public PostsListDtoSerializer(Class<PostsListDto> type) {
    super(type);
  }

  @Override
  public void serialize(PostsListDto postsListDto, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeStartArray();
    for (PostDto post : postsListDto.getPosts()) {
      jsonGenerator.writeObject(post);
    }
    jsonGenerator.writeEndArray();
  }
}
