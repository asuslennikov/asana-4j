package ru.jewelline.asana4j.api.beans;

import ru.jewelline.asana4j.api.HasId;
import ru.jewelline.asana4j.api.HasName;

public abstract class BaseBean implements HasId {

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName()).append("(id=").append(getId());
        if (this instanceof HasName) {
            sb.append(", name=").append(((HasName) this).getName());
        } else {
            sb.append(", hash=").append(hashCode());
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HasId baseBean = (HasId) o;

        return getId() == baseBean.getId();

    }

    @Override
    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }
}
