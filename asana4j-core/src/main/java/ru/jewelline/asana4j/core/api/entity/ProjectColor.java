package ru.jewelline.asana4j.core.api.entity;


import ru.jewelline.asana.common.StringUtils;

/**
 * Enum which holds all available project colors.
 *
 * @see Project#getColor()
 * @see Tag#getColor()
 */
public enum ProjectColor {
    DARK_PINK("dark-pink"),
    DARK_GREEN("dark-green"),
    DARK_BLUE("dark-blue"),
    DARK_RED("dark-red"),
    DARK_TEAL("dark-teal"),
    DARK_BROWN("dark-brown"),
    DARK_ORANGE("dark-orange"),
    DARK_PURPLE("dark-purple"),
    DARK_WARM_GRAY("dark-warm-gray"),
    LIGHT_PINK("light-pink"),
    LIGHT_GREEN("light-green"),
    LIGHT_BLUE("light-blue"),
    LIGHT_RED("light-red"),
    LIGHT_TEAL("light-teal"),
    LIGHT_YELLOW("light-yellow"),
    LIGHT_ORANGE("light-orange"),
    LIGHT_PURPLE("light-purple"),
    LIGHT_WARM_GRAY("light-warm-gray"),
    NONE(null) {
        @Override
        public boolean isColorMatch(String colorCode) {
            return StringUtils.emptyOrOnlyWhiteSpace(colorCode);
        }
    },
    ;

    private String colorCode;

    ProjectColor(String colorCode) {
        this.colorCode = colorCode;
    }

    /**
     * @return A string representation of color code, for example: <code>dark-pink</code> for {@link #DARK_PINK}
     * instance.
     */
    public String getColorCode() {
        return this.colorCode;
    }

    /**
     * Checks if the given value matches the color code of the {@link ProjectColor}
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
     * Matches the <code>colorCode</code> parameter to one of instances from the {@link ProjectColor} enum
     *
     * @param colorCode a string representation of project color or null
     * @return A {@link ProjectColor} instance
     */
    public static ProjectColor getColorByCode(String colorCode) {
        for (ProjectColor color : ProjectColor.values()) {
            if (color.isColorMatch(colorCode)) {
                return color;
            }
        }
        return ProjectColor.NONE;
    }
}
