package com.infotamia.cosmetics.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.infotamia.jackson.ObjectMapperProducer;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.parsing.Parser;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import javax.ws.rs.core.HttpHeaders;

import java.net.URI;
import java.util.Collection;
import java.util.Map;

/**
 * builder style that wraps RestAssured rest client.
 *
 * @author Mohammed Al-Ani
 */
public class RestAssuredWrapper {

    private static final String basePath = "http://127.0.0.1:9998/cosmetics/api/v1/";
    public static class GetBuilder {
        private final RequestSpecification given = RestAssured.given().config(RestAssured.config()
                .objectMapperConfig(new ObjectMapperConfig()
                        .jackson2ObjectMapperFactory((type, s) -> new ObjectMapperProducer().createObjectMapper())));
        private Integer statusCode;
        private String target;


        public JwtBuilder target(String target) {
            this.target = URI.create(basePath).resolve(target).toString();
            return new JwtBuilder(this);
        }

        public static final class JwtBuilder {
            private final GetBuilder builder;

            public JwtBuilder(GetBuilder builder) {
                this.builder = builder;
            }

            public ParamBuilder jwt(String jwt) {
                if (jwt == null) return new ParamBuilder(builder);
                builder.given.header(HttpHeaders.AUTHORIZATION, jwt)
                        .accept(ContentType.JSON)
                        .contentType(ContentType.JSON);
                return new ParamBuilder(builder);
            }
        }

        public static final class ParamBuilder {

            private final GetBuilder builder;

            public ParamBuilder(GetBuilder builder) {

                this.builder = builder;
            }

            public ParamBuilder query(String paramName, Object value) {
                builder.given.queryParam(paramName, value);
                return this;
            }

            public ExecuteBuilder expectingStatusCode(Integer statusCode) {
                builder.statusCode = statusCode;
                return new ExecuteBuilder(builder);
            }
        }

        public static final class ExecuteBuilder {
            private final GetBuilder builder;

            public ExecuteBuilder(GetBuilder builder) {

                this.builder = builder;
            }

            public ValidatableResponse execute() {
                if (builder.statusCode == null) {
                    throw new RuntimeException("fuck off");
                }
                return builder.given.get(builder.target)
                        .then()
                        .log().ifValidationFails(LogDetail.BODY).statusCode(builder.statusCode);
            }

            public <T> T execute(Class<T> clazz) {
                if (builder.statusCode == null) {
                    throw new RuntimeException("fuck off");
                }
                return builder.given.get(builder.target)
                        .then()
                        .log().ifValidationFails(LogDetail.BODY).
                                statusCode(builder.statusCode)
                        .extract()
                        .response()
                        .as(clazz);
            }

            public <T> Collection<T> execute(Class<T> clazz, Class collectionType) {
                if (builder.statusCode == null) {
                    throw new RuntimeException("fuck off");
                }
                JavaType javaType = new ObjectMapperProducer().createObjectMapper().getTypeFactory().constructParametricType(collectionType, clazz);
                return builder.given.get(builder.target)
                        .then()
                        .log().ifValidationFails(LogDetail.BODY).
                                statusCode(builder.statusCode)
                        .extract()
                        .response()
                        .as(javaType, ObjectMapperType.JACKSON_2);
            }

            public <T extends Map> T execute(Class<T> mapType, Class key, Class value) {
                if (builder.statusCode == null) {
                    throw new RuntimeException("fuck off");
                }
                JavaType javaType = new ObjectMapperProducer().createObjectMapper().getTypeFactory().constructMapType(mapType, key, value);
                return builder.given.get(builder.target)
                        .then()
                        .log().ifValidationFails(LogDetail.BODY).
                                statusCode(builder.statusCode)
                        .extract()
                        .response()
                        .as(javaType, ObjectMapperType.JACKSON_2);
            }
        }
    }

    public static class PostBuilder {
        private final RequestSpecification given = RestAssured.given().config(RestAssured.config()
                .objectMapperConfig(new ObjectMapperConfig()
                        .jackson2ObjectMapperFactory((type, s) -> new ObjectMapperProducer().createObjectMapper())));
        private Integer statusCode;
        private String target;
        private byte[] requestBody;


