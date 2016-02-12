package ru.jewelline.asana4j.core.api.entity;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * A user object represents an account in Asana that can be given access to various workspaces, projects, and tasks.
 * <p>
 * Like other objects in the system, users are referred to by numerical IDs. However, the special string identifier
 * <code>me</code> can be used anywhere a user ID is accepted, to refer to the current authenticated user.
 *
 * @api.link <a href="https://asana.com/developers/api-reference/users">Users API page</a>
 * @see HasId
 * @see HasName
 */
public interface User extends HasId, HasName {

    /**
     * @return The user’s email address
     * @api.field <code>email</code>
     * @api.access Read-only
     */
    String getEmail();

    /**
     * @return A map of the user's profile photo in various sizes, or null if no photo is set. <p>
     * Sizes provided are 21, 27, 36, 60, and 128. Images are in PNG format.
     * @api.field <code>photo</code>
     * @api.access Read-only
     */
    Map<PhotoSize, String> getPhotoUrl();

    /**
     * Downloads the user photo with given size.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param size        A photo size for download.
     * @param destination A destination stream for that attachment.
     * @return <code>true</code> if photo was downloaded, <code>false</code> if it can not
     * (for example if the {@link #getPhotoUrl()} is empty or doesn't contain an url for photo with given size).
     * @see #getPhotoUrl()
     */
    boolean downloadPhoto(PhotoSize size, OutputStream destination);

    /**
     * @return Workspaces and organizations this user may access.<p>
     * The API will only return workspaces and organizations that are also available for the authenticated user.
     * @api.field <code>workspaces</code>
     * @api.access Read-only
     */
    List<Workspace> getWorkspaces();

    /**
     * Enum which holds all available photo sizes.
     *
     * @see User#getPhotoUrl()
     */
    enum PhotoSize {
        SIZE_21("image_21x21"),
        SIZE_27("image_27x27"),
        SIZE_26("image_36x36"),
        SIZE_60("image_60x60"),
        SIZE_128("image_128x128"),;

        private String size;

        PhotoSize(String size) {
            this.size = size;
        }

        @Override
        public String toString() {
            return this.size;
        }
    }
}
