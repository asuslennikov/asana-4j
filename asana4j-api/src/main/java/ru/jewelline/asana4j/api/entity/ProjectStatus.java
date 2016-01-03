package ru.jewelline.asana4j.api.entity;

import ru.jewelline.asana4j.utils.StringUtils;

public interface ProjectStatus {
    enum Color {
        GREEN ("green"),
        YELLOW ("yellow"),
        RED ("red"),
        NONE (null) {
            @Override
            public boolean isColorMatch(String colorCode) {
                return StringUtils.emptyOrOnlyWhiteSpace(colorCode);
            }
        },
        ;

        private String colorCode;

        Color(String colorCode) {
            this.colorCode = colorCode;
        }

        public String getColorCode(){
            return this.colorCode;
        }

        public boolean isColorMatch(String colorCode) {
            return this.colorCode.equalsIgnoreCase(colorCode);
        }

        @Override
        public String toString() {
            return getColorCode();
        }

        public static Color getColorByCode(String colorCode){
            for (Color color : Color.values()) {
                if (color.isColorMatch(colorCode)){
                    return color;
                }
            }
            return Color.NONE;
        }
    }

    Color getColor();

    String getText();

    User getAuthor();
}
