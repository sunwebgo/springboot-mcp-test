package com.example.mcp.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class TestTools {

    @Tool(description = "两个整数相加，返回求和结果")
    public int add(int a, int b) {
        return a + b;
    }

    @Tool(description = "获取服务器当前时间，证明 MCP 服务在本机真实运行")
    public String getServerTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    @Tool(description = "原样返回传入的文本，用于验证字符串参数往返")
    public String echo(String text) {
        return "echo: " + text;
    }

    @Tool(description = "查询某个城市所在的时区")
    public String getTimeZone(String city) {
        if (city == null || city.isBlank()) {
            return "城市名称不能为空";
        }
        String trimmedCity = city.trim();
        Optional<ZoneId> matchedZone = ZoneId.getAvailableZoneIds().stream()
                .filter(zoneId -> {
                    String lowerZone = zoneId.toLowerCase();
                    String lowerCity = trimmedCity.toLowerCase();
                    // 匹配时区ID中以城市名结尾的部分，如 Asia/Shanghai 匹配 Shanghai
                    int lastSlash = lowerZone.lastIndexOf('/');
                    if (lastSlash >= 0) {
                        String cityPart = lowerZone.substring(lastSlash + 1);
                        return cityPart.equals(lowerCity) || cityPart.replace('_', ' ').equals(lowerCity);
                    }
                    return lowerZone.equals(lowerCity);
                })
                .findFirst()
                .map(ZoneId::of);

        if (matchedZone.isPresent()) {
            ZoneId zone = matchedZone.get();
            ZonedDateTime now = ZonedDateTime.now(zone);
            ZoneOffset offset = now.getOffset();
            return trimmedCity + " 所在时区: " + zone.getId() + " (UTC" + offset + ")";
        }
        return "未找到城市 \"" + trimmedCity + "\" 的时区信息，请尝试其他城市";
    }
}