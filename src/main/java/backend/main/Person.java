package backend.main;

import java.util.List;

public class Person {

    long id;
    boolean isPermenant;
    List<Reason> reasons;

    public Person(long id, boolean isPermenant, List<Reason> reasons) {
        this.id = id;
        this.isPermenant = isPermenant;
        this.reasons = reasons;

    }
}