        public JwtBuilder target(String target) {
            this.target = URI.create(basePath).resolve(target).toString();
            return new JwtBuilder(this);
        }

        public static final class JwtBuilder {
            private final PostBuilder builder;

            public JwtBuilder(PostBuilder builder) {
                this.builder = builder;
            }

            public EntityBuilder jwt(String jwt) {
                if (jwt == null) return new EntityBuilder(builder);
                builder.given.header(HttpHeaders.AUTHORIZATION, jwt)
                        .accept(ContentType.JSON)
                        .contentType(ContentType.JSON);
                return new EntityBuilder(builder);
            }
        }

        public static final class EntityBuilder {
            private final PostBuilder builder;

            public EntityBuilder(PostBuilder builder) {
                this.builder = builder;
            }

            public StatusBuilder entity(Object entity) throws JsonProcessingException {
                builder.requestBody = new ObjectMapperProducer().createObjectMapper().writeValueAsBytes(entity);
                builder.given.body(builder.requestBody);
                return new StatusBuilder(builder);
            }

            public StatusBuilder entity() {
                return new StatusBuilder(builder);
            }
        }

        public static final class StatusBuilder {

            private final PostBuilder builder;

            public StatusBuilder(PostBuilder builder) {

                this.builder = builder;
            }

            public ExecuteBuilder expectingStatusCode(Integer statusCode) {
                builder.statusCode = statusCode;
                return new ExecuteBuilder(builder);
            }


        }

        public static final class ExecuteBuilder {
            private final PostBuilder builder;

            public ExecuteBuilder(PostBuilder builder) {

                this.builder = builder;
            }

            public ValidatableResponse execute() {
                if (builder.statusCode == null) {
                    throw new RuntimeException("fuck off");
                }
                return builder.given.post(builder.target)
                        .then()
                        .log().ifValidationFails(LogDetail.BODY).statusCode(builder.statusCode);
            }

            public <T> T execute(Class<T> clazz) {
                if (builder.statusCode == null) {
                    throw new RuntimeException("fuck off");
                }
                return builder.given.post(builder.target)
                        .then()
                        .log().ifValidationFails(LogDetail.BODY).
                                statusCode(builder.statusCode)
                        .parser("application/json", Parser.JSON)
                        .extract()
                        .response()
                        .as(clazz);
            }

            public <T> Collection<T> execute(Class<T> clazz, Class collectionType) {
                if (builder.statusCode == null) {
                    throw new RuntimeException("fuck off");
                }
                JavaType javaType = new ObjectMapperProducer().createObjectMapper()
                        .getTypeFactory()
                        .constructParametricType(collectionType, clazz);
                return builder.given.post(builder.target)
                        .then()
                        .log().ifValidationFails(LogDetail.BODY).
                                statusCode(builder.statusCode)
                        .parser("application/json", Parser.JSON)
                        .extract()
                        .response()
                        .as(javaType, ObjectMapperType.JACKSON_2);
            }
        }
    }

    public static class PatchBuilder {
        private final RequestSpecification given = RestAssured.given().config(RestAssured.config()
                .objectMapperConfig(new ObjectMapperConfig()
                        .jackson2ObjectMapperFactory((type, s) -> new ObjectMapperProducer().createObjectMapper())));
        private Integer statusCode;
        private String target;
        private byte[] requestBody;


        public JwtBuilder target(String target) {
            this.target = URI.create(basePath).resolve(target).toString();
            return new JwtBuilder(this);
        }

        public static final class JwtBuilder {
            private final PatchBuilder builder;

            public JwtBuilder(PatchBuilder builder) {
                this.builder = builder;
            }

            public EntityBuilder jwt(String jwt) {
                if (jwt == null) return new EntityBuilder(builder);
                builder.given.header(HttpHeaders.AUTHORIZATION, jwt)
                        .accept(ContentType.JSON)
                        .contentType(ContentType.JSON);
                return new EntityBuilder(builder);
            }
        }

