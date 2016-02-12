package ru.jewelline.asana4j.core.api.entity;

import java.io.OutputStream;

/**
 * An attachment object represents any file attached to a task in Asana, whether it’s an uploaded file or one
 * associated via a third-party service such as Dropbox or Google Drive.
 *
 * @api.link <a href="https://asana.com/developers/api-reference/attachments">Attachments</a>
 * @see HasId
 * @see HasName
 */
public interface Attachment extends HasId, HasName {
    /**
     * @return The time at which this story was created.
     * @api.field <code>created_at</code>
     * @api.access Read-only
     */
    String getCreatedAt();

    /**
     * @return The URL containing the content of the attachment. May be <code>null</code> if the attachment is
     * hosted by box. If present, this URL may only be valid for 1 hour from the time of retrieval. You should
     * avoid persisting this URL somewhere and just refresh it on demand to ensure you do not keep stale URLs.
     * @api.field <code>download_url</code>
     * @api.access Read-only
     */
    String getDownloadUrl();

    /**
     * @return The service hosting the attachment.
     * @api.field <code>host</code>
     * @api.access Read-only
     * @see Attachment.Host
     */
    Host getHost();

    /**
     * @return The task this attachment is attached to.
     * @api.field <code>parent</code>
     * @api.access Read-only
     * @see Task
     */
    Task getParent();

    /**
     * @return The URL where the attachment can be viewed, which may be friendlier to users in a browser than
     * just directing them to a raw file.
     * @api.field <code>view_url</code>
     * @api.access Read-only
     */
    String getViewUrl();

    /**
     * Downloads the attachment using a result of the {@link #getDownloadUrl()} method as a source URL.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param destinationStream A destination stream for that attachment.
     * @return <code>true</code> if attachment was downloaded, <code>false</code> if it can not
     * (for example if the {@link #getDownloadUrl()} is empty).
     * @see #getDownloadUrl()
     */
    boolean download(OutputStream destinationStream);

    /**
     * Downloads the attachment preview using a result of the {@link #getViewUrl()} method as a source URL.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param destinationStream A destination stream for that attachment.
     * @return <code>true</code> if attachment was downloaded, <code>false</code> if it can not
     * (for example if the {@link #getViewUrl()} is empty).
     * @see #getViewUrl()
     */
    boolean downloadPreview(OutputStream destinationStream);

    /**
     * Enum which holds all available hosts for an attachment.
     *
     * @see Attachment#getHost()
     */
    enum Host {
        ASANA("asana"),
        DROPBOX("dropbox"),
        GDRIVE("gdrive"),
        BOX("box"),;

        private String hostType;

        Host(String hostType) {
            this.hostType = hostType;
        }

        /**
         * @return A string representation of attachment's host, for example: <code>asana</code> for {@link #ASANA}
         * instance.
         */
        public String getHostType() {
            return this.hostType;
        }

        /**
         * Checks if the given value matches the host type of the {@link Attachment.Host} instance.
         *
         * @param hostType one of hosts.
         * @return <code>true</code> if the host type matches the one from the instance.
         * @see #getHostType()
         */
        public boolean isHostMatch(String hostType) {
            return this.hostType.equalsIgnoreCase(hostType);
        }

        @Override
        public String toString() {
            return getHostType();
        }

        /**
         * Matches the <code>hostType</code> parameter to one of instances from the {@link Attachment.Host} enum.
         *
         * @param hostType a string representation of attachment's host or null
         * @return A {@link Attachment.Host} instance
         */
        public static Host getHostByType(String hostType) {
            for (Host host : Host.values()) {
                if (host.isHostMatch(hostType)) {
                    return host;
                }
            }
            return Host.ASANA;
        }
    }
}
