package com.example.Travel_web.BE.enums;

public enum TourDuration {
    // Ví dụ: "3N2D" tương ứng với 3 ngày (và 2 đêm)
    DURATION_3N2D("3N2D", 3),
    DURATION_4N3D("4N3D", 4),
    DURATION_5N4D("5N4D", 5);

    private final String label;
    private final int days;

    TourDuration(String label, int days) {
        this.label = label;
        this.days = days;
    }

    public String getLabel() {
        return label;
    }

    public int getDays() {
        return days;
    }

    public static int parseDaysFromLabel(String label) {
        int index = label.indexOf("N");
        if (index > 0) {
            try {
                return Integer.parseInt(label.substring(0, index));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Label không hợp lệ: " + label);
            }
        }
        throw new IllegalArgumentException("Label không hợp lệ: " + label);
    }
}
