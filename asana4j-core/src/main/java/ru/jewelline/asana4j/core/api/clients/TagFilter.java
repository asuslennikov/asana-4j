package ru.jewelline.asana4j.core.api.clients;

/**
 * A holder class for filters values
 */
public final class TagFilter {
    private long workspaceId;
    private long teamId;
    private boolean archived;

    public long getWorkspaceId() {
        return workspaceId;
    }

    /**
     * @param workspaceId The workspace or organization to filter tags on.
     * @return The filter.
     */
    public TagFilter setWorkspaceId(long workspaceId) {
        this.workspaceId = workspaceId;
        return this;
    }

    public long getTeamId() {
        return teamId;
    }

    /**
     * @param teamId The team to filter tags on.
     * @return The filter.
     */
    public TagFilter setTeamId(long teamId) {
        this.teamId = teamId;
        return this;
    }

    public boolean isArchived() {
        return archived;
    }

    /**
     * @param archived Only return tags whose archived field takes on the value of this parameter.
     * @return The filter.
     */
    public TagFilter setArchived(boolean archived) {
        this.archived = archived;
        return this;
    }
}
