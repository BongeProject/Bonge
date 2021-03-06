package org.bonge.convert.scoreboard;

import org.bonge.convert.Converter;
import org.spongepowered.api.scoreboard.criteria.Criteria;
import org.spongepowered.api.scoreboard.criteria.Criterion;

import java.io.IOException;

public class CriteriaConverter implements Converter<String, Criterion> {
    @Override
    public Class<Criterion> getSpongeClass() {
        return Criterion.class;
    }

    @Override
    public Class<String> getBukkitClass() {
        return String.class;
    }

    @Override
    public Criterion from(String criteria) throws IOException {
        switch (criteria){
            case "dummy": return Criteria.DUMMY.get();
            case "deathCount": return Criteria.DEATH_COUNT.get();
            case "playerKillCount": return Criteria.PLAYER_KILL_COUNT.get();
            case "totalKillCount": return Criteria.TOTAL_KILL_COUNT.get();
            case "health": return Criteria.HEALTH.get();
            case "trigger": return Criteria.TRIGGER.get();
            default: throw new IOException("Unknown criteria of '" + criteria + "'");
        }
    }

    @Override
    public String to(Criterion criterion) throws IOException{
        if(criterion.equals(Criteria.DEATH_COUNT.get())){
            return "deathCount";
        }
        if(criterion.equals(Criteria.DUMMY.get())){
            return "dummy";
        }
        if(criterion.equals(Criteria.HEALTH.get())){
            return "health";
        }
        if(criterion.equals(Criteria.PLAYER_KILL_COUNT.get())){
            return "playerKillCount";
        }
        if(criterion.equals(Criteria.TOTAL_KILL_COUNT.get())){
            return "totalKillCount";
        }
        if(criterion.equals(Criteria.TRIGGER.get())){
            return "trigger";
        }
        throw new IOException("Unknown criteria of " + criterion.toString());
    }
}
