package org.enums;

public enum ServiceName {
    OPEN_WEATHER_MAP("openweathermap");

    private final String service;

    ServiceName(String service) { this.service = service; }

    public String getServiceName() {
        return service;
    }
}
