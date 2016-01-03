package ru.jewelline.asana4j.api.entity;

import ru.jewelline.asana4j.utils.StringUtils;

import java.util.List;

public interface Project extends HasId, HasName {
    User getOwner();

    ProjectStatus getCurrentStatus();

    /**
     * The day on which this project is due. This takes a date with format YYYY-MM-DD.
     */
    String getDueDate();

    boolean isArchived();

    boolean isPublic();

    List<User> getMembers();

    Color getColor();

    String getNotes();

    Workspace getWorkspace();

    List<Task> getTasks();

    void delete();

    ProjectUpdater startUpdate();

    enum Color {
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
    interface ProjectUpdater{

        ProjectUpdater setName(String name);

        ProjectUpdater setOwner(User user);

        ProjectUpdater setStatus(ProjectStatus.Color color, String text, User author);

        ProjectUpdater setDueDate(String date);

        ProjectUpdater setColor(Color color);

        ProjectUpdater setNotes(String notes);

        ProjectUpdater setArchived(boolean isArchived);

        ProjectUpdater setPublic(boolean isPublic);

        Project abandon();

        Project update();
    }
}
