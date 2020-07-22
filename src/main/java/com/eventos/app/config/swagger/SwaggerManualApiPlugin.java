package com.eventos.app.config.swagger;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.collect.Sets;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.OperationBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ApiListingScannerPlugin;
import springfox.documentation.spi.service.contexts.DocumentationContext;
import springfox.documentation.spring.web.readers.operation.CachingOperationNameGenerator;

import java.util.*;

@AllArgsConstructor
@Component
@Api(tags = {"authentication-controller"})
public class SwaggerManualApiPlugin implements ApiListingScannerPlugin {

    private final CachingOperationNameGenerator operationNames;

    @Override
    public List<ApiDescription> apply(DocumentationContext context) {
        return new ArrayList<>(
                Arrays.asList(
                        new ApiDescription(
                                "oauth",
                                "/oauth/token",
                                "Authentication",
                                Collections.singletonList(
                                        new OperationBuilder(
                                                operationNames)
                                                .summary("login")
                                                .tags(new HashSet<String>(Arrays.asList("authentication-controller")))
                                                .authorizations(new ArrayList<>())
                                                .codegenMethodNameStem("credentials-grant-type-POST")
                                                .method(HttpMethod.POST)
                                                .notes("Autenticação do Usuário")
                                                .parameters(
                                                Collections.singletonList(
                                                        new springfox.documentation.builders.ParameterBuilder()
                                                                .description("User email")
                                                                .type(new TypeResolver().resolve(String.class))
                                                                .name("username")
                                                                .parameterType("query")
                                                                .parameterAccess("access")
                                                                .required(true)
                                                                .modelRef(new springfox.documentation.schema.ModelRef("string"))
                                                                .build()))
                                                .parameters(
                                                        Collections.singletonList( //<4a>
                                                                new ParameterBuilder()
                                                                        .description("username")
                                                                        .name("username")
                                                                        .required(true)
                                                                        .build()))
                                                .parameters(
                                                        Collections.singletonList(
                                                                new springfox.documentation.builders.ParameterBuilder()
                                                                        .description("Password")
                                                                        .type(new TypeResolver().resolve(String.class))
                                                                        .name("password")
                                                                        .parameterType("query")
                                                                        .parameterAccess("access")
                                                                        .required(true)
                                                                        .modelRef(new springfox.documentation.schema.ModelRef("string"))
                                                                        .build()))
                                                .parameters(
                                                        Collections.singletonList( //<4a>
                                                                new ParameterBuilder()
                                                                        .description("password")
                                                                        .name("password")
                                                                        .required(true)
                                                                        .build()))
                                                .parameters(
                                                        Collections.singletonList(
                                                                new springfox.documentation.builders.ParameterBuilder()
                                                                        .description("Method for a client application to acquire an access token")
                                                                        .type(new TypeResolver().resolve(String.class))
                                                                        .name("grant_type")
                                                                        .parameterType("query")
                                                                        .parameterAccess("access")
                                                                        .required(true)
                                                                        .modelRef(new springfox.documentation.schema.ModelRef("string"))
                                                                        .build()))
                                                .parameters(
                                                        Collections.singletonList( //<4a>
                                                                new ParameterBuilder()
                                                                        .name("grant_type")
                                                                        .description("grant_type")
                                                                        .required(true)
                                                                        .build()))
                                                .responseMessages(responseMessages())
                                                .responseModel(new springfox.documentation.schema.ModelRef("string"))
                                                .build())
                        ,
                                false)));
    }

    /**
     * @return Set of response messages that overide the default/global response messages
     */
    private Set<ResponseMessage> responseMessages() {
        ResponseMessage[] responseMessages = new ResponseMessage[]{new ResponseMessageBuilder()
                .code(200)
                .message("OK")
                .responseModel(new springfox.documentation.schema.ModelRef("object"))
                .build(),
                new ResponseMessageBuilder()
                        .code(201)
                        .message("Created")
                        .build(),
                new ResponseMessageBuilder()
                .code(401)
                .message("Unauthorized")
                .build(),
                new ResponseMessageBuilder()
                        .code(403)
                        .message("Forbidden")
                        .build(),
                new ResponseMessageBuilder()
                        .code(404)
                        .message("Not Found")
                        .build()
        };
        Set<ResponseMessage> set= Sets.newHashSet(responseMessages);
        return set;
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return DocumentationType.SWAGGER_2.equals(documentationType);
    }
}