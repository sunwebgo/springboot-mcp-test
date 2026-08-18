package com.example.mcp;

import com.example.mcp.service.TestTools;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class McpTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(McpTestApplication.class, args);
    }

    @Bean
    public ToolCallbackProvider serverTools(TestTools testTools) {
        return MethodToolCallbackProvider.builder().toolObjects(testTools).build();
    }
}
