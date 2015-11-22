package com.rest.akka.api.serialization

import akka.http.scaladsl.marshalling.{ Marshaller, ToEntityMarshaller }
import akka.http.scaladsl.model.{ ContentType, ContentTypes, HttpCharsets, HttpEntity, MediaTypes }
import akka.http.scaladsl.unmarshalling.{ FromEntityUnmarshaller, Unmarshaller }
import akka.stream.Materializer

import org.json4s.DefaultFormats
import org.json4s.ext.JavaTypesSerializers
import org.json4s.jackson.Serialization

import com.fasterxml.jackson.databind.{ DeserializationFeature, SerializationFeature }

trait JsonSerialization {

  val jsonMediaType = MediaTypes.`application/json`
  val jsonContentType = ContentTypes.`application/json`

  protected val jsonSerialization = Serialization

  protected implicit def jsonFormats = DefaultFormats ++ JavaTypesSerializers.all

  protected implicit def jsonMarshallerAnyRef[A <: AnyRef]: ToEntityMarshaller[A] =
    Marshaller.withOpenCharset(jsonMediaType) { (anyRef, negotiatedCharSet) =>
      HttpEntity(ContentType(jsonMediaType, negotiatedCharSet), jsonSerialization.write(anyRef))
    }

  protected implicit def jsonMarshallerAnyVal[A <: AnyVal]: ToEntityMarshaller[A] =
    Marshaller.withOpenCharset(jsonMediaType) { (anyVal, negotiatedCharSet) =>
      HttpEntity(ContentType(jsonMediaType, negotiatedCharSet), anyVal.toString)
    }

  protected implicit def jsonUnmarshaller[A: Manifest](implicit mat: Materializer): FromEntityUnmarshaller[A] =
    Unmarshaller.byteStringUnmarshaller
      .forContentTypes(jsonMediaType)
      .mapWithCharset { (data, charset) =>
        val input = if (charset == HttpCharsets.`UTF-8`) data.utf8String else data.decodeString(charset.nioCharset.name)
        jsonSerialization.read(input)
      }

  org.json4s.jackson.JsonMethods.mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
    .configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false)
    .configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true)
    .configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false)
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false)
    .configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true)
    .configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
    .configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true)
    .configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)
}
