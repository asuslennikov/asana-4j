package ru.jewelline.asana4j.api.entity;

import ru.jewelline.asana4j.utils.StringUtils;

/**
 * Represents a current state of project. It holds a current status color, a short text description and an author.
 */
public interface ProjectStatus {
    /**
     * Enum which holds all available colors for the project's status
     *
     * @see ProjectStatus#getColor()
     */
    enum Color {
        GREEN("green"),
        YELLOW("yellow"),
        RED("red"),
        NONE(null) {
            @Override
            public boolean isColorMatch(String colorCode) {
                return StringUtils.emptyOrOnlyWhiteSpace(colorCode);
            }
        },;

        private String colorCode;

        Color(String colorCode) {
            this.colorCode = colorCode;
        }

        /**
         * @return A string representation of color code, for example: <code>green</code> for {@link #GREEN}
         * instance.
         */
        public String getColorCode() {
            return this.colorCode;
        }

        /**
         * Checks if the given value matches the color code of the {@link ProjectStatus.Color}
         * instance.
         *
         * @param colorCode one of color codes or null.
         * @return <code>true</code> if the color code matches the one from the instance.
         * @see #getColorCode()
         */
        public boolean isColorMatch(String colorCode) {
            return this.colorCode.equalsIgnoreCase(colorCode);
        }

        @Override
        public String toString() {
            return getColorCode();
        }

        /**
         * Matches the <code>colorCode</code> parameter to one of instances from the {@link ProjectStatus.Color} enum
         *
         * @param colorCode a string representation of project's status color or null
         * @return A {@link ProjectStatus.Color} instance
         */
        public static Color getColorByCode(String colorCode) {
            for (Color color : Color.values()) {
                if (color.isColorMatch(colorCode)) {
                    return color;
                }
            }
            return Color.NONE;
        }
    }

    /**
     * @return A project's status color.
     * @api.field <code>color</code>
     * @api.access Read-write
     * @see ProjectStatus.Color
     */
    Color getColor();

    /**
     * @return A project's status test description.
     * @api.field <code>text</code>
     * @api.access Read-write
     */
    String getText();

    /**
     * @return An author of the current project's state.
     * @api.field <code>author</code>
     * @api.access Read-write
     * @see User
     */
    User getAuthor();
}
