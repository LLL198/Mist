package com.una.embyhub.pointsbot.model;

public record BrainQuestion(String type, String prompt, String hiddenPrompt, String answerData, String explanation, int hideAfterSeconds) {
}
