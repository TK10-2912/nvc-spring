package com.springboot.app.entity;

import lombok.Data;

import java.util.List;

@Data
public class WebhookData {
    private int row;
    private List<String> values;
}
