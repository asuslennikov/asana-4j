package ru.jewelline.asana4j.api.entity;

/**
 * Custom external data allows a client application to add app-specific metadata to Tasks in the API. The custom data
 * includes a string id that can be used to retrieve objects and a data blob that can store character strings.
 * <p>
 * The blob may store unicode-safe serialized data such as JSON or YAML. The external id is capped at 1,024 characters,
 * while data blobs are capped at 32,768 characters. Each object supporting external data can have one id and one data
 * blob stored with it. You can also use either or both of those fields.
 * <p>
 * The external id field is a good choice to create a reference between a resource in Asana and another database, such
 * as cross-referencing an Asana task with a customer record in a CRM, or a bug in a dedicated bug tracker. Since it is
 * just a unicode string, this field can store numeric IDs as well as URIs, however, when using URIs extra care must be
 * taken when forming queries that the parameter is escaped correctly. By assigning an external id you can use the
 * notation external:custom_id to reference your object anywhere that you may use the original object id.
 *
 * @api.link <a href="https://asana.com/developers/documentation/getting-started/custom-external-data">Custom External Data</a>
 */
public class ExternalData {
    private String id;
    private String data;

    public ExternalData() {
    }

    public ExternalData(String id, String data) {
        this.id = id;
        this.data = data;
    }

    /**
     * @return The external id. Max size is 1024 characters. Can be a URI.
     * @api.field <code>id</code>
     * @api.access Read-write
     */
    public String getId() {
        return this.id;
    }

    /**
     * Updates the external id.
     *
     * @param id The external id.
     * @see #getId()
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The external data blob. Max size is 32,786 characters.
     * @api.field <code>id</code>
     * @api.access Read-write
     */
    public String getData() {
        return this.data;
    }

    /**
     * Updates the stored data.
     *
     * @param data The external data blob.
     * @see #getData()
     */
    public void setData(String data) {
        this.data = data;
    }
}