        public static final class EntityBuilder {
            private final PatchBuilder builder;

            public EntityBuilder(PatchBuilder builder) {
                this.builder = builder;
            }

            public StatusBuilder entity(Object entity) throws JsonProcessingException {
                builder.requestBody = new ObjectMapperProducer().createObjectMapper().writeValueAsBytes(entity);
                builder.given.body(builder.requestBody);
                return new StatusBuilder(builder);
            }
        }

        public static final class StatusBuilder {

            private final PatchBuilder builder;

            public StatusBuilder(PatchBuilder builder) {

                this.builder = builder;
            }

            public ExecuteBuilder expectingStatusCode(Integer statusCode) {
                builder.statusCode = statusCode;
                return new ExecuteBuilder(builder);
            }


        }

        public static final class ExecuteBuilder {
            private final PatchBuilder builder;

            public ExecuteBuilder(PatchBuilder builder) {

                this.builder = builder;
            }

            public ValidatableResponse execute() {
                if (builder.statusCode == null) {
                    throw new RuntimeException("fuck off");
                }
                return builder.given.patch(builder.target)
                        .then()
                        .log().ifValidationFails(LogDetail.BODY).statusCode(builder.statusCode);
            }

            public <T> T execute(Class<T> clazz) {
                if (builder.statusCode == null) {
                    throw new RuntimeException("fuck off");
                }
                return builder.given.patch(builder.target)
                        .then()
                        .log().ifValidationFails(LogDetail.BODY).
                                statusCode(builder.statusCode)
                        .parser("application/json", Parser.JSON)
                        .extract()
                        .response()
                        .as(clazz);
            }

            public <T> Collection<T> execute(Class<T> clazz, Class collectionType) {
                if (builder.statusCode == null) {
                    throw new RuntimeException("fuck off");
                }
                JavaType javaType = new ObjectMapperProducer().createObjectMapper().getTypeFactory().constructParametricType(collectionType, clazz);
                return builder.given.patch(builder.target)
                        .then()
                        .log().ifValidationFails(LogDetail.BODY).
                                statusCode(builder.statusCode)
                        .parser("application/json", Parser.JSON)
                        .extract()
                        .response()
                        .as(javaType, ObjectMapperType.JACKSON_2);
            }
        }
    }


    public static class DeleteBuilder {
        private final RequestSpecification given = RestAssured.given().config(RestAssured.config()
                .objectMapperConfig(new ObjectMapperConfig()
                        .jackson2ObjectMapperFactory((type, s) -> new ObjectMapperProducer().createObjectMapper())));
        private Integer statusCode;
        private String target;
        private byte[] requestBody;


        public JwtBuilder target(String target) {
            this.target = URI.create(basePath).resolve(target).toString();
            return new JwtBuilder(this);
        }

        public static final class JwtBuilder {
            private final DeleteBuilder builder;

            public JwtBuilder(DeleteBuilder builder) {
                this.builder = builder;
            }

            public StatusBuilder jwt(String jwt) {
                if (jwt == null) return new StatusBuilder(builder);
                builder.given.header(HttpHeaders.AUTHORIZATION, jwt)
                        .accept(ContentType.JSON)
                        .contentType(ContentType.JSON);
                return new StatusBuilder(builder);
            }
        }


        public static final class StatusBuilder {

            private final DeleteBuilder builder;

            public StatusBuilder(DeleteBuilder builder) {
                this.builder = builder;
            }

            public ExecuteBuilder expectingStatusCode(Integer statusCode) {
                builder.statusCode = statusCode;
                return new ExecuteBuilder(builder);
            }


        }

        public static final class ExecuteBuilder {
            private final DeleteBuilder builder;

            public ExecuteBuilder(DeleteBuilder builder) {

                this.builder = builder;
            }

            public ValidatableResponse execute() {
                if (builder.statusCode == null) {
                    throw new RuntimeException("fuck off");
                }
                return builder.given.delete(builder.target)
                        .then()
                        .log().ifValidationFails(LogDetail.BODY).statusCode(builder.statusCode);
            }

        }
    }
}

