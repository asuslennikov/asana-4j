package ru.jewelline.asana4j.api.entity;

import java.util.List;

public interface Task extends HasId, HasName {

    User getAssignee();

    AssigneeStatus getAssigneeStatus();

    String getCreatedAt();

    boolean isCompleted();

    String getCompletedAt();

    String getDueOn();

    String getDueAt();

    List<User> getFollowers();

    boolean isHearted();

    List<User> getHeartsAuthors();

    String getModifiedAt();

    int getNumberOfHearts();

    List<Project> getProjects();

    Task getParent();

    String getNotes();

    Workspace getWorkspace();

    void delete();

    enum AssigneeStatus {
        INBOX("inbox"),
        LATER("later"),
        TODAY("today"),
        UPCOMING("upcoming"),
        ;

        private String status;

        AssigneeStatus(String status) {
            this.status = status;
        }

        public String getStatusCode() {
            return this.status;
        }

        public boolean isStatusMatch(String statusCode) {
            return this.status.equalsIgnoreCase(statusCode);
        }

        @Override
        public String toString() {
            return getStatusCode();
        }

        public static AssigneeStatus getStatusByCode(String colorCode) {
            for (AssigneeStatus color : AssigneeStatus.values()) {
                if (color.isStatusMatch(colorCode)) {
                    return color;
                }
            }
            return AssigneeStatus.UPCOMING;
        }
    }

    interface TaskBuilder<T extends TaskBuilder> {

        T setAssignee(User assignee);

        T setAssigneeStatus(AssigneeStatus assigneeStatus);

        T setCompleted(boolean isCompleted);

        T setDueOn(String dueOn);

        T setDueAt(long dueAt);

        T setHearted(boolean isHearted);

        T setName(String name);

        T setNotes(String notes);

    }

    interface TaskCreator extends TaskBuilder<TaskCreator> {

        TaskCreator setWorkspace(Workspace workspace);

        TaskCreator setProjects(Project... projects);

        Task create();
    }

    interface TaskUpdater extends TaskBuilder<TaskUpdater> {

        Task abandon();

        Task update();
    }
}
