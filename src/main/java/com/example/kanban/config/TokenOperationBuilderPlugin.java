/*package com.example.kanban.config;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;

public class TokenOperationBuilderPlugin implements OperationBuilderPlugin {

    @Override
    public void apply(OperationContext context) {
        context.requestMappingPattern()
                .indent(pattern -> context.operationBuilder()
                        .hidden(headers -> headers.add
                                ("Authorization", "Bearer your-access-token")));
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }
}
*/