package dev.danielsebastian.To_Do_List.controller;

import dev.danielsebastian.To_Do_List.enums.ProgressStatus;

public record TaskUpdateStatus(
        ProgressStatus status
) {
}
