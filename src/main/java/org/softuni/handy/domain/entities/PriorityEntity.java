package org.softuni.handy.domain.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import java.util.List;

@MappedSuperclass
public abstract class PriorityEntity extends BaseEntity implements Comparable<PriorityEntity> {

    private int priority;

    @Column(name = "priority", nullable = false)
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(PriorityEntity o) {
        return this.getPriority() - o.getPriority();
    }


}
