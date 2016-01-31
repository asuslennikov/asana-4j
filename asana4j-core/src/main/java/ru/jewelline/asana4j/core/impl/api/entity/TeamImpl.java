package ru.jewelline.asana4j.core.impl.api.entity;

import ru.jewelline.asana4j.api.entity.Team;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityContext;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityImpl;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldWriter;

import java.util.Arrays;
import java.util.List;

public class TeamImpl extends ApiEntityImpl<TeamImpl> implements Team {

    private long id;
    private String name;

    public TeamImpl(ApiEntityContext context) {
        super(TeamImpl.class, context);
    }

    @Override
    protected List<JsonFieldReader<TeamImpl>> getFieldReaders() {
        return Arrays.<JsonFieldReader<TeamImpl>>asList(TeamImplProcessor.values());
    }

    @Override
    protected List<JsonFieldWriter<TeamImpl>> getFieldWriters() {
        return Arrays.<JsonFieldWriter<TeamImpl>>asList(TeamImplProcessor.values());
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
